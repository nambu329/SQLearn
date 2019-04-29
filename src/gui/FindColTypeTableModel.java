//선택한 테이블의 컬럼 데이터 타입 반환 테이블
package gui;

import javax.swing.table.AbstractTableModel;

public class FindColTypeTableModel extends AbstractTableModel{
	String[] columnName=new String[1];
	String[][] data=new String[1][1];
	
	public int getRowCount() {
		return data.length;
	}
	public int getColumnCount() {
		return columnName.length;
	}
	public String getColumnName(int col) {
		return columnName[col];
	}
	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
}