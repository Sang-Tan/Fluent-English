package com.fluentenglish.web.user.repository;

import com.fluentenglish.web.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullName(String username);
    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
}
