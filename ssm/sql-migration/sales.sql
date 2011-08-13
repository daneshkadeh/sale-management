-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 15, 2011 at 05:31 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `sales`
--
drop table if exists tbl_detail_invoice;
drop table if exists tbl_invoice;
drop table if exists tbl_goods;
drop table if exists tbl_customer;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_customer`
--

CREATE TABLE IF NOT EXISTS `tbl_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_customer`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_detail_invoice`
--

CREATE TABLE IF NOT EXISTS `tbl_detail_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goods_id` int(11) NOT NULL,
  `goods_name` varchar(250) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT '0',
  `price_before_tax` double NOT NULL DEFAULT '0',
  `tax` double NOT NULL DEFAULT '0',
  `price_after_tax` double NOT NULL DEFAULT '0',
  `money_before_tax` double NOT NULL DEFAULT '0',
  `money_of_tax` double NOT NULL DEFAULT '0',
  `money_after_tax` double NOT NULL DEFAULT '0',
  `invoice_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
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

CREATE TABLE IF NOT EXISTS `tbl_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `priceBeforeTax` double NOT NULL DEFAULT '0',
  `tax` double NOT NULL DEFAULT '0',
  `price_after_tax` double NOT NULL DEFAULT '0',
  `addQuan` int(11) NOT NULL DEFAULT '0' COMMENT 'Additional Quantity',
  `curQuan` int(11) NOT NULL DEFAULT '0' COMMENT 'Current Quantity',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `tbl_goods`
--


-- --------------------------------------------------------

--
-- Table structure for table `tbl_invoice`
--

CREATE TABLE IF NOT EXISTS `tbl_invoice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` date NOT NULL,
  `customer_id` int(11) NOT NULL,
  `total_before_tax` double NOT NULL DEFAULT '0',
  `tax_total` double NOT NULL DEFAULT '0',
  `total_after_tax` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
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
