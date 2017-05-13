-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 25, 2017 at 10:06 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `engine`
--
CREATE DATABASE IF NOT EXISTS `engine` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `engine`;

-- --------------------------------------------------------

--
-- Table structure for table `base_urls`
--

CREATE TABLE `base_urls` (
  `url` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `base_urls`:
--

--
-- Truncate table before insert `base_urls`
--

TRUNCATE TABLE `base_urls`;
-- --------------------------------------------------------

--
-- Table structure for table `indices`
--

CREATE TABLE `indices` (
  `url` varchar(700) NOT NULL,
  `index_id` int(11) NOT NULL,
  `word` varchar(255) DEFAULT NULL,
  `position` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `indices`:
--

--
-- Truncate table before insert `indices`
--

TRUNCATE TABLE `indices`;
-- --------------------------------------------------------

--
-- Table structure for table `pagestovisit`
--

CREATE TABLE `pagestovisit` (
  `base` text NOT NULL,
  `urlToVisit` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `pagestovisit`:
--

--
-- Truncate table before insert `pagestovisit`
--

TRUNCATE TABLE `pagestovisit`;
-- --------------------------------------------------------

--
-- Table structure for table `visited_urls`
--

CREATE TABLE `visited_urls` (
  `base` text NOT NULL,
  `visitedUrl` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- RELATIONS FOR TABLE `visited_urls`:
--

--
-- Truncate table before insert `visited_urls`
--

TRUNCATE TABLE `visited_urls`;
--
-- Indexes for dumped tables
--

--
-- Indexes for table `indices`
--
ALTER TABLE `indices`
  ADD PRIMARY KEY (`index_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `indices`
--
ALTER TABLE `indices`
  MODIFY `index_id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
