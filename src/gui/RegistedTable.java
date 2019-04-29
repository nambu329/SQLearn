package gui;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import util.ConnectionManager;

public class RegistedTable extends JFrame{
	//현재 계정이 보유한 테이블 목록 가져오기
	JTable table_registed;//현재 계정이 보유한 객체들
	ResultModel resultModel;
	JScrollPane scroll;
	Connection con;
	ConnectionManager connectionManager;
	MainGUI mainGUI;
	String tableName;
	
	
	public RegistedTable(MainGUI mainGUI) {
		this.mainGUI = mainGUI;
		table_registed = new JTable();
		scroll = new JScrollPane(table_registed);
		add(scroll);
		//table_registed와 마우스 리스너 연결 
		table_registed.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row=table_registed.getSelectedRow();
				int col=0;
//				System.out.println(table_registed.getValueAt(row, col));		
				tableName=(String)table_registed.getValueAt(row, col);	
//				System.out.println(tableName);
				mainGUI.getRecords("select * from " + tableName);
			}
		});
		
		setBounds(300, 300, 500, 300);
		setVisible(false);
	}
	public void getUserTables(Connection con, ConnectionManager connectionManager) {
		this.con = con;
		this.connectionManager = connectionManager;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select table_name,tablespace_name from user_tables";
		try {
			pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			//컬럼 조사하기 
			ResultSetMetaData meta=rs.getMetaData();
			String[] columName=new String[meta.getColumnCount()];
			for(int i=0;i<meta.getColumnCount();i++) {
				columName[i]=meta.getColumnName(i+1);
//				System.out.println(columName[i]);
			}
			//이차원 배열에 레코드 채워넣기!!
			rs.last();
			int total=rs.getRow();
			String[][] data=new String[total][columName.length];
			
			rs.beforeFirst();//원위치!!
			
			for(int i=0;i<total;i++) {
				rs.next();
				for(int a=0;a<columName.length;a++){
					data[i][a]=rs.getString(a+1);
				}
			}
			resultModel=new ResultModel();
			resultModel.columnName=columName;
			resultModel.data=data;//교체!!
			
			table_registed.setModel(resultModel);
			table_registed.updateUI();			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt, rs);
		}		
	}
}
