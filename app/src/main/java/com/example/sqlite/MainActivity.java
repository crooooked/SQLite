package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etname;
    private EditText etemail;
    private EditText etphone;
    private Button btnadd;
    private Button btndel;
    private Button btnupd;
    private Button btnread;
    private TextView tvshow;
    MyHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void click(View view){
        String name,email,phone;
        SQLiteDatabase db;
        ContentValues values;
        etname=findViewById(R.id.et_name);
        etemail=findViewById(R.id.et_email);
        etphone=findViewById(R.id.et_phone);
        btnadd=findViewById(R.id.btn_write);
        btndel=findViewById(R.id.btn_remove);
        btnupd=findViewById(R.id.btn_update);
        btnread=findViewById(R.id.btn_read);
        tvshow=findViewById(R.id.show);
        myHelper=new MyHelper(this);
        switch(view.getId()){
            case R.id.btn_write:
                name=etname.getText().toString();
                email=etemail.getText().toString();
                phone=etphone.getText().toString();
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name",name);
                values.put("email",email);
                values.put("phone",phone);
                db.insert("info",null,values);
                Toast.makeText(this,"添加成功",Toast.LENGTH_SHORT).show();
                db.close();
                break;
            case R.id.btn_remove:
                db=myHelper.getWritableDatabase();
                db.delete("info",null,null);
                Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                tvshow.setText("\"                              ----------------\\n                           --no records--\\n                              ----------------\"");
                db.close();
            case R.id.btn_update:
                db=myHelper.getWritableDatabase();
                values=new ContentValues();
                values.put("name",etname.getText().toString());
                values.put("email",etemail.getText().toString());
                db.update("info",values,"phone=?",new String[]{etphone.getText().toString()});
                Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
            case R.id.btn_read:
                db=myHelper.getWritableDatabase();
                Cursor cursor=db.query("info",null,null,null,null,null,null,null);
                if(cursor.getCount()==0){
                    tvshow.setText("                              ----------------\n                           --no records--\n                              ----------------");
                    Toast.makeText(this,"没有数据",Toast.LENGTH_SHORT).show();
                }
                else{
                    cursor.moveToFirst();
                    tvshow.setText("姓名"+cursor.getString(1)+"\n邮箱："+cursor.getString(2)+"\n电话："+cursor.getString(3));
                }
                while(cursor.moveToNext()){
                    tvshow.append("\n"+"姓名"+cursor.getString(1)+"\n邮箱："+cursor.getString(2)+"\n电话："+cursor.getString(3));
                }
                break;
        }

    }
}
