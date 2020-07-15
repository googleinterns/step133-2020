#!/bin/sh
#
# Copyright 2019 Google LLC
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License..
# You may obtain a copy of the License at
#
#     https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Update all submodules. Needed for the JS compiler and soy.
git submodule init
git submodule update

# Get deps for the _test.html page generator:
cd deps/closure-library/closure/bin/generate_closure_unit_tests
npm update || exit $?
cd - >/dev/null 2>&1

cd deps/closure-templates/
# Check to see if we have already build the soy jars.
if ! test -d "target"; then
echo "Building soy compiler..."
  mvn package
fi
cd - >/dev/null 2>&1
# We must change the working directory to set the working directory for the path
# of the generated js files.
cd src/main/webapp/js
echo "Removing old generated soy JS..."
rm -rf "soy_generated"
echo "Compiling soy sources..."
./../../../../common/soy_srcs.py
cd - >/dev/null 2>&1

# Generate deps for JS tests.
./deps/closure-library/closure/bin/build/depswriter.py \
  --root "deps/closure-library/closure/goog" \
  --root_with_prefix "deps/closure-templates/javascript ../soy/" \
  --root_with_prefix "deps/jsaction ../jsaction/" \
  --root_with_prefix "src/main/webapp/js ../" \
  --root_with_prefix "src/test/javascript ../../jstests" \
  --output_file "src/test/javascript/deps.js" || exit $?
# Remove target directory to build from scratch:
echo "Cleaning old target directory..."
rm -rf "./target"
