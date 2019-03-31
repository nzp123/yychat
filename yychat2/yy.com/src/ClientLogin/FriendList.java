package ClientLogin;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class FriendList extends JFrame implements ActionListener,MouseListener
{//接口
    //成员变量
	CardLayout cardLayout;
	//第一个卡片
	JPanel myFriendPanel;
	
	JButton myFriendButton;
	JScrollPane myFriendJScrollpane;//滚动列表
	JPanel myFriendListJPanel;
	
	JPanel myStrangerBlackList;
	JButton myStrangerButton;
	JButton BlackListButton;
	
	//第二个卡片
	JPanel StrangerBlackJPanel;
	
	JButton myFriendJButton1;
	JButton BlackListButton1;
	
	JScrollPane strangerJScrollpane;
	JPanel StrangerBlack;
	JButton myStrangerButton1;
	JPanel StrangerBlackListJPanel;
	String userName;
	public static final int MYSTRANGERCOUNT=21;
	JLabel[] StrangerLabel=new JLabel[MYSTRANGERCOUNT];
	
	public static final int MYFRIENDCOUNT=51;
	JLabel[] myFriendLabel=new JLabel[MYFRIENDCOUNT];//对象数组
	public FriendList(String userName){
		this.userName= userName;
		myFriendPanel=new JPanel(new BorderLayout());//边界布局
		//System.out.println(myFriendPanel.getLayout());
		myFriendButton=new JButton("我的好友");
		myFriendPanel.add(myFriendButton,"North");
		
		myFriendListJPanel=new JPanel(new GridLayout(MYFRIENDCOUNT-1,1));
		for(int i=1;i<MYFRIENDCOUNT;i++){
			myFriendLabel[i]=new JLabel(i+"",new ImageIcon("images/yy2.gif"),JLabel.LEFT);
			myFriendLabel[i].addMouseListener(this);
			myFriendListJPanel.add(myFriendLabel[i]);
		  }
		myFriendJScrollpane=new JScrollPane(myFriendListJPanel);
		myFriendPanel.add(myFriendJScrollpane);
		
		myStrangerButton=new JButton("陌生人");
		myStrangerButton.addActionListener(this);//事件监听器
		BlackListButton=new JButton("黑名单");
		
		myStrangerBlackList=new JPanel(new GridLayout(2,1));
		myStrangerBlackList.add(myStrangerButton);
		myStrangerBlackList.add(BlackListButton);
		myFriendPanel.add(myStrangerBlackList,"South");
		
		//第二个卡片
		StrangerBlackJPanel=new JPanel(new BorderLayout());
		BlackListButton1=new JButton("黑名单");
		myFriendJButton1=new JButton("我的好友");
		myStrangerButton1=new JButton("陌生人");
		myFriendJButton1.addActionListener(this);//事件监听器
		StrangerBlackJPanel.add(BlackListButton1,"South");
		
		StrangerBlackListJPanel=new JPanel(new GridLayout(MYSTRANGERCOUNT-1,1));
		for(int i=1;i<MYSTRANGERCOUNT;i++){
			StrangerLabel[i]=new JLabel(i+"",new ImageIcon("images/yy4.gif"),JLabel.LEFT);
			StrangerLabel[i].addMouseListener(this);
			StrangerBlackListJPanel.add(StrangerLabel[i]);
		  }
		strangerJScrollpane=new JScrollPane(StrangerBlackListJPanel);
		StrangerBlackJPanel.add(strangerJScrollpane);
		
		StrangerBlack=new JPanel(new GridLayout(2,1));
		StrangerBlack.add(myFriendJButton1);
		StrangerBlack.add(myStrangerButton1);
		StrangerBlackJPanel.add(StrangerBlack,"North");
		
		cardLayout=new CardLayout();
		this.setLayout(cardLayout);
		this.add(myFriendPanel,"1");
		this.add(StrangerBlackJPanel,"2");
		
		
		this.setSize(200, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle(userName);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		//FriendList friendList=new FriendList();
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource()==myStrangerButton) cardLayout.show(this.getContentPane(),"2");
		if(arg0.getSource()==myFriendJButton1) cardLayout.show(this.getContentPane(),"1");
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getClickCount()==2){
			JLabel jlb1=(JLabel)arg0.getSource();
			String receiver=jlb1.getText();
			new Thread(new FriendChat(this.userName,receiver)).start();
		}
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		JLabel jlbl1=(JLabel)arg0.getSource();
		jlbl1.setForeground(Color.red);
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		JLabel jlbl2=(JLabel)arg0.getSource();
		jlbl2.setForeground(Color.BLACK);
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
