package com.app.toolsUneedBack.entity;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CustomerRole {
    USER,
    ADMIN;
    // MODERATOR("MODERATOR"),
    // SUPER_ADMIN("SUPER_ADMIN");


        @JsonCreator
        public static CustomerRole fromString(String value) {
            return CustomerRole.valueOf(value.toUpperCase());
        }

}
