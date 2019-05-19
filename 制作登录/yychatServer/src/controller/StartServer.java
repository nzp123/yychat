package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import yychat.model.Message;
import yychat.model.User;

public class StartServer {
    ServerSocket ss;
	Socket s;
	ObjectOutputStream oos;
	
	public static HashMap hmSocket=new HashMap<String,Socket>();//泛型，通用类，键值对
	String userName;
	String passWord;
	public StartServer(){
		try {
			ss=new ServerSocket(3456);
            System.out.println("服务器启动，在监听3456端口...");
			while(true){ 
			s=ss.accept();
			System.out.println(s);
			ObjectInputStream ois;
			//输入流
			ois=new ObjectInputStream(s.getInputStream());
			User user=(User)ois.readObject();
			userName=user.getUserName();
			passWord=user.getPassWord();
			System.out.println(user.getUserName());
			System.out.println(user.getPassWord());
			
			//使用数据库来验证用户和密码
		    boolean loginSuccess=YychatDbUtil.loginValidate(userName, passWord);

			Message mess=new Message();
			mess.setSender("sender");
			mess.setReceiver(user.getUserName());
			//mess.setContent(content);
			if(loginSuccess){//不能用“==”，对象比较
				
				//消息传递，创建一个Message对象
				
				mess.setMessageType("1");//验证通过
				
				String friendString=YychatDbUtil.getFriendString(userName);
				mess.setContent(friendString);
				System.out.println(userName+"的全部好友："+friendString);
				
				//保存每一个用户对应的Socket
				hmSocket .put(userName, s);
				
				//如何接受客户端聊天信息？另建一个线程来接收
				new ServerReceiverThread(s,hmSocket).start();
			
			}
			else{
				mess.setMessageType("0");//验证不通过
			}
			sendMessage(s,mess);

			}
		} catch (IOException | ClassNotFoundException  e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(Socket s,Message mess) throws IOException {
		oos=new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(mess);
	}
    
	public static void main(String[] args) {
	
}
}
