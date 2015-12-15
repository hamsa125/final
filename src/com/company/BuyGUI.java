package com.company;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BuyGUI extends JPanel {
    private JPanel acquireAlbumPanel;
    private JTextField artistTextField;
    private JTextField titleTextField;
    private JComboBox<Consignor> consignorCombobox;
    private JComboBox<String> sizeCombobox;
    private JComboBox<String> conditionCombobox;
    private JTextField priceTextField;
    private JButton checkInventoryButton;
    private JTextArea inventoryTextArea;
    private JButton buyAlbumButton;
    private JButton declineButton;
    private JTextArea buyAlbumEitherFormConsingnorTextArea;
    private JButton quitProgramButton;

    public BuyGUI() {





        // Set options for condition comboBox
        final String poor = "Poor";
        final String fair = "Fair";
        final String good = "Good";


        conditionCombobox.addItem(poor);
        conditionCombobox.addItem(fair);
        conditionCombobox.addItem(good);

        conditionCombobox.setSelectedItem(null);

        // Populate consignor comboBox
        consignorCombobox.setModel(ConsignorAccountsGUI.consignorComboBoxModel);
        consignorCombobox.setSelectedItem(null);

        checkInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: What if one of these fields is blank?
                String name = artistTextField.getText();
                String title = titleTextField.getText();

                StringBuilder stringBuilder = new StringBuilder();
                int numStoreCopies = _Main_RecordStoreController.requestInventoryCheck(name, title, Album.STATUS_STORE);
                String inventorySearchResultString = "Copies in store: " + numStoreCopies + "\n";
                stringBuilder.append(inventorySearchResultString);

                int numBargainCopies = _Main_RecordStoreController.requestInventoryCheck(name, title, Album.STATUS_BARGAIN_BIN);
                String bargainSearchResultString = "Copies in bargain bin: " + numBargainCopies + "\n";
                stringBuilder.append(bargainSearchResultString);

                int numSoldCopies = _Main_RecordStoreController.requestInventoryCheck(name, title, Album.STATUS_SOLD);
                String soldCopiesSearchResultString = "Copies sold in past 60 days: " + numSoldCopies + "\n";
                stringBuilder.append(soldCopiesSearchResultString);

                int numDonatedCopies = _Main_RecordStoreController.requestInventoryCheck(name, title, Album.STATUS_DONATED);
                String donatedCopiesSearchResultString = "Copies donated in past 60 days: " + numDonatedCopies + "\n";
                stringBuilder.append(donatedCopiesSearchResultString);

                inventoryTextArea.setText(stringBuilder.toString());
            }
        });

        declineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetBuyAlbumFields();
            }
        });

        buyAlbumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // TODO: Input validation. Any prohibited characters?
                String name = artistTextField.getText();
                String title = titleTextField.getText();

                Consignor consignor = (Consignor) ConsignorAccountsGUI.consignorComboBoxModel.getSelectedItem();
                int consignorId = consignor.getId();


                String sizeString = sizeCombobox.getSelectedItem().toString();


                int condition;
                if (sizeString.equalsIgnoreCase(poor)) {
                    condition = Album.CONDITION_POOR;
                } else if (sizeString.equalsIgnoreCase(fair)) {
                    condition = Album.CONDITION_FAIR;
                } else if (sizeString.equalsIgnoreCase(good)) {
                    condition = Album.CONDITION_GOOD;
                } else {
                    condition = Album.CONDITION_GOOD;
                }

                // TODO: Input validation
                // TODO change to spinner instead of text field
                float price = Float.parseFloat(priceTextField.getText());

                // TODO: Null values allowed? Or are all fields required?
                Album newAlbum = new Album(consignorId, name, title, condition, price);
                _Main_RecordStoreController.requestAddAlbum(newAlbum);
                resetBuyAlbumFields();
            }
        });

        quitProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _Main_RecordStoreController.quitProgram();
            }
        });
    }

    public void resetBuyAlbumFields() {
        artistTextField.setText("");
        titleTextField.setText("");
        consignorCombobox.setSelectedItem(null);
        sizeCombobox.setSelectedItem(null);
        conditionCombobox.setSelectedItem(null);
        priceTextField.setText("");
        inventoryTextArea.setText("");
    }

    public JPanel getPanel() {
        return acquireAlbumPanel;
    }

}
