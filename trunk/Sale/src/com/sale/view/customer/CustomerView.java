/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sale.view.customer;

import com.sale.ui.customer.Input;
import javax.swing.JComponent;
import javax.swing.JPanel;
import org.springframework.richclient.application.support.AbstractView;

/**
 *
 * @author Chanhchua
 */

public class CustomerView extends AbstractView {

    @Override
    protected JComponent createControl() {
        Input panel = new Input();
        return panel;
    }

}
