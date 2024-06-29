-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 22 Jun 2024 pada 08.47
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_tmd`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tscore`
--

CREATE TABLE `tscore` (
  `username` varchar(255) NOT NULL,
  `score` int(11) NOT NULL,
  `up` int(11) NOT NULL,
  `down` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tscore`
--

INSERT INTO `tscore` (`username`, `score`, `up`, `down`) VALUES
('a1', 0, 0, 0),
('a2', 0, 0, 0),
('aditya', 216, 4, 5),
('boy', 1266, 19, 35),
('boya', 6042, 117, 132),
('boyaaaaaaaaaa', 846, 15, 21),
('boyaditya', 489, 10, 11),
('boyassssss', 594, 9, 17),
('boys', 913, 19, 19),
('dit', 0, 0, 0),
('newbie', 660, 11, 16),
('pengguna', 0, 0, 0),
('penggunalain', 0, 0, 0),
('proplayer', 21, 0, 1),
('test', 18, 0, 1),
('userkeren', 584, 10, 13);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tscore`
--
ALTER TABLE `tscore`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
