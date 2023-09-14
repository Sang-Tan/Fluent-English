package com.fluentenglish.web.admin.authen.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "admins",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Admin extends AuditableEntity {
    private String fullName;
    private String email;
    private String password;
    private boolean enabled;
}
