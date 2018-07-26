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
		Collections.synchronizedMap(clients); // hashmap ����ȭ
	}

	public void socket() {
		int portNumber = 0;
		portNumber = Integer.parseInt(gui.getTextField_1().getText());
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gui.getTextArea().append("������ ���۵Ǿ����ϴ�.\n");
		connect();
	}

	public void connect() {

		while (true) {
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				e.printStackTrace();
			}
			gui.getTextArea().append("[" + socket.getInetAddress() + " : " + socket.getPort() + "]���� �����Ͽ����ϴ�.\n");
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
		String clientList = "[Ŭ���̾�Ʈ]";
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
						out.writeUTF("[�α��� ����]ID�ߺ�");
					} else if(name.contains("|")||name.contains("[")||name.contains("]")){
						out.writeUTF("[�α��� ����]�Ұ���������");
					} else {}
					name = in.readUTF();
				}
				out.writeUTF("[�α��� ����]");
				clients.put(name, out);
				list.put(name, socket.getInetAddress());
				sendToAll("[" + name + "] ���� �����ϼ̽��ϴ�.\n");
				gui.getTextField().setText(clients.size() + "��");
				Object[] clientList = printList().toArray();
				gui.getList().setListData(clientList);
				while (in != null) {
					sendList();
					result = in.readUTF();
					if(result.contains("�ӼӸ�")){
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
				sendToAll("#" + name + "���� �����̽��ϴ�.\n");
				clients.remove(name);
				list.remove(name);
				sendList();
				gui.getTextField().setText(clients.size() + "��");
				Object[] clientList = printList().toArray();
				gui.getList().setListData(clientList);
				gui.getTextArea().append("[" + socket.getInetAddress() + " : " + socket.getPort() + "]���� ������ �����Ͽ����ϴ�.\n");
				gui.getTextArea().append("���� ���� ������ ���� " + clients.size() + "�Դϴ�.\n");
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