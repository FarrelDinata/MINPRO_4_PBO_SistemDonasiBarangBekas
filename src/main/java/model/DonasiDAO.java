/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import config.Koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonasiDAO {

    // Method Helper ORM: Memetakan baris ResultSet database ke Objek Domain Java
    private Donasi mapResultSetToEntity(ResultSet rs) throws SQLException {
        String id = rs.getString("id_donasi");
        String kategori = rs.getString("kategori");
        String pendonor = rs.getString("nama_pendonor");
        String barang = rs.getString("nama_barang");
        int jumlah = rs.getInt("jumlah_barang");

        if ("Pakaian".equalsIgnoreCase(kategori)) {
            String ukuran = rs.getString("ukuran");
            return new DonasiPakaian(id, pendonor, barang, jumlah, ukuran);
        } else if ("Elektronik".equalsIgnoreCase(kategori)) {
            String merek = rs.getString("merek");
            boolean berfungsi = rs.getBoolean("masih_berfungsi");
            return new DonasiElektronik(id, pendonor, barang, jumlah, merek, berfungsi);
        }
        return null;
    }

    // CREATE
    public boolean insert(Donasi donasi) {
        String sql = "INSERT INTO donasi (id_donasi, kategori, nama_pendonor, nama_barang, jumlah_barang, ukuran, merek, masih_berfungsi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, donasi.getIdDonasi());
            stmt.setString(2, donasi.getKategori());
            stmt.setString(3, donasi.getNamaPendonor());
            stmt.setString(4, donasi.getNamaBarang());
            stmt.setInt(5, donasi.getJumlahBarang());

            if (donasi instanceof DonasiPakaian) {
                stmt.setString(6, ((DonasiPakaian) donasi).getUkuran());
                stmt.setNull(7, java.sql.Types.VARCHAR);
                stmt.setNull(8, java.sql.Types.BOOLEAN);
            } else if (donasi instanceof DonasiElektronik) {
                DonasiElektronik e = (DonasiElektronik) donasi;
                stmt.setNull(6, java.sql.Types.VARCHAR);
                stmt.setString(7, e.getMerek());
                stmt.setBoolean(8, e.isMasihBerfungsi());
            }

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal Tambah Data: " + e.getMessage());
            return false;
        }
    }

    // READ ALL
    public List<Donasi> getAll() {
        List<Donasi> list = new ArrayList<>();
        String sql = "SELECT * FROM donasi";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Donasi d = mapResultSetToEntity(rs);
                if (d != null) {
                    list.add(d);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal Mengambil Data: " + e.getMessage());
        }
        return list;
    }

    // READ BY ID
    public Donasi getById(String id) {
        String sql = "SELECT * FROM donasi WHERE id_donasi = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal Cari ID: " + e.getMessage());
        }
        return null;
    }

    // UPDATE
    public boolean update(Donasi donasi) {
        String sql = "UPDATE donasi SET nama_pendonor = ?, nama_barang = ?, jumlah_barang = ?, ukuran = ?, merek = ?, masih_berfungsi = ? WHERE id_donasi = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, donasi.getNamaPendonor());
            stmt.setString(2, donasi.getNamaBarang());
            stmt.setInt(3, donasi.getJumlahBarang());

            if (donasi instanceof DonasiPakaian) {
                stmt.setString(4, ((DonasiPakaian) donasi).getUkuran());
                stmt.setNull(5, java.sql.Types.VARCHAR);
                stmt.setNull(6, java.sql.Types.BOOLEAN);
            } else if (donasi instanceof DonasiElektronik) {
                DonasiElektronik e = (DonasiElektronik) donasi;
                stmt.setNull(4, java.sql.Types.VARCHAR);
                stmt.setString(5, e.getMerek());
                stmt.setBoolean(6, e.isMasihBerfungsi());
            }

            stmt.setString(7, donasi.getIdDonasi());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal Update Data: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean delete(String id) {
        String sql = "DELETE FROM donasi WHERE id_donasi = ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Gagal Hapus Data: " + e.getMessage());
            return false;
        }
    }

    // SEARCH
    public List<Donasi> searchByPendonor(String nama) {
        List<Donasi> list = new ArrayList<>();
        String sql = "SELECT * FROM donasi WHERE nama_pendonor LIKE ?";
        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nama + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Donasi d = mapResultSetToEntity(rs);
                    if (d != null) {
                        list.add(d);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Gagal Pencarian Data: " + e.getMessage());
        }
        return list;
    }
}