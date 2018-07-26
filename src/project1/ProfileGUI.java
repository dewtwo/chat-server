package project1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ProfileGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_3;
	private JButton btnNewButton;
	ChatClientGUI cc;

	public JTextField getTextField() {
		return textField;
	}
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	public JTextField getTextField_3() {
		return textField_3;
	}
	public void setTextField_3(JTextField textField_3) {
		this.textField_3 = textField_3;
	}

	public ProfileGUI() {
		setTitle("JOY CHAT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 370, 182);
		contentPane = new JPanel();
		contentPane.setForeground(Color.BLACK);
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblI = new JLabel("IP\uC8FC\uC18C");
		lblI.setBounds(25, 58, 65, 30);
		contentPane.add(lblI);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBackground(Color.PINK);
		textField.setBounds(89, 63, 232, 21);
		contentPane.add(textField);
		
		JLabel label_3 = new JLabel("\uD3EC\uD2B8\uBC88\uD638");
		label_3.setForeground(Color.BLACK);
		label_3.setBackground(Color.WHITE);
		label_3.setBounds(25, 17, 65, 35);
		contentPane.add(label_3);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBackground(Color.PINK);
		textField_3.setBounds(89, 24, 232, 21);
		contentPane.add(textField_3);
		
		btnNewButton = new JButton("\uC811\uC18D");
		btnNewButton.setBounds(137, 107, 97, 27);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cc = new ChatClientGUI();
				setVisible(false);
			}
		});
		
		setVisible(true);
	}
}
