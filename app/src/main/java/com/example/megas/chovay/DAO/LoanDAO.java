package com.example.megas.chovay.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.Database.Database;

import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    SQLiteDatabase database;

    public LoanDAO(Context context) {
        Database data = new Database(context);
        database = data.open();
    }

    public void addLoan(LoanDTO loanDTO) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.TB_LOAN_PEOPLEID, loanDTO.getPeopleId());
        contentValues.put(Database.TB_LOAN_AMOUNT, loanDTO.getAmount());
        contentValues.put(Database.TB_LOAN_CREATEDTIME, loanDTO.getCreatedTime());
        contentValues.put(Database.TB_LOAN_STATUS, loanDTO.getStatus());
        contentValues.put(Database.TB_LOAN_NOTE, loanDTO.getNote());

        database.insert(Database.TB_LOAN, null, contentValues);
    }

    public List<LoanDTO> getListLoan() {
        List<LoanDTO> loanDTOList = new ArrayList<>();

        String query = "SELECT * FROM " + Database.TB_LOAN;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_ID));
            int peopleId = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_PEOPLEID));
            int amount = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_AMOUNT));
            int status = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_STATUS));

            String createdTime = cursor.getString(cursor.getColumnIndex(Database.TB_LOAN_CREATEDTIME));
            String note = cursor.getString(cursor.getColumnIndex(Database.TB_LOAN_NOTE));

            LoanDTO loanDTO = new LoanDTO(id, peopleId, amount, status, createdTime, note);

            loanDTOList.add(loanDTO);

            cursor.moveToNext();
        }

        return loanDTOList;
    }

    public List<LoanDTO> getListLoanByPeopleId(int peopleId) {
        List<LoanDTO> loanDTOList = new ArrayList<>();

        String query = "SELECT * FROM " + Database.TB_LOAN + " WHERE " + Database.TB_LOAN_PEOPLEID + "=" + peopleId;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_ID));
            int amount = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_AMOUNT));
            int status = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_STATUS));

            String createdTime = cursor.getString(cursor.getColumnIndex(Database.TB_LOAN_CREATEDTIME));
            String note = cursor.getString(cursor.getColumnIndex(Database.TB_LOAN_NOTE));

            LoanDTO loanDTO = new LoanDTO(id, peopleId, amount, status, createdTime, note);

            loanDTOList.add(loanDTO);

            cursor.moveToNext();
        }

        return loanDTOList;
    }

    public List<LoanDTO> getListLoanByPeopleId(int peopleId, int status) {
        List<LoanDTO> loanDTOList = new ArrayList<>();

        String query = "SELECT * FROM " + Database.TB_LOAN + " WHERE " + Database.TB_LOAN_PEOPLEID + "=" + peopleId +
                " AND " + Database.TB_LOAN_STATUS + "=" + status;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_ID));
            int amount = cursor.getInt(cursor.getColumnIndex(Database.TB_LOAN_AMOUNT));

            String createdTime = cursor.getString(cursor.getColumnIndex(Database.TB_LOAN_CREATEDTIME));
            String note = cursor.getString(cursor.getColumnIndex(Database.TB_LOAN_NOTE));

            LoanDTO loanDTO = new LoanDTO(id, peopleId, amount, status, createdTime, note);

            loanDTOList.add(loanDTO);

            cursor.moveToNext();
        }

        return loanDTOList;
    }
}
