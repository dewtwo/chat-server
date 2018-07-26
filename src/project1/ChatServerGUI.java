package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JButton;

public class ChatServerGUI extends JFrame {
	ChatServer cs;
	private JTextField textField;
	private JTextField textField_1;
	private JPanel contentPane;
	private JTextArea textArea;
	private JButton button;
	private JList list;

	public ChatServerGUI() {
		setTitle("JOY CHAT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 591, 429);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextArea();
		textArea.setBackground(Color.PINK);
		textArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(12, 10, 360, 335);
		contentPane.add(scroll);

		button = new JButton("\uC811\uC18D");
		button.setBounds(307, 355, 65, 23);
		contentPane.add(button);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button) {
					String portNum = textField_1.getText();
					try{
						int portNumber = Integer.parseInt(portNum);
						if (portNumber < 1023 || portNumber >= 65535) {
							JOptionPane.showMessageDialog(null, "port 번호는 1023~65535 사이의 숫자로 입력해주세요!", "port 번호 오류",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} catch (NumberFormatException e1){
						JOptionPane.showMessageDialog(null, "port 번호는 1023~65535 사이의 숫자로 입력해주세요!", "port 번호 오류",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					
					cs = new ChatServer();
					cs.start();
					button.setEnabled(false);
				}
			}
		});

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(454, 8, 109, 27);
		contentPane.add(textField);

		list = new JList();
		list.setBackground(Color.LIGHT_GRAY);
		JScrollPane scroll2 = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(390, 70, 173, 308);
		contentPane.add(scroll2);

		JLabel label = new JLabel("\uC811\uC18D\uC790 \uC218");
		label.setBounds(390, 11, 57, 24);
		contentPane.add(label);

		JLabel label_1 = new JLabel("\uC811\uC18D\uC790 \uBA85\uB2E8");
		label_1.setBounds(443, 45, 120, 24);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("port \uBC88\uD638");
		label_2.setBounds(12, 352, 57, 29);
		contentPane.add(label_2);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBackground(Color.WHITE);
		textField_1.setBounds(68, 354, 232, 24);
		contentPane.add(textField_1);

		setVisible(true);
	}

	public JTextField getTextField() {
		return textField;
	}
	public JTextField getTextField_1() {
		return textField_1;
	}
	public JTextArea getTextArea() {
		return textArea;
	}
	public JList getList() {
		return list;
	}
}