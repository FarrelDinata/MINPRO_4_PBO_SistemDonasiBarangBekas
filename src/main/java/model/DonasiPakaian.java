/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class DonasiPakaian extends Donasi {

    private String ukuran;

    public DonasiPakaian(String idDonasi, String namaPendonor, String namaBarang, int jumlahBarang, String ukuran) {
        super(idDonasi, namaPendonor, namaBarang, jumlahBarang);
        setUkuran(ukuran);
    }

    public DonasiPakaian(String idDonasi, String namaPendonor, String namaBarang, int jumlahBarang) {
        this(idDonasi, namaPendonor, namaBarang, jumlahBarang, "All Size");
    }

    public String getUkuran() {
        return ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = validasiTeks(ukuran, "Ukuran Pakaian");
    }

    @Override
    public String getKategori() {
        return "Pakaian";
    }

    @Override
    public String getDetailRincian() {
        return "Ukuran Pakaian: " + getUkuran();
    }

    @Override
    public String cekKelayakanPenyaluran() {
        if (getJumlahBarang() > 0 && !getUkuran().equalsIgnoreCase("Tidak Layak")) {
            return "Siap Disalurkan ke Panti/Posko";
        }
        return "Perlu Sortir Ulang";
    }

    @Override
    public boolean isSiapDisalurkan() {
        return getJumlahBarang() > 0;
    }

    @Override
    public String tampilkanInfo() {
        return super.tampilkanInfo() + " | Ukuran: " + getUkuran();
    }
}