package com.yychat.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import yychat.model.Message;
import yychat.model.User;

public class ClientConnetion {
     public static Socket s;//��̬��Ա����
	public ClientConnetion(){
	
	try {//�쳣����
		s=new Socket("127.0.0.1",3456);
		System.out.println("�ͻ���Socket"+s);
	} catch (IOException e) {
		e.printStackTrace();
	}//������ַ���ز��ַ
	
}
	public Message loginValidate(User user){
		//��������������
		ObjectOutputStream oos;
		Message mess=null;
	    try {
			oos=new ObjectOutputStream(s.getOutputStream());//����ֽ����������
		    oos.writeObject(user);
		    
		    ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
		    mess=(Message)ois.readObject();
		    
	    } catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	    return mess;
	}
}