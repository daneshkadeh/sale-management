package com.s3s.ssm.interfaces.contact;

import java.util.List;

import com.s3s.ssm.dto.contact.CustEncouragementDTO;
import com.s3s.ssm.dto.contact.LongDebtCustDTO;
import com.s3s.ssm.entity.contact.Partner;

public interface IContactService {
    List<Partner> getPartners();

    List<Partner> getSuppliers();

    List<Partner> getCustomers();

    List<CustEncouragementDTO> statisticCustEncouragement(Partner customer);

    List<LongDebtCustDTO> statisticLongDebtCust(Partner customer);
}
