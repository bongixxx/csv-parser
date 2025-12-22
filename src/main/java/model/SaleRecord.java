package model;

import java.time.LocalDate;
import java.util.Date;

public class SaleRecord {

    private String region;
    private String country;
    private String itemType;
    private SalesChannel salesChannel;
    private OrderPriority orderPriority;
    private LocalDate orderDate;
    private int unitsSold;
    private double totalProfit;

    public SaleRecord(String region,
                      String country,
                      String itemType,
                      SalesChannel salesChannel,
                      OrderPriority orderPriority,
                      LocalDate orderDate,
                      int unitsSold,
                      double totalProfit) {
        this.region = region;
        this.country = country;
        this.itemType = itemType;
        this.salesChannel = salesChannel;
        this.orderPriority = orderPriority;
        this.orderDate = orderDate;
        this.unitsSold = unitsSold;
        this.totalProfit = totalProfit;
    }

    // Геттеры и сеттеры (по необходимости)

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getItemType() {
        return itemType;
    }

    public SalesChannel getSalesChannel() {
        return salesChannel;
    }

    public OrderPriority getOrderPriority() {
        return orderPriority;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    @Override
    public String toString() {
        return "SaleRecord{" +
                "region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", itemType='" + itemType + '\'' +
                ", salesChannel=" + salesChannel +
                ", orderPriority=" + orderPriority +
                ", orderDate=" + orderDate +
                ", unitsSold=" + unitsSold +
                ", totalProfit=" + totalProfit +
                '}';
    }
}
