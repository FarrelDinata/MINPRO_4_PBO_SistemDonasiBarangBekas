/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import controller.DonasiController;
import java.util.List;
import java.util.Scanner;
import model.Donasi;
import model.DonasiElektronik;
import model.DonasiPakaian;

public class DonasiView {

    private final DonasiController controller;
    private final Scanner scanner;

    public DonasiView() {
        this.controller = new DonasiController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int pilihan;
        do {
            tampilkanMenu();
            pilihan = bacaAngka("Pilih menu (0-5): ", 0, 5);
            switch (pilihan) {
                case 1:
                    menuTambah();
                    break;
                case 2:
                    menuTampilkan();
                    break;
                case 3:
                    menuUbah();
                    break;
                case 4:
                    menuHapus();
                    break;
                case 5:
                    menuCari();
                    break;
                case 0:
                    System.out.println("\nTerima kasih telah menggunakan Sistem Donasi Barang Bekas!");
                    break;
            }
        } while (pilihan != 0);
    }

    private void tampilkanMenu() {
        System.out.println("\n==========================================");
        System.out.println("   SISTEM MANAJEMEN DONASI BARANG BEKAS   ");
        System.out.println("==========================================");
        System.out.println("1. Tambah Data Donasi");
        System.out.println("2. Tampilkan Seluruh Donasi");
        System.out.println("3. Ubah Data Donasi");
        System.out.println("4. Hapus Data Donasi");
        System.out.println("5. Cari Donasi Berdasarkan Pendonor");
        System.out.println("0. Keluar");
        System.out.println("------------------------------------------");
    }

    private void menuTambah() {
        System.out.println("\n--- TAMBAH DATA DONASI BARU ---");
        String id;
        while (true) {
            id = bacaTeks("ID Donasi: ");
            if (controller.isIdExist(id)) {
                System.out.println("Error: ID Donasi sudah terdaftar. Gunakan ID lain!");
            } else {
                break;
            }
        }

        int jenis = bacaAngka("Kategori Barang (1. Pakaian, 2. Elektronik): ", 1, 2);
        String pendonor = bacaTeks("Nama Pendonor: ");
        String barang = bacaTeks("Nama Barang: ");
        int jumlah = bacaAngka("Jumlah Barang (1-1000): ", 1, 1000);

        try {
            boolean sukses;
            if (jenis == 1) {
                String ukuran = bacaTeks("Ukuran Pakaian (S/M/L/XL/All Size): ");
                sukses = controller.tambahDonasiPakaian(id, pendonor, barang, jumlah, ukuran);
            } else {
                String merek = bacaTeks("Merek Barang: ");
                boolean berfungsi = bacaYaTidak("Apakah barang masih berfungsi dengan baik? (y/n): ");
                sukses = controller.tambahDonasiElektronik(id, pendonor, barang, jumlah, merek, berfungsi);
            }

            if (sukses) {
                System.out.println(">>> Data donasi berhasil disimpan ke Database! <<<");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambahkan data: " + e.getMessage());
        }
    }

    private void menuTampilkan() {
        List<Donasi> list = controller.getAllDonasi();
        if (list.isEmpty()) {
            System.out.println("\nBelum ada data donasi tersimpan di Database.");
            return;
        }

        System.out.println("\n=== DAFTAR DONASI BARANG BEKAS (DATABASE) ===");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).tampilkanInfo(i + 1));
        }
    }

    private void menuUbah() {
        List<Donasi> list = controller.getAllDonasi();
        if (list.isEmpty()) {
            System.out.println("\nBelum ada data donasi untuk diubah.");
            return;
        }

        menuTampilkan();
        int nomor = bacaAngka("\nMasukkan nomor data yang ingin diubah: ", 1, list.size());
        Donasi target = list.get(nomor - 1);

        System.out.println("\n--- Mengubah Data ID: " + target.getIdDonasi() + " ---");
        try {
            String pendonorBaru = bacaTeks("Nama Pendonor Baru: ");
            String barangBaru = bacaTeks("Nama Barang Baru: ");
            int jumlahBaru = bacaAngka("Jumlah Barang Baru (1-1000): ", 1, 1000);

            boolean sukses = false;
            if (target instanceof DonasiPakaian) {
                String ukuranBaru = bacaTeks("Ukuran Pakaian Baru: ");
                sukses = controller.ubahDonasiPakaian(target.getIdDonasi(), pendonorBaru, barangBaru, jumlahBaru, ukuranBaru);
            } else if (target instanceof DonasiElektronik) {
                String merekBaru = bacaTeks("Merek Baru: ");
                boolean berfungsiBaru = bacaYaTidak("Masih berfungsi baik? (y/n): ");
                sukses = controller.ubahDonasiElektronik(target.getIdDonasi(), pendonorBaru, barangBaru, jumlahBaru, merekBaru, berfungsiBaru);
            }

            if (sukses) {
                System.out.println(">>> Data donasi di Database berhasil diperbarui! <<<");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal mengubah data: " + e.getMessage());
        }
    }

    private void menuHapus() {
        List<Donasi> list = controller.getAllDonasi();
        if (list.isEmpty()) {
            System.out.println("\nBelum ada data donasi untuk dihapus.");
            return;
        }

        menuTampilkan();
        int nomor = bacaAngka("\nMasukkan nomor data yang ingin dihapus: ", 1, list.size());
        Donasi target = list.get(nomor - 1);

        if (bacaYaTidak("Apakah Anda yakin menghapus ID " + target.getIdDonasi() + " dari Database? (y/n): ")) {
            if (controller.hapusDonasi(target.getIdDonasi())) {
                System.out.println(">>> Data donasi berhasil dihapus dari Database! <<<");
            }
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    private void menuCari() {
        String nama = bacaTeks("\nMasukkan nama pendonor yang dicari: ");
        List<Donasi> hasil = controller.cariBerdasarkanPendonor(nama);

        if (hasil.isEmpty()) {
            System.out.println("Tidak ditemukan data donasi atas nama: " + nama);
        } else {
            System.out.println("\n=== HASIL PENCARIAN DONASI ===");
            for (int i = 0; i < hasil.size(); i++) {
                Donasi d = hasil.get(i);
                System.out.println((i + 1) + ". " + d.getNamaPendonor() + " - " + d.getNamaBarang());
                System.out.println("   Rincian Spesifik: " + d.getDetailRincian());
            }
        }
    }

    private int bacaAngka(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String raw = scanner.nextLine().trim();
            try {
                int val = Integer.parseInt(raw);
                if (val >= min && val <= max) {
                    return val;
                }
                System.out.println("Masukkan angka dalam rentang " + min + " - " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat!");
            }
        }
    }

    private String bacaTeks(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = scanner.nextLine().trim();
            if (!val.isEmpty()) {
                return val;
            }
            System.out.println("Inputan tidak boleh kosong.");
        }
    }

    private boolean bacaYaTidak(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = scanner.nextLine().trim().toLowerCase();
            if (val.equals("y") || val.equals("ya")) {
                return true;
            }
            if (val.equals("n") || val.equals("tidak")) {
                return false;
            }
            System.out.println("Ketik 'y' untuk Ya atau 'n' untuk Tidak.");
        }
    }
}