-- phpMyAdmin SQL Dump
-- version 2.11.0
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 04, 2011 at 12:23 AM
-- Server version: 5.0.45
-- PHP Version: 5.2.4

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `sales`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_auth_login`
--

CREATE TABLE `tbl_auth_login` (
  `id` int(11) NOT NULL auto_increment,
  `appName` varchar(250) NOT NULL,
  `loginModuleClass` varchar(250) NOT NULL,
  `controlFlag` varchar(250) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_auth_login`
--

INSERT INTO `tbl_auth_login` (`id`, `appName`, `loginModuleClass`, `controlFlag`) VALUES
(1, 'simpleDb', 'com.hbsoft.ssm.security.SalesLoginModule', 'REQUIRED');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_auth_principal`
--

CREATE TABLE `tbl_auth_principal` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(250) NOT NULL,
  `class` varchar(250) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_auth_principal`
--

INSERT INTO `tbl_auth_principal` (`id`, `name`, `class`) VALUES
(1, 'Manager', 'com.hbsoft.ssm.security.SalesPrincipal');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_auth_user`
--

CREATE TABLE `tbl_auth_user` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tbl_auth_user`
--

INSERT INTO `tbl_auth_user` (`id`, `username`, `password`) VALUES
(1, 'hoangle', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_auth_user_principal`
--

CREATE TABLE `tbl_auth_user_principal` (
  `user_id` int(11) NOT NULL,
  `principal_id` int(11) NOT NULL,
  PRIMARY KEY  (`user_id`,`principal_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tbl_auth_user_principal`
--

INSERT INTO `tbl_auth_user_principal` (`user_id`, `principal_id`) VALUES
(1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_customer`
--

CREATE TABLE `tbl_customer` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_customer`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_detail_invoice`
--

CREATE TABLE `tbl_detail_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `goods_id` int(11) NOT NULL,
  `goods_name` varchar(250) NOT NULL,
  `quantity` int(11) NOT NULL default '0',
  `price_before_tax` double NOT NULL default '0',
  `tax` double NOT NULL default '0',
  `price_after_tax` double NOT NULL default '0',
  `money_before_tax` double NOT NULL default '0',
  `money_of_tax` double NOT NULL default '0',
  `money_after_tax` double NOT NULL default '0',
  `invoice_id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `invoice_id` (`invoice_id`),
  KEY `goods_id` (`goods_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_detail_invoice`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_goods`
--

CREATE TABLE `tbl_goods` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(250) NOT NULL,
  `priceBeforeTax` double NOT NULL default '0',
  `tax` double NOT NULL default '0',
  `price_after_tax` double NOT NULL default '0',
  `addQuan` int(11) NOT NULL default '0' COMMENT 'Additional Quantity',
  `curQuan` int(11) NOT NULL default '0' COMMENT 'Current Quantity',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_goods`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_invoice`
--

CREATE TABLE `tbl_invoice` (
  `id` int(11) NOT NULL auto_increment,
  `created_date` date NOT NULL,
  `customer_id` int(11) NOT NULL,
  `total_before_tax` double NOT NULL default '0',
  `tax_total` double NOT NULL default '0',
  `total_after_tax` double NOT NULL default '0',
  PRIMARY KEY  (`id`),
  KEY `customer_id` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_invoice`
--


--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_detail_invoice`
--
ALTER TABLE `tbl_detail_invoice`
  ADD CONSTRAINT `tbl_detail_invoice_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `tbl_goods` (`id`),
  ADD CONSTRAINT `tbl_detail_invoice_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `tbl_invoice` (`id`);

--
-- Constraints for table `tbl_invoice`
--
ALTER TABLE `tbl_invoice`
  ADD CONSTRAINT `tbl_invoice_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `tbl_customer` (`id`);
