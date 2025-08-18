package com.app.toolsUneedBack.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum TransactionType {
    EXPENSE,
    INCOME,
    TRANSFER;


    @JsonCreator
    public static TransactionType fromString(String value) {
        return TransactionType.valueOf(value.toUpperCase());
    }

}
