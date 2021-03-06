//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : LoginActivity.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//

package com.lxl.travel.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lxl.travel.ETGApplication;
import com.lxl.travel.base.BaseActivity;
import com.lxl.travel.biz.LoginBiz;
import com.lxl.travel.entity.UserEntity;
import com.lxl.travel.utils.CameraForImageUtil;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.ImageCompress;
import com.lxl.travel.utils.LogUtil;
import com.lxl.travel.utils.SharedPreferencesUtil;
import com.lxl.travel.utils.Tools;
import com.lxl.travel.utils.ImageCompress.CompressOptions;
import com.lxl.trivel.R;

/**登陆页面*/
public class LoginActivity extends BaseActivity {
	/**用户名、密码*/
	EditText et_login_username, et_login_password;
	/**接收LoginBiz发送的广播接收器*/
	LoginBroadcastReceiver loginReceiver;
	/**新用户注册？*/
	private TextView tv_login_newUser;
	/**忘记密码?*/
	private TextView tv_login_forgetPassword;
	private ImageView iv_login_logo;
	private Button btn_login_submit;
	/**使用SharedPreferencesUtil时需要储存数据的key值*/
	private static String[] keys = new String[] { "username", "md5password",
			"nickname", "gender", "lastLoginTime", "regTime" };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//去掉actionBar
		getActionBar().hide();
		setView();
		//获取SharedPreferences偏好设置
		setData();
		setListener();
		//注册广播接收器
		loginReceiver = new LoginBroadcastReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Const.ACTION_LOGIN);
		registerReceiver(loginReceiver, filter);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		//注销广播接收器
		unregisterReceiver(loginReceiver);
		super.onDestroy();
	}

	private void setView() {
		et_login_username = (EditText) findViewById(R.id.et_login_username);
		et_login_password = (EditText) findViewById(R.id.et_login_password);
		iv_login_logo = (ImageView)findViewById(R.id.iv_login_logo);
		btn_login_submit = (Button)findViewById(R.id.btn_login_submit);
		
		tv_login_newUser = (TextView) findViewById(R.id.tv_login_newUser);
		tv_login_forgetPassword = (TextView) findViewById(R.id.tv_login_forget_password);
	}
	/**获取偏好设置中的数据*/
	private void setData() {
		/*
		 * private String username,md5password,nickname,gender; private Date
		 * lastLoginTime, regTime;
		 */
		//userInfo为储存用户登录信息  的文件名
		Map<String, String> data = SharedPreferencesUtil.getPerferences(this,
				"userInfo", keys);
		//通过对应的key获取对应的值，keys[0]为用户名信息key值，data.get(keys[1])为密码信息key值
		et_login_username.setText(data.get(keys[0]));
		et_login_password.setText(data.get(keys[1]));
		File file = CameraForImageUtil.getOutputMediaFile(data.get(keys[0]));
		if (file.exists()){
			ImageCompress.CompressOptions options = new CompressOptions();
			options.uri = Uri.fromFile(file);
			options.maxHeight = 78;
			options.maxWidth = 78;

			ImageCompress imageCompress = new ImageCompress();
			Bitmap bitmap = imageCompress.compressFromUri(
					LoginActivity.this, options);
			/* bitmap = BitmapUtil.loadBitmap(file.getAbsolutePath(), 48, 48); */
			iv_login_logo.setImageBitmap(bitmap);
		}
	}

	private void setListener() {
		//TextView 只能设置Touch事件
		tv_login_newUser.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//当事件为抬起时，跳转到注册页面
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Intent intent = new Intent(LoginActivity.this,
							RegisterAcitvity.class);
					startActivity(intent);
				}
				return true;
			}
		});
		tv_login_forgetPassword.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Toast.makeText(LoginActivity.this, "忘记密码，请重新注册",
							Toast.LENGTH_SHORT).show();
				}
				return true;
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login_submit://登录按钮
			// 点击登陆
			if (checkLoginInfo()) {//检查登录需要的信息是否填写正确
				//弹出ProgressDialog
				Tools.showProgressDialog(this, "正在努力登陆中...");
				btn_login_submit.setEnabled(false);
				//启动工作线程，执行登录业务
				new Thread() {
					public void run() {
						//新建登录业务
						new LoginBiz(LoginActivity.this).login(
								et_login_username.getText().toString().trim(),//�û���
								et_login_password.getText().toString().trim());//����
					};
				}.start();
			}
			break;
		}
	}
	/**检查登录信息，若登录信息全都正确则返回true，否则返回false*/
	private boolean checkLoginInfo() {
		//判断用户名是否为空
		if ("".equals(et_login_username.getText().toString().trim())) {
			Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		//判断密码是否为空
		if ("".equals(et_login_password.getText().toString().trim())) {
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	/**接收LoginBiz发送的广播接收器类，当接收到登录业务返回的广播是，判断登录是否成功*/
	class LoginBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			//��¼���״̬��
			int status = intent.getIntExtra(Const.KEY_DATA, -1);
			//�ر�ProgressDialog
			Tools.closeProgressDialog();
			btn_login_submit.setEnabled(true);
			switch (status) {
			case Const.CONNECTION_OUT_TIME://请求超时
				Toast.makeText(LoginActivity.this, "请求超时", Toast.LENGTH_SHORT)
				.show();
				break;
			case Const.STATUS_LOGIN_SUCCESS://状态码为登录成功
				Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT)
						.show();
				//取得登录biz传回的user信息
				UserEntity entity = (UserEntity) intent
						.getSerializableExtra("entity");
				// 若信息为空，则不执行，直接返回
				if (entity == null) {
					return;
				}
				//把用户信息保存到Application
				ETGApplication.userEntity = entity;
				LogUtil.i("userEntity", ":" + entity);
				//把用户信息保存到偏好设置
				List<String> data = new ArrayList<String>();
				// 保存到文件
				// list中一定要按此顺序储存，与keys保持一致
				// "username","md5password","nickname","gender","lastLoginTime",
				// "regTime"
				data.add(entity.getUsername());
				data.add(entity.getMd5password());
				data.add(entity.getNickname());
				data.add(entity.getGender());
				data.add("" + entity.getLastLoginTime());
				data.add("" + entity.getRegTime());
				SharedPreferencesUtil.saveShared(LoginActivity.this,
						"userInfo", keys, data);
				//������ҳ
				Intent i = new Intent(LoginActivity.this,
						HomePageActivity.class);
				startActivity(i);
				finish();
				break;
			case Const.STATUS_LOGIN_FAIL://״̬��Ϊ��¼ʧ��
				String msg = intent.getStringExtra("msg");//�õ���¼ʧ��ԭ�򣬲�Toast
				Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG)
						.show();
				break;
				case -1://默认为-1；
					Toast.makeText(LoginActivity.this, "系统错误,请重试",
							Toast.LENGTH_LONG).show();
				break;
			}
		}

	}
}
