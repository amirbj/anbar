package com.paya.administrator.anbar;

/**
 * Created by Administrator on 16/08/2016.
 */
public class ProductList {

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    private String name,unit, productid, unitid;
    private int number, id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


