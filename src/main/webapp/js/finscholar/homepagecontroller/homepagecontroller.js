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

const {CollegeListView} = goog.require('finscholar.collegelistview');
// const {CollegePageView} = goog.require('finscholar.collegepageview');
// const {ErrorPageView} = goog.require('finscholar.errorpageview');
const {PageController} = goog.require('pagecontroller');
const {ScholarshipListView} = goog.require('finscholar.scholarshiplistview');
// const {ScholarshipPageView} = goog.require('finscholar.scholarshippageview');
const {homepage} = goog.require('finscholar.homepagecontroller.templates');
const googDom = goog.require('goog.dom');
const googSoy = goog.require('goog.soy');
const jsactionActionFlow = goog.require('jsaction.ActionFlow');
const jsactionDispatcher = goog.require('jsaction.Dispatcher');
const jsactionEventContract = goog.require('jsaction.EventContract');

/**
 * Class for the home page controller.
 * @public
 */
class HomePageController extends PageController {

  /** 
   * @param {!Element} container The HTML div where the main frame is rendered.
   */
  constructor(container) {
    super();
    /** @private @const {!Element} */
    this.container_ = container;

    /** @private @const {!jsactionEventContract} */
    this.eventContract_ = new jsactionEventContract();

    /** @private @const {!jsactionDispatcher} */
    this.dispatcher_ = new jsactionDispatcher();

    /** @private @const {!function(jsaction.ActionFlow): undefined} */
    this.bindedNavbarOnclickHandler_ = this.handleNavbarOnclickEvent_.bind(this);

    /** @private {number} */
    this.navbarPageIndex_ = 0;

    /** @private @const 
     * {!Array<CollegeListView, ScholarshipListView, 
     *     CollegePageView, ScholarshipPageView, ErrorPageView>} 
     */
    this.TEMPLATE_HANDLERS_ = [
                               CollegeListView, 
                                ScholarshipListView, 
                               ];
    
    this.initJsaction_();

    this.container_.innerHTML = this.getContent_();

    /** @private @const {!Element} subView_*/
    this.subView_ = /** @type {!Element} */ (googDom.getElement('content'));
  }

  /**
   * Sets up the event handlers for elements in the main frame.
   * @private
   */
  initJsaction_() {
    // Events will be handled for all elements under this container.
    this.eventContract_.addContainer(
        /** @type {!Element} */ (googDom.getElement('main')));
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
   * @return {!googSoy.data.SanitizedHtml} The rendered HTML of the common framework.
   * @private
   */
  getContent_() {
    return homepage();
  }

  /**
   * Handles click and double click events on navbar.
   * @param {!jsactionActionFlow} flow Contains the data related to the action.
   *     and more. See actionflow.js.
   * @private
   */
  async handleNavbarOnclickEvent_(flow) {
    const index = flow.node().getAttribute('index');
    this.navbarPageIndex_ = parseInt(index, 10);
    await this.renderPage();
  }

  /**
   * Render the div with id 'content' based on the click event navber buttons.
   */
  async renderPage() {
    await (new this.TEMPLATE_HANDLERS_[this.navbarPageIndex_]).renderView(this.subView_);
  }
}

exports = {HomePageController};
