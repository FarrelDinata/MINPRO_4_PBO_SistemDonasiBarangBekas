/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.Donasi;
import model.DonasiDAO;
import model.DonasiElektronik;
import model.DonasiPakaian;

public class DonasiController {

    private final DonasiDAO donasiDAO;

    public DonasiController() {
        this.donasiDAO = new DonasiDAO();
    }

    public List<Donasi> getAllDonasi() {
        return donasiDAO.getAll();
    }

    public boolean isIdExist(String id) {
        return donasiDAO.getById(id) != null;
    }

    public boolean tambahDonasiPakaian(String id, String pendonor, String barang, int jumlah, String ukuran) {
        if (isIdExist(id)) {
            throw new IllegalArgumentException("ID Donasi " + id + " sudah terdaftar di database!");
        }
        DonasiPakaian p = new DonasiPakaian(id, pendonor, barang, jumlah, ukuran);
        return donasiDAO.insert(p);
    }

    public boolean tambahDonasiElektronik(String id, String pendonor, String barang, int jumlah, String merek, boolean berfungsi) {
        if (isIdExist(id)) {
            throw new IllegalArgumentException("ID Donasi " + id + " sudah terdaftar di database!");
        }
        DonasiElektronik e = new DonasiElektronik(id, pendonor, barang, jumlah, merek, berfungsi);
        return donasiDAO.insert(e);
    }

    public boolean ubahDonasiPakaian(String id, String pendonorBaru, String barangBaru, int jumlahBaru, String ukuranBaru) {
        DonasiPakaian pBaru = new DonasiPakaian(id, pendonorBaru, barangBaru, jumlahBaru, ukuranBaru);
        return donasiDAO.update(pBaru);
    }

    public boolean ubahDonasiElektronik(String id, String pendonorBaru, String barangBaru, int jumlahBaru, String merekBaru, boolean berfungsiBaru) {
        DonasiElektronik eBaru = new DonasiElektronik(id, pendonorBaru, barangBaru, jumlahBaru, merekBaru, berfungsiBaru);
        return donasiDAO.update(eBaru);
    }

    public boolean hapusDonasi(String id) {
        return donasiDAO.delete(id);
    }

    public List<Donasi> cariBerdasarkanPendonor(String nama) {
        return donasiDAO.searchByPendonor(nama);
    }
}