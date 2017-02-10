package com.lyan.studyrealm.app;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Author LYJ
 * Created on 2017/2/9.
 * Time 11:35
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);//初始化Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("mineRealm.realm")//设置数据库名称
                .schemaVersion(0)//设置版本号
                .build();
        Realm.setDefaultConfiguration(configuration);//设置配置
    }
}
