javax-to-jakarta
================

[![Build Status](https://travis-ci.com/KengoTODA/javax-to-jakarta.svg?branch=master)](https://travis-ci.com/KengoTODA/javax-to-jakarta)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg)](http://commitizen.github.io/cz-cli/)
[![semantic-release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)

This is a POC project to confirm that `javax.` package can be replaced with `jakarta.` package automatically for [supoprting this change](https://eclipse-foundation.blog/2019/05/03/jakarta-ee-java-trademarks/). This project provides several tols that supports to run old programs on new Jakarta EE that has no `javax.` package:

* [JVM agent](https://docs.oracle.com/en/java/javase/11/docs/api/java.instrument/java/lang/instrument/package-summary.html) (in progress)
* Gradle plugin (not started)
* Maven plugin (not started)

In short, it is possible to replace, but we probably cannot support reflection.

How to build
------------

Simply run `./gradlew` then it will run compile, test and assemble all projects.

To upgrade dependencies and plugins, run `./gradlew useLatestVersions` that is supported by [use-latest-versions plugin](https://github.com/patrikerdes/gradle-use-latest-versions-plugin).

Copyright
---------

Copyright 2019 Kengo TODA

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
