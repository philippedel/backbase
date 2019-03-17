
public class account {
	private int AccountID;
	private String AccountType;
	private String InitiatorType;
	private String DateTime;
	private double Transaction;

	public account (int AccountID,String AccountType,String InitiatorType,String DateTime,double Transaction) {
		this.AccountID = AccountID;
		this.AccountType = AccountType;
		this.InitiatorType = InitiatorType;
		this.DateTime = DateTime;
		this.Transaction = Transaction;
	}	
	public String getAccountType() {
		return AccountType;
	}
	public void setAccountType(String accountType) {
		this.AccountType = accountType;
	}
	public String getInitiatorType() {
		return InitiatorType;
	}
	public void setInitiatorType(String initiatorType) {
		this.InitiatorType = initiatorType;
	}
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		this.DateTime = dateTime;
	}
	public int getAccountID() {
		return AccountID;
	}
	public void setAccountID(int accountID) {
		this.AccountID = accountID;
	}
	public double getTransaction() {
		return Transaction;
	}
	public void setTransaction(double transaction) {
		this.Transaction = transaction;
	}
	public void printAccount() {
		System.out.println(this.AccountID+","+this.AccountType+","+this.InitiatorType+","+this.DateTime+","+this.Transaction);
	}


}
