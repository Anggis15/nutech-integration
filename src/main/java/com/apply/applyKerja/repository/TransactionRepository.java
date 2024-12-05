package com.apply.applyKerja.repository;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 03/12/24 16.09
@Last Modified 03/12/24 16.09
Version 1.0
*/

import com.apply.applyKerja.model.Transaction;
import com.apply.applyKerja.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Modifying
    @Query(value = "INSERT INTO transaction (invoice_number, description ,total_amount ,transaction_type, created_on, id_user_profile) VALUES (?, ?, ?, ?, ?, ?)", nativeQuery = true)
    void insertTransaction(String invoiceNumber, String describtion, Long totalAmount, String transactionType, LocalDateTime time, String userProfileId);

    @Query(value = "SELECT * FROM transaction WHERE id_user_profile = ? ORDER BY created_on desc LIMIT ? OFFSET ?", nativeQuery = true)
    List<Transaction> getAllTrasanctionUseLimit(String id, int Limit, int offset);

    @Query(value = "SELECT * FROM transaction WHERE id_user_profile = ? ORDER BY created_on desc", nativeQuery = true)
    List<Transaction> getAllTrasanctions(String id);
}
