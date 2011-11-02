package com.s3s.ssm.entity.store;

import java.util.Date;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.param.Store;

public class ExchangeStoreForm extends AbstractCodeOLObject {
    private Store fromStore;
    private Store toStore;
    private String fromUser;
    private String toUser;
    private String responsibleUser;
    private Date createdDate;
    private Date sentDate;
    private Date receivedDate;
    private ExchangeStoreStatus status;

}
