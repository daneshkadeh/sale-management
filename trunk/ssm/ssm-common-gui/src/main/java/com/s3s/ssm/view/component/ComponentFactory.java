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

    public static IComponentInfo createSalesContractComponentInfo() {
        SalesContractSearchComponent psc = new SalesContractSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createAccountantComponentInfo() {
        AccountantSearchComponent psc = new AccountantSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createStorekeeperComponentInfo() {
        StorekeeperSearchComponent psc = new StorekeeperSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createSalerComponentInfo() {
        SalerSearchComponent psc = new SalerSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createCashierComponentInfo() {
        CashierSearchComponent psc = new CashierSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createOperatorComponentInfo() {
        OperatorSearchComponent psc = new OperatorSearchComponent();
        return new SearchComponentInfo(psc);
    }

    public static IComponentInfo createItemComponentInfo() {
        ItemSearchComponent psc = new ItemSearchComponent();
        return new SearchComponentInfo(psc);
    }
}
