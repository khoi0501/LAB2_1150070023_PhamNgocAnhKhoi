package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_TripleDES extends JFrame {

    private JTextArea txt_plaintext, txt_ciphertext;
    private JTextField txt_key;
    private JButton btn_encrypt, btn_decrypt, btn_save, btn_open;
    private JLabel lbl_status;

    public frm_TripleDES() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("TripleDES Cipher");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitle = new JLabel("MẬT MÃ TRIPLEDES (3DES)");
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
        txt_key = new JTextField("123456789012345678901234", 25);
        keyPanel.add(txt_key);
        JLabel lblKeyHint = new JLabel("Đúng 24 ký tự (24 byte)");
        lblKeyHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblKeyHint.setForeground(Color.GRAY);
        keyPanel.add(lblKeyHint);
        mainPanel.add(keyPanel);
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

        btn_encrypt.addActionListener(e -> {
            String plaintext = txt_plaintext.getText();
            if (plaintext.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hãy nhập Plaintext trước khi mã hóa.");
                return;
            }
            try {
                String ciphertext = TripleDESCipher.encrypt(plaintext, txt_key.getText());
                txt_ciphertext.setText(ciphertext);
                lbl_status.setText("<html>Đã mã hóa TripleDES. " + HistoryDao.tryInsert("TripleDES", "ENCRYPT", null,
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
                String plaintext = TripleDESCipher.decrypt(ciphertext, txt_key.getText());
                txt_plaintext.setText(plaintext);
                lbl_status.setText("<html>Đã giải mã TripleDES. " + HistoryDao.tryInsert("TripleDES", "DECRYPT", null,
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
            String path = FileTextIO.saveText(this, txt_ciphertext, "tripledes_ciphertext.txt");
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
