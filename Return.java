
package librarymanagementsystem_2nd;


class Return {
    private int returnID, borrowID, readerID, librarianID;
    private String fineAmount, returnDate;
    
    public Return(int returnID, int borrowID, int readerID, int librarianID, String fineAmount, String returnDate) {
        this.readerID = returnID;
        this.borrowID = borrowID;
        this.readerID = readerID;
        this.librarianID = librarianID;
        this.fineAmount = fineAmount;
        this.returnDate = returnDate;
    }
    
    public int getReturnID() {
        return returnID;
    }
    
    public int getBorrowID() {
        return borrowID;
    }
    
    public int getReaderID() {
        return readerID;
    }
    public int getLibrarianID() {
        return librarianID;
    }
    public String getFineAmount() {
        return fineAmount;
    }
    public String getReturnDate() {
        return returnDate;
    }
}
