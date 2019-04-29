package gui;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import util.ConnectionManager;

public class MainGUI extends JFrame implements ActionListener{
	
	JMenuBar bar;
	JMenu menu;
	JMenu menu2;
	JMenu menu3 = new JMenu("����");
	JMenuItem[] item = new JMenuItem[3]; //�������� ���� �ڸ��� Ȯ���� ��
	JMenuItem[] item2 = new JMenuItem[2]; //�������� ���� �ڸ��� Ȯ���� ��
	JMenuItem edit = new JMenuItem("Edit");
	JMenuItem delete = new JMenuItem("Delete");
	JMenuItem runMenu = new JMenuItem("Run");
	String[] item_title = {"DB��������", "����", "�������̺�"};
	JPanel p_loginStatus;
	JTextField t_url; //localhost
	JTextField t_sid; //XE
	JTextField t_port;
	JTextField t_user;
	Choice ch_user;
	JPasswordField t_password;
	JTable t_result; // ���� ��� ���
	JTable t_colTypeTable;
	JTextArea area_input;
	JScrollPane sc_area; 
	JScrollPane sc_resultTable;
	JScrollPane sc_colTypeTable;	
	ResultModel resultModel;
	RegistedTable registedTable;
	Connection con;
	ConnectionManager connectionManager;
	LoginInfo loginInfo;
	JButton bt_do;
	String t_title;
	String valueForDelete;
	String valueForEdit;
	String valueForColType;
	String valueTitle;
	String[] columnNameForGetRecord;
	String[] columnNameForColType;
	FindColTypeTableModel findcoltypetablemodel;
	String[][] colData; //FindColType�޼��忡 �ִ°�
	int total = 0; //FindColType�� �ִ� ��Ż �ټ� ��� ����
//	JFrame test;
	EditFrame editFrame;
	int cnt = 0;
	//���� ���� �ڿ�
	HSSFWorkbook workbook; //����
	HSSFSheet sheet;
	HSSFRow excelRow;
	HSSFCell excelCell; //column
	JFileChooser saveChooser;
	
	public MainGUI(String title) {
		super(title);
//		test = new JFrame();
//		test.setSize(new Dimension(300, 300));
//		test.setVisible(true);
		
		bar = new JMenuBar();
		menu = new JMenu("����");
		for(int i = 0; i < item.length; i += 1) {
			item[i] = new JMenuItem(item_title[i]);
			item[i].addActionListener(this);
		}
		menu2 = new JMenu("RunCode");
		
		p_loginStatus = new JPanel();
		t_url = new JTextField("localhost");
		t_sid = new JTextField("XE");
		t_port = new JTextField("1521");
		t_user = new JTextField();
		ch_user = new Choice();
		t_password = new JPasswordField();
		t_result = new JTable();
		t_colTypeTable = new JTable();
		area_input = new JTextArea();
		sc_area = new JScrollPane(area_input);
		sc_resultTable = new JScrollPane(t_result); 
		sc_colTypeTable = new JScrollPane(t_colTypeTable);
		findcoltypetablemodel = new FindColTypeTableModel();
		loginInfo = new LoginInfo(this);
		bt_do = new JButton("���� ����");
		registedTable = new RegistedTable(this);
		saveChooser = new JFileChooser("C:/Users/HAKSUNNAM/Desktop");
		saveChooser.setFileSelectionMode(saveChooser.DIRECTORIES_ONLY); 		
		
		area_input.setFont(new Font("����", Font.BOLD, 15));
		
		sc_area.setPreferredSize(new Dimension(760, 245));
		sc_resultTable.setPreferredSize(new Dimension(760, 215));
		
	
//		p_loginStatus.setBackground(Color.RED);		
		
		//�ٿ� �޴� ���̱�
		bar.add(menu);
		bar.add(menu2);
		bar.add(menu3);
		menu.add(item[0]);
		menu.add(item[1]);
		menu2.add(runMenu);
		menu3.add(edit);
		menu3.add(delete);
		//���м� 
		menu.addSeparator();
		menu.add(item[2]);

		//�ٸ� �����ӿ� ���̱�
		this.setJMenuBar(bar); //�̰� ����
		p_loginStatus.add(sc_resultTable);
		p_loginStatus.add(sc_area);
		this.add(p_loginStatus);
		
//		test.add(sc_colTypeTable);
		
		//����Ű �����
		runMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,InputEvent.CTRL_MASK));
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,InputEvent.CTRL_MASK));
		item[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_MASK));
		item[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		item[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
		
		//����Ű ����
		runMenu.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		//���̺�� ���콺 ������ ����
		t_result.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 1) { //����Ŭ���� 2 ��� �̷��� ���ʿ������ ����Ŭ�������� �̷�������� �ϸ�ȴٴ� �� ���
					int row = t_result.getSelectedRow();
					int col = 0;
					int col2 = t_result.getSelectedColumn();
					valueForDelete = (String) t_result.getValueAt(row,col);
					valueForColType = (String) t_result.getColumnName(col2);
					valueForEdit = (String) t_result.getValueAt(row, col2);

					System.out.println("row" + row + "col2" + col2);
					System.out.println(valueForEdit + "��ǥȮ��");
					System.out.println(valueForColType + "�̰ǰ�");
				}
			}
		});
		
		//���� ����
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if(con !=null) {
					try {
						con.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				System.exit(0);				
			}
		});
		connectionManager = new ConnectionManager();
		con=connectionManager.connect("system","manager");//��ư�� ���� �������� ����, �̸� �����س��´�!!
		loginInfo.getUsers(con); //db���� ���� + ���� �ҷ����� �޼���
		this.setBounds(500, 350, 800, 550);
		this.setVisible(true);	
	}
	public void actionPerformed(ActionEvent e) {
		//e�� �̺�Ʈ�� ����Ų ��ü
		Object obj = e.getSource(); //�̺�Ʈ ����Ų ������Ʈ ��ȯ(��뼺�� ���� �Ѱ�, �׳� �ٷ� �ᵵ��)
		if(obj == item[0]) {
			loginInfo();
		}
		else if(obj == item[1]) {
			System.out.println("��Ʈ�� + s ����");
			int result = saveChooser.showSaveDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) {
				saveFile();
			}
		}
		else if(obj == item[2]) {
			showTable();
		}
		else if(obj == runMenu) {
			System.out.println("��Ʈ�� + ���� ����");
			runCode();
		}
		else if(obj == edit) {
			System.out.println("���� ����");
			edit();
			FindColType();
//			editFrame = new EditFrame(this);
//			editFrame.setVisible(true);
		}
		else if(obj == delete) {
			System.out.println("���� ����");
			int delete = JOptionPane.showConfirmDialog(MainGUI.this, "���� �Ͻðڽ��ϱ�?");
			if(delete == 0) {
				delete();
			}
		}		
	}
	public void runCode() {
		//����Ű �̺�Ʈ�� 	
		if(area_input.getText().length() > 0) {
			System.out.println("������ ����");
			runSQL();			
		} else {
			JOptionPane.showMessageDialog(MainGUI.this, "�������� �Է��ϼ���");
		}
	}
	//���࿩�� �Ǵ� �޼���
	public void runSQL() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = area_input.getText();
		try {
			pstmt = con.prepareStatement(sql);
			boolean result = pstmt.execute();
			if(result) { //true�ϰ�� select
				System.out.println("select�� ����");	
				cnt += 1;
//				System.out.println(cnt);
				getRecords(sql);
			} else { //DML ����
				System.out.println("DML����");
				cnt += 1;
				getRecords("select * from " + registedTable.tableName);
//				System.out.println(cnt);
//				JOptionPane.showMessageDialog(MainGUI.this, "������ ���������� ����Ǿ����ϴ�.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MainGUI.this, "executeSQL���� �߻��� ����(��Ÿ�� ���ɼ��� �ֽ��ϴ�.)");
			e.printStackTrace();
		}
	}	
	//������ ���̺��� ���ڵ� ��������
	public void getRecords(String sql) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;	
		try {
			pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			//��Ÿ ���� �� �𵨿� ����
			ResultSetMetaData meta=rs.getMetaData();
			columnNameForGetRecord=new String[meta.getColumnCount()]; //�÷� ���� Ȯ��(�迭)
			for(int i=0;i<columnNameForGetRecord.length;i++) {
				columnNameForGetRecord[i]=meta.getColumnName(i+1); //�÷� �������� ��ŭ �÷��� �̸����� ���
				System.out.println(columnNameForGetRecord[i]);
			}		
			valueTitle = columnNameForGetRecord[0];
//			System.out.println(valueTitle);
			rs.last();
			int total=rs.getRow();//���ڵ� �� ����
			String[][] data=new String[total][columnNameForGetRecord.length];
			rs.beforeFirst();//���� ��ġ��
			for(int i=0;i<total;i++) {
				rs.next();//Ŀ�� ��ĭ ����
				for(int a=0;a<columnNameForGetRecord.length;a++) {
					data[i][a]=rs.getString(a+1); //�����͵� 2���� data�迭�� ����ֱ�
				}
			}
			//���� ��������� �����
			resultModel= new ResultModel();
			resultModel.columnName=columnNameForGetRecord;
			resultModel.data=data;
			
			//setModel�� jtable�������(��� ���� ��������)
			t_result.setModel(resultModel);
//			t_result.setValueAt(t_result.editCellAt(t_result.getSelectedRow(), t_result.getSelectedColumn()),t_result.getSelectedRow(),t_result.getSelectedColumn());
			t_result.updateUI();
			JOptionPane.showMessageDialog(MainGUI.this, "������ ���������� ����Ǿ����ϴ�.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt, rs);
		}
		registedTable.setVisible(false);
	}
	//�÷�Ÿ��Ȯ�ο�
	public void FindColType() {
		String sql = "select data_type from cols where table_name="+"'"+registedTable.tableName+"'";
//		System.out.println(sql);
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			ResultSetMetaData meta=rs.getMetaData();
			columnNameForColType=new String[meta.getColumnCount()];
			for(int i=0;i<columnNameForColType.length;i++) {
				columnNameForColType[i]=meta.getColumnName(i+1);
				System.out.println(columnNameForColType[i]);
			}	
			rs.last();
			total=rs.getRow();//���ڵ� �� ����
			colData=new String[total][columnNameForColType.length];
			rs.beforeFirst();//���� ��ġ��
			for(int i=0;i<total;i++) {
				rs.next();//Ŀ�� ��ĭ ����
				for(int a=0;a<columnNameForColType.length;a++) {
					colData[i][a]=rs.getString(a+1);
					System.out.println(colData[i][a]);
					System.out.println(i);
//					System.out.println(a);
				}
			}
			t_colTypeTable.setModel(findcoltypetablemodel);
//			t_colTypeTable.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//����
	//���� �ʿ��ұ�? ī�װ� �÷� ����(�̸����� �˾ƾ���)
	//�׸��� int���� string���� �̰͵� Ȯ���������
	//select table_name, data_type from cols where table_name='PRODUCT';
	//update product set price=10000 where product_id=1
	public void edit() {
		PreparedStatement pstmt = null;	
		StringBuffer sb = new StringBuffer();
		System.out.println(valueForEdit);
		sb.append("update "+registedTable.tableName+" set "+valueForColType+"="+valueForEdit);
		sb.append(" where "+valueTitle+"="+valueForDelete);
		System.out.println(sb);
//		try {
//			pstmt = con.prepareStatement(sb.toString());
//			//FindColType ���� ����� total(�� row ����)
//			int row = t_result.getSelectedRow();
//			for(int i = 0; i < total; i += 1) {		
//				if(colData[i][0].equals("NUMBER")) {
//					System.out.println("������");
//					pstmt.setInt(i, (Integer)t_result.getValueAt(row, i));
//				} else if(colData[i][0].equals("VARCHAR2")) {
//					System.out.println("������");
//					pstmt.setString(i, (String)t_result.getValueAt(row, i));
//				}
//			}
//			int result = pstmt.executeUpdate();
//			if(result == 0) {
//				JOptionPane.showMessageDialog(MainGUI.this, "��������");
//			} else {
//				JOptionPane.showMessageDialog(MainGUI.this, "��������");
//			}
//		} catch (SQLException e) {
//			System.out.println("��������");
//			e.printStackTrace();
//		}
	}
	//����
	public void delete() {
		PreparedStatement pstmt = null;
		String sql = "delete from "+registedTable.tableName+" where "+valueTitle+"="+valueForDelete;
//		System.out.println(sql);
		try {
			pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if(result == 0) {
				JOptionPane.showMessageDialog(this, "������ �����Ͽ����ϴ�.");
			} else {
				JOptionPane.showMessageDialog(this, "������ �����Ͽ����ϴ�.");
			}
		} catch (SQLException e) {
			System.out.println("delete �޼��忡�� �߻��� �����Դϴ�.");
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt);
		}
		getRecords("select * from " + registedTable.tableName);
	}
	public void loginInfo() {
		loginInfo.setVisible(true);
		System.out.println("�α�������");
	}
	public void saveFile() {
		System.out.println("���� ���̺�����");
		//select�� ����
		workbook = new HSSFWorkbook(); //���� ����
		sheet = workbook.createSheet(registedTable.tableName); //��Ʈ ����
		String sql = "select * from " + registedTable.tableName;
//		System.out.println(sql);
		try {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //����������
			rs = pstmt.executeQuery();//������ ����
			//select�� ���� ������ �����Ҷ�
//			pstmt = memberManager.con.prepareStatement(sql); //����
//			int result = pstmt.executeUpdate(); //����
			//��Ÿ������ �м�(column�� �������)
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount(); //�÷��� Ȯ�� 1���� ������
			System.out.println("�÷�ī��Ʈ�� " + columnCount);
			excelRow = sheet.createRow(0);//row(��) ����
			//���� ä���ֱ�
			for(int i = 1; i <= columnCount; i += 1) { //column ����
				excelCell = excelRow.createCell(i-1); //row�� 0���� �����ϱ� ����
				excelCell.setCellValue(meta.getColumnName(i)); //column�� ����
			}
			//���ڵ� ä���
			//Ŀ��, column�� 1����, row�� 0����
			rs.last(); //���ڵ� �� �� �ľ� ���� Ŀ�� �̵�
			int total_row = rs.getRow();
			rs.beforeFirst(); //Ŀ�� ����ġ
			for(int i = 1; i <= total_row; i += 1) { //index 1�� ���� �����ϱ⶧��(�������� �ι�°��), ù���� �̹� ä��������
				HSSFRow r = sheet.createRow(i); //���ڵ���� �� row ����
				rs.next(); //���� ���ڵ����� �� �ۼ��Ǹ� �����ٷ� Ŀ���̵�
				for(int j = 0; j < columnCount; j += 1) { //column�� ��ŭ cell����
					HSSFCell cell = r.createCell(j); 
					cell.setCellValue(rs.getString(j+1)); //rs�� 1���� ����
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		try {
			String path = saveChooser.getSelectedFile().getAbsolutePath();
			workbook.write(new File(path));
			System.out.println("����Ϸ�");
			JOptionPane.showMessageDialog(this, "�����Ͽ����ϴ�.");
		} catch (IOException e) {
			System.out.println("�������");
			JOptionPane.showMessageDialog(this, "���忡 �����߽��ϴ�. Ȯ���� �̱��԰��ɼ��� �ֽ��ϴ�.");
			e.printStackTrace();
		} 
	}
	public void showTable() {
		registedTable.getUserTables(con, this.connectionManager);
		registedTable.setVisible(true);	
		System.out.println("�������̺�");
	}

	public static void main(String[] args) {
		new MainGUI("SQLearn");
	}

}
