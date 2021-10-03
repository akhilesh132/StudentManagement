module com.akhilesh.hrms.security.domain {
    requires spring.security.core;
    requires com.auth0.jwt;

    exports com.akhilesh.hrms.security.domain.exceptions;
    exports com.akhilesh.hrms.security.domain.models;
    exports com.akhilesh.hrms.security.domain.services;
}