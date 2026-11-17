package week_02;
import javax.swing.*;
import java.awt.*;

public class frm_KetNoiSQL extends JFrame {

    private JTextField txt_server, txt_port, txt_database, txt_user;
    private JPasswordField txt_password;
    private JButton btn_test, btn_history;
    private JLabel lbl_status;

    public frm_KetNoiSQL() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Kết nối Microsoft SQL Server");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 8, 5, 8);
        c.anchor = GridBagConstraints.WEST;

        JLabel lblTitle = new JLabel("KẾT NỐI MICROSOFT SQL SERVER");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        c.gridx=0; c.gridy=0; c.gridwidth=2; add(lblTitle, c);

        c.gridwidth=1;
        addRow("Server:", txt_server = new JTextField("127.0.0.1", 20), 1, c);
        addRow("Port:", txt_port = new JTextField("1433", 8), 2, c);
        addRow("Database:", txt_database = new JTextField("LAB2_MaHoaHienDai", 20), 3, c);
        addRow("User:", txt_user = new JTextField("lab2_user", 20), 4, c);
        addRow("Password:", txt_password = new JPasswordField(20), 5, c);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        btn_test = new JButton("Test Connection");
        btn_history = new JButton("Xem lịch sử");
        btnPanel.add(btn_test); btnPanel.add(btn_history);
        c.gridx=0; c.gridy=6; c.gridwidth=2; add(btnPanel, c);

        lbl_status = new JLabel("Chưa kết nối.");
        lbl_status.setPreferredSize(new Dimension(450, 40));
        c.gridy=7; add(lbl_status, c);

        pack();

        btn_test.addActionListener(e -> {
            try {
                int port = Integer.parseInt(txt_port.getText().trim());
                DbConfig.set(txt_server.getText().trim(), port, txt_database.getText().trim(),
                        txt_user.getText().trim(), txt_password.getPassword());
                lbl_status.setText("<html>" + DbConfig.test() + "</html>");
            } catch (Exception ex) {
                DbConfig.clear();
                lbl_status.setText("<html>Kết nối thất bại: " + ex.getMessage() + "</html>");
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Không kết nối được SQL Server", JOptionPane.ERROR_MESSAGE);
            }
        });

        btn_history.addActionListener(e -> new frm_LichSu().setVisible(true));
    }

    private void addRow(String label, JComponent field, int row, GridBagConstraints c) {
        c.gridx=0; c.gridy=row; c.gridwidth=1; add(new JLabel(label), c);
        c.gridx=1; add(field, c);
    }
}
