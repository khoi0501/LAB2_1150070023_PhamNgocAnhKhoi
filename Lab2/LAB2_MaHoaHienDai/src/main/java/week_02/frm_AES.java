package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_AES extends JFrame {

    private String currentUser = null;

    private JTextField txt_username, txt_ticket, txt_key;
    private JPasswordField txt_password;
    private JTextArea txt_plaintext, txt_ciphertext;
    private JButton btn_sql, btn_register, btn_login, btn_encrypt, btn_decrypt, btn_save, btn_open;
    private JLabel lbl_status;

    public frm_AES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES Cipher - Register / Login");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("AES-GCM - ĐĂNG KÝ / ĐĂNG NHẬP (SQL SERVER)");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        // User & Password row
        JPanel authPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        authPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        authPanel.add(new JLabel("Username:"));
        txt_username = new JTextField("sinhvien01", 10);
        authPanel.add(txt_username);
        authPanel.add(new JLabel("Password:"));
        txt_password = new JPasswordField(10);
        authPanel.add(txt_password);
        JLabel lblPassHint = new JLabel("(ít nhất 8 ký tự)");
        lblPassHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblPassHint.setForeground(Color.GRAY);
        authPanel.add(lblPassHint);
        mainPanel.add(authPanel);
        mainPanel.add(Box.createVerticalStrut(5));

        // Auth Buttons
        JPanel authBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        authBtnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn_sql = new JButton("Kết nối SQL Server");
        btn_register = new JButton("Register");
        btn_login = new JButton("Login");
        authBtnPanel.add(btn_sql);
        authBtnPanel.add(btn_register);
        authBtnPanel.add(btn_login);
        mainPanel.add(authBtnPanel);
        mainPanel.add(Box.createVerticalStrut(5));

        // Ticket row
        JPanel ticketPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        ticketPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ticketPanel.add(new JLabel("Ticket:"));
        txt_ticket = new JTextField(25);
        txt_ticket.setEditable(false);
        ticketPanel.add(txt_ticket);
        mainPanel.add(ticketPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Plaintext
        JLabel lblPlain = new JLabel("Plaintext:");
        lblPlain.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPlain.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblPlain);

        txt_plaintext = new JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        JScrollPane spPlain = new JScrollPane(txt_plaintext);
        spPlain.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(spPlain);
        mainPanel.add(Box.createVerticalStrut(10));

        // Key AES
        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
        keyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        keyPanel.add(new JLabel("Key AES:"));
        txt_key = new JTextField(15);
        keyPanel.add(txt_key);
        JLabel lblKeyHint = new JLabel("Mật khẩu để tạo khóa AES-256");
        lblKeyHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblKeyHint.setForeground(Color.GRAY);
        keyPanel.add(lblKeyHint);
        mainPanel.add(keyPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Ciphertext
        JLabel lblCipher = new JLabel("Ciphertext:");
        lblCipher.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCipher.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblCipher);

        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        JScrollPane spCipher = new JScrollPane(txt_ciphertext);
        spCipher.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(spCipher);
        mainPanel.add(Box.createVerticalStrut(10));

        // Action Buttons
        JPanel actionBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actionBtnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn_encrypt = new JButton("Encrypt");
        btn_decrypt = new JButton("Decrypt");
        btn_save = new JButton("Save File");
        btn_open = new JButton("Open File");
        actionBtnPanel.add(btn_encrypt);
        actionBtnPanel.add(btn_decrypt);
        actionBtnPanel.add(btn_save);
        actionBtnPanel.add(btn_open);
        mainPanel.add(actionBtnPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Status
        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lbl_status);

        add(mainPanel, BorderLayout.CENTER);
        pack();

        // Listeners
        btn_sql.addActionListener(e -> new frm_KetNoiSQL().setVisible(true));

        btn_register.addActionListener(e -> {
            try {
                String user = txt_username.getText().trim();
                UserDao.register(user, txt_password.getPassword());
                HistoryDao.tryInsert("AES", "REGISTER", user, 0, 0);
                lbl_status.setText("Register thành công: " + user + ". Bấm Login để đăng nhập.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            } catch (java.sql.SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi SQL Server: " + ex.getMessage(),
                        "Lỗi SQL Server", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_login.addActionListener(e -> {
            try {
                String user = txt_username.getText().trim();
                if (UserDao.login(user, txt_password.getPassword())) {
                    currentUser = user;
                    txt_ticket.setText(UserDao.newTicket());
                    HistoryDao.tryInsert("AES", "LOGIN", user, 0, 0);
                    lbl_status.setText("Login thành công: " + user + ". Có thể dùng Encrypt/Decrypt.");
                } else {
                    currentUser = null;
                    txt_ticket.setText("");
                    JOptionPane.showMessageDialog(this, "Sai username hoặc mật khẩu.",
                            "Login thất bại", JOptionPane.ERROR_MESSAGE);
                }
            } catch (java.sql.SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi SQL Server: " + ex.getMessage(),
                        "Lỗi SQL Server", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_encrypt.addActionListener(e -> {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
                return;
            }
            String plaintext = txt_plaintext.getText();
            if (plaintext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
                return;
            }
            try {
                String ciphertext = AESCipher.encrypt(plaintext, txt_key.getText());
                txt_ciphertext.setText(ciphertext);
                lbl_status.setText("<html>Đã mã hóa AES. " + HistoryDao.tryInsert("AES", "ENCRYPT",
                        currentUser,
                        plaintext.length(), ciphertext.length()) + "</html>");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Mã hóa thất bại:\n" + ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_decrypt.addActionListener(e -> {
            if (currentUser == null) {
                JOptionPane.showMessageDialog(this, "Hãy Login trước khi dùng AES.");
                return;
            }
            String ciphertext = txt_ciphertext.getText();
            if (ciphertext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
                return;
            }
            try {
                String plaintext = AESCipher.decrypt(ciphertext, txt_key.getText());
                txt_plaintext.setText(plaintext);
                lbl_status.setText("<html>Đã giải mã AES. " + HistoryDao.tryInsert("AES", "DECRYPT",
                        currentUser,
                        ciphertext.length(), plaintext.length()) + "</html>");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Dữ liệu không hợp lệ", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Giải mã thất bại: sai Key hoặc bản mã bị sửa.\n" +
                        ex.getMessage(),
                        "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_save.addActionListener(e -> {
            String path = FileTextIO.saveText(this, txt_ciphertext, "aes_ciphertext.txt");
            if (path != null) {
                lbl_status.setText("Đã lưu tệp: " + path);
            }
        });

        btn_open.addActionListener(e -> {
            String path = FileTextIO.openText(this, txt_ciphertext);
            if (path != null) {
                lbl_status.setText("Đã mở tệp: " + path);
            }
        });
    }
}
