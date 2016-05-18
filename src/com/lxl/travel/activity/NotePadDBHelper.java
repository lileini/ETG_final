package com.lxl.travel.activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lxl.travel.utils.LogUtil;
/**借助此类的方法操作数据库*/
public class NotePadDBHelper 
       extends SQLiteOpenHelper{

	public static NotePadDBHelper newInstance(Context context){
		return new NotePadDBHelper(context,//此时并不会创建数据库
	    "ETG.db", null,1);
	}
	private NotePadDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	/**此方法用于查询*/
	public Cursor query(String sql,String[]selectionArgs){
		//获得操作数据库的SQLiteDatabase对象
		SQLiteDatabase db=
				getReadableDatabase();//数据库不存在则创建，存在则打开
		//借助SQLiteDatabase对象执行查询
		return db.rawQuery(sql,
				selectionArgs);
	}

	/**借助此方法将数据写到数据库*/
	public long insert(String table,ContentValues values){
		SQLiteDatabase db=
				getWritableDatabase();
		long id=db.insert(table,null,values);
		db.close();
		return id;
	}
	/**借助此方法根据id删除数据库中的数据*/
    public int delete(String table,String whereClause,String[]whereArgs){
    	SQLiteDatabase db=
    	getWritableDatabase();
    	//String sql="delete from notetab where _id=?"
    	//db.execSQL(sql, bindArgs)
    	int n=db.delete(table,whereClause,whereArgs);
    	db.close();
    	return n;
    }
	/**更新数据*/
    public int update(String table,ContentValues values,String whereClause,String[]whereArgs){
    	SQLiteDatabase db=
    			getWritableDatabase();
    	int n=db.update(table, values, whereClause, whereArgs);
    	db.close();
    	return n;
    }


	/**此方法在创建数据库时自动执行，假如
	 * 数据已存在，则不在执行。
	 * 具体在此方法中做什么由业务而定。
	 * */
	@Override
	public void onCreate(SQLiteDatabase db) {
		LogUtil.i("TAG", "helper.onCreate");
		//可以在此方法中创建表
		String sqla="create table ETGTravelLog(" +
		"_id integer primary key autoincrement," +
		"_username text not null,"+
		"_content text not null," +
		"_created text not null)";
		//第二张表
		String sqlb = "create table ETGgeneralInfo(" +
		"_id integer primary key autoincrement," +
		"_username text not null,"+
		"_address text not null," +
		"_name text not null)";
		db.execSQL(sqla);
		db.execSQL(sqlb);
		LogUtil.i("TAG", "table create ok!");
	}
	/**在数据库版本升级时此方法会自动执行，
	 * 具体升级时做什么，由app业务而定。*/
	@Override
	public void onUpgrade(SQLiteDatabase db,
	int oldVersion, int newVersion) {
		LogUtil.i("TAG", "helper.onCreate");
		String sqla="drop table ETGTravelLog";
		String sqlb="drop table ETGgeneralInfo";
		db.execSQL(sqla);
		db.execSQL(sqlb);
		onCreate(db);
	}

}
