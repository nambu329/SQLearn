package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class EditFrame extends JFrame{
	JScrollPane scroll;
	JTextField tf_edit;
	MainGUI mainGUI;
	JButton editButton;
	public EditFrame(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		tf_edit = new JTextField(10);
		editButton = new JButton("수정하기");
		this.add(editButton);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainGUI.edit();
			}
		});
		this.add(tf_edit);
		this.add(editButton);
		setBounds(300, 500, 200, 100);
		setVisible(false);
	}
}
