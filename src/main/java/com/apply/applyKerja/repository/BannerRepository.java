package com.apply.applyKerja.repository;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 03/12/24 16.08
@Last Modified 03/12/24 16.08
Version 1.0
*/

import com.apply.applyKerja.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner, String> {

    @Query(value = "SELECT * FROM banner WHERE is_active = 1", nativeQuery = true)
    List<Banner> findAllBanner();
}
