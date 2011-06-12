/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sale.ui.customer;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Chanhchua
 */
public class AbstractInputUI {
    JTextField idField;
    JPanel inputArea;
    JPanel buttonArea;
    void setLayout() {
        inputArea.add(idField);
    }
}
