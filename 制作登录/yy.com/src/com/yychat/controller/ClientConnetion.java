package com.yychat.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import yychat.model.Message;
import yychat.model.User;

public class ClientConnetion {
     public static Socket s;//静态成员变量
     public static HashMap hmSocket=new HashMap<String,Socket>();
     
	public ClientConnetion(){
	
	try {//异常处理
		s=new Socket("127.0.0.1",3456);
		System.out.println("客户端Socket"+s);
	} catch (IOException e) {
		e.printStackTrace();
	}//本机地址，回测地址
	
}
	public Message loginValidate(User user){
		//对象的输入输出流
		ObjectOutputStream oos;
		Message mess=null;
	    try {
			oos=new ObjectOutputStream(s.getOutputStream());//获得字节输出流对象
		    oos.writeObject(user);
		    
		    ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
		    mess=(Message)ois.readObject();
		    
		    if(mess.getMessageType().equals("1")){
		    	hmSocket.put(user.getUserName(), s);
		    	new ClientReceiverThread(s).start();
		    }
		    
	    } catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	    return mess;
	}
}
