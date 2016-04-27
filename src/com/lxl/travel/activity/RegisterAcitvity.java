//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : RegisterAcitvity.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//

package com.lxl.travel.activity;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.lxl.travel.biz.RegisterIntentService;
import com.lxl.travel.entity.UserEntity;
import com.lxl.travel.utils.CameraForImageUtil;
import com.lxl.travel.utils.Const;
import com.lxl.travel.utils.DateUtil;
import com.lxl.travel.utils.ImageCompress;
import com.lxl.travel.utils.ImageCompress.CompressOptions;
import com.lxl.travel.utils.Tools;
import com.lxl.trivel.R;
import com.thinkland.sdk.android.loopj.f;

/** ע��ҳ��activity */
public class RegisterAcitvity extends Activity {

	EditText etUsername, etPassword, etConfirmPassword, etName;
	Button btnSubmit;
	/** ����ע��ҵ�񷵻صĹ㲥������ */
	RegisterBroadcastReceiver myRegister;
	private ImageView iv_register_selectIcon;
	private RadioButton rb_reginster_man;
	private ImageView iv_reginster_back;
	private ImageView iv_register_header;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.reginster_activity);
			// ȥ��actionBar
			getActionBar().hide();
			setupView();
			addListener();
			myRegister = new RegisterBroadcastReceiver();
			IntentFilter filter = new IntentFilter(Const.ACTION_REGISTER);
			registerReceiver(myRegister, filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addListener() {
		// TODO Auto-generated method stub
		MyOnClickListener myOnClickListener = new MyOnClickListener();
		btnSubmit.setOnClickListener(myOnClickListener);
		iv_reginster_back.setOnClickListener(myOnClickListener);
		iv_register_header.setOnClickListener(myOnClickListener);
	}

	private void setupView() {
		// TODO Auto-generated method stub
		etUsername = (EditText) findViewById(R.id.et_register_username);
		etPassword = (EditText) findViewById(R.id.et_register_password);
		etConfirmPassword = (EditText) findViewById(R.id.et_register_confirm_password);
		etName = (EditText) findViewById(R.id.et_register_name);
		btnSubmit = (Button) findViewById(R.id.btn_register_submit);
		rb_reginster_man = (RadioButton) findViewById(R.id.rb_reginster_man);
		iv_reginster_back = (ImageView) findViewById(R.id.iv_reginster_back);
		iv_register_header = (ImageView) findViewById(R.id.iv_register_header);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// ע���㲥������
		unregisterReceiver(myRegister);
	}

	/** ����¼������� */
	class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.btn_register_submit) {// �����ťΪ�ύ��ťʱ
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				String confirmPassword = etConfirmPassword.getText().toString();
				String name = etName.getText().toString();

				// ��֤�û�������������
				StringBuilder builder = new StringBuilder();
				if (TextUtils.isEmpty(username)) {
					builder.append("�û���Ϊ��\n");
				}
				if (TextUtils.isEmpty(password)) {
					builder.append("����Ϊ��\n");
				}
				// ��builderΪ�գ���˵���û�����������Ϣ��ȷ
				if (TextUtils.isEmpty(builder.toString())) {
					// �ж��������������Ƿ�һ��
					if (!etPassword
							.getText()
							.toString()
							.trim()
							.equals(etConfirmPassword.getText().toString()
									.trim())) {
						Toast.makeText(RegisterAcitvity.this, "�������벻һ�£�����������",
								Toast.LENGTH_SHORT).show();
						return;
					}
					// ��֤�ɹ�,�ύ��ť��Ϊ�����ã���ֹ�ظ��ύ
					btnSubmit.setEnabled(false);
					// ��ʾProgressDialog
					Tools.showProgressDialog(RegisterAcitvity.this,
							"�ף�ע���У����Ժ�...");
					// ����ע��ҵ���service
					Intent intent = new Intent(RegisterAcitvity.this,
							RegisterIntentService.class);
					// ����ʵ��
					UserEntity userEntity = new UserEntity();
					userEntity.setUsername(username);
					userEntity.setMd5password(password);
					userEntity.setNickname(name);
					if (rb_reginster_man.isChecked()) {
						userEntity.setGender("m");// ��
					} else {
						userEntity.setGender("w");// Ů
					}
					userEntity.setRegTime(DateUtil.getCurrentDate());// ���ע��ʱ�䣬Ϊ��ǰϵͳʱ��
					userEntity.setLastLoginTime(DateUtil.getCurrentDate());// �������һ�ε�¼ʱ��
					intent.putExtra(Const.KEY_DATA, userEntity);
					RegisterAcitvity.this.startService(intent);
				} else {// builder��Ϊ�գ�˵���û�����������Ϣ����ȷ
					Toast.makeText(RegisterAcitvity.this, builder.toString(),
							Toast.LENGTH_LONG).show();
				}
			}
			if (v.getId() == R.id.iv_reginster_back) {// ���ذ�ť
				Intent i = new Intent(RegisterAcitvity.this,
						LoginActivity.class);
				startActivity(i);
				finish();
			}
			if (v.getId() == R.id.iv_register_header) {// �û�ͷ��ť
				String[] items = new String[] { "���", "���" };
				AlertDialog.Builder builder = new AlertDialog.Builder(
						RegisterAcitvity.this);
				builder.setSingleChoiceItems(items, -1,
						new AlertDialog.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:// �������
									Intent intent = new Intent(
											MediaStore.ACTION_IMAGE_CAPTURE);
									// create a file to save the image
									Uri fileUri = CameraForImageUtil
											.getOutputMediaFileUri("username_header");
									// �˴����intent��ֵ���ù�ϵ�������onActivityResult�л�����Ǹ���֧������ϵ��data�Ƿ�Ϊnull������˴�ָ�����������dataΪnull
									// set the image file name
									intent.putExtra(MediaStore.EXTRA_OUTPUT,
											fileUri);
									startActivityForResult(
											intent,
											Const.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
									dialog.dismiss();
									break;
								case 1:

									// ����ͼ��

									// ȥͼ����ѡ��ͼƬ
									Intent intent1 = new Intent(
											Intent.ACTION_GET_CONTENT);
									intent1.setType("image/*"); // Or 'image/
									// jpeg
									startActivityForResult(intent1,
											Const.RESULT_PICK_PHOTO_NORMAL);
									dialog.dismiss();

									break;
								}
							}
						});
				builder.setCancelable(true);
				builder.show();

			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//�������
		if (requestCode == Const.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE
				&& resultCode == this.RESULT_OK) {
			File file = CameraForImageUtil.getOutputMediaFile("username_header");
			ImageCompress.CompressOptions options = new CompressOptions();
			options.uri = Uri.fromFile(file);
			options.maxHeight = 48;
			options.maxWidth = 48;

			ImageCompress imageCompress = new ImageCompress();
			Bitmap bitmap = imageCompress.compressFromUri(
					RegisterAcitvity.this, options);
			/* bitmap = BitmapUtil.loadBitmap(file.getAbsolutePath(), 48, 48); */
			iv_register_header.setImageBitmap(bitmap);
		}
		//ͼ�ⷵ��
		if (requestCode == Const.RESULT_PICK_PHOTO_NORMAL
				&& resultCode == this.RESULT_OK) {
			Uri selectIcon = data.getData();
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = getContentResolver().query(selectIcon, proj, null,
					null, null);
			cursor.moveToFirst();
			String path = cursor.getString(0);
			// �ü�ͼƬ�������"file:///sdcard/temp.jpg";
			cropImageUri(path);
			// iv_register_selectIcon.setImageBitmap(bitmap);
		}
		// ͼƬ�Ѳü��ú�ѹ��
		if (requestCode == Const.CROP_A_PICTURE && resultCode == this.RESULT_OK) {
			// ѹ��ͼƬ
			// BitmapFactory.decodeFile(Const.IMAGE_FILE_LOCATION);
			File file = CameraForImageUtil.getOutputMediaFile("username_header");
			ImageCompress.CompressOptions options = new CompressOptions();
			options.uri = Uri.fromFile(file);
			options.maxHeight = 78;
			options.maxWidth = 78;

			ImageCompress imageCompress = new ImageCompress();
			Bitmap bitmap = imageCompress.compressFromUri(
					RegisterAcitvity.this, options);
			/* bitmap = BitmapUtil.loadBitmap(file.getAbsolutePath(), 48, 48); */
			iv_register_header.setImageBitmap(bitmap);
		}
	}

	// �ü�ͼƬ
	private void cropImageUri(String path) {
		File file = new File(path);
		Intent intent = new Intent("com.android.camera.action.CROP");
		Uri uri = Uri.fromFile(file);
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 2);
		intent.putExtra("outputX", 400);
		intent.putExtra("outputY", 400);
		// ����Ϊtrueֱ�ӷ���bitmap
		intent.putExtra("return-data", false);
		intent.putExtra("output", CameraForImageUtil.getOutputMediaFileUri("username_header"));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, Const.CROP_A_PICTURE);
	}

	/** ע��ҵ�񷵻صĹ㲥������,���ݷ���״̬���ж�ע���Ƿ�ɹ� */
	class RegisterBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int status = intent.getIntExtra(Const.KEY_DATA, -1);
			// ProgressDialog����
			Tools.closeProgressDialog();
			// �ύ��ť����
			btnSubmit.setEnabled(true);
			switch (status) {
			case -1:// Ĭ��ֵ
				Toast.makeText(RegisterAcitvity.this, "ϵͳ����,������",
						Toast.LENGTH_LONG).show();
				break;
			case Const.STATUS_REGISTER_SUCCESS:// ״̬��Ϊ�ɹ�
				File file = CameraForImageUtil.getOutputMediaFile("username_header");
				file.renameTo(CameraForImageUtil.getOutputMediaFile(etUsername.getText().toString()));
				Toast.makeText(RegisterAcitvity.this, "ע��ɹ�", Toast.LENGTH_LONG)
						.show();
				Intent i = new Intent(RegisterAcitvity.this,
						LoginActivity.class);
				startActivity(i);
				finish();
				break;
			case Const.CONNECTION_OUT_TIME:// ���ӳ�ʱ
				Toast.makeText(RegisterAcitvity.this, "���ӳ�ʱ,������",
						Toast.LENGTH_LONG).show();
				break;
			case Const.STATUS_REGISTER_FAIL:// ״̬Ϊʧ��
				String msg = intent.getStringExtra("msg");
				Toast.makeText(RegisterAcitvity.this, msg, Toast.LENGTH_LONG)
						.show();
				break;
			}
		}
	}

}