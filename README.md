# Smart Video Tagger

This is an unfinished project. The motivation was to find an easy solution 
to normalize filenames for movies and series/shows so that they all would
have the same format making it easier to parse it for other services (like [DLNA](http://www.dlna.org/) server).

It basically walks through the file system reading filenames and metadata with [ffmpeg](https://ffmpeg.org/) .
Different analyzers (see `IAnalyzer` implementations) try to parse specific information from it
like date, season/episode, title etc. A very simple probability and guessing system tries then
the extrapolate the correct values for e.g. title.

Use the `extensions.txt` to configure which files to examine and the `ignore-words.txt`
to set which words in filenames will be ignored (ie. never have any meaning).

You need ffmpeg to use the metadata reading feature.
 
 There are stubs for a JavaFX (some old version) UI, altough they were never finished.

## Build

Use maven (3.1+) to create a jar

    mvn clean package

There is no user interface, the correct way to start the tagger can be seen in `Start.java`

## Tech Stack

* Java 1.6
* Maven
* Log4j

# License

Copyright 2012 Patrick Favre-Bulle

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.