package com.akhilesh.studentManagement.security.persistence.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditable{

    @Column(name = "created_by")
    @CreatedBy
    protected String createdBy;

    @Column(name = "last_modified_by")
    @LastModifiedBy
    protected String lastModifiedBy;

    @Column(name = "created_at")
    @CreatedDate
    protected LocalDateTime createdDate;

    @Column(name = "last_modified_at")
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

}
