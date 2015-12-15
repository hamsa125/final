package com.company;

/**
 * Created by Husna on 12/10/2015.
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;



public class TabsWindowGUI extends JFrame {
    private JPanel rootPanel;
    private JTabbedPane tabbedPane;

    public TabsWindowGUI() {
        setContentPane(rootPanel);

       //tabbedPane = new JTabbedPane();


     //  rootPanel.add(tabbedPane);
        final SellsRegisterGUI sellGUI = new SellsRegisterGUI();
        final BuyGUI acquireGUI = new BuyGUI();
        final ConsignorAccountsGUI consignorAccountsGUI = new ConsignorAccountsGUI();
        final ManageConsignorsGUI manageConsignorsGUI = new ManageConsignorsGUI();
        final InventoryGUI inventoryGUI = new InventoryGUI();

            tabbedPane.add("Sells Register", sellGUI.getPanel());
            tabbedPane.add("Acquire Album", acquireGUI.getPanel());
            tabbedPane.add("Consignor Accounts", consignorAccountsGUI.getPanel());
            tabbedPane.add("Manage Consignors", manageConsignorsGUI.getPanel());
            tabbedPane.add("Manage Inventory", inventoryGUI.getPanel());

        //testSystem.out.print("sdsdsd" + tabbedPane);
        // Adapted from: http://stackoverflow.com/questions/6799731/jtabbedpane-changelistener
        tabbedPane.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                switch (tabbedPane.getSelectedIndex()) {
                    case 0:
                        sellGUI.resetSearchFields();
                        break;
                    case 1:
                        acquireGUI.resetBuyAlbumFields();
                        break;
                    case 2:
                        consignorAccountsGUI.reset();
                        break;
                    case 3:
                        manageConsignorsGUI.reset();
                        break;
                    case 4:
                        inventoryGUI.reset();
                        break;
                }
            }
        });

        setSize(new Dimension(750, 700));
        setVisible(true);
        pack();
    }
}
