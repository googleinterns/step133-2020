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

const {ScholarshipListDataHandler} = goog.require('datahandlers.scholarshiplistdatahandler');
const {commonlistview, scholarshiplistitems, collegelistitems} = goog.require('finscholar.commonlistview.templates');
const googDom = goog.require('goog.dom');

const ITEM_CONTAINER_ID = 'table-body';

/** The mini controller for scholarship list view. */
class CommonListView {
  
  constructor(dataHandler, optionIndex) {
    /** @private @const {ScholarshipListDataHandler | CollegeListDataHandler} */
    this.dataHandler_ = dataHandler;
    /** @private @const {string} */
    this.optionIndex_ = optionIndex;
    /** @private @const {function({scholarships : !Array<?>}):Element} */
    this.template_ = this.optionIndex_ == '0' ? collegelistitems : scholarshiplistitems;
    /** @private {number} The number of batch of data has been loaded into the view. */
    this.batch_ = 0;
    /** @private {Element|null} The container for all list items. */
    this.container_ = null;
    /** @private @const {!function():Promise<undefined>} */
    this.bindedScrollHandler_ = this.loadNextBatch_.bind(this);
    /** @private @const {!function(number):Promise<undefined>} */
    this.bindedScholarshipLoader_ = this.renderNextBatch_.bind(this);
    /** @private {number} */
    this.itemsPerBatch_ = 15;
  }

  /** 
   * Loads the first two batches of list item to page. 
   * @param {!Element} tableContainer 
   * The container where the entire table is rendered to.
   */
  async init(tableContainer) {
    tableContainer.innerHTML = commonlistview({pageIndex: this.optionIndex_});
    this.container_ = googDom.getElement(ITEM_CONTAINER_ID)
    window.addEventListener('scroll', await this.bindedScrollHandler_);
    this.renderNextBatch_(this.itemsPerBatch_ * 2);
  }

  /**
   * Loads the next batch of data and render to the view.
   * @param {number} numberOfItems The number of objects to be loaded from server.
   */
  async renderNextBatch_(numberOfItems) {
    const dataList = await this.dataHandler_.getNextBatch(this.batch_, numberOfItems);
    try {
      this.container_.innerHTML += this.template_({scholarships: dataList});
    } catch (e) {
      console.log(e);
    }
    this.batch_ += 1;
  }

  /**
   * Checks if the page is about to reach the bottom,
   * if so, load one more batch of data.
   */
  async loadNextBatch_() {
    const cellHeight = googDom.getFirstElementChild(this.container_).offsetHeight;
    const scrolledHeight = window.scrollY;
    const browserHeight = window.innerHeight;
    const threshold = this.batch_ * this.itemsPerBatch_ * cellHeight;
    if (scrolledHeight + browserHeight > threshold) {
      await this.bindedScholarshipLoader_(this.itemsPerBatch_);
    }
  }
}

exports = {CommonListView};