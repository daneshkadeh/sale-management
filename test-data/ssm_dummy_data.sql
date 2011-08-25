-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Aug 25, 2011 at 03:33 PM
-- Server version: 5.1.36
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sales`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_customer`
--

CREATE TABLE IF NOT EXISTS `tbl_customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=35 ;

--
-- Dumping data for table `tbl_customer`
--

INSERT INTO `tbl_customer` (`id`, `name`) VALUES
(1, 'customer1'),
(2, 'customer2'),
(3, 'customer3'),
(4, 'customer4'),
(5, 'customer5'),
(6, 'customer6'),
(7, 'customer7'),
(8, 'customer8'),
(9, 'customer9'),
(10, 'customer10'),
(11, 'customer11'),
(12, 'customer12'),
(14, 'customer13'),
(15, 'customer14'),
(16, 'customer15'),
(17, 'customer16'),
(18, 'customer17'),
(19, 'customer18'),
(20, 'customer20'),
(21, 'customer21'),
(23, 'customer23'),
(24, 'customer24'),
(25, 'customer25'),
(26, 'customer26'),
(27, 'customer27'),
(28, 'customer28'),
(29, 'Pham Cong Bang'),
(30, 'Pham Cong Bang2'),
(32, 'Hoang123'),
(33, 'Hoang'),
(34, 'Phan Hong Phuc');

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `tbl_detail_invoice`
--

INSERT INTO `tbl_detail_invoice` (`id`, `goods_id`, `goods_name`, `quantity`, `price_before_tax`, `tax`, `price_after_tax`, `money_before_tax`, `money_of_tax`, `money_after_tax`, `invoice_id`) VALUES
(1, 1, 'goods1', 1, 100, 1, 99, 100, 1, 99, 1),
(2, 1, 'goods1', 1, 100, 1, 99, 100, 1, 99, 3),
(3, 1, 'goods1', 1, 199, 1, 198, 199, 1, 198, 4),
(4, 2, 'goods21', 1, 200, 2, 198, 200, 2, 198, 5),
(5, 1, 'goods1', 1, 100, 1, 99, 100, 1, 99, 7);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `tbl_goods`
--

INSERT INTO `tbl_goods` (`id`, `name`, `priceBeforeTax`, `tax`, `price_after_tax`, `addQuan`, `curQuan`) VALUES
(1, 'goods1', 100, 1, 99, 2, 4),
(2, 'goods2', 200, 2, 198, 4, 10),
(3, 'goods3', 300, 3, 297, 4, 30),
(4, 'name4', 100, 1, 99, 10, 111),
(5, 'goods5', 100, 1, 99, 10, 100),
(6, 'goods6', 100, 1, 99, 10, 110);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `tbl_invoice`
--

INSERT INTO `tbl_invoice` (`id`, `created_date`, `customer_id`, `total_before_tax`, `tax_total`, `total_after_tax`) VALUES
(1, '2011-08-13', 1, 100, 1, 99),
(2, '2011-08-13', 1, 100, 1, 99),
(3, '2011-08-13', 2, 100, 1, 99),
(4, '2011-08-13', 6, 199, 1, 198),
(5, '2011-08-13', 32, 200, 2, 198),
(6, '2011-08-20', 4, 100, 1, 99),
(7, '2011-08-20', 1, 100, 1, 99);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_detail_invoice`
--
ALTER TABLE `tbl_detail_invoice`
  ADD CONSTRAINT `tbl_detail_invoice_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `tbl_invoice` (`id`),
  ADD CONSTRAINT `tbl_detail_invoice_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `tbl_goods` (`id`);

--
-- Constraints for table `tbl_invoice`
--
ALTER TABLE `tbl_invoice`
  ADD CONSTRAINT `tbl_invoice_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `tbl_customer` (`id`);
