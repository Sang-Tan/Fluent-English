package com.fluentenglish.web.user;

import com.fluentenglish.web.admin.authen.entity.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User extends AuditableEntity {
    private String fullName;
    private String email;
    private String password;
    private boolean enabled;
}
