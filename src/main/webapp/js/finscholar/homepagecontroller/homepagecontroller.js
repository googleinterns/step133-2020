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

const {BasicView} = goog.require('basicview');
const {homepage} = goog.require('finscholar.homepagecontroller.templates');

/**
 * Class for the home page controller.
 */
class HomePageController extends BasicView {
  constructor() {
    super();
    super.setCurrentContent(homepage());
  }

  /**
   * Loads the home page view.
   * @override
   */
  async renderView() {
    super.setCurrentContent(homepage());
    super.resetAndUpdate();
  }	  

}

exports = {HomePageController};
