package com.s3s.ssm.test;

import java.util.Arrays;
import java.util.Date;
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

import com.s3s.ssm.entity.contact.Bank;
import com.s3s.ssm.entity.contact.BankAccount;
import com.s3s.ssm.entity.contact.Contact;
import com.s3s.ssm.entity.contact.ContactFamilyType;
import com.s3s.ssm.entity.contact.ContactShop;
import com.s3s.ssm.entity.contact.ContactType;
import com.s3s.ssm.entity.finance.Payment;
import com.s3s.ssm.entity.finance.PaymentContentType;
import com.s3s.ssm.entity.finance.PaymentMeanEnum;
import com.s3s.ssm.entity.finance.PaymentStatus;
import com.s3s.ssm.entity.finance.PaymentType;
import com.s3s.ssm.entity.param.Article;
import com.s3s.ssm.entity.param.CurrencyEnum;
import com.s3s.ssm.entity.param.Item;
import com.s3s.ssm.entity.param.Manufacturer;
import com.s3s.ssm.entity.param.Operator;
import com.s3s.ssm.entity.param.Product;
import com.s3s.ssm.entity.param.ProductFamilyType;
import com.s3s.ssm.entity.param.ProductType;
import com.s3s.ssm.entity.param.Store;
import com.s3s.ssm.entity.param.Supplier;
import com.s3s.ssm.entity.param.UnitOfMeasure;
import com.s3s.ssm.entity.param.UomCategory;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.DetailSalesContract;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.sales.InvoicePaymentStatus;
import com.s3s.ssm.entity.sales.InvoiceStatus;
import com.s3s.ssm.entity.sales.InvoiceType;
import com.s3s.ssm.entity.sales.ItemOriginPrice;
import com.s3s.ssm.entity.sales.ItemPrice;
import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.DetailImportProduct;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.entity.store.ImportProductForm;
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
    private static final String UOM_PAIR = "PAIR";
    private static final String ADDRESS = "64/7 Ngo Chi Quoc";

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
        daoHelper.getDao(Payment.class).deleteAll(daoHelper.getDao(Payment.class).findAll());
        daoHelper.getDao(PaymentType.class).deleteAll(daoHelper.getDao(PaymentType.class).findAll());

        daoHelper.getDao(DetailSalesContract.class).deleteAll(daoHelper.getDao(DetailSalesContract.class).findAll());
        daoHelper.getDao(SalesContract.class).deleteAll(daoHelper.getDao(SalesContract.class).findAll());

        daoHelper.getDao(DetailExportStore.class).deleteAll(daoHelper.getDao(DetailExportStore.class).findAll());
        daoHelper.getDao(ExportStoreForm.class).deleteAll(daoHelper.getDao(ExportStoreForm.class).findAll());

        daoHelper.getDao(DetailImportProduct.class).deleteAll(daoHelper.getDao(DetailImportProduct.class).findAll());
        daoHelper.getDao(ImportProductForm.class).deleteAll(daoHelper.getDao(ImportProductForm.class).findAll());

        daoHelper.getDao(DetailInvoice.class).deleteAll(daoHelper.getDao(DetailInvoice.class).findAll());
        daoHelper.getDao(Invoice.class).deleteAll(daoHelper.getDao(Invoice.class).findAll());

        daoHelper.getDao(ItemPrice.class).deleteAll(daoHelper.getDao(ItemPrice.class).findAll());
        daoHelper.getDao(ItemOriginPrice.class).deleteAll(daoHelper.getDao(ItemOriginPrice.class).findAll());
        daoHelper.getDao(DetailSalesContract.class).deleteAll(daoHelper.getDao(DetailSalesContract.class).findAll());
        daoHelper.getDao(SalesContract.class).deleteAll(daoHelper.getDao(SalesContract.class).findAll());

        daoHelper.getDao(Article.class).deleteAll(daoHelper.getDao(Article.class).findAll());
        daoHelper.getDao(Item.class).deleteAll(daoHelper.getDao(Item.class).findAll());

        daoHelper.getDao(Product.class).deleteAll(daoHelper.getDao(Product.class).findAll());
        daoHelper.getDao(Manufacturer.class).deleteAll(daoHelper.getDao(Manufacturer.class).findAll());
        daoHelper.getDao(ProductType.class).deleteAll(daoHelper.getDao(ProductType.class).findAll());

        daoHelper.getDao(UnitOfMeasure.class).deleteAll(daoHelper.getDao(UnitOfMeasure.class).findAll());
        daoHelper.getDao(UomCategory.class).deleteAll(daoHelper.getDao(UomCategory.class).findAll());

        daoHelper.getDao(Supplier.class).deleteAll(daoHelper.getDao(Supplier.class).findAll());
        daoHelper.getDao(Contact.class).deleteAll(daoHelper.getDao(Contact.class).findAll());
        daoHelper.getDao(ContactType.class).deleteAll(daoHelper.getDao(ContactType.class).findAll());
        daoHelper.getDao(BankAccount.class).deleteAll(daoHelper.getDao(BankAccount.class).findAll());
        daoHelper.getDao(Bank.class).deleteAll(daoHelper.getDao(Bank.class).findAll());
        daoHelper.getDao(Store.class).deleteAll(daoHelper.getDao(Store.class).findAll());
        daoHelper.getDao(Operator.class).deleteAll(daoHelper.getDao(Operator.class).findAll());
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
        s_logger.info("Item is saved OK : " + item.getListUom().get(1).getCode() + ", price=" + item.getBaseSellPrice());

    }

    private static void initDatabase(DaoHelper daoHelper) {
        List<UnitOfMeasure> listUom = initUOM(daoHelper);

        initManufacturer(daoHelper);
        initProductType(daoHelper);
        List<Product> products = initProduct(daoHelper);

        List<Item> listItem = initItem(daoHelper, listUom, products);

        List<BankAccount> listBankAccount = initBankAccount(daoHelper);
        List<Operator> listOperator = initOperator(daoHelper);
        List<Contact> listContact = initContact(daoHelper, listBankAccount);
        List<Supplier> listSupplier = initSupplier(daoHelper, listContact, listBankAccount);
        List<Store> listStore = initStore(daoHelper, listOperator);
        List<Article> listGoods = initGood(daoHelper, listStore, listItem);
        Set<ItemPrice> listItemPrices = initItemPrice(daoHelper, listItem, listContact);
        List<ItemOriginPrice> listItemOriginPrices = initItemOriginPrice(daoHelper, listItem, listSupplier);
        List<SalesContract> listSalesContracts = initSalesContracts(daoHelper, listSupplier, listItem);
        List<Invoice> listInvoice = initInvoice(daoHelper, listItem, listContact);
        List<Payment> listPayments = initPayment(daoHelper, listInvoice, listContact);
    }

    private static List<Payment> initPayment(DaoHelper daoHelper, List<Invoice> listInvoice, List<Contact> listContact) {
        PaymentType paymentType = new PaymentType();
        paymentType.setCode("SELL_PRODUCT");
        paymentType.setName("Sell product");
        paymentType.setContentType(PaymentContentType.RECEIVE_SELL);
        paymentType.setIsReceived(true);
        daoHelper.getDao(PaymentType.class).saveOrUpdate(paymentType);

        Payment payment = new Payment();
        payment.setPaymentType(paymentType);
        payment.setPaymentMean(PaymentMeanEnum.CASH);
        payment.setContact(listContact.get(0));
        payment.setInvoice(listInvoice.get(0));
        payment.setMoney(10000.0);
        payment.setStatus(PaymentStatus.CLOSED);
        daoHelper.getDao(Payment.class).saveOrUpdate(payment);
        return Arrays.asList(payment);
    }

    /**
     * create 2 invoices "0000001" without contact and "0000002" with contact.
     */
    private static List<Invoice> initInvoice(DaoHelper daoHelper, List<Item> listItem, List<Contact> listContact) {
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

    private static Set<ItemPrice> initItemPrice(DaoHelper daoHelper, List<Item> listItem, List<Contact> listContact) {
        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setContactType(listContact.get(0).getContactType());
        itemPrice.setSellPrice(100.0);
        itemPrice.setCurrency(CurrencyEnum.VND);
        // itemPrice.setItem(listItem.get(0));
        // daoHelper.getDao(ItemPrice.class).saveOrUpdate(itemPrice);
        listItem.get(0).addItemPrice(itemPrice);
        daoHelper.getDao(Item.class).saveOrUpdate(listItem.get(0));
        return listItem.get(0).getListItemPrices();
    }

    private static List<ItemOriginPrice> initItemOriginPrice(DaoHelper daoHelper, List<Item> listItem,
            List<Supplier> listSupplier) {
        ItemOriginPrice originPrice = new ItemOriginPrice();
        originPrice.setItem(listItem.get(0));
        originPrice.setOriginalPrice(90.0);
        originPrice.setCurrency(CurrencyEnum.VND);
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
        salesContract.setCurrency(CurrencyEnum.VND);

        DetailSalesContract detail = new DetailSalesContract();
        detail.setItem(listItem.get(0));
        detail.setAmount(5L);
        detail.setUnitPrice(100.0);
        detail.setCurrency(CurrencyEnum.VND);
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
        bank.setCode("ACB");
        bank.setName("Ngan hang thuong mai co phan A Chau");
        daoHelper.getDao(Bank.class).save(bank);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setBank(bank);
        bankAccount.setAccountNumber("123456");

        BankAccount bankAccount2 = new BankAccount();
        bankAccount2.setBank(bank);
        bankAccount2.setAccountNumber("111222");
        List<BankAccount> result = Arrays.asList(bankAccount, bankAccount2);
        daoHelper.getDao(BankAccount.class).saveOrUpdateAll(result);
        return result;
    }

    private static List<Store> initStore(DaoHelper daoHelper, List<Operator> listOperator) {
        Store store = new Store();
        store.setCode("K05");
        store.setAddress(ADDRESS);
        store.setStoredAddress(ADDRESS);
        store.setExportAddress(ADDRESS);
        store.setImportAddress(ADDRESS);
        store.setName("Kho 05");
        store.setManager(listOperator.get(0));
        daoHelper.getDao(Store.class).saveOrUpdate(store);
        return Arrays.asList(store);
    }

    private static List<Supplier> initSupplier(DaoHelper daoHelper, List<Contact> listContact,
            List<BankAccount> listBankAccount) {
        Supplier supplier = new Supplier();
        supplier.setCode("NIKE");
        supplier.setName("Nike company");
        supplier.setPhoneNumber("0909825783");
        supplier.setMainContact(listContact.get(0));

        supplier.setBankAccount(listBankAccount.get(0));
        daoHelper.getDao(Supplier.class).saveOrUpdate(supplier);
        return Arrays.asList(supplier);
    }

    private static List<Contact> initContact(DaoHelper daoHelper, List<BankAccount> listBankAccounts) {
        ContactType contactType = new ContactType();
        contactType.setCode("B2B");
        contactType.setContactFamilyType(ContactFamilyType.CUSTOMER);
        contactType.setDescription("Ban si - Bussiness to bussiness");
        daoHelper.getDao(ContactType.class).saveOrUpdate(contactType);

        Contact contact = new Contact();
        contact.setCode("CONTYBANLE123");
        contact.setFullName("Cong ty ban le 123");
        contact.setContactType(contactType);
        contact.setEmail("banle123@solution3s.com");

        contact.setBankAccount(listBankAccounts.get(1));
        contact.setMaximumDayDebt(100L);

        // daoHelper.getDao(Contact.class).saveOrUpdate(contact);

        ContactShop shop = new ContactShop();
        shop.setCode("GIAY_SO_1");
        shop.setName("Cua hang giay so 1");
        // shop.setContact(contact);
        shop.setAddress("123 Bui vien");
        shop.setFixPhone("0909000000");
        shop.setRemark("Mo cua 8h-21h");
        // daoHelper.getDao(ContactShop.class).saveOrUpdate(shop);

        contact.addShop(shop);
        daoHelper.getDao(Contact.class).saveOrUpdate(contact);
        return Arrays.asList(contact);
    }

    private static List<Operator> initOperator(DaoHelper daoHelper) {
        Operator operator = new Operator();
        operator.setLogin("testOperator");
        operator.setPassword("123456encrypted");
        operator.setEmail("test@solution3s.com");
        operator.setFullName("Test Operator");
        operator.setActive(true);
        daoHelper.getDao(Operator.class).saveOrUpdate(operator);
        return Arrays.asList(operator);
    }

    private static List<UnitOfMeasure> initUOM(DaoHelper daoHelper) {
        UomCategory category = new UomCategory();
        category.setCode("SIZE");
        category.setName("Size");
        daoHelper.getDao(UomCategory.class).saveOrUpdate(category);

        UnitOfMeasure pair = new UnitOfMeasure();
        pair.setCode(UOM_PAIR);
        // pair.setName(""); // TODO: error UTF-8 here. Please careful for production
        pair.setName("Doi");
        pair.setUomCategory(category);
        pair.setIsBaseMeasure(false);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(pair);

        UnitOfMeasure size39 = new UnitOfMeasure();
        size39.setCode("39");
        size39.setName("39");
        size39.setUomCategory(category);
        size39.setIsBaseMeasure(false);
        daoHelper.getDao(UnitOfMeasure.class).saveOrUpdate(size39);
        return Arrays.asList(pair, size39);
    }

    private static List<Item> initItem(DaoHelper daoHelper, List<UnitOfMeasure> listUom, List<Product> products) {
        Item item = new Item();
        item.setProduct(products.get(0));
        item.setBaseSellPrice(10000.0);
        item.setListUom(listUom);
        item.setSumUomName("size 39");
        item.setCurrency(CurrencyEnum.VND);
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
        uomDC.add(Restrictions.eq("code", UOM_PAIR));
        product.setMainUom(daoHelper.getDao(UnitOfMeasure.class).findByCriteria(uomDC).get(0));
        ProductType type = daoHelper.getDao(ProductType.class).findAll().get(0);

        Manufacturer manufacturer = daoHelper.getDao(Manufacturer.class).findAll().get(0);

        product.setType(type);
        product.setManufacturer(manufacturer);
        daoHelper.getDao(Product.class).saveOrUpdate(product);

        return Arrays.asList(product);
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
