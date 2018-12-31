package com.example.megas.chovay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.megas.chovay.DAO.GroupDAO;
import com.example.megas.chovay.DAO.LoanDAO;
import com.example.megas.chovay.DAO.PeopleDAO;
import com.example.megas.chovay.DTO.GroupDTO;
import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.DTO.PeopleDTO;

import java.util.Calendar;
import java.util.List;

public class AddLoanActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    AutoCompleteTextView txtName;
    EditText txtAmount, txtNote;

    Button btnAdd, btnCancel;

    RadioGroup radioGroup;
    RadioButton rdbPeople, rdbGroup;

    Spinner spnGroupName;

    LoanDAO loanDAO;
    PeopleDAO peopleDAO;
    GroupDAO groupDAO;

    List<PeopleDTO> peopleDTOList;
    List<GroupDTO> groupDTOList;

    boolean isRadioButtonPeopleSelected = true;
    boolean isEdit = false;
    int loanId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Add");

        setVar();

       checkIsEdit();

        setEvent();
    }

    private void checkIsEdit(){
        isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            loanId = getIntent().getIntExtra("loanId", -1);
            if (loanId > 0) {
                LoanDTO loanDTO = loanDAO.getLoanById(loanId);
                txtName.setText(peopleDAO.getPeopleNameById(loanDTO.getPeopleId()));
                txtAmount.setText(String.valueOf(loanDTO.getAmount()));
                txtNote.setText(loanDTO.getNote());

                txtName.setEnabled(false);
                spnGroupName.setEnabled(false);
                rdbPeople.setEnabled(false);
                rdbGroup.setEnabled(false);
            }
        }
    }

    private void setVar() {
        txtName = findViewById(R.id.txtNameAddLoan);
        txtAmount = findViewById(R.id.txtAmountAddLoan);
        txtNote = findViewById(R.id.txtNoteAddLoan);

        btnAdd = findViewById(R.id.btnAddAddLoan);
        btnCancel = findViewById(R.id.btnCancelAddLoan);

        spnGroupName = findViewById(R.id.spnGroupNameAddLoan);
        spnGroupName.setEnabled(false);

        radioGroup = findViewById(R.id.radioGroupAddLoan);
        rdbGroup=findViewById(R.id.rdbGroupAddLoan);
        rdbPeople=findViewById(R.id.rdbPeopleAddLoan);

        loanDAO = new LoanDAO(this);
        peopleDAO = new PeopleDAO(this);
        groupDAO = new GroupDAO(this);

        peopleDTOList = peopleDAO.getListPeople();
        groupDTOList = groupDAO.getListGroup();
    }

    private void setEvent() {
        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        radioGroup.setOnCheckedChangeListener(this);

        txtName.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Util.listPeopleToStringArray(peopleDTOList)));

        ArrayAdapter spinAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, Util.listGroupToStringArray(groupDTOList));
        spinAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnGroupName.setAdapter(spinAdapter);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.btnAddAddLoan:
                int amount = Integer.parseInt(txtAmount.getText().toString());
                String note = txtNote.getText().toString();

                if (isEdit) {
                    loanDAO.update(loanId, amount, note);

                } else if (isRadioButtonPeopleSelected) {
                    String name = txtName.getText().toString();
                    int peopleId = peopleDAO.isPeopleExists(name);

                    if (peopleId < 0) {
                        peopleId = peopleDAO.addPeople(new PeopleDTO(0, name, -1));
                    }

                    loanDAO.addLoan(new LoanDTO(0, peopleId, amount, 0, Calendar.getInstance().getTime().toString(), note));
                } else {
                    GroupDTO groupDTO = groupDTOList.get(spnGroupName.getSelectedItemPosition());
                    List<PeopleDTO> list = peopleDAO.getListPeopleByGroupId(groupDTO.getId());

                    for (PeopleDTO peopleDTO : list) {
                        loanDAO.addLoan(new LoanDTO(0, peopleDTO.getId(), amount, 0, Calendar.getInstance().getTime().toString(), note));
                    }
                }
                finish();
                break;

            case R.id.btnCancelAddLoan:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        int checkedRadioId = radioGroup.getCheckedRadioButtonId();

        switch (checkedRadioId) {
            case R.id.rdbPeopleAddLoan:
                isRadioButtonPeopleSelected = true;
                spnGroupName.setEnabled(false);
                txtName.setEnabled(true);
                break;

            case R.id.rdbGroupAddLoan:
                isRadioButtonPeopleSelected = false;
                spnGroupName.setEnabled(true);
                txtName.setEnabled(false);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
