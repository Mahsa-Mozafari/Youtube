package com.example.videoplayer.Model.AccountPck;

public enum PremiumPackage {
    BRONZE(30, 5),
    SILVER(60, 9),
    GOLD(180, 14);

    private final int days;
    private final int price;

    PremiumPackage(int days, int price) {
        this.days = days;
        this.price = price;
    }

    public int getDays() {
        return days;
    }

    public int getPrice() {
        return price;
    }
}

