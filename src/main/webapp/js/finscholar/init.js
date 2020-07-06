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

/** @fileoverview main entry point for the app */

goog.module('finscholar');

const GoogDom = goog.require('goog.dom');
const {welcome} = goog.require('example.templates');

/** @param {!string} message */
const sayHello = (message) => {
  const data = {greeting: message, year: new Date().getFullYear()};
  const html = welcome(data);
  GoogDom.getElement('hello').innerHTML = html;
};


window['onload'] = () => {
  sayHello('Carlos Medrano');
};
