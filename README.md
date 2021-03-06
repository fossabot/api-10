# [api.dongfg.com](https://api.dongfg.com)
[![Build Status](https://travis-ci.org/dongfg/api.svg?branch=master)](https://travis-ci.org/dongfg/api)
[![codebeat badge](https://codebeat.co/badges/cf340d66-a053-4e3e-aac7-9d973d46ed63)](https://codebeat.co/projects/github-com-dongfg-api-master)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fdongfg%2Fapi.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fdongfg%2Fapi?ref=badge_shield)

my personal api build with spring boot 2 and kotlin


## Prerequisites
Spring Cloud Config or you can put config under `resources`
Config File :
- add datasource config
- add mongodb config
- add redis config
- add `api` config, structure must match `com.dongfg.project.api.common.property.ApiProperty`


## How to run
This is a springboot gradle project, just:
```shell
git clone https://github.com/dongfg/api.git && cd api
SPRING_PROFILES_ACTIVE=dev ./gradlew bootRun
```

## Built With
* [Kotlin](https://kotlinlang.org/) - kotlin programming language
* [Spring Boot](https://projects.spring.io/spring-boot/) - The base framework
* [Graphql Spec](https://graphql.org/), [Graphql-java](https://github.com/graphql-java/graphql-java)  - API framework

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


[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fdongfg%2Fapi.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fdongfg%2Fapi?ref=badge_large)