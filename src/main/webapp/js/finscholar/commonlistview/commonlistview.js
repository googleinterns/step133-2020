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
  
  constructor(dataHandler, template, containerID) {
    this.dataHandler_ = dataHandler;
    this.template_ = template;
    this.containerID_ = containerID;
    this.batch_ = 0;
    this.container_ = undefined;
    this.bindedScrollHandler_ = this.loadNextBatch_.bind(this);
    this.bindedScholarshipLoader_ = this.renderNextBatch_.bind(this);
  }

  /**
   * Renders a scholarship list view to the container.
   * @param {!Element} container The HTML container to load the view.
   */
  renderView(container) {
    container.innerHTML = commonlistview(null);
  }

  init(container) {
    const totalNumberOfItems = this.dataHandler_.getTotalNumber();
    this.container_ = container;
    window.addEventListener('scroll', this.bindedScrollHandler_);
    this.renderNextBatch_();
    this.renderNextBatch_();
    this.setContainerHeight_();
  }

  setContainerHeight_() {
    this.container_.style.height = '5000px';
  }

  renderNextBatch_() {
    const dataList = this.dataHandler_.getNextBatch(this.batch_ + 1);
    dataList.forEach(e => this.container_.innerHTML += this.template_(e));
    this.batch_ += 1;
  }

  loadNextBatch_() {
    // const body = document.body,
    // const html = document.documentElement;

    // const height = Math.max( body.scrollHeight, body.offsetHeight, 
    //                    html.clientHeight, html.scrollHeight, html.offsetHeight );
    const totalHeight = document.body.clientHeight;
    const scrolledHeight = window.scrollY;
    const browserHeight = window.innerHeight;
    const ratio = (browserHeight + scrolledHeight)/totalHeight;
    console.log(ratio);
    console.log(this.batch_);
    console.log((this.batch_ - 1)/this.batch_);
    if (ratio > (this.batch_ - 1)/this.batch_) {
      
      this.bindedScholarshipLoader_();
    }
  }
}

exports = {CommonListView};