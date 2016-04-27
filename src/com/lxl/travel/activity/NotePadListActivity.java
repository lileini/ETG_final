package com.lxl.travel.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.lxl.travel.ETGApplication;
import com.lxl.travel.utils.LogUtil;
import com.lxl.trivel.R;

/**
 * 此页面为打开备忘录的第一个页面 功能：以列表形式显示所有备忘录信息
 * */
public class NotePadListActivity extends ListActivity {
	private SimpleCursorAdapter adapter;
	private NotePadDBHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_pad_list);
		getActionBar().hide();
		// ListView实现
		setListView();
	}

	/**
	 * Activity运行时执行此方法： 只要Activity由不可见到可见这种状态 都要执行此方法
	 * */
	private Cursor newCursor;

	@Override
	protected void onResume() {
		super.onResume();
		updateListView();
	}

	private void updateListView() {
		// 查询数据库
		String sql = "select * from ETGTravelLog where _username="
				+ ETGApplication.userEntity.getUsername();
		newCursor = dbHelper.query(sql, null);
		// 交换数据源Cursor对象
		Cursor oldCursor = adapter.swapCursor(newCursor);
		// 关闭原有Cursor
		if (oldCursor != null)
			oldCursor.close();
		LogUtil.i("TAG", "MainActivity.onResume");
	}

	@Override
	protected void onStop() {
		super.onStop();
		LogUtil.i("TAG", "MainActivity.onStop");
	}

	private void setListView() {
		// 1.ListView
		ListView lsv = getListView();
		// ListView lsv=(ListView) findViewById(android.R.id.list);
		// 2.Adapter (SimpleCursorAdapter)
		// 2.1 item data (Cursor)
		dbHelper = NotePadDBHelper.newInstance(this);// 1表示version
		String from[] = { "_content", "_created" };// curror对象对应的记录中某些列的名字
		// 2.2 item view (R.layout.note_layout_item_2)
		int to[] = { R.id.contentId, R.id.createId };// item view中用于显示数据的id值
		adapter = new SimpleCursorAdapter(this, R.layout.note_layout_item_2,
				null, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);// 标识
		// 3.关联Adapter
		setListAdapter(adapter);
		// 4.设置监听(点击,长按)
		// 上下文菜单对象
		registerForContextMenu(lsv);

		Button newNoteUI = (Button) findViewById(R.id.newNoteUI);
		newNoteUI.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NotePadListActivity.this,
						NotePadAddActivity.class);
				startActivity(intent);
			}
		});
	}

	public void onClick(View v){
		finish();
	}
	
	/** 点击listview的item时要执行的方法 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {// the
																				// row
																				// id
																				// of
																				// item
		Intent intent = new Intent(this, NotePadDetailActivity.class);
		// 此的id的值为表中某行记录的主键_id值。
		intent.putExtra("_id", (int) id);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.note_pad_list, menu);
		return true;
	}

	// ================================
	/** 创建上下文菜单(长按注册了上下文菜单的那个view时执行此方法) */
	private static final int DELETE_ID = 100;
	private static final int EDIT_ID = 101;

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// 添加两个菜单项
		menu.add(0, DELETE_ID, 1, "删除");
		menu.add(0, EDIT_ID, 2, "编辑");
	}

	/** 处理上下文菜单的点击事件 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (DELETE_ID == item.getItemId()) {
			deleteNoteItem(item);
		}
		if (EDIT_ID == item.getItemId()) {
			AdapterContextMenuInfo cmi = (AdapterContextMenuInfo) item
					.getMenuInfo();
			LogUtil.i("TAG", "cmi=" + cmi);
			long id = cmi.id;
			Intent intent = new Intent(NotePadListActivity.this,
					NotePadDetailActivity.class);
			intent.putExtra("_id", id);
			startActivity(intent);
		}
		return super.onContextItemSelected(item);
	}

	/** 根据_id删除数据 */
	private void deleteNoteItem(MenuItem item) {
		AdapterContextMenuInfo cmi = (AdapterContextMenuInfo) item
				.getMenuInfo();
		LogUtil.i("TAG", "cmi=" + cmi);
		long id = cmi.id;// row id of the item
		// int pos=cmi.position;//listview中item的位置
		// 1.获得要删除的数据的id
		// 2.根据id删除数据
		dbHelper.delete("ETGTravelLog", "_id=?",
				new String[] { String.valueOf(id) });
		Toast.makeText(this, "删除成功", 0).show();
		// 3.更新ListView
		updateListView();
	}

	/** Activity销毁时会自动执行此方法 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.i("TAG", "MainActivity.onDestroy");
		if (newCursor != null)
			newCursor.close();
	}
}
