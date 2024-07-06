
class Librarian {
    private int LibrarianID;
    private String FirstName, LastName, Sex, HouseNo, Street, City , BirthDate, PhoneNumber, Salary, HiredDate;
    private byte[] picture;
    
    public Librarian( int LibrarianID, String FirstName,String LastName, String Sex, String HouseNo, String Street, String City, String BirthDate, String PhoneNumber, String Salary, String HiredDate, byte[] image){
    
        this.LibrarianID = LibrarianID;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Sex = Sex;
        this.HouseNo = HouseNo;
        this.Street = Street;
        this.City = City;
        this.BirthDate = BirthDate;
        this.PhoneNumber = PhoneNumber;
        this.Salary = Salary;
        this.HiredDate = HiredDate;
        this.picture = image;
    }

    
    public int getID() {
        return LibrarianID;
    }
    public String getFName() {
        return FirstName;
    }
    public String getLName() {
        return LastName;
    }
    public String getSex() {
        return Sex;
    }
    public String getHouse() {
        return HouseNo;
    }
    public String getStreet() {
        return Street;
    }
    public String getCity() {
        return City;
    }
    public String getBirthDate() {
        return BirthDate;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public String getSalary() {
        return Salary;
    }
    public String getHIredDate(){
        return HiredDate;
    }
    public byte[] getImage() {
        return picture;
    }
    
}
