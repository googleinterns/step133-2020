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

/** @fileoverview Class for navbar. */

goog.module('finscholar.navbar');

const JsactionActionFlow = goog.require('jsaction.ActionFlow');
const JsactionDispatcher = goog.require('jsaction.Dispatcher');
const JsactionEventContract = goog.require('jsaction.EventContract');
const googDom = goog.require('goog.dom');


/**
 * Class for the navbar.
 */
class NavBar {
  constructor() {
    /** @private @const {!JsactionEventContract} */
    this.eventContract_ = new JsactionEventContract();

    /** @private @const {!JsactionDispatcher} */
    this.dispatcher_ = new JsactionDispatcher();

    /** @private @type {!Array<function(number): undefined>} */
    this.listeners_ = [];

    /** @private @const {function(!JsactionActionFlow): undefined} */
    this.bindedNavbarOnclickHandler_ =
        this.handleNavbarOnclickEvent_.bind(this);

    /** @private @type {number} */
    this.navbarPageIndex_ = 0;

    this.initJsaction_();
  }

  /**
   * Sets up the event handlers for elements in the navbar.
   * @private
   */
  initJsaction_() {
    // Events will be handled for all elements under this container.
    this.eventContract_.addContainer(
        /** @type {!Element} */ (googDom.getElement('navbar')));
    // Register the event types we care about.
    this.eventContract_.addEvent('click');
    this.eventContract_.addEvent('dblclick');
    this.eventContract_.dispatchTo(
        this.dispatcher_.dispatch.bind(this.dispatcher_));
    this.dispatcher_.registerHandlers(
        'navbar',  // the namespace
        null,      // handler object
        {
          // action map
          'clickAction': this.bindedNavbarOnclickHandler_,
          'doubleClickAction': this.bindedNavbarOnclickHandler_,
        });
  }

  /**
   * Handles click and double click events on navbar.
   * @param {!JsactionActionFlow} flow Contains the data related to the action.
   *     and more. See actionflow.js.
   * @private
   */
  handleNavbarOnclickEvent_(flow) {
    console.log('testing firing of handler.');
    const index = flow.node().getAttribute('index');
    this.navbarPageIndex_ = parseInt(index, 10);
    this.listeners_.forEach((listener) => {
      listener(this.navbarPageIndex_);
    });
  }

  /**
   * Takes an event to update with navbar updates.
   * @param {function(number): undefined} listener
   */
  registerListener(listener) {
    this.listeners_.push(listener);
  }
}

exports = {NavBar};
