module com.akhilesh.hrms.security.config {
    requires com.akhilesh.hrms.security.filter;
    requires spring.beans;
    requires spring.context;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.security.web;
    requires com.akhilesh.hrms.security.persistence;
    requires com.akhilesh.hrms.security.service;
    requires spring.security.crypto;
}