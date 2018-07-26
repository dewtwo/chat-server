package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

public class ChatClient {
	static ProfileGUI gui;
	static ChatClientGUI gui2;
	ClientSender cs;
	ClientReceiver cr;
	Set<String> userList = new HashSet<>();
	boolean chatState;
	Thread receiver;
	Vector<String> vt;

	public ChatClient() {}

	void setGUI(ChatClientGUI gui) {
		this.gui2 = gui;
	}
	void setClientSender(ClientSender cs) {
		this.cs = cs;
	}
	ClientSender getClientSender() {
		return cs;
	}
	public ClientReceiver getClientReceiver() {
		return cr;
	}
	public void setClientReceiver(ClientReceiver cr) {
		this.cr = cr;
	}
	public Vector<String> getVt() {
		return vt;
	}
	public void setVt(Vector<String> vt) {
		this.vt = vt;
	}

	public void start() {
		try {
			String serverIp = gui.getTextField().getText().trim();
			int portNumber = Integer.parseInt(gui.getTextField_3().getText());
			Socket socket = new Socket(serverIp, portNumber);
			connect(socket);
		} catch (ConnectException ce) {
			ce.printStackTrace();
		} catch (Exception e) {
		}
	}
	
	void connect(Socket socket){
		gui2.getBtnNewButton_1().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==gui2.getBtnNewButton_1()){
					String name = gui2.getTextField_1().getText();
					setClientSender(new ClientSender(socket, name));
					setClientReceiver(new ClientReceiver(socket));
					receiver = new Thread(cr);
					receiver.start();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		gui = new ProfileGUI();
	}

	class ClientReceiver extends Thread {
		Socket socket;
		DataInputStream in;

		public ClientReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
			}
		}

		public void run() {
			try {
				while (in != null) {
					String msg = in.readUTF();
					if(msg.contains("[로그인 실패]")){
						if(msg.contains("ID중복")){
							JOptionPane.showMessageDialog(null,"해당 닉네임이 이미 존재합니다.\n"
									+ "다른 닉네임을 입력해주세요!","닉네임 중복 오류",JOptionPane.INFORMATION_MESSAGE);
						} else if(msg.contains("불가문자포함")){
							JOptionPane.showMessageDialog(null,"|,[,]는 닉네임에 들어갈 수 없습니다.\n"
									+ "다른 닉네임을 입력해주세요!","닉네임 중복 오류",JOptionPane.INFORMATION_MESSAGE);
						}
						chatState = false;
						return;
					} else if(msg.contains("[로그인 성공]")){
						chatState = true;
						gui2.getBtnNewButton_1().setEnabled(false);
						gui2.getTextArea().setText("서버에 연결되었습니다.\n");
					} else if(msg.contains("[클라이언트]") && chatState == true){
						printList(msg);
					} else if(chatState == true){
						gui2.appendMsg(msg);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void printList(String msg){
			vt = new Vector<>();
			int index = msg.indexOf("]");
			String list = msg.substring(index+1);
			StringTokenizer st = new StringTokenizer(list,"|");
			while(st.hasMoreTokens()){
				vt.add(st.nextToken());
			}
			gui2.getList().setListData(vt);
			gui2.getTextField().setText(vt.size()+"명");
			gui2.getComboBox_1().setModel(new DefaultComboBoxModel<>(getVt()));
		}
	}

	class ClientSender {
		Socket socket;
		DataOutputStream out;
		String name;

		public ClientSender(Socket socket, String name) {
			this.socket = socket;
			try {
				out = new DataOutputStream(socket.getOutputStream());
				this.name = name;
				if (out != null) {
					out.writeUTF(name);
				}
			} catch (Exception e) {
			}
		}
		
		public void sendMsg(String msg) {
			try {
				out.writeUTF("[" + name + "] " + msg + "\n");
			} catch (IOException e) {
			}
		}
		
		public void secretMsg(String name,String msg) {
			try {
				out.writeUTF("귓속말//  "+"[" + this.name + "] " + msg + "\n");
				out.writeUTF(name);
				gui2.getTextArea().append(name+"님에게 귓속말// "+"[" + this.name + "] " + msg + "\n");
			} catch (IOException e) {
			}
		}
	}
}
