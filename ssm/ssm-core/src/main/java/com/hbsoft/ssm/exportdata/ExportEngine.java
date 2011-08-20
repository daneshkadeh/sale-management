package com.hbsoft.ssm.exportdata;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExportEngine {
	private String outPutFile;
	ArrayList dataList;
	Class classType;

	public ExportEngine(final String outPutFile, Class classType) {
		super();
		this.outPutFile = outPutFile;
		this.classType = classType;
	}

	public void exportExcel() {
		try {
			WritableWorkbook workbook = Workbook.createWorkbook(new File(outPutFile));
			WritableSheet sheet = workbook.createSheet("Sheet0", 0);
			// generate label
			Field[] fields = classType.getDeclaredFields();
			int col1 = 0;
			for (Field field : fields) {
				Label label = new Label(col1, 0, field.getName());
				sheet.addCell(label);
				col1++;
			}
			int row = 1;
			for (int i = 0; i < dataList.size(); i++) {
				int col = 0;
				for (Field field : fields) {
					try {
						Method method = classType.getMethod("get" + upperFirstChar(field.getName()));
						Object obj = method.invoke(dataList.get(i), null);
						Label labelData = null;
						if (obj instanceof java.lang.Number) {
							Number number = new Number(col, row, (Integer) obj);
							sheet.addCell(number);
						} else if (obj instanceof String) {
							labelData = new Label(col, row, obj.toString());
							sheet.addCell(labelData);
						} else if (obj instanceof Date) {
							DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
							labelData = new Label(col, row, df.format((Date) obj));
							sheet.addCell(labelData);
						} else {
							System.out.println("Not supported");
						}

					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					col++;
				}
				row++;
			}
			workbook.write();
			workbook.close();
			System.out.println("create excel finish");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void exportCSV() {

	}

	public void exportXML() {

	}

	private String upperFirstChar(String s) {
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	private void loadData() {
		if (dataList == null) {
			dataList = new ArrayList();
		}
	}

	public void setDataList(final ArrayList dataList) {
		this.dataList = dataList;
	}
}
