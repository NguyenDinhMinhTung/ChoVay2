package com.example.megas.chovay.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.megas.chovay.DTO.PeopleDTO;
import com.example.megas.chovay.Database.Database;

import java.util.ArrayList;
import java.util.List;

public class PeopleDAO {
    SQLiteDatabase database;

    public PeopleDAO(Context context) {
        Database data = new Database(context);
        database = data.open();
    }

    public int addPeople(PeopleDTO peopleDTO) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.TB_PEOPLE_GROUPID, peopleDTO.getGroupId());
        contentValues.put(Database.TB_PEOPLE_NAME, peopleDTO.getName());

        database.insert(Database.TB_PEOPLE, null, contentValues);

        String query = "SELECT * FROM " + Database.TB_PEOPLE + " ORDER BY " + Database.TB_PEOPLE_ID + " DESC LIMIT 1";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        int id = cursor.getInt(cursor.getColumnIndex(Database.TB_PEOPLE_ID));

        return id;
    }

    public List<PeopleDTO> getListPeople() {
        List<PeopleDTO> peopleDTOList = new ArrayList<>();

        String query = "SELECT * FROM " + Database.TB_PEOPLE;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(Database.TB_PEOPLE_ID));
            int groupId = cursor.getInt(cursor.getColumnIndex(Database.TB_PEOPLE_GROUPID));
            String name = cursor.getString(cursor.getColumnIndex(Database.TB_PEOPLE_NAME));

            PeopleDTO peopleDTO = new PeopleDTO(id, name, groupId);
            peopleDTOList.add(peopleDTO);

            cursor.moveToNext();
        }

        return peopleDTOList;
    }

    public String getPeopleNameById(int peopleId) {
        String query = "SELECT * FROM " + Database.TB_PEOPLE + " WHERE " + Database.TB_PEOPLE_ID + "=" + peopleId;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        String name = cursor.getString(cursor.getColumnIndex(Database.TB_PEOPLE_NAME));

        return name;
    }

    public int isPeopleExists(String name) {
        List<PeopleDTO> peopleDTOList = getListPeople();

        for (PeopleDTO people : peopleDTOList) {
            if (people.getName().equals(name)) return people.getId();
        }

        return -1;
    }

    public List<PeopleDTO> getListPeopleByGroupId(int groupId) {
        List<PeopleDTO> peopleDTOList = new ArrayList<>();

        String query = "SELECT * FROM " + Database.TB_PEOPLE + " WHERE " + Database.TB_PEOPLE_GROUPID + "=" + groupId;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(Database.TB_PEOPLE_ID));
            String name = cursor.getString(cursor.getColumnIndex(Database.TB_PEOPLE_NAME));

            PeopleDTO peopleDTO = new PeopleDTO(id, name, groupId);
            peopleDTOList.add(peopleDTO);

            cursor.moveToNext();
        }

        return peopleDTOList;
    }

    public void setGroupId(int peopleId, int groupId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Database.TB_PEOPLE_GROUPID, groupId);
        database.update(Database.TB_PEOPLE, contentValues, Database.TB_PEOPLE_ID + "=" + peopleId, null);
    }
}
