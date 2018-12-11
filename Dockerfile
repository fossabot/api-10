FROM gradle:jdk8-alpine AS builder
COPY . /home/gradle/src
WORKDIR /home/gradle/src
USER root
RUN gradle -x test clean unpack

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/home/gradle/src/build/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.dongfg.project.api.DongfgApiApplicationKt"]