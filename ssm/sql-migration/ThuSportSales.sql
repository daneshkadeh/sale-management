CREATE TABLE `s_product` (
  `id` int(11) NOT NULL auto_increment,
  `producttype_id`  int(11),
  `product_code` varchar(32) collate utf8_bin,
  `product_name` varchar(128) collate utf8_bin,
  `manufacturer_id` int(11),
  `model` varchar(32) collate utf8_bin,
  `description` varchar(128) collate utf8_bin,
  `uploadfile_id` int(11),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_product_code` (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

 -- collected from list linked uom
CREATE TABLE `s_item` (
  `id` int(11) NOT NULL auto_increment,
  `product_id` int(11) NOT NULL,
  `sum_uom_name` varchar(128) collate utf8_bin,
  `base_sell_price` double NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_item_product` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

-- Good is a idenfied item (eg. 1 t-shirt is a good)
CREATE TABLE `s_good` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `barcode` varchar(32) NOT NULL,
  `first_maintain_date` date NOT NULL,
  `second_maintain_date` date,
  `store_id` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_good_item` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `at_item_uom` (
  `item_id` int(11) NOT NULL,
  `uom_id` int(11) NOT NULL,
  PRIMARY KEY  (`item_id`,`uom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_item_origin_price` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `suppier_id` int(11) NOT NULL,
  `original_price` double NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_origin_price_item` (`item_id`),
  KEY `idx_origin_price_supplier` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_item_price` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `contacttype_id` int(11) NOT NULL,
  `sell_price` double NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_price_item` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_uom_category` (
  `id` int(11) NOT NULL auto_increment,
  `uom_category_code` varchar(32) NOT NULL,
  `uom_category_name` varchar(128) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_unit_of_measure` (
  `id` int(11) NOT NULL auto_increment,
  `uom_category_id` int(11) NOT NULL,
  `uom_code` varchar(32) NOT NULL collate utf8_bin,
  `uom_name` varchar(128) NOT NULL collate utf8_bin,
  `is_base_measure` tinyint(4) NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_uom_code` (`uom_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_manufacturer` (
  `id` int(11) NOT NULL auto_increment,
  `manu_code` varchar(128) NOT NULL collate utf8_bin,
  `manu_name` varchar(128) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_manu_code` (`manu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


CREATE TABLE `s_bank_account` (
  `id` int(11) NOT NULL auto_increment,
  `bank_code` varchar(32) NOT NULL collate utf8_bin,
  `bank_name` varchar(128) collate utf8_bin,
  `account_number` varchar(32) NOT NULL,
  `account_name` varchar(32),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_bank_code` (`bank_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


CREATE TABLE `s_supplier` (
  `id` int(11) NOT NULL auto_increment,
  `supplier_code` varchar(32) NOT NULL collate utf8_bin,
  `supplier_name` varchar(128) NOT NULL collate utf8_bin,
  `main_contact_id` int(11),
  `phone_number` varchar(32),
  `fix_phone_number` varchar(32),
  `fax_number` varchar(32),
  `email` varchar(32),
  `bankaccount_id` int(11),
  `note` varchar(128),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_supplier_code` (`supplier_code`),
  KEY `idx_main_contact_id` (`main_contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_sales_contract` (
  `id` int(11) NOT NULL auto_increment,
  `salescon_code` varchar(32) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `date_contract` date NOT NULL,
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_salescon_code` (`salescon_code`),
  KEY `idx_supplier_id` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_sales_contract` (
  `id` int(11) NOT NULL auto_increment,
  `salescon_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` double NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_salescon_id` (`salescon_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_code` varchar(32) NOT NULL collate utf8_bin,
  `store_name` varchar(128) NOT NULL collate utf8_bin,
  `address` varchar(256) NOT NULL,
  `stored_address` varchar(256) NOT NULL,
  `import_address` varchar(256) NOT NULL,
  `export_address` varchar(256) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_store_code` (`store_code`),
  KEY `idx_store_name` (`store_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `sellable_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_session_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `refer_month` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_session_detail_store` (
  `id` int(11) NOT NULL auto_increment,
  `sess_store_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `begin_amount` int(11) NOT NULL,
  `end_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_sess_store_id` (`sess_store_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


CREATE TABLE `s_check_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `date_check` date NOT NULL,
  `responsible_user` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_check_store` (
  `id` int(11) NOT NULL auto_increment,
  `checkstore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `removed_amount` int(11) NOT NULL,
  `removed_type` varchar(32) NOT NULL,
  `remark` varchar(256) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_checkstore_id` (`checkstore_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_exchange_store_form` (
  `id` int(11) NOT NULL auto_increment,
  `exchange_store_code` varchar(32) NOT NULL,
  `from_store_id` int(11) NOT NULL,
  `to_store_id` int(11) NOT NULL,
  `from_user` varchar(32) NOT NULL,
  `to_user` varchar(32) NOT NULL,
  `responsible_user` varchar(32) NOT NULL,
  `created_date` date NOT NULL,
  `sent_date` date,
  `received_date` date,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_exchange_store_code` (`exchange_store_code`),
  KEY `idx_from_store_id` (`from_store_id`),
  KEY `idx_to_store_id` (`to_store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_exchange_store` (
  `id` int(11) NOT NULL auto_increment,
  `exchangestore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_exchangestore_id` (`exchangestore_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_import_product_form` (
  `id` int(11) NOT NULL auto_increment,
  `importproduct_code` varchar(32) NOT NULL,
  `created_date` date NOT NULL,
  `store_id` int(11) NOT NULL,
  `salescon_id` int(11),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_importproduct_code` (`importproduct_code`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_salescon_id` (`salescon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_import_product` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `import_amount` int(11) NOT NULL,
  `remaining_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

  -- GOODS, SERVICE (maintainance), COMPONENT, VOUCHER
CREATE TABLE `s_product_type` (
  `id` int(11) NOT NULL auto_increment,
  `product_type_code` varchar(32) NOT NULL,
  `product_family_type` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_product_type_code` (`product_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

-- s_package extends from s_product
CREATE TABLE `s_package` (
  `id` int(11) NOT NULL auto_increment,
  `package_code` varchar(32) NOT NULL collate utf8_bin,
  `package_name` varchar(32) NOT NULL collate utf8_bin,
  `total_price` double NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_package_code` (`package_code`),
  KEY `idx_package_name` (`package_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_package_line` (
  `id` int(11) NOT NULL auto_increment,
  `package_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `total_price` double NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_package_id` (`package_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

-- if status is RESERVED, sellable_amount of store will be decrease
-- OPEN, RESERVED, CLOSED, CANCELLED.
-- NO_PAYMENT, DEPOSIT, BALANCED
CREATE TABLE `s_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_number` varchar(32) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `created_date` varchar(32) NOT NULL,
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `status` varchar(32) NOT NULL,
  `payment_status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_invoice_number` (`invoice_number`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `price_before_tax` double NOT NULL default '0',
  `price_of_tax` double NOT NULL default '0',
  `price_after_tax` double NOT NULL default '0',
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_export_store_form` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `store_id` int(11) NOT NULL,
  `contact_id` int(11),
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_export_store` (
  `id` int(11) NOT NULL auto_increment,
  `exportstore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_exportstore_id` (`exportstore_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

-- should have shipment_type table
 -- UN_SENT, SENDING, RECEIVED
CREATE TABLE `s_shipment` (
  `id` int(11) NOT NULL auto_increment,
  `exportstore_id` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_exportstore` (`exportstore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

 -- OPEN, PAID, CANCELLED
CREATE TABLE `s_payment_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11) NOT NULL,
  `money` double NOT NULL default '0',
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_invoice_id` (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

 -- OPEN, PAID, CANCELLED
CREATE TABLE `s_payment_contact` (
  `id` int(11) NOT NULL auto_increment,
  `contact_id` int(11) NOT NULL,
  `money` double NOT NULL default '0',
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

 -- CUSTOMER (B2B, B2C), SUPPLIER, SUPPORTEE
CREATE TABLE `s_contact_type` (
  `id` int(11) NOT NULL auto_increment,
  `contact_type_code` varchar(32) NOT NULL,
  `description` varchar(128) NOT NULL collate utf8_bin,
  `contact_family_type` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_contact_type_code` (`contact_type_code`),
  KEY `idx_contact_family_type` (`contact_family_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_contact_debt` (
  `id` int(11) NOT NULL auto_increment,
  `contact_id` int(11) NOT NULL,
  `debt_amount` int(11) NOT NULL,
  `money_type` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

 -- ON, OFF
CREATE TABLE `s_coupon` (
  `id` int(11) NOT NULL auto_increment,
  `coupon_code` varchar(32) NOT NULL collate utf8_bin,
  `coupon_name` varchar(32) NOT NULL collate utf8_bin,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_coupon_code` (`coupon_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

 -- ON, OFF
CREATE TABLE `s_coupon_item` (
  `id` int(11) NOT NULL auto_increment,
  `coupon_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `base_price` int(11) NOT NULL,
  `coupon_price` int(11) NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_item` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_warranty_form` (
  `id` int(11) NOT NULL auto_increment,
  `good_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `number_of_month` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_good_id` (`good_id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_warranty` (
  `id` int(11) NOT NULL auto_increment,
  `warranty_id` int(11) NOT NULL,
  `warranty_date` date NOT NULL,
  `description` varchar(256) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_warranty_id` (`warranty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_maintainance_form` (
  `id` int(11) NOT NULL auto_increment,
  `maintainance_date` date NOT NULL,
  `contact_id` int(11) NOT NULL,
  `invoice_id` int(11),
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_detail_maintainance` (
  `id` int(11) NOT NULL auto_increment,
  `maintainance_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` int(11) NOT NULL,
  `total_price` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_maintainance_id` (`maintainance_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_support_form` (
  `id` int(11) NOT NULL auto_increment,
  `supplier_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `reason` varchar(128) NOT NULL collate utf8_bin,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `number_of_month` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_contact_id` (`contact_id`),
  KEY `idx_supplier_id` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

  -- for reference, not count into sales
CREATE TABLE `s_detail_support_form` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `total_price` double NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_warning_board` (
  `id` int(11) NOT NULL auto_increment,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_received_money_form` (
  `id` int(11) NOT NULL auto_increment,
  `related_code` varchar(32) NOT NULL,
  `receive_type` varchar(32) NOT NULL,
  `responsible_user` varchar(32) NOT NULL,
  `money` int(11) NOT NULL,
  `type_money` varchar(32) NOT NULL,
  `validate_user` varchar(32) NOT NULL,
  `description` varchar(256) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_related_code` (`related_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

-- status: EMAIL, IMPORT_PRODUCT
CREATE TABLE `s_paid_money_form` (
  `id` int(11) NOT NULL auto_increment,
  `contact_id` int(11),
  `invoice_id` int(11),
  `importstore_id` int(11),
  `related_code` varchar(32) NOT NULL,
  `paid_type` varchar(32) NOT NULL, 
  `responsible_user` varchar(32) NOT NULL,
  `money` int(11) NOT NULL,
  `type_money` varchar(32) NOT NULL,
  `validate_user` varchar(32) NOT NULL,
  `description` varchar(256) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` date NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` date NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_related_code` (`related_code`),
  KEY `idx_contact_id` (`contact_id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_importstore_id` (`importstore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `s_upload_file` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(128) NOT NULL default '' collate utf8_bin,
  `description` varchar(128) NOT NULL default '' collate utf8_bin,
  `diskfile` varchar(128) NOT NULL default '' collate utf8_bin,
  `filename` varchar(128) NOT NULL default '' collate utf8_bin,
  `folder` varchar(128) NOT NULL default '',
  `filesize` int(11) NOT NULL default '0',
  `file_type` varchar(128) NOT NULL default '',
  `content` longblob NOT NULL,
  PRIMARY KEY  (`id`),
  KEY `idx_diskfile` (`diskfile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

