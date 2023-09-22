package com.fluentenglish.web.auth.admin;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "admins",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
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
}
