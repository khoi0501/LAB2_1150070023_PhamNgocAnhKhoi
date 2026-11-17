package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_Twofish extends JFrame {

    private JTextArea txt_plaintext, txt_ciphertext;
    private JTextField txt_key, txt_iv;
    private JButton btn_iv, btn_encrypt, btn_decrypt, btn_save, btn_open;
    private JLabel lbl_status;

    public frm_Twofish() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Twofish Cipher");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("MẬT MÃ TWOFISH (CBC)");
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
        keyPanel.add(new JLabel("Key: "));
        txt_key = new JTextField("Twofish@Lab2", 15);
        keyPanel.add(txt_key);
        JLabel lblKeyHint = new JLabel("Mật khẩu bất kỳ (khóa = SHA-256)");
        lblKeyHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblKeyHint.setForeground(Color.GRAY);
        keyPanel.add(lblKeyHint);
        mainPanel.add(keyPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        JPanel ivPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        ivPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        ivPanel.add(new JLabel("IV (HEX): "));
        txt_iv = new JTextField(24);
        ivPanel.add(txt_iv);
        btn_iv = new JButton("Generate IV");
        ivPanel.add(btn_iv);
        mainPanel.add(ivPanel);
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

        btn_iv.addActionListener(e -> {
            txt_iv.setText(TwofishCipher.newIv());
            lbl_status.setText("Đã tạo IV ngẫu nhiên 16 byte. Giữ nguyên IV này để giải mã.");
        });

        btn_encrypt.addActionListener(e -> {
            String plaintext = txt_plaintext.getText();
            if (plaintext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
                return;
            }
            try {
                String ciphertext = TwofishCipher.encrypt(plaintext, txt_key.getText(), txt_iv.getText());
                txt_ciphertext.setText(ciphertext);
                lbl_status.setText("<html>Đã mã hóa Twofish. " + HistoryDao.tryInsert("Twofish", "ENCRYPT", null,
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
                String plaintext = TwofishCipher.decrypt(ciphertext, txt_key.getText(), txt_iv.getText());
                txt_plaintext.setText(plaintext);
                lbl_status.setText("<html>Đã giải mã Twofish. " + HistoryDao.tryInsert("Twofish", "DECRYPT", null,
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
            String content = TwofishCipher.toFile(txt_iv.getText(), txt_ciphertext.getText());
            String path = FileTextIO.saveString(this, content, "twofish_ciphertext.txt");
            if (path != null) {
                lbl_status.setText("Đã lưu IV + bản mã vào: " + path);
            }
        });

        btn_open.addActionListener(e -> {
            String content = FileTextIO.openString(this);
            if (content == null) {
                return;
            }
            try {
                String[] parts = TwofishCipher.fromFile(content);
                txt_iv.setText(parts[0]);
                txt_ciphertext.setText(parts[1]);
                lbl_status.setText("Đã mở tệp, khôi phục IV và bản mã. Nhập đúng Key rồi bấm Decrypt.");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Tệp không hợp lệ", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
