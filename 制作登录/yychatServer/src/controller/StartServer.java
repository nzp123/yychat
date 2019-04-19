package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import yychat.model.Message;
import yychat.model.User;

public class StartServer {
    ServerSocket ss;
	Socket s;
	
	public static HashMap hmSocket=new HashMap<String,Socket>();//泛型，通用类，键值对
	String userName;
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
			this.userName=user.getUserName();
			System.out.println(user.getUserName());
			System.out.println(user.getPassWord());
			
			//Server端验证密码是否“123456”
			Message mess=new Message();
			mess.setSender("sender");
			mess.setReceiver(user.getUserName());
			//mess.setContent(content);
			if(user.getPassWord().equals("123456")){//不能用“==”，对象比较
				//消息传递，创建一个Message对象
				mess.setMessageType("1");//验证通过
				
				//保存每一个用户对应的Socket
				hmSocket .put(userName, s);
				
				//如何接受客户端聊天信息？另建一个线程来接收
				new ServerReceiverThread(s,hmSocket).start();
			}
			else{
			    mess.setMessageType("0");//验证不通过
			}
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(mess);
			
		
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
    
	public static void main(String[] args) {
	
}
}
