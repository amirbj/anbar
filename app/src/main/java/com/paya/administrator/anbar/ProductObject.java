package com.paya.administrator.anbar;

/**
 * Created by Administrator on 16/08/2016.
 */

public class ProductObject {

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    private String name,unit,unitId,  productId;
    private int number;
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    public int getNumber() {
        return number;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int number) {
        this.number = number;
    }



}
