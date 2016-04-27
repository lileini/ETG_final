package com.lxl.travel.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.travel.ETGApplication;
import com.lxl.trivel.R;


public class NotePadAddActivity extends Activity {

	private EditText contentEt;
	private NotePadDBHelper dbHelper;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_pad_add);
		RelativeLayout layout = (RelativeLayout)findViewById(R.id.title_LayoutId);
		 layout.setBackgroundColor(Color.BLACK);
	    TextView titleTv=(TextView) findViewById(R.id.detial_tv_tial);
	    titleTv.setTextColor(Color.WHITE);
	    titleTv.setText("备忘录添加");
	    
		contentEt=(EditText) findViewById(R.id.contentEtId);
	    dbHelper=NotePadDBHelper.newInstance(this);
	  
	    
	}
	public void onTitleClick(View v){
		if(v.getId()==R.id.leftImgId){
			finish();
		}else if(v.getId()==R.id.rightImgId){
			saveNoteContent();	
		}
	}
	private void saveNoteContent(){
		//1.获得输入的数据
		String content=contentEt.getText().toString();
		//2.对数据进行非空验证
		if(TextUtils.isEmpty(content)){
		   contentEt.setError("内容不能为空!");
		   return;
		}
		//3.将数据写入到数据库
		//此对象为值对象，封装数据
		ContentValues cv=new ContentValues();
		cv.put("_content", content);//key为列名
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cv.put("_created", sdf.format(new Date()));
		cv.put("_username", ETGApplication.userEntity.getUsername());
		long id=dbHelper.insert("ETGTravelLog", cv);
		Toast.makeText(this, "保存成功", 1).show();
		//4.关闭当前页面
		finish();
	}

}
