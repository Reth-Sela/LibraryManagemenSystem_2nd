/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package librarymanagementsystem_2nd;

import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author User
 */
public class Book extends javax.swing.JFrame {
    Connection connection=null;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Statement statement;
    String url = "jdbc:sqlserver://LAPTOP-VBAMK3DF\\SQLEXPRESS;databaseName=librarySM;intergratedSecurity=true;encrypt=true;trustServerCertificate=true";
    String username = "sa";
    String password = "02062004"; 
    DefaultTableModel model;
    TableRowSorter<DefaultTableModel> sorter;
    Border originalBorder;
    Border redBorder = BorderFactory.createLineBorder(Color.RED);
    CallableStatement collap;


    //add table listener
    private void tablelistener(){
          bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = bookTable.getSelectedRow();
            if (row >= 0) {
                int modelRow = bookTable.convertRowIndexToModel(row);
                int selectedId = (int) model.getValueAt(modelRow, 0); // Assuming the ID is in the first column
                System.out.println("Selected ID: " + selectedId);
                
               String  query ="{call spGetBookByID(?)}";
               
                try {
                    connection =DriverManager.getConnection(url , username ,password);
                   collap =connection.prepareCall(query);
                    collap.setInt(1,selectedId);
                    int getID=0;
                    String getTitle="";
                    String getAutor="";
                    String getCategory="";
                    int qty=0;
                    resultSet=preparedStatement.executeQuery();
                    while(resultSet.next()){
                        getID=resultSet.getInt(1);
                         getTitle=resultSet.getString(2);
                         getAutor=resultSet.getString(3);
                         getCategory=resultSet.getString(4);
                         qty=resultSet.getInt(5);
                               
                    }
                    String convertID=String.valueOf(getID);
                    idTextField.setText(convertID);
                    titleTextField.setText(getTitle);
                    autorTextField.setText(getAutor);
                    categoryTextField.setText(getCategory);
                    String convertQty=String.valueOf(qty);
                    qtyTextField.setText(convertQty);
                           
                        
                    
                          
                } catch (SQLException ex) {
                    Logger.getLogger(ReaderForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });
     }
    
    //when the form start want it to focus title text field 
    private void titleForcus(){
        titleTextField.requestFocus();
    }
    
    
    //show all row in the table 
    private  void table(){
        try {
            
            connection=DriverManager.getConnection(url,username,password);
            model=(DefaultTableModel) bookTable.getModel();
            statement =connection.createStatement();
            String query="select * from vwGetAllBook order by bookID";
            resultSet=statement.executeQuery(query);
            while(resultSet.next()){
              int id=resultSet.getInt("bookID");
              String title=resultSet.getString("bookTitle");
              String autor=resultSet.getString("autor");
              String category=resultSet.getString("category");
              int qty=resultSet.getInt("bookQty");
            // System.out.println(id+name+sex+address+phone);
              
              Object[] string={id,title,autor,category,qty};
             model.addRow(string);
                      
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ReaderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
     }
    
    //add the exception to the title text field 
    
    private void titleException(){
        originalBorder=titleTextField.getBorder();
        titleTextField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
            titleCatchException();            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            titleCatchException();            }

            @Override
            public void changedUpdate(DocumentEvent e) {
             titleCatchException();
            }
            
            private void titleCatchException(){
                String getTitle=titleTextField.getText();
                if(getTitle.isEmpty() || getTitle.matches(".*\\d.*") || getTitle.matches(".*[^a-zA-Z ].*")){
                    titleInputRequire.setText("*");
                    titleTextField.setBorder(redBorder);
                }
                else{
                    titleInputRequire.setText("");
                    titleTextField.setBorder(originalBorder);
                }
               
            }
        });
    }
    
    //add the exception to the autor text field 
    
    private void autorException(){
        originalBorder=autorTextField.getBorder();
        autorTextField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
            autorCatchException();            
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            autorCatchException();            
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            autorCatchException();            }
            private void autorCatchException(){
                String getAutor=autorTextField.getText();
                if(getAutor.isEmpty() || getAutor.matches(".*\\d.*") || getAutor.matches(".*[^a-zA-Z ].*")){
                    autorInputRequire.setText("*");
                    autorTextField.setBorder(redBorder);
                }
                else{
                    autorInputRequire.setText("");
                    autorTextField.setBorder(originalBorder);
                }
                
            }
        });
    }
    
    // add the exception in to the category textfield
    
    private void categoryException(){
        categoryTextField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
            categoryCatchException();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            categoryCatchException();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            categoryCatchException();
            }
            private void categoryCatchException(){
                    String getCategory = categoryTextField.getText();

        
            
            if(getCategory.isEmpty() || getCategory.matches(".*\\d.*") || getCategory.matches(".*[^a-zA-Z ].*")){
                    categoryInputRequire.setText("*");
                    categoryTextField.setBorder(redBorder);
                }
                else{
                    categoryInputRequire.setText("");
                    categoryTextField.setBorder(originalBorder);
                }
        }
        });
    }
    
    //add exception to the qtyTextfield
    private void qtyException(){
        qtyTextField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
            qtyCatchException();            
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            qtyCatchException();            
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            qtyCatchException();            }
            
            private void qtyCatchException(){
            
            String getQty=qtyTextField.getText();
            if(getQty.isEmpty() || !getQty.matches(".*[^a-zA-Z ].*")){
                    qtyInputRequire.setText("*");
                    qtyTextField.setBorder(redBorder);
                }
                else{
                    qtyInputRequire.setText("");
                    qtyTextField.setBorder(originalBorder);
                }
            }
            
        
        });
    }
        
        
            
        
    

         
    
    

    /**
     * Creates new form Book
     */
    public Book() {
        initComponents();
        table();
        titleForcus();
        titleException();
        autorException();
        categoryException();
        qtyException();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        qtyTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        autorTextField = new javax.swing.JTextField();
        titleTextField = new javax.swing.JTextField();
        categoryTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        searchTextField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        bookTable = new javax.swing.JTable();
        newBtn = new javax.swing.JButton();
        addBtn = new javax.swing.JButton();
        removeBtn = new javax.swing.JButton();
        updateBtn = new javax.swing.JButton();
        titleInputRequire = new javax.swing.JLabel();
        autorInputRequire = new javax.swing.JLabel();
        categoryInputRequire = new javax.swing.JLabel();
        qtyInputRequire = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Book's Information");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Book's Information");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ID");

        idTextField.setEnabled(false);
        idTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTextFieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("TITLE");

        qtyTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qtyTextFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("AUTOR");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("CATEGORY");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("QTY");

        autorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autorTextFieldActionPerformed(evt);
            }
        });

        titleTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleTextFieldActionPerformed(evt);
            }
        });

        categoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryTextFieldActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("SEARCH");

        searchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseClicked(evt);
            }
        });
        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });

        bookTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BookID", "Title", "Autor", "Category", "Qty"
            }
        ));
        bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bookTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(bookTable);

        newBtn.setBackground(new java.awt.Color(204, 255, 204));
        newBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        newBtn.setText("New");
        newBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                newBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                newBtnMouseExited(evt);
            }
        });
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });

        addBtn.setBackground(new java.awt.Color(204, 255, 204));
        addBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addBtn.setText("ADD");
        addBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addBtnMouseExited(evt);
            }
        });
        addBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBtnActionPerformed(evt);
            }
        });

        removeBtn.setBackground(new java.awt.Color(204, 255, 204));
        removeBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        removeBtn.setText("Remove");
        removeBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                removeBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                removeBtnMouseExited(evt);
            }
        });
        removeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBtnActionPerformed(evt);
            }
        });

        updateBtn.setBackground(new java.awt.Color(204, 255, 204));
        updateBtn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        updateBtn.setText("Update");
        updateBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                updateBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                updateBtnMouseExited(evt);
            }
        });
        updateBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateBtnActionPerformed(evt);
            }
        });

        titleInputRequire.setForeground(new java.awt.Color(255, 0, 0));

        autorInputRequire.setForeground(new java.awt.Color(255, 0, 0));

        categoryInputRequire.setForeground(new java.awt.Color(255, 0, 0));

        qtyInputRequire.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(123, 123, 123)
                                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(categoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(autorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(qtyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 6, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(103, 103, 103)
                                .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(104, 104, 104)
                                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(titleInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(autorInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(categoryInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(qtyInputRequire, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(48, 48, 48))))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addComponent(jLabel7))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(titleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(titleInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(autorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(autorInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(categoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5))
                            .addComponent(categoryInputRequire))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qtyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(qtyInputRequire)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(removeBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void idTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_idTextFieldActionPerformed

    private void qtyTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qtyTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qtyTextFieldActionPerformed

    private void autorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autorTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_autorTextFieldActionPerformed

    private void titleTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleTextFieldActionPerformed

    private void categoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryTextFieldActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
        model = (DefaultTableModel) bookTable.getModel();
      //  int
        String updateIDstring=idTextField.getText();
        //int updateID=Integer.parseInt(updateIDstring);
        //System.out.println(updateID);
        int select= bookTable.getSelectedRow();
        if(select== -1){
            if(select ==0){
            JOptionPane.showMessageDialog(rootPane,"No table contain");}
            else
                JOptionPane.showMessageDialog(rootPane,"Select A row please!");
        }
        else{
            int updateID=(int)bookTable.getValueAt(bookTable.getSelectedRow(), 0);
            System.out.println(updateID);
            //get the text from textfield
            String title=titleTextField.getText();
            String category=categoryTextField.getText();
            String autor=autorTextField.getText();
            String stringQty=qtyTextField.getText();
            
            int qty=Integer.parseInt(stringQty);
            
            
            
            String query ="{call spUpdateBookByID(?,?,?,?,?)}";
            try {
                connection=DriverManager.getConnection(url,username, password);
                collap=connection.prepareCall(query);
                 collap.setInt(1,updateID);
                collap.setString(2, title);
                collap.setString(3,category);
                collap.setString(4,autor);
                collap.setInt(5,qty); 
               
               
                int updateResult=collap.executeUpdate();
                System.out.print(updateResult);
                if (updateResult==-1){
                    
                    model.setValueAt(title,bookTable.getSelectedRow(), 1);
                    model.setValueAt(autor,bookTable.getSelectedRow(), 2);
                    model.setValueAt(category,bookTable.getSelectedRow(), 3);
                    model.setValueAt(qty,bookTable.getSelectedRow(), 4);
                    JOptionPane.showMessageDialog(rootPane,"Updated!");
                }
                
            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }//GEN-LAST:event_updateBtnActionPerformed

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
       idTextField.setText("");
       titleTextField.setText("");
       autorTextField.setText("");
       categoryTextField.setText("");
       qtyTextField.setText("");
    }//GEN-LAST:event_newBtnActionPerformed

    private void newBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseEntered
        newBtn.setBackground(new Color(102,255,102));
    }//GEN-LAST:event_newBtnMouseEntered

    private void newBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseExited
       newBtn.setBackground(new Color(204,255,204));
    }//GEN-LAST:event_newBtnMouseExited

    private void addBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseEntered
               addBtn.setBackground(new Color(102,255,102));

    }//GEN-LAST:event_addBtnMouseEntered

    private void addBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addBtnMouseExited
       addBtn.setBackground(new Color(204,255,204));
    }//GEN-LAST:event_addBtnMouseExited

    private void removeBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeBtnMouseEntered
       removeBtn.setBackground(new Color(205,0, 51));
    }//GEN-LAST:event_removeBtnMouseEntered

    private void removeBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_removeBtnMouseExited
             removeBtn.setBackground(new Color(255,213, 213));

    }//GEN-LAST:event_removeBtnMouseExited

    private void updateBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseEntered
        updateBtn.setBackground(new Color(255,255,102));
    }//GEN-LAST:event_updateBtnMouseEntered

    private void updateBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseExited
        updateBtn.setBackground(new Color(255,255,204));
    }//GEN-LAST:event_updateBtnMouseExited

    private void addBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBtnActionPerformed
     // Add Button 
     
     String title = titleTextField.getText();
     String autor =autorTextField.getText();
     String category= categoryTextField.getText();
     int  qty = Integer.parseInt(qtyTextField.getText());
    
     System.out.println(title+autor+category+qty);
     
     String query ="{call spInsertBook(?,?,?,?)}";
    
        try {
            connection= DriverManager.getConnection(url,username,password);
            collap=connection.prepareCall(query);
            
            collap.setString(1, title);
            collap.setString(2, autor);
            collap.setString(3, category);
            collap.setInt(4, qty);
            
             int inserted=collap.executeUpdate();
             // if insert successfully
               if(inserted==-1){
                  int lastID=(int)bookTable.getValueAt(bookTable.getRowCount()-1, 0);
                  lastID+=1;
                  System.out.print(lastID);
                   Object[] row={lastID,title,autor,category,qty}; 
                   JOptionPane.showMessageDialog(rootPane,"Done!");
                   model.addRow(row); 
               }
               else
                   JOptionPane.showConfirmDialog(rootPane,"Error occur!");
                


            
        } catch (SQLException ex) {
            Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
        
    }//GEN-LAST:event_addBtnActionPerformed

    private void bookTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bookTableMouseClicked
        model=(DefaultTableModel) bookTable.getModel();
        
        int id=(int)model.getValueAt(bookTable.getSelectedRow(), 0);
        String idString= String.valueOf(id);
        String title= model.getValueAt(bookTable.getSelectedRow(),1).toString();
        String autor= model.getValueAt(bookTable.getSelectedRow(),2).toString();
        String category= model.getValueAt(bookTable.getSelectedRow(),3).toString();
        int qty=(int) model.getValueAt(bookTable.getSelectedRow(),4);
        String qtyString=String.valueOf(qty);
        idTextField.setText(idString);
        titleTextField.setText(title);
        autorTextField.setText(autor);
        categoryTextField.setText(category);
        qtyTextField.setText(qtyString);
        
        
      
        
        
        
    }//GEN-LAST:event_bookTableMouseClicked

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
         
             model=(DefaultTableModel) bookTable.getModel();
             searchTextField.getDocument().addDocumentListener(new DocumentListener(){
             @Override
             public void insertUpdate(DocumentEvent e) {
                  filterTable();            
             }

             @Override
             public void removeUpdate(DocumentEvent e) {
                  filterTable();            
             }

             @Override
             public void changedUpdate(DocumentEvent e) {
                  filterTable();            
             }
             
             private void filterTable(){
                 String text= searchTextField.getText();
                 sorter=new TableRowSorter<>(model);
                 bookTable.setRowSorter(sorter);
                if(text.trim().length()==0){
                    sorter.setRowFilter(null);
                }
                else
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
          
             
         });
         tablelistener();
    }//GEN-LAST:event_searchTextFieldMouseClicked

    private void removeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBtnActionPerformed
       // TODO add your handling code here:
        model= (DefaultTableModel) bookTable.getModel();
        int selectRow= bookTable.getSelectedRow();
        System.out.println(selectRow);
        
        if (selectRow == -1) {
        // No row is selected
        if (bookTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Table is empty");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Select a row to delete");
        }
    } else {
            int removeID = (int) bookTable.getValueAt(selectRow, 0);
            String query= "{call spDeleteBookByID(?)}";
            
            try {
                connection =DriverManager.getConnection(url,username, password);
                collap=connection.prepareCall(query);
                collap.setInt(1, removeID);
                int resultRemove= collap.executeUpdate();
                if (resultRemove ==-1){
                    model.removeRow(selectRow);
                    JOptionPane.showMessageDialog(rootPane, "Deleted");
                }
                else 
                 JOptionPane.showMessageDialog(rootPane, "feild to delete ");

            } catch (SQLException ex) {
                Logger.getLogger(Book.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
            
        
        
    }//GEN-LAST:event_removeBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Book.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Book().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBtn;
    private javax.swing.JLabel autorInputRequire;
    private javax.swing.JTextField autorTextField;
    private javax.swing.JTable bookTable;
    private javax.swing.JLabel categoryInputRequire;
    private javax.swing.JTextField categoryTextField;
    private javax.swing.JTextField idTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newBtn;
    private javax.swing.JLabel qtyInputRequire;
    private javax.swing.JTextField qtyTextField;
    private javax.swing.JButton removeBtn;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel titleInputRequire;
    private javax.swing.JTextField titleTextField;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
