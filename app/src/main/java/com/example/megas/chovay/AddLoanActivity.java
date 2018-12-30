package com.example.megas.chovay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.megas.chovay.DAO.LoanDAO;
import com.example.megas.chovay.DAO.PeopleDAO;
import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.DTO.PeopleDTO;

import java.util.Calendar;
import java.util.Date;

public class AddLoanActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView txtName;
    EditText txtAmount, txtNote;

    Button btnAdd, btnCancel;

    LoanDAO loanDAO;
    PeopleDAO peopleDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);

        setVar();

        setEvent();
    }

    private void setVar() {
        txtName = findViewById(R.id.txtNameAddLoan);
        txtAmount = findViewById(R.id.txtAmountAddLoan);
        txtNote = findViewById(R.id.txtNoteAddLoan);

        btnAdd = findViewById(R.id.btnAddAddLoan);
        btnCancel = findViewById(R.id.btnCancelAddLoan);

        loanDAO = new LoanDAO(this);
        peopleDAO = new PeopleDAO(this);
    }

    private void setEvent() {
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.btnAddAddLoan:
                String name = txtName.getText().toString();
                int peopleId = peopleDAO.isPeopleExists(name);
                int amount = Integer.parseInt(txtAmount.getText().toString());
                String note = txtNote.getText().toString();

                if (peopleId < 0) {
                    peopleId = peopleDAO.addPeople(new PeopleDTO(0, name, -1));
                }

                loanDAO.addLoan(new LoanDTO(0, peopleId, amount, 0, Calendar.getInstance().getTime().toString(), note));
                finish();
                break;

            case R.id.btnCancelAddLoan:
                finish();
                break;
        }
    }
}
