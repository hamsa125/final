package com.company;import java.util.ArrayList;



public class Consignor {
    protected int consignorId;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected float amountOwed;

    public Consignor(int id, String name, String phone, String email, Float amountOwed) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phone;
        this.consignorId = id;
        if (amountOwed == null) {this.amountOwed = 0;}
        else {this.amountOwed = amountOwed;}
    }

    public int getId() {
        return this.consignorId;
    }



    public String getConsignorPaymentDetails() {
        StringBuilder stringBuilder = new StringBuilder();
        String amountOwed = "Owed: " + RecordStoreController_main.currencyFormatter.format(this.amountOwed) + "\n\n";
        stringBuilder.append(amountOwed);
        String email = "Email address: " + this.email + "\n";
        stringBuilder.append(email);
        String phone = "Phone number: " + this.phoneNumber;
        stringBuilder.append(phone);
        return stringBuilder.toString();
    }
    public String getConsignorNotificationDetails() {
        StringBuilder stringBuilder = new StringBuilder();
        String email = "Email address: " + this.email + "\n";
        stringBuilder.append(email);
        String phone = "Phone number: " + this.phoneNumber;
        stringBuilder.append(phone);
        stringBuilder.append("\n\n");
        ArrayList<ConsignorAlbum> unsoldAlbums = RecordStoreController_main.requestConsignorsUnsoldAlbums(this.consignorId);
        for (ConsignorAlbum album : unsoldAlbums) {
            stringBuilder.append(album);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String getDetails() {
        return this.name + ": " + this.email + ", " + this.phoneNumber;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
