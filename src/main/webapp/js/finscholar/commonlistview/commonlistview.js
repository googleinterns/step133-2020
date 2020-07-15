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
const {commonlistview} = goog.require('finscholar.commonlistview.templates');
const googDom = goog.require('goog.dom');
const NUMBER_PER_BATCH = 10;


/** The mini controller for scholarship list view. */
class CommonListView {
  
  constructor(dataHandler, template) {
    /** @private @const {ScholarshipListDataHandler} */
    this.dataHandler_ = dataHandler;
    /** @private @const {function():Element} */
    this.template_ = template;
    /** @private The number of batch of data has been loaded into the view. */
    this.batch_ = 0;
    /** @private {Element|null} The container for all list items. */
    this.container_ = null;
    /** @private @const {!function(jsaction.ActionFlow):undefined} */
    this.bindedScrollHandler_ = this.loadNextBatch_.bind(this);
    /** @private @const {!function():undefined} */
    this.bindedScholarshipLoader_ = this.renderNextBatch_.bind(this);
  }

  /** 
   * Loads the first two batches of list item to page. 
   * @param {!Element} container 
   */
  init(container) {
    const totalNumberOfItems = this.dataHandler_.getTotalNumber();
    this.container_ = container;
    window.addEventListener('scroll', this.bindedScrollHandler_);
    this.renderNextBatch_();
    this.renderNextBatch_();
  }

  /**
   * Loads the next batch of data and render to the view.
   */
  renderNextBatch_() {
    const dataList = this.dataHandler_.getNextBatch(this.batch_);
    console.log(dataList);
    try {
      this.container_.innerHTML += this.template_({scholarships: dataList});
    } catch (e) {
      console.log(e);
    }
    this.batch_ += 1;
  }

  /**
   * Check if the page is about to reach the bottom,
   * if so, load one more batch of data.
   */
  loadNextBatch_() {
    const totalHeight = document.body.clientHeight;
    const scrolledHeight = window.scrollY;
    const browserHeight = window.innerHeight;
    const ratio = (browserHeight + scrolledHeight)/totalHeight;
    if (ratio > (this.batch_ - 1)/this.batch_) {
      this.bindedScholarshipLoader_();
    }
  }
}

exports = {CommonListView};