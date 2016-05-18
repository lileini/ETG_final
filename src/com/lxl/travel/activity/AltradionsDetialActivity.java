//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : EasyToTravel
//  @ File Name : AltradionsDetialActivity.java
//  @ Date : 2015/10/12
//  @ Author : 
//
//
/**
 * @author cm
 * */

package com.lxl.travel.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lxl.travel.ETGApplication;
import com.lxl.travel.base.BaseActivity;
import com.lxl.travel.biz.CmTravelDetailBiz;
import com.lxl.travel.entity.AltradionsEntity;
import com.lxl.travel.entity.PlanEntity;
import com.lxl.travel.entity.TravelDetailEntity;
import com.lxl.travel.utils.JsonParser;
import com.lxl.trivel.R;
/** 	这个Activity为旅游界面点击item后跳转的界面，显示旅游景点的详细信息。1.点击最上方的图片可以显示图像TOAST提示
 * 2.点击加入计划可以实现计划的加入。 */ public class AltradionsDetialActivity extends BaseActivity {
	
	public TextView tv01;		//textviewΪ�������ݣ�imageviewΪ���ص�ͼƬ
	public TextView tv02;
	public TextView tv03;
	public ImageView iv01;
	private AltradionsEntity altradionsEntity;  //�����ʵ���ࡱ���� װ ���صľ������ݡ�
	public ArrayList<String> list = new ArrayList<String>();  //���listΪ��ȡjson��String�У���http��ͷ��jpg./��β�м����վURL
	BitmapUtils btutils ;   //Xutils�еĹ����� ���ڻ������ͼƬ
	private LinearLayout ll;   //���LL��Ҫ��Ϊ�˼���imageview�������ַ�������ȡ������վ��URL���ܼ������ͼƬ��
	private View view;
	private ImageView iv;
	private Intent fileintent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_detail);
		getActionBar().hide();
		altradionsEntity = (AltradionsEntity) getIntent().getSerializableExtra("TravelDetail");
		//Log.i("TravelDetail = ", altradionsEntity.toString());
		fileintent = getIntent();
		setviews();
		setlisteners();
		
	}
	public void test(String s){
		btutils = new BitmapUtils(this);
		//1.�����õ���json ���� ����ת��Ϊʵ����
		TravelDetailEntity entity = JsonParser.parserForDetail(s);
		//2.1����textview�ϵ�����
		tv02.setText(entity.getOpenTimeDesc());
		tv01.setText(altradionsEntity.getTravelAddress());
		tv03.setText(altradionsEntity.getProductManagerRecommand());
		btutils.display(iv01, altradionsEntity.getImage());
		//2.2����֮ǰ��Ҫ ��ʵ����ĵڶ�������ת��Ϊ ��������֪��ͼ�����ݣ�String�����뼯�ϣ����ӣ�src=\"http*****.jpg.\"  ��ȡ��ַURL��
		String pull = entity.getIntroduction();
		//Log.i("PULL = ", pull);
		String titel = "src=\"";
		String from = "\"";
		int Start = pull.indexOf(titel)+5;
		int end = pull.indexOf(from, Start);
		if(Start<end){
		if(Start!=4){
			Log.i("Start = ", Start+"");
			Log.i("end = ", end+"");
			String ss = (String) pull.subSequence(Start, end);
			list.add(ss);
		}
			while(Start!=-1){
				Start = pull.indexOf(titel, end)+5;
				end = pull.indexOf(from, Start);
				if(Start==4){
					break;
				}
				String sss = (String) pull.subSequence(Start, end);
				System.out.println(sss);
				list.add(sss);
			}
		
			entity.setImageList(list);
			//Log.i("imageURL[2] = ", list.get(2));
		//3.�жϼ��ϵĳ��ȣ���ӵ�����imageview����ScrollView��
			  ll = (LinearLayout) findViewById(R.id.LinearLayout_imageDetail);
			for (int i = 0; i < list.size(); i++) {
				  view = View.inflate(this, R.layout.linearlayout_image_detail, null);
				  iv = (ImageView) view.findViewById(R.id.imageView_detail);
				  try {
					ll.addView(view);
					btutils.display(iv, list.get(i));
				} catch (Exception e) {
					e.printStackTrace();
				}		 
			}
		}
	}
	

	private void setlisteners() {
		//��ʱδ���ϣ�
	}
	//����ƻ������ļ���
	public void jiaruPLAN(View v){
		if(ETGApplication.userEntity == null){
			Toast.makeText(this, "并没有登录~亲", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(AltradionsDetialActivity.this, LoginActivity.class);
			startActivity(intent);
		}else{
		Intent intent = new Intent(this,AddPlanActivity.class);
		intent.putExtra("AltradionsEntity", altradionsEntity);
		intent.putExtra("fromDate", fileintent.getStringExtra("timeFrom"));//������������   ����ƻ�������ת���������Ǵ��ĳ���ʱ��
		intent.putExtra("toDate", fileintent.getStringExtra("timeto"));//   �ͷ���ʱ��  ����ʱ����������ƻ�������ʱ���ػ�ȥ��
		startActivity(intent);
		finish();
		}
	}

	private void setviews() {
		iv01 = (ImageView)findViewById(R.id.AltradionsDetialActivityimageView1);
		tv01 = (TextView)findViewById(R.id.AltradionsDetialActivitytextView1);
		tv02 = (TextView)findViewById(R.id.AltradionsDetialActivitytextView2);
		tv03 = (TextView)findViewById(R.id.AltradionsDetialActivitytextView3);
		new CmTravelDetailBiz(this, Integer.parseInt(altradionsEntity.getTravelID())).GetTravelDetail();
		
	}
//��һ��imageview�ļ�����������˾��
	public void image_onClick(View view){
		MyToast my = new MyToast();
		my.myTosat(AltradionsDetialActivity.this, R.drawable.b01, "���������ƻ�������~", Toast.LENGTH_LONG);
	}
	//�Զ�����˾��ͼƬ���У��������Աߣ�
	class MyToast {
	    public   void myTosat(Context context , int imageId ,String content , int duration){
	        //newһ��toast����Ҫ��ʾ��activity��������
	        Toast toast = new Toast(context);
	        //��ʾ��ʱ��
	        toast.setDuration(duration);
	        //��ʾ��λ��
	        toast.setGravity(Gravity.BOTTOM, 0, 300);
	        //���¸�toast���в���
	        LinearLayout toastLayout = new LinearLayout(context);
	        toastLayout.setOrientation(LinearLayout.HORIZONTAL);
	        toastLayout.setGravity(Gravity.CENTER_VERTICAL);
	       
	        ImageView imageView = new ImageView(context);
	        imageView.setImageResource(imageId);
	        imageView.setLayoutParams(new LayoutParams(200, 200));
	        //��imageView��ӵ�toastLayout�Ĳ��ֵ���
	        toastLayout.addView(imageView);
	       
	        TextView textView = new TextView(context);
	        textView.setText(content);
	        textView.setTextSize(30);
	        textView.setTextColor(Color.RED);
//	        textView.setBackgroundColor(Color.GRAY);
	        //��textView��ӵ�toastLayout�Ĳ��ֵ���
	        toastLayout.addView(textView);
	        //��toastLayout��ӵ�toast�Ĳ��ֵ���
	        toast.setView(toastLayout);
	        toast.show();
	    }
	}
	
	
	public void addPlan(PlanEntity planEntity) {
		
	
	}
}
