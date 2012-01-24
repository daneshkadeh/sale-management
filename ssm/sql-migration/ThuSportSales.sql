-- phpMyAdmin SQL Dump
-- version 2.11.0
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 25, 2012 at 02:07 AM
-- Server version: 5.0.45
-- PHP Version: 5.2.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `solution_testssm`
--

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
  `advantage_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `at_advantage_buyitem`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_advantage_buypackage`
--

CREATE TABLE `at_advantage_buypackage` (
  `advantage_id` int(11) NOT NULL,
  `package_id` int(11) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `at_advantage_buypackage`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_advantage_giftitem`
--

CREATE TABLE `at_advantage_giftitem` (
  `advantage_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `at_advantage_giftitem`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_advantage_giftpackage`
--

CREATE TABLE `at_advantage_giftpackage` (
  `advantage_id` int(11) NOT NULL,
  `package_id` int(11) NOT NULL,
  PRIMARY KEY  (`advantage_id`,`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `at_advantage_giftpackage`
--


-- --------------------------------------------------------

--
-- Table structure for table `at_item_uom`
--

CREATE TABLE `at_item_uom` (
  `item_id` int(11) NOT NULL,
  `uom_id` int(11) NOT NULL,
  PRIMARY KEY  (`item_id`,`uom_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `at_item_uom`
--


-- --------------------------------------------------------

--
-- Table structure for table `au_role`
--

CREATE TABLE `au_role` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(32) collate utf8_bin NOT NULL,
  `isEnable` tinyint(1) NOT NULL default '1',
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=10 ;

--
-- Dumping data for table `au_role`
--

INSERT INTO `au_role` (`id`, `code`, `name`, `isEnable`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '1', 'ROLE_ADMIN', 1, 'DEFAULT_USER', '2011-12-01 11:17:56', 'admin', '2012-01-24 02:45:00', 70),
(2, '2', 'ROLE_MANAGER', 1, 'DEFAULT_USER', '2011-12-01 11:40:13', 'admin', '2012-01-21 23:30:49', 16),
(3, '3', 'ROLE_USER', 1, 'DEFAULT_USER', '2011-12-01 11:40:24', 'admin', '2012-01-22 22:08:30', 12),
(4, '4', 'ROLE_DEVELOPER', 1, 'DEFAULT_USER', '2011-12-12 22:23:17', 'DEFAULT_USER', '2011-12-25 22:09:26', 1),
(5, '5', 'ROLE_CUSTOMER', 1, 'DEFAULT_USER', '2011-12-25 14:43:20', 'DEFAULT_USER', '2011-12-25 22:09:32', 4),
(6, '6', 'ROLE_PM', 1, 'DEFAULT_USER', '2011-12-25 14:53:29', 'DEFAULT_USER', '2011-12-25 22:09:37', 4),
(7, '7', 'ROLE_TEST', 1, 'DEFAULT_USER', '2011-12-25 22:21:30', 'DEFAULT_USER', '2011-12-26 13:27:16', 4),
(8, '8', 'ROLE_DEPLOY', 1, 'DEFAULT_USER', '2011-12-25 22:42:23', 'DEFAULT_USER', '2011-12-25 22:49:29', 6),
(9, '9', 'ROLE_DEPLOY2', 1, 'DEFAULT_USER', '2011-12-25 22:46:31', 'DEFAULT_USER', '2011-12-25 22:46:31', 0);

-- --------------------------------------------------------

--
-- Table structure for table `au_user`
--

CREATE TABLE `au_user` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `username` varchar(32) collate utf8_bin NOT NULL,
  `password` varchar(128) collate utf8_bin default NULL,
  `isAccountNonExpired` tinyint(1) NOT NULL default '1',
  `isAccountNonLocked` tinyint(1) NOT NULL default '1',
  `isCredentialsNonExpired` tinyint(1) NOT NULL default '1',
  `isEnabled` tinyint(1) NOT NULL default '1',
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=5 ;

--
-- Dumping data for table `au_user`
--

INSERT INTO `au_user` (`id`, `code`, `username`, `password`, `isAccountNonExpired`, `isAccountNonLocked`, `isCredentialsNonExpired`, `isEnabled`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '1', 'admin', 'admin', 1, 1, 1, 1, 'DEFAULT_USER', '2011-12-12 22:04:44', 'admin', '2012-01-22 22:08:30', 19),
(2, '2', 'user1', 'user1', 1, 1, 1, 0, 'DEFAULT_USER', '2011-12-12 22:22:55', 'admin', '2012-01-21 23:30:49', 6),
(3, '3', 'user2', 'user2', 1, 1, 1, 1, 'admin', '2011-12-26 15:18:17', 'admin', '2011-12-26 15:18:17', 0),
(4, '4', 'user4', 'user4', 1, 1, 1, 1, 'admin', '2012-01-24 02:45:00', 'admin', '2012-01-24 02:45:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `au_user_role`
--

CREATE TABLE `au_user_role` (
  `id_user` int(11) NOT NULL,
  `id_role` int(11) NOT NULL,
  PRIMARY KEY  (`id_user`,`id_role`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `au_user_role`
--

INSERT INTO `au_user_role` (`id_user`, `id_role`) VALUES
(1, 1),
(1, 3),
(2, 2),
(3, 1),
(4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `o_sale_target`
--

CREATE TABLE `o_sale_target` (
  `id` int(11) NOT NULL auto_increment,
  `id_stall` int(11) NOT NULL,
  `year` int(4) NOT NULL,
  `month1` double default NULL,
  `month2` double default NULL,
  `month3` double default NULL,
  `month4` double default NULL,
  `month5` double default NULL,
  `month6` double default NULL,
  `month7` double default NULL,
  `month8` double default NULL,
  `month9` double default NULL,
  `month10` double default NULL,
  `month11` double default NULL,
  `month12` double default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=4 ;

--
-- Dumping data for table `o_sale_target`
--

INSERT INTO `o_sale_target` (`id`, `id_stall`, `year`, `month1`, `month2`, `month3`, `month4`, `month5`, `month6`, `month7`, `month8`, `month9`, `month10`, `month11`, `month12`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, 4, 2012, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'admin', '2012-01-21 23:20:29', 'admin', '2012-01-21 23:20:29', 0),
(2, 5, 2012, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'admin', '2012-01-21 23:29:37', 'admin', '2012-01-21 23:30:49', 4),
(3, 6, 2012, 1, 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'admin', '2012-01-22 22:08:30', 'admin', '2012-01-22 22:08:30', 0);

-- --------------------------------------------------------

--
-- Table structure for table `o_stall`
--

CREATE TABLE `o_stall` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(50) collate utf8_bin NOT NULL,
  `manager_id` int(11) NOT NULL,
  `is_active` tinyint(1) NOT NULL default '1',
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=7 ;

--
-- Dumping data for table `o_stall`
--

INSERT INTO `o_stall` (`id`, `code`, `name`, `manager_id`, `is_active`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(4, '4', 'Stall4', 2, 1, 'admin', '2012-01-21 23:20:29', 'admin', '2012-01-21 23:20:29', 0),
(5, '5', 'Stall5', 3, 1, 'admin', '2012-01-21 23:29:37', 'admin', '2012-01-21 23:30:49', 4),
(6, '6', 'Stall6', 1, 1, 'admin', '2012-01-22 22:08:29', 'admin', '2012-01-22 22:08:29', 0);

-- --------------------------------------------------------

--
-- Table structure for table `o_stall_user`
--

CREATE TABLE `o_stall_user` (
  `id_stall` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  PRIMARY KEY  (`id_stall`,`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `o_stall_user`
--

INSERT INTO `o_stall_user` (`id_stall`, `id_user`) VALUES
(4, 1),
(5, 1),
(5, 2),
(6, 1);

-- --------------------------------------------------------

--
-- Table structure for table `s_advantage`
--

CREATE TABLE `s_advantage` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `discount_percent` int(11) NOT NULL default '0',
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_advantage`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_article`
--

CREATE TABLE `s_article` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `barcode` varchar(32) collate utf8_bin default NULL,
  `first_maintain_date` datetime default NULL,
  `second_maintain_date` datetime default NULL,
  `store_id` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_barcode` (`barcode`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_article`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_bank`
--

CREATE TABLE `s_bank` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `bank_name` varchar(128) collate utf8_bin default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_bank_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_bank`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_bank_account`
--

CREATE TABLE `s_bank_account` (
  `id` int(11) NOT NULL auto_increment,
  `bank_id` int(11) NOT NULL,
  `account_number` varchar(32) collate utf8_bin NOT NULL,
  `account_name` varchar(32) collate utf8_bin default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_bank_account` (`bank_id`,`account_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_bank_account`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_basic_information`
--

CREATE TABLE `s_basic_information` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) character set utf8 collate utf8_unicode_ci NOT NULL,
  `company_name` varchar(250) character set utf8 collate utf8_unicode_ci NOT NULL,
  `agent` varchar(250) character set utf8 collate utf8_unicode_ci NOT NULL,
  `position` varchar(100) character set utf8 collate utf8_unicode_ci NOT NULL,
  `upload_file_id` int(11) default NULL,
  `company_address` varchar(250) character set utf8 collate utf8_unicode_ci NOT NULL,
  `tel` varchar(20) character set utf8 collate utf8_unicode_ci NOT NULL,
  `fax` varchar(20) character set utf8 collate utf8_unicode_ci NOT NULL,
  `website` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `bank_name` varchar(250) character set utf8 collate utf8_unicode_ci NOT NULL,
  `bank_address` varchar(250) character set utf8 collate utf8_unicode_ci NOT NULL,
  `usd_acct_number` varchar(100) NOT NULL,
  `vnd_acct_number` varchar(100) NOT NULL,
  `benefice_name` varchar(100) character set utf8 collate utf8_unicode_ci NOT NULL,
  `def_currency_id` int(11) NOT NULL,
  `def_detail_inv_num` int(3) NOT NULL,
  `def_page_row_num` int(3) NOT NULL,
  `def_payment_method` int(3) NOT NULL,
  `order_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `sales_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `sales_refund_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `pur_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `pur_refund_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `spon_contract_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `movement_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `export_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `import_inv_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `payment_bill_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `receipt_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `promotion_code_rule` varchar(50) character set utf8 collate utf8_unicode_ci NOT NULL,
  `backup_path` varchar(250) NOT NULL,
  `sell_on_credit` int(1) default NULL,
  `digit_adter_comma_quan` int(2) NOT NULL,
  `digit_adter_comma_price` int(2) NOT NULL,
  `digit_adter_comma_rate` int(2) NOT NULL,
  `thousands_Separator` varchar(1) NOT NULL,
  `odd_Separator` varchar(1) NOT NULL,
  `usr_log_i` varchar(32) character set utf8 collate utf8_unicode_ci NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_basic_information`
--

INSERT INTO `s_basic_information` (`id`, `code`, `company_name`, `agent`, `position`, `upload_file_id`, `company_address`, `tel`, `fax`, `website`, `email`, `bank_name`, `bank_address`, `usd_acct_number`, `vnd_acct_number`, `benefice_name`, `def_currency_id`, `def_detail_inv_num`, `def_page_row_num`, `def_payment_method`, `order_inv_code_rule`, `sales_inv_code_rule`, `sales_refund_inv_code_rule`, `pur_inv_code_rule`, `pur_refund_inv_code_rule`, `spon_contract_code_rule`, `movement_inv_code_rule`, `export_inv_code_rule`, `import_inv_code_rule`, `payment_bill_code_rule`, `receipt_code_rule`, `promotion_code_rule`, `backup_path`, `sell_on_credit`, `digit_adter_comma_quan`, `digit_adter_comma_price`, `digit_adter_comma_rate`, `thousands_Separator`, `odd_Separator`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '1', 'THUSPORT', 'MS Hien', 'T?ng gi', NULL, '569A, Nguy?n ', '(848) 38220541', '84 - 8 - 38220542', 'www.thusport.com', 'thusport@yahoo.com', 'NG', '569A, Nguy?n ', '1602 2010 19836', '1602 2010 19820', 'THU SPORTS', 1, 10, 10, 1, 'H', 'H', 'XTNCC_', 'NHKTL_', 'BK_', 'H', 'CK_', 'XK_', 'NK_', 'PC_', 'PT_', 'KM_', 'D:\\Download', NULL, 5, 5, 2, '.', ',', 'DEFAULT_USER', '2011-12-18 16:40:31', 'DEFAULT_USER', '2011-12-18 16:40:31', 0);

-- --------------------------------------------------------

--
-- Table structure for table `s_check_store`
--

CREATE TABLE `s_check_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `date_start_check` datetime NOT NULL,
  `date_end_check` datetime default NULL,
  `responsible_user` varchar(32) collate utf8_bin NOT NULL,
  `status` varchar(16) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_store_date` (`store_id`,`date_start_check`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_check_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_contact_debt`
--

CREATE TABLE `s_contact_debt` (
  `id` int(11) NOT NULL auto_increment,
  `partner_id` int(11) NOT NULL,
  `debt_money` double NOT NULL,
  `currency_id` int(11) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_contact_currency` (`partner_id`,`currency_id`),
  KEY `idx_contact_id` (`partner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_contact_debt`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_contact_shop`
--

CREATE TABLE `s_contact_shop` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `customer_id` int(11) NOT NULL,
  `address` varchar(256) collate utf8_bin default NULL,
  `phone` varchar(32) collate utf8_bin default NULL,
  `fix_phone` varchar(32) collate utf8_bin default NULL,
  `fax` varchar(32) collate utf8_bin default NULL,
  `email` varchar(64) collate utf8_bin default NULL,
  `remark` varchar(256) collate utf8_bin default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_contact_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_contact_shop`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_coupon`
--

CREATE TABLE `s_coupon` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `coupon_name` varchar(32) collate utf8_bin NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_coupon`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_coupon_item`
--

CREATE TABLE `s_coupon_item` (
  `id` int(11) NOT NULL auto_increment,
  `coupon_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `contacttype_id` int(11) NOT NULL,
  `base_price` int(11) NOT NULL,
  `coupon_price` int(11) NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_coupon_item` (`coupon_id`,`item_id`,`contacttype_id`),
  KEY `idx_coupon_id` (`coupon_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_coupon_item`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_currency`
--

CREATE TABLE `s_currency` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(20) collate utf8_bin NOT NULL,
  `symbol` varchar(10) collate utf8_bin default NULL,
  `active` tinyint(4) NOT NULL default '0',
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `s_currency`
--

INSERT INTO `s_currency` (`id`, `code`, `name`, `symbol`, `active`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '00001', 'VND', 'd', 1, 'DEFAULT_USER', '2011-12-14 22:51:15', 'DEFAULT_USER', '2011-12-14 22:54:23', 1),
(2, '0002', 'USD', '$', 1, 'DEFAULT_USER', '2011-12-14 22:54:38', 'DEFAULT_USER', '2011-12-14 22:54:38', 0);

-- --------------------------------------------------------

--
-- Table structure for table `s_customer`
--

CREATE TABLE `s_customer` (
  `customer_id` int(11) NOT NULL,
  `fix_phone` varchar(20) default NULL,
  `mobile_phone` varchar(20) default NULL,
  `fax` varchar(20) default NULL,
  `email` varchar(100) default NULL,
  `address` varchar(256) default NULL,
  `tax_code` varchar(50) default NULL,
  `bank_account_id` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_customer`
--

INSERT INTO `s_customer` (`customer_id`, `fix_phone`, `mobile_phone`, `fax`, `email`, `address`, `tax_code`, `bank_account_id`) VALUES
(3, '123456', '123456', '123456', '123456', '123456', '123456', NULL),
(4, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `s_detail_check_store`
--

CREATE TABLE `s_detail_check_store` (
  `id` int(11) NOT NULL auto_increment,
  `checkstore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `change_amount` int(11) NOT NULL,
  `change_type` varchar(32) collate utf8_bin NOT NULL,
  `remark` varchar(256) collate utf8_bin default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_checkstore_item` (`checkstore_id`,`item_id`),
  KEY `idx_checkstore_id` (`checkstore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_check_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_exchange_store`
--

CREATE TABLE `s_detail_exchange_store` (
  `id` int(11) NOT NULL auto_increment,
  `exchangestore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_exchangestore_item` (`exchangestore_id`,`item_id`),
  KEY `ui_exchangestore_id` (`exchangestore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_exchange_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_export_store`
--

CREATE TABLE `s_detail_export_store` (
  `id` int(11) NOT NULL auto_increment,
  `exportstore_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_export_item` (`exportstore_id`,`item_id`),
  KEY `idx_exportstore_id` (`exportstore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_export_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_import_product`
--

CREATE TABLE `s_detail_import_product` (
  `id` int(11) NOT NULL auto_increment,
  `import_product_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `import_amount` int(11) NOT NULL,
  `remaining_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_import_item` (`import_product_id`,`item_id`),
  KEY `idx_import_product_id` (`import_product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_import_product`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_invoice`
--

CREATE TABLE `s_detail_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11) NOT NULL,
  `packageline_id` int(11) default NULL,
  `item_id` int(11) default NULL,
  `amount` int(11) NOT NULL,
  `price_before_tax` double NOT NULL default '0',
  `price_of_tax` double NOT NULL default '0',
  `price_after_tax` double NOT NULL default '0',
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `detail_invoice_type` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_invoice_item` (`invoice_id`,`item_id`),
  KEY `idx_invoice_id` (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_invoice`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_maintainance`
--

CREATE TABLE `s_detail_maintainance` (
  `id` int(11) NOT NULL auto_increment,
  `maintainance_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` int(11) NOT NULL,
  `total_price` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_maintainance_id` (`maintainance_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_maintainance`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_sales_contract`
--

CREATE TABLE `s_detail_sales_contract` (
  `id` int(11) NOT NULL auto_increment,
  `salescon_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `idx_salescon_item` (`salescon_id`,`item_id`),
  KEY `idx_salescon_id` (`salescon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_sales_contract`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_session_store`
--

CREATE TABLE `s_detail_session_store` (
  `id` int(11) NOT NULL auto_increment,
  `sess_store_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `begin_amount` int(11) NOT NULL,
  `end_amount` int(11) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_sess_store_item` (`sess_store_id`,`item_id`),
  KEY `idx_sess_store_id` (`sess_store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_session_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_store`
--

CREATE TABLE `s_detail_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `sellable_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_store_item` (`store_id`,`item_id`),
  KEY `idx_store_id` (`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_support_form`
--

CREATE TABLE `s_detail_support_form` (
  `id` int(11) NOT NULL auto_increment,
  `support_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `unit_price` double NOT NULL,
  `total_price` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_support_id` (`support_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_support_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_detail_warranty`
--

CREATE TABLE `s_detail_warranty` (
  `id` int(11) NOT NULL auto_increment,
  `warranty_id` int(11) NOT NULL,
  `warranty_date` datetime NOT NULL,
  `description` varchar(256) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_warranty_id` (`warranty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_detail_warranty`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_exchange_rate`
--

CREATE TABLE `s_exchange_rate` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) character set utf8 collate utf8_unicode_ci NOT NULL,
  `update_date` datetime NOT NULL,
  `currency_id` int(11) NOT NULL,
  `rate` int(20) NOT NULL,
  `usr_log_i` varchar(32) NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) character set utf8 collate utf8_unicode_ci NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY  (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_exchange_rate`
--

INSERT INTO `s_exchange_rate` (`id`, `code`, `update_date`, `currency_id`, `rate`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '1', '2011-12-18 00:00:00', 2, 21000, 'DEFAULT_USER', '2011-12-18 22:28:35', 'DEFAULT_USER', '2011-12-18 22:28:35', 0);

-- --------------------------------------------------------

--
-- Table structure for table `s_exchange_store_form`
--

CREATE TABLE `s_exchange_store_form` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `from_store_id` int(11) NOT NULL,
  `to_store_id` int(11) NOT NULL,
  `from_user` varchar(32) collate utf8_bin NOT NULL,
  `to_user` varchar(32) collate utf8_bin NOT NULL,
  `responsible_user` varchar(32) collate utf8_bin NOT NULL,
  `created_date` datetime NOT NULL,
  `sent_date` datetime default NULL,
  `received_date` datetime default NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_code` (`code`),
  KEY `idx_from_store_id` (`from_store_id`),
  KEY `idx_to_store_id` (`to_store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_exchange_store_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_export_store_form`
--

CREATE TABLE `s_export_store_form` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `store_id` int(11) NOT NULL,
  `contact_id` int(11) default NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `ui_invoice_store` (`invoice_id`,`store_id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_export_store_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_import_product_form`
--

CREATE TABLE `s_import_product_form` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `created_date` datetime NOT NULL,
  `store_id` int(11) NOT NULL,
  `salescon_id` int(11) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_code` (`code`),
  KEY `idx_salescon_id` (`salescon_id`),
  KEY `idx_salescon_store` (`salescon_id`,`store_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_import_product_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_invoice`
--

CREATE TABLE `s_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_number` varchar(32) collate utf8_bin NOT NULL,
  `invoice_type` varchar(32) collate utf8_bin NOT NULL,
  `contact_id` int(11) default NULL,
  `created_date` datetime NOT NULL,
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `payment_status` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_invoice_number` (`invoice_number`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_invoice`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_item`
--

CREATE TABLE `s_item` (
  `id` int(11) NOT NULL auto_increment,
  `product_id` int(11) NOT NULL,
  `sum_uom_name` varchar(128) collate utf8_bin default NULL,
  `base_sell_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_item`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_item_origin_price`
--

CREATE TABLE `s_item_origin_price` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `original_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_item_supplier` (`item_id`,`supplier_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_item_origin_price`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_item_price`
--

CREATE TABLE `s_item_price` (
  `id` int(11) NOT NULL auto_increment,
  `item_id` int(11) NOT NULL,
  `contacttype_id` int(11) NOT NULL,
  `sell_price` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_item_contacttype_id` (`item_id`,`contacttype_id`),
  KEY `idx_item_id` (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_item_price`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_maintainance_form`
--

CREATE TABLE `s_maintainance_form` (
  `id` int(11) NOT NULL auto_increment,
  `maintainance_date` datetime NOT NULL,
  `contact_id` int(11) NOT NULL,
  `invoice_id` int(11) default NULL,
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_maintainance_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_manufacturer`
--

CREATE TABLE `s_manufacturer` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `symbol_id` int(11) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_manufacturer_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_manufacturer`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_operator`
--

CREATE TABLE `s_operator` (
  `operator_id` int(11) NOT NULL,
  `full_name` varchar(256) collate utf8_bin NOT NULL default '',
  `email` varchar(64) collate utf8_bin default NULL,
  `phone` varchar(32) collate utf8_bin default NULL,
  `address` varchar(256) collate utf8_bin default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `s_operator`
--

INSERT INTO `s_operator` (`operator_id`, `full_name`, `email`, `phone`, `address`) VALUES
(1, 'Le Thanh Hoang', NULL, NULL, NULL),
(4, '', NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `s_package`
--

CREATE TABLE `s_package` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `min_total_item_amount` int(11) NOT NULL,
  `max_total_item_amount` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_package`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_package_line`
--

CREATE TABLE `s_package_line` (
  `id` int(11) NOT NULL auto_increment,
  `package_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `parentpackline_id` int(11) default NULL,
  `min_item_amount` int(11) NOT NULL,
  `max_item_amount` int(11) NOT NULL,
  `optional` int(1) NOT NULL default '1',
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `ui_package_item` (`package_id`,`item_id`),
  KEY `idx_package_id` (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_package_line`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_package_line_item_price`
--

CREATE TABLE `s_package_line_item_price` (
  `id` int(11) NOT NULL auto_increment,
  `package_line_id` int(11) NOT NULL,
  `contact_type_id` int(11) NOT NULL,
  `sell_item_price` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `discount_percent` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_package_line_item_price`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_paid_money_form`
--

CREATE TABLE `s_paid_money_form` (
  `id` int(11) NOT NULL auto_increment,
  `contact_id` int(11) default NULL,
  `invoice_id` int(11) default NULL,
  `importstore_id` int(11) default NULL,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `paid_type` varchar(32) collate utf8_bin NOT NULL,
  `responsible_user` varchar(32) collate utf8_bin NOT NULL,
  `money` int(11) NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `validatetime_user` varchar(32) collate utf8_bin NOT NULL,
  `description` varchar(256) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_related_code` (`code`),
  KEY `idx_contact_id` (`contact_id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_importstore_id` (`importstore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_paid_money_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_partner`
--

CREATE TABLE `s_partner` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `title` int(1) default NULL,
  `comment` varchar(256) collate utf8_bin default NULL,
  `website` varchar(256) collate utf8_bin default NULL,
  `is_customer` int(1) default NULL,
  `is_supplier` int(1) default NULL,
  `is_employee` int(1) default NULL,
  `debit_limit` double default NULL,
  `unit_id` int(11) default NULL,
  `is_active` int(1) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_uom_category_code` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=5 ;

--
-- Dumping data for table `s_partner`
--

INSERT INTO `s_partner` (`id`, `code`, `name`, `title`, `comment`, `website`, `is_customer`, `is_supplier`, `is_employee`, `debit_limit`, `unit_id`, `is_active`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '1', 'Cust1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'admin', '2012-01-24 00:38:38', 'admin', '2012-01-24 00:38:38', 0),
(2, '2', 'SUPPLIER1', NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, 1, 'admin', '2012-01-24 12:59:59', 'admin', '2012-01-24 13:00:08', 2),
(3, '3', 'Customer3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'admin', '2012-01-24 16:58:01', 'admin', '2012-01-24 16:58:01', 0),
(4, '4', 'CUSTOMER4', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, 'admin', '2012-01-24 17:12:23', 'admin', '2012-01-24 17:12:23', 0);

-- --------------------------------------------------------

--
-- Table structure for table `s_partner_category`
--

CREATE TABLE `s_partner_category` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `parent_category_id` int(11) default NULL,
  `is_active` int(1) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=3 ;

--
-- Dumping data for table `s_partner_category`
--

INSERT INTO `s_partner_category` (`id`, `code`, `name`, `parent_category_id`, `is_active`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '1', 'SUPPLIER', NULL, 1, 'admin', '2012-01-24 14:18:49', 'admin', '2012-01-24 17:12:23', 1),
(2, '2', 'GOLD', 1, 1, 'admin', '2012-01-24 14:19:25', 'admin', '2012-01-24 14:19:25', 0);

-- --------------------------------------------------------

--
-- Table structure for table `s_partner_partner_category`
--

CREATE TABLE `s_partner_partner_category` (
  `partner_id` int(11) NOT NULL,
  `partner_category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `s_partner_partner_category`
--

INSERT INTO `s_partner_partner_category` (`partner_id`, `partner_category_id`) VALUES
(4, 1);

-- --------------------------------------------------------

--
-- Table structure for table `s_payment`
--

CREATE TABLE `s_payment` (
  `id` int(11) NOT NULL auto_increment,
  `invoice_id` int(11) default NULL,
  `contact_id` int(11) default NULL,
  `payment_type_id` int(11) NOT NULL,
  `money` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `payment_mean` varchar(16) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_contact_id` (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_payment`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_payment_type`
--

CREATE TABLE `s_payment_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `content_type` varchar(32) collate utf8_bin NOT NULL,
  `is_received` int(1) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_payment_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_product`
--

CREATE TABLE `s_product` (
  `id` int(11) NOT NULL auto_increment,
  `producttype_id` int(11) default NULL,
  `code` varchar(32) collate utf8_bin default NULL,
  `name` varchar(128) collate utf8_bin default NULL,
  `manufacturer_id` int(11) default NULL,
  `model` varchar(32) collate utf8_bin default NULL,
  `description` varchar(128) collate utf8_bin default NULL,
  `uploadfile_id` int(11) default NULL,
  `main_uom_id` int(11) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_product_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_product`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_product_type`
--

CREATE TABLE `s_product_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `product_family_type` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_product_type_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_product_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_received_money_form`
--

CREATE TABLE `s_received_money_form` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `receive_type` varchar(32) collate utf8_bin NOT NULL,
  `responsible_user` varchar(32) collate utf8_bin NOT NULL,
  `money` int(11) NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `validatetime_user` varchar(32) collate utf8_bin NOT NULL,
  `description` varchar(256) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_related_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_received_money_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_sales_contract`
--

CREATE TABLE `s_sales_contract` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `supplier_id` int(11) NOT NULL,
  `datetime_contract` datetime NOT NULL,
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(16) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_sales_contract_code` (`code`),
  KEY `idx_supplier_id` (`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_sales_contract`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_session_store`
--

CREATE TABLE `s_session_store` (
  `id` int(11) NOT NULL auto_increment,
  `store_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `refer_month` int(11) NOT NULL,
  `refer_quarter` int(11) NOT NULL,
  `refer_year` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_store_id` (`store_id`),
  KEY `idx_store_year` (`store_id`,`refer_year`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_session_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_shipment`
--

CREATE TABLE `s_shipment` (
  `id` int(11) NOT NULL auto_increment,
  `shipment_type_id` int(11) NOT NULL,
  `exportstore_id` int(11) NOT NULL,
  `money` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `status` varchar(32) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_exportstore_id` (`exportstore_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_shipment`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_shipment_type`
--

CREATE TABLE `s_shipment_type` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `base_price` double NOT NULL,
  `currency` varchar(3) collate utf8_bin NOT NULL,
  `active` int(1) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_shipment_type`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_store`
--

CREATE TABLE `s_store` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `store_name` varchar(128) collate utf8_bin NOT NULL,
  `manager_code` varchar(32) collate utf8_bin NOT NULL,
  `address` varchar(256) collate utf8_bin NOT NULL,
  `stored_address` varchar(256) collate utf8_bin NOT NULL,
  `import_address` varchar(256) collate utf8_bin NOT NULL,
  `export_address` varchar(256) collate utf8_bin NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_store_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_store`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_supplier`
--

CREATE TABLE `s_supplier` (
  `supplier_id` int(11) NOT NULL,
  `representer` varchar(256) collate utf8_bin default NULL,
  `sex` tinyint(1) NOT NULL,
  `position` varchar(256) collate utf8_bin default NULL,
  `address` varchar(256) collate utf8_bin default NULL,
  `phone` varchar(20) collate utf8_bin default NULL,
  `fax` varchar(20) collate utf8_bin default NULL,
  `email` varchar(32) collate utf8_bin default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `s_supplier`
--

INSERT INTO `s_supplier` (`supplier_id`, `representer`, `sex`, `position`, `address`, `phone`, `fax`, `email`) VALUES
(2, 'Thu Hien', 1, 'Giam doc', 'AAAAA', '123456', '1234556', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `s_support_form`
--

CREATE TABLE `s_support_form` (
  `id` int(11) NOT NULL auto_increment,
  `supplier_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `reason` varchar(128) collate utf8_bin NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `number_of_month` int(11) NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_contact_id` (`contact_id`),
  KEY `idx_end_date` (`end_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_support_form`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_unit_of_measure`
--

CREATE TABLE `s_unit_of_measure` (
  `id` int(11) NOT NULL auto_increment,
  `uom_category_id` int(11) NOT NULL,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `uom_name` varchar(128) collate utf8_bin NOT NULL,
  `is_base_measure` tinyint(4) NOT NULL default '0',
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_uom_code` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2 ;

--
-- Dumping data for table `s_unit_of_measure`
--

INSERT INTO `s_unit_of_measure` (`id`, `uom_category_id`, `code`, `uom_name`, `is_base_measure`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, 1, '1', 'met', 0, 'admin', '2012-01-24 03:03:24', 'admin', '2012-01-24 03:03:24', 0);

-- --------------------------------------------------------

--
-- Table structure for table `s_uom_category`
--

CREATE TABLE `s_uom_category` (
  `id` int(11) NOT NULL auto_increment,
  `code` varchar(32) collate utf8_bin NOT NULL,
  `name` varchar(128) collate utf8_bin NOT NULL,
  `parentUomCategory_id` int(11) default NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_uom_category_code` (`code`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=4 ;

--
-- Dumping data for table `s_uom_category`
--

INSERT INTO `s_uom_category` (`id`, `code`, `name`, `parentUomCategory_id`, `usr_log_i`, `dte_log_i`, `usr_log_lu`, `dte_log_lu`, `version`) VALUES
(1, '1', 'Chieu dai', NULL, 'admin', '2012-01-24 03:03:04', 'admin', '2012-01-24 03:03:04', 0),
(2, 'm', 'mét', 1, 'admin', '2012-01-24 14:33:29', 'admin', '2012-01-24 14:33:29', 0),
(3, 'mm', 'milimet', 1, 'admin', '2012-01-24 14:33:51', 'admin', '2012-01-24 14:33:51', 0);

-- --------------------------------------------------------

--
-- Table structure for table `s_upload_file`
--

CREATE TABLE `s_upload_file` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `title` varchar(128) collate utf8_bin NOT NULL default '',
  `description` varchar(128) collate utf8_bin NOT NULL default '',
  `diskfile` varchar(128) collate utf8_bin NOT NULL default '',
  `filename` varchar(128) collate utf8_bin NOT NULL default '',
  `folder` varchar(128) collate utf8_bin NOT NULL default '',
  `filesize` int(11) NOT NULL default '0',
  `file_type` varchar(128) collate utf8_bin NOT NULL default '',
  `content` longblob,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `idx_diskfile` (`diskfile`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_upload_file`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_warning_board`
--

CREATE TABLE `s_warning_board` (
  `id` int(11) NOT NULL auto_increment,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_warning_board`
--


-- --------------------------------------------------------

--
-- Table structure for table `s_warranty_form`
--

CREATE TABLE `s_warranty_form` (
  `id` int(11) NOT NULL auto_increment,
  `article_id` int(11) NOT NULL,
  `contact_id` int(11) NOT NULL,
  `number_of_month` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` datetime NOT NULL,
  `usr_log_i` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_i` datetime NOT NULL,
  `usr_log_lu` varchar(32) collate utf8_bin NOT NULL,
  `dte_log_lu` datetime NOT NULL,
  `version` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  UNIQUE KEY `ui_article_id` (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1 ;

--
-- Dumping data for table `s_warranty_form`
--


--
-- Constraints for dumped tables
--

--
-- Constraints for table `acl_entry`
--
ALTER TABLE `acl_entry`
  ADD CONSTRAINT `foreign_fk_4` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`),
  ADD CONSTRAINT `foreign_fk_5` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`);

--
-- Constraints for table `acl_object_identity`
--
ALTER TABLE `acl_object_identity`
  ADD CONSTRAINT `foreign_fk_1` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`),
  ADD CONSTRAINT `foreign_fk_2` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`),
  ADD CONSTRAINT `foreign_fk_3` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`);
