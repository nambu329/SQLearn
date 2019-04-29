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
	JMenu menu3 = new JMenu("편집");
	JMenuItem[] item = new JMenuItem[3]; //아이템을 만들 자리를 확보한 것
	JMenuItem[] item2 = new JMenuItem[2]; //아이템을 만들 자리를 확보한 것
	JMenuItem edit = new JMenuItem("Edit");
	JMenuItem delete = new JMenuItem("Delete");
	JMenuItem runMenu = new JMenuItem("Run");
	String[] item_title = {"DB접속정보", "저장", "계정테이블"};
	JPanel p_loginStatus;
	JTextField t_url; //localhost
	JTextField t_sid; //XE
	JTextField t_port;
	JTextField t_user;
	Choice ch_user;
	JPasswordField t_password;
	JTable t_result; // 수행 결과 출력
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
	String[][] colData; //FindColType메서드에 있는거
	int total = 0; //FindColType에 있는 토탈 줄수 계산 변수
//	JFrame test;
	EditFrame editFrame;
	int cnt = 0;
	//엑셀 저장 자원
	HSSFWorkbook workbook; //엑셀
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
		menu = new JMenu("파일");
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
		bt_do = new JButton("쿼리 실행");
		registedTable = new RegistedTable(this);
		saveChooser = new JFileChooser("C:/Users/HAKSUNNAM/Desktop");
		saveChooser.setFileSelectionMode(saveChooser.DIRECTORIES_ONLY); 		
		
		area_input.setFont(new Font("돋움", Font.BOLD, 15));
		
		sc_area.setPreferredSize(new Dimension(760, 245));
		sc_resultTable.setPreferredSize(new Dimension(760, 215));
		
	
//		p_loginStatus.setBackground(Color.RED);		
		
		//바에 메뉴 붙이기
		bar.add(menu);
		bar.add(menu2);
		bar.add(menu3);
		menu.add(item[0]);
		menu.add(item[1]);
		menu2.add(runMenu);
		menu3.add(edit);
		menu3.add(delete);
		//구분선 
		menu.addSeparator();
		menu.add(item[2]);

		//바를 프레임에 붙이기
		this.setJMenuBar(bar); //이거 조심
		p_loginStatus.add(sc_resultTable);
		p_loginStatus.add(sc_area);
		this.add(p_loginStatus);
		
//		test.add(sc_colTypeTable);
		
		//단축키 만들기
		runMenu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,InputEvent.CTRL_MASK));
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE,InputEvent.CTRL_MASK));
		item[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_MASK));
		item[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		item[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,InputEvent.CTRL_MASK));
		
		//단축키 주입
		runMenu.addActionListener(this);
		edit.addActionListener(this);
		delete.addActionListener(this);
		//테이블과 마우스 리스너 연결
		t_result.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 1) { //더블클릭은 2 사실 이렇게 할필요없지만 더블클릭구현시 이런방식으로 하면된다는 걸 상기
					int row = t_result.getSelectedRow();
					int col = 0;
					int col2 = t_result.getSelectedColumn();
					valueForDelete = (String) t_result.getValueAt(row,col);
					valueForColType = (String) t_result.getColumnName(col2);
					valueForEdit = (String) t_result.getValueAt(row, col2);

					System.out.println("row" + row + "col2" + col2);
					System.out.println(valueForEdit + "좌표확인");
					System.out.println(valueForColType + "이건가");
				}
			}
		});
		
		//종료 로직
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
		con=connectionManager.connect("system","manager");//버튼에 의해 접속하지 말고, 미리 연결해놓는다!!
		loginInfo.getUsers(con); //db접속 정보 + 계정 불러오는 메서드
		this.setBounds(500, 350, 800, 550);
		this.setVisible(true);	
	}
	public void actionPerformed(ActionEvent e) {
		//e가 이벤트를 일으킨 주체
		Object obj = e.getSource(); //이벤트 일으킨 컴포넌트 반환(사용성을 위해 한것, 그냥 바로 써도됨)
		if(obj == item[0]) {
			loginInfo();
		}
		else if(obj == item[1]) {
			System.out.println("컨트롤 + s 누름");
			int result = saveChooser.showSaveDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) {
				saveFile();
			}
		}
		else if(obj == item[2]) {
			showTable();
		}
		else if(obj == runMenu) {
			System.out.println("컨트롤 + 엔터 누름");
			runCode();
		}
		else if(obj == edit) {
			System.out.println("수정 누름");
			edit();
			FindColType();
//			editFrame = new EditFrame(this);
//			editFrame.setVisible(true);
		}
		else if(obj == delete) {
			System.out.println("삭제 누름");
			int delete = JOptionPane.showConfirmDialog(MainGUI.this, "삭제 하시겠습니까?");
			if(delete == 0) {
				delete();
			}
		}		
	}
	public void runCode() {
		//단축키 이벤트들 	
		if(area_input.getText().length() > 0) {
			System.out.println("쿼리문 실행");
			runSQL();			
		} else {
			JOptionPane.showMessageDialog(MainGUI.this, "쿼리문을 입력하세요");
		}
	}
	//수행여부 판단 메서드
	public void runSQL() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = area_input.getText();
		try {
			pstmt = con.prepareStatement(sql);
			boolean result = pstmt.execute();
			if(result) { //true일경우 select
				System.out.println("select문 수행");	
				cnt += 1;
//				System.out.println(cnt);
				getRecords(sql);
			} else { //DML 수행
				System.out.println("DML수행");
				cnt += 1;
				getRecords("select * from " + registedTable.tableName);
//				System.out.println(cnt);
//				JOptionPane.showMessageDialog(MainGUI.this, "쿼리가 정상적으로 수행되었습니다.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(MainGUI.this, "executeSQL에서 발생한 오류(오타의 가능성이 있습니다.)");
			e.printStackTrace();
		}
	}	
	//선택한 테이블의 레코드 가져오기
	public void getRecords(String sql) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;	
		try {
			pstmt=con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs=pstmt.executeQuery();
			//메타 정보 얻어서 모델에 적용
			ResultSetMetaData meta=rs.getMetaData();
			columnNameForGetRecord=new String[meta.getColumnCount()]; //컬럼 갯수 확인(배열)
			for(int i=0;i<columnNameForGetRecord.length;i++) {
				columnNameForGetRecord[i]=meta.getColumnName(i+1); //컬럼 갯수길이 만큼 컬럼의 이름들을 얻기
				System.out.println(columnNameForGetRecord[i]);
			}		
			valueTitle = columnNameForGetRecord[0];
//			System.out.println(valueTitle);
			rs.last();
			int total=rs.getRow();//레코드 총 갯수
			String[][] data=new String[total][columnNameForGetRecord.length];
			rs.beforeFirst();//원상 위치로
			for(int i=0;i<total;i++) {
				rs.next();//커서 한칸 전진
				for(int a=0;a<columnNameForGetRecord.length;a++) {
					data[i][a]=rs.getString(a+1); //데이터들 2차원 data배열에 집어넣기
				}
			}
			//모델의 멤버변수에 덮어쓰기
			resultModel= new ResultModel();
			resultModel.columnName=columnNameForGetRecord;
			resultModel.data=data;
			
			//setModel의 jtable적용시점(모든 수행 끝났을때)
			t_result.setModel(resultModel);
//			t_result.setValueAt(t_result.editCellAt(t_result.getSelectedRow(), t_result.getSelectedColumn()),t_result.getSelectedRow(),t_result.getSelectedColumn());
			t_result.updateUI();
			JOptionPane.showMessageDialog(MainGUI.this, "쿼리가 정상적으로 수행되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			connectionManager.closeDB(pstmt, rs);
		}
		registedTable.setVisible(false);
	}
	//컬럼타입확인용
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
			total=rs.getRow();//레코드 총 갯수
			colData=new String[total][columnNameForColType.length];
			rs.beforeFirst();//원상 위치로
			for(int i=0;i<total;i++) {
				rs.next();//커서 한칸 전진
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
	//수정
	//뭐가 필요할까? 카테고리 컬럼 갯수(이름으로 알아야함)
	//그리고 int인지 string인지 이것도 확인해줘야함
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
//			//FindColType 에서 갖고온 total(총 row 갯수)
//			int row = t_result.getSelectedRow();
//			for(int i = 0; i < total; i += 1) {		
//				if(colData[i][0].equals("NUMBER")) {
//					System.out.println("숫자형");
//					pstmt.setInt(i, (Integer)t_result.getValueAt(row, i));
//				} else if(colData[i][0].equals("VARCHAR2")) {
//					System.out.println("문자형");
//					pstmt.setString(i, (String)t_result.getValueAt(row, i));
//				}
//			}
//			int result = pstmt.executeUpdate();
//			if(result == 0) {
//				JOptionPane.showMessageDialog(MainGUI.this, "수정실패");
//			} else {
//				JOptionPane.showMessageDialog(MainGUI.this, "수정성공");
//			}
//		} catch (SQLException e) {
//			System.out.println("아직오류");
//			e.printStackTrace();
//		}
	}
	//삭제
	public void delete() {
		PreparedStatement pstmt = null;
		String sql = "delete from "+registedTable.tableName+" where "+valueTitle+"="+valueForDelete;
//		System.out.println(sql);
		try {
			pstmt = con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
			if(result == 0) {
				JOptionPane.showMessageDialog(this, "삭제에 실패하였습니다.");
			} else {
				JOptionPane.showMessageDialog(this, "삭제에 성공하였습니다.");
			}
		} catch (SQLException e) {
			System.out.println("delete 메서드에서 발생한 오류입니다.");
			e.printStackTrace();
		} finally {
			connectionManager.closeDB(pstmt);
		}
		getRecords("select * from " + registedTable.tableName);
	}
	public void loginInfo() {
		loginInfo.setVisible(true);
		System.out.println("로그인정보");
	}
	public void saveFile() {
		System.out.println("현재 테이블저장");
		//select문 실행
		workbook = new HSSFWorkbook(); //엑셀 생성
		sheet = workbook.createSheet(registedTable.tableName); //시트 생성
		String sql = "select * from " + registedTable.tableName;
//		System.out.println(sql);
		try {
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			pstmt = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); //쿼리문생성
			rs = pstmt.executeQuery();//쿼리문 전송
			//select문 외의 쿼리문 전송할때
//			pstmt = memberManager.con.prepareStatement(sql); //생성
//			int result = pstmt.executeUpdate(); //전송
			//메타데이터 분석(column명 조사목적)
			ResultSetMetaData meta = rs.getMetaData();
			int columnCount = meta.getColumnCount(); //컬럼수 확인 1부터 시작함
			System.out.println("컬럼카운트는 " + columnCount);
			excelRow = sheet.createRow(0);//row(행) 생성
			//제목 채워넣기
			for(int i = 1; i <= columnCount; i += 1) { //column 생성
				excelCell = excelRow.createCell(i-1); //row는 0부터 시작하기 때문
				excelCell.setCellValue(meta.getColumnName(i)); //column내 내용
			}
			//레코드 채우기
			//커서, column은 1부터, row는 0부터
			rs.last(); //레코드 줄 수 파악 목적 커서 이동
			int total_row = rs.getRow();
			rs.beforeFirst(); //커서 원위치
			for(int i = 1; i <= total_row; i += 1) { //index 1번 으로 시작하기때문(엑셀에서 두번째줄), 첫줄은 이미 채워져있음
				HSSFRow r = sheet.createRow(i); //레코드들이 들어갈 row 생성
				rs.next(); //한줄 레코드조사 후 작성되면 다음줄로 커서이동
				for(int j = 0; j < columnCount; j += 1) { //column수 만큼 cell생성
					HSSFCell cell = r.createCell(j); 
					cell.setCellValue(rs.getString(j+1)); //rs는 1부터 시작
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		try {
			String path = saveChooser.getSelectedFile().getAbsolutePath();
			workbook.write(new File(path));
			System.out.println("저장완료");
			JOptionPane.showMessageDialog(this, "저장하였습니다.");
		} catch (IOException e) {
			System.out.println("저장실패");
			JOptionPane.showMessageDialog(this, "저장에 실패했습니다. 확장자 미기입가능성이 있습니다.");
			e.printStackTrace();
		} 
	}
	public void showTable() {
		registedTable.getUserTables(con, this.connectionManager);
		registedTable.setVisible(true);	
		System.out.println("계정테이블");
	}

	public static void main(String[] args) {
		new MainGUI("SQLearn");
	}

}
