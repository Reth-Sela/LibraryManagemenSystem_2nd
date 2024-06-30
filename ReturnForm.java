
package librarymanagementsystem_2nd;


import javax.swing.JOptionPane;
import java.sql.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
/**
 *
 * @author Phear
 */
public final class ReturnForm extends javax.swing.JFrame {

    /**
     * Creates new form ReturnForm
     */
    public ReturnForm() {
        initComponents();
        show_return();
    }
    public ArrayList<Return> returnList() {
        ArrayList<Return> returnList = new ArrayList <> ();
        String connectionString = "jdbc:sqlserver://SOTHEARITH;user=nani;password=50th34rith;Database=LibDB;encrypt=true;trustServerCertificate=true";

        try (Connection connection = DriverManager.getConnection(connectionString)) {
            String query1 = "SELECT * FROM ReturnTb";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Return nReturn;
            while (rs.next()) {
                nReturn = new Return(rs.getInt("ReturnID"), rs.getInt("BorrowID"), rs.getInt("ReaderID"), rs.getInt("LibrarianID"), rs.getString("FineAmount"), rs.getString("ReturnDate"));
                returnList.add(nReturn);
            }
            
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error.");
            e.printStackTrace();
        }
        
        return returnList;
    }
    
    public void show_return() {
        ArrayList<Return> list = returnList();
        DefaultTableModel model = (DefaultTableModel)displayR.getModel();
        Object[] row = new Object[6];
        for (int i=0; i<list.size(); i++) {
            row[0] = list.get(i).getReturnID();
            row[1] = list.get(i).getBorrowID();
            row[2] = list.get(i).getReaderID();
            row[3] = list.get(i).getLibrarianID();
            row[4] = list.get(i).getFineAmount();
            row[5] = list.get(i).getReturnDate();
            
            model.addRow(row);
        } 
        
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        displayR = new javax.swing.JTable();
        fineAmount = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        librarianID = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnDelete = new javax.swing.JButton();
        returnDate = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        returnID = new javax.swing.JTextField();
        readerID = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        searchdata = new javax.swing.JTextField();
        borrowID = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ReturnID = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel2.setText("BorrowID");

        displayR.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ReturnID", "BorrowID", "ReaderID", "LibrarianID", "FineAmount", "ReturnDate"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        displayR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                displayRMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(displayR);

        jLabel3.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel3.setText("FineAmount");

        btnUpdate.setFont(new java.awt.Font("Poppins ExtraBold", 0, 14)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel4.setText("LibrarianID");

        btnDelete.setFont(new java.awt.Font("Poppins ExtraBold", 0, 14)); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnInsert.setFont(new java.awt.Font("Poppins ExtraBold", 0, 14)); // NOI18N
        btnInsert.setText("Insert");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel5.setText("ReturnDate");

        returnID.setEditable(false);

        jLabel6.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel6.setText("ReturnID");

        jLabel1.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel1.setText("ReaderID");

        searchdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchdataActionPerformed(evt);
            }
        });
        searchdata.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchdataKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Poppins SemiBold", 0, 36)); // NOI18N
        jLabel7.setText("Return Book");

        ReturnID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReturnIDActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel8.setText("Search");

        jLabel9.setFont(new java.awt.Font("Poppins SemiBold", 0, 18)); // NOI18N
        jLabel9.setText("ReturnID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(fineAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addGap(18, 18, 18)
                                            .addComponent(returnID, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addGap(18, 18, 18)
                                            .addComponent(borrowID, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(returnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(librarianID, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)
                                        .addComponent(readerID, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchdata, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addGap(59, 59, 59)
                                .addComponent(btnUpdate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete)
                                .addGap(90, 90, 90)
                                .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 754, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ReturnID, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(299, 299, 299)
                        .addComponent(jLabel7)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(readerID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(returnID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(borrowID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fineAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(librarianID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(returnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchdata, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnInsert, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReturnID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(213, 213, 213))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        String connectionString = "jdbc:sqlserver://SOTHEARITH;user=nani;password=50th34rith;Database=LibDB;encrypt=true;trustServerCertificate=true";
        
        try (Connection connection = DriverManager.getConnection(connectionString)) {
           //  int row = displayR.getSelectedRow();
           //  String value = (displayR.getModel().getValueAt(row, 0).toString());
             String value = returnID.getText();
             String query ="UPDATE ReturnTb SET BorrowID=?, ReaderID=?, LibrarianID=?, ReturnDate=?, FineAmount=? where returnID="+value;
              PreparedStatement pst = connection.prepareStatement(query);

            pst.setString(1, borrowID.getText());
            pst.setString(2, readerID.getText());
            pst.setString(3, librarianID.getText());

            pst.setString(4, returnDate.getText());

            pst.setString(5, fineAmount.getText());

            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel)displayR.getModel();
            model.setRowCount(0);
            show_return();
            JOptionPane.showMessageDialog(null, "Updated successfully.");
       }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String connectionString = "jdbc:sqlserver://SOTHEARITH;user=nani;password=50th34rith;Database=LibDB;encrypt=true;trustServerCertificate=true";

        
        
        try (Connection connection = DriverManager.getConnection(connectionString)) {
            int row = displayR.getSelectedRow();
            String value = (displayR.getModel().getValueAt(row, 0).toString());
            String query = "DELETE FROM ReturnTb where returnID="+value;
            PreparedStatement pst = connection.prepareStatement(query);
            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel)displayR.getModel();
            model.setRowCount(0);
            show_return();
            JOptionPane.showMessageDialog(null, "Deleted successfully.");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
        
        String connectionString = "jdbc:sqlserver://SOTHEARITH;user=nani;password=50th34rith;Database=LibDB;encrypt=true;trustServerCertificate=true";

        
        
        try (Connection connection = DriverManager.getConnection(connectionString)) {

            String query = "INSERT INTO ReturnTb ( BorrowID, ReaderID, LibrarianID, ReturnDate, FineAmount) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);

            pst.setString(1, borrowID.getText());
            pst.setString(2, readerID.getText());
            pst.setString(3, librarianID.getText());

            pst.setString(4, returnDate.getText());

            pst.setString(5, fineAmount.getText());

            pst.executeUpdate();
            DefaultTableModel model = (DefaultTableModel)displayR.getModel();
            model.setRowCount(0);
            show_return();
            JOptionPane.showMessageDialog(null, "Inserted successfully.");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error.");
            e.printStackTrace();
        }
        
        
    }//GEN-LAST:event_btnInsertActionPerformed

    private void searchdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchdataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchdataActionPerformed

    private void displayRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_displayRMouseClicked
        int i = displayR.getSelectedRow();
        TableModel model = displayR.getModel();
        returnID.setText(model.getValueAt(i, 0).toString());
        borrowID.setText(model.getValueAt(i, 1).toString());
        readerID.setText(model.getValueAt(i, 2).toString());
        librarianID.setText(model.getValueAt(i, 3).toString());
        fineAmount.setText(model.getValueAt(i, 4).toString());
        returnDate.setText(model.getValueAt(i, 5).toString());
        
    }//GEN-LAST:event_displayRMouseClicked

    private void ReturnIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReturnIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReturnIDActionPerformed

    private void searchdataKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchdataKeyReleased
        // TODO add your handling code here:
        String connectionString = "jdbc:sqlserver://SOTHEARITH;user=nani;password=50th34rith;Database=LibDB;encrypt=true;trustServerCertificate=true";

        
        
        try (Connection connection = DriverManager.getConnection(connectionString)) {
             String sql = "Select * from ReturnTb where ReturnID =?";
             PreparedStatement pst = connection.prepareStatement(sql);
             pst.setString(1, searchdata.getText());
             ResultSet rs = pst.executeQuery();
             if(rs.next()){
                 String setid = rs.getString("ReturnID");
                 ReturnID.setText(setid);
                 
                 String setrd = rs.getString("ReaderID");
                 readerID.setText(setrd);
                 String setbr = rs.getString("BorrowID");
                 borrowID.setText(setbr);
                 String setLr = rs.getString("LibrarianID");
                 librarianID.setText(setLr);
                  String setFm = rs.getString("FineAmount");
                 fineAmount.setText(setFm);
                  String setRd = rs.getString("ReturnDate");
                 returnDate.setText(setRd);
             }
        }    
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error.");
            e.printStackTrace();
        }
    }//GEN-LAST:event_searchdataKeyReleased

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
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReturnForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReturnForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ReturnID;
    private javax.swing.JTextField borrowID;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTable displayR;
    private javax.swing.JTextField fineAmount;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField librarianID;
    private javax.swing.JTextField readerID;
    private javax.swing.JTextField returnDate;
    private javax.swing.JTextField returnID;
    private javax.swing.JTextField searchdata;
    // End of variables declaration//GEN-END:variables
}
