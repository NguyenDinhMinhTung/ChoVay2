package com.example.megas.chovay.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.megas.chovay.DTO.GroupDTO;
import com.example.megas.chovay.Database.Database;

import java.util.ArrayList;
import java.util.List;

public class GroupDAO {
    SQLiteDatabase database;

    public GroupDAO(Context context){
        Database data=new Database(context);
        database=data.open();
    }

    public void addGroup(GroupDTO groupDTO){
        ContentValues contentValues=new ContentValues();

        contentValues.put(Database.TB_GROUP_NAME,groupDTO.getName());

        database.insert(Database.TB_GROUP,null,contentValues);
    }

    public List<GroupDTO> getListGroup(){
        List<GroupDTO> groupDTOList=new ArrayList<>();

        String query="SELECT * FROM "+Database.TB_GROUP;
        Cursor cursor=database.rawQuery(query,null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            int id=cursor.getInt(cursor.getColumnIndex(Database.TB_GROUP_ID));
            String name=cursor.getString(cursor.getColumnIndex(Database.TB_GROUP_NAME));

            GroupDTO groupDTO=new GroupDTO(id,name);

            groupDTOList.add(groupDTO);
            cursor.moveToNext();
        }

        return groupDTOList;
    }
}
