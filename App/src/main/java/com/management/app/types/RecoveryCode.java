package com.management.app.types;

import java.time.LocalDateTime;
import java.util.Random;

public class RecoveryCode {

    private String code;
    private LocalDateTime timeStamp;

    public  RecoveryCode() {
        code = generateResetCode();
        timeStamp = LocalDateTime.now();
    }

    private String generateResetCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
