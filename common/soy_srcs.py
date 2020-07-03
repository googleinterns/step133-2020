#!/usr/bin/env python3
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

# Helper script to build soy sources.

from subprocess import call
import os


def listFiles(folder):
    for root, folders, files in os.walk(folder):
        for filename in folders + files:
            yield os.path.join(root, filename)


def buildCmdline():
    cmdline = [
        '/usr/lib/jvm/java-1.8.0-openjdk-amd64/bin/java', '-jar',
        '../../../../deps/closure-templates/target/soy-2019-10-08-SoyToJsSrcCompiler.jar',
        '--allowExternalCalls', 'false', '--inputRoots',
        os.getcwd(), '--outputDirectory', 'soy_generated'
    ]
    cmdline.append('--srcs')
    soyPaths = ""
    for filename in listFiles(os.path.abspath(os.path.join(os.getcwd(), os.pardir))):
        if filename[-4:] == '.soy':
            print("find template ", filename)
            soyPaths += filename + ","

    cmdline.append(soyPaths)
    return cmdline


cmdline = buildCmdline()
call(cmdline)
