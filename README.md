# [api.dongfg.com](https://api.dongfg.com)
[![Build Status](https://travis-ci.org/dongfg/api.svg?branch=master)](https://travis-ci.org/dongfg/api)
[![CircleCI](https://circleci.com/gh/dongfg/api.svg?style=svg)](https://circleci.com/gh/dongfg/api)

my personal api build with spring boot 2 and kotlin


## Prerequisites
For security, i did not commit all config files. This is my config structure:
```shell
$ pwd
~/Projects/java/api/src/main/resources

$ tree -L 1 config
config
├── application-dev.yml
├── application-external.yml
├── application-prd.yml
└── application.yml

0 directories, 4 files

```
Explanation: 
* application.yml - common config
* application-dev/prd.yml - mysql, redis, mongo config
* application-external.yml - secret config (ApiProperty and spring security user)

> **ddl.sql located in doc folder**

## How to run
This is a springboot gradle project, just:
```shell
git clone https://github.com/dongfg/api.git && cd api
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

## Built With
* [Kotlin](https://kotlinlang.org/) - kotlin programming language
* [Spring Boot](https://projects.spring.io/spring-boot/) - The base framework
* [Graphql SPec](https://graphql.org/), [Graphql-java](https://github.com/graphql-java/graphql-java)  - API framework

## License
The MIT License (MIT)

Copyright (c) 2018 dongfg <mail@dongfg.com> (https://dongfg.com)

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
