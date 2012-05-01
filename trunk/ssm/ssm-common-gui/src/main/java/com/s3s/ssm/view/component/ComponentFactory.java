package com.s3s.ssm.view.component;

import com.s3s.ssm.view.edit.IComponentInfo;
import com.s3s.ssm.view.edit.SearchComponentInfo;

/**
 * This factory help to create common components for presentation view.
 * 
 */
public class ComponentFactory {
    public static SearchComponentInfo createPartnerSearchInfo() {
        PartnerSearchComponent psc = new PartnerSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createProductComponentInfo() {
        ProductSearchComponent psc = new ProductSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createInvoiceComponentInfo() {
        InvoiceSearchComponent psc = new InvoiceSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createSalesInvoiceComponentInfo() {
        InvoiceSearchComponent psc = new InvoiceSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createAccountingStaffComponentInfo() {
        OperatorSearchComponent psc = new OperatorSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createStorekeeperComponentInfo() {
        OperatorSearchComponent psc = new OperatorSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createSalesContractComponentInfo() {
        SalesContractSearchComponent psc = new SalesContractSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createSalerComponentInfo() {
        OperatorSearchComponent psc = new OperatorSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createCashierComponentInfo() {
        OperatorSearchComponent psc = new OperatorSearchComponent();
        return new SearchComponentInfo(psc);
    }
}
