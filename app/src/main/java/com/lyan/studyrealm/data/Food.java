package com.lyan.studyrealm.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Author LYJ
 * Created on 2017/2/10.
 * Time 16:18
 * Food表
 */

public class Food extends RealmObject{

    private String type;//类别
    @PrimaryKey//不能为空
    private String name;//名称
    private double price;//价钱
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
