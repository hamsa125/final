package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class ConsignorAccountsGUI extends JPanel {
    private JPanel accountPanel;
    private JList<ConsignorAlbum> consignorsAlbumsJList;
    private JLabel amountOwedLabel;
    private JTextField TextField;

    private JTextField consignorEmailTextField;
    private JTextField consignorNameTextField;
    private JTextField consignorPhoneTextField;
    private JTextField Email1122;
    private JButton addNewConsignorButton;
    private JButton editSelectedConsignorButton;
    private JComboBox<Consignor> consignorNamesComboBox;
    private JButton deleteSelectedConsignorButton;
    private JTextArea addEditAndDeleteTextArea; // This is NOT unused
    private JList<Payment> paymentsJList;
    private JButton returnSelectedAlbumToButton;
    private JButton payFullAmountOwedButton;
    private JButton quitProgramButton;

    protected static ArrayList<Consignor> consignorArrayList = new ArrayList<Consignor>();
    protected static DefaultComboBoxModel<Consignor> consignorComboBoxModel = new DefaultComboBoxModel<Consignor>();
    private static DefaultListModel<ConsignorAlbum> consignorAlbumListModel = new DefaultListModel<ConsignorAlbum>();
    private static DefaultListModel<Payment> consignorPaymentListModel = new DefaultListModel<Payment>();

    protected SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/YYYY");

    // Constructor
    public ConsignorAccountsGUI() {

        // Create list models for consignor album list
        consignorsAlbumsJList.setModel(consignorAlbumListModel);
        consignorsAlbumsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create list models for consignor payment list
        paymentsJList.setModel(consignorPaymentListModel);
        paymentsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Set model for consignor combobox
        consignorNamesComboBox.setModel(consignorComboBoxModel);
        refreshConsignorList();

        // Set default selection
        consignorNamesComboBox.setSelectedItem(consignorComboBoxModel.getElementAt(0));

        consignorNamesComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Consignor consignorSelected = (Consignor) consignorNamesComboBox.getSelectedItem();
                if (e.getStateChange() == ItemEvent.SELECTED && !consignorSelected.name.equalsIgnoreCase("none selected")) {
                    Consignor selectedConsignor = (Consignor) consignorNamesComboBox.getSelectedItem();
                    consignorNameTextField.setText(selectedConsignor.name);
                    consignorEmailTextField.setText(selectedConsignor.email);
                    consignorPhoneTextField.setText(selectedConsignor.phoneNumber);
                    getConsignorAlbumDetails(selectedConsignor.consignorId);
                    getConsignorPaymentDetails(selectedConsignor.consignorId);
                    amountOwedLabel.setText("Amount owed: " + _Main_RecordStoreController.currencyFormatter.format(selectedConsignor.amountOwed));

                } else {
                    // No consignor selected
                    clearFields();
                }
            }
        });

        addNewConsignorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameInput = consignorNameTextField.getText();
                boolean duplicate = false;

                for (Consignor consignor : consignorArrayList) {
                    if (consignor.name.equalsIgnoreCase(nameInput)) {
                        // If name entered matches an existing entry in consignor database
                        // Display warning dialog box

                        String[] dialogOptions = {"Continue", "Cancel"};
                        int n = JOptionPane.showOptionDialog(null,
                                "That name is already in our consignor database, \nwith the following contact info: \n" + consignor.getDetails() +
                                        "\nWould you like to continue to add a new \nconsignor account, or cancel and edit the existing account?",
                                "Posible Duplicate",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE,
                                null,
                                dialogOptions,
                                dialogOptions[1]);

                        if (n == 1) {
                            duplicate = true;
                        }
                    }
                }

                if (!duplicate) {
                    String email = consignorEmailTextField.getText();
                    String phone = consignorPhoneTextField.getText();
                    _Main_RecordStoreController.requestAddConsignor(nameInput, email, phone);
                    refreshConsignorList();
                }
            }
        });


        deleteSelectedConsignorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Consignor consignorToRemove = (Consignor) consignorNamesComboBox.getSelectedItem();
                _Main_RecordStoreController.requestRemoveConsignor(consignorToRemove);
                refreshConsignorList();
            }
        });

        returnSelectedAlbumToButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!consignorsAlbumsJList.isSelectionEmpty()) {

                    Album selectedAlbum = consignorsAlbumsJList.getSelectedValue();
                    if (selectedAlbum.status == Album.STATUS_STORE || selectedAlbum.status == Album.STATUS_BARGAIN_BIN) {
                        _Main_RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.STATUS_BARGAIN_BIN);
                    }
                    consignorAlbumListModel.removeElement(selectedAlbum);
                }
            }
        });

        // TODO implement edit consignor info button functionality

        payFullAmountOwedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Consignor consignorSelected = (Consignor) consignorNamesComboBox.getSelectedItem();
                _Main_RecordStoreController.requestPayConsignorInFull(consignorSelected);
            }
        });

        quitProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _Main_RecordStoreController.quitProgram();
            }
        });
    }

    private void refreshConsignorList() {
        // Populate comboBox model
        consignorArrayList = _Main_RecordStoreController.requestAllConsignors();
        consignorComboBoxModel.removeAllElements();
        Consignor nullValueObject = new Consignor(-1, "none selected", "", "", (float)0.0);
        consignorComboBoxModel.addElement(nullValueObject);

        for (Consignor consignor : consignorArrayList) {
            consignorComboBoxModel.addElement(consignor);
        }
    }

    private void getConsignorAlbumDetails(int consignorId) {

        // Populate JList model
        consignorAlbumListModel.removeAllElements();
        ArrayList<ConsignorAlbum> consignorsAlbums = _Main_RecordStoreController.requestAllConsignorsAlbums(consignorId);

        for (ConsignorAlbum album : consignorsAlbums) {
            consignorAlbumListModel.addElement(album);
        }
    }

    private void getConsignorPaymentDetails(int consignorId) {

        // Populate JList model
        consignorPaymentListModel.removeAllElements();
        ArrayList<Payment> consignorsPayments = _Main_RecordStoreController.requestAllConsignorsPayments(consignorId);

        for (Payment payment : consignorsPayments) {
            consignorPaymentListModel.addElement(payment);
        }
    }

    private void clearFields() {
        consignorNameTextField.setText("");
        consignorEmailTextField.setText("");
        consignorPhoneTextField.setText("");
        consignorAlbumListModel.removeAllElements();
        consignorPaymentListModel.removeAllElements();
    }

    public void reset() {
        refreshConsignorList();
        clearFields();
    }

    public JPanel getPanel() {
        return accountPanel;
    }
}
