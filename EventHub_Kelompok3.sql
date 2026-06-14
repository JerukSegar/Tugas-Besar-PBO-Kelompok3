-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 14 Jun 2026 pada 16.45
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `eventhub2`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `check_in`
--

CREATE TABLE `check_in` (
  `id` bigint(20) NOT NULL,
  `penyelenggara_id` bigint(20) NOT NULL,
  `tiket_id` bigint(20) NOT NULL,
  `waktu_checkin` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `events`
--

CREATE TABLE `events` (
  `id` bigint(20) NOT NULL,
  `created_by` bigint(20) NOT NULL,
  `deskripsi` varchar(1000) DEFAULT NULL,
  `harga` varchar(255) NOT NULL,
  `kapasitas` int(11) NOT NULL,
  `kategori` varchar(255) NOT NULL,
  `lokasi` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `penyelenggara` varchar(255) NOT NULL,
  `tanggal` varchar(255) NOT NULL,
  `terisi` int(11) NOT NULL,
  `tipe_harga` varchar(255) NOT NULL,
  `venue` varchar(255) NOT NULL,
  `waktu` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `events`
--

INSERT INTO `events` (`id`, `created_by`, `deskripsi`, `harga`, `kapasitas`, `kategori`, `lokasi`, `nama`, `penyelenggara`, `tanggal`, `terisi`, `tipe_harga`, `venue`, `waktu`) VALUES
(4, 6, 'Seminar membahas perkembangan AI di Indonesia, tren global, dan peluang karier di bidang teknologi kecerdasan buatan.', 'Rp 10.000', 20, 'seminar', 'Bandung', 'Seminar Nasional Kecerdasan Buatan 2026', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'paid', 'Gedung A', '10.00–06.00 WIB'),
(5, 6, 'Workshop hands-on belajar dasar desain UI/UX menggunakan Figma, mencakup wireframing, prototyping, dan prinsip desain antarmuka.', 'Gratis', 10, 'workshop', 'Bandung', 'Workshop Desain UI/UX untuk Pemula', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'free', 'Gedung B', '10.00–13.00 WIB'),
(6, 6, 'Konferensi tahunan mempertemukan para founder, investor, dan ekosistem startup Indonesia untuk berbagi insight dan networking.', 'Rp 12.000', 12, 'konferensi', 'Bandung', 'Konferensi Startup Indonesia 2025', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'paid', 'Gedung C', '09.00–13.00 WIB'),
(7, 6, 'Webinar interaktif mengenalkan konsep dasar investasi saham, reksa dana, dan manajemen keuangan bagi anak muda.', 'Gratis', 20, 'webinar', 'Bandung', 'Webinar Investasi Saham untuk Generasi Z', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'free', 'Gedung D', ''),
(8, 6, 'Bootcamp 3 hari intensif belajar Python dari dasar hingga analisis data, visualisasi, dan pengantar machine learning.', 'Rp 30.000', 30, 'bootcamp', 'Bandung', 'Bootcamp Python & Data Science Intensif', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'paid', 'Gedung E', ''),
(9, 6, 'Seminar edukasi tentang pentingnya kesehatan mental remaja, cara mengelola stres, dan akses layanan psikologi yang terjangkau.', 'Gratis', 23, 'seminar', 'Bandung', 'Seminar Kesehatan Mental Remaja', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'free', 'Gedung F', '09.00–15.00 WIB'),
(10, 6, 'Belajar teknik foto produk profesional hanya dengan smartphone, pencahayaan sederhana, dan editing ringan untuk jualan online.', 'Rp 50.000', 45, 'workshop', 'Bandung', 'Workshop Fotografi Produk untuk UMKM', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'paid', 'Gedung G', ''),
(11, 6, 'Forum tahunan para pendidik, pembuat kebijakan, dan pelaku teknologi membahas transformasi pendidikan di era digital.', 'Gratis', 16, 'konferensi', 'Bandung', 'Konferensi Pendidikan Digital Indonesia', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'free', 'Gedung H', ''),
(12, 6, 'Strategi digital marketing praktis untuk bisnis lokal: optimasi Instagram, Google Business, dan cara mengelola iklan berbayar dengan anggaran terbatas.', 'Gratis', 33, 'webinar', 'Bandung', 'Webinar Digital Marketing untuk Bisnis Lokal', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'free', 'Gedung I', '09.00–14.00 WIB'),
(13, 6, 'Bootcamp 2 hari melatih kemampuan berbicara di depan umum, storytelling, dan teknik presentasi bisnis yang meyakinkan.', 'Rp 40.000', 21, 'bootcamp', 'Bandung', 'Bootcamp Public Speaking & Presentasi Bisnis', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'paid', 'Gedung J', ''),
(14, 6, 'Seminar membahas implementasi UU Perlindungan Data Pribadi, kewajiban perusahaan, dan hak pengguna dalam era digital.', 'Gratis', 27, 'seminar', 'Bandung', 'Seminar Hukum Perlindungan Data Pribadi', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'free', 'Gedung K', ''),
(15, 6, 'Workshop praktis membuat konten video viral di TikTok dan Instagram Reels, mulai dari konsep, shooting, editing, hingga strategi posting.', 'Rp 100.000', 30, 'workshop', 'Bandung', 'Workshop Pembuatan Konten Video TikTok & Reels', 'Himpunan Mahasiswa Informatika', '20 Jun 2026', 0, 'paid', 'Gedung L', '09.00–15.00 WIB');

-- --------------------------------------------------------

--
-- Struktur dari tabel `laporan`
--

CREATE TABLE `laporan` (
  `id` bigint(20) NOT NULL,
  `dibuat_at` datetime(6) DEFAULT NULL,
  `event_id` bigint(20) NOT NULL,
  `penyelenggara_id` bigint(20) NOT NULL,
  `total_checkin` int(11) DEFAULT NULL,
  `total_pendapatan` bigint(20) DEFAULT NULL,
  `total_peserta` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `pendaftaran`
--

CREATE TABLE `pendaftaran` (
  `id` bigint(20) NOT NULL,
  `event_id` bigint(20) NOT NULL,
  `kode_tiket` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tiket`
--

CREATE TABLE `tiket` (
  `id` bigint(20) NOT NULL,
  `diterbitkan_at` datetime(6) DEFAULT NULL,
  `kode_qr` varchar(255) NOT NULL,
  `pendaftaran_id` bigint(20) NOT NULL,
  `status_tiket` varchar(255) NOT NULL,
  `tipe_tiket` varchar(31) NOT NULL,
  `status_pembayaran` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `institusi` varchar(255) DEFAULT NULL,
  `kota` varchar(255) DEFAULT NULL,
  `nama` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(255) NOT NULL,
  `telp` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `email`, `institusi`, `kota`, `nama`, `password`, `role`, `telp`) VALUES
(4, 'peserta@gmail.com', 'Telkom University', 'Padang', 'peserta', '12345678L?', 'peserta', '082288648705'),
(6, 'penyelenggara@gmail.com', 'Telkom University', 'Padang', 'penyelenggara', '12345678L?', 'penyelenggara', '082288648705');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `check_in`
--
ALTER TABLE `check_in`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `laporan`
--
ALTER TABLE `laporan`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `pendaftaran`
--
ALTER TABLE `pendaftaran`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK85tvau3ccy8vkk2skdhiuq2po` (`kode_tiket`);

--
-- Indeks untuk tabel `tiket`
--
ALTER TABLE `tiket`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK9vcji3cneotw2ab6m6bioe3os` (`kode_qr`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `check_in`
--
ALTER TABLE `check_in`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT untuk tabel `events`
--
ALTER TABLE `events`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT untuk tabel `laporan`
--
ALTER TABLE `laporan`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT untuk tabel `pendaftaran`
--
ALTER TABLE `pendaftaran`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT untuk tabel `tiket`
--
ALTER TABLE `tiket`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
