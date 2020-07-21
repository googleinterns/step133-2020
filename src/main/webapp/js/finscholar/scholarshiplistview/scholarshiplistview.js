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

goog.module('finscholar.scholarshiplistview');

const JsactionActionFlow = goog.require('jsaction.ActionFlow');
const JsactionDispatcher = goog.require('jsaction.Dispatcher');
const JsactionEventContract = goog.require('jsaction.EventContract');
const {CommonListView} = goog.require('finscholar.commonlistview');
const {ScholarshipListDataHandler} = goog.require('datahandlers.scholarshiplistdatahandler');

const SCHOLARSHIP_LIST_INDEX = '1';

/** The mini controller for scholarship list view. */
class ScholarshipListView extends CommonListView {
  constructor() {
    super(new ScholarshipListDataHandler(), SCHOLARSHIP_LIST_INDEX);
    /** @private @const {!JsactionEventContract} */
    this.eventContract_ = new JsactionEventContract();

    /** @private @const {!JsactionDispatcher} */
    this.dispatcher_ = new JsactionDispatcher();

    /** @private @const {function(!JsactionActionFlow): undefined} */
    this.bindedOnclickHandler_ = this.handleOnclickEvent_.bind(this);
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
        'scholarshiplistview',  // the namespace
        null,                   // handler object
        {
          // action map
          'clickAction': this.bindedOnclickHandler_,
          'doubleClickAction': this.bindedOnclickHandler_,
        });
  }
  /**
   * Renders a scholarship list view to the container.
   * @override
   */
  async renderView() {
    try {
      await super.init();
      this.initJsaction_();
    } catch (e) {
      console.log(e);
      throw e;
    }
  }

  /**
   * Handles click and double click events on navbar.
   * @param {!JsactionActionFlow} flow Contains the data related to the action.
   *     and more. See actionflow.js.
   * @private
   */
  handleOnclickEvent_(flow) {
    console.log('jsaction fired on list item.');
    this.listeners.forEach((listener) => {
      listener(/** @type {!Element} */ (flow.node()));
    });
  }
}

exports = {ScholarshipListView};
