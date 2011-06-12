/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sale.view.customer;

import com.sale.ui.customer.Output;
import javax.swing.JComponent;
import org.springframework.richclient.application.support.AbstractView;

/**
 *
 * @author Chanhchua
 */

public class OutputCustomerView extends AbstractView {

    @Override
    protected JComponent createControl() {
        Output panel = new Output();
        return panel;
    }

}
