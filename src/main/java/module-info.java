module com.dongfg.project.api {
    requires static lombok;

    requires java.base;

    requires spring.jcl;
    requires spring.core;
    requires spring.beans;
    requires spring.context;
    requires spring.aop;
    requires spring.web;
    requires spring.expression;

    requires spring.boot;
    requires spring.boot.autoconfigure;

    requires spring.data.jpa;
    requires spring.data.mongodb;

    requires com.google.common;

    requires com.zaxxer.hikari;
    requires mongo.java.driver;

    requires java.annotation;
}