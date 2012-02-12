package com.s3s.ssm.utils;

import java.util.UUID;

public class InvoiceHelper {
    public static String getNextInvoiceNumber() {
        // Should specify the rule of invoice Number later. Now return unique id for testing
        return UUID.randomUUID().toString();
    }
}
