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
	
	public static HashMap hmSocket=new HashMap<String,Socket>();//���ͣ�ͨ���࣬��ֵ��
	String userName;
	String passWord;
	public StartServer(){
		try {
			ss=new ServerSocket(3456);
            System.out.println("�������������ڼ���3456�˿�...");
			while(true){ 
			s=ss.accept();
			System.out.println(s);
			ObjectInputStream ois;
			//������
			ois=new ObjectInputStream(s.getInputStream());
			User user=(User)ois.readObject();
			userName=user.getUserName();
			passWord=user.getPassWord();
			System.out.println(user.getUserName());
			System.out.println(user.getPassWord());
			
			//ʹ�����ݿ�����֤�û�������
		    boolean loginSuccess=YychatDbUtil.loginValidate(userName, passWord);

			Message mess=new Message();
			mess.setSender("sender");
			mess.setReceiver(user.getUserName());
			//mess.setContent(content);
			if(loginSuccess){//�����á�==��������Ƚ�
				
				//��Ϣ���ݣ�����һ��Message����
				
				mess.setMessageType("1");//��֤ͨ��
				
				String friendString=YychatDbUtil.getFriendString(userName);
				mess.setContent(friendString);
				System.out.println(userName+"��ȫ�����ѣ�"+friendString);
				
				//����ÿһ���û���Ӧ��Socket
				hmSocket .put(userName, s);
				
				//��ν��ܿͻ���������Ϣ����һ���߳�������
				new ServerReceiverThread(s,hmSocket).start();
			
			}
			else{
				mess.setMessageType("0");//��֤��ͨ��
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
