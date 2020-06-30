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

cd deps/closure-templates/
# Check to see if we have already build the soy jars.
if ! test -d "target"; then
  mvn package
fi
cd - >/dev/null 2>&1
# We must change the working directory to set the working directory for the path
# of the generated js files.
cd src/main/webapp
/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java -jar ../../../deps/closure-templates/target/soy-2019-10-08-SoyToJsSrcCompiler.jar \
  --allowExternalCalls false \
  --outputDirectory js/include \
  --srcs templates/*.soy || exit $?
cd - >/dev/null 2>&1
