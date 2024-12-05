package com.apply.applyKerja.repository;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 03/12/24 16.07
@Last Modified 03/12/24 16.07
Version 1.0
*/

import com.apply.applyKerja.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, String> {

    @Query(value = "SELECT * FROM user_profile u WHERE email = ? and is_active = 1", nativeQuery = true)
    Optional<UserProfile> findByEmail(String email);
    @Modifying
    @Query(value = "INSERT INTO user_profile (id, email, first_name, last_name, password, is_active, balance, created_on) VALUES (?, ?, ?, ?, ?, 1, 0, CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00'))", nativeQuery = true)
    void insertUserProfile(String id, String email, String firstName, String lastName, String password);
    @Modifying
    @Query(value = "UPDATE user_profile set first_name = ?, last_name = ?, updated_on =  CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00') where email = ? and is_active = 1", nativeQuery = true)
    void editProfile(String firstName, String lastName, String email);
    @Modifying
    @Query(value = "UPDATE user_profile set image = ?, updated_on =  CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00') where email = ? and is_active = 1", nativeQuery = true)
    void editImage(String image, String email);
    @Modifying
    @Query(value = "UPDATE user_profile set balance = ?, updated_on =  CONVERT_TZ(DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'), '+00:00', '+07:00') where email = ? and is_active = 1", nativeQuery = true)
    void editBalance(Long balance, String email);
}
