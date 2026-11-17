package week_02;

import javax.swing.*;
import java.awt.*;

public class frm_LichSu extends JFrame {

    private JTable tbl_history;
    private JButton btn_refresh;
    private JLabel lbl_status;

    public frm_LichSu() {
        initComponents();
        setLocationRelativeTo(null);
        tbl_history.setModel(HistoryDao.emptyModel());
        loadData();
    }

    private void initComponents() {
        setTitle("Lịch sử thao tác (SQL Server)");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        JLabel lblTitle = new JLabel("LỊCH SỬ THAO TÁC - BẢNG DBO.CRYPTO_HISTORY");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        topPanel.add(lblTitle, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        tbl_history = new JTable();
        JScrollPane scrollPane = new JScrollPane(tbl_history);
        scrollPane.setPreferredSize(new Dimension(760, 260));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        btn_refresh = new JButton("Tải lại");
        btn_refresh.setPreferredSize(new Dimension(110, 30));
        btn_refresh.addActionListener(e -> loadData());
        bottomPanel.add(btn_refresh, BorderLayout.WEST);

        lbl_status = new JLabel("Đang tải...");
        lbl_status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bottomPanel.add(lbl_status, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
        pack();
    }

    private void loadData() {
        try {
            tbl_history.setModel(HistoryDao.loadLatest(50));
            lbl_status.setText("Đã tải " + tbl_history.getRowCount() + " dòng mới nhất từ dbo.crypto_history.");
        } catch (java.sql.SQLException ex) {
            lbl_status.setText("Không đọc được lịch sử: " + ex.getMessage());
        }
    }
}
