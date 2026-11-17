package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_Menu extends JFrame {

    private JButton btn_des, btn_tripledes, btn_aes, btn_rc4, btn_twofish, btn_aesctr, btn_sql, btn_history;

    public frm_Menu() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("LAB 2 - Mã hóa đối xứng hiện đại");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitle = new JLabel("LAB 2 - MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        JLabel lblSub = new JLabel("Chọn một bài để mở form tương ứng:");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblSub);
        mainPanel.add(Box.createVerticalStrut(20));

        // Grid for buttons
        JPanel gridPanel = new JPanel(new GridLayout(4, 2, 15, 12));
        gridPanel.setMaximumSize(new Dimension(500, 180));

        btn_des = new JButton("2.1 DES");
        btn_tripledes = new JButton("2.2 TripleDES");
        btn_aes = new JButton("2.3 AES + Login");
        btn_rc4 = new JButton("2.4 RC4");
        btn_twofish = new JButton("2.5 Twofish");
        btn_aesctr = new JButton("2.6 Nâng cao - AES-CTR");
        btn_sql = new JButton("Kết nối SQL Server");
        btn_history = new JButton("Xem lịch sử");

        Font btnFont = new Font("Segoe UI", Font.PLAIN, 13);
        JButton[] buttons = {btn_des, btn_tripledes, btn_aes, btn_rc4, btn_twofish, btn_aesctr, btn_sql, btn_history};
        for (JButton b : buttons) {
            b.setFont(btnFont);
            b.setPreferredSize(new Dimension(200, 35));
            gridPanel.add(b);
        }

        mainPanel.add(gridPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JLabel lblNote = new JLabel("Bài nâng cao 02 (AES-CBC) viết bằng Python: chạy tệp aes_cbc_gui.py.");
        lblNote.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblNote.setForeground(Color.DARK_GRAY);
        lblNote.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblNote);

        add(mainPanel, BorderLayout.CENTER);
        pack();

        // Listeners
        btn_des.addActionListener(e -> new frm_DES().setVisible(true));
        btn_tripledes.addActionListener(e -> new frm_TripleDES().setVisible(true));
        btn_aes.addActionListener(e -> new frm_AES().setVisible(true));
        btn_rc4.addActionListener(e -> new frm_RC4().setVisible(true));
        btn_twofish.addActionListener(e -> new frm_Twofish().setVisible(true));
        btn_aesctr.addActionListener(e -> new frm_AES_CTR().setVisible(true));
        btn_sql.addActionListener(e -> new frm_KetNoiSQL().setVisible(true));
        btn_history.addActionListener(e -> new frm_LichSu().setVisible(true));
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
        SwingUtilities.invokeLater(() -> new frm_Menu().setVisible(true));
    }
}
