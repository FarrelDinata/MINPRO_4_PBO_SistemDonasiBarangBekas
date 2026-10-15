-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Oct 15, 2026 at 03:22 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_donasi_barang`
--

-- --------------------------------------------------------

--
-- Table structure for table `donasi`
--

CREATE TABLE `donasi` (
  `id_donasi` varchar(20) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `nama_pendonor` varchar(100) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `jumlah_barang` int NOT NULL,
  `ukuran` varchar(20) DEFAULT NULL,
  `merek` varchar(50) DEFAULT NULL,
  `masih_berfungsi` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `donasi`
--

INSERT INTO `donasi` (`id_donasi`, `kategori`, `nama_pendonor`, `nama_barang`, `jumlah_barang`, `ukuran`, `merek`, `masih_berfungsi`) VALUES
('D001', 'Pakaian', 'Budi Santoso', 'Jaket Hoodie', 3, 'L', NULL, NULL),
('D002', 'Pakaian', 'Siti Aminah', 'Kemeja Batik', 5, 'All Size', NULL, NULL),
('D003', 'Elektronik', 'Andi Wijaya', 'Kipas Angin', 2, NULL, 'Miyako', 1),
('D004', 'Elektronik', 'Rina Lestari', 'Setrika', 1, NULL, 'Philips', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `donasi`
--
ALTER TABLE `donasi`
  ADD PRIMARY KEY (`id_donasi`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
