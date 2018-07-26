package project1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class ChatServer extends Thread {
	HashMap clients;
	HashMap<String, InetAddress> list = new HashMap<>();
	static ChatServerGUI gui;
	ServerSocket serverSocket = null;
	Socket socket = null;
	
	public ChatServer() {
		clients = new HashMap();
		Collections.synchronizedMap(clients); // hashmap 동기화
	}

	public void socket() {
		int portNumber = 0;
		portNumber = Integer.parseInt(gui.getTextField_1().getText());
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.getTextArea().append("서버가 시작되었습니다.\n");
		connect();
	}

	public void connect() {

		while (true) {
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			gui.getTextArea().append("[" + socket.getInetAddress() + " : " + socket.getPort() + "]에서 접속하였습니다.\n");
			ServerReceiver thread = new ServerReceiver(socket);
			thread.start();
		}
	}

	public void run() {
		socket();
	}

	void sendToAll(String msg) {
		Iterator it = clients.keySet().iterator();

		while (it.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream) clients.get(it.next());
				out.writeUTF(msg);
			} catch (IOException e) {
			}
		}
	}
	
	void sendToOne(String name,String msg){
		try {
			DataOutputStream out = (DataOutputStream)clients.get(name);
			out.writeUTF(msg);
		} catch (IOException e) {
		}
	}

	void sendList(){
		Iterator it = clients.keySet().iterator();
		while (it.hasNext()) {
			try {
				DataOutputStream out = (DataOutputStream) clients.get(it.next());
				out.writeUTF(updateList());
			} catch (IOException e) {
			}
		}
	}
	
	String updateList(){
		Iterator it = clients.keySet().iterator();
		String clientList = "[클라이언트]";
		while(it.hasNext()){
			clientList += it.next()+"|";
		}
		return clientList;
	}

	ArrayList<String> printList() {
		ArrayList<String> clientList = new ArrayList<>();
		Iterator it = list.keySet().iterator();
		String result = "";
		while (it.hasNext()) {
			String name = (String) it.next();
			String ip = list.get(name).getHostAddress();
			result = name + " : " + ip;
			clientList.add(result);
		}
		return clientList;
	}

	public static void main(String[] args) {
		ChatServer cs = new ChatServer();
		gui = new ChatServerGUI();
	}

	class ServerReceiver extends Thread {
		Socket socket;
		DataInputStream in;
		DataOutputStream out;

		public ServerReceiver(Socket socket) {
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
			}
		}

		public void run() {
			String name = "";
			String result = "";
			try {
				name = in.readUTF();
				while(!clients.isEmpty() && (clients.containsKey(name)||name.contains("|")||name.contains("[")||name.contains("]"))){
					if(clients.containsKey(name)){
						out.writeUTF("[로그인 실패]ID중복");
					} else if(name.contains("|")||name.contains("[")||name.contains("]")){
						out.writeUTF("[로그인 실패]불가문자포함");
					} else {}
					name = in.readUTF();
				}
				out.writeUTF("[로그인 성공]");
				clients.put(name, out);
				list.put(name, socket.getInetAddress());
				sendToAll("[" + name + "] 님이 입장하셨습니다.\n");
				gui.getTextField().setText(clients.size() + "명");
				Object[] clientList = printList().toArray();
				gui.getList().setListData(clientList);
				while (in != null) {
					sendList();
					result = in.readUTF();
					if(result.contains("귓속말")){
						String choiceName = in.readUTF();
						sendToOne(choiceName, result);
					} else{
						sendToAll(result);
					}
					gui.getTextArea().append(result);
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} finally {
				sendToAll("#" + name + "님이 나가셨습니다.\n");
				clients.remove(name);
				list.remove(name);
				sendList();
				gui.getTextField().setText(clients.size() + "명");
				Object[] clientList = printList().toArray();
				gui.getList().setListData(clientList);
				gui.getTextArea().append("[" + socket.getInetAddress() + " : " + socket.getPort() + "]에서 접속을 종료하였습니다.\n");
				gui.getTextArea().append("현재 서버 접속자 수는 " + clients.size() + "입니다.\n");
				try {
					if (clients.isEmpty()) {
						in.close();
						out.close();
					}
				} catch (Exception e2) {
				}
			}
		}
	}
}