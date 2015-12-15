package com.company;import java.util.Date;


public class Payment {
    protected int paymentId;
    protected int consignorId;
    protected java.sql.Date date;
    protected float amount;

    public Payment(int consignorId, float amount) {
        this.consignorId = consignorId;
        this.amount = amount;
        java.util.Date utilDate = new java.util.Date();
        this.date = new java.sql.Date(utilDate.getTime());
    }

    public Payment(int consignorId, java.sql.Date date, float amount) {
        this.consignorId = consignorId;
        this.amount = amount;
        this.date = date;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return this.amount + ", " + this.date;
    }
}
