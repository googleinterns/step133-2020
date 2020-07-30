// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/** @fileoverview The mini controller for scholarship list view. */

goog.module('finscholar.commonlistview');

const JsactionActionFlow = goog.require('jsaction.ActionFlow');
const JsactionDispatcher = goog.require('jsaction.Dispatcher');
const JsactionEventContract = goog.require('jsaction.EventContract');
const googDom = goog.require('goog.dom');
const googSoy = goog.require('goog.soy');
const {BasicView} = goog.require('basicview');
const {ListDataHandler} = goog.require('datahandlers.listdatahandler');
const {commonlistview, endoflist, listitems, loading} = goog.require('finscholar.commonlistview.templates');
const {ASCENDING, DESCENDING, SORT_PARAMS_MAP} = goog.require('datahandlers.collegequerybuilder');

const EMPTY_STRING = '';
const INVALID_RESPONSE = 'Invalid data from server.';
const ITEM = 'items';
const ITEM_CONTAINER_ID = 'table-body';
const STATUS_BAR_ID = 'status';
const SORT_BY_SELECTOR_ID = 'sortBy';
const SORT_ORDER_SELECTOR_ID = 'sortOrder';

/** The mini controller for scholarship list view. */
class CommonListView extends BasicView {
  /**
   * @param {!ListDataHandler} dataHandler
   * @param {string} optionTag
   */
  constructor(dataHandler, optionTag) {
    super();

    /** @private @const {!ListDataHandler} */
    this.dataHandler_ = dataHandler;

    /** @private @const {string} */
    this.optionTag_ = optionTag;

    /**
     * @private @const {function(!listitems.Params): !googSoy.data.SanitizedHtml}
     */
    this.template_ = listitems;

    /**
     * @private @type {!Array<function(!Element): ?Promise<undefined>>}
     */
    this.listeners_ = [];

    /**
     * @private @type {number} The number of batch of data has been loaded into the view.
     */
    this.batch_ = 0;

    /** @private {?Element} The container for all list items. */
    this.container_ = null;

    /** @private @const {function(): ?Promise<undefined>} */
    this.bindedScrollHandler_ = this.loadNextBatch_.bind(this);

    /** @private @const {function(number): ?Promise<undefined>} */
    this.bindedDataLoader_ = this.renderNextBatch_.bind(this);

    /** @private @const {function(!JsactionActionFlow): ?Promise<undefined>} */
    this.bindedSelectorHandler_ = this.changeSort_.bind(this);

    /** @private {number} Number of items to be added for each load. */
    this.itemsPerBatch_ = 5;

    /** @private {string} The id of the last item in the list. */
    this.idOfLastItem_ = EMPTY_STRING;

    /** @private {string} The sort parameter for the query. */
    this.sortBy_ = Array.from(SORT_PARAMS_MAP)[0][0];

    /** @private {string} The sort order for the query. */
    this.sortOrder_ = ASCENDING;

    /** @private {?Element} */
    this.sortBySelector_ = null;

    /** @private {?Element} */
    this.sortOrderSelector_ = null;
    
    /** @private {?Element} */
    this.statusBar_ = null;

    /** @private {?number} */
    this.totalItemsNumber_ = 0;

    /** @private {boolean} */
    this.isLoading_ = false;

    /** @private @const {!JsactionEventContract} */
    this.eventContract_ = new JsactionEventContract();

    /** @private @const {!JsactionDispatcher} */
    this.dispatcher_ = new JsactionDispatcher();

    /** @private @const {function(!JsactionActionFlow): ?Promise<undefined>} */
    this.bindedOnclickHandler_ = this.handleOnclickEvent_.bind(this);

    /** @private {?Element} */
    this.scrollDiv_ = null;
  }

  /**
   * Sets up the event handlers for elements in the list.
   * @private
   */
  initJsaction_() {
    // Events will be handled for all elements under this container.
    this.eventContract_.addContainer(
        /** @type {!Element} */ (super.getCurrentContentElement()));
    // Register the event types we care about.
    this.eventContract_.addEvent('click');
    this.eventContract_.addEvent('dblclick');
    this.eventContract_.addEvent('change');
    this.eventContract_.dispatchTo(
        this.dispatcher_.dispatch.bind(this.dispatcher_));
    this.dispatcher_.registerHandlers(
        'commonlistview',  // the namespace
        null,              // handler object
        {
          // action map
          'clickAction': this.bindedOnclickHandler_,
          'doubleClickAction': this.bindedOnclickHandler_,
          'change' : this.bindedSelectorHandler_
        });
  }

  /**
   * Handles click and double click events on navbar.
   * @param {!JsactionActionFlow} flow Contains the data related to the action.
   *     and more. See actionflow.js.
   * @private
   */
  async handleOnclickEvent_(flow) {
    this.listeners_.forEach(async (listener) => {
      await listener(/** @type {!Element} */ (flow.node()));
    });
  }

  /**
   * Loads the first two batches of list item to page.
   */
  async renderView() {
    super.setCurrentContent(commonlistview({
      pagetype: this.optionTag_,
      sortParams: SORT_PARAMS_MAP,
      asc: ASCENDING,
      desc: DESCENDING}));
    super.resetAndUpdate();
    this.initJsaction_();
    // tableContainer.innerHTML = commonlistview({pagetype: this.optionTag_});
    this.container_ = googDom.getElement(ITEM_CONTAINER_ID);
    this.statusBar_ = googDom.getElement(STATUS_BAR_ID);
    this.sortBySelector_ = googDom.getElement(SORT_BY_SELECTOR_ID);
    this.sortBySelector_.value = this.sortBy_;
    this.sortOrderSelector_ = googDom.getElement(SORT_ORDER_SELECTOR_ID);
    this.sortOrderSelector_.value = this.sortOrder_;
    window.addEventListener('scroll', this.bindedScrollHandler_);
    this.scrollDiv_ = googDom.getElement('scroll-div');
    this.scrollDiv_.addEventListener('scroll', this.bindedScrollHandler_);
    try {
      this.totalItemsNumber_ = await this.dataHandler_.getTotalNumber();
      await this.renderNextBatch_(this.itemsPerBatch_ * 2);
      this.batch_ = 2;
      while (this.scrollDiv_.scrollHeight <= this.scrollDiv_.clientHeight) {
        await this.renderNextBatch_(this.itemsPerBatch_);
      }
    } catch(e) {
      console.log(e);
      throw e;
    }
  }
  
  /** 
   * Changes the sort values for the query and 
   * rerenders using the new query parameters. 
   * @param {!JsactionActionFlow} flow Contains the data related to the action of
   *    changing sort params.
   * @private
   */
  async changeSort_(flow) {
    this.sortBy_ = googDom.getElement(SORT_BY_SELECTOR_ID).value;
    this.sortOrder_ = googDom.getElement(SORT_ORDER_SELECTOR_ID).value;
    this.batch_ = 0;
    this.listeners_.forEach(async (listener) => {
      await listener(/** @type {!Element} */ (flow.node()));
    });
  }

  /**
   * Loads the next batch of data and render to the view.
   * @param {number} numberOfItems The number of objects to be loaded from server.
   * @private
   */
  async renderNextBatch_(numberOfItems) {
    this.statusBar_.innerHTML = loading();
    try {
      this.isLoading_ = true;
      const dataBatch = await this.dataHandler_.getNextBatch(
          this.optionTag_, this.batch_, 
          numberOfItems, this.idOfLastItem_,
          this.sortBy_, this.sortOrder_);
      const dataList = dataBatch ? dataBatch[ITEM] : undefined;
      if (!dataBatch || !dataList || dataList.length == 0) {
        this.statusBar_.innerHTML = endoflist();
        this.isLoading_ = false;
        throw new Error(INVALID_RESPONSE);
      }
      this.idOfLastItem_ = 
          dataList ? dataList[dataList.length - 1][0] : EMPTY_STRING;
      this.container_.innerHTML += this.template_({batchofitems: dataBatch});
      this.statusBar_.innerHTML = endoflist();
      this.batch_ += 1;
    } catch (e) {
      console.log(e);
      throw e;
    } finally {
      this.isLoading_ = false;
    }
  }

  /**
   * Checks if the page is about to reach the bottom,
   * if so, load one more batch of data.
   * @private
   */
  async loadNextBatch_() {
    if (this.isLoading_) {
      return;
    }
    const cellHeight = googDom.getFirstElementChild(this.container_).offsetHeight;
    const scrolledHeight = this.scrollDiv_.scrollTop;
    const innerHeight = this.scrollDiv_.clientHeight;
    const threshold = (this.batch_ - 1) * this.itemsPerBatch_ * cellHeight;
    if (scrolledHeight + innerHeight > threshold &&
        this.batch_ * this.itemsPerBatch_ < this.totalItemsNumber_) {
      try {
        await this.bindedDataLoader_(this.itemsPerBatch_);
      } catch (e) {
        console.log(e);
        throw e;
      }
    }
  }

  /**
   * Registers a listener for jsaction.
   * @param {function(!Element): ?Promise<undefined>} listener
   */
  registerListener(listener) {
    this.listeners_.push(listener);
  }

  /**
   * Before the currently view is removed, call this function to remove
   * scroll event handler.
   */
  removeScrollHandler() {
    this.container_.removeEventListener('scroll', this.bindedScrollHandler_);
  }
}

exports = {CommonListView};
