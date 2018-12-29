package com.example.megas.chovay.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    public static final String TB_LOAN = "LOAN";
    public static final String TB_GROUP = "GROUPS";
    public static final String TB_PEOPLE = "PEOPLE";


    public static final String TB_LOAN_ID = "ID";
    public static final String TB_LOAN_PEOPLEID = "PEOPLEID";
    public static final String TB_LOAN_AMOUNT = "AMOUNT";
    public static final String TB_LOAN_CREATEDTIME = "CREATEDTIME";
    public static final String TB_LOAN_STATUS = "STATUS";
    public static final String TB_LOAN_NOTE = "NOTE";

    public static final String TB_GROUP_ID = "ID";
    public static final String TB_GROUP_NAME = "NAME";

    public static final String TB_PEOPLE_ID = "ID";
    public static final String TB_PEOPLE_NAME = "NAME";
    public static final String TB_PEOPLE_GROUPID = "GROUPID";

    public Database(Context context) {
        super(context, "CHOVAY", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tbLOAN = "CREATE TABLE " + TB_LOAN + " (" + TB_LOAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TB_LOAN_PEOPLEID + " INTEGER NOT NULL, "
                + TB_LOAN_AMOUNT + " INTEGER NOT NULL, " + TB_LOAN_CREATEDTIME + " TEXT, " + TB_LOAN_STATUS + " INTEGER NOT NULL, " + TB_LOAN_NOTE + " TEXT)";

        String tbGROUP = "CREATE TABLE " + TB_GROUP + " (" + TB_GROUP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_GROUP_NAME + " TEXT NOT NULL)";

        String tbPEOPLE = "CREATE TABLE " + TB_PEOPLE + " (" + TB_PEOPLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TB_PEOPLE_GROUPID + " INTEGER, " + TB_PEOPLE_NAME + " TEXT NOT NULL)";

        db.execSQL(tbLOAN);
        db.execSQL(tbGROUP);
        db.execSQL(tbPEOPLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase open() {
        return this.getWritableDatabase();
    }
}
