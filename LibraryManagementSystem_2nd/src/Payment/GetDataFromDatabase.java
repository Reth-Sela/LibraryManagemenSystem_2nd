package Payment;

public class GetDataFromDatabase {
   private int PaymentID,ReaderID,LibrarianID;
   private String DescriptionPM,DatePM,LibrarianName;
   private double Amount;

   public GetDataFromDatabase(int PaymentID,int ReaderID,int LibrarianID,String DescriptionPm,String DatePM,double Amount,
                              String LibrarianName){
            this.PaymentID=PaymentID;
            this.ReaderID=ReaderID;
            this.LibrarianID=LibrarianID;
            this.DescriptionPM=DescriptionPm;
            this.DatePM=DatePM;
            this.Amount=Amount;
            this.LibrarianName=LibrarianName;
   }

   public int getPaymentID() {
      return PaymentID;
   }

   public int getReaderID() {
      return ReaderID;
   }

   public int getLibrarianID() {
      return LibrarianID;
   }

   public String getDescriptionPM() {
      return DescriptionPM;
   }

   public double getAmount() {
      return Amount;
   }

   public String getDatePM() {
      return DatePM;
   }

   public String getLibrarianName() {
      return LibrarianName;
   }
}
