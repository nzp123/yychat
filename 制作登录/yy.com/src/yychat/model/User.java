package yychat.model;

import java.io.Serializable;

public class User implements Serializable{
    private String UserName;
    private String PassWord;
	
    public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;//局部变量给成员变量赋值
	}
	public String getPassWord() {
		return PassWord;
	}
	public void setPassWord(String passWord) {
		PassWord = passWord;
	}
    
}
