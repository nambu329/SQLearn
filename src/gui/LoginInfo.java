package gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import util.ConnectionManager;

public class LoginInfo extends JFrame{
	ConnectionManager connectionManager;
	MainGUI mainGUI;
	JPanel p_info;
	JTextField t_url; //localhost
	JTextField t_sid; //XE
	JTextField t_port;
	JPasswordField t_password;
	Choice ch_user;
	JButton bt_connect;
	String user;
	
	public LoginInfo(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		connectionManager = new ConnectionManager();
		p_info = new JPanel();
		t_url = new JTextField("localhost");
		t_sid = new JTextField("XE");
		t_port = new JTextField("1521");
		ch_user = new Choice();
		t_password = new JPasswordField();
		bt_connect = new JButton("접속");
		
		Dimension d = new Dimension(150,25);	
		t_url.setPreferredSize(d);
		t_sid.setPreferredSize(d);
		t_port.setPreferredSize(d);
		ch_user.setPreferredSize(d);
		t_password.setPreferredSize(d);
		
		p_info.add(t_url);
		p_info.add(t_sid);
		p_info.add(t_port);
		p_info.add(ch_user);
		p_info.add(t_password);
		p_info.add(bt_connect);
		this.add(p_info);
		
		//버튼과 리스너 연결 
		bt_connect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//choice 에서 선택한 계정으로 재 접속!!
				connectionManager.closeDB(mainGUI.con);
				user=ch_user.getSelectedItem();
				String pass=new String(t_password.getPassword());
				
				mainGUI.con=connectionManager.connect(user, pass);
				if(mainGUI.con==null) {
					JOptionPane.showMessageDialog(mainGUI , "올바른 접속 정보를 입력하세요");
				}else {
					JOptionPane.showMessageDialog(mainGUI , user + "로 접속합니다.");
					mainGUI.setTitle(user + "로 접속중..");
				}
				setVisible(false);
			}
		});
		
		setBounds(300, 400, 200, 300);
		setVisible(false);	
	}
	//유져 계정 가져오기 
	public void getUsers(Connection con) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select username from dba_users order by username asc";
		try {
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery();	
			while(rs.next()) {
				ch_user.add(rs.getString("username"));
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt, rs);
		}		
	}
}
