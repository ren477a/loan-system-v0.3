-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 13, 2016 at 01:05 PM
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
(335, 'Jonas', '', 'Jacinto', 'January', 1, 1993, 'Montalban, Rizal', 'jonasjjj@google.com', 'more than 3 years', 20000),
(336, 'Dennis', '', 'Farinas', 'June', 3, 1991, 'TIP', 'beast@gmail.com', '1 - 2 years', 50000);

-- --------------------------------------------------------

--
-- Table structure for table `adminaccount`
--

CREATE TABLE `adminaccount` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `adminaccount`
--

INSERT INTO `adminaccount` (`username`, `password`) VALUES
('jonas', 'abcde');

-- --------------------------------------------------------

--
-- Table structure for table `duedate_acc335_loan1`
--

CREATE TABLE `duedate_acc335_loan1` (
  `period` int(11) NOT NULL,
  `date` varchar(12) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `duedate_acc335_loan1`
--

INSERT INTO `duedate_acc335_loan1` (`period`, `date`, `status`) VALUES
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
-- Table structure for table `duedate_acc336_loan1`
--

CREATE TABLE `duedate_acc336_loan1` (
  `period` int(11) NOT NULL,
  `date` varchar(12) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `duedate_acc336_loan1`
--

INSERT INTO `duedate_acc336_loan1` (`period`, `date`, `status`) VALUES
(1, '11/13/2016', 'PAID'),
(2, '12/13/2016', 'PAID'),
(3, '01/13/2017', 'PAID'),
(4, '02/13/2017', 'PAID'),
(5, '03/13/2017', 'PAID'),
(6, '04/13/2017', 'PAID'),
(7, '05/13/2017', 'PAID'),
(8, '06/13/2017', 'PAID'),
(9, '07/13/2017', 'PAID'),
(10, '08/13/2017', 'PAID'),
(11, '09/13/2017', 'PAID'),
(12, '10/13/2017', 'PAID'),
(13, '11/13/2017', 'PAID'),
(14, '12/13/2017', 'PAID'),
(15, '01/13/2018', 'PAID'),
(16, '02/13/2018', 'PAID'),
(17, '03/13/2018', 'PAID'),
(18, '04/13/2018', 'PAID'),
(19, '05/13/2018', 'PAID'),
(20, '06/13/2018', 'PAID'),
(21, '07/13/2018', 'PAID'),
(22, '08/13/2018', 'PAID'),
(23, '09/13/2018', 'PAID'),
(24, '10/13/2018', 'PAID');

-- --------------------------------------------------------

--
-- Table structure for table `duedate_acc336_loan2`
--

CREATE TABLE `duedate_acc336_loan2` (
  `period` int(11) NOT NULL,
  `date` varchar(12) NOT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `duedate_acc336_loan2`
--

INSERT INTO `duedate_acc336_loan2` (`period`, `date`, `status`) VALUES
(1, '01/13/2017', 'UNPAID'),
(2, '04/13/2017', 'UNPAID'),
(3, '07/13/2017', 'UNPAID'),
(4, '10/13/2017', 'UNPAID'),
(5, '01/13/2018', 'UNPAID'),
(6, '04/13/2018', 'UNPAID'),
(7, '07/13/2018', 'UNPAID'),
(8, '10/13/2018', 'UNPAID'),
(9, '01/13/2019', 'UNPAID'),
(10, '04/13/2019', 'UNPAID'),
(11, '07/13/2019', 'UNPAID'),
(12, '10/13/2019', 'UNPAID'),
(13, '01/13/2020', 'UNPAID'),
(14, '04/13/2020', 'UNPAID'),
(15, '07/13/2020', 'UNPAID'),
(16, '10/13/2020', 'UNPAID'),
(17, '01/13/2021', 'UNPAID'),
(18, '04/13/2021', 'UNPAID'),
(19, '07/13/2021', 'UNPAID'),
(20, '10/13/2021', 'UNPAID');

-- --------------------------------------------------------

--
-- Table structure for table `loan_335`
--

CREATE TABLE `loan_335` (
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
-- Dumping data for table `loan_335`
--

INSERT INTO `loan_335` (`id`, `term`, `payback`, `amount`, `total`, `periodical`, `balance`, `paid`) VALUES
(1, '12 months', 'Every month', 100000, 110000, 9166.666666666666, 110000, 0);

-- --------------------------------------------------------

--
-- Table structure for table `loan_336`
--

CREATE TABLE `loan_336` (
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
-- Dumping data for table `loan_336`
--

INSERT INTO `loan_336` (`id`, `term`, `payback`, `amount`, `total`, `periodical`, `balance`, `paid`) VALUES
(1, '2 years', 'Every month', 90000, 101250, 4218.75, 0, 101250),
(2, '5 years', 'Every quarter', 500000, 587500, 29375, 587500, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `duedate_acc335_loan1`
--
ALTER TABLE `duedate_acc335_loan1`
  ADD PRIMARY KEY (`period`);

--
-- Indexes for table `duedate_acc336_loan1`
--
ALTER TABLE `duedate_acc336_loan1`
  ADD PRIMARY KEY (`period`);

--
-- Indexes for table `duedate_acc336_loan2`
--
ALTER TABLE `duedate_acc336_loan2`
  ADD PRIMARY KEY (`period`);

--
-- Indexes for table `loan_335`
--
ALTER TABLE `loan_335`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `loan_336`
--
ALTER TABLE `loan_336`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=337;
--
-- AUTO_INCREMENT for table `loan_335`
--
ALTER TABLE `loan_335`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `loan_336`
--
ALTER TABLE `loan_336`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
