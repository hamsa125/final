package com.company;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Husna on 12/10/2015.
 */

public class InventoryGUI extends JPanel {
    private JList<Album> albumAgingJList;
    private JButton findAlbumsOver37Button;
    private JButton markAllButton;
    private JButton markSelectedButton;
    private JButton findAlbumsOver13Button;
    private JTextArea albumAgingTextArea;
    private JPanel inventoryPanel;
    private JScrollPane inventoryScrollpane;
    private JTextArea searchTheAlbumsThatTextArea;
    private JTextArea searchTheBargainBinTextArea;
    private JButton quitProgramButton;
    private JSpinner spinner1;
    private JSpinner spinner2;

    DefaultListModel<Album> albumAgingListModel = new DefaultListModel<Album>();
    ArrayList<Album> albumAgingArrayList = new ArrayList<Album>();

    // Indicates which album aging period is
    // being requested in Manage Inventory panel
    public static final int THIRTY_SEVEN_DAYS = 1;
    public static final int THIRTEEN_MONTHS = 2;

    public InventoryGUI() {

        // Create list models for inventory search
        albumAgingJList.setModel(albumAgingListModel);
        albumAgingJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        findAlbumsOver37Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryGUI.this.albumAgingListModel.removeAllElements();
                albumAgingArrayList.clear();
                albumAgingArrayList = _Main_RecordStoreController.requestAlbumsOfAge(THIRTY_SEVEN_DAYS);

                if (albumAgingArrayList.isEmpty()) {
                    albumAgingTextArea.setText("No albums found.");

                } else {
                    albumAgingTextArea.setText("Which albums would you like to move to the bargain bin?");
                    for (Album album : albumAgingArrayList) {
                        InventoryGUI.this.albumAgingListModel.addElement(album);
                    }
                }
            }
        });

        findAlbumsOver13Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InventoryGUI.this.albumAgingListModel.removeAllElements();
                albumAgingArrayList.clear();
                albumAgingArrayList = _Main_RecordStoreController.requestAlbumsOfAge(THIRTEEN_MONTHS);

                if (albumAgingArrayList.isEmpty()) {
                    albumAgingTextArea.setText("No albums found.");

                } else {
                    albumAgingTextArea.setText("Which albums would you like to donate?");
                    for (Album album : albumAgingArrayList) {
                        InventoryGUI.this.albumAgingListModel.addElement(album);
                    }
                }
            }
        });

        markSelectedButton.addActionListener(new ActionListener() {
            @Override
          public void actionPerformed(ActionEvent e) {

                Album selectedAlbum = (Album) albumAgingJList.getSelectedValue();
                albumAgingListModel.removeElement(selectedAlbum);

                if (selectedAlbum.status == Album.STATUS_STORE) {
                    _Main_RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.STATUS_BARGAIN_BIN);

                } else if (selectedAlbum.status == Album.STATUS_BARGAIN_BIN) {
                    _Main_RecordStoreController.requestUpdateAlbumStatus(selectedAlbum, Album.STATUS_DONATED);
                }
           }
        });



        quitProgramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _Main_RecordStoreController.quitProgram();
            }
        });
    }

    public void reset() {
        albumAgingListModel.removeAllElements();
        albumAgingTextArea.setText("");
    }

    public JPanel getPanel() {
        return inventoryPanel;
    }
}
