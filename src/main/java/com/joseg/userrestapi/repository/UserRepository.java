package com.joseg.userrestapi.repository;

import com.joseg.userrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);

    //int updateUserById(@Param("id") UUID id, @Param("token") String token);
}
