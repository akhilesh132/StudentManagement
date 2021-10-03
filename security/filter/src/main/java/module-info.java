module com.akhilesh.hrms.security.filter {
    requires com.akhilesh.hrms.security.domain;
    requires spring.beans;
    requires spring.security.core;
    requires spring.security.web;
    requires spring.context;
    requires org.apache.tomcat.embed.core;
    requires spring.web;

    exports com.akhilesh.hrms.security.filters;
}