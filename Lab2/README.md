# BÁO CÁO THỰC HÀNH LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI
**HỌC PHẦN: AN TOÀN VÀ BẢO MẬT THÔNG TIN**

---

## THÔNG TIN SINH VIÊN
- **Họ và tên:** Phạm Ngọc Anh Khôi
- **MSSV:** 1150070023
- **Lớp:** 11-TMDT 
- **Link Youtube:** https://www.youtube.com/watch?v=abznYImFy_M

---

## 1. CẤU TRÚC THƯ MỤC DỰ ÁN

```
Downloads\ATTBMTT\
├── LAB2_HuongDan.pdf           # File tài liệu hướng dẫn Lab 2
├── README.md                   # File thông tin và hướng dẫn chạy chương trình
├── LAB2_Python\
│   └── aes_cbc_gui.py          # Bài nâng cao 02: AES-256-CBC bằng Python (Tkinter)
└── LAB2_MaHoaHienDai\          # Project Java Maven (NetBeans)
    ├── pom.xml                 # Cấu hình thư viện Maven (JDK 17, mssql-jdbc, BouncyCastle)
    ├── sql\                    # Các script SQL Server
    │   ├── 01_Tao_CSDL.sql          # Tạo database và bảng app_users, crypto_history
    │   ├── 02_Tao_Tai_Khoan_SQL.sql # Tạo login/user lab2_user và cấp quyền
    │   └── 03_Kiem_Tra_Du_Lieu.sql  # Truy vấn kiểm tra dữ liệu sau khi chạy app
    └── src\main\java\week_02\
        ├── CipherUtil.java          # Tiện ích chuyển đổi Base64, HEX, Salt, PBKDF2
        ├── FileTextIO.java          # Đọc và lưu file văn bản (.txt)
        ├── DbConfig.java            # Quản lý kết nối JDBC SQL Server
        ├── HistoryDao.java          # Ghi/đọc lịch sử bảng dbo.crypto_history
        ├── UserDao.java             # Đăng ký, đăng nhập tài khoản (PBKDF2)
        ├── DESCipher.java           # Thuật toán DES chế độ CBC, đệm PKCS5
        ├── TripleDESCipher.java     # Thuật toán TripleDES (3DES), đệm PKCS5
        ├── AESCipher.java           # Thuật toán AES-256 chế độ GCM
        ├── RC4Cipher.java           # Thuật toán mã dòng RC4 (KSA + PRGA)
        ├── TwofishCipher.java       # Thuật toán Twofish chế độ CBC (BouncyCastle)
        ├── AESCTRCipher.java        # Thuật toán AES-256 chế độ CTR (mã dòng, no padding)
        ├── frm_Menu.java            # Cửa sổ chính điều hướng toàn bộ ứng dụng
        ├── frm_DES.java             # Giao diện Bài 2.1
        ├── frm_TripleDES.java       # Giao diện Bài 2.2
        ├── frm_AES.java             # Giao diện Bài 2.3
        ├── frm_RC4.java             # Giao diện Bài 2.4
        ├── frm_Twofish.java         # Giao diện Bài 2.5
        ├── frm_AES_CTR.java         # Giao diện Bài 2.6 Nâng cao 01
        ├── frm_KetNoiSQL.java       # Form cấu hình & kiểm tra kết nối SQL Server
        └── frm_LichSu.java          # Form xem lịch sử thao tác bảng crypto_history
```

---

## 2. CHUẨN BỊ CƠ SỞ DỮ LIỆU SQL SERVER

Trước khi chạy ứng dụng Java, cần khởi tạo CSDL trong SQL Server Management Studio (SSMS):

1. **Mở SSMS**, kết nối bằng tài khoản quản trị (**Windows Authentication**).
2. Mở file `sql\01_Tao_CSDL.sql` và bấm **Execute (F5)** để tạo CSDL `LAB2_MaHoaHienDai` cùng 2 bảng:
   - `dbo.app_users`: Lưu tài khoản đăng ký (chỉ lưu salt và password hash bằng PBKDF2).
   - `dbo.crypto_history`: Lưu lịch sử thao tác mã hóa/giải mã.
3. Mở file `sql\02_Tao_Tai_Khoan_SQL.sql` và bấm **Execute (F5)** để tạo tài khoản:
   - **Login / User:** `lab2_user`
   - **Mật khẩu:** `LabUser@2026`
   - Được cấp quyền `db_datareader` và `db_datawriter`.

---

## 3. HƯỚNG DẪN CHẠY ỨNG DỤNG JAVA (NETBEANS)

### Cách chạy trong Apache NetBeans:
1. Mở NetBeans ➔ chọn **File** ➔ **Open Project...** ➔ duyệt đến thư mục:
   `Downloads\ATTBMTT\LAB2_MaHoaHienDai`
2. Nhấp chuột phải vào tên project ➔ chọn **Clean and Build** (phím tắt `Shift + F11`) để Maven tải thư viện và biên dịch.
3. Nhấp chuột phải vào file **`frm_Menu.java`** trong package `week_02` ➔ chọn **Run File** (phím tắt `Shift + F6`), hoặc nhấn **`F6`** (Run Project).

### Kiểm tra kết nối SQL từ giao diện:
1. Trên form Menu, bấm nút **Kết nối SQL Server**.
2. Điền thông tin:
   - **Server:** `127.0.0.1`
   - **Port:** `1433` *(hoặc `14333` tùy cấu hình SQL Server)*
   - **Database:** `LAB2_MaHoaHienDai`
   - **User:** `lab2_user`
   - **Password:** `LabUser@2026`
3. Bấm **Test Connection** ➔ Hệ thống thông báo: `Kết nối thành công: Microsoft SQL Server...`.

---

## 4. HƯỚNG DẪN CHẠY BÀI NÂNG CAO PYTHON (AES-CBC)

Yêu cầu máy đã cài **Python 3.10+**.

1. Cài đặt thư viện mã hóa `cryptography`:
   ```bash
   pip install cryptography
   ```
2. Mở giao diện ứng dụng đồ họa (Tkinter):
   ```bash
   py LAB2_Python\aes_cbc_gui.py
   ```
   - Bấm **Generate Key** và **Generate IV**.
   - Nhập đoạn văn bản bất kỳ vào ô Plaintext.
   - Bấm **Encrypt** để mã hóa, bấm **Decrypt** để giải mã.
3. Chạy kiểm thử tự động (Unit Test / Self-Test):
   ```bash
   py LAB2_Python\aes_cbc_gui.py --self-test
   ```
   - Kết quả mong đợi: `SELF-TEST OK: 4 thông điệp mã hóa/giải mã đúng, sai khóa bị phát hiện.`

---

## 5. DỮ LIỆU KIỂM THỬ BẮT BUỘC (TEST CASES)

| Bài | Tên thuật toán | Dữ liệu thử nghiệm | Kết quả kiểm thử đạt chuẩn |
|:---:|:---|:---|:---|
| **2.1** | **DES** | Plaintext: `Xin chào Lab 2 - Mã hóa hiện đại!`<br>Key: `12345678` | `Decrypt(Encrypt(x)) = x`. Mỗi lần Encrypt sinh ra bản mã khác nhau do IV ngẫu nhiên. Nhập Key sai/thiếu ký tự sẽ báo lỗi. |
| **2.2** | **TripleDES** | Plaintext: `Xin chào Lab 2 - Mã hóa hiện đại!`<br>Key: `123456789012345678901234` | Giải mã đúng nguyên văn. Thử xóa 1 ký tự Key (còn 23 ký tự) chương trình báo lỗi bắt buộc đúng 24 byte. |
| **2.3** | **AES-GCM + Login** | Đăng ký user: `sinhvien01`, pass: `Password@123`<br>Key AES: `MatKhauAES@2026` | Đăng ký & đăng nhập thành công, cấp mã Ticket 128-bit. Giải mã đúng. Thử sửa 1 ký tự bản mã sẽ bị GCM bắt lỗi giả mạo ngay. Dữ liệu được lưu vào SQL Server. |
| **2.4** | **RC4** | Plaintext: `Plaintext`<br>Key: `Key` | Bản mã xuất ra đúng chuẩn HEX: **`BBF316E8D940AF0AD3`**. Giải mã ra lại chữ gốc. |
| **2.5** | **Twofish** | Key: `Twofish@Lab2`<br>IV: Sinh ngẫu nhiên bằng nút `Generate IV` | Giải mã đúng. Thử bấm sinh IV mới rồi giải mã sẽ báo lỗi (chứng minh chế độ CBC yêu cầu khớp cả Key và IV). Chức năng Save/Open File khôi phục đầy đủ IV. |
| **2.6** | **AES-CTR (Java)** | Key & Nonce: Sinh ngẫu nhiên<br>Plaintext: Văn bản dài (> 500 ký tự) | Không cần cơ chế đệm (no padding), độ dài bản mã bằng đúng bản rõ. Giải mã đúng 100% văn bản dài tiếng Việt. |
| **2.6** | **AES-CBC (Python)**| Key & IV: Base64 ngẫu nhiên<br>Plaintext: Văn bản dài tiếng Việt | Đệm PKCS7 chuẩn 16 byte. Giải mã hoàn chỉnh. Chạy `--self-test` đạt PASS. |

---

## 6. XEM LỊCH SỬ THAO TÁC CSDL
- **Trên ứng dụng Java:** Bấm nút **Xem lịch sử** trên Menu chính để mở form `frm_LichSu`, bảng hiển thị 50 thao tác gần nhất được tải trực tiếp từ SQL Server.
- **Trên SSMS:** Mở file `sql\03_Kiem_Tra_Du_Lieu.sql` và bấm **Execute (F5)** để truy vấn toàn bộ bản ghi trong bảng `dbo.app_users` và `dbo.crypto_history`.
