/*
 * SSMDataLoader
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.s3s.ssm.entity.catalog.Advantage;
import com.s3s.ssm.entity.catalog.Article;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductProperty.PropertyType;
import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.entity.catalog.ProductType;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.entity.catalog.Store;
import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.entity.config.Institution;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.PaymentMode;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.entity.contact.ContactDebt;
import com.s3s.ssm.entity.contact.ContactShop;
import com.s3s.ssm.entity.contact.Customer;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerCategory;
import com.s3s.ssm.entity.contact.Supplier;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailSalesContract;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.entity.sales.ItemOriginPrice;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.DetailImportProduct;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.entity.store.ImportProductForm;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.DaoHelper;

/**
 * This data loader create a basic database so that the application can be tested. </br> NOTE: This class should only
 * run on test database. It cleans database before testing.
 * 
 * @author phamcongbang
 * 
 */

public class SSMDataLoader {
    public static Log s_logger = LogFactory.getLog(SSMDataLoader.class);
    private static final String NIKE_MANUFACTURER = "NIKE";
    private static final String PRODUCT_TYPE_SHOES = "SHOES";
    private static final String PRODUCT_GIAY_NAM = "GIAY_NAM";
    private static final String UOM_KG = "KG";
    private static final String COMPANY_ADDRESS = "28, Pham Hong Thai, P Ben Thanh, Q1, HCM";

    public static void main(String[] args) {
        // Not find solution to get class path from ssm-core.
        // String classpath = MainProgram.class.getClassLoader().get
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        s_logger.info("Starting data loader SSM...");
        ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");

        ConfigProvider configProvider = ConfigProvider.getInstance();
        DaoHelper daoHelper = configProvider.getDaoHelper();

        try {
            s_logger.info("Cleaning data SSM");
            cleanDatabase(daoHelper);

            s_logger.info("Initializing data SSM");
            initDatabase(daoHelper);

            s_logger.info("Testing data SSM");
            testInsertedData(daoHelper);
            s_logger.info("Finished data loader SSM");
            System.exit(0);
        } catch (Exception e) {
            s_logger.error("Error when init data, please check and clean test data", e);
        }
        s_logger.info("Error on data loader SSM!");
    }

    private static void cleanDatabase(DaoHelper daoHelper) {
        // daoHelper.getDao(Payment.class).deleteAll(daoHelper.getDao(Payment.class).findAll());
        // daoHelper.getDao(PaymentContent.class).deleteAll(daoHelper.getDao(PaymentContent.class).findAll());

        daoHelper.getDao(DetailSalesContract.class).deleteAll(daoHelper.getDao(DetailSalesContract.class).findAll());
        daoHelper.getDao(SalesContract.class).deleteAll(daoHelper.getDao(SalesContract.class).findAll());

        daoHelper.getDao(DetailExportStore.class).deleteAll(daoHelper.getDao(DetailExportStore.class).findAll());
        daoHelper.getDao(ExportStoreForm.class).deleteAll(daoHelper.getDao(ExportStoreForm.class).findAll());

        daoHelper.getDao(DetailImportProduct.class).deleteAll(daoHelper.getDao(DetailImportProduct.class).findAll());
        daoHelper.getDao(ImportProductForm.class).deleteAll(daoHelper.getDao(ImportProductForm.class).findAll());

        daoHelper.getDao(DetailInvoice.class).deleteAll(daoHelper.getDao(DetailInvoice.class).findAll());
        daoHelper.getDao(Invoice.class).deleteAll(daoHelper.getDao(Invoice.class).findAll());
        daoHelper.getDao(DetailSalesContract.class).deleteAll(daoHelper.getDao(DetailSalesContract.class).findAll());
        daoHelper.getDao(SalesContract.class).deleteAll(daoHelper.getDao(SalesContract.class).findAll());

        daoHelper.getDao(Article.class).deleteAll(daoHelper.getDao(Article.class).findAll());
        daoHelper.getDao(Advantage.class).deleteAll(daoHelper.getDao(Advantage.class).findAll());
        daoHelper.getDao(PackageLine.class).deleteAll(daoHelper.getDao(PackageLine.class).findAll());
        daoHelper.getDao(SPackage.class).deleteAll(daoHelper.getDao(SPackage.class).findAll());

        daoHelper.getDao(ItemPrice.class).deleteAll(daoHelper.getDao(ItemPrice.class).findAll());
        daoHelper.getDao(ItemOriginPrice.class).deleteAll(daoHelper.getDao(ItemOriginPrice.class).findAll());
        daoHelper.getDao(Item.class).deleteAll(daoHelper.getDao(Item.class).findAll());
        daoHelper.getDao(Product.class).deleteAll(daoHelper.getDao(Product.class).findAll());
        daoHelper.getDao(Manufacturer.class).deleteAll(daoHelper.getDao(Manufacturer.class).findAll());
        daoHelper.getDao(ProductType.class).deleteAll(daoHelper.getDao(ProductType.class).findAll());

        daoHelper.getDao(ProductProperty.class).deleteAll(daoHelper.getDao(ProductProperty.class).findAll());
        daoHelper.getDao(ProductPropertyElement.class).deleteAll(
                daoHelper.getDao(ProductPropertyElement.class).findAll());

        daoHelper.getDao(Supplier.class).deleteAll(daoHelper.getDao(Supplier.class).findAll());
        daoHelper.getDao(ContactDebt.class).deleteAll(daoHelper.getDao(ContactDebt.class).findAll());
        daoHelper.getDao(Customer.class).deleteAll(daoHelper.getDao(Customer.class).findAll());
        daoHelper.getDao(PartnerCategory.class).deleteAll(daoHelper.getDao(PartnerCategory.class).findAll());
        daoHelper.getDao(Store.class).deleteAll(daoHelper.getDao(Store.class).findAll());
        daoHelper.getDao(Operator.class).deleteAll(daoHelper.getDao(Operator.class).findAll());

        // Finance module
        // daoHelper.getDao(PaymentContent.class).deleteAll(daoHelper.getDao(PaymentContent.class).findAll());
        // Config module
        daoHelper.getDao(Organization.class).deleteAll(daoHelper.getDao(Organization.class).findAll());
        daoHelper.getDao(Institution.class).deleteAll(daoHelper.getDao(Institution.class).findAll());
        daoHelper.getDao(UploadFile.class).deleteAll(daoHelper.getDao(UploadFile.class).findAll());
        daoHelper.getDao(UnitOfMeasure.class).deleteAll(daoHelper.getDao(UnitOfMeasure.class).findAll());
        daoHelper.getDao(UomCategory.class).deleteAll(daoHelper.getDao(UomCategory.class).findAll());
        daoHelper.getDao(ExchangeRate.class).deleteAll(daoHelper.getDao(ExchangeRate.class).findAll());
        daoHelper.getDao(SCurrency.class).deleteAll(daoHelper.getDao(SCurrency.class).findAll());
        daoHelper.getDao(BankAccount.class).deleteAll(daoHelper.getDao(BankAccount.class).findAll());
        daoHelper.getDao(Bank.class).deleteAll(daoHelper.getDao(Bank.class).findAll());
    }

    private static void testInsertedData(DaoHelper daoHelper) {
        DetachedCriteria productDc = daoHelper.getDao(Product.class).getCriteria();
        productDc.add(Restrictions.eq("code", PRODUCT_GIAY_NAM));
        List<Product> listProduct = daoHelper.getDao(Product.class).findByCriteria(productDc);
        Product product = listProduct.get(0);
        s_logger.info("Product is saved OK : " + product.getCode() + ", " + product.getManufacturer().getCode());

        DetachedCriteria itemDC = daoHelper.getDao(Item.class).getCriteria();
        itemDC.add(Restrictions.eq("product.id", product.getId()));
        List<Item> listItem = daoHelper.getDao(Item.class).findByCriteria(itemDC);
        Item item = listItem.get(0);
        s_logger.info("Item is saved OK : " + item.getListUom().get(0).getCode() + ", price=" + item.getBaseSellPrice());

    }

    private static void initDatabase(DaoHelper daoHelper) {
        // Init data for config module
        List<UnitOfMeasure> listUom = initUOM(daoHelper);
        List<BankAccount> listBankAccount = initBankAccount(daoHelper);
        List<SCurrency> listSCurrency = initSCurrency(daoHelper);
        List<Organization> listOrganization = initOrganization(daoHelper, listBankAccount, listSCurrency);

        initManufacturer(daoHelper);
        initProductType(daoHelper);

        List<ProductProperty> properties = initProductProperty(daoHelper);
        List<ProductPropertyElement> elements = initProductPropertyElements(daoHelper, properties);

        List<Product> products = initProduct(daoHelper);
        List<Voucher> voucher = initVoucher(daoHelper);

        List<Item> listItem = initItem(daoHelper, listUom, products);
        List<SPackage> listPackage = initPackage(daoHelper, listItem);
        List<Advantage> listAdvantage = initAdvantage(daoHelper, listItem, listPackage);

        List<Operator> listOperator = initOperator(daoHelper);
        List<Partner> listContact = initContact(daoHelper, listBankAccount);
        List<ContactDebt> listContactDebt = initContactDebt(daoHelper, listContact);
        List<Supplier> listSupplier = initSupplier(daoHelper, listContact, listBankAccount);
        List<Store> listStore = initStore(daoHelper, listOperator);
        List<Article> listGoods = initGood(daoHelper, listStore, listItem);
        Set<ItemPrice> listItemPrices = initItemPrice(daoHelper, listItem, listContact);
        List<ItemOriginPrice> listItemOriginPrices = initItemOriginPrice(daoHelper, listItem, listSupplier);
        List<SalesContract> listSalesContracts = initSalesContracts(daoHelper, listSupplier, listItem);
        List<Invoice> listInvoice = initInvoice(daoHelper, listItem, listContact);
        // Init data for finance module
        // List<Payment> listPayments = initPayment(daoHelper, listInvoice, listContact);
    }

    private static List<Voucher> initVoucher(DaoHelper daoHelper) {

        Voucher voucher = new Voucher();
        voucher.setCode("VOCHER01");
        voucher.setName("Voucher 01");
        voucher.setDescription("For B2B only");
        voucher.setMinAmount(Money.create("VND", 500L));

        DetachedCriteria uomDC = daoHelper.getDao(UnitOfMeasure.class).getCriteria();
        uomDC.add(Restrictions.eq("code", "Cai"));
        voucher.setMainUom(daoHelper.getDao(UnitOfMeasure.class).findByCriteria(uomDC).get(0));
        ProductType type = daoHelper.getDao(ProductType.class).findAll().get(0);
        voucher.setType(type);
        voucher.setManufacturer(daoHelper.getDao(Manufacturer.class).findAll().get(0));// TODO: will be removed
        daoHelper.getDao(Voucher.class).saveOrUpdate(voucher);
        return Arrays.asList(voucher);
    }

    private static List<ProductPropertyElement> initProductPropertyElements(DaoHelper daoHelper,
            List<ProductProperty> properties) {
        ProductPropertyElement element1 = new ProductPropertyElement();
        element1.setProperty(properties.get(0));
        element1.setValue("GREEN");

        ProductPropertyElement element2 = new ProductPropertyElement();
        element2.setProperty(properties.get(0));
        element2.setValue("BLUE");
        daoHelper.getDao(ProductPropertyElement.class).save(element1);
        daoHelper.getDao(ProductPropertyElement.class).save(element2);
        return Arrays.asList(element1, element2);
    }

    private static List<ProductProperty> initProductProperty(DaoHelper daoHelper) {
        ProductProperty property1 = new ProductProperty();
        property1.setCode("COLOR");
        property1.setName("Mau sac");
        property1.setType(PropertyType.SIMPLE);
        daoHelper.getDao(ProductProperty.class).saveOrUpdate(property1);

        ProductProperty property2 = new ProductProperty();
        property2.setCode("SIZE");
        property2.setName("Kich thuoc");
        property2.setType(PropertyType.SIMPLE);
        daoHelper.getDao(ProductProperty.class).saveOrUpdate(property2);
        return Arrays.asList(property1, property2);
    }

    private static List<Advantage> initAdvantage(DaoHelper daoHelper, List<Item> listItem, List<SPackage> listPackage) {
        Advantage advantage = new Advantage();
        advantage.setCode("KHUYENMAI_2011");
        advantage.setName("Khuyen mai mua dong 2011");
        advantage.setDiscountPercent(0);
        advantage.getListBuyPackage().add(listPackage.get(0));
        advantage.getListGiftItem().add(listItem.get(0));
        daoHelper.getDao(Advantage.class).saveOrUpdate(advantage);
        return Arrays.asList(advantage);
    }

    private static List<SPackage> initPackage(DaoHelper daoHelper, List<Item> listItem) {
        SPackage pack = new SPackage();
        pack.setCode("Group_12_BLX");
        pack.setName("Goi 12 san pham BLX");
        pack.setMinTotalItemAmount(12);
        pack.setMaxTotalItemAmount(12);

        daoHelper.getDao(SPackage.class).saveOrUpdate(pack);

        PackageLine line = new PackageLine();
        line.setItem(listItem.get(0));
        line.setMinItemAmount(12);
        line.setMaxItemAmount(12);
        line.setOptional(false);
        line.setPackage(pack);
        daoHelper.getDao(PackageLine.class).saveOrUpdate(line);
        return Arrays.asList(pack);
    }

    private static List<ContactDebt> initContactDebt(DaoHelper daoHelper, List<Partner> listContact) {
        ContactDebt contactDebt = new ContactDebt();
        contactDebt.setPartner(listContact.get(0));
        contactDebt.setDebtMoney(1000000.0);
        contactDebt.setCurrency("VND");
        daoHelper.getDao(ContactDebt.class).saveOrUpdate(contactDebt);
        return Arrays.asList(contactDebt);
    }

    private static List<Payment> initPayment(DaoHelper daoHelper, List<Invoice> listInvoice, List<Partner> listContact) {

        PaymentContent pc1 = new PaymentContent();
        pc1.setCode("01"); // TODO: should use code rule of organization
        pc1.setPaymentType(PaymentType.PAY);
        pc1.setName("Chi tra khi nhap hang khach tra lai");
        pc1.setParent(null);

        PaymentContent pc2 = new PaymentContent();
        pc2.setCode("02"); // TODO: should use code rule of organization
        pc2.setPaymentType(PaymentType.PAY);
        pc2.setName("Chi tra nha cung cap");
        pc2.setParent(null);

        PaymentContent pc3 = new PaymentContent();
        pc3.setCode("03"); // TODO: should use code rule of organization
        pc3.setPaymentType(PaymentType.RECEIPT);
        pc3.setName("Khach tra tien mua hang");
        pc3.setParent(null);

        PaymentContent pc4 = new PaymentContent();
        pc4.setCode("04"); // TODO: should use code rule of organization
        pc4.setPaymentType(PaymentType.RECEIPT);
        pc4.setName("Thu lai khi xuat tra hang nha cung cap");
        pc4.setParent(null);
        List<PaymentContent> listPaymentContent = Arrays.asList(pc1, pc2, pc3, pc4);
        daoHelper.getDao(PaymentContent.class).saveOrUpdateAll(listPaymentContent);

        Payment payment = new Payment();
        payment.setPaymentContent(pc1);
        // payment.setPaymentMean(PaymentMode.CASH);
        // // payment.setContact(listContact.get(0));
        // // payment.setInvoice(listInvoice.get(0));
        // payment.setMoney(10000.0);
        // payment.setStatus(PaymentStatus.CLOSED);
        daoHelper.getDao(Payment.class).saveOrUpdate(payment);
        return Arrays.asList(payment);
    }

    /**
     * create 2 invoices "0000001" without contact and "0000002" with contact.
     */
    private static List<Invoice> initInvoice(DaoHelper daoHelper, List<Item> listItem, List<Partner> listContact) {
        Invoice invoice1 = new Invoice();
        invoice1.setContact(null);
        invoice1.setInvoiceNumber("0000001");
        invoice1.setCreatedDate(new Date());
        invoice1.setPaymentStatus(InvoicePaymentStatus.NO_PAYMENT);
        invoice1.setStatus(InvoiceStatus.OPEN);
        invoice1.setType(InvoiceType.SALES);
        invoice1.setMoneyBeforeTax(10000.0);
        invoice1.setMoneyOfTax(0.0);
        invoice1.setMoneyAfterTax(10000.0);
        daoHelper.getDao(Invoice.class).saveOrUpdate(invoice1);

        DetailInvoice detailInvoice = new DetailInvoice();
        detailInvoice.setInvoice(invoice1);
        detailInvoice.setItem(listItem.get(0));
        detailInvoice.setAmount(2);
        detailInvoice.setPriceBeforeTax(5000.0);
        detailInvoice.setPriceAfterTax(5000.0);
        detailInvoice.setMoneyBeforeTax(10000.0);
        detailInvoice.setMoneyAfterTax(10000.0);
        daoHelper.getDao(DetailInvoice.class).saveOrUpdate(detailInvoice);

        Invoice invoice2 = new Invoice();
        invoice2.setContact(listContact.get(0));
        invoice2.setInvoiceNumber("0000002");
        invoice2.setCreatedDate(new Date());
        invoice2.setPaymentStatus(InvoicePaymentStatus.NO_PAYMENT);
        invoice2.setStatus(InvoiceStatus.OPEN);
        invoice2.setType(InvoiceType.SALES);
        invoice2.setMoneyBeforeTax(22000.0);
        invoice2.setMoneyOfTax(0.0);
        invoice2.setMoneyAfterTax(22000.0);
        daoHelper.getDao(Invoice.class).saveOrUpdate(invoice2);

        DetailInvoice detailInvoice2 = new DetailInvoice();
        detailInvoice2.setInvoice(invoice2);
        detailInvoice2.setItem(listItem.get(0));
        detailInvoice2.setAmount(2);
        detailInvoice2.setPriceBeforeTax(6000.0);
        detailInvoice2.setPriceAfterTax(6000.0);
        detailInvoice2.setMoneyBeforeTax(12000.0);
        detailInvoice2.setMoneyAfterTax(12000.0);
        daoHelper.getDao(DetailInvoice.class).saveOrUpdate(detailInvoice2);

        DetailInvoice detailInvoice3 = new DetailInvoice();
        detailInvoice3.setInvoice(invoice2);
        detailInvoice3.setItem(listItem.get(0));
        detailInvoice3.setAmount(2);
        detailInvoice3.setPriceBeforeTax(5000.0);
        detailInvoice3.setPriceAfterTax(5000.0);
        detailInvoice3.setMoneyBeforeTax(10000.0);
        detailInvoice3.setMoneyAfterTax(10000.0);
        daoHelper.getDao(DetailInvoice.class).saveOrUpdate(detailInvoice3);
        return Arrays.asList(invoice1, invoice2);
    }

    private static Set<ItemPrice> initItemPrice(DaoHelper daoHelper, List<Item> listItem, List<Partner> listContact) {
        ItemPrice itemPrice = new ItemPrice();
        List<PartnerCategory> listPartnerCate = new ArrayList<>(listContact.get(0).getPartnerCateSet());
        itemPrice.setPartnerCategory(listPartnerCate.get(0));
        itemPrice.setSellPrice(100.0);
        itemPrice.setCurrency("VND");
        itemPrice.setItem(listItem.get(0));
        daoHelper.getDao(ItemPrice.class).saveOrUpdate(itemPrice);
        listItem.get(0).addItemPrice(itemPrice);
        daoHelper.getDao(Item.class).saveOrUpdate(listItem.get(0));
        return listItem.get(0).getListItemPrices();
    }

    private static List<ItemOriginPrice> initItemOriginPrice(DaoHelper daoHelper, List<Item> listItem,
            List<Supplier> listSupplier) {
        ItemOriginPrice originPrice = new ItemOriginPrice();
        originPrice.setItem(listItem.get(0));
        originPrice.setOriginalPrice(90.0);
        originPrice.setCurrency("VND");
        originPrice.setSupplier(listSupplier.get(0));
        daoHelper.getDao(ItemOriginPrice.class).saveOrUpdate(originPrice);
        return Arrays.asList(originPrice);
    }

    private static List<SalesContract> initSalesContracts(DaoHelper daoHelper, List<Supplier> listSuppliers,
            List<Item> listItem) {
        SalesContract salesContract = new SalesContract();
        salesContract.setCode("CO123456");
        salesContract.setSupplier(listSuppliers.get(0));
        salesContract.setDateContract(new Date());
        salesContract.setMoneyBeforeTax(1000.0);
        salesContract.setMoneyOfTax(10.0);
        salesContract.setMoneyAfterTax(990.0);
        salesContract.setCurrency("VND");

        DetailSalesContract detail = new DetailSalesContract();
        detail.setItem(listItem.get(0));
        detail.setAmount(5L);
        detail.setUnitPrice(100.0);
        detail.setCurrency("VND");
        salesContract.addDetailSalesContract(detail);

        daoHelper.getDao(SalesContract.class).saveOrUpdate(salesContract);
        return Arrays.asList(salesContract);
    }

    private static List<Article> initGood(DaoHelper daoHelper, List<Store> listStore, List<Item> listItem) {
        Article good = new Article();
        good.setBarcode("12345678910");
        good.setItem(listItem.get(0));
        good.setStore(listStore.get(0));
        good.setFirstMaintainDate(DateTime.now().plusMonths(6).toDate());
        daoHelper.getDao(Article.class).saveOrUpdate(good);
        return Arrays.asList(good);
    }

    private static List<BankAccount> initBankAccount(DaoHelper daoHelper) {
        Bank bank = new Bank();
        bank.setCode("NHPTNT_3");
        bank.setName("NGAN HANG PHAT TRIEN NONG THON CHI NHANH 3");
        bank.setAddress("569A, Nguyen Dinh Chieu, Q3, HCM, Viet Nam");
        daoHelper.getDao(Bank.class).save(bank);

        BankAccount usdBankAcct = new BankAccount();
        usdBankAcct.setBank(bank);
        usdBankAcct.setAccountNumber("1602 2010 19836");

        BankAccount vndBankAcct = new BankAccount();
        vndBankAcct.setBank(bank);
        vndBankAcct.setAccountNumber("1602 2010 19820");
        List<BankAccount> result = Arrays.asList(vndBankAcct, vndBankAcct);
        daoHelper.getDao(BankAccount.class).saveOrUpdateAll(result);
        return result;
    }

    private static List<SCurrency> initSCurrency(DaoHelper daoHelper) {
        SCurrency vndCurrency = new SCurrency();
        vndCurrency.setCode("01");
        vndCurrency.setName("VND");
        vndCurrency.setSymbol("d");

        SCurrency usdCurrency = new SCurrency();
        usdCurrency.setCode("02");
        usdCurrency.setName("USD");
        usdCurrency.setSymbol("$");

        List<SCurrency> result = Arrays.asList(vndCurrency, usdCurrency);
        daoHelper.getDao(SCurrency.class).saveOrUpdateAll(result);

        ExchangeRate exRate = new ExchangeRate();
        exRate.setCode("01");
        exRate.setCurrency(usdCurrency);
        exRate.setRate(21000);
        daoHelper.getDao(ExchangeRate.class).save(exRate);

        return result;
    }

    private static List<Organization> initOrganization(DaoHelper daoHelper, List<BankAccount> listBankAccount,
            List<SCurrency> listSCurrency) {
        Institution ins = initInstitution(daoHelper);
        Organization org1 = new Organization();
        // Organization information
        org1.setInstitution(ins);
        org1.setCode("HCM_BRANCH");
        org1.setName("Chi nhanh HCM");
        org1.setAddress(COMPANY_ADDRESS);
        // Bank information
        org1.setBeneficeName("THU SPORTS");
        if (listBankAccount != null && listBankAccount.size() == 2) {
            org1.setUsdBankAcct(listBankAccount.get(0));
            org1.setVndBankAcct(listBankAccount.get(1));
        }
        // General Information
        if (listSCurrency != null) {
            SCurrency defCurrency = listSCurrency.get(1);
            org1.setDefCurrency(defCurrency);
        }
        org1.setDefPaymentMethod(PaymentMode.CASH);
        org1.setEnableChangeInvDate(0);
        // rule of code generation
        org1.setSellOnCredit(2); // ko cho phep ban am

        List<Organization> result = Arrays.asList(org1);
        daoHelper.getDao(Organization.class).saveOrUpdateAll(result);
        return result;
    }

    private static Institution initInstitution(DaoHelper daoHelper) {
        Institution institution = new Institution();
        institution.setCode("THUSPORTS");
        institution.setCompanyName("THU SPORTS");
        institution.setAgent("Huynh Thi Dieu Hien");
        institution.setPosition("Tong giam doc");
        institution.setCompanyAddress(COMPANY_ADDRESS);
        institution.setTel("(848) 38220541");
        institution.setFax("84 - 8 - 38220542");
        institution.setWebsite("www.thusport.com");
        institution.setEmail("support@thusport.com");
        daoHelper.getDao(Institution.class).save(institution);
        return institution;
    }

    private static List<Store> initStore(DaoHelper daoHelper, List<Operator> listOperator) {
        Store store = new Store();
        store.setCode("K05");
        store.setAddress(COMPANY_ADDRESS);
        store.setStoredAddress(COMPANY_ADDRESS);
        store.setExportAddress(COMPANY_ADDRESS);
        store.setImportAddress(COMPANY_ADDRESS);
        store.setName("Kho 05");
        store.setManagerCode("OPERATOR1");
        daoHelper.getDao(Store.class).saveOrUpdate(store);
        return Arrays.asList(store);
    }

    private static List<Supplier> initSupplier(DaoHelper daoHelper, List<Partner> listContact,
            List<BankAccount> listBankAccount) {
        Supplier supplier = new Supplier();
        supplier.setCode("NIKE");
        supplier.setName("Nike company");
        supplier.setSex(true); // TODO: why supplier has sex? I think Sex should only be applied on individual
        // supplier.setPhoneNumber("0909825783");
        // supplier.setMainContact(listContact.get(0));

        // supplier.setBankAccount(listBankAccount.get(0));
        daoHelper.getDao(Supplier.class).saveOrUpdate(supplier);
        return Arrays.asList(supplier);
    }

    private static List<Partner> initContact(DaoHelper daoHelper, List<BankAccount> listBankAccounts) {
        PartnerCategory contactType = new PartnerCategory();
        contactType.setCode("B2B");
        contactType.setName("B2B");
        // contactType.setContactFamilyType(ContactFamilyType.CUSTOMER);
        // contactType.setDescription("Ban si - Bussiness to bussiness");
        daoHelper.getDao(PartnerCategory.class).saveOrUpdate(contactType);

        Partner contact = new Customer();
        contact.setCode("CONTYBANLE123");
        contact.setName("Cong ty ban le 123");
        // contact.setFullName("Cong ty ban le 123");
        Set<PartnerCategory> partnerCateSet = new HashSet<PartnerCategory>();
        partnerCateSet.add(contactType);
        contact.setPartnerCateSet(partnerCateSet);
        // contact.setEmail("banle123@solution3s.com");

        // contact.setBankAccount(listBankAccounts.get(1));
        // contact.setMaximumDayDebt(100L);

        // daoHelper.getDao(Contact.class).saveOrUpdate(contact);

        ContactShop shop = new ContactShop();
        shop.setCode("GIAY_SO_1");
        shop.setName("Cua hang giay so 1");
        // shop.setContact(contact);
        shop.setAddress("123 Bui vien");
        shop.setFixPhone("0909000000");
        shop.setRemark("Mo cua 8h-21h");
        // daoHelper.getDao(ContactShop.class).saveOrUpdate(shop);

        ((Customer) contact).addShop(shop);
        daoHelper.getDao(Partner.class).saveOrUpdate(contact);
        return Arrays.asList(contact);
    }

    private static List<Operator> initOperator(DaoHelper daoHelper) {
        Operator operator = new Operator();
        // operator.setLogin("testOperator");
        operator.setCode("admin"); // TODO: what is this?
        operator.setUsername("admin");
        operator.setPassword("admin");
        operator.setEmail("test@solution3s.com");
        operator.setFullName("Test Operator");
        // operator.setActive(true);
        daoHelper.getDao(Operator.class).saveOrUpdate(operator);
        return Arrays.asList(operator);
    }

    private static List<UnitOfMeasure> initUOM(DaoHelper daoHelper) {
        UomCategory category = new UomCategory();
        category.setCode("Weight");
        category.setName("Can nang");
        daoHelper.getDao(UomCategory.class).saveOrUpdate(category);

        UnitOfMeasure kg = new UnitOfMeasure();
        kg.setCode(UOM_KG);
        // pair.setName(""); // TODO: error UTF-8 here. Please careful for production
        kg.setName("Kilogam");
        kg.setUomCategory(category);
        kg.setIsBaseMeasure(false);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(kg);

        UnitOfMeasure gam = new UnitOfMeasure();
        gam.setCode("G");
        gam.setName("gam");
        gam.setUomCategory(category);
        gam.setIsBaseMeasure(false);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(gam);

        UomCategory category2 = new UomCategory();
        category2.setCode("Unit");
        category2.setName("Don vi dem");
        daoHelper.getDao(UomCategory.class).saveOrUpdate(category2);

        UnitOfMeasure cai = new UnitOfMeasure();
        cai.setCode("Cai");
        // pair.setName(""); // TODO: error UTF-8 here. Please careful for production
        cai.setName("Cai");
        cai.setUomCategory(category2);
        cai.setIsBaseMeasure(false);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(cai);
        return Arrays.asList(cai, gam, cai);
    }

    private static List<Item> initItem(DaoHelper daoHelper, List<UnitOfMeasure> listUom, List<Product> products) {
        Item item = new Item();
        item.setProduct(products.get(0));
        item.setBaseSellPrice(10000.0);
        item.setListUom(Arrays.asList(listUom.get(0)));
        item.setSumUomName("size 39");
        item.setCurrency("VND");

        ItemPropertyValue propertyValue = new ItemPropertyValue();
        List<ProductProperty> properties = new ArrayList<>(products.get(0).getProperties());
        propertyValue.setProperty(properties.get(0));
        DetachedCriteria elementDc = daoHelper.getDao(ProductPropertyElement.class).getCriteria();
        elementDc.add(Restrictions.eq("property", properties.get(0)));
        List<ProductPropertyElement> elements = daoHelper.getDao(ProductPropertyElement.class).findByCriteria(
                elementDc, 0, 1);

        propertyValue.setElement(elements.get(0));
        // item.addPropertyValue(propertyValue);//TODO: error with this
        daoHelper.getDao(Item.class).saveOrUpdate(item);

        return Arrays.asList(item);
    }

    private static List<Product> initProduct(DaoHelper daoHelper) {
        Product product = new Product();
        product.setCode(PRODUCT_GIAY_NAM);
        product.setName("Giay nam");
        product.setDescription("Giay nam choi tennis");
        product.setModel("Model100");

        DetachedCriteria uomDC = daoHelper.getDao(UnitOfMeasure.class).getCriteria();
        uomDC.add(Restrictions.eq("code", UOM_KG));
        product.setMainUom(daoHelper.getDao(UnitOfMeasure.class).findByCriteria(uomDC).get(0));
        ProductType type = daoHelper.getDao(ProductType.class).findAll().get(0);

        Manufacturer manufacturer = daoHelper.getDao(Manufacturer.class).findAll().get(0);

        product.setType(type);
        product.setManufacturer(manufacturer);
        product.addProperty(daoHelper.getDao(ProductProperty.class).findAll().get(0));
        daoHelper.getDao(Product.class).saveOrUpdate(product);

        return Arrays.asList(product);
    }

    protected static void init100Product(DaoHelper daoHelper) {
        DetachedCriteria uomDC = daoHelper.getDao(UnitOfMeasure.class).getCriteria();
        uomDC.add(Restrictions.eq("code", UOM_KG));

        ProductType type = daoHelper.getDao(ProductType.class).findAll().get(0);

        Manufacturer manufacturer = daoHelper.getDao(Manufacturer.class).findAll().get(0);
        UnitOfMeasure unitOfMeasure = daoHelper.getDao(UnitOfMeasure.class).findByCriteria(uomDC).get(0);

        for (int i = 0; i < 200; i++) {
            Product product = new Product();
            product.setCode(PRODUCT_GIAY_NAM + i);
            product.setName("Giay nam " + i);
            product.setDescription("Giay nam choi tennis");
            product.setModel("Model100" + i);

            product.setMainUom(unitOfMeasure);
            product.setType(type);
            product.setManufacturer(manufacturer);
            daoHelper.getDao(Product.class).saveOrUpdate(product);
        }
    }

    private static List<ProductType> initProductType(DaoHelper daoHelper) {
        ProductType productType = new ProductType();
        productType.setCode(PRODUCT_TYPE_SHOES);
        productType.setName("Giay dep");
        productType.setProductFamilyType(ProductFamilyType.GOODS);
        daoHelper.getDao(ProductType.class).saveOrUpdate(productType);
        return Arrays.asList(productType);
    }

    private static List<Manufacturer> initManufacturer(DaoHelper daoHelper) {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCode(NIKE_MANUFACTURER);
        manufacturer.setName("Nike corporation");
        daoHelper.getDao(Manufacturer.class).saveOrUpdate(manufacturer);
        return Arrays.asList(manufacturer);
    }
}
