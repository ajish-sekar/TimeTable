package com.example.ajish.timetable.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Lectures";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "TimeTable";

    private static final String KEY_ID = "id";
    private static final String KEY_DAY = "day";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_START = "start";
    private static final String KEY_END = "end";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, " + KEY_DAY +
                " TEXT, " + KEY_SUBJECT + " TEXT, " + KEY_START +
                " TEXT, " + KEY_END + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );

        onCreate(sqLiteDatabase);
    }

    public void addLecture(Lectures lecture){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DAY, lecture.getDay());
        values.put(KEY_SUBJECT, lecture.getSubject());
        values.put(KEY_START, lecture.getStartTime());
        values.put(KEY_END, lecture.getEndTime());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Lectures getLecture(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID, KEY_DAY, KEY_SUBJECT,
                KEY_START, KEY_END}, KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);
        if(cursor != null)
            cursor.moveToFirst();

        Lectures lecture = new Lectures(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2), cursor.getString(3), cursor.getString(4));
        return lecture;
    }

    public List<Lectures> getAll(){
        List<Lectures> lectures = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Lectures lecture = new Lectures();
                lecture.setId(Integer.parseInt(cursor.getString(0)));
                lecture.setDay(cursor.getString(1));
                lecture.setSubject(cursor.getString(2));
                lecture.setStartTime(cursor.getString(3));
                lecture.setEndTime(cursor.getString(4));

                lectures.add(lecture);
            }while (cursor.moveToNext());
        }

        return lectures;
    }

    public int updateLecture(Lectures lecture){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DAY, lecture.getDay());
        values.put(KEY_SUBJECT, lecture.getSubject());
        values.put(KEY_START, lecture.getStartTime());
        values.put(KEY_END, lecture.getEndTime());

        return db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(lecture.getId())});
    }

    public void deleteLecture(Lectures lecture){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(lecture.getId())});

        db.close();
    }

    public List<Lectures> getDayLectures(String day){
        List<Lectures> lectures = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID, KEY_DAY, KEY_SUBJECT,
                KEY_START, KEY_END}, KEY_DAY + " = ?", new String[]{day}, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                Lectures lecture = new Lectures();
                lecture.setId(Integer.parseInt(cursor.getString(0)));
                lecture.setDay(cursor.getString(1));
                lecture.setSubject(cursor.getString(2));
                lecture.setStartTime(cursor.getString(3));
                lecture.setEndTime(cursor.getString(4));

                lectures.add(lecture);

            }while (cursor.moveToNext());
        }

        return lectures;
    }
}
