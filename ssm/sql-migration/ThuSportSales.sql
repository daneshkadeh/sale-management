  -- GOODS, SERVICE (maintainance), COMPONENT, VOUCHER
CREATE TABLE `s_product_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `product_family_type` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_product_type_code ON s_product_type (code);

CREATE TABLE `s_product` (
  `id` int(11) NOT NULL auto_increment,
  `producttype_id`  int(11),
  `code` varchar(32),
  `name` varchar(128) collate utf8_bin,
  `manufacturer_id` int(11),
  `model` varchar(32) collate utf8_bin,
  `description` varchar(128) collate utf8_bin,
  `uploadfile_id` int(11),
  `main_uom_id` int(11),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_product_code ON s_product(code);

 -- collected from list linked uom
CREATE TABLE `s_item` (
  `id` int(11) NOT NULL auto_increment,
  `product_id` int(11) NOT NULL,
  `sum_uom_name` varchar(128) collate utf8_bin,
  `base_sell_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_product_id ON s_item(product_id);

-- Article is a idenfied item (eg. 1 t-shirt is a article)
CREATE TABLE `s_article` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `barcode` varchar(32),
  `first_maintain_date` datetime,
  `second_maintain_date` datetime,
  `store_id` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_barcode ON s_article(barcode);
CREATE INDEX idx_item_id ON s_article(item_id);

CREATE TABLE `at_item_uom` (
  `item_id` int(11) NOT NULL,
  `uom_id` int(11) NOT NULL,
  PRIMARY KEY  (`item_id`,`uom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_item_origin_price` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `original_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_item_id ON s_item_origin_price(item_id);
CREATE UNIQUE INDEX ui_item_supplier ON s_item_origin_price(item_id, supplier_id);

CREATE TABLE `s_item_price` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `contacttype_id` int(11) NOT NULL,
  `sell_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_item_id ON s_item_price(item_id);
CREATE UNIQUE INDEX ui_item_contacttype_id ON s_item_price(item_id, contacttype_id);

CREATE TABLE `s_uom_category` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `uom_category_name` varchar(128) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_uom_category_code ON s_uom_category(code);

CREATE TABLE `s_unit_of_measure` (
  `id` int(11) NOT NULL auto_increment,
  `uom_category_id` int(11) NOT NULL,
  `code` varchar(32) NOT NULL,
  `uom_name` varchar(128) NOT NULL collate utf8_bin,
  `is_base_measure` tinyint(4) NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;
CREATE UNIQUE INDEX ui_uom_code ON s_unit_of_measure(code);

CREATE TABLE `s_manufacturer` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_manufacturer_code ON s_manufacturer(code);

CREATE TABLE `s_bank` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `bank_name` varchar(128) collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_bank_code ON s_bank(code);

CREATE TABLE `s_bank_account` (
  `id` int(11) NOT NULL auto_increment,
  `bank_id` int(11) NOT NULL,
  `account_number` varchar(32) NOT NULL,
  `account_name` varchar(32),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_bank_account ON s_bank_account(bank_id, account_number);

CREATE TABLE `s_supplier` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `supplier_name` varchar(128) NOT NULL collate utf8_bin,
  `main_contact_id` int(11),
  `phone_number` varchar(32),
  `fix_phone_number` varchar(32),
  `fax_number` varchar(32),
  `email` varchar(32),
  `bank_account_id` int(11),
  `note` varchar(256),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_supplier_code ON s_supplier(code);

-- status: OPEN, PROCESSING, CLOSED, CANCELLED.
CREATE TABLE `s_sales_contract` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `datetime_contract` datetime NOT NULL,
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(16) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_sales_contract_code ON s_sales_contract(code);
CREATE INDEX idx_supplier_id ON s_sales_contract(supplier_id);

CREATE TABLE `s_detail_sales_contract` (
  `id` int(11) NOT NULL auto_increment,
  `salescon_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_salescon_id ON s_detail_sales_contract(salescon_id);
CREATE UNIQUE INDEX idx_salescon_item ON s_detail_sales_contract(salescon_id, item_id);

CREATE TABLE `s_store` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `store_name` varchar(128) NOT NULL collate utf8_bin,
  `manager_code` varchar(32) NOT NULL,
  `address` varchar(256) NOT NULL collate utf8_bin,
  `stored_address` varchar(256) NOT NULL collate utf8_bin,
  `import_address` varchar(256) NOT NULL collate utf8_bin,
  `export_address` varchar(256) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_store_code ON s_store(code);

CREATE TABLE `s_detail_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `sellable_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_store_id ON s_detail_store(store_id);
CREATE UNIQUE INDEX ui_store_item ON s_detail_store(store_id, item_id);

CREATE TABLE `s_session_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `refer_month` int(11) NOT NULL,
  `refer_quarter` int(11) NOT NULL,
  `refer_year` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_store_id ON s_session_store(store_id);
CREATE INDEX idx_store_year ON s_session_store(store_id, refer_year);

CREATE TABLE `s_detail_session_store` (
  `id` int(11) NOT NULL auto_increment,
  `sess_store_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `begin_amount` int(11) NOT NULL,
  `end_amount` int(11),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_sess_store_id ON s_detail_session_store(sess_store_id);
CREATE UNIQUE INDEX ui_sess_store_item ON s_detail_session_store(sess_store_id, item_id);

-- status: OPEN, VALIDATED, CANCELLED.
CREATE TABLE `s_check_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `date_start_check` datetime NOT NULL,
  `date_end_check` datetime,
  `responsible_user` varchar(32) NOT NULL,
  `status` varchar(16) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_store_id ON s_check_store(store_id);
CREATE UNIQUE INDEX ui_store_date ON s_check_store(store_id, date_start_check);

CREATE TABLE `s_detail_check_store` (
  `id` int(11) NOT NULL auto_increment,
  `checkstore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `change_amount` int(11) NOT NULL,
  `change_type` varchar(32) NOT NULL,
  `remark` varchar(256),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_checkstore_id ON s_detail_check_store(checkstore_id);
CREATE UNIQUE INDEX ui_checkstore_item ON s_detail_check_store(checkstore_id, item_id);

CREATE TABLE `s_exchange_store_form` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `from_store_id` int(11) NOT NULL,
  `to_store_id` int(11) NOT NULL,
  `from_user` varchar(32) NOT NULL,
  `to_user` varchar(32) NOT NULL,
  `responsible_user` varchar(32) NOT NULL,
  `created_date` datetime NOT NULL,
  `sent_date` datetime,
  `received_date` datetime,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_exchange_store_form(code);
CREATE INDEX idx_from_store_id ON s_exchange_store_form(from_store_id);
CREATE INDEX idx_to_store_id ON s_exchange_store_form(to_store_id);

CREATE TABLE `s_detail_exchange_store` (
  `id` int(11) NOT NULL auto_increment,
  `exchangestore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_exchangestore_item ON s_detail_exchange_store(exchangestore_id,item_id);
CREATE INDEX ui_exchangestore_id ON s_detail_exchange_store(exchangestore_id);

CREATE TABLE `s_import_product_form` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `created_date` datetime NOT NULL,
  `store_id` int(11) NOT NULL,
  `salescon_id` int(11),
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_import_product_form(code);
CREATE INDEX idx_salescon_id ON s_import_product_form(salescon_id);
CREATE INDEX idx_salescon_store ON s_import_product_form(salescon_id, store_id);

CREATE TABLE `s_detail_import_product` (
  `id` int(11) NOT NULL auto_increment,
  `import_product_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `import_amount` int(11) NOT NULL,
  `remaining_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_import_item ON s_detail_import_product(import_product_id, item_id);
CREATE INDEX idx_import_product_id ON s_detail_import_product(import_product_id);

CREATE TABLE `s_package` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL collate utf8_bin,
  `total_expected_item_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_package(code);

CREATE TABLE `s_package_line` (
  `id` int(11) NOT NULL auto_increment,
  `package_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `parentpackline_id` int(11),
  `min_item_amount` int(11) NOT NULL,
  `max_item_amount` int(11) NOT NULL,
  `optional` int(1) NOT NULL default '1',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_package_item ON s_package_line(package_id, item_id);
CREATE INDEX idx_package_id ON s_package_line(package_id);


CREATE TABLE `s_package_line_item_price` (
  `id` int(11) NOT NULL auto_increment,
  `package_line_id` int(11) NOT NULL,
  `contact_type_id` int(11) NOT NULL,
  `sell_item_price` double NOT NULL,
  `currency` varchar(3) NOT NULL,
  `discount_percent` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


-- if status is RESERVED, sellable_amount of store will be decreased
-- status: OPEN, RESERVED, CLOSED, CANCELLED, ABANDONED.
-- invoice_type: SALES, MAINTAINANCE
-- payment_status: NO_PAYMENT, DEPOSIT, BALANCED.
CREATE TABLE `s_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_number` varchar(32) NOT NULL,
  `invoice_type` varchar(32) NOT NULL,
  `contact_id` int(11),
  `created_date` datetime NOT NULL,
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) NOT NULL,
  `payment_status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_invoice_number ON s_invoice(invoice_number);
CREATE INDEX idx_contact_id ON s_invoice(contact_id);

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
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) NOT NULL,
  `detail_invoice_type` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_invoice_item ON s_detail_invoice(invoice_id, item_id);
CREATE INDEX idx_invoice_id ON s_detail_invoice(invoice_id);

CREATE TABLE `s_export_store_form` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `store_id` int(11) NOT NULL,
  `contact_id` int(11),
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX ui_invoice_store ON s_export_store_form(invoice_id, store_id);
CREATE INDEX idx_invoice_id ON s_export_store_form(invoice_id);
CREATE INDEX idx_contact_id ON s_export_store_form(contact_id);

CREATE TABLE `s_detail_export_store` (
  `id` int(11) NOT NULL auto_increment,
  `exportstore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_export_item ON s_detail_export_store(exportstore_id, item_id);
CREATE INDEX idx_exportstore_id ON s_detail_export_store(exportstore_id);

-- should have shipment_type table
CREATE TABLE `s_shipment_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `base_price` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `active` int(1) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_shipment_type(code);

 -- UN_SENT, SENDING, RECEIVED
CREATE TABLE `s_shipment` (
  `id` int(11) NOT NULL auto_increment,
  `shipment_type_id` int(11) NOT NULL,
  `exportstore_id` int(11) NOT NULL,
  `money` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_exportstore_id ON s_shipment(exportstore_id);

CREATE TABLE `s_payment_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `content_type` varchar(32) NOT NULL,
  `is_received` int(1) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


 -- status: OPEN, CLOSED, CANCELLED. payement_mean: CASH, VISA, CREDIT CARD, VOUCHER, BANK
CREATE TABLE `s_payment` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11),
  `contact_id` int(11),
  `payment_type_id` int(11) NOT NULL,
  `money` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) NOT NULL,
  `payment_mean` varchar(16) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_invoice_id ON s_payment(invoice_id);
CREATE INDEX idx_contact_id ON s_payment(contact_id);

 -- CUSTOMER (B2B, B2C), SUPPLIER, SUPPORTEE
CREATE TABLE `s_contact_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `description` varchar(128) NOT NULL collate utf8_bin,
  `contact_family_type` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_contact_type(code);

CREATE TABLE `s_contact` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `full_name` varchar(128) NOT NULL collate utf8_bin,
  `contact_type_id` int(11) NOT NULL,
  `address` varchar(256) collate utf8_bin,
  `phone` varchar(32) collate utf8_bin,
  `fix_phone` varchar(32) collate utf8_bin,
  `fax` varchar(32) collate utf8_bin,
  `email` varchar(64) collate utf8_bin,
  `tax_code` varchar(32) collate utf8_bin,
  `bank_account_id` int(11),
  `maximum_day_debt` int(11) NOT NULL default '0',
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_contact(code);

CREATE TABLE `s_contact_shop` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL collate utf8_bin,
  `contact_id` int(11) NOT NULL,
  `address` varchar(256) collate utf8_bin,
  `phone` varchar(32) collate utf8_bin,
  `fix_phone` varchar(32) collate utf8_bin,
  `fax` varchar(32) collate utf8_bin,
  `email` varchar(64) collate utf8_bin,
  `remark` varchar(256) collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_contact_shop(code);
CREATE INDEX idx_contact_id ON s_contact_shop(contact_id);

CREATE TABLE `s_contact_debt` (
  `id` int(11) NOT NULL auto_increment,
  `contact_id` int(11) NOT NULL,
  `debt_money` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_contact_currency ON s_contact_debt(contact_id, currency);
CREATE INDEX idx_contact_id ON s_contact_debt(contact_id);

 -- ON, OFF
CREATE TABLE `s_coupon` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `coupon_name` varchar(32) NOT NULL collate utf8_bin,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_code ON s_coupon(code);

 -- ON, OFF
CREATE TABLE `s_coupon_item` (
  `id` int(11) NOT NULL auto_increment,
  `coupon_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `contacttype_id` int(11) NOT NULL,
  `base_price` int(11) NOT NULL,
  `coupon_price` int(11) NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_coupon_item ON s_coupon_item(coupon_id, item_id, contacttype_id);
CREATE INDEX idx_coupon_id ON s_coupon_item(coupon_id);
CREATE INDEX idx_item_id ON s_coupon_item(item_id);

CREATE TABLE `s_warranty_form` (
  `id` int(11) NOT NULL auto_increment,
  `article_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `number_of_month` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE UNIQUE INDEX ui_article_id ON s_warranty_form(article_id);

CREATE TABLE `s_detail_warranty` (
  `id` int(11) NOT NULL auto_increment,
  `warranty_id` int(11) NOT NULL,
  `warranty_date` datetime NOT NULL,
  `description` varchar(256) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_warranty_id ON s_detail_warranty(warranty_id);

-- TODO: Is this table required? Could we use table invoice to sell SERVICE product.  (invoiceType = MAINTAINANCE)
CREATE TABLE `s_maintainance_form` (
  `id` int(11) NOT NULL auto_increment,
  `maintainance_date` datetime NOT NULL,
  `contact_id` int(11) NOT NULL,
  `invoice_id` int(11),
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
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
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_maintainance_id` (`maintainance_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_support_form` (
  `id` int(11) NOT NULL auto_increment,
  `supplier_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `reason` varchar(128) NOT NULL collate utf8_bin,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `number_of_month` int(11) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_supplier_id ON s_support_form(supplier_id);
CREATE INDEX idx_contact_id ON s_support_form(contact_id);
CREATE INDEX idx_end_date ON s_support_form(end_date); -- TODO: is date index effective?

  -- for reference, not count into sales
CREATE TABLE `s_detail_support_form` (
  `id` int(11) NOT NULL auto_increment,
  `support_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `total_price` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE INDEX idx_support_id ON s_detail_support_form(support_id);
CREATE INDEX idx_item_id ON s_detail_support_form(item_id);

CREATE TABLE `s_warning_board` (
  `id` int(11) NOT NULL auto_increment,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE `s_received_money_form` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) NOT NULL,
  `receive_type` varchar(32) NOT NULL,
  `responsible_user` varchar(32) NOT NULL,
  `money` int(11) NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `validatetime_user` varchar(32) NOT NULL,
  `description` varchar(256) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_related_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

-- status: EMAIL, IMPORT_PRODUCT
CREATE TABLE `s_paid_money_form` (
  `id` int(11) NOT NULL auto_increment,
  `contact_id` int(11),
  `invoice_id` int(11),
  `importstore_id` int(11),
  `code` varchar(32) NOT NULL,
  `paid_type` varchar(32) NOT NULL, 
  `responsible_user` varchar(32) NOT NULL,
  `money` int(11) NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `validatetime_user` varchar(32) NOT NULL,
  `description` varchar(256) NOT NULL collate utf8_bin,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_related_code` (`code`),
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
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_diskfile` (`diskfile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `s_currency` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(32) collate utf8_bin NOT NULL default '',
  `code` varchar(3) collate utf8_bin NOT NULL default '',
  `symbol_left` varchar(12) collate utf8_bin NOT NULL,
  `symbol_right` varchar(12) collate utf8_bin NOT NULL,
  `decimal_place` char(1) collate utf8_bin NOT NULL,
  `value` double NOT NULL,
  `status` int(1) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS `s_operator` (
  `id` int(11) NOT NULL auto_increment,
  `login` varchar(32) collate utf8_bin NOT NULL default '',
  `password` varchar(32) collate utf8_bin NOT NULL default '',
  `full_name` varchar(256) collate utf8_bin NOT NULL default '',
  `email` varchar(64) collate utf8_bin,
  `phone` varchar(32) collate utf8_bin,
  `address` varchar(256) collate utf8_bin,
  `active` int(1) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;
CREATE UNIQUE INDEX ui_login ON s_operator(login);
