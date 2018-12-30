package com.example.megas.chovay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.megas.chovay.DAO.GroupDAO;
import com.example.megas.chovay.DAO.LoanDAO;
import com.example.megas.chovay.DAO.PeopleDAO;
import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.DTO.PeopleDTO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddLoanActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    AutoCompleteTextView txtName;
    EditText txtAmount, txtNote;

    Button btnAdd, btnCancel;

    RadioGroup radioGroup;

    Spinner spnGroupName;

    LoanDAO loanDAO;
    PeopleDAO peopleDAO;
    GroupDAO groupDAO;

    List<PeopleDTO> peopleDTOList;

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

        spnGroupName=findViewById(R.id.spnGroupNameAddLoan);

        radioGroup=findViewById(R.id.radioGroupAddLoan);

        loanDAO = new LoanDAO(this);
        peopleDAO = new PeopleDAO(this);
        groupDAO=new GroupDAO(this);

        peopleDTOList= peopleDAO.getListPeople();
    }

    private String[] listToStringArray(List<PeopleDTO> list){
        String[] result=new String[list.size()];

        for (int i=0;i<list.size();i++){
            result[i]=list.get(i).getName();
        }

        return result;
    }

    private void setEvent() {
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(this);

        txtName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listToStringArray(peopleDTOList)));
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        int checkedRadioId=radioGroup.getCheckedRadioButtonId();

        switch (checkedRadioId){
            case R.id.rdbPeopleAddLoan:

                break;

            case R.id.rdbGroupAddLoan:
                break;
        }
    }
}
