# Mini Project 4 - Sistem Donasi Barang Bekas (Integrasi Database JDBC & DAO)

**Nama Repository**: `Minpro-4-PBO-SistemDonasiBarangBekas`

---

## 1. Deskripsi Singkat Program
Program **Sistem Donasi Barang Bekas** adalah aplikasi berbasis CLI Java yang digunakan untuk mengelola data barang donasi dari masyarakat secara permanen menggunakan basis data MySQL. Program menerapkan arsitektur **MVC**, pola **DAO (Data Access Object)**, serta query berparameter dengan **Prepared Statement JDBC** untuk menjamin keamanan dari serangan *SQL Injection*.

---

## 2. Penjelasan Struktur Package
Proyek ini disusun secara modular berbasis pola arsitektur MVC dan JDBC Data Layer:

1. `config`:
   - `Koneksi.java`: Mengelola pembuatan koneksi (*Connection*) ke database MySQL `db_donasi_barang`.
2. `model`:
   - `Donasi.java`: Abstract class dasar untuk entitas donasi.
   - `DonasiPakaian.java` & `DonasiElektronik.java`: Subclass spesifik entitas barang.
   - `StatusDonasi.java`: Interface penyedia kontrak status kelayakan donasi.
   - `DonasiDAO.java`: Kelas DAO yang mengeksekusi perintah SQL CRUD dan pemetaan *Object Relational Mapping (ORM)*.
3. `controller`:
   - `DonasiController.java`: Mengubungkan interaksi layer View dengan logika akses basis data pada DAO.
4. `view`:
   - `DonasiView.java`: Menangani antarmuka CLI, pilihan menu, serta validasi *input* pengguna.
5. `main`:
   - `MainApp.java`: Class *entry point* utama penggerak aplikasi.

---

## 3. Penjelasan Alur Program
1. **Inisialisasi Koneksi**: Program diinstalasi melalui `MainApp.java` yang memanggil `DonasiView.start()`.
2. **Read Auto-Load**: Saat menu Tampilkan Seluruh Donasi dipilih, `DonasiDAO` mengeksekusi query `SELECT * FROM donasi` menggunakan Prepared Statement, lalu otomatis mengembalikan data dummy awal dari file `.sql`.
3. **Tambah Data (Create)**: Input dari pengguna dikirim oleh Controller ke `DonasiDAO` yang menjalankan `INSERT INTO donasi` dengan simbol placeholder (`?`).
4. **Ubah Data (Update)**: Mengubah record secara spesifik di database melalui `UPDATE donasi SET ... WHERE id_donasi = ?`.
5. **Hapus Data (Delete)**: Menghapus record terpilih dari basis data menggunakan `DELETE FROM donasi WHERE id_donasi = ?`.
6. **Pencarian Data**: Melakukan query pola `LIKE %nama%` berparameter.

---

## 4. Penjelasan Penerapan Encapsulation dan Inheritance
* **Encapsulation**:
  * Seluruh atribut entitas bertipe `private` (`idDonasi`, `namaPendonor`, `namaBarang`, `jumlahBarang`, `ukuran`, `merek`, `masihBerfungsi`).
  * Akses mutasi dan baca dialirkan melalui `getter` dan `setter` dengan validasi kondisi.
* **Inheritance**:
  * Class `DonasiPakaian` dan `DonasiElektronik` mewarisi (*extends*) seluruh karakteristik dari class induk `Donasi`.

---

## 5. Penjelasan Penerapan Polymorphism dan Abstraction
* **Abstraction**:
  * Class `Donasi` berstatus `abstract class`.
  * Memiliki abstract method `getKategori()` dan `getDetailRincian()` yang diimplementasikan secara dinamis oleh subclass-nya.
* **Polymorphism**:
  * **Overriding**: Implementasi method `tampilkanInfo()`, `getKategori()`, dan `getDetailRincian()` pada subclass.
  * **Overloading**: Constructor ganda pada class `Donasi` serta variasi method `tampilkanInfo()`.

---

## 6. Penjelasan Penerapan JDBC & Prepared Statement
* **JDBC Driver**: Menggunakan `com.mysql.cj.jdbc.Driver` yang dihubungkan melalui `DriverManager.getConnection()`.
* **Prepared Statement & Anti-SQL Injection**: Seluruh transaksi basis data di `DonasiDAO.java` menggunakan `PreparedStatement` dengan sintaks placeholder (`?`). Nilai ditautkan menggunakan method `setString()`, `setInt()`, `setBoolean()`, dan `setNull()`, menjamin parameter diperlakukan murni sebagai data bukannya klausa SQL.

---

## 7. Penjelasan Letak Penerapan Nilai Tambah (Framework ORM / ORM Pattern)
* **Object-Relational Mapping (ORM-Like Concept)**:
  * Pada kelas `DonasiDAO.java`, diterapkan method mapper `mapResultSetToEntity(ResultSet rs)`. Method ini secara otomatis mengubah (*mapping*) data mentah baris tabel relasional MySQL menjadi instance objek domain polymorphism Java (`DonasiPakaian` atau `DonasiElektronik`).
     <img width="530" height="229" alt="image" src="https://github.com/user-attachments/assets/960d6dc5-e520-4b16-ba56-c728f4c368a2" />

  * Konversi tipe data relational (seperti `TINYINT` / `BOOLEAN` ke `boolean` Java) diisikan otomatis ke atribut objek domain tanpa perlu *manual mapping* di layer View.

---

## 8. Contoh Output Program & Screenshots

### BAGIAN YANG DI-SCREENSHOT (SS) PADA RUNTIME NETBEANS:
1. **SS 1 (Tampil Data Awal / Read Dummy SQL)**:
   - <img width="1429" height="463" alt="WhatsApp Image 2026-10-15 at 23 36 36" src="https://github.com/user-attachments/assets/f8badfa1-a923-4fe6-ab7b-bd22efbcb5a6" />

2. **SS 2 (Tambah Data Donasi / Create JDBC)**:
   - <img width="416" height="227" alt="image" src="https://github.com/user-attachments/assets/20305aa1-a80d-4f22-8a1a-cd6861bca2f8" />

3. **SS 3 (Ubah Data / Update Prepared Statement)**:
   - <img width="720" height="356" alt="image" src="https://github.com/user-attachments/assets/d5c20094-4915-4379-ba8d-94a4af5972e2" />

4. **SS 4 (Cari Data Donasi / Search Pattern)**:
   - <img width="613" height="381" alt="WhatsApp Image 2026-10-15 at 23 45 01" src="https://github.com/user-attachments/assets/09f54f39-9d24-4947-b3a9-d26033ef7dc7" />

5. **SS 5 (Hapus Data / Delete JDBC)**:
   - <img width="710" height="292" alt="image" src="https://github.com/user-attachments/assets/2f011fcc-ec26-4f73-bb3e-ce94daa0788f" />


---

### Contoh Tampilan Console Text:

```text
==========================================
   SISTEM MANAJEMEN DONASI BARANG BEKAS   
==========================================
1. Tambah Data Donasi
2. Tampilkan Seluruh Donasi
3. Ubah Data Donasi
4. Hapus Data Donasi
5. Cari Donasi Berdasarkan Pendonor
0. Keluar
------------------------------------------
Pilih menu (0-5): 2

=== DAFTAR DONASI BARANG BEKAS (DATABASE) ===
1. [Pakaian] ID: D001 | Pendonor: Budi Santoso | Barang: Jaket Hoodie | Jumlah: 3 unit | Status: Siap Disalurkan ke Panti/Posko | Ukuran: L
   Detail Rincian: Ukuran Pakaian: L
2. [Pakaian] ID: D002 | Pendonor: Siti Aminah | Barang: Kemeja Batik | Jumlah: 5 unit | Status: Siap Disalurkan ke Panti/Posko | Ukuran: All Size
   Detail Rincian: Ukuran Pakaian: All Size
3. [Elektronik] ID: D003 | Pendonor: Andi Wijaya | Barang: Kipas Angin | Jumlah: 2 unit | Status: Siap Disalurkan | Merek: Miyako | Berfungsi: Ya
   Detail Rincian: Merek: Miyako | Kondisi Berfungsi: Ya
4. [Elektronik] ID: D004 | Pendonor: Rina Lestari | Barang: Setrika | Jumlah: 1 unit | Status: Butuh Perbaikan/Service | Merek: Philips | Berfungsi: Tidak
   Detail Rincian: Merek: Philips | Kondisi Berfungsi: Tidak
