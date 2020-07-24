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

/** @fileoverview App state manager. */

goog.module('finscholar.appstate');

const {HomePageController} = goog.require('finscholar.homepagecontroller');
const {NavBar} = goog.require('finscholar.navbar');
const {navbarViewFactory} = goog.require('finscholar.viewfactory');


/** Class that keeps track of the app's state. */
class AppState {
  constructor() {
    if (AppState.instance_) {
      return AppState.instance_;
    }

    /** @private @const {!AppState} */
    AppState.instance_ = this;

    this.currentView_ = new HomePageController();
    this.currentView_.renderView();

    /** @private @type {!NavBar} Initializes the code for the nav bar. */
    this.navbarInstance_ = new NavBar();

    this.navbarInstance_.registerListener(this.navbarUpdate.bind(this));
  }

  /**
   * Returns the current app state instance.
   * @return {!AppState}
   */
  static getInstance() {
    if (AppState.instance_) {
      return AppState.instance_;
    }
    return new AppState();
  }

  /**
   * Handles updates from the nav bar.
   * @param {number} index The button mapped to the view that the user selected.
   */
  navbarUpdate(index) {
    this.currentView_ = navbarViewFactory(index);
    this.currentView_.renderView();
    this.navbarInstance_ = new NavBar();
    this.navbarInstance_.registerListener(this.navbarUpdate.bind(this));
  }
}

exports = {AppState};