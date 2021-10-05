module com.akhilesh.hrms.security.service {
    requires com.akhilesh.hrms.security.domain;
    requires com.akhilesh.hrms.security.persistence;
    requires spring.beans;
    requires spring.context;
    requires spring.security.core;

    exports com.akhilesh.hrms.security.services;
}