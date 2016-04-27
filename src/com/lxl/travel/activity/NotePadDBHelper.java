package com.lxl.travel.activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.lxl.travel.utils.LogUtil;
/**��������ķ����������ݿ�*/
public class NotePadDBHelper 
       extends SQLiteOpenHelper{

	public static NotePadDBHelper newInstance(Context context){
		return new NotePadDBHelper(context,//��ʱ�����ᴴ�����ݿ�
	    "ETG.db", null,1);
	}
	private NotePadDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	/**�˷������ڲ�ѯ*/
    public Cursor query(String sql,String[]selectionArgs){
    	//��ò������ݿ��SQLiteDatabase����
    	SQLiteDatabase db=
    	getReadableDatabase();//���ݿⲻ�����򴴽����������
    	//����SQLiteDatabase����ִ�в�ѯ
    	return db.rawQuery(sql,
    	selectionArgs);
    }
    
    /**�����˷���������д�����ݿ�*/
    public long insert(String table,ContentValues values){
        SQLiteDatabase db=
        getWritableDatabase();
        long id=db.insert(table,null,values);
        db.close();
    	return id;
    }
    /**�����˷�������idɾ�����ݿ��е�����*/
    public int delete(String table,String whereClause,String[]whereArgs){
    	SQLiteDatabase db=
    	getWritableDatabase();
    	//String sql="delete from notetab where _id=?"
    	//db.execSQL(sql, bindArgs)
    	int n=db.delete(table,whereClause,whereArgs);
    	db.close();
    	return n;
    }
    /**��������*/
    public int update(String table,ContentValues values,String whereClause,String[]whereArgs){
    	SQLiteDatabase db=
    			getWritableDatabase();
    	int n=db.update(table, values, whereClause, whereArgs);
    	db.close();
    	return n;
    }
    
    
	/**�˷����ڴ������ݿ�ʱ�Զ�ִ�У�����
	 * �����Ѵ��ڣ�����ִ�С�
	 * �����ڴ˷�������ʲô��ҵ�������
	 * */
	@Override
	public void onCreate(SQLiteDatabase db) {
		LogUtil.i("TAG", "helper.onCreate");
		//�����ڴ˷����д�����
		String sqla="create table ETGTravelLog(" +
		"_id integer primary key autoincrement," +
		"_username text not null,"+
		"_content text not null," +
		"_created text not null)"; 
		//�ڶ��ű�
		String sqlb = "create table ETGgeneralInfo(" +
		"_id integer primary key autoincrement," +
		"_username text not null,"+
		"_address text not null," +
		"_name text not null)";
		db.execSQL(sqla);
		db.execSQL(sqlb);
		LogUtil.i("TAG", "table create ok!");
	}
	/**�����ݿ�汾����ʱ�˷������Զ�ִ�У�
	 * ��������ʱ��ʲô����appҵ�������*/
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
