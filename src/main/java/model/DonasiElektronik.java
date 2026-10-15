/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author DELL
 */
public class DonasiElektronik extends Donasi {

    private String merek;
    private boolean masihBerfungsi;

    public DonasiElektronik(String idDonasi, String namaPendonor, String namaBarang, int jumlahBarang, String merek, boolean masihBerfungsi) {
        super(idDonasi, namaPendonor, namaBarang, jumlahBarang);
        setMerek(merek);
        setMasihBerfungsi(masihBerfungsi);
    }

    public DonasiElektronik(String idDonasi, String namaPendonor, String namaBarang, int jumlahBarang, String merek) {
        this(idDonasi, namaPendonor, namaBarang, jumlahBarang, merek, true);
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = validasiTeks(merek, "Merek Elektronik");
    }

    public boolean isMasihBerfungsi() {
        return masihBerfungsi;
    }

    public void setMasihBerfungsi(boolean masihBerfungsi) {
        this.masihBerfungsi = masihBerfungsi;
    }

    @Override
    public String getKategori() {
        return "Elektronik";
    }

    @Override
    public String getDetailRincian() {
        return "Merek: " + getMerek() + " | Kondisi Berfungsi: " + (isMasihBerfungsi() ? "Ya" : "Tidak");
    }

    @Override
    public String cekKelayakanPenyaluran() {
        if (isMasihBerfungsi()) {
            return "Siap Disalurkan";
        }
        return "Butuh Perbaikan/Service";
    }

    @Override
    public boolean isSiapDisalurkan() {
        return isMasihBerfungsi();
    }

    @Override
    public String tampilkanInfo() {
        return super.tampilkanInfo() + " | Merek: " + getMerek() + " | Berfungsi: " + (isMasihBerfungsi() ? "Ya" : "Tidak");
    }
}