module com.akhilesh.hrms.security.persistence {
    requires com.akhilesh.hrms.security.domain;
    requires java.persistence;
    requires java.validation;
    requires spring.beans;
    requires spring.context;

    exports com.akhilesh.hrms.security.persistence.repositories.concrete;
}