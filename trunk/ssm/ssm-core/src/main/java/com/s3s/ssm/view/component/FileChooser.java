package com.s3s.ssm.view.component;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

/**
 * Dir chooser component.
 * 
 * @author Le Thanh Hoang
 */
public class FileChooser extends JPanel {
    private static final long serialVersionUID = 224298802918909546L;
    private static final String CHOOSE_FILE_TXT = "...";
    private static final String CHOOSER_DIALOG_TITLE = "Choose Directory";
    private final static int DEFAULT_TEXTFIELD_COLUMN = 20;
    private JTextField txtFldPath;
    private JButton chooseBtn;
    private JFileChooser dirFileChooser;
    private int fileSelectionMod = JFileChooser.FILES_AND_DIRECTORIES;

    public FileChooser() {
        this(null);
    }

    public FileChooser(String value) {
        setLayout(new MigLayout("insets 0 0 0 0", "[shrink][]"));
        txtFldPath = new JTextField();
        chooseBtn = new JButton(new ChooseDirAction());
        txtFldPath.setColumns(DEFAULT_TEXTFIELD_COLUMN);
        if (value != null) {
            txtFldPath.setText(value);
        }

        chooseBtn.setText(CHOOSE_FILE_TXT);
        add(txtFldPath, "cell 0 0");

        add(chooseBtn, "cell 1 0");
    }

    private class ChooseDirAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            dirFileChooser = new JFileChooser();
            // set propeeries for File Chooser
            dirFileChooser.setDialogTitle(CHOOSER_DIALOG_TITLE);
            dirFileChooser.setFileSelectionMode(fileSelectionMod);

            int option = dirFileChooser.showOpenDialog(SwingUtilities.getRootPane(FileChooser.this));
            if (option == JFileChooser.APPROVE_OPTION) {
                File f = dirFileChooser.getSelectedFile();
                txtFldPath.setText(f.getAbsolutePath());
                FileChooser.this.repaint();
                FileChooser.this.validate();
            }
        }
    }

    public int getFileSelectionMod() {
        return fileSelectionMod;
    }

    public void setFileSelectionMod(int fileSelectionMod) {
        this.fileSelectionMod = fileSelectionMod;
    }

    public String getFilePath() {
        return txtFldPath.getText();
    }
}
