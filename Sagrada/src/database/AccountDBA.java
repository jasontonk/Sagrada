package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Account;

public class AccountDBA {

	private DataBaseConnection conn;
	
	public AccountDBA(DataBaseConnection c) {
		this.conn = c;
	}
	
	public boolean addAccountDB(String username, String password) {
		String query = "INSERT INTO account VALUES('"+username+"','"+password+"');";
		
		if(!accountExists(username)) {
			try {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
				return true;
			}catch(SQLException e) {
				e.printStackTrace();
				return false;
			}	
		}
		return false;
	}
	
	public Account GetAccountDB(String username) {
		Account account = null;
		String query = "SELECT * FROM account WHERE username = '"+username+"';";
		
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				account = new Account(rs.getString("username"),rs.getString("password"), conn);
			}
			stmt.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return account;
	}
	
	public String updateUsernameDB(String oldaccount,String newaccount) {
		
		String query = "UPDATE account SET username = '"+newaccount+"' WHERE username = '"+oldaccount+"';";
		try {
			if(accountExists(oldaccount)) {
				Statement stmt = conn.createStatemant();
				stmt.executeUpdate(query);
				stmt.close();
				return "Account is succesvol geupdated";
			}else {
				return "Account bestaat niet";
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ging wat fout";
		}
	}
	
	public String updatePasswordDB(String username, String passwoord) {
		String query = "UPDATE account SET password = '"+passwoord+"' WHERE username = '"+username+"';";

			try {
				if(accountExists(username)) {
					Statement stmt = conn.createStatemant();
					stmt.executeUpdate(querzy);
					stmt.close();
					return "Wachtwoord is succesvol geupdated";
				}else {
					return "Account bestaat niet";
				}
				
			}catch (SQLException e) {
				e.printStackTrace();
				return "ging wat fout";
			}
	}
	
	public boolean accountExists(String username) {
		String query = "SELECT count(*) as count FROM account WHERE username = '"+username+"'; ";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				int count = rs.getInt("count");
				if (count >0) {
					return true;
				}else {
					return false;
				}
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Account> getAllAccounts(){
		ArrayList<Account> list = new ArrayList<>();
		String query = "SELECT * FROM account;";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				String username = rs.getString("username");
				String password = rs.getString("password");
				Account account = new Account(username,password, conn);
				list.add(account);
			}
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getHighestScore(Account account) { 
		int score = 0;
		String query = "SELECT MAX(score) AS hoogste_score FROM player WHERE username = '"+account.getUsername()+"';";
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			 if (rs.next()) {
	                score = rs.getInt("hoogste_score");
	            }
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return score;
	}
	
	public String getMostUsedColor(Account account) { 
		String color= "";
		String query = "SELECT diecolor AS color, COUNT(diecolor) AS meest_gebruikte_kleur FROM playerframefield JOIN player ON player.idplayer = playerframefield.player_idplayer WHERE player.username= '"+account.getUsername()+"' GROUP BY diecolor ORDER BY meest_gebruikte_kleur DESC LIMIT 1;";
		
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
                color = rs.getString("color");
            }
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return color;
	}
	
	public int getMostUsedValue(Account account) {
		int value = 0;
		String query = "SELECT eyes, COUNT(eyes) AS aantal_keer_gebruikt FROM playerframefield JOIN player ON player.idplayer = playerframefield.player_idplayer JOIN gamedie ON gamedie.dienumber = playerframefield.dienumber WHERE player.username = '"+account.getUsername()+"' GROUP BY eyes ORDER BY aantal_keer_gebruikt DESC LIMIT 1";
		
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
                value = rs.getInt("eyes");
            }
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public int getValueOfDifferentPlayedAccounts(Account account){
		int value = 0;
		String query = "SELECT COUNT(DISTINCT username) AS count FROM player WHERE username != '"+account.getUsername()+"' AND game_idgame IN(SELECT game_idgame FROM player WHERE username = '"+account+"')";
		
		try {
			Statement stmt = conn.createStatemant();
			ResultSet rs = stmt.executeQuery(query);
			if (rs.next()) {
                value = rs.getInt("count");
            }
			stmt.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public int getWins(Account account) {
		ArrayList<Integer> games = new GameDBA(conn).getAllGamesId();
		int wins = 0;
		for(int i = 0; i <games.size();i++){
			String query = "SELECT username, MAX(score) AS winscore FROM player WHERE game_idgame = "+games.get(i)+" AND playstatus_playstatus = 'uitgespeeld' GROUP BY username, game_idgame ORDER BY winscore DESC LIMIT 1";
			try {
				Statement stmt = conn.createStatemant();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
	                if(rs.getString("username").equals(account.getUsername())) {
	                	wins++;
	                }
	            }
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wins;
	}
	
	public int getLoses(Account account){
		ArrayList<Integer> games = new GameDBA(conn).getAllGamesId();
		int loses = 0;
		for(int i = 0; i <games.size();i++){
			String query = "SELECT username FROM player WHERE game_idgame = "+games.get(i)+" AND playstatus_playstatus = 'uitgespeeld' AND score < (SELECT MAX(score) FROM player WHERE game_idgame= "+games.get(i)+") GROUP BY username, game_idgame;";
			try {
				Statement stmt = conn.createStatemant();
				ResultSet rs = stmt.executeQuery(query);
				if (rs.next()) {
	                if(rs.getString("username").equals(account.getUsername())) {
	                	loses++;
	                }
	            }
				stmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return loses;
	}
}
