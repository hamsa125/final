package com.company;

import com.company.Consignor;
import com.company._Main_RecordStoreController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ManageConsignorsGUI extends JPanel {

    private JPanel consignorsPanel;
    private JButton payInFullButton;
    private JList<Consignor> consignorJList;
    private JTextArea consignorDetailsTextArea;
    private JTextArea findConsignorsThatWeTextArea;
    private JButton findConsignorsToPay;
    private JButton findConsignorsToNotify;
    private JTextArea findAllConsignorsWhoseTextArea;
    private JButton quitProgramButton;
    private JSpinner spinner2;
    private JSpinner spinner3;
    private JComboBox searchPurposeComboBox;
    private boolean detailDisplayModeNotify;

    private static DefaultListModel<Consignor> consignorListModel = new DefaultListModel<Consignor>();

    // Constructor
    public ManageConsignorsGUI() {

        consignorJList.setModel(consignorListModel);
        consignorJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        findConsignorsToNotify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detailDisplayModeNotify = true;
                payInFullButton.setEnabled(false);
                displayConsignorsToNotify();
            }
        });

        findConsignorsToPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detailDisplayModeNotify = false;
                displayConsignorsToPay();
            }
        });

        consignorJList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!consignorJList.isSelectionEmpty()) {
                    Consignor selectedConsignor = consignorJList.getSelectedValue();
                    if (detailDisplayModeNotify) {
                        consignorDetailsTextArea.setText(selectedConsignor.getConsignorNotificationDetails());
                    } else {
                        consignorDetailsTextArea.setText(selectedConsignor.getConsignorPaymentDetails());
                        payInFullButton.setEnabled(true);
                    }
                }
            }
        });

        payInFullButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Consignor selectedConsignor = consignorJList.getSelectedValue();
                _Main_RecordStoreController.requestPayConsignorInFull(selectedConsignor);
                consignorDetailsTextArea.setText("");
                consignorListModel.removeElement(selectedConsignor);
            }
        });

        quitProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _Main_RecordStoreController.quitProgram();
            }
        });
    }

    private void displayConsignorsToPay() {
        reset();
        ArrayList<Consignor> consignorsToPay = _Main_RecordStoreController.requestConsignorsToPay();
        for (Consignor consignor : consignorsToPay) {
            consignorListModel.addElement(consignor);
        }
    }

    private void displayConsignorsToNotify() {
        reset();
        ArrayList<Consignor> consignorsToNotify = _Main_RecordStoreController.requestConsignorsToNotify();
        for (Consignor consignor : consignorsToNotify) {
            consignorListModel.addElement(consignor);
        }
    }

    public void reset() {
        consignorDetailsTextArea.setText("");
        consignorListModel.removeAllElements();
    }

    public JPanel getPanel() {
        return consignorsPanel;
    }
}
