package com.fluentenglish.web.gaming.user.level;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLevelRepository extends JpaRepository<UserLevel, Integer> {
    Optional<UserLevel> findByLevel(Integer level);
}
