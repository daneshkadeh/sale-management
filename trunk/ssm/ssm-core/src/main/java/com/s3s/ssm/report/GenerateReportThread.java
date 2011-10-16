package com.s3s.ssm.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class GenerateReportThread extends Thread {
    private String outPutFile = "";
    private String reportType = "";
    private String reportPath = "";
    private boolean isOpened = true;

    HashMap parameterMap = new HashMap();
    Connection connection;
    JRExporter exporter;
    JasperPrint print;

    public GenerateReportThread(final String targetDir, final String targetFileName, final String reportPath,
            final String reportType) {
        this.reportPath = reportPath;
        this.reportType = reportType;
        this.outPutFile = targetDir + "/" + targetFileName + "." + reportType;
    }

    @Override
    public void run() {
        try {
            print = JasperFillManager.fillReport(reportPath, parameterMap, connection);
            setExporter();
            exporter.exportReport();
            connection.close();
            if (isOpened == true) {
                try {
                    Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + outPutFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (JRException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void setParameterMap(HashMap parameterMap) {
        this.parameterMap = parameterMap;
    }

    private void setExporter() throws Exception {
        if (ReportType.PDF.equals(reportType)) {
            setJRPdfExporter();
        } else if (ReportType.EXCEL.equals(reportType)) {
            setJRXlsExporter();
        } else if (ReportType.CSV.equals(reportType)) {
            setJRCsvExporter();
        } else if (ReportType.XML.equals(reportType)) {
            setJRXmlExporter();
        } else {
            throw new Exception("Not supported");
        }
    }

    private void setJRPdfExporter() {
        exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outPutFile);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
    }

    private void setJRXlsExporter() {
        try {
            exporter = new JRXlsExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, new FileOutputStream(new File(outPutFile)));
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setJRCsvExporter() {
        exporter = new JRCsvExporter();
    }

    private void setJRXmlExporter() {

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

}