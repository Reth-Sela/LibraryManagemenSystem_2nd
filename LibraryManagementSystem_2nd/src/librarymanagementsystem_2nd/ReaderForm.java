/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package librarymanagementsystem_2nd;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.BorderFactory;
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
public class ReaderForm extends javax.swing.JFrame {
    Connection connection=null;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Statement statement;
    String url = "jdbc:sqlserver://LAPTOP-VBAMK3DF\\SQLEXPRESS;databaseName=librarySM;intergratedSecurity=true;encrypt=true;trustServerCertificate=true";
    String username = "sa";
    String password = "02062004";  
    DefaultTableModel model;
    DefaultTableModel model2;
    TableRowSorter<DefaultTableModel> sorter;
    Border redBorder = BorderFactory.createLineBorder(Color.RED);
    Border originalBorder;
    


    /**
     * Creates new form ReaderForm
     */
    public ReaderForm() {
        initComponents();
        table();
        nameHandleException();
        phoneHandleException();
        addressHandleException();
        
    }
    
    // nameText handle exception 
     private void nameHandleException(){
         originalBorder=nameTextField.getBorder();

      nameTextField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateNameInput();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                                validateNameInput();

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                                validateNameInput();

            }
            
            private void validateNameInput(){
                String nameText= nameTextField.getText();

                if (nameText.isEmpty() || nameText.matches(".*\\d.*") || nameText.matches(".*[^a-zA-Z ].*")) {
                    nameTextField.setBorder(redBorder);
                    nameInputrequire.setText("*");
                } else {
                    nameTextField.setBorder(originalBorder);
                    nameInputrequire.setText("");
                }
      
            }
        });
     }
     
     
     //phoneTextField handle exception
     
      private void phoneHandleException(){
          originalBorder=phoneTextfield.getBorder();
         
          phoneTextfield.getDocument().addDocumentListener(new DocumentListener(){
              @Override
              public void insertUpdate(DocumentEvent e) {
                  validatePhoneNumber();
              }

              @Override
              public void removeUpdate(DocumentEvent e) {
                  validatePhoneNumber();
              }

              @Override
              public void changedUpdate(DocumentEvent e) {
                  validatePhoneNumber();
              }
              private void validatePhoneNumber(){
                  String phoneNumber=phoneTextfield.getText();
                  
                  if(phoneNumber.isEmpty() || !phoneNumber.matches("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$")){
                   phoneInputRequire.setText("*");
                   phoneTextfield.setBorder(redBorder);
                   
                  
              }
                  else{
                      phoneTextfield.setBorder(originalBorder);
                      phoneInputRequire.setText("");
                  }
                      
              
              }
          
          
          });
      } 
      
      
      private void addressHandleException(){
          originalBorder= addressTextField.getBorder();
          
          addressTextField.getDocument().addDocumentListener(new DocumentListener(){
              @Override
              public void insertUpdate(DocumentEvent e) {
                  validateAddress();
              }

              @Override
              public void removeUpdate(DocumentEvent e) {
                  validateAddress();
              }

              @Override
              public void changedUpdate(DocumentEvent e) {
                  validateAddress();
              }
              
              private void validateAddress(){
                  String address= addressTextField.getText();
                  if(address.isEmpty()){
                      addressTextField.setBorder(redBorder);
                      addressInputRequire.setText("*");
                  }
                  else
                  {
                      addressTextField.setBorder(originalBorder);
                      addressInputRequire.setText("");
                  }
              }
          });
      }
    
     
     
    
    
     private static boolean isValidPhoneNumber(String phoneNumber) {
        // Regular expression for a valid phone number format (adjust as per your requirements)
        String regex = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
        return phoneNumber.matches(regex);
    }
     
  // add table listener after filtering
     private void tablelistener(){
          jTable.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = jTable.getSelectedRow();
            if (row >= 0) {
                int modelRow = jTable.convertRowIndexToModel(row);
                int selectedId = (int) model.getValueAt(modelRow, 0); // Assuming the ID is in the first column
                System.out.println("Selected ID: " + selectedId);
                
               String  query ="select * from tbReader where readerID=?";
               
                try {
                    connection =DriverManager.getConnection(url , username ,password);
                    preparedStatement=connection.prepareStatement(query);
                    preparedStatement.setInt(1,selectedId);
                    int getID=0;
                    String getName="";
                    String getSex="";
                    String getAddress="";
                    String getPhone="";
                    resultSet=preparedStatement.executeQuery();
                    while(resultSet.next()){
                        getID=resultSet.getInt(1);
                         getName=resultSet.getString(2);
                         getSex=resultSet.getString(3);
                         getAddress=resultSet.getString(4);
                         getPhone=resultSet.getString(5);
                               
                    }
                    String convertID=String.valueOf(getID);
                    idTextField.setText(convertID);
                    nameTextField.setText(getName);
                    if(getSex .equals("male")){
                        maleRadioBtn.setSelected(true);
                    }else
                        femaleRadiobtn.setSelected(true);
                    addressTextField.setText(getAddress);
                    phoneTextfield.setText(getPhone);
                           
                        
                    
                          
                } catch (SQLException ex) {
                    Logger.getLogger(ReaderForm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });
     }
     
     
     
    
       
     // add all data to put at the table 
     
     private  void table(){
        try {
            connection=DriverManager.getConnection(url,username,password);
            model=(DefaultTableModel) jTable.getModel();
            statement =connection.createStatement();
            String query="select * from tbReader order by readerID";
            resultSet=statement.executeQuery(query);
            while(resultSet.next()){
              int id=resultSet.getInt("readerID");
              String name=resultSet.getString("readerName");
              String sex=resultSet.getString("sex");
              String address=resultSet.getString("Address");
              String phone=resultSet.getString("phoneNumber");
            // System.out.println(id+name+sex+address+phone);
              
              Object[] string={id,name,sex,address,phone};
             model.addRow(string);
                      
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ReaderForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
     }
     
      
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        newBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        nameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        insertBtn = new javax.swing.JButton();
        nameInputrequire = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        deleteBtn = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        maleRadioBtn = new javax.swing.JRadioButton();
        femaleRadiobtn = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        addressTextField = new javax.swing.JTextField();
        addressInputRequire = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        searchTextfeild = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        updateBtn = new javax.swing.JButton();
        phoneTextfield = new javax.swing.JTextField();
        phoneInputRequire = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Reader's Information");
        setBackground(new java.awt.Color(102, 0, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel1.setText("ID");

        idTextField.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        idTextField.setEnabled(false);
        idTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTextFieldActionPerformed(evt);
            }
        });

        newBtn.setBackground(new java.awt.Color(204, 204, 204));
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(newBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        nameTextField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel3.setText("Name");

        insertBtn.setText("Insert");
        insertBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                insertBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                insertBtnMouseExited(evt);
            }
        });
        insertBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertBtnActionPerformed(evt);
            }
        });

        nameInputrequire.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nameInputrequire.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nameInputrequire, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(insertBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(nameInputrequire, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))))
        );

        deleteBtn.setText("Delete");
        deleteBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteBtnMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteBtnMouseExited(evt);
            }
        });
        deleteBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteBtnActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel4.setText("Sex");

        buttonGroup1.add(maleRadioBtn);
        maleRadioBtn.setText("Male");
        maleRadioBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleRadioBtnActionPerformed(evt);
            }
        });

        buttonGroup1.add(femaleRadiobtn);
        femaleRadiobtn.setText("Female");
        femaleRadiobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleRadiobtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel4)
                .addGap(57, 57, 57)
                .addComponent(maleRadioBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(femaleRadiobtn, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(129, 129, 129))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(femaleRadiobtn)
                    .addComponent(maleRadioBtn)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(deleteBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel6.setText("Address");

        addressTextField.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        addressInputRequire.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        addressInputRequire.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(addressTextField)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addressInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(addressInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19))))
        );

        jLabel9.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\User\\Downloads\\icons8-search-30.png")); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 3, 15)); // NOI18N
        jLabel10.setText("Search");

        searchTextfeild.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextfeildMouseClicked(evt);
            }
        });
        searchTextfeild.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextfeildActionPerformed(evt);
            }
        });

        jTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Sex", "Address", "Phone"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);
        if (jTable.getColumnModel().getColumnCount() > 0) {
            jTable.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(193, 193, 193)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchTextfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(229, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 808, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(searchTextfeild, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Arial", 3, 18)); // NOI18N
        jLabel5.setText("Phone");

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

        phoneTextfield.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        phoneInputRequire.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        phoneInputRequire.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(phoneTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phoneInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86)
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(130, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(phoneInputRequire, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phoneTextfield, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(15, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(updateBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("READER'S Registeration");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(252, 252, 252)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(0, 6, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void idTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTextFieldActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_idTextFieldActionPerformed

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        // TODO add your handling code here:
        String none="";
        idTextField.setText(none);
        nameTextField.setText(none);
        phoneTextfield.setText(none);
        addressTextField.setText(none);
        maleRadioBtn.setSelected(false);
        femaleRadiobtn.setSelected(false);
        nameInputrequire.setText("");

    }//GEN-LAST:event_newBtnActionPerformed

    private void deleteBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteBtnActionPerformed
        // TODO add your handling code here:
      // Ensure model is correctly set to the table's model
    model = (DefaultTableModel) jTable.getModel();

    // Check if a row is selected
    int selectedRow = jTable.getSelectedRow();
    if (selectedRow == -1) {
        // No row is selected
        if (jTable.getRowCount() == 0) {
            JOptionPane.showMessageDialog(rootPane, "Table is empty");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Select a row to delete");
        }
    } else {
        int deleteID=(int)model.getValueAt(selectedRow,0);
        String query ="Delete from tbReader where readerID=?";
        
        
        try {
            connection=DriverManager.getConnection(url,username,password);
            preparedStatement=connection.prepareStatement(query);
             preparedStatement.setInt(1,deleteID);
             int result =preparedStatement.executeUpdate();
             if(result>0){
                 model.removeRow(selectedRow);
                 JOptionPane.showMessageDialog(rootPane,"Deleted!");
             }
             else
                 JOptionPane.showMessageDialog(rootPane,"Field to delete!");
            
        } catch (SQLException ex) {
            Logger.getLogger(ReaderForm.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        
        // Row is selected, delete it
        
    }
        
        
    }//GEN-LAST:event_deleteBtnActionPerformed

    private void maleRadioBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleRadioBtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_maleRadioBtnActionPerformed

    private void femaleRadiobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleRadiobtnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_femaleRadiobtnActionPerformed

    private void updateBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateBtnActionPerformed
        // TODO add your handling code here:
        model =(DefaultTableModel) jTable.getModel();
        int select=jTable.getSelectedRow();
        if(select== -1){
            if(select ==0){
            JOptionPane.showMessageDialog(rootPane,"No table contain");}
            else
                JOptionPane.showMessageDialog(rootPane,"Select A row please!");
        }
        else{
            int updateID=(int) jTable.getValueAt(jTable.getSelectedRow(),0);
            String query ="update tbReader set readerName=?,sex=?,Address=? ,phoneNumber=? "
                    + "where readerID=?";
            String upName=nameTextField.getText();
            String upSex;

            if(maleRadioBtn.isSelected()){
                upSex="male";
            }
            else 
                upSex="female";
            
            String upAddress=addressTextField.getText(); 
            String upPhone=phoneTextfield.getText();
            try {
                connection = DriverManager.getConnection(url , username, password);
                preparedStatement=connection.prepareStatement(query);
                preparedStatement.setString(1,upName);
                preparedStatement.setString(2,upSex);
                preparedStatement.setString(3,upAddress);
                preparedStatement.setString(4,upPhone);
                preparedStatement.setInt(5,updateID);
                int updateResult=preparedStatement.executeUpdate();
                if (updateResult>0){
                    
                    model.setValueAt(upName,jTable.getSelectedRow(), 1);
                    model.setValueAt(upSex,jTable.getSelectedRow(), 2);
                    model.setValueAt(upAddress,jTable.getSelectedRow(), 3);
                    model.setValueAt(upPhone,jTable.getSelectedRow(), 4);
                    JOptionPane.showMessageDialog(rootPane,"Updated!");
                }

                
                
            } catch (SQLException ex) {
                Logger.getLogger(ReaderForm.class.getName()).log(Level.SEVERE, null, ex);
            }finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            }
                 
           
            
        
        
        }
        
            
        
        
    }//GEN-LAST:event_updateBtnActionPerformed

    private void searchTextfeildActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextfeildActionPerformed
        
    }//GEN-LAST:event_searchTextfeildActionPerformed

    private void insertBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertBtnActionPerformed
        // TODO add your handling code here:
         // Database credentials
        
       
        try {
            
            //JOptionPane.showMessageDialog(rootPane,"Load success");
             connection=DriverManager.getConnection(url,username,password);
            
             //JOptionPane.showMessageDialog(rootPane,"Successfully joy ery");
             boolean istrue=true;
             String readerName=nameTextField.getText();
             String sex="";
             if(maleRadioBtn.isSelected()){
                 sex="male";
             }
             else
             sex="female";
              String address=addressTextField.getText();
             String phoneNumber=phoneTextfield.getText();
     
            model=(DefaultTableModel) jTable.getModel();
       

 
      // Exception with nameTextField input
      

     
 
            
  
    

  
//    } else if (readerName.contains(" ")) {
//        //JOptionPane.showMessageDialog(null, "Last name required (please include a space)", "Input Error", JOptionPane.ERROR_MESSAGE);
//        nameInputrequire.setText("Last name required ");
//        nameTextField.setText("");
//        nameTextField.requestFocus();
//    } 
//     if(!readerName.isEmpty() && !readerName.matches(".*\\d.*") && !readerName.contains(" ") ){
//     break;
//     }
//   
//}


       
    
    
   
  

  

    // RadioButton Exception
//    if (maleRadioBtn.isSelected()) {
//        sex = "male";
//    } else if (femaleRadiobtn.isSelected()) {
//        sex = "female";
//    } else {
//        JOptionPane.showMessageDialog(rootPane, "None select sex!", "Oops!", JOptionPane.ERROR_MESSAGE);
//        isInputValid = false;
//    }
   

//    // Address exception
//     address = addressTextField.getText().trim();
//    if (address.isEmpty()) {
//        addressTextField.requestFocus();
//        addressInputRequire.setText("*");
//        isInputValid = false;
//    } else {
//        addressInputRequire.setText("");
//    }
    

    



    


// If we reach this point, all inputs are valid
//System.out.println(readerName);
//System.out.println(sex);
//System.out.println(phoneNumber);
//System.out.println(address);

// Proceed with further processing, such as storing data in the database
              //System.out.println(readerName + sex +address+phoneNumber);
       
             String query="INSERT INTO tbReader(readerName,sex,Address,phoneNumber)VALUES(?,?,?,?)";
             preparedStatement =connection.prepareStatement(query);
             preparedStatement.setString(1,readerName);
             preparedStatement.setString(2,sex);
             preparedStatement.setString(3,address);
             preparedStatement.setString(4,phoneNumber);
              int inserted=preparedStatement.executeUpdate();
             // if insert successfully
               if(inserted>0){
                  int lastID=(int)jTable.getValueAt(jTable.getRowCount()+1, 0);
                  System.out.print(lastID);
                   Object[] row={lastID,readerName,sex,address,phoneNumber}; 
                   JOptionPane.showMessageDialog(rootPane,"Done!");
                   model.addRow(row); 
               }
               else
                   JOptionPane.showConfirmDialog(rootPane,"Error occur!");
                
        } catch (Exception ex) {
            Logger.getLogger(ReaderForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }//GEN-LAST:event_insertBtnActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        // TODO add your handling code here:
        model= (DefaultTableModel) jTable.getModel();
        
        int id=(int) model.getValueAt(jTable.getSelectedRow(),0);
        String name= (String)model.getValueAt(jTable.getSelectedRow(),1);
        String sex= (String)model.getValueAt(jTable.getSelectedRow(),2);
        String address= (String)model.getValueAt(jTable.getSelectedRow(),3);
        String phone= (String)model.getValueAt(jTable.getSelectedRow(),4);
     
      idTextField.setText(Integer.toString(id));
      nameTextField.setText(name);
      addressTextField.setText(address);
      phoneTextfield.setText(phone);
      if (sex.equals("male")){
      maleRadioBtn.setSelected(true);
      }
      else 
          femaleRadiobtn.setSelected(true);
      
      
        
    }//GEN-LAST:event_jTableMouseClicked

    private void searchTextfeildMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextfeildMouseClicked
        // TODO add your handling code here:
        model=(DefaultTableModel) jTable.getModel();
        searchTextfeild.getDocument().addDocumentListener(new DocumentListener(){
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
            
            public void filterTable(){
                String text= searchTextfeild.getText();
                 sorter=new TableRowSorter<>(model);
                 jTable.setRowSorter(sorter);
                if(text.trim().length()==0){
                    sorter.setRowFilter(null);
                }
                else
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
            }
            
            
        
        });
        
        
//        model2= (DefaultTableModel) jTable.getModel();
//        int getId=(int) model2.getValueAt(jTable.getSelectedRow(), 0);
//        System.out.println(getId);
        
        tablelistener();  
        
             
             
    }//GEN-LAST:event_searchTextfeildMouseClicked

    private void newBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseEntered
        // TODO add your handling code here:
        newBtn.setBackground(new Color(153,255,153));
    }//GEN-LAST:event_newBtnMouseEntered

    private void newBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_newBtnMouseExited
        // TODO add your handling code here:
                newBtn.setBackground(new Color(204,204,204));

    }//GEN-LAST:event_newBtnMouseExited

    private void insertBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertBtnMouseEntered
        // TODO add your handling code here:
                insertBtn.setBackground(new Color(153,255,153));

    }//GEN-LAST:event_insertBtnMouseEntered

    private void insertBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_insertBtnMouseExited
        // TODO add your handling code here:
                insertBtn.setBackground(new Color(204,204,204));

    }//GEN-LAST:event_insertBtnMouseExited

    private void deleteBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseExited
        // TODO add your handling code here:
                deleteBtn.setBackground(new Color(204,204,204));

    }//GEN-LAST:event_deleteBtnMouseExited

    private void deleteBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteBtnMouseEntered
        // TODO add your handling code here:
                        deleteBtn.setBackground(new Color(255,51,51));

    }//GEN-LAST:event_deleteBtnMouseEntered

    private void updateBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseEntered
        // TODO add your handling code here:
                        updateBtn.setBackground(new Color(255,255,204));

    }//GEN-LAST:event_updateBtnMouseEntered

    private void updateBtnMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateBtnMouseExited
        // TODO add your handling code here:
                updateBtn.setBackground(new Color(204,204,204));

    }//GEN-LAST:event_updateBtnMouseExited

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
            java.util.logging.Logger.getLogger(ReaderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReaderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReaderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReaderForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReaderForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressInputRequire;
    private javax.swing.JTextField addressTextField;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton deleteBtn;
    private javax.swing.JRadioButton femaleRadiobtn;
    private javax.swing.JTextField idTextField;
    private javax.swing.JButton insertBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private javax.swing.JRadioButton maleRadioBtn;
    private javax.swing.JLabel nameInputrequire;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton newBtn;
    private javax.swing.JLabel phoneInputRequire;
    private javax.swing.JTextField phoneTextfield;
    private javax.swing.JTextField searchTextfeild;
    private javax.swing.JButton updateBtn;
    // End of variables declaration//GEN-END:variables
}
