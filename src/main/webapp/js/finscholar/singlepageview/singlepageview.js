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

/** @fileoverview This class renders view for scholarship. */

goog.module('finscholar.singlepageview');

const {BasicView} = goog.require('basicview');
const JsactionActionFlow = goog.require('jsaction.ActionFlow');
const JsactionDispatcher = goog.require('jsaction.Dispatcher');
const JsactionEventContract = goog.require('jsaction.EventContract');
const {SinglePageDataHandler} = goog.require('datahandlers.singlepagedatahandler');

/**
 * Class for single scholarship/college page view.
 * @public
 */
class SinglePageView extends BasicView {

  constructor(dataHandler, template) {
    super();

    /** 
     * @protected @const {!SinglePageDataHandler} dataHandler
     * The object fetches and formats scholarship data.
     */
    this.dataHandler = dataHandler;

    /**
     * @private @const {!function(Object): goog.soy.data.SanitizedHtml}
     */
    this.template_ = template;

    /** @protected {string} The id of the scholarhsip/college. */
    this.id = '';

    /**
     * @private @type {!Array<function(!Element): ?Promise<undefined>>}
     */
    this.listeners_ = [];

    /** @private @const {!JsactionEventContract} */
    this.eventContract_ = new JsactionEventContract();

    /** @private @const {!JsactionDispatcher} */
    this.dispatcher_ = new JsactionDispatcher();

    /** @private @const {function(!JsactionActionFlow): ?Promise<undefined>} */
    this.bindedOnclickHandler_ = this.handleOnclickEvent_.bind(this);
  }

  /**
   * Sets the id of the college or scholarship.
   * @param {string} id
   */
  setId(id) {
    this.id = id;
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
    this.eventContract_.dispatchTo(
        this.dispatcher_.dispatch.bind(this.dispatcher_));
    this.dispatcher_.registerHandlers(
        'singlepageview',  // the namespace
        null,              // handler object
        {
          // action map
          'clickAction': this.bindedOnclickHandler_,
          'doubleClickAction': this.bindedOnclickHandler_,
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
   * Render the single scholarship/college page.
   * @override
   */
  async renderView() {
    let formattedData = undefined;
    try {
      formattedData = await this.dataHandler.fetchAndFormatData(this.id);
    } catch (e) {
      console.log(e);
      throw new Error(`Cannot get data for object ${this.id}, message: ${e}`);
    }
    try {
      super.setCurrentContent(this.template_(formattedData));      
      super.resetAndUpdate();
      this.initJsaction_();
    } catch(e) {
      console.log(e);
      throw new Error(`Failed to generate html: ${e}`);
    }
  }

  /**
   * Registers a listener for jsaction.
   * @param {function(!Element): ?Promise<undefined>} listener
   */
  registerListener(listener) {
    this.listeners_.push(listener);
  }
}

exports = {SinglePageView};
