package project1;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class ChatClientGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNewLabel;
	private JLabel label;
	private JLabel label_1;
	private JTextArea textArea;
	private JList list;
	ChatClient cc;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JComboBox comboBox_1;
	private JButton btnNewButton_1;
	private JComboBox comboBox;
	private String name;
	private JButton btnNewButton;

	public JTextField getTextField() {
		return textField;
	}
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
	public JTextField getTextField_1() {
		return textField_1;
	}
	public void setTextField_1(JTextField textField_1) {
		this.textField_1 = textField_1;
	}
	public JTextField getTextField_2() {
		return textField_2;
	}
	public void setTextField_2(JTextField textField_2) {
		this.textField_2 = textField_2;
	}
	public JTextArea getTextArea() {
		return textArea;
	}
	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	public JList getList() {
		return list;
	}
	public void setList(JList list) {
		this.list = list;
	}
	public JComboBox getComboBox_1() {
		return comboBox_1;
	}
	public void setComboBox_1(JComboBox comboBox) {
		this.comboBox_1 = comboBox_1;
	}
	public JButton getBtnNewButton_1() {
		return btnNewButton_1;
	}
	public void setBtnNewButton_1(JButton btnNewButton_1) {
		this.btnNewButton_1 = btnNewButton_1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public ChatClientGUI() {
		setTitle("JOY CHAT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 681, 491);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textArea = new JTextArea();
		textArea.setBackground(Color.PINK);
		textArea.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(12, 53, 458, 313);
		contentPane.add(scroll);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(541, 95, 112, 27);
		contentPane.add(textField);

		list = new JList();
		list.setValueIsAdjusting(true);
		list.setBackground(Color.LIGHT_GRAY);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll2 = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(482, 154, 171, 289);
		contentPane.add(scroll2);

		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(100, 376, 129, 21);
		contentPane.add(comboBox_1);
		comboBox_1.addActionListener(this);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "\uC804\uCCB4", "\uADD3\uC18D\uB9D0" }));
		comboBox.setBounds(12, 376, 76, 21);
		contentPane.add(comboBox);
		comboBox.addActionListener(this);

		textField_1 = new JTextField();
		textField_1.setBackground(Color.PINK);
		textField_1.setBounds(85, 11, 385, 32);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBackground(Color.PINK);
		textField_2.setBounds(12, 402, 458, 41);
		contentPane.add(textField_2);
		textField_2.addKeyListener(new KeyListener() {
			int n = 0;
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (comboBox.getSelectedIndex() == 0) {
						String msg = textField_2.getText();
						cc.getClientSender().sendMsg(msg);
						textField_2.setText("");
					} else if (comboBox.getSelectedIndex() == 1) {
						n = comboBox_1.getSelectedIndex();
						String msg = textField_2.getText();
						String name = (String) comboBox_1.getSelectedItem();
						comboBox_1.setSelectedIndex(n);
						cc.getClientSender().secretMsg(name, msg);
						textField_2.setText("");
					}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if ((comboBox.getSelectedIndex() == 1) && (e.getKeyCode() == KeyEvent.VK_ENTER)) {
					comboBox_1.setSelectedIndex(n);
				}
			}
		});

		btnNewButton = new JButton("\uBC29 \uCCAD\uC18C");
		btnNewButton.setBounds(482, 51, 171, 28);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(this);

		lblNewLabel = new JLabel("\uB098\uC758 \uB2C9\uB124\uC784");
		lblNewLabel.setBounds(12, 11, 76, 32);
		contentPane.add(lblNewLabel);

		label = new JLabel("\uC811\uC18D\uC790 \uC218");
		label.setBounds(482, 98, 57, 24);
		contentPane.add(label);

		label_1 = new JLabel("\uC811\uC18D\uC790 \uBA85\uB2E8");
		label_1.setBounds(530, 126, 102, 24);
		contentPane.add(label_1);

		btnNewButton_1 = new JButton("\uC785\uC7A5");
		btnNewButton_1.setBounds(482, 12, 65, 28);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\uC811\uC18D \uC885\uB8CC");
		btnNewButton_2.setBounds(551, 12, 102, 28);
		contentPane.add(btnNewButton_2);

		setVisible(true);
		cc = new ChatClient();
		cc.setGUI(this);
		cc.start();
	}

	void appendMsg(String msg) {
		textArea.append(msg);
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == comboBox) {
			int idx = comboBox.getSelectedIndex();
			switch (idx) {
			case 0:
				comboBox_1.setEnabled(false);
				break;
			case 1:
				comboBox_1.setEnabled(true);
				break;
			}
		} else if (obj == comboBox_1) {
			String name = comboBox_1.getSelectedItem().toString();
			setName(name);
		} else if (obj == btnNewButton) {
			cc.gui2.getTextArea().setText("");
		}
	}
}
