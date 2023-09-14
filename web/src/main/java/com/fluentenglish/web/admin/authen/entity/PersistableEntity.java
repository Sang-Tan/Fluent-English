package com.fluentenglish.web.admin.authen.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;


import java.io.Serializable;

@Data
@MappedSuperclass
public class PersistableEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public PersistableEntity() {
    }
}
