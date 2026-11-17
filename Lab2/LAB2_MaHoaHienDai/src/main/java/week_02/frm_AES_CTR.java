package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_AES_CTR extends JFrame {

    private JTextArea txt_plaintext, txt_ciphertext;
    private JTextField txt_key, txt_nonce;
    private JButton btn_genkey, btn_gennonce, btn_encrypt, btn_decrypt, btn_save, btn_open;
    private JLabel lbl_status;

    public frm_AES_CTR() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("AES-CTR Cipher");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("BÀI NÂNG CAO 01 - AES-256 CHẾ ĐỘ CTR");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        JLabel lblPlaintext = new JLabel("Plaintext:");
        lblPlaintext.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblPlaintext.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblPlaintext);

        txt_plaintext = new JTextArea(3, 40);
        txt_plaintext.setLineWrap(true);
        txt_plaintext.setWrapStyleWord(true);
        JScrollPane spPlain = new JScrollPane(txt_plaintext);
        spPlain.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(spPlain);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel keyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        keyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        keyPanel.add(new JLabel("Key (Base64): "));
        txt_key = new JTextField(25);
        keyPanel.add(txt_key);
        btn_genkey = new JButton("Generate Key");
        keyPanel.add(btn_genkey);
        mainPanel.add(keyPanel);
        mainPanel.add(Box.createVerticalStrut(5));

        JPanel noncePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        noncePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        noncePanel.add(new JLabel("Nonce (Base64): "));
        txt_nonce = new JTextField(25);
        noncePanel.add(txt_nonce);
        btn_gennonce = new JButton("Generate Nonce");
        noncePanel.add(btn_gennonce);
        mainPanel.add(noncePanel);
        mainPanel.add(Box.createVerticalStrut(10));

        JLabel lblCiphertext = new JLabel("Ciphertext:");
        lblCiphertext.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCiphertext.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lblCiphertext);

        txt_ciphertext = new JTextArea(3, 40);
        txt_ciphertext.setLineWrap(true);
        txt_ciphertext.setWrapStyleWord(true);
        JScrollPane spCipher = new JScrollPane(txt_ciphertext);
        spCipher.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(spCipher);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn_encrypt = new JButton("Encrypt");
        btn_decrypt = new JButton("Decrypt");
        btn_save = new JButton("Save File");
        btn_open = new JButton("Open File");
        btnPanel.add(btn_encrypt);
        btnPanel.add(btn_decrypt);
        btnPanel.add(btn_save);
        btnPanel.add(btn_open);
        mainPanel.add(btnPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        lbl_status = new JLabel("Sẵn sàng.");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl_status.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(lbl_status);

        add(mainPanel, BorderLayout.CENTER);
        pack();

        btn_genkey.addActionListener(e -> {
            txt_key.setText(AESCTRCipher.newKey());
            lbl_status.setText("Đã tạo khóa ngẫu nhiên 256 bit.");
        });

        btn_gennonce.addActionListener(e -> {
            txt_nonce.setText(AESCTRCipher.newNonce());
            lbl_status.setText("Đã tạo nonce ngẫu nhiên 96 bit. Mỗi lần mã hóa nên tạo nonce mới.");
        });

        btn_encrypt.addActionListener(e -> {
            String plaintext = txt_plaintext.getText();
            if (plaintext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
                return;
            }
            try {
                String ciphertext = AESCTRCipher.encrypt(plaintext, txt_key.getText(), txt_nonce.getText());
                txt_ciphertext.setText(ciphertext);
                lbl_status.setText("<html>Đã mã hóa AES-CTR. " + HistoryDao.tryInsert("AES-CTR", "ENCRYPT", null,
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
            String ciphertext = txt_ciphertext.getText();
            if (ciphertext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Ciphertext trước khi giải mã.");
                return;
            }
            try {
                String plaintext = AESCTRCipher.decrypt(ciphertext, txt_key.getText(), txt_nonce.getText());
                txt_plaintext.setText(plaintext);
                lbl_status.setText("<html>Đã giải mã AES-CTR. " + HistoryDao.tryInsert("AES-CTR", "DECRYPT", null,
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
            if (txt_ciphertext.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Chưa có bản mã để lưu.");
                return;
            }
            String content = AESCTRCipher.toFile(txt_nonce.getText(), txt_ciphertext.getText());
            String path = FileTextIO.saveString(this, content, "aes_ctr_ciphertext.txt");
            if (path != null) {
                lbl_status.setText("Đã lưu nonce + bản mã (không lưu Key) vào: " + path);
            }
        });

        btn_open.addActionListener(e -> {
            String content = FileTextIO.openString(this);
            if (content == null) {
                return;
            }
            try {
                String[] parts = AESCTRCipher.fromFile(content);
                txt_nonce.setText(parts[0]);
                txt_ciphertext.setText(parts[1]);
                lbl_status.setText("Đã mở tệp. Nhập đúng Key rồi bấm Decrypt.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
