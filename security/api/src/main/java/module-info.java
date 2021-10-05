module com.akhilesh.hrms.security.api {
    requires com.akhilesh.hrms.security.domain;
    requires spring.beans;
    requires spring.web;
    requires spring.security.core;
    requires spring.context;
    requires com.fasterxml.jackson.annotation;
    requires java.validation;
    requires org.apache.commons.lang3;
    requires spring.context.support;

    exports com.akhilesh.hrms.security.rest.controllers.rest;
    exports com.akhilesh.hrms.security.rest.controllers.models.request;
    exports com.akhilesh.hrms.security.rest.controllers.models.response;
}