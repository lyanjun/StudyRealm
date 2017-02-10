package com.lyan.studyrealm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lyan.studyrealm.R;
import com.lyan.studyrealm.data.Person;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;

/**
 * 事务操作，无回调
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.insert)
    Button insert;
    @BindView(R.id.update)
    Button update;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.select)
    Button select;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.age)
    EditText age;
    private Realm realm;

    /**
     * 初始化界面
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();
    }

    /**
     * 销毁界面
     */
    @Override
    protected void onDestroy() {
        realm.close();
        super.onDestroy();
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @OnClick({R.id.insert, R.id.update, R.id.delete, R.id.select})
    public void onClick(View view) {
        String inputName = name.getText().toString();//输入的名称
        String inputAge = age.getText().toString();//输入的年龄
        String message = null;//弹出提示信息
        switch (view.getId()) {
            case R.id.insert://增加
                if (!"".equals(inputName) && !"".equals(inputAge)){
                    message = "增加";
                    Person person = new Person();//要插入的数据
                    person.setId(UUID.randomUUID().toString());//设置ID
                    person.setName(inputName);//设置插入数据的名字
                    person.setAge(Integer.parseInt(inputAge.trim()));//设置插入数据的年龄
                    realm.beginTransaction();//开启事务
                    realm.copyToRealmOrUpdate(person);//传入对象
                    realm.commitTransaction();//提交事务
                }else {
                    message = "请录入增加信息";
                }
                break;
            case R.id.update://更新
                if (!"".equals(inputName) && !"".equals(inputAge)){
                    message = "更新";
                    Person result = selectResult(inputName);//查询结果
                    realm.beginTransaction();//开启事务
                    result.setAge(Integer.parseInt(inputAge.trim()));//设置年龄
                    realm.commitTransaction();//提交事务
                }else {
                    message = "请录入更新条件";
                }
                break;
            case R.id.delete://删除
                if (!"".equals(inputName)){
                    message = "删除";
                    Person result = selectResult(inputName);//查询结果
                    realm.beginTransaction();//开启事务
                    result.deleteFromRealm();//删除数据
                    realm.commitTransaction();//提交事务
                }else {
                    message = "请录入删除条件";
                }
                break;
            case R.id.select://查询
                RealmResults<Person> results = realm.where(Person.class).findAll();//查询结果
                List<Person> personList = realm.copyFromRealm(results);
                if (personList.size() == 0){//size为0则表示没有数据
                    message = "没有数据";
                    break;
                }
                message = "查询数据";
                for (Person person : personList){
                    Log.i("ID",person.getId());//打印查询ID
                    Log.i("Name",person.getName());//打印查询名称
                    Log.i("Age",person.getAge()+"");//打印查询年龄
                    Log.e("分割线","==============================");
                }
                break;
        }
        showToast(message);//弹出提示
    }

    /**
     * 条件查询
     * @param name
     */
    private Person selectResult(String name){
        //条件查询
        Person person = realm.where(Person.class).equalTo("name",name).findFirst();
        return person;//查询结果
    }
    /**
     * 吐司
     *
     * @param message
     */
    private void showToast(String message) {
        Toast.makeText(this.getApplicationContext(), message + "！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 隐藏软键盘
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }
}
