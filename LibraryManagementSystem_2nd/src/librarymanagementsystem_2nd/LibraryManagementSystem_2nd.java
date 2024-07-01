/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package librarymanagementsystem_2nd;

import Payment.FrmPayment;

/**
 *
 * @author User
 */
public class LibraryManagementSystem_2nd {


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        FrmPayment myForm = new FrmPayment();
        myForm.connectToDatabase();
        myForm.fetchData();


        // TODO code application logic here

    }


}
