-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 16, 2025 at 02:30 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ooad`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart_items`
--

CREATE TABLE `cart_items` (
  `idCustomer` varchar(50) NOT NULL,
  `idProduct` varchar(50) NOT NULL,
  `count` int(11) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table `deliveries`
--

CREATE TABLE `deliveries` (
  `idOrder` varchar(50) NOT NULL,
  `idCourier` varchar(50) NOT NULL,
  `status` enum('Pending','In Progress','Delivered') NOT NULL DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `idOrder` varchar(50) NOT NULL,
  `idProduct` varchar(50) NOT NULL,
  `qty` int(11) NOT NULL
) ;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`idOrder`, `idProduct`, `qty`) VALUES
('ORD1765891789907', 'PRD1', 1);

-- --------------------------------------------------------

--
-- Table structure for table `order_headers`
--

CREATE TABLE `order_headers` (
  `idOrder` varchar(50) NOT NULL,
  `idCustomer` varchar(50) NOT NULL,
  `idPromo` varchar(50) DEFAULT NULL,
  `status` varchar(50) NOT NULL,
  `orderedAt` datetime NOT NULL,
  `totalAmount` decimal(15,2) NOT NULL
) ;

--
-- Dumping data for table `order_headers`
--

INSERT INTO `order_headers` (`idOrder`, `idCustomer`, `idPromo`, `status`, `orderedAt`, `totalAmount`) VALUES
('ORD1765891789907', 'USR1765891756250', NULL, 'PAID', '2025-12-16 20:29:49', 50000.00);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `idProduct` varchar(50) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(15,2) NOT NULL,
  `stock` int(11) NOT NULL,
  `category` varchar(50) NOT NULL
) ;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`idProduct`, `name`, `price`, `stock`, `category`) VALUES
('PRD1', 'Chicken', 50000.00, 9, 'Meat');

-- --------------------------------------------------------

--
-- Table structure for table `promos`
--

CREATE TABLE `promos` (
  `idPromo` varchar(50) NOT NULL,
  `code` varchar(50) NOT NULL,
  `headline` varchar(255) NOT NULL,
  `discountPercentage` decimal(5,2) NOT NULL
) ;

--
-- Dumping data for table `promos`
--

INSERT INTO `promos` (`idPromo`, `code`, `headline`, `discountPercentage`) VALUES
('PRM1', 'DISCOUNT10', 'Promo 10%', 10.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `idUser` varchar(50) NOT NULL,
  `fullName` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `role` enum('CUSTOMER','ADMIN','COURIER') NOT NULL,
  `balance` decimal(15,2) DEFAULT 0.00,
  `emergencyContact` varchar(20) DEFAULT NULL,
  `vehicleType` varchar(50) DEFAULT NULL,
  `vehiclePlate` varchar(20) DEFAULT NULL
) ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`idUser`, `fullName`, `email`, `password`, `phone`, `address`, `role`, `balance`, `emergencyContact`, `vehicleType`, `vehiclePlate`) VALUES
('USR1', 'Alice', 'alice@gmail.com', '123456', '08123456789', 'Jakarta', 'CUSTOMER', 0.00, NULL, NULL, NULL),
('USR1765891756250', 'paisal1', 'paisal1@gmail.com', '123456', '082134872984', 'jakarta barat', 'CUSTOMER', 0.00, NULL, NULL, NULL),
('USR2', 'Bob', 'admin@gmail.com', '123456', '08123456780', 'Office', 'ADMIN', 0.00, '081111111', NULL, NULL),
('USR3', 'Courier', 'courier@gmail.com', '123456', '0811111111', 'Depo', 'COURIER', 0.00, NULL, 'Motor', 'B1234XYZ');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD PRIMARY KEY (`idCustomer`,`idProduct`),
  ADD KEY `idProduct` (`idProduct`);

--
-- Indexes for table `deliveries`
--
ALTER TABLE `deliveries`
  ADD PRIMARY KEY (`idOrder`),
  ADD KEY `idx_delivery_courier` (`idCourier`),
  ADD KEY `idx_delivery_status` (`status`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`idOrder`,`idProduct`),
  ADD KEY `idProduct` (`idProduct`);

--
-- Indexes for table `order_headers`
--
ALTER TABLE `order_headers`
  ADD PRIMARY KEY (`idOrder`),
  ADD KEY `idPromo` (`idPromo`),
  ADD KEY `idx_order_customer` (`idCustomer`),
  ADD KEY `idx_order_status` (`status`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`idProduct`);

--
-- Indexes for table `promos`
--
ALTER TABLE `promos`
  ADD PRIMARY KEY (`idPromo`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `idx_promo_code` (`code`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`idUser`),
  ADD UNIQUE KEY `email` (`email`),
  ADD KEY `idx_user_email` (`email`),
  ADD KEY `idx_user_role` (`role`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `cart_items`
--
ALTER TABLE `cart_items`
  ADD CONSTRAINT `cart_items_ibfk_1` FOREIGN KEY (`idCustomer`) REFERENCES `users` (`idUser`) ON DELETE CASCADE,
  ADD CONSTRAINT `cart_items_ibfk_2` FOREIGN KEY (`idProduct`) REFERENCES `products` (`idProduct`) ON DELETE CASCADE;

--
-- Constraints for table `deliveries`
--
ALTER TABLE `deliveries`
  ADD CONSTRAINT `deliveries_ibfk_1` FOREIGN KEY (`idOrder`) REFERENCES `order_headers` (`idOrder`) ON DELETE CASCADE,
  ADD CONSTRAINT `deliveries_ibfk_2` FOREIGN KEY (`idCourier`) REFERENCES `users` (`idUser`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`idOrder`) REFERENCES `order_headers` (`idOrder`) ON DELETE CASCADE,
  ADD CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`idProduct`) REFERENCES `products` (`idProduct`);

--
-- Constraints for table `order_headers`
--
ALTER TABLE `order_headers`
  ADD CONSTRAINT `order_headers_ibfk_1` FOREIGN KEY (`idCustomer`) REFERENCES `users` (`idUser`),
  ADD CONSTRAINT `order_headers_ibfk_2` FOREIGN KEY (`idPromo`) REFERENCES `promos` (`idPromo`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
