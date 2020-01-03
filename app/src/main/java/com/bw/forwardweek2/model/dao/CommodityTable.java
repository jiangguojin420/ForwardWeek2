package com.bw.forwardweek2.model.dao;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CommodityTable {
    //用来存商品json
    private String CommodityJson;

    @Generated(hash = 401483532)
    public CommodityTable(String CommodityJson) {
        this.CommodityJson = CommodityJson;
    }

    @Generated(hash = 385016888)
    public CommodityTable() {
    }

    public String getCommodityJson() {
        return this.CommodityJson;
    }

    public void setCommodityJson(String CommodityJson) {
        this.CommodityJson = CommodityJson;
    }
}
