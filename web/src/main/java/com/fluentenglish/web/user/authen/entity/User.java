package com.fluentenglish.web.user.authen.entity;

import com.fluentenglish.web.admin.authen.entity.AuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User extends AuditableEntity {
    private String fullName;
    private String email;
    private String password;
    private boolean enabled;

    public User(String fullName, String email, String password, boolean enabled) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public User() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
