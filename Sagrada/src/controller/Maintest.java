package controller;

import database.AccountDBA;
import database.DataBaseConnection;

public class Maintest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataBaseConnection db = new DataBaseConnection("com.mysql.jdbc.Driver");
		AccountDBA ac = new AccountDBA(db);
		System.out.println(ac.addAccountDB("Matheus", "password"));
	
		
	}

}
