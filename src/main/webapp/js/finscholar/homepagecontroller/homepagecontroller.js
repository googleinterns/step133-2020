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
const {CollegeListView} = goog.require('finscholar.collegelistview');
const {CollegePageView} = goog.require('finscholar.collegepageview');
const {ScholarshipListView} = goog.require('finscholar.scholarshiplistview');
const {PageController} = goog.require('pagecontroller');
const {ScholarshipPageView} = goog.require('finscholar.scholarshippageview');

/**
 * Class for the home page controller.
 */
class HomePageController extends PageController {

  /** 
   * @param {!Element} container The HTML div where the main frame is rendered.
   */
  constructor(container) {
    super();
    /** @private @const @type {!Element} */
    this.container_ = container;
    /** @private @const @type {!JsactionEventContract} */
    this.eventContract_ = new JsactionEventContract();
    /** @private @const @type {!JsactionDispatcher} */
    this.dispatcher_ = new JsactionDispatcher();
    /** @private @const bindedNavbarOnclickHandler_ The binded navbar onclick event handler. */
    this.bindedNavbarOnclickHandler_ = this.handleNavbarOnclickEvent_.bind(this);
    this.initJsaction_();
    /** @private */
    this.navbarPageIndex_ = 0;
    /** @private @const */
    this.TEMPLATE_HANDLERS_ = [new CollegeListView(), new ScholarshipListView(), new CollegePageView(), new ScholarshipPageView()];
    this.container_.innerHTML = this.getContent_();
    this.subView_ = GoogDom.getElement('content');
    this.renderPage_();
  }

  /**
   * Sets up the event handlers for elements in the main frame.
   * @private
   */
  initJsaction_() {
    // Events will be handled for all elements under this container.
    this.eventContract_.addContainer(
        /** @type {!Element} */ (GoogDom.getElement('main')));
    // Register the event types we care about.
    this.eventContract_.addEvent('click');
    this.eventContract_.addEvent('dblclick');
    this.eventContract_.dispatchTo(
        this.dispatcher_.dispatch.bind(this.dispatcher_));
    this.dispatcher_.registerHandlers(
      'homepagecontroller',  // the namespace
      null,                  // handler object
      {
        // action map
        'clickAction': this.bindedNavbarOnclickHandler_,
        'doubleClickAction' : this.bindedNavbarOnclickHandler_,
      });
  }

  /**
   * @return {!GoogSoy.data.SanitizedHtml} The rendered HTML of the common framework.
   * @private
   */
  getContent_() {
    return homepage();
  }

  /**
   * Handles click and double click events on navbar.
   * @param {!JsactionActionFlow} flow Contains the data related to the action
   *     and more. See actionflow.js.
   */
  handleNavbarOnclickEvent_(flow) {
    const index = flow.node().getAttribute('index');
    this.navbarPageIndex_ = index;
    this.renderPage_();
  }

  /**
   * Render the div with id 'content' based on the click event navber buttons.
   * @private
   */
  renderPage_() {
    this.TEMPLATE_HANDLERS_[this.navbarPageIndex_].renderView(this.subView_);
  }
}

exports = {HomePageController};
