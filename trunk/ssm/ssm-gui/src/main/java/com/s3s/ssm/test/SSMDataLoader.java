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

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;

import com.s3s.ssm.entity.catalog.Advantage;
import com.s3s.ssm.entity.catalog.Article;
import com.s3s.ssm.entity.catalog.Goods;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.ItemPrice;
import com.s3s.ssm.entity.catalog.ItemPropertyValue;
import com.s3s.ssm.entity.catalog.Manufacturer;
import com.s3s.ssm.entity.catalog.PackageLine;
import com.s3s.ssm.entity.catalog.PackageLineItemPrice;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.catalog.ProductFamilyType;
import com.s3s.ssm.entity.catalog.ProductProperty;
import com.s3s.ssm.entity.catalog.ProductProperty.PropertyType;
import com.s3s.ssm.entity.catalog.ProductPropertyElement;
import com.s3s.ssm.entity.catalog.ProductType;
import com.s3s.ssm.entity.catalog.SPackage;
import com.s3s.ssm.entity.catalog.Service;
import com.s3s.ssm.entity.catalog.Voucher;
import com.s3s.ssm.entity.config.Address;
import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.BankAccount;
import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.entity.config.Institution;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.entity.config.UploadFile;
import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.entity.contact.ContactDebt;
import com.s3s.ssm.entity.contact.CustomerProfile;
import com.s3s.ssm.entity.contact.Individual;
import com.s3s.ssm.entity.contact.IndividualRoleEnum;
import com.s3s.ssm.entity.contact.IndividualTitleEnum;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerCategory;
import com.s3s.ssm.entity.contact.PartnerProfile;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.entity.contact.SupplierProfile;
import com.s3s.ssm.entity.finance.ContractPayment;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.finance.PaymentMode;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.sales.ContractDocument;
import com.s3s.ssm.entity.sales.ContractDocument.DocumentPlaceEnum;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailSalesContract;
import com.s3s.ssm.entity.sales.ImportationSC;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.entity.sales.ItemOriginPrice;
import com.s3s.ssm.entity.sales.SalesConfirm;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.entity.shipment.TransportationType;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.entity.store.DetailMoveStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.entity.store.ImportStoreForm;
import com.s3s.ssm.entity.store.MoveStoreForm;
import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.model.Money;
import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.DaoHelper;
import com.s3s.ssm.util.ServiceProvider;

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
    private static final String COMPANY_ADDRESS = "28, Pham Hong Thai, P Ben Thanh, Q1, TP.HCM";
    private static final String STORE_Q1 = "Kho Q1";
    private static final String STORE_Q1_ADDRESS = "28, Pham Hong Thai, P Ben Thanh, Q1, TP.HCM";
    private static final String STORE_Q9 = "Kho Q9";
    private static final String STORE_Q9_ADDRESS = "34 Thuy Loi, P. Phuoc Long A, Q9, TP.HCM";

    ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();

    public static void main(String[] args) {
        // Not find solution to get class path from ssm-core.
        // String classpath = MainProgram.class.getClassLoader().get
        DOMConfigurator.configure("src/main/resources/log4j.xml");
        s_logger.info("Starting data loader SSM...");
        // ApplicationContext appContext = new ClassPathXmlApplicationContext("config/BeanLocations.xml");
        // AnnotationSessionFactoryBean sessionFactory = (AnnotationSessionFactoryBean) appContext
        // .getBean("sessionFactory");
        // sessionFactory.getHibernateProperties().put("hibernate.hbm2ddl.auto", "create-drop");

        ConfigProvider configProvider = ConfigProvider.getInstance();
        DaoHelper daoHelper = configProvider.getDaoHelper();
        ServiceProvider serviceProvider = ConfigProvider.getInstance().getServiceProvider();

        try {
            s_logger.info("Cleaning data SSM");
            cleanDatabase(daoHelper);

            s_logger.info("Initializing data SSM");
            initDatabase(daoHelper, serviceProvider);

            s_logger.info("Testing data SSM");
            testInsertedData(daoHelper);
            s_logger.info("Finished data loader SSM");
            System.exit(0);
        } catch (Exception e) {
            s_logger.error("Error when init data, please check and clean test data", e);
            System.exit(0);
        }
        s_logger.info("Error on data loader SSM!");
    }

    private static void cleanDatabase(DaoHelper daoHelper) {
        // Finance module
        daoHelper.getDao(ContractPayment.class).deleteAll(daoHelper.getDao(ContractPayment.class).findAll());
        daoHelper.getDao(Payment.class).deleteAll(daoHelper.getDao(Payment.class).findAll());
        daoHelper.getDao(PaymentContent.class).deleteAll(daoHelper.getDao(PaymentContent.class).findAll());
        // Store module
        daoHelper.getDao(ShipPrice.class).deleteAll(daoHelper.getDao(ShipPrice.class).findAll());
        daoHelper.getDao(ShipPriceType.class).deleteAll(daoHelper.getDao(ShipPriceType.class).findAll());
        daoHelper.getDao(DetailImportStore.class).deleteAll(daoHelper.getDao(DetailImportStore.class).findAll());
        daoHelper.getDao(ImportStoreForm.class).deleteAll(daoHelper.getDao(ImportStoreForm.class).findAll());

        daoHelper.getDao(ImportationSC.class).deleteAll(daoHelper.getDao(ImportationSC.class).findAll());
        daoHelper.getDao(DetailSalesContract.class).deleteAll(daoHelper.getDao(DetailSalesContract.class).findAll());
        daoHelper.getDao(SalesContract.class).deleteAll(daoHelper.getDao(SalesContract.class).findAll());
        daoHelper.getDao(ContractDocument.class).deleteAll(daoHelper.getDao(ContractDocument.class).findAll());
        daoHelper.getDao(DetailExportStore.class).deleteAll(daoHelper.getDao(DetailExportStore.class).findAll());
        daoHelper.getDao(ExportStoreForm.class).deleteAll(daoHelper.getDao(ExportStoreForm.class).findAll());

        daoHelper.getDao(DetailImportStore.class).deleteAll(daoHelper.getDao(DetailImportStore.class).findAll());
        daoHelper.getDao(ImportStoreForm.class).deleteAll(daoHelper.getDao(ImportStoreForm.class).findAll());
        daoHelper.getDao(MoveStoreForm.class).deleteAll(daoHelper.getDao(MoveStoreForm.class).findAll());
        daoHelper.getDao(MoveStoreOrder.class).deleteAll(daoHelper.getDao(MoveStoreOrder.class).findAll());
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
        daoHelper.getDao(Goods.class).deleteAll(daoHelper.getDao(Goods.class).findAll());
        daoHelper.getDao(Service.class).deleteAll(daoHelper.getDao(Service.class).findAll());
        daoHelper.getDao(Voucher.class).deleteAll(daoHelper.getDao(Voucher.class).findAll());
        daoHelper.getDao(Manufacturer.class).deleteAll(daoHelper.getDao(Manufacturer.class).findAll());
        daoHelper.getDao(ProductType.class).deleteAll(daoHelper.getDao(ProductType.class).findAll());

        daoHelper.getDao(ProductProperty.class).deleteAll(daoHelper.getDao(ProductProperty.class).findAll());
        daoHelper.getDao(ProductPropertyElement.class).deleteAll(
                daoHelper.getDao(ProductPropertyElement.class).findAll());

        daoHelper.getDao(TransportationType.class).deleteAll(daoHelper.getDao(TransportationType.class).findAll());

        daoHelper.getDao(ContactDebt.class).deleteAll(daoHelper.getDao(ContactDebt.class).findAll());
        daoHelper.getDao(Partner.class).deleteAll(daoHelper.getDao(Partner.class).findAll());
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

        daoHelper.getDao(AudienceCategory.class).deleteAll(daoHelper.getDao(AudienceCategory.class).findAll());
    }

    private static void testInsertedData(DaoHelper daoHelper) {
        DetachedCriteria productDc = daoHelper.getDao(Goods.class).getCriteria();
        productDc.add(Restrictions.eq("code", PRODUCT_GIAY_NAM));
        List<Goods> listGoods = daoHelper.getDao(Goods.class).findByCriteria(productDc);
        Goods goods = listGoods.get(0);
        s_logger.info("List saved items: " + goods.getListItems());
        s_logger.info("Product is saved OK : " + goods.getCode() + ", " + goods.getManufacturer().getCode());

        DetachedCriteria itemDC = daoHelper.getDao(Item.class).getCriteria();
        itemDC.add(Restrictions.eq("product.id", goods.getId()));
        List<Item> listItem = daoHelper.getDao(Item.class).findByCriteria(itemDC);
        Item item = listItem.get(0);
        // s_logger.info("Item is saved OK : " + item.getListUom().get(0).getCode() + ", price=" +
        // item.getBaseSellPrice());

    }

    private static void initDatabase(DaoHelper daoHelper, ServiceProvider serviceProvider) {
        // Init data for config module
        List<UnitOfMeasure> listUom = initUOM(daoHelper);
        List<BankAccount> listBankAccount = initBankAccount(daoHelper);
        List<SCurrency> listSCurrency = initSCurrency(daoHelper);
        List<Organization> listOrganization = initOrganization(daoHelper, listBankAccount, listSCurrency);

        initManufacturer(daoHelper);
        initProductType(daoHelper);

        List<Partner> listContact = initCustomer(daoHelper, listBankAccount);
        List<ContactDebt> listContactDebt = initContactDebt(daoHelper, listContact);
        List<Partner> listSupplier = initSupplier(daoHelper, listContact, listBankAccount);

        List<ProductProperty> properties = initProductProperty(daoHelper);
        List<ProductPropertyElement> elements = initProductPropertyElements(daoHelper, properties);

        List<Goods> goods = initGoods(daoHelper);
        List<Voucher> voucher = initVoucher(daoHelper);
        List<Service> services = initService(daoHelper);

        List<Item> listItem = initItem(daoHelper, listUom, goods);
        List<SPackage> listPackage = initPackage(daoHelper, listItem);
        List<Advantage> listAdvantage = initAdvantage(daoHelper, listItem, listPackage);

        List<Operator> listOperator = initOperator(daoHelper);

        List<Store> listStore = initStore(daoHelper, listOperator);
        List<Article> listGoods = initArticle(daoHelper, listStore, listItem);
        Set<ItemPrice> listItemPrices = initItemPrice(daoHelper, listItem, listContact);
        List<ItemOriginPrice> listItemOriginPrices = initItemOriginPrice(daoHelper, listItem, listSupplier);
        List<SalesContract> listSalesContracts = initSalesContracts(daoHelper, listSupplier, listItem);
        List<Invoice> listInvoice = initInvoice(daoHelper, listItem, listContact);
        // Init data for Store module
        List<ShipPrice> listShipPrice = initShipPrice(daoHelper);
        List<TransportationType> listTransportationType = initTransportationType(daoHelper);
        List<ImportStoreForm> listImportStore = initImportStore(daoHelper, listStore, listItem, listSalesContracts,
                listOperator);
        List<ExportStoreForm> listExportStore = initExportStore(daoHelper, listStore, listItem, listOperator,
                listInvoice, listTransportationType);
        // Init data for finance module
        List<Payment> listPayments = initPayment(daoHelper, serviceProvider, listSalesContracts, listContact,
                listOperator);
        List<MoveStoreForm> listMoveStoreForm = initMoveStore(daoHelper, listStore, listItem, listOperator,
                listTransportationType);
    }

    private static List<MoveStoreForm> initMoveStore(DaoHelper daoHelper, List<Store> listStore, List<Item> listItem,
            List<Operator> listOperator, List<TransportationType> listTransType) {

        MoveStoreOrder order1 = new MoveStoreOrder();
        order1.setCode("001");
        order1.setFromStore(listStore.get(1));
        order1.setDestStore(listStore.get(0));
        order1.setDestDate(DateUtils.addMonths(new Date(), 1));

        MoveStoreForm form = new MoveStoreForm();
        DetailMoveStore detail1 = new DetailMoveStore();
        DetailMoveStore detail2 = new DetailMoveStore();

        Item item = listItem.get(0);
        Product product = item.getProduct();
        UnitOfMeasure uom = daoHelper.getDao(UnitOfMeasure.class).findByCode("Cai");
        TransportationType transType = listTransType.get(0);
        Store fromStore = listStore.get(0);
        Store destStore = listStore.get(1);

        form.setCode("001");
        form.setMoveStoreOrder(order1);
        form.setFromStoreman("Phan Hong Phuc");
        form.setDestStoreman("Pham Cong Bang");
        form.setFromAddress(fromStore.getExportAddress());
        form.setDestAddress(destStore.getImportAddress());
        form.setTransType(listTransType.get(0));
        form.setStaff(listOperator.get(0));
        form.setExportQtyTotal(100);
        form.setImportQtyTotal(100);

        detail1.setMoveForm(form);
        detail1.setProduct(product);
        detail1.setItem(item);
        detail1.setUom(uom);
        detail1.setExportQty(20);
        detail1.setImportQty(20);

        detail2.setMoveForm(form);
        detail2.setProduct(product);
        detail2.setItem(item);
        detail2.setUom(uom);
        detail2.setExportQty(80);
        detail2.setImportQty(80);

        daoHelper.getDao(MoveStoreOrder.class).save(order1);
        daoHelper.getDao(MoveStoreForm.class).save(form);
        daoHelper.getDao(DetailMoveStore.class).saveOrUpdateAll(Arrays.asList(detail1, detail2));

        MoveStoreOrder order2 = new MoveStoreOrder();
        order2.setCode("002");
        order2.setFromStore(listStore.get(0));
        order2.setDestStore(listStore.get(1));
        order2.setDestDate(DateUtils.addMonths(new Date(), 2));
        daoHelper.getDao(MoveStoreOrder.class).save(order2);

        return Arrays.asList(form);
    }

    private static List<ExportStoreForm> initExportStore(DaoHelper daoHelper, List<Store> listStore,
            List<Item> listItem, List<Operator> listOperator, List<Invoice> listInvoice,
            List<TransportationType> listTransType) {
        ExportStoreForm form = new ExportStoreForm();
        DetailExportStore detail1 = new DetailExportStore();
        DetailExportStore detail2 = new DetailExportStore();

        String custCode = listInvoice.get(0).getContact().getCode();
        String custName = listInvoice.get(0).getContact().getName();
        Item item = listItem.get(0);
        Product product = item.getProduct();
        UnitOfMeasure unit = daoHelper.getDao(UnitOfMeasure.class).findByCode("Cai");
        TransportationType transType = listTransType.get(0);

        form.setCode("001");
        form.setStore(listStore.get(0));
        form.setStaff(listOperator.get(0));
        form.setInvoice(listInvoice.get(0));
        form.setCustCode(custCode);
        form.setCustName(custName);
        form.setTransType(transType);
        form.setTransPrice(Money.create("VND", 20000000L));

        detail1.setLineNo(1);
        detail1.setExportForm(form);
        detail1.setProduct(product);
        detail1.setItem(item);
        detail1.setUom(unit);
        detail1.setBaseUom(unit);
        detail1.setReqQuan(100L);
        detail1.setRealQuan(70L);
        detail1.setRemainQuan(30L);

        detail2.setLineNo(2);
        detail2.setExportForm(form);
        detail2.setProduct(product);
        detail2.setItem(item);
        detail2.setUom(unit);
        detail2.setBaseUom(unit);
        detail2.setReqQuan(300L);
        detail2.setRealQuan(200L);
        detail2.setRemainQuan(100L);

        daoHelper.getDao(ExportStoreForm.class).save(form);
        daoHelper.getDao(DetailExportStore.class).saveOrUpdateAll(Arrays.asList(detail1, detail2));
        return Arrays.asList(form);
    }

    private static List<TransportationType> initTransportationType(DaoHelper daoHelper) {
        TransportationType atStoreType = new TransportationType();
        atStoreType.setCode("AtStore");
        atStoreType.setName("Lay tai kho");
        atStoreType.setDescription("Khach tu tuc viec van chuyen, kho chi viec xuat hang.");
        daoHelper.getDao(TransportationType.class).saveOrUpdate(atStoreType);

        TransportationType trainType = new TransportationType();
        trainType.setCode("Train");
        trainType.setName("Xe lua");
        trainType.setDescription("Cong ty giao hang bang xe lua. Moi thu tuc deu do cong ty thuc hien.");
        daoHelper.getDao(TransportationType.class).saveOrUpdate(trainType);

        TransportationType lorryType = new TransportationType();
        lorryType.setCode("Lorry");
        lorryType.setName("Xe tai");
        lorryType.setDescription("Cong ty giao hang bang xe tai. Moi thu tuc deu do cong ty thuc hien.");
        daoHelper.getDao(TransportationType.class).saveOrUpdate(lorryType);

        return Arrays.asList(atStoreType, trainType, lorryType);
    }

    private static List<Service> initService(DaoHelper daoHelper) {
        ProductType productType = new ProductType();
        productType.setCode("ServiceAtHome");
        productType.setName("Sua chua tai nha");
        productType.setProductFamilyType(ProductFamilyType.SERVICE);
        daoHelper.getDao(ProductType.class).saveOrUpdate(productType);

        Service service = new Service();
        service.setCode("TennisMaintain");
        service.setName("Sua chua vot tennis");
        service.setDescription("For B2C");
        service.setType(productType);
        daoHelper.getDao(Service.class).saveOrUpdate(service);
        return Arrays.asList(service);
    }

    private static List<Voucher> initVoucher(DaoHelper daoHelper) {
        ProductType productType = new ProductType();
        productType.setCode("Voucher");
        productType.setName("Phieu mua hang");
        productType.setProductFamilyType(ProductFamilyType.VOUCHER);
        daoHelper.getDao(ProductType.class).saveOrUpdate(productType);

        Voucher voucher = new Voucher();
        voucher.setCode("VOCHER01");
        voucher.setName("Voucher 01");
        voucher.setDescription("For B2B only");
        voucher.setMinAmount(Money.create("VND", 500L));
        voucher.setType(productType);
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
        // pack.setMinTotalItemAmount(12);
        // pack.setMaxTotalItemAmount(12);

        daoHelper.getDao(SPackage.class).saveOrUpdate(pack);

        PackageLine line = new PackageLine();
        line.setIsAllItem(true);
        line.setMinItemAmount(12);
        line.setMaxItemAmount(12);
        line.setOptional(false);
        line.setPackage(pack);
        line.setProduct(listItem.get(0).getProduct());

        PackageLineItemPrice itemPrice = new PackageLineItemPrice();
        itemPrice.setItem(listItem.get(0));
        itemPrice.setAudienceCategory(daoHelper.getDao(AudienceCategory.class).findAll().get(0));
        itemPrice.setSellPrice(Money.create("VND", 9000L));
        line.addItemPrice(itemPrice);
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

    private static List<Payment> initPayment(DaoHelper daoHelper, ServiceProvider serviceProvider,
            List<SalesContract> listSalesContract, List<Partner> listContact, List<Operator> listOperator) {

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

        // String code = serviceProvider.getService(IConfigService.class).generateCode(Payment.class);
        SCurrency defCurrency = daoHelper.getDao(SCurrency.class).findAll().get(0);
        Money money = Money.create(defCurrency.getCode(), 1000000L);
        Payment payment = new Payment();
        payment.setCode("001");
        payment.setPaymentContent(pc1);
        payment.setPaymentDate(new Date());
        payment.setPartner(listContact.get(0));
        payment.setOperator(listOperator.get(0));
        payment.setPaymentMode(PaymentMode.CASH);
        payment.setRate(21000);
        payment.setAmount(money);
        daoHelper.getDao(Payment.class).saveOrUpdate(payment);

        // code = serviceProvider.getService(IConfigService.class).generateCode(Payment.class);
        Payment receipt = new Payment();
        receipt.setCode("002");
        receipt.setPaymentContent(pc3);
        receipt.setPaymentDate(new Date());
        receipt.setPartner(listContact.get(0));
        receipt.setOperator(listOperator.get(0));
        receipt.setPaymentMode(PaymentMode.BANK_TRANSFER);
        receipt.setRate(21000);
        receipt.setAmount(money);
        daoHelper.getDao(Payment.class).saveOrUpdate(receipt);

        // code = serviceProvider.getService(IConfigService.class).generateCode(Payment.class);
        ContractPayment contractPayment = new ContractPayment();
        contractPayment.setCode("003");
        contractPayment.setPaymentContent(pc4);
        contractPayment.setPaymentDate(new Date());
        contractPayment.setPartner(listContact.get(0));
        contractPayment.setOperator(listOperator.get(0));
        contractPayment.setPaymentMode(PaymentMode.BANK_TRANSFER);
        contractPayment.setRate(21000);
        contractPayment.setAmount(money);
        contractPayment.setSalesContract(listSalesContract.get(0));
        daoHelper.getDao(Payment.class).saveOrUpdate(contractPayment);
        return Arrays.asList(payment, receipt, contractPayment);
    }

    /**
     * create 2 invoices "0000001" without contact and "0000002" with contact.
     */
    private static List<Invoice> initInvoice(DaoHelper daoHelper, List<Item> listItem, List<Partner> listContact) {
        Invoice invoice1 = new Invoice();
        invoice1.setContact(listContact.get(0));
        invoice1.setInvoiceNumber("0000001");
        invoice1.setCreatedDate(new Date());
        invoice1.setPaymentStatus(InvoicePaymentStatus.NO_PAYMENT);
        invoice1.setStatus(InvoiceStatus.OPEN);
        invoice1.setType(InvoiceType.SALES);
        invoice1.setMoneyBeforeTax(Money.create("VND", 10000L));
        invoice1.setMoneyOfTax(Money.zero("VND"));
        invoice1.setMoneyAfterTax(Money.create("VND", 10000L));
        daoHelper.getDao(Invoice.class).saveOrUpdate(invoice1);

        DetailInvoice detailInvoice = new DetailInvoice();
        detailInvoice.setInvoice(invoice1);
        detailInvoice.setItem(listItem.get(0));
        detailInvoice.setProduct(listItem.get(0).getProduct());
        detailInvoice.setAmount(2);
        detailInvoice.setPriceBeforeTax(Money.create("VND", 5000L));
        detailInvoice.setPriceAfterTax(Money.create("VND", 5000L));
        detailInvoice.setMoneyBeforeTax(Money.create("VND", 10000L));
        detailInvoice.setMoneyAfterTax(Money.create("VND", 10000L));
        daoHelper.getDao(DetailInvoice.class).saveOrUpdate(detailInvoice);

        // add a package to invoice1
        SPackage pack = daoHelper.getDao(SPackage.class).findAll().get(0);
        for (PackageLine line : pack.getPackageLines()) {
            DetailInvoice detailPack = new DetailInvoice();
            detailPack.setInvoice(invoice1);
            detailPack.setPackageLine(line);
            detailPack.setItem(line.getProduct().getListItems().iterator().next());
            detailPack.setAmount(line.getMinItemAmount());
            detailPack.setPriceBeforeTax(Money.create("VND", 5000L));
            detailPack.setPriceAfterTax(Money.create("VND", 5000L));
            detailPack.setMoneyBeforeTax(Money.create("VND", 10000L));
            detailPack.setMoneyAfterTax(Money.create("VND", 10000L));
            daoHelper.getDao(DetailInvoice.class).saveOrUpdate(detailPack);
        }

        Invoice invoice2 = new Invoice();
        invoice2.setContact(listContact.get(0));
        invoice2.setInvoiceNumber("0000002");
        invoice2.setCreatedDate(new Date());
        invoice2.setPaymentStatus(InvoicePaymentStatus.NO_PAYMENT);
        invoice2.setStatus(InvoiceStatus.OPEN);
        invoice2.setType(InvoiceType.SALES);
        invoice2.setMoneyBeforeTax(Money.create("VND", 22000L));
        invoice2.setMoneyOfTax(Money.zero("VND"));
        invoice2.setMoneyAfterTax(Money.create("VND", 22000L));
        daoHelper.getDao(Invoice.class).saveOrUpdate(invoice2);

        DetailInvoice detailInvoice2 = new DetailInvoice();
        detailInvoice2.setInvoice(invoice2);
        detailInvoice2.setItem(listItem.get(0));
        detailInvoice2.setAmount(2);
        detailInvoice2.setPriceBeforeTax(Money.create("VND", 6000L));
        detailInvoice2.setPriceAfterTax(Money.create("VND", 6000L));
        detailInvoice2.setMoneyBeforeTax(Money.create("VND", 12000L));
        detailInvoice2.setMoneyAfterTax(Money.create("VND", 12000L));
        daoHelper.getDao(DetailInvoice.class).saveOrUpdate(detailInvoice2);

        DetailInvoice detailInvoice3 = new DetailInvoice();
        detailInvoice3.setInvoice(invoice2);
        detailInvoice3.setItem(listItem.get(0));
        detailInvoice3.setAmount(2);
        detailInvoice3.setPriceBeforeTax(Money.create("VND", 5000L));
        detailInvoice3.setPriceAfterTax(Money.create("VND", 5000L));
        detailInvoice3.setMoneyBeforeTax(Money.create("VND", 10000L));
        detailInvoice3.setMoneyAfterTax(Money.create("VND", 10000L));
        daoHelper.getDao(DetailInvoice.class).saveOrUpdate(detailInvoice3);
        return Arrays.asList(invoice1, invoice2);
    }

    private static List<ShipPrice> initShipPrice(DaoHelper daoHelper) {
        ShipPriceType shipPriceType1 = new ShipPriceType();
        shipPriceType1.setCode("CVC01");
        shipPriceType1.setName("20'");

        ShipPriceType shipPriceType2 = new ShipPriceType();
        shipPriceType2.setCode("CVC02");
        shipPriceType2.setName("30'");

        ShipPriceType shipPriceType3 = new ShipPriceType();
        shipPriceType3.setCode("CVC03");
        shipPriceType3.setName("Hang le");

        daoHelper.getDao(ShipPriceType.class).saveOrUpdateAll(
                Arrays.asList(shipPriceType1, shipPriceType2, shipPriceType3));

        ShipPrice shipPrice1 = new ShipPrice();
        shipPrice1.setShipPriceType(shipPriceType1);
        shipPrice1.setPrice(Money.create("VND", 15000L));

        ShipPrice shipPrice2 = new ShipPrice();
        shipPrice2.setShipPriceType(shipPriceType2);
        shipPrice2.setPrice(Money.create("VND", 30000L));

        ShipPrice shipPrice3 = new ShipPrice();
        shipPrice3.setShipPriceType(shipPriceType3);
        shipPrice3.setPrice(Money.create("VND", 5000L));

        List shipPriceList = Arrays.asList(shipPrice1, shipPrice2, shipPrice3);
        daoHelper.getDao(ShipPrice.class).saveOrUpdateAll(shipPriceList);

        return shipPriceList;
    }

    private static List<ImportStoreForm> initImportStore(DaoHelper daoHelper, List<Store> listStore,
            List<Item> listItem, List<SalesContract> listSalesContact, List<Operator> listOperator) {
        ImportStoreForm form1 = new ImportStoreForm();
        form1.setCode("00000001");// TODO: get according to common rule
        form1.setStore(listStore.get(0));
        form1.setSalesContract(listSalesContact.get(0));
        form1.setSupplierName("KHACHHANG01");
        form1.setReceiver(listOperator.get(0));
        form1.setSender("Nguyen Van A");

        Item item = listItem.get(0);
        DetailImportStore detail1 = new DetailImportStore();
        detail1.setImportStoreForm(form1);
        detail1.setProduct(item.getProduct());
        detail1.setItem(item);
        detail1.setQuantity(20);
        UnitOfMeasure unit = daoHelper.getDao(UnitOfMeasure.class).findByCode("Cai");
        detail1.setUom(unit);
        detail1.setBaseUom(unit);
        detail1.setPriceUnit(item.getOriginPrice());
        detail1.setPriceSubtotal(item.getOriginPrice().multiply(detail1.getQuantity()));
        form1.getDetailImportStores().add(detail1);
        daoHelper.getDao(ImportStoreForm.class).save(form1);
        return Arrays.asList(form1);
    }

    private static Set<ItemPrice> initItemPrice(DaoHelper daoHelper, List<Item> listItem, List<Partner> listContact) {
        ItemPrice itemPrice = new ItemPrice();
        List<AudienceCategory> listAudienceCate = daoHelper.getDao(AudienceCategory.class).findAll();
        itemPrice.setAudienceCategory(listAudienceCate.get(0));
        itemPrice.setSellPrice(Money.create("VND", 100L));
        itemPrice.setItem(listItem.get(0));
        daoHelper.getDao(ItemPrice.class).saveOrUpdate(itemPrice);
        listItem.get(0).getListItemPrices().add(itemPrice);
        daoHelper.getDao(Item.class).saveOrUpdate(listItem.get(0));
        return listItem.get(0).getListItemPrices();
    }

    private static List<ItemOriginPrice> initItemOriginPrice(DaoHelper daoHelper, List<Item> listItem,
            List<Partner> listSupplier) {
        ItemOriginPrice originPrice = new ItemOriginPrice();
        originPrice.setItem(listItem.get(0));
        originPrice.setOriginalPrice(90.0);
        originPrice.setCurrency("VND");
        originPrice.setSupplier(listSupplier.get(0));
        daoHelper.getDao(ItemOriginPrice.class).saveOrUpdate(originPrice);
        return Arrays.asList(originPrice);
    }

    private static List<SalesContract> initSalesContracts(DaoHelper daoHelper, List<Partner> listSuppliers,
            List<Item> listItem) {
        SalesConfirm salesConfirm = new SalesConfirm();
        salesConfirm.setCode("CONFIRM_NIKE_000001");
        salesConfirm.setCreatedDate(new Date());
        salesConfirm.setSupplier(listSuppliers.get(0));
        salesConfirm.setExpectedQtySC(4);
        salesConfirm.setDescription("Ther are 4 sales contracts required."
                + " Delivery can be available in the end of March, April, May and June");
        daoHelper.getDao(SalesConfirm.class).save(salesConfirm);

        ContractDocument document = new ContractDocument();
        document.setCode("OCOBOARDBILLOLANDING");
        document.setName("Original Clean on Board Bill of Lading");
        document.setDocumentPlace(DocumentPlaceEnum.IN_FOREIGN);
        daoHelper.getDao(ContractDocument.class).saveOrUpdate(document);

        SalesContract salesContract = new SalesContract();
        salesContract.setCode("CO123456");
        salesContract.setSupplier(listSuppliers.get(0));
        salesContract.setSalesConfirm(salesConfirm);
        salesContract.setDateContract(new Date());
        salesContract.setMoneyBeforeTax(Money.create("USD", 1000L));
        salesContract.setMoneyOfTax(Money.create("USD", 10L));
        salesContract.setMoneyAfterTax(Money.create("USD", 1010L));

        DetailSalesContract detail = new DetailSalesContract();
        detail.setProduct(listItem.get(0).getProduct());
        detail.setQuantity(5);
        detail.setUnitPrice(Money.create("USD", 1010L));
        detail.setTotalPrice(Money.create("USD", 5050L));
        salesContract.addDetailSalesContract(detail);
        salesContract.getListDocuments().add(document);

        daoHelper.getDao(SalesContract.class).saveOrUpdate(salesContract);
        return Arrays.asList(salesContract);
    }

    private static List<Article> initArticle(DaoHelper daoHelper, List<Store> listStore, List<Item> listItem) {
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

        BankAccount USDBankAcct = new BankAccount();
        USDBankAcct.setBank(bank);
        USDBankAcct.setAccountNumber("1602 2010 19836");

        BankAccount VNDBankAcct = new BankAccount();
        VNDBankAcct.setBank(bank);
        VNDBankAcct.setAccountNumber("1602 2010 19820");
        List<BankAccount> result = Arrays.asList(VNDBankAcct, VNDBankAcct);
        daoHelper.getDao(BankAccount.class).saveOrUpdateAll(result);
        return result;
    }

    private static List<SCurrency> initSCurrency(DaoHelper daoHelper) {
        SCurrency VNDCurrency = new SCurrency();
        VNDCurrency.setCode("VND");
        VNDCurrency.setName("VND");
        VNDCurrency.setSymbol("d");

        SCurrency USDCurrency = new SCurrency();
        USDCurrency.setCode("USD");
        USDCurrency.setName("USD");
        USDCurrency.setSymbol("$");

        List<SCurrency> result = Arrays.asList(VNDCurrency, USDCurrency);
        daoHelper.getDao(SCurrency.class).saveOrUpdateAll(result);

        ExchangeRate USDExRate = new ExchangeRate();
        USDExRate.setCurrency(USDCurrency);
        USDExRate.setRate(21000D);
        daoHelper.getDao(ExchangeRate.class).save(USDExRate);

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
        org1.setIsDefault(true);

        Organization org2 = new Organization();
        org2.setInstitution(ins);
        org2.setCode("HANOI_BRANCH");
        org2.setName("Chi nhanh Ha Noi");
        org2.setAddress(COMPANY_ADDRESS);
        // Bank information
        org2.setBeneficeName("THU SPORTS");
        if (listBankAccount != null && listBankAccount.size() == 2) {
            org2.setUsdBankAcct(listBankAccount.get(0));
            org2.setVndBankAcct(listBankAccount.get(1));
        }
        // General Information
        if (listSCurrency != null) {
            SCurrency defCurrency = listSCurrency.get(1);
            org2.setDefCurrency(defCurrency);
        }
        org2.setDefPaymentMethod(PaymentMode.CASH);
        org2.setEnableChangeInvDate(0);
        // rule of code generation
        org2.setSellOnCredit(2); // ko cho phep ban am
        List<Organization> result = Arrays.asList(org1, org2);
        daoHelper.getDao(Organization.class).saveOrUpdateAll(result);
        daoHelper.getDao(Institution.class).saveOrUpdate(ins);
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

        institution.setOrderInvCodeRule("HDDH");
        institution.setPurInvCodeRule("HDMH");
        institution.setSalesInvCodeRule("NKHMTL");
        institution.setPurInvCodeRule("MHNCC");
        institution.setPurRefundInvCodeRule("XTNCC");
        institution.setSponContractCodeRule("HDTT");
        institution.setMovementInvCodeRule("CK");
        institution.setExportInvCodeRule("XK");
        institution.setImportInvCodeRule("NK");
        institution.setPaymentBillCodeRule("PC");
        institution.setPromotionCodeRule("PT");
        institution.setSponContractCodeRule("KM");
        daoHelper.getDao(Institution.class).save(institution);
        return institution;
    }

    private static List<Store> initStore(DaoHelper daoHelper, List<Operator> listOperator) {
        Store store1 = new Store();
        store1.setCode("K01");
        store1.setAddress(STORE_Q1_ADDRESS);
        store1.setStoredAddress(STORE_Q1_ADDRESS);
        store1.setExportAddress(STORE_Q1_ADDRESS);
        store1.setImportAddress(STORE_Q1_ADDRESS);
        store1.setName(STORE_Q1);
        store1.setPhone("(848) 38220541");
        store1.setFax("84 - 8 - 38220542");
        store1.setManager(listOperator.get(0));

        Store store2 = new Store();
        store2.setCode("K02");
        store2.setAddress(STORE_Q9_ADDRESS);
        store2.setStoredAddress(STORE_Q9_ADDRESS);
        store2.setExportAddress(STORE_Q9_ADDRESS);
        store2.setImportAddress(STORE_Q9_ADDRESS);
        store2.setName(STORE_Q9);
        store2.setPhone("08.62809933");
        store2.setFax("08.37312964");
        store2.setManager(listOperator.get(0));

        daoHelper.getDao(Store.class).saveOrUpdateAll(Arrays.asList(store1, store2));
        return Arrays.asList(store1, store2);
    }

    private static List<Partner> initSupplier(DaoHelper daoHelper, List<Partner> listContact,
            List<BankAccount> listBankAccount) {
        Partner supplier = new Partner();
        supplier.setCode("NIKE");
        supplier.setName("Nike company");
        // supplier.setSex(true); // TODO: why supplier has sex? I think Sex should only be applied on individual
        // supplier.setPhoneNumber("0909825783");
        // supplier.setMainContact(listContact.get(0));

        // supplier.setBankAccount(listBankAccount.get(0));
        PartnerProfile profile = new SupplierProfile();
        profile.setPartner(supplier);
        profile.setType(PartnerProfileTypeEnum.SUPPLIER);
        supplier.addPartnerProfile(profile);

        supplier.getMainIndividual().setFirstName("Tschumi");
        supplier.getMainIndividual().setLastName("Jean Paul");
        supplier.getMainIndividual().setFullName("Jean Paul Tschumi");
        supplier.getMainIndividual().setPosition("Genernal director");
        supplier.getMainAddressLink().getAddress().setName("Nike company place");
        supplier.getMainAddressLink().getAddress().setAddress("1 Harvard Rue");
        supplier.getMainAddressLink().getAddress().setCity("London");
        supplier.getMainAddressLink().getAddress().setDistrict("1");
        daoHelper.getDao(Partner.class).saveOrUpdate(supplier);
        return Arrays.asList(supplier);
    }

    private static List<Partner> initCustomer(DaoHelper daoHelper, List<BankAccount> listBankAccounts) {
        AudienceCategory aucate = new AudienceCategory();
        aucate.setCode("Vip1");
        aucate.setName("Khach hang than thiet");
        daoHelper.getDao(AudienceCategory.class).saveOrUpdate(aucate);

        PartnerCategory contactType = new PartnerCategory();
        contactType.setCode("B2B");
        contactType.setName("B2B");
        // contactType.setContactFamilyType(ContactFamilyType.CUSTOMER);
        // contactType.setDescription("Ban si - Bussiness to bussiness");
        daoHelper.getDao(PartnerCategory.class).saveOrUpdate(contactType);

        Partner contact = new Partner();
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
        PartnerProfile profile = new CustomerProfile();
        profile.setPartner(contact);
        profile.setType(PartnerProfileTypeEnum.CUSTOMER);
        ((CustomerProfile) profile).getAudienceCates().add(aucate);

        contact.addPartnerProfile(profile);
        Individual individual = contact.getMainIndividual();
        individual.setFirstName("Van Tam");
        individual.setLastName("Nguyen");
        individual.setFullName("Nguyen Van Tam");
        individual.setTitle(IndividualTitleEnum.MR);
        individual.setRole(IndividualRoleEnum.MAIN);
        individual.setPosition("Ca nhan");
        individual.setPartner(contact);

        Address address = contact.getMainAddressLink().getAddress();
        address.setName("Cua hang giay so 1");
        address.setAddress("123 Bui vien");
        address.setDistrict("1");
        address.setCity("Ho Chi Minh");
        address.setPostalCode("70000");
        address.setFixPhone("0909000000");
        address.setRemark("Mo cua 8h-21h");
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
        category.setCode(UomCategory.WEIGHT_UOM_CATE);
        category.setName("Can nang");
        daoHelper.getDao(UomCategory.class).saveOrUpdate(category);

        UnitOfMeasure kg = new UnitOfMeasure();
        kg.setCode(UOM_KG);
        // pair.setName(""); // TODO: error UTF-8 here. Please careful for production
        kg.setName("Kilogam");
        kg.setUomCategory(category);
        kg.setIsBaseMeasure(true);
        kg.setChangeRate(1F);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(kg);

        UnitOfMeasure gam = new UnitOfMeasure();
        gam.setCode("G");
        gam.setName("gam");
        gam.setUomCategory(category);
        gam.setIsBaseMeasure(false);
        gam.setChangeRate(0.001F);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(gam);

        UomCategory category2 = new UomCategory();
        category2.setCode(UomCategory.UNIT_UOM_CATE);
        category2.setName("Don vi dem");
        daoHelper.getDao(UomCategory.class).saveOrUpdate(category2);

        UnitOfMeasure cai = new UnitOfMeasure();
        cai.setCode("Cai");
        // pair.setName(""); // TODO: error UTF-8 here. Please careful for production
        cai.setName("Cai");
        cai.setUomCategory(category2);
        cai.setIsBaseMeasure(true);
        cai.setChangeRate(1F);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(cai);
        return Arrays.asList(cai, gam, cai);
    }

    private static List<Item> initItem(DaoHelper daoHelper, List<UnitOfMeasure> listUom, List<Goods> goods) {
        Item item = new Item();
        item.setProduct(goods.get(0));
        item.setBaseSellPrice(Money.create("VND", 10000L));
        item.setOriginPrice(Money.create("VND", 9000L));
        item.setListUom(Arrays.asList(listUom.get(0)));
        item.setSumUomName("size 39");

        ItemPropertyValue propertyValue = new ItemPropertyValue();
        List<ProductProperty> properties = new ArrayList<>(goods.get(0).getProperties());
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

    private static List<Goods> initGoods(DaoHelper daoHelper) {
        Goods product = new Goods();
        product.setCode(PRODUCT_GIAY_NAM);
        product.setName("Giay nam");
        product.setDescription("Giay nam choi tennis");
        product.setVatRate(5.5);
        product.setModel("Model100");
        product.setBaseSellPrice(Money.create("VND", 10000L));
        product.setOriginPrice(Money.create("VND", 9000L));

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

    // protected static void init100Product(DaoHelper daoHelper) {
    // DetachedCriteria uomDC = daoHelper.getDao(UnitOfMeasure.class).getCriteria();
    // uomDC.add(Restrictions.eq("code", UOM_KG));
    //
    // ProductType type = daoHelper.getDao(ProductType.class).findAll().get(0);
    //
    // Manufacturer manufacturer = daoHelper.getDao(Manufacturer.class).findAll().get(0);
    // UnitOfMeasure unitOfMeasure = daoHelper.getDao(UnitOfMeasure.class).findByCriteria(uomDC).get(0);
    //
    // for (int i = 0; i < 200; i++) {
    // Goods goods = new Goods();
    // goods.setCode(PRODUCT_GIAY_NAM + i);
    // goods.setName("Giay nam " + i);
    // goods.setDescription("Giay nam choi tennis");
    // goods.setModel("Model100" + i);
    //
    // goods.setMainUom(unitOfMeasure);
    // goods.setType(type);
    // goods.setManufacturer(manufacturer);
    // daoHelper.getDao(Product.class).saveOrUpdate(goods);
    // }
    // }

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
