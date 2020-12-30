package com.example.getsensorvalue;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sensor_database";
    private static final String TABLE1 = "table1";
    private static final String TABLE2 = "table2";
    private static final String TABLE3 = "table3";
    private static final String TABLE4 = "table4";
    private static final String TABLE5 = "table5";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " + TABLE1 + "(id INTEGER PRIMARY KEY,light TEXT)";
        String table2 = "CREATE TABLE " + TABLE2 + "(id INTEGER PRIMARY KEY, proximity TEXT)";
        String table3 = "CREATE TABLE " + TABLE3 + "(id INTEGER PRIMARY KEY,accelerometer TEXT)";
        String table4 = "CREATE TABLE " + TABLE4 + "(id INTEGER PRIMARY KEY,gyrospoe TEXT)";
        String table5 = "CREATE TABLE " + TABLE5 + "(id INTEGER PRIMARY KEY,time TEXT)";

        db.execSQL(table1);
        db.execSQL(table2);
        db.execSQL(table3);
        db.execSQL(table4);
        db.execSQL(table5);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE2);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE3);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE4);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE5);
        onCreate(db);

    }


    public boolean insert(String light,String proximity,String accelerometer,String gyrospoe,String time){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();

        ContentValues values1=new ContentValues();
        values1.put("light",light);
        sqLiteDatabase.insert(TABLE1,null,values1);

        ContentValues values2=new ContentValues();
        values2.put("proximity",proximity);
        sqLiteDatabase.insert(TABLE2,null,values2);

        ContentValues values3=new ContentValues();
        values3.put("accelerometer",accelerometer);
        sqLiteDatabase.insert(TABLE3,null,values3);

        ContentValues values4=new ContentValues();
        values4.put("gyrospoe",gyrospoe);
        sqLiteDatabase.insert(TABLE4,null,values4);

        ContentValues values5=new ContentValues();
        values5.put("time",time);
        sqLiteDatabase.insert(TABLE5,null,values5);
        return true;
    }

    public ArrayList getLight(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE1,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("light")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getProximity(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE2,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("proximity")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getAccelerometer(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE3,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("accelerometer")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getGyrospoe(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE4,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("gyrospoe")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList getTime(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<String> arrayList=new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE5,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("time")));
            cursor.moveToNext();
        }
        return arrayList;
    }


}
