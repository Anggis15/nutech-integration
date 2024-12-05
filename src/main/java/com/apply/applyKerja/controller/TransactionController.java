package com.apply.applyKerja.controller;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 14.50
@Last Modified 04/12/24 14.50
Version 1.0
*/

import com.apply.applyKerja.dto.request.RequestBalanceDTOValidasi;
import com.apply.applyKerja.dto.request.TransactionDTOValidasi;
import com.apply.applyKerja.service.MstServicesService;
import com.apply.applyKerja.service.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/balance")
    public ResponseEntity<Object> getBalance(HttpServletRequest request){
        return transactionService.getBalance(request);
    }

    @PostMapping("/topup")
    public ResponseEntity<Object> doTopup(@RequestBody RequestBalanceDTOValidasi amount, HttpServletRequest request){
        return transactionService.toupUpBalance(amount.getBalance(),request);
    }
    @PostMapping("/transaction")
    public ResponseEntity<Object> doTransaction(@RequestBody TransactionDTOValidasi serviceCode, HttpServletRequest request){
        return transactionService.doTransaction(serviceCode.getServiceCode(),request);
    }

    @GetMapping("/transaction/history")
    public ResponseEntity<Object> getAllHistoryTransaction(@RequestParam(required = false) Integer limit,
                                                           @RequestParam(required = false) Integer offset,
                                                           HttpServletRequest request){
        if (limit==null){
            return transactionService.getAllDataNotLimit(request);
        }
        offset = (offset==null)?0:offset;
        return transactionService.getAllDataUseLimit(limit,offset,request);
    }
}
