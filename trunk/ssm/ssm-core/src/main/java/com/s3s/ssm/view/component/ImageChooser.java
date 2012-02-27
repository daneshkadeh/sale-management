/*
 * ImageChooser
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.component;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.util.IOUtils;
import org.jdesktop.swingx.JXHyperlink;

/**
 * Image chooser component. TODO: Should we scale image before saving to reduce the size of image to improve speed of
 * application?
 * 
 * @author Phan Hong Phuc
 */
public class ImageChooser extends JPanel {

    private static final long serialVersionUID = -262102422715201322L;

    private static final String[] IMAGE_EXTENSION = { "jpeg", "jpg", "JPG", "JPEG", "gif", "png" };
    private static final String REMOVE_IMG_TXT = "Remove";
    private static final String ADD_IMAGE_TXT = "Add image";
    private static final String CHANGE_IMG_TXT = "Change image";
    private static final int IMG_HEIGHT = 150;
    private static final int IMG_WIDTH = 150;
    private JLabel imgLabel; // the JLabel contains the image icon
    private JXHyperlink hyperLinkBrowseImg;
    private JButton btnRemoveImg;
    private byte[] imageData;

    public ImageChooser() {
        this(null);
    }

    public ImageChooser(byte[] imageData) {
        this.imageData = imageData;

        setLayout(new MigLayout("insets 0 0 0 0", "[shrink][]"));

        imgLabel = new JLabel();
        hyperLinkBrowseImg = new JXHyperlink(new ChooseImageAction());
        hyperLinkBrowseImg.setText(ADD_IMAGE_TXT);

        // TODO HPP: change string "Remove" to Trashbin Icon
        btnRemoveImg = new JButton(REMOVE_IMG_TXT);
        btnRemoveImg.addActionListener(new RemoveImageAction());

        add(hyperLinkBrowseImg, "flowy, cell 1 0, top");

        if (ArrayUtils.isNotEmpty(imageData)) {
            try {
                // convert byte array back to BufferedImage
                InputStream in = new ByteArrayInputStream(imageData);
                BufferedImage image = ImageIO.read(in);

                ImageIcon imgIcon = new ImageIcon(image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH));
                imgLabel.setIcon(imgIcon);
                hyperLinkBrowseImg.setText(CHANGE_IMG_TXT);
                add(imgLabel, "cell 0 0");
                add(btnRemoveImg, "cell 1 0");
            } catch (IOException e) {
                // TODO HPP
                e.printStackTrace();
            }
        }
    }

    private class ChooseImageAction extends AbstractAction {
        private static final long serialVersionUID = 6808805995161914881L;

        @Override
        public void actionPerformed(ActionEvent e) {
            FileFilter imgFileFilter = new FileNameExtensionFilter("Images", IMAGE_EXTENSION);
            JFileChooser imgFileChooser = new JFileChooser();
            imgFileChooser.setFileFilter(imgFileFilter);
            int option = imgFileChooser.showOpenDialog(SwingUtilities.getRootPane(ImageChooser.this));
            if (option == JFileChooser.APPROVE_OPTION) {
                File f = imgFileChooser.getSelectedFile();
                try {
                    // Set data
                    imageData = IOUtils.toByteArray(new FileInputStream(f));

                    // re-init component.
                    Image image = ImageIO.read(f);
                    image = image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
                    ImageIcon imgIcon = new ImageIcon(image);
                    imgLabel.setIcon(imgIcon);
                    hyperLinkBrowseImg.setText(CHANGE_IMG_TXT);
                    ImageChooser.this.add(imgLabel, "cell 0 0");
                    ImageChooser.this.add(btnRemoveImg, "cell 1 0");
                    ImageChooser.this.repaint();
                    ImageChooser.this.revalidate();
                } catch (IOException e1) {
                    // TODO HPP
                    e1.printStackTrace();
                }
            }
        }
    }

    private class RemoveImageAction extends AbstractAction {
        private static final long serialVersionUID = -4681615548948629684L;

        @Override
        public void actionPerformed(ActionEvent e) {
            int option = JOptionPane.showConfirmDialog(null, "Do you want to remove the image?", "Confirm",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Clear data
                imageData = null;

                imgLabel.setIcon(null);
                hyperLinkBrowseImg.setText(ADD_IMAGE_TXT);
                ImageChooser.this.remove(imgLabel);
                ImageChooser.this.remove(btnRemoveImg);
                SwingUtilities.getRoot(ImageChooser.this).repaint();
                SwingUtilities.getRoot(ImageChooser.this).revalidate();
            }
        }
    }

    public byte[] getImageData() {
        return imageData;
    }
}
