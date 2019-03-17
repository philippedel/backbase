//IMPORTANT INFORMATION the CSV file has to be in the java src folder if run from cmd
//if the project is runed from eclipse the CSV has to be in the folder before src
/*
 * We are assuming that only 1 person has account and there will not going to be more than 2 ids in the csv file
 * Considering that this are the transaction for 1 client only
 * 
 * The OUTPUT will be created in the src folder as a csv called "results"
 * 
 * The file also hadles if the savings account does not have enought balance to cover the overdraft
 * and it will add whatever it has to current account to minimise overdraft charges
 * 
 * I have used an account class to make object for the the CURRETN and SAVINGS account
 * 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import javax.swing.JOptionPane;
	
public class CSVReadWrite {
	static account CURRENT = null;
	static account SAVINGS = null;
	public static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));


	public static void main(String[] args) throws IOException {		
        System.out.println("Input csv file name located in the src folder(without '.csv' ex. 'customer-1234567-ledger' ):");
        String input = br.readLine();

		String fileName = input+".csv";			//change here the name of the file if you are running another csv
		File myfile = new File(fileName);	//read about file
		try {
			FileWriter fileWriter = new FileWriter("results.csv",false);
		}catch(Exception ex) {
			
		}
		try {
			Scanner inputStream = new Scanner(myfile);
			inputStream.next(); //ignore the first line
			double current = 0.00;
			double savings = 0.00;
			String values[] = new String[5];
			double transaction = 0.0;
			int id = 0;
			String line[] = new String[5];
			inputStream.nextLine();
			//adding the first line in output csv
			try {
		  	    FileWriter fileWriter = new FileWriter("results.csv",true);
		  	    BufferedWriter bufferWrite = new BufferedWriter(fileWriter);
		  	    PrintWriter printWriter = new PrintWriter(bufferWrite);
		  	    
		  	    printWriter.println("AcountID,AcountType,InitiatorType,DateTime,TransactionValue");
		  	    printWriter.flush();
		  	    printWriter.close();			
		  	  } catch (Exception ex) {
		  		JOptionPane.showMessageDialog(null,"Record not saved");
		  	  }
			while(inputStream.hasNext()) {
				String data = inputStream.nextLine();	//gets a whole line
				 values = data.split(",");
				 for(int j = 0;j< values.length; j++) {					 
					 line[j]=values[j];
				 }
				 try{							//converting string to integer
					   id = Integer.parseInt(line[0]);
					}catch(NumberFormatException ex){}	// handle exception					 
					 try {								//converting string to double/floating point
						 transaction = Double.parseDouble(line[4]);
					 }catch(Exception ex) {}
					if(CURRENT == null && line[1].equals("CURRENT")) {		//creating object for current account and savings account
						 CURRENT = new account(id,line[1],line[2],line[3],transaction);
//						 CURRENT.printAccount();
					}else if(line[1].equals("SAVINGS") && SAVINGS == null){
						SAVINGS = new account(id,line[1],line[2],line[3],transaction);
//						SAVINGS.printAccount();
					}

			  	  try {
				  	    FileWriter fileWriter = new FileWriter("results.csv",true);
				  	    BufferedWriter bufferWrite = new BufferedWriter(fileWriter);
				  	    PrintWriter printWriter = new PrintWriter(bufferWrite);
				  	    
				  	    printWriter.println(data);
				  	    printWriter.flush();
				  	    printWriter.close();			
				  	  } catch (Exception ex) {
				  		JOptionPane.showMessageDialog(null,"Record not saved");
				  	  }
				  for(int i = 0; i <values.length;i++) {		// or for(string i :values)
				  		if(values[i].equals("CURRENT")) {
				  			current = current + Double.parseDouble(values[4]);
				  		}else if(values[i].equals("SAVINGS")) {
				  			savings = savings + Double.parseDouble(values[4]);			  			
				  		}
				  		if(current < 0 && savings > 0) {
				  			if (savings >= Math.abs(current)) {			//checking that saving balance is bigger than overdraft
				  				savings = savings + current;			
							  	  try {
								  	    FileWriter fileWriter = new FileWriter("results.csv",true);
								  	    BufferedWriter bufferWrite = new BufferedWriter(fileWriter);
								  	    PrintWriter printWriter = new PrintWriter(bufferWrite);
								  	    
								  	    printWriter.println(SAVINGS.getAccountID() + "," + SAVINGS.getAccountType() + ",SYSTEM," + java.time.Clock.systemUTC().instant() + "," + Math.round(current * 100.0) / 100.0 );
								  	    printWriter.println(CURRENT.getAccountID() + "," + CURRENT.getAccountType() + ",SYSTEM," + java.time.Clock.systemUTC().instant() + "," + Math.round(Math.abs(current) *100.0) / 100.0);	//in java 8 it will show the time with a precision of 2
								  	    printWriter.flush();
								  	    printWriter.close();			
								  	    
								  	  } catch (Exception ex) {
								  		JOptionPane.showMessageDialog(null,"Record not saved");
								  	  }
							  	current = current + Math.abs(current);
				  			}else if(savings < Math.abs(current)) {		//if savings balance is smaller than current overdraft saving account 
				  				current = current + savings;			//will add whatever it has to current account to minimise overdraft charges
							  	  try {
								  	    FileWriter fileWriter = new FileWriter("results.csv",true);
								  	    BufferedWriter bufferWrite = new BufferedWriter(fileWriter);
								  	    PrintWriter printWriter = new PrintWriter(bufferWrite);
								  	    
								  	    printWriter.println(SAVINGS.getAccountID() + "," + SAVINGS.getAccountType() + ",SYSTEM," + java.time.Clock.systemUTC().instant() + ",-" + Math.round(savings * 100.0) / 100.0 );
								  	    printWriter.println(CURRENT.getAccountID() + "," + CURRENT.getAccountType() + ",SYSTEM," + java.time.Clock.systemUTC().instant() + "," + Math.round(Math.abs(savings) *100.0) / 100.0);	//in java 8 it will show the time with a precision of 2
								  	    printWriter.flush();
								  	    printWriter.close();			
								  	    
								  	  } catch (Exception ex) {
								  		JOptionPane.showMessageDialog(null,"Record not saved");
								  	  }
					  				savings = savings - savings;
				  			} 
				  		}
				  	}
			}
//			System.out.println(current + " " + savings);
			inputStream.close();
	  	    JOptionPane.showMessageDialog(null,"Record saved");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	System.out.println("Check src folder for results.csv file");
	}
}
