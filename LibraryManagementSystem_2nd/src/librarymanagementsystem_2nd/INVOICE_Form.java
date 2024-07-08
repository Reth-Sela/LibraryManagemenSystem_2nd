package librarymanagementsystem_2nd;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

import Payment.FrmPayment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.math.BigDecimal;


public class INVOICE_Form extends JFrame {



    /**
     * @param args the command line arguments
     */




    private JPanel mainPanel;
    private JTextField Invoicetf;
    private JTextField Readertf;
    private JTextField Datetf;
    private JTextField Totaltf;
    private JTable Datatb;

    // Database connection variables
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public INVOICE_Form() {
        initComponents();
        connectToDatabase(); // Connect to the database when the form is initialized
        setContentPane(mainPanel);
        setTitle("INVOICE Form");
        setSize(800, 700);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("INVOICE", JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 48));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel invoiceLabel = new JLabel("Invoice No:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(invoiceLabel, gbc);

        Invoicetf = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(Invoicetf, gbc);

        JLabel dateLabel = new JLabel("Date:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(dateLabel, gbc);

        Datetf = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(Datetf, gbc);

        JLabel readerLabel = new JLabel("Reader ID:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(readerLabel, gbc);

        Readertf = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(Readertf, gbc);

        JLabel totalLabel = new JLabel("Total:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(totalLabel, gbc);

        Totaltf = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(Totaltf, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        Datatb = new JTable(new DefaultTableModel(
                new Object[][]{},
                new String[]{"Book ID", "Book Title", "Book Author", "Quantity", "Return Date"}
        ));
        JScrollPane tableScrollPane = new JScrollPane(Datatb);
        mainPanel.add(tableScrollPane, BorderLayout.SOUTH);

        // Adding action listeners to text fields
        Invoicetf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                InvoicetfActionPerformed(evt);
            }
        });

        Readertf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ReadertfActionPerformed(evt);
            }
        });

        Datetf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                DatetfActionPerformed(evt);
            }
        });

        Totaltf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                TotaltfActionPerformed(evt);
            }
        });
    }

        public void connectToDatabase() {
            try {
                // Change these variables according to your database setup
                String url = "jdbc:sqlserver://LAPTOP-VBAMK3DF\\SQLEXPRESS;databaseName=librarySM;intergratedSecurity=true;encrypt=true;trustServerCertificate=true";
                String username = "sa";
                String password = "02062004";

                connection = DriverManager.getConnection(url, username, password);
                //JOptionPane.showMessageDialog(this, "Connected to database successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Failed to connect to database");
            }
        }

    private void fetchInvoiceData(String invoiceNo) {
        try {
            String query = "SELECT * FROM tbInvoice WHERE InvoiceID = ?";

            statement = connection.prepareStatement(query);
            statement.setString(1, invoiceNo);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Retrieve data using column names
                Invoicetf.setText(resultSet.getString("InvoiceID"));
                Readertf.setText(resultSet.getString("readerID"));
                // Fetch additional columns
                try {
                    Date invoiceDate = resultSet.getDate("InvoiceDate");
                    if (invoiceDate != null) {
                        Datetf.setText(invoiceDate.toString());
                    } else {
                        Datetf.setText("");
                        System.out.println("InvoiceDate is null");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error accessing InvoiceDate column: " + e.getMessage());
                }

                try {
                    BigDecimal amount = resultSet.getBigDecimal("Amount");
                    if (amount != null) {
                        Totaltf.setText(amount.toString());
                    } else {
                        Totaltf.setText("");
                        System.out.println("Amount is null");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("Error accessing Amount column: " + e.getMessage());
                }

                // Fetch and display table data
                fetchTableData(invoiceNo);
            } else {
                System.out.println("No data found for InvoiceID: " + invoiceNo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching invoice data for InvoiceID: " + invoiceNo);
        }
    }

    private void fetchReaderName(int readerID) {
        try {
            String query = "SELECT readerName FROM tbReader WHERE ReaderID = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, readerID);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Readertf.setText(resultSet.getString("readerName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fetchTableData(String invoiceNo) {
        try {
            String query = "SELECT b.BookID, b.BookTitle, b.Author, bb.BookAmount, bb.DueDate "
                    + "FROM tblBook b "
                    + "INNER JOIN tblBorrow bb ON b.BookID = bb.BookID "
                    + "WHERE bb.BorrowID = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, invoiceNo);
            resultSet = statement.executeQuery();

            DefaultTableModel model = (DefaultTableModel) Datatb.getModel();
            model.setRowCount(0); // Clear existing rows

            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("BookID"),
                        resultSet.getString("BookTitle"),
                        resultSet.getString("Author"),
                        resultSet.getInt("BookAmount"),
                        resultSet.getString("DueDate")
                });
            }

            // Refresh table display
            Datatb.revalidate();
            Datatb.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching table data: " + e.getMessage());
        }
    }

    private void InvoicetfActionPerformed(ActionEvent evt) {
        // Fetch and display data based on the entered invoice number
        String invoiceNo = Invoicetf.getText();
        fetchInvoiceData(invoiceNo);
    }

    private void ReadertfActionPerformed(ActionEvent evt) {
        // Placeholder for any specific actions related to the Reader ID field
    }

    private void DatetfActionPerformed(ActionEvent evt) {
        // Placeholder for any specific actions related to the Date field
    }

    private void TotaltfActionPerformed(ActionEvent evt) {
        // Placeholder for any specific actions related to the Total field
    }

    public static void main(String[] args) {
        new INVOICE_Form();
    }

}
