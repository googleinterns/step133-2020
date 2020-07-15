// Copyright The Closure Library Authors.
// SPDX-License-Identifier: Apache-2.0
var _allTests = ['jstests/finscholar/init_test.html'];

// If we're running in a nodejs context, export tests. Used when running tests
// externally on Travis.
if (typeof module !== 'undefined' && module.exports) {
  module.exports = _allTests;
}
