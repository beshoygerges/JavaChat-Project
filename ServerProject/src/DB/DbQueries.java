/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.User;

/**
 *
 * @author ahmed mohsen
 */
public class DbQueries implements DbInt{
    
        private static DbQueries instance = null;
       
        final static String URL="jdbc:mysql://localhost:3306/chat";
	final static String USERNAME="root";
	final static String PASSWORD="";
        private Connection con;
   private DbQueries() {
      // Exists only to defeat instantiation.
      try {
		con=DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }

   public static DbQueries getInstance() {
      if(instance == null) {
         instance = new DbQueries();
      }
      return instance;
   }
   
        @Override
   public boolean isExist(String email){
       boolean exist=true;
            try {
                PreparedStatement select=con.prepareStatement("select COUNT(*) from user where email = ?");
                select.setString(1, email);
                ResultSet rs= select.executeQuery();
                rs.next();
                if(rs.getInt(1) < 1){
                    exist = false;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return exist;
            
   }
   
        @Override
   public User getUserData(String email){
            User user = null;
       try {
		PreparedStatement select=con.prepareStatement("select * from user where email = ?");
                select.setString(1, email);
                ResultSet rs= select.executeQuery();
		while(rs.next()){
			
                    user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("country"), rs.getString("birthdate"), rs.getString("status"), rs.getString("gender"), rs.getString("onlinestatus"));
			
		}
		rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return user;
   }
   @Override
   public int insertUser(User bean){
		int result=0;
		
		try {
		 PreparedStatement insert =	con.prepareStatement("insert into user(username,email,password,country,birthdate,status,gender,onlinestatus) values(?,?,?,?,?,?,?,?)");
		insert.setString(1, bean.getUserName());
		insert.setString(2, bean.getEmail());
		insert.setString(3, bean.getPassword());
		insert.setString(4, bean.getCountry());
		insert.setString(5, bean.getBirthDate());
		insert.setString(6, bean.getStatus());
		insert.setString(7, bean.getGender());
		insert.setString(8, bean.getOnlineStatus());
		result = insert.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
   @Override
   public int updateUser(User bean){
		int result=0;
		
		try {
		PreparedStatement update =con.prepareStatement("update user set username=?,email=?,password=?,country=?,birthdate=?,status=?,gender=?,onlinestatus=? where id=?");
		update.setString(1, bean.getUserName());
		update.setString(2, bean.getEmail());
		update.setString(3, bean.getPassword());
		update.setString(4, bean.getCountry());
		update.setString(5, bean.getBirthDate());
		update.setString(6, bean.getStatus());
		update.setString(7, bean.getGender());
		update.setString(8, bean.getOnlineStatus());
		update.setInt(9, bean.getId());
		result = update.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}

    @Override
    public int updateStatus(int id, String status) {
         int result=0;
		
		try {
		PreparedStatement update =con.prepareStatement("update user set status=? where id=?");
		
		update.setString(1, status);
		
		update.setInt(2, id);
		result = update.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
    }

    @Override
    public int updateOnline(int id, String onlineStatus) {
        int result=0;
		
		try {
		PreparedStatement update =con.prepareStatement("update user set onlinestatus=? where id=?");
		
		update.setString(1, onlineStatus);
		
		update.setInt(2, id);
		result = update.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
    }

    @Override
    public ArrayList<User> getFriends(int id) {
        ArrayList<User> friends = new ArrayList<User>();
       try {
		PreparedStatement select=con.prepareStatement("select peoplef.id   as id,peoplef.username as username," +
                "peoplef.email as email,peoplef.password as password,peoplef.birthdate as birthdate," +
                "peoplef.status as status,peoplef.country as country,peoplef.gender as gender,peoplef.onlinestatus as onlinestatus" +
                "  from user join friendship" +
                "  on user.id = friendship.user_id or user.id = friendship.friend_id" +
                " join user peoplef" +
                "  on (peoplef.id = friendship.user_id and peoplef.id <> user.id) or (peoplef.id = friendship.friend_id and peoplef.id <> user.id)" +
                " WHERE user.id =?");   
                select.setInt(1, id);
                ResultSet rs= select.executeQuery();
		while(rs.next()){
			
                    friends.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("country"), rs.getString("birthdate"), rs.getString("status"), rs.getString("gender"), rs.getString("onlinestatus")));
			
		}
		rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       return friends;
    }
    
    @Override
    public void addFriend(int id, int friendId, String category) {
            
            try 
            {
                PreparedStatement insertFriend=con.prepareStatement("insert into friendship (user_id,friend_id,category,status) values(?,?,?,?)");
                insertFriend.setInt(1, id);
                insertFriend.setInt(2, friendId);
                insertFriend.setString(3,category );
                insertFriend.setInt(4, 0);
                
                insertFriend.executeUpdate();
            }
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
    }

    @Override
    public void acceptFriend(int id, int friendId) {
            try 
            {
                PreparedStatement accept=con.prepareStatement("update friendship set status='1' where id=? and friend_id=?");
                accept.setInt(1, id);
                accept.setInt(2, friendId);
                
                accept.executeUpdate();
            }
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
    }

    @Override
    public void blockFriend(int id, int friendId) {
        
            try 
            {
                PreparedStatement block=con.prepareStatement("update friendship set status='2' where id=? and friend_id=?");
                block.setInt(1, id);
                block.setInt(2, friendId);
                
                block.executeUpdate();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
    }
   
   
    
}
