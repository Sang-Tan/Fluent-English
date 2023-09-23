package com.fluentenglish.web.auth.admin;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    private String fullName;
    private String email;
    private String password;
    private boolean enabled;
}
