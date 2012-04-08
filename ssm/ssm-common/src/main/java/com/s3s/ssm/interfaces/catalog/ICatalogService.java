package com.s3s.ssm.interfaces.catalog;

import java.util.List;

import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;

public interface ICatalogService {
    List<Product> getListProducts();

    List<Item> getAllItem();
}
