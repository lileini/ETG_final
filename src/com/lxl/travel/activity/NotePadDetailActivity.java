package com.lxl.travel.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.lxl.trivel.R;

public class NotePadDetailActivity extends Activity {

	private NotePadDBHelper dbHelper;
	private EditText contentEt;
	private String content;
	private int _id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        //声明要使用自定义title
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_note_pad_detail);
        //设置自定义title的布局文件
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.custom_d_title_1);
        dbHelper=NotePadDBHelper.newInstance(this);
		//1.获得intent
		Intent intent=getIntent();
		//2.获得intent中的_id值
		 _id=intent.getIntExtra("_id",-1);
		//3.根据id查找相关记录
		String sql="select * from ETGTravelLog where _id=?";
		Cursor c=dbHelper.query(sql,new String[]{String.valueOf(_id)});
		if(c!=null){
		   c.moveToNext();	//移动指针
		   content=c.getString(c.getColumnIndex("_content"));
		}
		//4.将记录中的内容显示在页面上。
		contentEt=(EditText) findViewById(R.id.contentEditId);
		contentEt.setText(content);
		//定位光标的位置
		
		contentEt.setOnClickListener(
		new OnClickListener() {
			@Override
			public void onClick(View v) {
				contentEt.setFocusable(true);
				contentEt.setFocusableInTouchMode(true);
			}
		});
		//==========
		setActionBar();
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
			runOnUiThread( new Runnable() {
				public void run() {
					Toast.makeText(NotePadDetailActivity.this, "点击进入编辑模式", 1).show();
				}
			});
			}
		}, 1000);
	}
	/**设置ActionBar*/
	private void setActionBar(){
		ActionBar bar=getActionBar();
		//bar.setDisplayHomeAsUpEnabled(true);
	    //bar.setDisplayShowHomeEnabled(false);
	}
	public void onTitleClick(View v){
		if(v.getId()==R.id.leftImgId){
			finish();
		}else if(v.getId()==R.id.rightImgId){
			updateNoteById();
		}
	}
	public void updateNoteById(){
		//1.获得输入的内容
		String newContent=contentEt.getText().toString();
		//2.验证是否为空
		//3.更新数据库
		ContentValues cv=
		new ContentValues();
		cv.put("_content", newContent);
		dbHelper.update("ETGTravelLog",
		cv,"_id=?", new String[]{String.valueOf(_id)});
		//4.关闭
		finish();
	}

}
