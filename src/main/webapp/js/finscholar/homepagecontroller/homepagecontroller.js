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

/** @fileoverview Base class for the page controller. */

goog.module('finscholar.homepagecontroller');

const JsactionActionFlow = goog.require('jsaction.ActionFlow');
const JsactionDispatcher = goog.require('jsaction.Dispatcher');
const JsactionEventContract = goog.require('jsaction.EventContract');
const GoogDom = goog.require('goog.dom');
const GoogSoy = goog.require('goog.soy');
const {homepage} = goog.require('finscholar.homepagecontroller.templates');
const {PageController} = goog.require('pagecontroller');

/**
 * Class for the home page controller.
 * @public
 */
class HomePageController extends PageController {
  constructor() {
    super();
    this.eventContract_ = new JsactionEventContract();
    // Events will be handled for all elements under this container.
    this.eventContract_.addContainer(
        /** @type {!Element} */ (GoogDom.getElement('main')));
    // Register the event types we care about.
    this.eventContract_.addEvent('click');
    this.dispatcher_ = new JsactionDispatcher();
    this.eventContract_.dispatchTo(
        this.dispatcher_.dispatch.bind(this.dispatcher_));
    this.dispatcher_.registerHandlers(
        'homepagecontroller',  // the namespace
        null,                  // handler object
        {
          // action map
          'clickAction': this.doStuff,
        });
  }

  /**
   * @return {!GoogSoy.data.SanitizedHtml} The rendered HTML of the common framework.
   */
  getContent() {
    return homepage();
  }

  /**
   * Do stuff when actions happen.
   * @param {!JsactionActionFlow} flow Contains the data related to the action
   *     and more. See actionflow.js.
   */
  doStuff(flow) {
    // do stuff
    console.log('doStuff called!');
  }
}

exports = {HomePageController};
