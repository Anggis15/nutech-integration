package com.apply.applyKerja.service;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 12.02
@Last Modified 04/12/24 12.02
Version 1.0
*/

import com.apply.applyKerja.dto.response.ResponseAllTransaction;
import com.apply.applyKerja.dto.response.ResponseTransaction;
import com.apply.applyKerja.model.MstService;
import com.apply.applyKerja.model.Transaction;
import com.apply.applyKerja.model.UserProfile;
import com.apply.applyKerja.repository.ServiceRepository;
import com.apply.applyKerja.repository.TransactionRepository;
import com.apply.applyKerja.repository.UserProfileRepository;
import com.apply.applyKerja.util.GetEMailFromToken;
import com.apply.applyKerja.util.GlobalFunction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    GetEMailFromToken getEMailFromToken = new GetEMailFromToken();

    @Transactional
    public ResponseEntity<Object> toupUpBalance(Long balance, HttpServletRequest request){
        String email = getEMailFromToken.getEmailFromToken(request.getHeader("Authorization"));
        LocalDateTime time = getEMailFromToken.getJakartaTime();
        Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
        String invoiceNumber = "TRX"+ UUID.randomUUID().toString().replaceAll("-","");
        if(opUser.isEmpty()){
            return GlobalFunction.generateResponse(108
                    , HttpStatus.BAD_REQUEST
                    ,"Tidak bisa topup tekendala System"
                    , null
                    , request);
        }
        UserProfile userProfile = opUser.get();
        Long totalBalance = userProfile.getBalance()+balance;
        try {
            userProfileRepository.editBalance(totalBalance,email);
            transactionRepository.insertTransaction(invoiceNumber,
                    "Top Up Balance",
                    balance,
                    "TOPUP",
                    time,
                    userProfile.getId());
        } catch (Exception e) {
            return GlobalFunction.generateResponse(108
                    ,HttpStatus.BAD_REQUEST
                    ,"Ada kesalahan pada system Mohon di tunngu beberapa saat lagi"
                    , null
                    , request);
        }
        Map<String,Object> response = new HashMap<>();
        response.put("ballance",totalBalance);
        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses",response,request);
    }

    public ResponseEntity<Object> getBalance(HttpServletRequest request){
        String email = getEMailFromToken.getEmailFromToken(request.getHeader("Authorization"));
        Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
        if(opUser.isEmpty()){
            return GlobalFunction.generateResponse(108
                    , HttpStatus.BAD_REQUEST
                    ,"Tidak bisa topup tekendala System"
                    , null
                    , request);
        }
        Map<String,Object> response = new HashMap<>();
        response.put("balance", opUser.get().getBalance());
        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses", response,request);
    }

    @Transactional
    public ResponseEntity<Object> doTransaction(String serviceCode, HttpServletRequest request){
        String email = getEMailFromToken.getEmailFromToken(request.getHeader("Authorization"));
        LocalDateTime time = getEMailFromToken.getJakartaTime();
        Optional<UserProfile> opUser = userProfileRepository.findByEmail(email);
        String invoiceNumber = "TRX"+ UUID.randomUUID().toString().replaceAll("-","");
        if(opUser.isEmpty()){
            return GlobalFunction.generateResponse(108
                    , HttpStatus.BAD_REQUEST
                    ,"Tidak bisa topup tekendala System"
                    , null
                    , request);
        }
        UserProfile userProfile = opUser.get();
        Optional<MstService> opService = serviceRepository.getServiceCode(serviceCode);
        if (opService.isEmpty()){
            return GlobalFunction.generateResponse(102
                    , HttpStatus.BAD_REQUEST
                    ,"Service ataus Layanan tidak ditemukan"
                    , null
                    , request);
        }
        MstService mstService = opService.get();
        Long totalAmount = mstService.getServiceTariff();
        Long totalBalance = userProfile.getBalance() - totalAmount;
        if(totalBalance<0){
            return GlobalFunction.generateResponse(103
                    , HttpStatus.BAD_REQUEST
                    ,"Balance yang dimiliki tidak cukup"
                    , null
                    , request);
        }

        try {
            userProfileRepository.editBalance(totalBalance,email);
            transactionRepository.insertTransaction(invoiceNumber,
                    mstService.getServiceName(),
                    totalAmount,
                    "PAYMENT",
                    time,
                    userProfile.getId());
        } catch (Exception e) {
            return GlobalFunction.generateResponse(108
                    ,HttpStatus.BAD_REQUEST
                    ,"Ada kesalahan pada system Mohon di tunngu beberapa saat lagi"
                    , null
                    , request);
        }

        ResponseTransaction responseTransaction = new ResponseTransaction();
        responseTransaction.setInvoiceNumber(invoiceNumber);
        responseTransaction.setServiceCode(mstService.getServiceCode());
        responseTransaction.setDescription(mstService.getServiceName());
        responseTransaction.setTotalAmount(totalAmount);
        responseTransaction.setCreatedOn(time);

        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses",responseTransaction,request);

    }

    public ResponseEntity<Object> getAllDataUseLimit(int limit, int offset, HttpServletRequest request){
        String id = getEMailFromToken.getIdFromToken(request.getHeader("Authorization"));
        System.out.println("ini id : " +id);
        ResponseAllTransaction response = new ResponseAllTransaction();
        List<ResponseTransaction> records = new ArrayList<>();
        System.out.println("ini limit : "+limit);
        List<Transaction> getAllTransaction = transactionRepository.getAllTrasanctionUseLimit(id,limit,offset);
        if(getAllTransaction.isEmpty()){
            getAllTransaction = transactionRepository.getAllTrasanctionUseLimit(id,10,0);
            if(getAllTransaction.isEmpty()){
                return GlobalFunction.generateResponse(107,HttpStatus.OK,"Akun belum memiliki transaksi", null, request);
            }
            for(Transaction data: getAllTransaction){
                ResponseTransaction transaction = new ResponseTransaction();
                transaction.setInvoiceNumber(data.getInvoiceNumber());
                transaction.setTransactionType(data.getTransactionType());
                transaction.setDescription(data.getDescription());
                transaction.setTotalAmount(data.getTotalAmount());
                transaction.setCreatedOn(data.getCreatedOn());
                records.add(transaction);
            }
            response.setLimit(limit);
            response.setOffset(offset);
            response.setRecords(records);
            return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses, Default limit 10 offset 1",response,request);
        }
        for(Transaction data: getAllTransaction){
            ResponseTransaction transaction = new ResponseTransaction();
            transaction.setInvoiceNumber(data.getInvoiceNumber());
            transaction.setTransactionType(data.getTransactionType());
            transaction.setDescription(data.getDescription());
            transaction.setTotalAmount(data.getTotalAmount());
            transaction.setCreatedOn(data.getCreatedOn());
            records.add(transaction);
        }
        response.setLimit(limit);
        response.setOffset(offset);
        response.setRecords(records);

        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses",response,request);
    }

    public ResponseEntity<Object> getAllDataNotLimit(HttpServletRequest request){
        String id = getEMailFromToken.getIdFromToken(request.getHeader("Authorization"));
        ResponseAllTransaction response = new ResponseAllTransaction();
        List<ResponseTransaction> records = new ArrayList<>();
        List<Transaction> getAllTransaction = transactionRepository.getAllTrasanctions(id);
        if (getAllTransaction.isEmpty()){
            return GlobalFunction.generateResponse(107,HttpStatus.OK,"Akun belum memiliki transaksi", null, request);
        }
        for(Transaction data: getAllTransaction){
            ResponseTransaction transaction = new ResponseTransaction();
            transaction.setInvoiceNumber(data.getInvoiceNumber());
            transaction.setTransactionType(data.getTransactionType());
            transaction.setDescription(data.getDescription());
            transaction.setTotalAmount(data.getTotalAmount());
            transaction.setCreatedOn(data.getCreatedOn());
            records.add(transaction);
        }
        response.setLimit(records.size());
        response.setRecords(records);

        return GlobalFunction.generateResponse(0,HttpStatus.OK,"Sukses",response,request);
    }

}
