package com.lxl.travel.adapter;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Text;

import com.lxl.trivel.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GeneralInfoAdapter extends BaseAdapter{
	private List<Map<String, String>> generalInfos;
	private Context context;
	
	public void setGeneralInfos(List<Map<String, String>> generalInfos) {
		this.generalInfos = generalInfos;
	}
	
	
	public GeneralInfoAdapter (Context context){
		this.context = context;
	}
	@Override
	public int getCount() {
		return generalInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return generalInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_generalinfo, null);
			holder = new ViewHolder();
			holder.addressTx = (TextView) convertView.findViewById(R.id.item_address_Tv);
			holder.nameTx = (TextView) convertView.findViewById(R.id.item_name_Tv);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.addressTx.setText(generalInfos.get(position).get("_address"));
		holder.nameTx.setText(generalInfos.get(position).get("_name"));
		
		return convertView;
	}
	
	class ViewHolder{
		private TextView nameTx;
		private TextView addressTx;
	}
}
