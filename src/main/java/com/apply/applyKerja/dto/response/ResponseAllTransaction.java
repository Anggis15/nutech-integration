package com.apply.applyKerja.dto.response;

/*
IntelliJ IDEA 2022.3.1 (Community Edition)
Build #IC-223.8214.52, built on December 20, 2022
@Author asd a.k.a. Anggi Saputra
Java Developer
Created on 04/12/24 16.47
@Last Modified 04/12/24 16.47
Version 1.0
*/

import java.util.List;

public class ResponseAllTransaction {
    private int limit;
    private int offset;
    private List<ResponseTransaction> records;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public List<ResponseTransaction> getRecords() {
        return records;
    }

    public void setRecords(List<ResponseTransaction> records) {
        this.records = records;
    }
}
