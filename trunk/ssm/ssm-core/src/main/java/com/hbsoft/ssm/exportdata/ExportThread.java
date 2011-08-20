package com.hbsoft.ssm.exportdata;

import java.io.IOException;
import java.util.ArrayList;

public class ExportThread extends Thread {

	private String targetFileName = "";
	private String targetDirectory = "";
	private String exportType = "";
	private boolean isOpened = true;
	private ArrayList dataList = null;
	private ExportEngine exportEngine = null;
	private Class classType;

	public ExportThread(String targetDir, String targetFileName, String exportType, Class classType) {
		targetDirectory = targetDir;
		this.targetFileName = targetFileName;
		this.exportType = exportType;
		this.classType = classType;
	}

	@Override
	public void run() {
		String outPutFile;
		outPutFile = targetDirectory + "/" + targetFileName + "." + exportType;
		exportEngine = new ExportEngine(outPutFile, classType);
		exportEngine.setDataList(dataList);
		export();
		if (isOpened == true) {
			openFile(outPutFile);
		}
	}

	private void export() {
		if (ExportType.EXCEL.equals(exportType)) {
			exportEngine.exportExcel();
		} else if (ExportType.CVS.equals(exportType)) {
			exportEngine.exportCSV();
		} else if (ExportType.XML.equals(exportType)) {
			exportEngine.exportXML();
		}

	}

	public void setDataList(ArrayList dataList) {
		this.dataList = dataList;
	}

	private void openFile(final String outPutFile) {
		try {
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + outPutFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isOpened() {
		return isOpened;
	}

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}

}
