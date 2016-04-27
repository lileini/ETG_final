//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : PersonalFragment.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//



package com.lxl.travel.fragment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.activity.LoginActivity;
import com.lxl.travel.activity.NotePadDBHelper;
import com.lxl.travel.adapter.GeneralInfoAdapter;
import com.lxl.travel.utils.CameraForImageUtil;
import com.lxl.travel.utils.ImageCompress;
import com.lxl.travel.utils.ImageCompress.CompressOptions;
import com.lxl.trivel.R;
import com.tarena.utils.ImageCircleView;

/**
 * �����е����ݿ���ز���Ӧ���첽����
 * */
@SuppressLint("SimpleDateFormat")
public class PersonalFragment extends Fragment{

	private TextView login_Tv;
	private View view;
	private TextView baseInfo_Tv;
	private TextView addGeneralInfo_Tv;
	private SwipeMenuListView general_lv;
	private EditText address_Et;
	private EditText name_Et;
	private Button submit_btn;
	private Button cancel_btn;
	private TextView registTime_Tv;
	private TextView lastLoginTime_Tv;
	private TextView gender_Tv;
	private TextView username_Tv;
	private TextView nickName_Tv;
	private TextView personal_generalinfo_Tv;
	private NotePadDBHelper dbHelper;
	private DisplayMetrics displayMetrics;
	private ImageCircleView userImg_Iv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dbHelper = NotePadDBHelper.newInstance(getActivity());
		initDisplayMetrics();
	}


	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				getResources().getDisplayMetrics());
	}

	private void initDisplayMetrics(){		
		//��ȡ��Ļ������
		WindowManager manager = getActivity().getWindowManager();
		//���õ����Ŀ���
		displayMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(displayMetrics);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getActivity().getActionBar().hide();
		view = inflater.inflate(R.layout.fragment_personal, null);
		setViews();
		//setListView();
		setListener();
		return view;
	}

	/**��ʼ���ؼ�*/
	private void setViews() {
		userImg_Iv = (ImageCircleView)view.findViewById(R.id.userImg_Iv_c);
		login_Tv = (TextView) view.findViewById(R.id.login_Tv);
		baseInfo_Tv = (TextView) view.findViewById(R.id.personal_info_Tv);
		addGeneralInfo_Tv = (TextView) view.findViewById(R.id.personal_addgeneralinfo_Tv);
		general_lv = (SwipeMenuListView) view.findViewById(R.id.generalinfo_Lv);
		personal_generalinfo_Tv = (TextView) view.findViewById(R.id.personal_generalinfo_Tv);
	}

	/**���ò໬listview����ʾ*/
	/*private void setListView() {
		// step 1. create a MenuCreator
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				// create "open" item
				SwipeMenuItem openItem = new SwipeMenuItem(
						PersonalFragment.this.getActivity());
				// set item background
				openItem.setBackground(R.drawable.travel_background_color_selector);
				// set item width
				openItem.setWidth(dp2px(90));
				// set item title
				openItem.setTitle("�༭");
				// set item title fontsize
				openItem.setTitleSize(18);
				// set item title font color
				openItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(openItem);

				// create "delete" item
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						PersonalFragment.this.getActivity());
				// set item background
				deleteItem.setBackground(R.drawable.trip_background_color_selector);
				// set item width
				deleteItem.setWidth(dp2px(90));
				// set a icon
				//deleteItem.setIcon(R.drawable.ic_delete);
				// set item title
				deleteItem.setTitle("ɾ��");
				// set item title fontsize
				deleteItem.setTitleSize(18);
				// set item title font color
				deleteItem.setTitleColor(Color.WHITE);
				// add to menu
				menu.addMenuItem(deleteItem);
			}
		};
		// set creator
		general_lv.setMenuCreator(creator);
		serSlideItemListener();
	}*/


	/**����item�໬�������*/
	/*private void serSlideItemListener() {
		general_lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch(index){
				case 0://�༭����
					int selectedId = Integer.valueOf(generalInfos.get(position).get(idKey));
					setPopupWindow(UPDATE_TO_DB,selectedId);
					name_Et.setText(generalInfos.get(position).get(nameKey));
					address_Et.setText(generalInfos.get(position).get(addressKey));
					break;
				case 1://ɾ������
					String whereClause = "_id="+generalInfos.get(position).get(idKey);
					deleteinfo(whereClause);
					generalInfos.remove(position);
					adapter.notifyDataSetChanged();
					break;
				}
			}
		});
	}*/

	@Override
	public void onResume() {
		if(ETGApplication.userEntity != null){
			login_Tv.setText(ETGApplication.userEntity.getNickname());
			//��ȡ���ش洢�ĳ�����Ϣ
			selectDataFromDB();
			//��ͷ��
			File file = CameraForImageUtil
					.getOutputMediaFile(ETGApplication.userEntity.getUsername());
			if (file.exists()) {
				ImageCompress.CompressOptions options = new CompressOptions();
				options.uri = Uri.fromFile(file);
				options.maxHeight = 78;
				options.maxWidth = 78;

				ImageCompress imageCompress = new ImageCompress();
				Bitmap bitmap = imageCompress.compressFromUri(
						getActivity(), options);
				/*
				 * bitmap = BitmapUtil.loadBitmap(file.getAbsolutePath(), 48,
				 * 48);
				 */
				userImg_Iv.setImageBitmap(bitmap);
			}else {
				userImg_Iv.setImageResource(R.drawable.icon_account);
			}

		}
		super.onResume();
	}

	/**�����ݿ��ѯ����*/
	private void selectDataFromDB() {
		if(generalInfos == null){
			generalInfos = new ArrayList<Map<String,String>>();
		}else {
			//����ϴε�����
			generalInfos.removeAll(generalInfos);
		}
		//��ѯ���
		String sql = "select * from ETGgeneralInfo where _username="+ETGApplication.userEntity.getUsername();		
		Cursor c = dbHelper.query(sql, null);
		//����ѯ�������ݷ�װ����,��listview����ʾ
		while(c.moveToNext()){
			Map<String, String> map = new HashMap<String, String>();
			map.put(nameKey, c.getString(c.getColumnIndex("_name")));
			map.put(addressKey, c.getString(c.getColumnIndex("_address")));
			map.put(idKey, String.valueOf(c.getInt(c.getColumnIndex("_id"))));
			generalInfos.add(0,map);
		}
		c.close();
		showListView();
	}

	/**���ü�����*/
	private void setListener() {

		//���õ����¼����
		login_Tv.setOnClickListener(new android.view.View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ETGApplication.userEntity != null){
					Toast.makeText(getActivity(), "���Ѿ���¼��", Toast.LENGTH_SHORT).show();
					return;
				}else{					
					Intent intent = new Intent(getActivity(),LoginActivity.class);
					getActivity().startActivity(intent);
				}
			}
		});

		//���������Ϣ����
		baseInfo_Tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ETGApplication.userEntity == null){
					Toast.makeText(PersonalFragment.this.getActivity(), "��¼֮��鿴������Ϣ", Toast.LENGTH_SHORT).show();
				}else {
					displayBaseInfo();	
				}	
			}
		});
		//ͷ��
		userImg_Iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ETGApplication.userEntity != null){
					Toast.makeText(getActivity(), "���һ����������������˺�", Toast.LENGTH_SHORT).show();
					return;
				}else{					
					Intent intent = new Intent(getActivity(),LoginActivity.class);
					getActivity().startActivity(intent);
				}
			}
		} );

		//������ӳ�����Ϣ
		addGeneralInfo_Tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ETGApplication.userEntity == null){
					Toast.makeText(PersonalFragment.this.getActivity(), "��¼֮�����Ӹ�����Ϣ", Toast.LENGTH_SHORT).show();
				}else {
					setPopupWindow(INSERT_TO_DB, -1);	
				}
			}
		});

		//listview��item�������
		setItemOnClickListener();
	}

	String[] items = new String[] { "�༭", "ɾ��" };
	/**����listview��item�������*/
	private void setItemOnClickListener() {
		general_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					final int position, long id) {

				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setSingleChoiceItems(items, -1, new android.content.DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						selectedItem = which;
						selectedPos = position;
					}
				});
				builder.setPositiveButton("ȷ��", positiveButtonListener);
				builder.setNegativeButton("ȡ��", negativeButtonListener);
				builder.setCancelable(true);
				builder.show();
			}
		});

	}

	/**ѡ����Ǳ༭����ɾ��*/
	private int selectedItem;
	/**�����item*/
	private int selectedPos;
	/**ȷ�� ����*/
	private android.content.DialogInterface.OnClickListener positiveButtonListener = new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (selectedItem) {
			case 0://����༭
				int selectedId = Integer.valueOf(generalInfos.get(selectedPos).get(idKey));
				setPopupWindow(UPDATE_TO_DB,selectedId);
				name_Et.setText(generalInfos.get(selectedPos).get(nameKey));
				address_Et.setText(generalInfos.get(selectedPos).get(addressKey));
				break;
			case 1://���ɾ��
				String whereClause = "_id="+generalInfos.get(selectedPos).get(idKey);
				deleteinfo(whereClause);
				generalInfos.remove(selectedPos);
				adapter.notifyDataSetChanged();
				break;

			}
		}
	};

	/**ȡ������*/
	private android.content.DialogInterface.OnClickListener negativeButtonListener = new android.content.DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			Toast.makeText(PersonalFragment.this.getActivity(), "ȡ��"+which, 1).show();
		}
	};


	/**������ʾ������Ϣ*/
	@SuppressWarnings("deprecation")
	private void displayBaseInfo() {
		//����,������Ϣ
		View baseInfoContentView = View.inflate(PersonalFragment.this.getActivity(), R.layout.item_personal_baseinfo_popupwindow, null);
		//���õ������ڵĴ�С��λ��
		PopupWindow baseInfoWindow = new PopupWindow(baseInfoContentView, displayMetrics.widthPixels/4*3, displayMetrics.heightPixels/5);
		// ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
		baseInfoWindow.setBackgroundDrawable(new BitmapDrawable());
		// ʹ��ۼ� ��Ҫ������˵���ؼ����¼��ͱ���Ҫ���ô˷���
		baseInfoWindow.setFocusable(true);
		baseInfoWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		initBaseInfoViews(baseInfoContentView);
		setBaseInfo(baseInfoContentView);
	}

	/**��ʼ��������Ϣ����ʾ�ؼ�
	 * @param baseInfoContentView */
	private void initBaseInfoViews(View baseInfoContentView) {
		username_Tv = (TextView) baseInfoContentView.findViewById(R.id.baseinfo_username_Tv);
		nickName_Tv = (TextView) baseInfoContentView.findViewById(R.id.baseinfo_nickname_Tv);
		gender_Tv = (TextView) baseInfoContentView.findViewById(R.id.baseinfo_gender_Tv);
		lastLoginTime_Tv = (TextView) baseInfoContentView.findViewById(R.id.baseinfo_lastLoginTime_Tv);
		registTime_Tv = (TextView) baseInfoContentView.findViewById(R.id.baseinfo_registTime_Tv);
	}

	/**���û�����Ϣ����ʾ
	 * @param baseInfoContentView */
	private void setBaseInfo(View baseInfoContentView) {
		if(ETGApplication.userEntity!=null){
			username_Tv.setText(ETGApplication.userEntity.getUsername());
			nickName_Tv.setText(ETGApplication.userEntity.getNickname());
			if("m".equals(ETGApplication.userEntity.getGender())){				
				gender_Tv.setText("��");
			}else if("f".equals(ETGApplication.userEntity.getGender())){				
				gender_Tv.setText("Ů");
			}
			//ת��ʱ��
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String lastLoginTime = format.format(ETGApplication.userEntity.getLastLoginTime());
			lastLoginTime_Tv.setText(lastLoginTime);
			String registTime = format.format(ETGApplication.userEntity.getRegTime());
			registTime_Tv.setText(registTime);
		}
	}

	private PopupWindow window;
	private View contentView;
	private static final int INSERT_TO_DB = -1;
	private static final int UPDATE_TO_DB = -2;
	/**���ó�����Ϣ����*/
	@SuppressWarnings("deprecation")
	private void setPopupWindow(int submitType, int selectedId) {
		//����,������Ϣ
		contentView = View.inflate(PersonalFragment.this.getActivity(), R.layout.item_personal_addinfo_popupwindow, null);
		//��ȡ��Ļ������
		WindowManager manager = getActivity().getWindowManager();
		//���õ����Ŀ���
		DisplayMetrics displayMetrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(displayMetrics);
		//���õ������ڵĴ�С��λ��
		window = new PopupWindow(contentView, displayMetrics.widthPixels/4*3, displayMetrics.heightPixels/4);
		// ʹ��ۼ� ��Ҫ������˵���ؼ����¼��ͱ���Ҫ���ô˷���
		window.setFocusable(true);
		// ���ñ����������Ϊ�˵��������Back��Ҳ��ʹ����ʧ�����Ҳ�����Ӱ����ı���
		window.setBackgroundDrawable(new BitmapDrawable());
		//�����̲��ᵲ��popupwindow
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		window.showAtLocation(personal_generalinfo_Tv, Gravity.CENTER, 0, displayMetrics.heightPixels/7);
		//window.showAsDropDown(personal_generalinfo_Tv);
		initView();
		setWindowListener(submitType,selectedId);
	}

	/**��ʼ��������Ϣ�����еĿؼ�*/
	private void initView() {
		name_Et = (EditText)contentView.findViewById(R.id.name_Et);
		address_Et = (EditText)contentView.findViewById(R.id.address_ET);
		submit_btn = (Button) contentView.findViewById(R.id.check_Btn);
		cancel_btn = (Button) contentView.findViewById(R.id.cancel_Btn);
	}

	/**���ó�����Ϣ�����ĵ������
	 * @param selectedPos */
	private void setWindowListener(final int submitType, final int selectedId) {
		//�����ύ����
		submit_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(name_Et.getText().toString()) 
						|| TextUtils.isEmpty(address_Et.getText().toString())){
					Toast.makeText(PersonalFragment.this.getActivity(), "��Ϣû������Ŷ", Toast.LENGTH_SHORT).show();
				}else{
					//�������ݵ��������ݿ�SQLite
					saveInfo(submitType, selectedId);
					window.dismiss();
				}

			}
		});

		//���ȡ������
		cancel_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				window.dismiss();
			}
		});

	}

	private List<Map<String, String>> generalInfos ;
	private String nameKey = "_name";
	private String addressKey = "_address";
	private String idKey = "_id";
	/**������Ϣ������
	 * @param submitType 
	 * @param selectedPos */
	private void saveInfo(int submitType, int selectedId) {
		String nameInfo = name_Et.getText().toString();
		String addressInfo = address_Et.getText().toString();
		Map<String, String> map = new HashMap<String, String>();
		//������Ϣ������
		map.put(nameKey, nameInfo);
		map.put(addressKey, addressInfo);
		generalInfos.add(map);
		if(INSERT_TO_DB == submitType){			
			insertDataToDB();
		}else if(UPDATE_TO_DB == submitType){
			ContentValues updateValues = new ContentValues();
			updateValues.put(nameKey, nameInfo);
			updateValues.put(addressKey, addressInfo);
			String whereClause = "_id="+selectedId;
			updateInfo(updateValues,whereClause,null);
		}
		selectDataFromDB();
	}

	/**�������ݿ����Ϣ*/
	private void updateInfo( ContentValues values, String whereClause, String[] whereArgs) {
		dbHelper.update("ETGgeneralInfo", values, whereClause, whereArgs);
	}


	/**�����ݿ��������*/
	private void insertDataToDB() {
		ContentValues values = new ContentValues();
		values.put("_username", ETGApplication.userEntity.getUsername());
		values.put("_name", name_Et.getText().toString());
		values.put("_address", address_Et.getText().toString());
		dbHelper.insert("ETGgeneralInfo", values);
	}

	private GeneralInfoAdapter adapter;
	/**���ó�����Ϣ��listview�ϵ���ʾ*/
	private void showListView() {
		if(adapter == null){
			adapter = new GeneralInfoAdapter(this.getActivity());
			adapter.setGeneralInfos(generalInfos);
			general_lv.setAdapter(adapter);
		}else {
			adapter.notifyDataSetChanged();
		}
	}


	/**����ؼ���״̬*/
	@Override
	public void onDestroyView() {
		adapter = null;
		super.onDestroyView();
	}

	/**�����ݿ�ɾ����Ϣ*/
	private void deleteinfo(String whereClause) {
		dbHelper.delete("ETGgeneralInfo", whereClause, null);
	}
}