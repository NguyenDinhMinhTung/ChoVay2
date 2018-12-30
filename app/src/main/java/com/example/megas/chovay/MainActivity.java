package com.example.megas.chovay;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.megas.chovay.Adapter.MainLoanAdapter;
import com.example.megas.chovay.DAO.LoanDAO;
import com.example.megas.chovay.DAO.PeopleDAO;
import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.DTO.PeopleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView lstMainLoan;
    MainLoanAdapter mainLoanAdapter;
    List<List<LoanDTO>> loanDTOList;
    List<PeopleDTO> peopleDTOList;
    TextView txtSum;

    FloatingActionButton fabAdd;

    LoanDAO loanDAO;
    PeopleDAO peopleDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVar();
        generateList();
        setEvent();
    }

    private void generateList() {
        peopleDTOList = peopleDAO.getListPeople();
        loanDTOList = new ArrayList<>();

        for (PeopleDTO peopleDTO : peopleDTOList) {
            List<LoanDTO> list = loanDAO.getListLoanByPeopleId(peopleDTO.getId(), 0);
            loanDTOList.add(list);
        }

        mainLoanAdapter = new MainLoanAdapter(loanDTOList, peopleDTOList,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        layoutManager.setOrientation(LinearLayout.VERTICAL);

        lstMainLoan.setLayoutManager(layoutManager);
        lstMainLoan.setAdapter(mainLoanAdapter);

        setSum();
    }

    private void setSum() {
        int sum = 0;

        for (List<LoanDTO> listLoan : loanDTOList) {
            for (LoanDTO loanDTO : listLoan) {
                sum += loanDTO.getAmount();
            }
        }
        txtSum.setText("合計：" + sum+"円");
    }

    private void setVar() {
        lstMainLoan = findViewById(R.id.lstMainLoan);
        fabAdd = findViewById(R.id.fabAddMainLoan);
        txtSum = findViewById(R.id.txtSumMainLoan);

        loanDAO = new LoanDAO(this);
        peopleDAO = new PeopleDAO(this);
    }

    private void setEvent() {
        fabAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fabAddMainLoan:
                Intent intent = new Intent(this, AddLoanActivity.class);
                intent.putExtra("people_name_list", (Serializable) peopleDTOList);
                startActivity(intent);

                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        generateList();
    }
}
