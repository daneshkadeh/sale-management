package com.s3s.ssm.entity.param;

/**
 * Country currency code. List: VND - Vietnam dong, USD - United States Dollar, EUR - Euro Member Countries, AUD -
 * Australia Dollar, SGD - Singapore Dollar, JPY - Japan Yen, CNY - China Yuan Renminbi
 * 
 * @see http://www.xe.com/iso4217.php
 * @author phamcongbang
 * 
 */
public enum CurrencyEnum {
    VND, USD, EUR, AUD, SGD, JPY, CNY;
    public static CurrencyEnum getDefaultCurrency() {
        return VND;
    }
}
