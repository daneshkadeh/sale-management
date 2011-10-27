package com.s3s.ssm.test;

import java.util.Arrays;
import java.util.List;

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
import com.s3s.ssm.entity.contact.ContactType;
import com.s3s.ssm.entity.param.Good;
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
        daoHelper.getDao(Good.class).deleteAll(daoHelper.getDao(Good.class).findAll());
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
        List<Good> listGoods = initGood(daoHelper, listStore, listItem);
    }

    private static List<Good> initGood(DaoHelper daoHelper, List<Store> listStore, List<Item> listItem) {
        Good good = new Good();
        good.setBarcode("12345678910");
        good.setItem(listItem.get(0));
        good.setStore(listStore.get(0));
        good.setFirstMaintainDate(DateTime.now().plusMonths(6).toDate());
        daoHelper.getDao(Good.class).saveOrUpdate(good);
        return Arrays.asList(good);
    }

    private static List<BankAccount> initBankAccount(DaoHelper daoHelper) {
        Bank bank = new Bank();
        bank.setCode("ACB");
        bank.setBankName("Ngan hang thuong mai co phan A Chau");
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
        item.setCurrency("VND");
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