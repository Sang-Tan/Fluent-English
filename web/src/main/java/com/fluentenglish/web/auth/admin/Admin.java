package com.fluentenglish.web.auth.admin;

import jakarta.persistence.*;

@Entity
@Table(name = "admins",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    private String fullName;
    private String email;
    private String password;
    private boolean enabled;

    public Admin(String fullName, String email, String password, boolean enabled) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
    }

    public Admin() {
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

    public Integer getId() {
        return id;
    }
}
