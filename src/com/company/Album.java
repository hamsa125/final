package com.company;import java.util.Calendar;

public class Album {

    protected int albumId;
    protected int consignorId;
    protected String artist;
    protected String title;

    protected int condition;
    protected float price;
    protected java.sql.Date dateConsigned;
    protected int status;
    protected java.sql.Date dateSold;


    // Condition constants
    protected static final int CONDITION_POOR = 1;
    protected static final int CONDITION_FAIR = 2;
    protected static final int CONDITION_GOOD = 3;


    // Status constants
    protected static final int STATUS_STORE = 1;
    protected static final int STATUS_BARGAIN_BIN = 2;
    protected static final int STATUS_SOLD = 3;
    protected static final int STATUS_DONATED = 4;


    Album(int consignorId, String artistName, String albumTitle, int condition, float price) {
        this.consignorId = consignorId;
        this.artist = artistName;
        this.title = albumTitle;

        this.condition = condition;
        this.price = price;
        java.util.Date utilDate = new java.util.Date();
        this.dateConsigned = new java.sql.Date(utilDate.getTime());
        this.status = STATUS_STORE;
    }

    Album(int id, int consignor, String artistName, String albumTitle, int condition, float price, java.util.Date dateConsigned, int status, java.util.Date dateSold) {
        this.albumId = id;
        this.consignorId = consignor;
        this.artist = artistName;
        this.title = albumTitle;
        this.condition = condition;
        this.price = price;
        this.dateConsigned = (java.sql.Date) dateConsigned;
        this.status = status;
        this.dateSold = (java.sql.Date) dateSold;
    }

    public void setSoldDate() {
        java.util.Date utilDate = new java.util.Date();
        this.dateSold = new java.sql.Date(utilDate.getTime());
        System.out.println("Sold date: " + this.dateSold);
    }

    public static java.sql.Date albumsConsignedBeforeThisDateGoToBargainBinToday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        java.util.Date utilDate = calendar.getTime();
        return new java.sql.Date(utilDate.getTime());
    }

    @Override
    public String toString() {
        return this.title + " by " + this.artist;
    }

    public String getAlbumDetailsForBuyers() {
        StringBuilder stringBuilder = new StringBuilder();
        String titleDetail = "Title: " + this.title + "\n";
        stringBuilder.append(titleDetail);

        String artistDetail = "Artist: " + this.artist + "\n";
        stringBuilder.append(artistDetail);



        String conditionDetail = "Condition: " + this.getConditionString() + "\n";
        stringBuilder.append(conditionDetail);

        String priceDetail = "Price: $" + this.price + "\n";
        stringBuilder.append(priceDetail);

        String statusDetail = "Location: " + this.getStatusString() + "\n";
        stringBuilder.append(statusDetail);

        return stringBuilder.toString();
    }


    //Condition
    protected String getConditionString() {
        switch (this.condition) {
            case CONDITION_POOR:return "Poor";
            case CONDITION_FAIR:return "Fair";
            case CONDITION_GOOD:return "Good";
            default:
                return "";
        }
    }

    protected String getStatusString() {
        switch (this.status) {
            case STATUS_STORE:return "Store";
            case STATUS_BARGAIN_BIN:return "Bargain Bin";
            case STATUS_SOLD:return "Sold";
            case STATUS_DONATED:
      return "Donated";
            default:
                return "";
        }
    }

}
