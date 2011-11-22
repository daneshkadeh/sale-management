package com.s3s.ssm.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * The list view containing buttons of the statistic list screen:
 * <ul>
 * <li>Print</li>
 * <li>Open Excel</li>
 * <li>Cancel</li>
 * </ul>
 * 
 * @see AbstractCommonListView
 * @see AbstractListView
 * @author Phan Hong Phuc
 * 
 * @param <T>
 */
public abstract class AbstractStatisticListView<T extends AbstractCodeOLObject> extends AbstractListView<T> {
    private static final long serialVersionUID = -1081095962690319904L;

    @Override
    protected JPanel createButtonPanel(JTable table) {
        JPanel pnlButton = new JPanel();
        JButton btnPrint = new JButton(ControlConfigUtils.getString("ListView.Statistic.Button.Print"));
        JButton btnOpenExcel = new JButton(ControlConfigUtils.getString("ListView.Statistic.Button.OpenExcel"));
        JButton btnCancel = new JButton(ControlConfigUtils.getString("ListView.Statistic.Button.Cancel"));

        pnlButton.add(btnPrint);
        pnlButton.add(btnOpenExcel);
        pnlButton.add(btnCancel);

        return pnlButton;
    }

}
