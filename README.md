# Pinjam Pustaka

## Deskripsi
Aplikasi Pinjam Pustaka adalah aplikasi berbasis Java Swing yang memungkinkan pengguna untuk meminjam buku dari perpustakaan. Aplikasi ini mencakup fitur login, registrasi, pencarian buku, peminjaman buku, dan logout. Data buku yang tersedia dimuat secara statis, dan aplikasi juga memungkinkan pengguna melihat gambar buku yang dipinjam.

---

## Fitur Utama
1. **Login dan Registrasi**
   - Pengguna dapat login dengan kredensial yang telah terdaftar.
   - Pengguna baru dapat mendaftar dengan mengisi username, password, dan nama sekolah.

2. **Peminjaman Buku**
   - Pengguna dapat mencari buku berdasarkan judul.
   - Buku yang ditemukan dapat dipinjam dengan memasukkan tanggal peminjaman.
   - Tanggal pengembalian dihitung otomatis (7 hari dari tanggal peminjaman).

3. **Tabel Informasi Peminjaman**
   - Menampilkan data peminjam, buku, tanggal peminjaman, tanggal pengembalian, status, dan gambar buku.

4. **Gambar Buku**
   - Gambar buku ditampilkan di tabel jika tersedia. Jika tidak, gambar default akan ditampilkan.

5. **Logout**
   - Pengguna dapat logout dan kembali ke halaman login.

---

## Cara Menjalankan Aplikasi
1. Pastikan Java Development Kit (JDK) sudah terinstall di komputer Anda.
2. Simpan file `BookLendingApp.java` dan semua gambar buku (dalam folder `images/`) di direktori yang sama.
3. Buka terminal atau IDE Java favorit Anda.
4. Kompilasi file:
   ```
   javac BookLendingApp.java
   ```
5. Jalankan aplikasi:
   ```
   java BookLendingApp
   ```

---

## Struktur Proyek
```
├── BookLendingApp.java  # File utama aplikasi
├── images/              # Folder berisi gambar buku
│   ├── bumi_manusia.jpg
│   ├── laskar_pelangi.jpg
│   ├── negeri_5_menara.jpg
│   ├── sang_pemimpi.jpg
│   └── default.jpg      # Gambar default jika buku tidak memiliki gambar
```

---

## Daftar Buku
| Judul Buku                  | Penulis                  | Tahun | Genre            |
|-----------------------------|--------------------------|-------|------------------|
| Bumi Manusia               | Pramoedya Ananta Toer    | 1980  | Fiksi sejarah    |
| Laskar Pelangi             | Andrea Hirata            | 2005  | Fiksi inspiratif |
| Negeri 5 Menara            | Ahmad Fuadi              | 2009  | Fiksi inspiratif |
| Sang Pemimpi               | Andrea Hirata            | 2006  | Fiksi inspiratif |
| Habis Gelap Terbitlah Terang | R.A. Kartini            | 1911  | Filsafat         |

---



