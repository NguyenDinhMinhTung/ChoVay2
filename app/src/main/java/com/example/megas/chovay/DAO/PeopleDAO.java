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

    public void addPeople(PeopleDTO peopleDTO) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.TB_PEOPLE_GROUPID, peopleDTO.getGroupId());
        contentValues.put(Database.TB_PEOPLE_NAME, peopleDTO.getName());

        database.insert(Database.TB_PEOPLE, null, contentValues);
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

    public Boolean isPeopleExists(String name) {
        List<PeopleDTO> peopleDTOList = getListPeople();

        for (PeopleDTO people : peopleDTOList) {
            if (people.getName().equals(name)) return true;
        }

        return false;
    }
}
