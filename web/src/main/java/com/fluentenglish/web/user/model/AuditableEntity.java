package com.fluentenglish.web.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class AuditableEntity extends PersistableEntity {
    public AuditableEntity() {
    }
    @Column(nullable = false)
    @CreatedDate
    @Builder.Default
    protected LocalDateTime createdDate = LocalDateTime.now();

}
