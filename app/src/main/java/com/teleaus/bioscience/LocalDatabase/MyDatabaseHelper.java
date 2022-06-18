package com.teleaus.bioscience.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "DogBreed.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "DogBreed_Library";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_IDS = "ids";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_IDS + " TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    public void saveNewPerson(HistoryDemoModel historyDemoModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IDS, historyDemoModel.getIds());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

   /* *//**Query only 1 record**//*
    public HistoryDemoModel getPerson(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE _id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        HistoryDemoModel historyDemoModel = new HistoryDemoModel();
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            historyDemoModel.setIds(cursor.getString(cursor.getColumnIndex(COLUMN_IDS)));

        }
        return historyDemoModel;
    }*/

    /**delete record**/


    /**Query records, give options to filter results**/
    public ArrayList<HistoryDemoModel> peopleList(String filter) {
        String query;
        if(filter.equals("")){
            //regular query
            query = "SELECT  * FROM " + TABLE_NAME;
        }else{
            //filter results by filter option provided
            query = "SELECT  * FROM " + TABLE_NAME + " ORDER BY "+ filter;
        }

        ArrayList<HistoryDemoModel> personLinkedList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        HistoryDemoModel person;

        if (cursor.moveToFirst()) {
            do {
                person = new HistoryDemoModel();

                person.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                person.setIds(cursor.getString(cursor.getColumnIndex(COLUMN_IDS)));

                personLinkedList.add(person);
            } while (cursor.moveToNext());
        }


        return personLinkedList;
    }


    public void deletePersonRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE _id='"+id+"'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }
}
