module com.akhilesh.hrms.security.persistence {
    requires com.akhilesh.hrms.security.domain;
    requires java.persistence;
    requires java.validation;
    requires spring.beans;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.security.core;
    requires spring.security.crypto;

    exports com.akhilesh.hrms.security.persistence.repositories.concrete;


}