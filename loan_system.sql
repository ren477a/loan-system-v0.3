-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 13, 2016 at 12:58 AM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `loan_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `id` int(11) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `middlename` varchar(50) DEFAULT NULL,
  `lastname` varchar(50) NOT NULL,
  `bday_month` varchar(20) NOT NULL,
  `bday_day` int(11) NOT NULL,
  `bday_year` int(11) NOT NULL,
  `address` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `tenure` varchar(50) NOT NULL,
  `monthly_income` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`id`, `firstname`, `middlename`, `lastname`, `bday_month`, `bday_day`, `bday_year`, `address`, `email`, `tenure`, `monthly_income`) VALUES
(329, 'Jasper', '', 'Agustin', 'January', 1, 1998, 'Rizal', 'japcake@gmail.com', '2 - 3 years', 50000),
(328, 'Peter', '', 'Dacio', 'January', 1, 1998, '89 New York, Cubao', 'peterdacio@yahoo.com', '1 - 2 years', 20000);

-- --------------------------------------------------------

--
-- Table structure for table `duedate_acc328_loan3`
--

CREATE TABLE `duedate_acc328_loan3` (
  `period` int(11) NOT NULL,
  `date` varchar(12) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `duedate_acc328_loan3`
--

INSERT INTO `duedate_acc328_loan3` (`period`, `date`, `status`) VALUES
(1, '11/13/2016', 'UNPAID'),
(2, '12/13/2016', 'UNPAID'),
(3, '01/13/2017', 'UNPAID'),
(4, '02/13/2017', 'UNPAID'),
(5, '03/13/2017', 'UNPAID'),
(6, '04/13/2017', 'UNPAID'),
(7, '05/13/2017', 'UNPAID'),
(8, '06/13/2017', 'UNPAID'),
(9, '07/13/2017', 'UNPAID'),
(10, '08/13/2017', 'UNPAID'),
(11, '09/13/2017', 'UNPAID'),
(12, '10/13/2017', 'UNPAID');

-- --------------------------------------------------------

--
-- Table structure for table `duedate_acc328_loan4`
--

CREATE TABLE `duedate_acc328_loan4` (
  `period` int(11) NOT NULL,
  `date` varchar(12) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `duedate_acc328_loan4`
--

INSERT INTO `duedate_acc328_loan4` (`period`, `date`, `status`) VALUES
(1, '01/13/2017', 'UNPAID'),
(2, '04/13/2017', 'UNPAID'),
(3, '07/13/2017', 'UNPAID'),
(4, '10/13/2017', 'UNPAID'),
(5, '01/13/2018', 'UNPAID'),
(6, '04/13/2018', 'UNPAID'),
(7, '07/13/2018', 'UNPAID'),
(8, '10/13/2018', 'UNPAID');

-- --------------------------------------------------------

--
-- Table structure for table `duedate_acc329_loan1`
--

CREATE TABLE `duedate_acc329_loan1` (
  `period` int(11) NOT NULL,
  `date` varchar(12) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `duedate_acc329_loan1`
--

INSERT INTO `duedate_acc329_loan1` (`period`, `date`, `status`) VALUES
(1, '04/13/2017', 'UNPAID'),
(2, '10/13/2017', 'UNPAID'),
(3, '04/13/2018', 'UNPAID'),
(4, '10/13/2018', 'UNPAID'),
(5, '04/13/2019', 'UNPAID'),
(6, '10/13/2019', 'UNPAID'),
(7, '04/13/2020', 'UNPAID'),
(8, '10/13/2020', 'UNPAID'),
(9, '04/13/2021', 'UNPAID'),
(10, '10/13/2021', 'UNPAID'),
(11, '04/13/2022', 'UNPAID'),
(12, '10/13/2022', 'UNPAID'),
(13, '04/13/2023', 'UNPAID'),
(14, '10/13/2023', 'UNPAID'),
(15, '04/13/2024', 'UNPAID'),
(16, '10/13/2024', 'UNPAID'),
(17, '04/13/2025', 'UNPAID'),
(18, '10/13/2025', 'UNPAID'),
(19, '04/13/2026', 'UNPAID'),
(20, '10/13/2026', 'UNPAID');

-- --------------------------------------------------------

--
-- Table structure for table `loan_328`
--

CREATE TABLE `loan_328` (
  `id` int(11) NOT NULL,
  `term` varchar(50) NOT NULL,
  `payback` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  `total` double NOT NULL,
  `periodical` double NOT NULL,
  `balance` double NOT NULL,
  `paid` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_328`
--

INSERT INTO `loan_328` (`id`, `term`, `payback`, `amount`, `total`, `periodical`, `balance`, `paid`) VALUES
(3, '12 months', 'Every month', 20000, 22500, 1875, 22500, 0),
(4, '2 years', 'Every quarter', 350000, 402500, 50312.5, 402500, 0);

-- --------------------------------------------------------

--
-- Table structure for table `loan_329`
--

CREATE TABLE `loan_329` (
  `id` int(11) NOT NULL,
  `term` varchar(50) NOT NULL,
  `payback` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  `total` double NOT NULL,
  `periodical` double NOT NULL,
  `balance` double NOT NULL,
  `paid` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `loan_329`
--

INSERT INTO `loan_329` (`id`, `term`, `payback`, `amount`, `total`, `periodical`, `balance`, `paid`) VALUES
(1, '10 years', 'Every 6 months', 15000000, 18750000, 937500, 18750000, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `duedate_acc328_loan3`
--
ALTER TABLE `duedate_acc328_loan3`
  ADD PRIMARY KEY (`period`);

--
-- Indexes for table `duedate_acc328_loan4`
--
ALTER TABLE `duedate_acc328_loan4`
  ADD PRIMARY KEY (`period`);

--
-- Indexes for table `duedate_acc329_loan1`
--
ALTER TABLE `duedate_acc329_loan1`
  ADD PRIMARY KEY (`period`);

--
-- Indexes for table `loan_328`
--
ALTER TABLE `loan_328`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `loan_329`
--
ALTER TABLE `loan_329`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=330;
--
-- AUTO_INCREMENT for table `loan_328`
--
ALTER TABLE `loan_328`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `loan_329`
--
ALTER TABLE `loan_329`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
