package Payment;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class FrmPayment extends JFrame {

    private JTextField txtSearch;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnExit;
    private JButton btnAdd;
    private JTextField txtPayDate;

    private JTextField txtAmount;
    private JTextField txtDescription;
    private JTextField txtLibrarianName;
    private JPanel PaymentPanel;
    private JTextField txtReaderID;
    private JTextField txtLibrarianID;
    private JButton btnSearch;
    private JPanel PanelData;
    private JTable tblData;
    private LocalDate today;

    public FrmPayment() {
        today = LocalDate.now();
        btnSearch.setPreferredSize(new Dimension(150, 40));
        txtSearch.setPreferredSize(new Dimension(150, 40));
        txtLibrarianName.setPreferredSize(new Dimension(400, 40));
        txtDescription.setPreferredSize(new Dimension(400, 40));
        txtPayDate.setPreferredSize(new Dimension(150, 40));
        txtAmount.setPreferredSize(new Dimension(150, 40));
        txtReaderID.setPreferredSize(new Dimension(150, 40));
        txtLibrarianID.setPreferredSize(new Dimension(150, 40));
        btnDelete.setPreferredSize(new Dimension(150, 40));
        btnExit.setPreferredSize(new Dimension(150, 40));
        btnAdd.setPreferredSize(new Dimension(150, 40));
        btnUpdate.setPreferredSize(new Dimension(150, 40));


        setTitle("Payment's From");
        setContentPane(PaymentPanel);
        setSize(1400, 900);
        setResizable(false);
        setDefaultCloseOperation(FrmPayment.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("EXIT");
                if (JOptionPane.showConfirmDialog(frame, "Are you sure to Exit!", "EXIT",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                     System.exit(0);
                }

            }
        });
//        Get Data from database to display on JTable
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String url = "jdbc:sqlserver://DESKTOP-3JNS3SB\\SQLEXPRESS;databaseName=PaymentDB;encrypt=true;trustServerCertificate=true";
                    String user = "sa";
                    String password = "Soben18042003";
                    Connection cnt = DriverManager.getConnection(url, user, password);
                    String sql = "INSERT INTO tbPayment(ReaderID,LibrarianID,DescriptionPM,DatePM,Amount,LibrarianName)" +
                            "Values(?,?,?,?,?,?)";
                    PreparedStatement statement = cnt.prepareStatement(sql);
                    statement.setString(1, txtReaderID.getText());
                    statement.setString(2, txtLibrarianID.getText());
                    statement.setString(3, txtDescription.getText());
                    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy"); // Adjust format as needed
                    try {
                        Date parsedDate = format.parse(txtPayDate.getText());
                        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                        statement.setDate(4, sqlDate);

                    } catch (ParseException d) {
                        // Handle parsing exception (e.g., invalid date format)
                        d.printStackTrace();
                        // You may want to display an error message to the user
                    }

                    statement.setString(5, txtAmount.getText());
                    statement.setString(6, txtLibrarianName.getText());
                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record has been added successfully");

                    txtLibrarianName.setText("");
                    txtPayDate.setText("");
                    txtDescription.setText("");
                    txtAmount.setText("");
                    txtReaderID.setText("");
                    txtLibrarianID.setText("");
                    txtLibrarianName.requestFocusInWindow();

                    fetchData();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
            }
        });

        tblData.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = tblData.getSelectedRow();
                TableModel model =tblData.getModel();
                txtReaderID.setText(model.getValueAt(i,1).toString());
                txtLibrarianID.setText(model.getValueAt(i,2).toString());
                txtDescription.setText(model.getValueAt(i,3).toString());
                txtPayDate.setText(model.getValueAt(i,4).toString());
                txtAmount.setText(model.getValueAt(i,5).toString());
                txtLibrarianName.setText(model.getValueAt(i,6).toString());
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("DELETE");
                if (JOptionPane.showConfirmDialog(frame, "Are you sure to Delete!", "DELETE",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
                    try {
                        String url = "jdbc:sqlserver://DESKTOP-3JNS3SB\\SQLEXPRESS;databaseName=PaymentDB;encrypt=true;trustServerCertificate=true";
                        String user = "sa";
                        String password = "Soben18042003";
                        Connection cnt = DriverManager.getConnection(url, user, password);
                        int row = tblData.getSelectedRow();
                        String value= (tblData.getModel().getValueAt(row,0).toString());
                        String query = "Delete from tbPayment where PaymentID="+value;
                        PreparedStatement pst = cnt.prepareStatement(query);
                        pst.executeUpdate();
                        fetchData();
                        JOptionPane.showMessageDialog(null,"Record has been deleted successfully");
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }


            }
        });
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String url = "jdbc:sqlserver://DESKTOP-3JNS3SB\\SQLEXPRESS;databaseName=PaymentDB;encrypt=true;trustServerCertificate=true";
                    String user = "sa";
                    String password = "Soben18042003";
                    Connection cnt = DriverManager.getConnection(url, user, password);

                    int row = tblData.getSelectedRow();
                    String value = (tblData.getModel().getValueAt(row, 0).toString());

                    String query = "UPDATE tbPayment SET ReaderID=?, LibrarianID=?, DescriptionPM=?, DatePM=?, Amount=?, LibrarianName=? WHERE PaymentID=?";
                    PreparedStatement statement = cnt.prepareStatement(query);

                    statement.setString(1, txtReaderID.getText());
                    statement.setString(2, txtLibrarianID.getText());
                    statement.setString(3, txtDescription.getText());

                    SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy"); // Adjust format as needed
                    try {
                        Date parsedDate = format.parse(txtPayDate.getText());
                        java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
                        statement.setDate(4, sqlDate);
                    } catch (ParseException d) {
                        // Handle parsing exception (e.g., invalid date format)
                        d.printStackTrace();
                        // You may want to display an error message to the user
                        JOptionPane.showMessageDialog(null, "Invalid date format. Please use MM-dd-yyyy format.");
                        return; // Exit the method if date parsing fails
                    }

                    statement.setString(5, txtAmount.getText());
                    statement.setString(6, txtLibrarianName.getText());
                    statement.setString(7, value);

                    statement.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Record has been updated successfully");

                    // Clear the text fields after updating
                    txtLibrarianName.setText("");
                    txtPayDate.setText("");
                    txtDescription.setText("");
                    txtAmount.setText("");
                    txtReaderID.setText("");
                    txtLibrarianID.setText("");
                    txtLibrarianName.requestFocusInWindow();

                    // Refresh the table data
                    fetchData();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }
        public void connectToDatabase() {
        try {
            // Change these variables according to your database setup
            String url = "jdbc:sqlserver://DESKTOP-3JNS3SB\\SQLEXPRESS;databaseName=PaymentDB;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "Soben18042003";

            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to database successfully...!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Opps, Somthing has an error...!");

        }

    }


    public void fetchData() {
        try {
            String url = "jdbc:sqlserver://DESKTOP-3JNS3SB\\SQLEXPRESS;databaseName=PaymentDB;encrypt=true;trustServerCertificate=true";
            String user = "sa";
            String password = "Soben18042003";

            Connection connection = DriverManager.getConnection(url, user, password);

            // Set table model with column names
            String[] columnNames = {"PaymentID", "ReaderID", "LibrarianID", "Description", "Date", "Amount","LibrarianName"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 0 initial rows
            tblData.setModel(model);
            String query = "Select * from tbPayment";
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            int rowCount = 0;
            model.setRowCount(0); // Clear existing rows (optional)

            while (rs.next()) {
                rowCount++;
                // Retrieve data from the ResultSet
                int paymentID = rs.getInt("PaymentID");
                int readerID = rs.getInt("ReaderID");
                int librarianID = rs.getInt("LibrarianID");
                String description = rs.getString("DescriptionPM");
                Date date = rs.getDate("DatePM");
                double amount = rs.getDouble("Amount");
                String librarianName = rs.getString("LibrarianName");

                // Add a row to the table model
                model.addRow(new Object[]{paymentID, readerID, librarianID, description, date, amount,librarianName});
            }
            // Refresh table display
            tblData.revalidate();
            tblData.repaint();

        } catch (SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
        JTableHeader tableHeader = tblData.getTableHeader();
        Font headerFont = new Font(null, Font.PLAIN, 16); // Adjust font name, style, and size
        tableHeader.setFont(headerFont);
        TableColumnModel columnModel = tblData.getColumnModel();
        columnModel.getColumn(3).setMinWidth(250);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(0).setCellRenderer(cellRenderer);
        columnModel.getColumn(1).setCellRenderer(cellRenderer);
        columnModel.getColumn(2).setCellRenderer(cellRenderer);
        columnModel.getColumn(5).setCellRenderer(cellRenderer);
    }



}
