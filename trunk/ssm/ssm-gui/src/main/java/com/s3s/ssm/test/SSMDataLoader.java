package com.s3s.ssm.test;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.s3s.ssm.entity.param.Item;
import com.s3s.ssm.entity.param.Manufacturer;
import com.s3s.ssm.entity.param.Product;
import com.s3s.ssm.entity.param.ProductFamilyType;
import com.s3s.ssm.entity.param.ProductType;
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
        daoHelper.getDao(Item.class).deleteAll(daoHelper.getDao(Item.class).findAll());

        daoHelper.getDao(Product.class).deleteAll(daoHelper.getDao(Product.class).findAll());
        daoHelper.getDao(Manufacturer.class).deleteAll(daoHelper.getDao(Manufacturer.class).findAll());
        daoHelper.getDao(ProductType.class).deleteAll(daoHelper.getDao(ProductType.class).findAll());

        daoHelper.getDao(UnitOfMeasure.class).deleteAll(daoHelper.getDao(UnitOfMeasure.class).findAll());
        daoHelper.getDao(UomCategory.class).deleteAll(daoHelper.getDao(UomCategory.class).findAll());
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

        initItem(daoHelper, listUom, products);
    }

    private static List<UnitOfMeasure> initUOM(DaoHelper daoHelper) {
        UomCategory category = new UomCategory();
        category.setCode("SIZE");
        category.setName("Size");
        daoHelper.getDao(UomCategory.class).saveOrUpdate(category);

        UnitOfMeasure pair = new UnitOfMeasure();
        pair.setCode(UOM_PAIR);
        // pair.setName(""); // error UTF-8 here. Please careful for production
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
