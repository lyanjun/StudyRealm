package com.lyan.studyrealm.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Author LYJ
 * Created on 2017/2/10.
 * Time 16:18
 * Food表
 */

public class Food extends RealmObject{
    @PrimaryKey//主键
    private String id;//ID
    @Required//不能为空
    private String type;//类别
    @Required
    private String name;//名称
    @Required
    private double price;//价钱

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
