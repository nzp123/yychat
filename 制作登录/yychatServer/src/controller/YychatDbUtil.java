package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YychatDbUtil {
   public static final String MYSQLDRIVER="com.mysql.jdbc.Driver";
   public static final String URL="jdbc:mysql://127.0.0.1:3306/yychat?useUnicode=true&characterEncoding=UTF-8";
   public static final String DBUSER="root";
   public static final String DBPASS="";
   
   public static void loadDriver(){
		try {
			Class.forName(MYSQLDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
   public static Connection getConnection(){
	   loadDriver();
	   Connection conn=null;
	try {
		conn = DriverManager.getConnection(URL,DBUSER,DBPASS);
	} catch (SQLException e) {
		e.printStackTrace();
	}
       return conn;
   }
	
   public static boolean loginValidate(String userName,String passWord){
	   boolean loginSuccess=false; 
	   ResultSet rs=null;
	   Connection conn=getConnection();
	   //3.建立一个preparedStatement
		String user_Login_Sql="select * from user where username=? and password=?";
		PreparedStatement ptmt=null;
		try {
			ptmt = conn.prepareStatement(user_Login_Sql);
			ptmt.setString(1, userName);
			ptmt.setString(2, passWord);
			//4.执行preparedStatment
			rs=ptmt.executeQuery();
			
			//5.判断结果集
			loginSuccess=rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeDB(conn,ptmt,rs);
		}
		return loginSuccess;
   }
	
   public static String getFriendString(String userName){
	   Connection conn=getConnection();
	   PreparedStatement ptmt=null;
	   ResultSet rs=null;
	   String friendString=" ";
	   String friend_Relation_Sql="select slaveuser from relation where majoruser=? and relationtype='1'";
		try {
			ptmt=conn.prepareStatement(friend_Relation_Sql);
			ptmt.setString(1, userName);
			rs=ptmt.executeQuery();
			while(rs.next()){//移动结果集中的指针，一个个的取出好友的名字
				friendString=friendString+rs.getString("slaveuser")+" ";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeDB(conn,ptmt,rs);
		}
		return friendString;
   }
  
   
   public static void closeDB( Connection conn,PreparedStatement ptmt,ResultSet rs){
	   if(conn!=null){
		 try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}   
	   }
	   
	   if(ptmt!=null){
			 try {
				ptmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}   
		   }
	   
	   if(rs!=null){
			 try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}   
		   }
   }

}
