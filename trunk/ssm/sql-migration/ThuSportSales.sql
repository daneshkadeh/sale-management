-- phpMyAdmin SQL Dump
-- version 2.11.0
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Mar 30, 2012 at 12:37 PM
-- Server version: 5.0.45
-- PHP Version: 5.2.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `solution_testssm`
--

-- --------------------------------------------------------



-- ------ Function and table to support get next sequence --------- --
delimiter //
create function seq(seq_name char (100)) returns bigint
begin
 update seq set val=last_insert_id(val+1) where name=seq_name;
 return last_insert_id();
end
//
delimiter ;

CREATE TABLE `seq` (
  `name` char(100) NOT NULL,
  `val` bigint(20) unsigned NOT NULL,
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



-- --------------------------------------------------------------- --

--
-- Table structure for table `acl_class`
--

CREATE TABLE `acl_class` (
  `id` bigint(20) NOT NULL auto_increment,
  `class` varchar(500) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_2` (`class`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `acl_class`
--

INSERT INTO `acl_class` (`id`, `class`) VALUES
(3, 'com.s3s.ssm.security.ACLResourceEnum'),
(2, 'com.s3s.ssm.view.security.ACLResourceEnum'),
(1, 'com.s3s.ssm.view.security.ACLResources');

-- --------------------------------------------------------

--
-- Table structure for table `acl_entry`
--

CREATE TABLE `acl_entry` (
  `id` bigint(20) NOT NULL auto_increment,
  `acl_object_identity` bigint(20) NOT NULL,
  `ace_order` int(11) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `mask` int(11) NOT NULL,
  `granting` tinyint(1) NOT NULL,
  `audit_success` tinyint(1) NOT NULL,
  `audit_failure` tinyint(1) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_4` (`acl_object_identity`,`ace_order`),
  KEY `foreign_fk_5` (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=197 ;

--
-- Dumping data for table `acl_entry`
--

INSERT INTO `acl_entry` (`id`, `acl_object_identity`, `ace_order`, `sid`, `mask`, `granting`, `audit_success`, `audit_failure`) VALUES
(107, 1, 0, 5, 16, 1, 0, 0),
(108, 1, 1, 9, 4, 1, 0, 0),
(109, 1, 2, 9, 16, 1, 0, 0),
(110, 1, 3, 8, 16, 1, 0, 0),
(111, 1, 4, 10, 16, 1, 0, 0),
(113, 3, 0, 5, 16, 1, 0, 0),
(189, 5, 0, 5, 32, 1, 0, 0),
(190, 5, 1, 5, 8, 1, 0, 0),
(191, 5, 2, 5, 4, 1, 0, 0),
(192, 5, 3, 5, 16, 1, 0, 0),
(193, 5, 4, 8, 16, 1, 0, 0),
(194, 5, 5, 7, 16, 1, 0, 0),
(195, 6, 0, 5, 32, 1, 0, 0),
(196, 6, 1, 8, 16, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `acl_object_identity`
--

CREATE TABLE `acl_object_identity` (
  `id` bigint(20) NOT NULL auto_increment,
  `object_id_class` bigint(20) NOT NULL,
  `object_id_identity` bigint(20) NOT NULL,
  `parent_object` bigint(20) default NULL,
  `owner_sid` bigint(20) default NULL,
  `entries_inheriting` tinyint(1) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_3` (`object_id_class`,`object_id_identity`),
  KEY `foreign_fk_1` (`parent_object`),
  KEY `foreign_fk_3` (`owner_sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `acl_object_identity`
--

INSERT INTO `acl_object_identity` (`id`, `object_id_class`, `object_id_identity`, `parent_object`, `owner_sid`, `entries_inheriting`) VALUES
(1, 1, 0, NULL, 1, 1),
(2, 1, 1, NULL, 1, 1),
(3, 2, 0, NULL, 11, 1),
(4, 2, 1, NULL, 11, 1),
(5, 3, 0, NULL, 11, 1),
(6, 3, 1, NULL, 11, 1),
(7, 3, 2, NULL, 11, 1),
(8, 3, 3, NULL, 11, 1),
(9, 3, 4, NULL, 11, 1),
(10, 3, 5, NULL, 11, 1),
(11, 3, 6, NULL, 11, 1);

-- --------------------------------------------------------

--
-- Table structure for table `acl_sid`
--

CREATE TABLE `acl_sid` (
  `id` bigint(20) NOT NULL auto_increment,
  `principal` tinyint(1) NOT NULL,
  `sid` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_1` (`sid`,`principal`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `acl_sid`
--

INSERT INTO `acl_sid` (`id`, `principal`, `sid`) VALUES
(11, 1, 'admin'),
(2, 0, 'ROLE_ADMIN'),
(5, 1, 'ROLE_ADMIN'),
(10, 1, 'ROLE_DEPLO'),
(9, 1, 'ROLE_DEPLOY'),
(4, 0, 'ROLE_MANAGER'),
(7, 1, 'ROLE_MANAGER'),
(6, 1, 'ROLE_PM'),
(8, 1, 'ROLE_TEST'),
(3, 0, 'user_admin'),
(1, 1, 'user_admin');

-- --------------------------------------------------------

--
-- Table structure for table `acl_class`
--

CREATE TABLE `acl_class` (
  `id` bigint(20) NOT NULL auto_increment,
  `class` varchar(500) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_2` (`class`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `acl_class`
--

INSERT INTO `acl_class` (`id`, `class`) VALUES
(3, 'com.s3s.ssm.security.ACLResourceEnum'),
(2, 'com.s3s.ssm.view.security.ACLResourceEnum'),
(1, 'com.s3s.ssm.view.security.ACLResources');

-- --------------------------------------------------------

--
-- Table structure for table `acl_entry`
--

CREATE TABLE `acl_entry` (
  `id` bigint(20) NOT NULL auto_increment,
  `acl_object_identity` bigint(20) NOT NULL,
  `ace_order` int(11) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `mask` int(11) NOT NULL,
  `granting` tinyint(1) NOT NULL,
  `audit_success` tinyint(1) NOT NULL,
  `audit_failure` tinyint(1) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_4` (`acl_object_identity`,`ace_order`),
  KEY `foreign_fk_5` (`sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=197 ;

--
-- Dumping data for table `acl_entry`
--

INSERT INTO `acl_entry` (`id`, `acl_object_identity`, `ace_order`, `sid`, `mask`, `granting`, `audit_success`, `audit_failure`) VALUES
(107, 1, 0, 5, 16, 1, 0, 0),
(108, 1, 1, 9, 4, 1, 0, 0),
(109, 1, 2, 9, 16, 1, 0, 0),
(110, 1, 3, 8, 16, 1, 0, 0),
(111, 1, 4, 10, 16, 1, 0, 0),
(113, 3, 0, 5, 16, 1, 0, 0),
(189, 5, 0, 5, 32, 1, 0, 0),
(190, 5, 1, 5, 8, 1, 0, 0),
(191, 5, 2, 5, 4, 1, 0, 0),
(192, 5, 3, 5, 16, 1, 0, 0),
(193, 5, 4, 8, 16, 1, 0, 0),
(194, 5, 5, 7, 16, 1, 0, 0),
(195, 6, 0, 5, 32, 1, 0, 0),
(196, 6, 1, 8, 16, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `acl_object_identity`
--

CREATE TABLE `acl_object_identity` (
  `id` bigint(20) NOT NULL auto_increment,
  `object_id_class` bigint(20) NOT NULL,
  `object_id_identity` bigint(20) NOT NULL,
  `parent_object` bigint(20) default NULL,
  `owner_sid` bigint(20) default NULL,
  `entries_inheriting` tinyint(1) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_3` (`object_id_class`,`object_id_identity`),
  KEY `foreign_fk_1` (`parent_object`),
  KEY `foreign_fk_3` (`owner_sid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `acl_object_identity`
--

INSERT INTO `acl_object_identity` (`id`, `object_id_class`, `object_id_identity`, `parent_object`, `owner_sid`, `entries_inheriting`) VALUES
(1, 1, 0, NULL, 1, 1),
(2, 1, 1, NULL, 1, 1),
(3, 2, 0, NULL, 11, 1),
(4, 2, 1, NULL, 11, 1),
(5, 3, 0, NULL, 11, 1),
(6, 3, 1, NULL, 11, 1),
(7, 3, 2, NULL, 11, 1),
(8, 3, 3, NULL, 11, 1),
(9, 3, 4, NULL, 11, 1),
(10, 3, 5, NULL, 11, 1),
(11, 3, 6, NULL, 11, 1);

-- --------------------------------------------------------

--
-- Table structure for table `acl_sid`
--

CREATE TABLE `acl_sid` (
  `id` bigint(20) NOT NULL auto_increment,
  `principal` tinyint(1) NOT NULL,
  `sid` varchar(100) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `unique_uk_1` (`sid`,`principal`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `acl_sid`
--

INSERT INTO `acl_sid` (`id`, `principal`, `sid`) VALUES
(11, 1, 'admin'),
(2, 0, 'ROLE_ADMIN'),
(5, 1, 'ROLE_ADMIN'),
(10, 1, 'ROLE_DEPLO'),
(9, 1, 'ROLE_DEPLOY'),
(4, 0, 'ROLE_MANAGER'),
(7, 1, 'ROLE_MANAGER'),
(6, 1, 'ROLE_PM'),
(8, 1, 'ROLE_TEST'),
(3, 0, 'user_admin'),
(1, 1, 'user_admin');

-- --------------------------------------------------------

--
-- Table structure for table `at_advantage_buyitem`
--

CREATE TABLE `at_advantage_buyitem` (
  `advantage_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`item_id`),
  KEY `FK5C614979FC2B26F1` (`item_id`),
  KEY `FK5C614979C59FC223` (`advantage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `at_advantage_buyitem`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_advantage_buypackage`
--

CREATE TABLE `at_advantage_buypackage` (
  `advantage_id` bigint(20) NOT NULL,
  `package_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`package_id`),
  KEY `FKAF290420C59FC223` (`advantage_id`),
  KEY `FKAF290420D556929E` (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `at_advantage_buypackage`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_advantage_giftitem`
--

CREATE TABLE `at_advantage_giftitem` (
  `advantage_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`item_id`),
  KEY `FK9C8D2563FC2B26F1` (`item_id`),
  KEY `FK9C8D2563C59FC223` (`advantage_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `at_advantage_giftitem`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_advantage_giftpackage`
--

CREATE TABLE `at_advantage_giftpackage` (
  `advantage_id` bigint(20) NOT NULL,
  `package_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`package_id`),
  KEY `FK5F15A7F6C59FC223` (`advantage_id`),
  KEY `FK5F15A7F6D556929E` (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `at_advantage_giftpackage`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_item_uom`
--

CREATE TABLE `at_item_uom` (
  `item_id` bigint(20) NOT NULL,
  `uom_id` bigint(20) NOT NULL,
  KEY `FK6788DC73FC2B26F1` (`item_id`),
  KEY `FK6788DC73AFB657A0` (`uom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `at_item_uom`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_product_property`
--

CREATE TABLE `at_product_property` (
  `product_id` bigint(20) NOT NULL,
  `property_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`product_id`,`property_id`),
  KEY `FK50AE41B15E2CA132` (`property_id`),
  KEY `FK50AE41B1AB63B28A` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `at_product_property`
--


-- --------------------------------------------------------

--
-- Table structure for table `au_role`
--

CREATE TABLE `au_role` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `isEnable` bit(1) default NULL,
  `name` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `au_role`
--


-- --------------------------------------------------------

--
-- Table structure for table `au_user`
--

CREATE TABLE `au_user` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `isAccountNonExpired` bit(1) default NULL,
  `isAccountNonLocked` bit(1) default NULL,
  `isCredentialsNonExpired` bit(1) default NULL,
  `isEnabled` bit(1) default NULL,
  `password` varchar(32) default NULL,
  `username` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `au_user`
--


-- --------------------------------------------------------

--
-- Table structure for table `au_user_role`
--

CREATE TABLE `au_user_role` (
  `id_user` bigint(20) NOT NULL,
  `id_role` bigint(20) NOT NULL,
  PRIMARY KEY  (`id_user`,`id_role`),
  KEY `FKB2524A1F17808E9C` (`id_role`),
  KEY `FKB2524A1F17836546` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `au_user_role`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_bank`
--

CREATE TABLE `config_bank` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `address` longtext,
  `bank_name` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `config_bank`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_bank_account`
--

CREATE TABLE `config_bank_account` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `account_name` varchar(128) default NULL,
  `account_number` varchar(32) NOT NULL,
  `bank_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK1E4FFD0794DAE04` (`bank_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `config_bank_account`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_currency`
--

CREATE TABLE `config_currency` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `active` bit(1) default NULL,
  `name` varchar(128) default NULL,
  `symbol` varchar(10) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `config_currency`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_exchange_rate`
--

CREATE TABLE `config_exchange_rate` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `rate` double default NULL,
  `update_date` datetime default NULL,
  `currency_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK2A964F3FCE40C623` (`currency_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `config_exchange_rate`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_institution`
--

CREATE TABLE `config_institution` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `agent` varchar(250) default NULL,
  `company_address` varchar(250) default NULL,
  `company_name` varchar(250) default NULL,
  `email` varchar(100) default NULL,
  `export_inv_code_rule` varchar(50) default NULL,
  `fax` varchar(20) default NULL,
  `import_inv_code_rule` varchar(50) default NULL,
  `movement_inv_code_rule` varchar(50) default NULL,
  `order_inv_code_rule` varchar(50) default NULL,
  `payment_bill_code_rule` varchar(50) default NULL,
  `position` varchar(100) default NULL,
  `promotion_code_rule` varchar(50) default NULL,
  `pur_inv_code_rule` varchar(50) default NULL,
  `pur_refund_inv_code_rule` varchar(50) default NULL,
  `receipt_code_rule` varchar(50) default NULL,
  `sales_inv_code_rule` varchar(50) default NULL,
  `sales_refund_inv_code_rule` varchar(50) default NULL,
  `spon_contract_code_rule` varchar(50) default NULL,
  `tel` varchar(20) default NULL,
  `website` varchar(100) default NULL,
  `upload_file_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK62F0B43B89B623E7` (`upload_file_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `config_institution`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_organization`
--

CREATE TABLE `config_organization` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `address` varchar(250) default NULL,
  `benefice_name` longtext,
  `def_detail_inv_num` int(11) default NULL,
  `def_page_row_num` int(11) default NULL,
  `def_payment_method` varchar(255) default NULL,
  `digit_after_amt` int(11) default NULL,
  `digit_after_quan` int(11) default NULL,
  `digit_after_rate` int(11) default NULL,
  `digit_after_unit_price` int(11) default NULL,
  `enable_chg_inv_date` int(11) default NULL,
  `is_default` bit(1) default NULL,
  `name` varchar(100) default NULL,
  `odd_Separator` varchar(1) default NULL,
  `sell_on_credit` int(11) default NULL,
  `thousands_Separator` varchar(1) default NULL,
  `def_currency_id` bigint(20) default NULL,
  `def_stall_id` bigint(20) default NULL,
  `institution_id` bigint(20) default NULL,
  `usd_bank_acct_id` bigint(20) default NULL,
  `vnd_bank_acct_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKD15FAF10996039BE` (`vnd_bank_acct_id`),
  KEY `FKD15FAF1079176F24` (`usd_bank_acct_id`),
  KEY `FKD15FAF10869FF8C` (`def_stall_id`),
  KEY `FKD15FAF10E797C8E9` (`def_currency_id`),
  KEY `FKD15FAF10B52809B0` (`institution_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `config_organization`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_unit_of_measure`
--

CREATE TABLE `config_unit_of_measure` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `is_base_measure` bit(1) NOT NULL,
  `uom_name` varchar(128) NOT NULL,
  `uom_category_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK320EC994CBA000F7` (`uom_category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `config_unit_of_measure`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_uom_category`
--

CREATE TABLE `config_uom_category` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `parentUomCategory_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKAA21F08792B9DA5A` (`parentUomCategory_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `config_uom_category`
--


-- --------------------------------------------------------

--
-- Table structure for table `config_upload_file`
--

CREATE TABLE `config_upload_file` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `content` longblob,
  `title` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `config_upload_file`
--


-- --------------------------------------------------------

--
-- Table structure for table `finace_payment_content`
--

CREATE TABLE `finace_payment_content` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) default NULL,
  `payment_type` varchar(255) default NULL,
  `parent_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK575A39993D390FD9` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `finace_payment_content`
--


-- --------------------------------------------------------

--
-- Table structure for table `finance_contract_payment`
--

CREATE TABLE `finance_contract_payment` (
  `contract_payment_id` bigint(20) NOT NULL,
  `sales_contract_id` bigint(20) default NULL,
  PRIMARY KEY  (`contract_payment_id`),
  KEY `FK2677399E92F51291` (`contract_payment_id`),
  KEY `FK2677399EFA3AB9EF` (`sales_contract_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `finance_contract_payment`
--


-- --------------------------------------------------------

--
-- Table structure for table `finance_payment`
--

CREATE TABLE `finance_payment` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `currency_code` varchar(255) default NULL,
  `money` bigint(20) default NULL,
  `notes` varchar(255) default NULL,
  `payment_date` datetime default NULL,
  `payment_mode` varchar(255) default NULL,
  `rate` int(11) default NULL,
  `operator_id` bigint(20) default NULL,
  `partner_id` bigint(20) default NULL,
  `payment_content_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK2757AEA1984FE183` (`payment_content_id`),
  KEY `FK2757AEA11BBF2162` (`operator_id`),
  KEY `FK2757AEA1F6C7EC4A` (`partner_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `finance_payment`
--


-- --------------------------------------------------------

--
-- Table structure for table `operator_operator`
--

CREATE TABLE `operator_operator` (
  `address` longtext,
  `email` varchar(64) default NULL,
  `full_name` longtext,
  `phone` varchar(32) default NULL,
  `operator_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`operator_id`),
  KEY `FKCD5EA9DFBD0FAB6D` (`operator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `operator_operator`
--


-- --------------------------------------------------------

--
-- Table structure for table `operator_sale_target`
--

CREATE TABLE `operator_sale_target` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `month1` double default NULL,
  `month10` double default NULL,
  `month11` double default NULL,
  `month12` double default NULL,
  `month2` double default NULL,
  `month3` double default NULL,
  `month4` double default NULL,
  `month5` double default NULL,
  `month6` double default NULL,
  `month7` double default NULL,
  `month8` double default NULL,
  `month9` double default NULL,
  `year` int(11) default NULL,
  `id_stall` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK7B3ED16E8DEFC5F4` (`id_stall`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `operator_sale_target`
--


-- --------------------------------------------------------

--
-- Table structure for table `operator_stall`
--

CREATE TABLE `operator_stall` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(50) NOT NULL,
  `manager_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKA0114445A866F1B9` (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `operator_stall`
--


-- --------------------------------------------------------

--
-- Table structure for table `o_stall_user`
--

CREATE TABLE `o_stall_user` (
  `id_stall` bigint(20) NOT NULL,
  `id_user` bigint(20) NOT NULL,
  PRIMARY KEY  (`id_stall`,`id_user`),
  KEY `FKEE152EDA8DEFC5F4` (`id_stall`),
  KEY `FKEE152EDA7632DB3B` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `o_stall_user`
--


-- --------------------------------------------------------

--
-- Table structure for table `store_detail_import`
--

CREATE TABLE `store_detail_import` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `currencyCode` varchar(255) default NULL,
  `price_subtotal` bigint(20) default NULL,
  `currency_code` varchar(255) default NULL,
  `price_unit` bigint(20) default NULL,
  `qty` int(11) default NULL,
  `form_id` bigint(20) default NULL,
  `item_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK60BAB875FC2B26F1` (`item_id`),
  KEY `FK60BAB875DC4E0F67` (`form_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `store_detail_import`
--


-- --------------------------------------------------------

--
-- Table structure for table `store_import_store_form`
--

CREATE TABLE `store_import_store_form` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `created_date` datetime default NULL,
  `import_num` varchar(255) default NULL,
  `is_printed` bit(1) default NULL,
  `is_processed` bit(1) default NULL,
  `mob_order` varchar(255) default NULL,
  `modified_date` datetime default NULL,
  `print_after_save` bit(1) default NULL,
  `receipt_date` datetime default NULL,
  `sender` varchar(255) default NULL,
  `ship_num` double default NULL,
  `currency_code` varchar(255) default NULL,
  `shipPrice` bigint(20) default NULL,
  `status` varchar(255) default NULL,
  `supplier_name` varchar(255) default NULL,
  `receiver_id` bigint(20) default NULL,
  `salescon_id` bigint(20) NOT NULL,
  `shipPriceType_id` bigint(20) default NULL,
  `store_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK8633965E965BD837` (`receiver_id`),
  KEY `FK8633965E14F6666B` (`shipPriceType_id`),
  KEY `FK8633965ED78E952B` (`store_id`),
  KEY `FK8633965E2D500E3E` (`salescon_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `store_import_store_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `store_ship_price`
--

CREATE TABLE `store_ship_price` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `currency_code` varchar(255) default NULL,
  `price` bigint(20) default NULL,
  `update_date` datetime NOT NULL,
  `ship_price_type_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKDCC707A426E21DDF` (`ship_price_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `store_ship_price`
--


-- --------------------------------------------------------

--
-- Table structure for table `store_ship_price_type`
--

CREATE TABLE `store_ship_price_type` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `store_ship_price_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `store_store`
--

CREATE TABLE `store_store` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `address` varchar(255) default NULL,
  `export_address` varchar(255) default NULL,
  `import_address` varchar(255) default NULL,
  `store_name` longtext NOT NULL,
  `stored_address` varchar(255) default NULL,
  `manager_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKA7726803A866F1B9` (`manager_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `store_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_advantage`
--

CREATE TABLE `s_advantage` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `discount_percent` int(11) default NULL,
  `name` varchar(128) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_advantage`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_article`
--

CREATE TABLE `s_article` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `barcode` varchar(32) default NULL,
  `first_maintain_date` datetime default NULL,
  `second_maintain_date` datetime default NULL,
  `item_id` bigint(20) NOT NULL,
  `store_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKFBA6512AFC2B26F1` (`item_id`),
  KEY `FKFBA6512AD78E952B` (`store_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_article`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_check_store`
--

CREATE TABLE `s_check_store` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `date_end_check` datetime default NULL,
  `date_start_check` datetime NOT NULL,
  `responsible_user` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `store_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK4205A51ED78E952B` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_check_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_contact_debt`
--

CREATE TABLE `s_contact_debt` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `currency_id` varchar(255) NOT NULL,
  `debt_money` double NOT NULL,
  `partner_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK4565219EF6C7EC4A` (`partner_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_contact_debt`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_contact_shop`
--

CREATE TABLE `s_contact_shop` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `address` longtext,
  `email` varchar(64) default NULL,
  `fax` varchar(32) default NULL,
  `fix_phone` varchar(32) default NULL,
  `name` varchar(128) NOT NULL,
  `phone` varchar(32) default NULL,
  `remark` varchar(100) default NULL,
  `customer_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK456C00011BBADF6A` (`customer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_contact_shop`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_customer`
--

CREATE TABLE `s_customer` (
  `address` longtext,
  `email` varchar(100) default NULL,
  `fax` varchar(20) default NULL,
  `fix_phone` varchar(20) default NULL,
  `mobile_phone` varchar(20) default NULL,
  `tax_code` varchar(50) default NULL,
  `customer_id` bigint(20) NOT NULL,
  `bank_account_id` bigint(20) default NULL,
  PRIMARY KEY  (`customer_id`),
  KEY `FKE684822A83066974` (`customer_id`),
  KEY `FKE684822A72F46197` (`bank_account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_customer`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_exchange_store`
--

CREATE TABLE `s_detail_exchange_store` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `amount` int(11) NOT NULL,
  `exchangestore_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKCB7726A7FC2B26F1` (`item_id`),
  KEY `FKCB7726A7F5E3304F` (`exchangestore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_exchange_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_export_store`
--

CREATE TABLE `s_detail_export_store` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `amount` int(11) NOT NULL,
  `exportstore_id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKFA5CBF8FC2B26F1` (`item_id`),
  KEY `FKFA5CBF886A6F2AF` (`exportstore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_export_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_invoice`
--

CREATE TABLE `s_detail_invoice` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `amount` int(11) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `money_after_tax` double NOT NULL,
  `money_before_tax` double NOT NULL,
  `money_of_tax` double NOT NULL,
  `price_after_tax` double NOT NULL,
  `price_before_tax` double NOT NULL,
  `price_of_tax` double NOT NULL,
  `status` varchar(255) NOT NULL,
  `detail_invoice_type` varchar(255) NOT NULL,
  `invoice_id` bigint(20) default NULL,
  `item_id` bigint(20) NOT NULL,
  `packageline_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKA8D23A8BFC2B26F1` (`item_id`),
  KEY `FKA8D23A8BB1905696` (`invoice_id`),
  KEY `FKA8D23A8B70EF79A3` (`packageline_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `s_detail_invoice`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_sales_contract`
--

CREATE TABLE `s_detail_sales_contract` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `amount` bigint(20) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `unit_price` double NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `salescon_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK46AA09C7FC2B26F1` (`item_id`),
  KEY `FK46AA09C72D500E3E` (`salescon_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_detail_sales_contract`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_session_store`
--

CREATE TABLE `s_detail_session_store` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `begin_amount` int(11) NOT NULL,
  `end_amount` int(11) default NULL,
  `item_id` bigint(20) NOT NULL,
  `sess_store_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKA7658ED68C68AE60` (`sess_store_id`),
  KEY `FKA7658ED6FC2B26F1` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_session_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_exchange_store_form`
--

CREATE TABLE `s_exchange_store_form` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `created_date` datetime NOT NULL,
  `from_user` varchar(32) NOT NULL,
  `received_date` datetime default NULL,
  `responsible_user` varchar(32) NOT NULL,
  `sent_date` datetime default NULL,
  `status` varchar(255) NOT NULL,
  `to_user` varchar(32) NOT NULL,
  `from_store_id` bigint(20) NOT NULL,
  `to_store_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK4C5BAB32406CD26F` (`to_store_id`),
  KEY `FK4C5BAB32E4B2AE20` (`from_store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_exchange_store_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_export_store_form`
--

CREATE TABLE `s_export_store_form` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `created_date` datetime NOT NULL,
  `status` varchar(255) NOT NULL,
  `contact_id` bigint(20) NOT NULL,
  `invoice_id` bigint(20) NOT NULL,
  `store_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK4EDDAE01B1905696` (`invoice_id`),
  KEY `FK4EDDAE01F4FC45F2` (`contact_id`),
  KEY `FK4EDDAE01D78E952B` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_export_store_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_goods`
--

CREATE TABLE `s_goods` (
  `alert_quantity` bigint(20) default NULL,
  `model` varchar(32) default NULL,
  `goods_id` bigint(20) NOT NULL,
  `main_uom_id` bigint(20) NOT NULL,
  `manufacturer_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`goods_id`),
  KEY `FK6B68988AB92D1CE6` (`main_uom_id`),
  KEY `FK6B68988A8491A21C` (`goods_id`),
  KEY `FK6B68988AEE747F31` (`manufacturer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_goods`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_invoice`
--

CREATE TABLE `s_invoice` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `created_date` datetime NOT NULL,
  `currency` varchar(255) NOT NULL,
  `invoice_number` varchar(32) NOT NULL,
  `money_after_tax` double NOT NULL,
  `money_before_tax` double NOT NULL,
  `money_of_tax` double NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `invoice_type` varchar(32) NOT NULL,
  `contact_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK9C23C761F4FC45F2` (`contact_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `s_invoice`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_item`
--

CREATE TABLE `s_item` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `base_sell_price` double NOT NULL,
  `currency` varchar(255) NOT NULL,
  `sum_uom_name` varchar(128) default NULL,
  `product_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKC9A9835F6E80D9E3` (`product_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_item`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_itemproperty_value`
--

CREATE TABLE `s_itemproperty_value` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `element_value` varchar(128) default NULL,
  `element_id` bigint(20) default NULL,
  `item_id` bigint(20) NOT NULL,
  `property_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK72410FA65E2CA132` (`property_id`),
  KEY `FK72410FA6FC2B26F1` (`item_id`),
  KEY `FK72410FA65D887A0D` (`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_itemproperty_value`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_item_origin_price`
--

CREATE TABLE `s_item_origin_price` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `currency` varchar(3) NOT NULL,
  `original_price` double NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK3F5BE7D0FC2B26F1` (`item_id`),
  KEY `FK3F5BE7D0E0E870AA` (`supplier_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_item_origin_price`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_item_price`
--

CREATE TABLE `s_item_price` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `currency` varchar(255) NOT NULL,
  `sell_price` double NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `contacttype_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK41275469FC2B26F1` (`item_id`),
  KEY `FK41275469DB620D96` (`contacttype_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_item_price`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_manufacturer`
--

CREATE TABLE `s_manufacturer` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `symbol_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK83DF201D3B7F3A89` (`symbol_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_manufacturer`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_package`
--

CREATE TABLE `s_package` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `max_total_item_amount` int(11) NOT NULL,
  `min_total_item_amount` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_package`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_package_line`
--

CREATE TABLE `s_package_line` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `max_item_amount` int(11) NOT NULL,
  `min_item_amount` int(11) NOT NULL,
  `optional` bit(1) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `package_id` bigint(20) NOT NULL,
  `parentpackline_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK895CEC99FC2B26F1` (`item_id`),
  KEY `FK895CEC9928D37186` (`parentpackline_id`),
  KEY `FK895CEC99D556929E` (`package_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_package_line`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_package_line_item_price`
--

CREATE TABLE `s_package_line_item_price` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `currency` varchar(255) NOT NULL,
  `sell_price` double NOT NULL,
  `package_line_id` bigint(20) NOT NULL,
  `contact_type_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK6F1D74839FF4C097` (`contact_type_id`),
  KEY `FK6F1D7483F07C1DB0` (`package_line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_package_line_item_price`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_partner`
--

CREATE TABLE `s_partner` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `comment` longtext,
  `debit_limit` double default NULL,
  `is_active` bit(1) NOT NULL,
  `is_customer` bit(1) default NULL,
  `is_employee` bit(1) default NULL,
  `is_supplier` bit(1) default NULL,
  `name` varchar(128) NOT NULL,
  `title` int(11) default NULL,
  `website` longtext,
  `unit_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKF80A60FCD0AC218F` (`unit_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `s_partner`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_partner_category`
--

CREATE TABLE `s_partner_category` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `is_active` bit(1) NOT NULL,
  `name` varchar(128) default NULL,
  `parent_category_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK7E54E8E15A53995D` (`parent_category_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_partner_category`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_partner_partner_category`
--

CREATE TABLE `s_partner_partner_category` (
  `partner_id` bigint(20) NOT NULL,
  `partner_category_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`partner_id`,`partner_category_id`),
  KEY `FK42688B78F6C7EC4A` (`partner_id`),
  KEY `FK42688B7849A2307B` (`partner_category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_partner_partner_category`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_product`
--

CREATE TABLE `s_product` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `description` varchar(128) default NULL,
  `name` varchar(128) NOT NULL,
  `producttype_id` bigint(20) NOT NULL,
  `uploadfile_id` bigint(20) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK14DB5123FE9D5F04` (`uploadfile_id`),
  KEY `FK14DB5123A01C0F43` (`producttype_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `s_product`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_productproperty_element`
--

CREATE TABLE `s_productproperty_element` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `element_value` varchar(128) NOT NULL,
  `property_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK756A9B55E2CA132` (`property_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `s_productproperty_element`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_product_property`
--

CREATE TABLE `s_product_property` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `property_type` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `s_product_property`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_product_type`
--

CREATE TABLE `s_product_type` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `name` varchar(128) NOT NULL,
  `product_family_type` varchar(32) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `s_product_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_sales_contract`
--

CREATE TABLE `s_sales_contract` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `currency` varchar(255) NOT NULL,
  `datetime_contract` datetime NOT NULL,
  `money_after_tax` double NOT NULL,
  `money_before_tax` double NOT NULL,
  `money_of_tax` double NOT NULL,
  `status` varchar(255) NOT NULL,
  `supplier_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK20FE8031E0E870AA` (`supplier_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_sales_contract`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_service`
--

CREATE TABLE `s_service` (
  `service_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`service_id`),
  KEY `FK9D914AE99F36425D` (`service_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_service`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_session_store`
--

CREATE TABLE `s_session_store` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `end_date` datetime default NULL,
  `refer_month` int(11) NOT NULL,
  `refer_quarter` int(11) NOT NULL,
  `refer_year` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `store_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FK3AD3A32CD78E952B` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_session_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_shipment`
--

CREATE TABLE `s_shipment` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `currency` varchar(255) NOT NULL,
  `money` double NOT NULL,
  `status` varchar(255) NOT NULL,
  `exportstore_id` bigint(20) NOT NULL,
  `shipment_type_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `FKA39C73E686A6F2AF` (`exportstore_id`),
  KEY `FKA39C73E683D11B81` (`shipment_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_shipment`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_shipment_type`
--

CREATE TABLE `s_shipment_type` (
  `id` bigint(20) NOT NULL auto_increment,
  `dte_log_i` datetime default NULL,
  `dte_log_lu` datetime default NULL,
  `usr_log_i` varchar(255) default NULL,
  `usr_log_lu` varchar(255) default NULL,
  `version` bigint(20) default NULL,
  `code` varchar(32) NOT NULL,
  `active` bit(1) NOT NULL,
  `base_price` double NOT NULL,
  `currency` varchar(255) NOT NULL,
  `name` varchar(128) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_shipment_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_supplier`
--

CREATE TABLE `s_supplier` (
  `address` longtext,
  `email` varchar(255) default NULL,
  `fax` varchar(20) default NULL,
  `phone` varchar(20) default NULL,
  `position` longtext,
  `representer` longtext,
  `sex` bit(1) default NULL,
  `supplier_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`supplier_id`),
  KEY `FK5F3EFC18CF7980C6` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_supplier`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_voucher`
--

CREATE TABLE `s_voucher` (
  `currency_code` varchar(255) default NULL,
  `min_amount` bigint(20) default NULL,
  `voucher_id` bigint(20) NOT NULL,
  PRIMARY KEY  (`voucher_id`),
  KEY `FK4D7612E28FB94BC4` (`voucher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_voucher`
--


--
-- Constraints for dumped tables
--

--
-- Constraints for table `config_unit_of_measure`
--
ALTER TABLE `config_unit_of_measure`
  ADD CONSTRAINT `FK320EC994CBA000F7` FOREIGN KEY (`uom_category_id`) REFERENCES `config_uom_category` (`id`);

--
-- Constraints for table `config_uom_category`
--
ALTER TABLE `config_uom_category`
  ADD CONSTRAINT `FKAA21F08792B9DA5A` FOREIGN KEY (`parentUomCategory_id`) REFERENCES `config_uom_category` (`id`);

--
-- Constraints for table `finace_payment_content`
--
ALTER TABLE `finace_payment_content`
  ADD CONSTRAINT `FK575A39993D390FD9` FOREIGN KEY (`parent_id`) REFERENCES `finace_payment_content` (`id`);

--
-- Constraints for table `finance_contract_payment`
--
ALTER TABLE `finance_contract_payment`
  ADD CONSTRAINT `FK2677399EFA3AB9EF` FOREIGN KEY (`sales_contract_id`) REFERENCES `s_sales_contract` (`id`),
  ADD CONSTRAINT `FK2677399E92F51291` FOREIGN KEY (`contract_payment_id`) REFERENCES `finance_payment` (`id`);

--
-- Constraints for table `finance_payment`
--
ALTER TABLE `finance_payment`
  ADD CONSTRAINT `FK2757AEA1F6C7EC4A` FOREIGN KEY (`partner_id`) REFERENCES `s_partner` (`id`),
  ADD CONSTRAINT `FK2757AEA11BBF2162` FOREIGN KEY (`operator_id`) REFERENCES `operator_operator` (`operator_id`),
  ADD CONSTRAINT `FK2757AEA1984FE183` FOREIGN KEY (`payment_content_id`) REFERENCES `finace_payment_content` (`id`);

--
-- Constraints for table `operator_operator`
--
ALTER TABLE `operator_operator`
  ADD CONSTRAINT `FKCD5EA9DFBD0FAB6D` FOREIGN KEY (`operator_id`) REFERENCES `au_user` (`id`);

--
-- Constraints for table `operator_sale_target`
--
ALTER TABLE `operator_sale_target`
  ADD CONSTRAINT `FK7B3ED16E8DEFC5F4` FOREIGN KEY (`id_stall`) REFERENCES `operator_stall` (`id`);

--
-- Constraints for table `operator_stall`
--
ALTER TABLE `operator_stall`
  ADD CONSTRAINT `FKA0114445A866F1B9` FOREIGN KEY (`manager_id`) REFERENCES `operator_operator` (`operator_id`);

--
-- Constraints for table `o_stall_user`
--
ALTER TABLE `o_stall_user`
  ADD CONSTRAINT `FKEE152EDA7632DB3B` FOREIGN KEY (`id_user`) REFERENCES `operator_operator` (`operator_id`),
  ADD CONSTRAINT `FKEE152EDA8DEFC5F4` FOREIGN KEY (`id_stall`) REFERENCES `operator_stall` (`id`);

--
-- Constraints for table `store_detail_import`
--
ALTER TABLE `store_detail_import`
  ADD CONSTRAINT `FK60BAB875DC4E0F67` FOREIGN KEY (`form_id`) REFERENCES `store_import_store_form` (`id`),
  ADD CONSTRAINT `FK60BAB875FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `store_import_store_form`
--
ALTER TABLE `store_import_store_form`
  ADD CONSTRAINT `FK8633965E2D500E3E` FOREIGN KEY (`salescon_id`) REFERENCES `s_sales_contract` (`id`),
  ADD CONSTRAINT `FK8633965E14F6666B` FOREIGN KEY (`shipPriceType_id`) REFERENCES `store_ship_price_type` (`id`),
  ADD CONSTRAINT `FK8633965E965BD837` FOREIGN KEY (`receiver_id`) REFERENCES `operator_operator` (`operator_id`),
  ADD CONSTRAINT `FK8633965ED78E952B` FOREIGN KEY (`store_id`) REFERENCES `store_store` (`id`);

--
-- Constraints for table `store_ship_price`
--
ALTER TABLE `store_ship_price`
  ADD CONSTRAINT `FKDCC707A426E21DDF` FOREIGN KEY (`ship_price_type_id`) REFERENCES `store_ship_price_type` (`id`);

--
-- Constraints for table `store_store`
--
ALTER TABLE `store_store`
  ADD CONSTRAINT `FKA7726803A866F1B9` FOREIGN KEY (`manager_id`) REFERENCES `operator_operator` (`operator_id`);

--
-- Constraints for table `s_article`
--
ALTER TABLE `s_article`
  ADD CONSTRAINT `FKFBA6512AD78E952B` FOREIGN KEY (`store_id`) REFERENCES `store_store` (`id`),
  ADD CONSTRAINT `FKFBA6512AFC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_check_store`
--
ALTER TABLE `s_check_store`
  ADD CONSTRAINT `FK4205A51ED78E952B` FOREIGN KEY (`store_id`) REFERENCES `store_store` (`id`);

--
-- Constraints for table `s_contact_debt`
--
ALTER TABLE `s_contact_debt`
  ADD CONSTRAINT `FK4565219EF6C7EC4A` FOREIGN KEY (`partner_id`) REFERENCES `s_partner` (`id`);

--
-- Constraints for table `s_contact_shop`
--
ALTER TABLE `s_contact_shop`
  ADD CONSTRAINT `FK456C00011BBADF6A` FOREIGN KEY (`customer_id`) REFERENCES `s_customer` (`customer_id`);

--
-- Constraints for table `s_customer`
--
ALTER TABLE `s_customer`
  ADD CONSTRAINT `FKE684822A72F46197` FOREIGN KEY (`bank_account_id`) REFERENCES `config_bank_account` (`id`),
  ADD CONSTRAINT `FKE684822A83066974` FOREIGN KEY (`customer_id`) REFERENCES `s_partner` (`id`);

--
-- Constraints for table `s_detail_exchange_store`
--
ALTER TABLE `s_detail_exchange_store`
  ADD CONSTRAINT `FKCB7726A7F5E3304F` FOREIGN KEY (`exchangestore_id`) REFERENCES `s_exchange_store_form` (`id`),
  ADD CONSTRAINT `FKCB7726A7FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_detail_export_store`
--
ALTER TABLE `s_detail_export_store`
  ADD CONSTRAINT `FKFA5CBF886A6F2AF` FOREIGN KEY (`exportstore_id`) REFERENCES `s_export_store_form` (`id`),
  ADD CONSTRAINT `FKFA5CBF8FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_detail_invoice`
--
ALTER TABLE `s_detail_invoice`
  ADD CONSTRAINT `FKA8D23A8B70EF79A3` FOREIGN KEY (`packageline_id`) REFERENCES `s_package_line` (`id`),
  ADD CONSTRAINT `FKA8D23A8BB1905696` FOREIGN KEY (`invoice_id`) REFERENCES `s_invoice` (`id`),
  ADD CONSTRAINT `FKA8D23A8BFC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_detail_sales_contract`
--
ALTER TABLE `s_detail_sales_contract`
  ADD CONSTRAINT `FK46AA09C72D500E3E` FOREIGN KEY (`salescon_id`) REFERENCES `s_sales_contract` (`id`),
  ADD CONSTRAINT `FK46AA09C7FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_detail_session_store`
--
ALTER TABLE `s_detail_session_store`
  ADD CONSTRAINT `FKA7658ED6FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`),
  ADD CONSTRAINT `FKA7658ED68C68AE60` FOREIGN KEY (`sess_store_id`) REFERENCES `s_session_store` (`id`);

--
-- Constraints for table `s_exchange_store_form`
--
ALTER TABLE `s_exchange_store_form`
  ADD CONSTRAINT `FK4C5BAB32E4B2AE20` FOREIGN KEY (`from_store_id`) REFERENCES `store_store` (`id`),
  ADD CONSTRAINT `FK4C5BAB32406CD26F` FOREIGN KEY (`to_store_id`) REFERENCES `store_store` (`id`);

--
-- Constraints for table `s_export_store_form`
--
ALTER TABLE `s_export_store_form`
  ADD CONSTRAINT `FK4EDDAE01D78E952B` FOREIGN KEY (`store_id`) REFERENCES `store_store` (`id`),
  ADD CONSTRAINT `FK4EDDAE01B1905696` FOREIGN KEY (`invoice_id`) REFERENCES `s_invoice` (`id`),
  ADD CONSTRAINT `FK4EDDAE01F4FC45F2` FOREIGN KEY (`contact_id`) REFERENCES `s_partner` (`id`);

--
-- Constraints for table `s_goods`
--
ALTER TABLE `s_goods`
  ADD CONSTRAINT `FK6B68988AEE747F31` FOREIGN KEY (`manufacturer_id`) REFERENCES `s_manufacturer` (`id`),
  ADD CONSTRAINT `FK6B68988A8491A21C` FOREIGN KEY (`goods_id`) REFERENCES `s_product` (`id`),
  ADD CONSTRAINT `FK6B68988AB92D1CE6` FOREIGN KEY (`main_uom_id`) REFERENCES `config_unit_of_measure` (`id`);

--
-- Constraints for table `s_invoice`
--
ALTER TABLE `s_invoice`
  ADD CONSTRAINT `FK9C23C761F4FC45F2` FOREIGN KEY (`contact_id`) REFERENCES `s_partner` (`id`);

--
-- Constraints for table `s_item`
--
ALTER TABLE `s_item`
  ADD CONSTRAINT `FKC9A9835F6E80D9E3` FOREIGN KEY (`product_id`) REFERENCES `s_product` (`id`);

--
-- Constraints for table `s_itemproperty_value`
--
ALTER TABLE `s_itemproperty_value`
  ADD CONSTRAINT `FK72410FA65D887A0D` FOREIGN KEY (`element_id`) REFERENCES `s_productproperty_element` (`id`),
  ADD CONSTRAINT `FK72410FA65E2CA132` FOREIGN KEY (`property_id`) REFERENCES `s_product_property` (`id`),
  ADD CONSTRAINT `FK72410FA6FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_item_origin_price`
--
ALTER TABLE `s_item_origin_price`
  ADD CONSTRAINT `FK3F5BE7D0E0E870AA` FOREIGN KEY (`supplier_id`) REFERENCES `s_supplier` (`supplier_id`),
  ADD CONSTRAINT `FK3F5BE7D0FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_item_price`
--
ALTER TABLE `s_item_price`
  ADD CONSTRAINT `FK41275469DB620D96` FOREIGN KEY (`contacttype_id`) REFERENCES `s_partner_category` (`id`),
  ADD CONSTRAINT `FK41275469FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_manufacturer`
--
ALTER TABLE `s_manufacturer`
  ADD CONSTRAINT `FK83DF201D3B7F3A89` FOREIGN KEY (`symbol_id`) REFERENCES `config_upload_file` (`id`);

--
-- Constraints for table `s_package_line`
--
ALTER TABLE `s_package_line`
  ADD CONSTRAINT `FK895CEC99D556929E` FOREIGN KEY (`package_id`) REFERENCES `s_package` (`id`),
  ADD CONSTRAINT `FK895CEC9928D37186` FOREIGN KEY (`parentpackline_id`) REFERENCES `s_package_line` (`id`),
  ADD CONSTRAINT `FK895CEC99FC2B26F1` FOREIGN KEY (`item_id`) REFERENCES `s_item` (`id`);

--
-- Constraints for table `s_package_line_item_price`
--
ALTER TABLE `s_package_line_item_price`
  ADD CONSTRAINT `FK6F1D7483F07C1DB0` FOREIGN KEY (`package_line_id`) REFERENCES `s_package_line` (`id`),
  ADD CONSTRAINT `FK6F1D74839FF4C097` FOREIGN KEY (`contact_type_id`) REFERENCES `s_partner_category` (`id`);

--
-- Constraints for table `s_partner`
--
ALTER TABLE `s_partner`
  ADD CONSTRAINT `FKF80A60FCD0AC218F` FOREIGN KEY (`unit_id`) REFERENCES `config_unit_of_measure` (`id`);

--
-- Constraints for table `s_partner_category`
--
ALTER TABLE `s_partner_category`
  ADD CONSTRAINT `FK7E54E8E15A53995D` FOREIGN KEY (`parent_category_id`) REFERENCES `s_partner_category` (`id`);

--
-- Constraints for table `s_partner_partner_category`
--
ALTER TABLE `s_partner_partner_category`
  ADD CONSTRAINT `FK42688B7849A2307B` FOREIGN KEY (`partner_category_id`) REFERENCES `s_partner_category` (`id`),
  ADD CONSTRAINT `FK42688B78F6C7EC4A` FOREIGN KEY (`partner_id`) REFERENCES `s_partner` (`id`);

--
-- Constraints for table `s_product`
--
ALTER TABLE `s_product`
  ADD CONSTRAINT `FK14DB5123A01C0F43` FOREIGN KEY (`producttype_id`) REFERENCES `s_product_type` (`id`),
  ADD CONSTRAINT `FK14DB5123FE9D5F04` FOREIGN KEY (`uploadfile_id`) REFERENCES `config_upload_file` (`id`);

--
-- Constraints for table `s_productproperty_element`
--
ALTER TABLE `s_productproperty_element`
  ADD CONSTRAINT `FK756A9B55E2CA132` FOREIGN KEY (`property_id`) REFERENCES `s_product_property` (`id`);

--
-- Constraints for table `s_sales_contract`
--
ALTER TABLE `s_sales_contract`
  ADD CONSTRAINT `FK20FE8031E0E870AA` FOREIGN KEY (`supplier_id`) REFERENCES `s_supplier` (`supplier_id`);

--
-- Constraints for table `s_service`
--
ALTER TABLE `s_service`
  ADD CONSTRAINT `FK9D914AE99F36425D` FOREIGN KEY (`service_id`) REFERENCES `s_product` (`id`);

--
-- Constraints for table `s_session_store`
--
ALTER TABLE `s_session_store`
  ADD CONSTRAINT `FK3AD3A32CD78E952B` FOREIGN KEY (`store_id`) REFERENCES `store_store` (`id`);

--
-- Constraints for table `s_shipment`
--
ALTER TABLE `s_shipment`
  ADD CONSTRAINT `FKA39C73E683D11B81` FOREIGN KEY (`shipment_type_id`) REFERENCES `s_shipment_type` (`id`),
  ADD CONSTRAINT `FKA39C73E686A6F2AF` FOREIGN KEY (`exportstore_id`) REFERENCES `s_export_store_form` (`id`);

--
-- Constraints for table `s_supplier`
--
ALTER TABLE `s_supplier`
  ADD CONSTRAINT `FK5F3EFC18CF7980C6` FOREIGN KEY (`supplier_id`) REFERENCES `s_partner` (`id`);

--
-- Constraints for table `s_voucher`
--
ALTER TABLE `s_voucher`
  ADD CONSTRAINT `FK4D7612E28FB94BC4` FOREIGN KEY (`voucher_id`) REFERENCES `s_product` (`id`);
