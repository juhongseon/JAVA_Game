package Client;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.smartcardio.Card;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.javafx.tk.Toolkit;

import UserForm.LoginForm;
import UserForm.UserForm;
import UserForm.WaitingRoomForm;

public class DisplayThread extends JFrame implements Runnable{
	
	public final int WIDTH = 360;
	public final int HEIGHT = 640;
	
	private volatile static DisplayThread uniqueInstance;
	private CardLayout card;
	private UserForm userForm;
	
	//Singleton constructor(ctor)
	private DisplayThread() {
		
		//title
		setTitle("캐치마이마인드");
		
		//position and size ( 360, 640 )
		Dimension screenSize = getToolkit().getScreenSize();
		setSize(WIDTH, HEIGHT);
		setBounds((screenSize.width - getSize().width)/2, (screenSize.height - getSize().height)/2,
				getSize().width, getSize().height);
		
		//layout - card
		card = new CardLayout();
		setLayout(card);
		
		
		//pushTemp////////////
		LoginForm login = LoginForm.getInstance(this);
		WaitingRoomForm waitingRoom = WaitingRoomForm.getInstance(this);
		getContentPane().add(login.getJPanel(), "login");
		getContentPane().add(waitingRoom.getJPanel(),"waitingRoom");
		 
		
		//exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	//Singleton Pattern
	public static DisplayThread getInstance() {
		if(uniqueInstance == null) {
			synchronized (DisplayThread.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new DisplayThread();
				}
			}
		}
		return uniqueInstance;
	}
	
	public CardLayout getCardLayout() {
		return card;
	}
	
	public  JPanel createJPanel(String path) {
		JPanel jp = new JPanel() {
			
			@Override
	         public void paintComponent(Graphics g) {
	        	 try {
	        		 ImageIcon img = new ImageIcon(getClass().getResource(path));
	        		 g.drawImage(img.getImage(), 0, 0,getWidth(),getHeight(), null);
	                 super.paintComponent(g);
	        	 }catch(Exception e) {
	        		 System.out.println("image is not applied");
	        	 }
	         }
		};
		jp.setOpaque(false);
		return jp;
	}
	@Override
	public void run() {
		
	}
}
