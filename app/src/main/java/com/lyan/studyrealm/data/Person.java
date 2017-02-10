package com.lyan.studyrealm.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Author LYJ
 * Created on 2017/2/9.
 * Time 11:49
 * 这就相当于一张表,Person表
 */

public class Person extends RealmObject{
    @PrimaryKey
    private String id;//主键ID
    private String name;//姓名
    private int age;//年龄

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
