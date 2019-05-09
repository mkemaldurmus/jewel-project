package com.exastax.internship.jewel.core;

import java.io.Serializable;

public class JewelItem implements Serializable {
    private final int id;
    private final int price;
    private final double weight;
    private final double carat;
    private final String diamondType;
    private final double diamondWeight;
    private final String diamondColor;
    private final String diamondClarity;
    private final String diamondShape;

    public JewelItem(int id, int price, double weight, double carat, String diamondType, double diamondWeight, String diamondColor, String diamondClarity, String diamondShape) {
        this.id = id;
        this.price = price;
        this.weight = weight;
        this.carat = carat;
        this.diamondType = diamondType;
        this.diamondWeight = diamondWeight;
        this.diamondColor = diamondColor;
        this.diamondClarity = diamondClarity;
        this.diamondShape = diamondShape;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public double getWeight() {
        return weight;
    }

    public double getCarat() {
        return carat;
    }

    public String getDiamondType() {
        return diamondType;
    }

    public double getDiamondWeight() {
        return diamondWeight;
    }

    public String getDiamondColor() {
        return diamondColor;
    }

    public String getDiamondClarity() {
        return diamondClarity;
    }

    public String getDiamondShape() {
        return diamondShape;
    }

    @Override
    public String toString() {
        return "JewelItem{" +
                "id=" + id +
                ", price=" + price +
                ", weight=" + weight +
                ", carat=" + carat +
                ", diamondType='" + diamondType + '\'' +
                ", diamondWeight=" + diamondWeight +
                ", diamondColor='" + diamondColor + '\'' +
                ", diamondClarity='" + diamondClarity + '\'' +
                ", diamondShape='" + diamondShape + '\'' +
                '}';
    }
}
