package UserForm;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Client.UserInputThread;
import Client.UserMessageProcessor;

public class SignUpForm implements UserForm{

	private volatile static SignUpForm uniqueInstance;
	
	private UserMessageProcessor userMessageProcessor;
	private Socket socket;
	private UserInputThread unt;
	
	private JDialog jdialog;
	private JLabel idLabel;
	private JLabel pwdLabel;
	private JLabel pwdOkLabel;
	private JTextField idTF;
	private JTextField pwdTF;
	private JTextField pwdOkTF;
	private JButton isExist;
	private JButton check;
	private JButton cencel;
	
	private String inputedId;
	private boolean isChecked;
	private String currentId;
	
	private SignUpForm(Socket socket) {
		this.socket = socket;
		unt = UserInputThread.getInstance(socket);
		userMessageProcessor = new UserMessageProcessor();
		
		inputedId = "";
		isChecked = false;
		
		jdialog = new JDialog();
		idLabel = new JLabel("아이디:");
		pwdLabel = new JLabel("비밀번호:");
		pwdOkLabel = new JLabel("비밀번호 확인:");
		idTF = new JTextField();
		pwdTF = new JTextField();
		pwdOkTF = new JTextField();
		isExist = new JButton("중복체크");
		check = new JButton("확인");
		cencel = new JButton("취소");
		
		jdialog.setLayout(null);
		jdialog.setModal(true);
		jdialog.setTitle("회원가입");
		
		jdialog.setSize(350,350);
		idLabel.setBounds(30, 40, 50, 30);
		pwdLabel.setBounds(30, 110, 50, 30);
		pwdOkLabel.setBounds(30, 180, 50, 30);
		idTF.setBounds(100, 40, 100, 30);
		pwdTF.setBounds(100, 110, 100, 30);
		pwdOkTF.setBounds(100, 180, 100, 30);
		isExist.setBounds(220, 40, 100, 30);
		check.setBounds(130, 250, 80, 30);
		cencel.setBounds(220, 250, 80, 30);
		
		jdialog.add(idLabel);
		jdialog.add(pwdLabel);
		jdialog.add(pwdOkLabel);
		jdialog.add(idTF);
		jdialog.add(pwdTF);
		jdialog.add(pwdOkTF);
		jdialog.add(isExist);
		jdialog.add(check);
		jdialog.add(cencel);
		
		isExist.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(idTF.getText().equals("")) {
					//1. dialog 생성
					getDialog("아이디를 입력하세요", 150, 100);
				}else {
					currentId = idTF.getText();
					//1. json data를 만든다.
					String sendData = "{";
					sendData += userMessageProcessor.getJSONData("method", "1100");
					sendData += "," + userMessageProcessor.getJSONData("id", idTF.getText());
					sendData += "}";
					//2. 서버로 보낸다.
					unt.setInputData(sendData);
					Runnable userInputThread = unt;
					Thread userThread = new Thread(userInputThread);
					userThread.start();
				}
				
			}
		});
	}
	public static SignUpForm getInstance(Socket socket) {
		if(uniqueInstance == null) {
			synchronized (SignUpForm.class) {
				if(uniqueInstance == null) {
					uniqueInstance = new SignUpForm(socket);
				}
			}
		}
		return uniqueInstance;
	}
	@Override
	public void display() {
		
	}

	@Override
	public JPanel getJPanel() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setDialLogVisible() {
		jdialog.setVisible(true);
	}
	public void setDialLogInvisible() {
		jdialog.setVisible(false);
	}
	public void getDialog(String s,int width, int height) {
		JDialog jd = new JDialog();
		jd.setLayout(new FlowLayout());
		jd.setModal(true);
		
		JLabel jl = new JLabel(s);
		JButton jb = new JButton("확인");
		
		jd.add(jl);
		jd.add(jb);
		
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jd.setVisible(false);
			}
		});
		
		jd.setSize(width, height);
		jd.setVisible(true);
	}
	public void setIsCheck(boolean value) {
		isChecked = value;
		if(value == true) {
			inputedId = currentId;
			getDialog("사용할 수 있는 아이디 입니다!", 250, 100);
		} else {
			getDialog("사용할 수 없는 아이디 입니다!", 250, 100);
		}
	}
}
