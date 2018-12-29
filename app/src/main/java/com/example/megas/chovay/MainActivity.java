package com.example.megas.chovay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.megas.chovay.Adapter.MainLoanAdapter;
import com.example.megas.chovay.DAO.LoanDAO;
import com.example.megas.chovay.DAO.PeopleDAO;
import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.DTO.PeopleDTO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView lstMainLoan;
    MainLoanAdapter mainLoanAdapter;
    List<MainLoanItem> mainLoanItemList;
    List<List<LoanDTO>> loanDTOList;

    LoanDAO loanDAO;
    PeopleDAO peopleDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVar();

        //loanDAO.addLoan(new LoanDTO(0, 1, 10000, 0, "", "TIEN NUOC"));
        //loanDAO.addLoan(new LoanDTO(0, 2, 1230, 0, "", "dsd"));

        generateList();

//        peopleDAO.addPeople(new PeopleDTO(0, "TUNG", -1));
//        peopleDAO.addPeople(new PeopleDTO(0, "HIEU", -1));
    }

    private void generateList() {
        List<PeopleDTO> peopleDTOList = peopleDAO.getListPeople();
        loanDTOList=new ArrayList<>();

        for (PeopleDTO peopleDTO : peopleDTOList) {
                List<LoanDTO> list=loanDAO.getListLoanByPeopleId(peopleDTO.getId(),0);
                loanDTOList.add(list);
        }

        mainLoanAdapter = new MainLoanAdapter(loanDTOList, peopleDTOList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        layoutManager.setOrientation(LinearLayout.VERTICAL);

        lstMainLoan.setLayoutManager(layoutManager);
        lstMainLoan.setAdapter(mainLoanAdapter);
    }

    private void setVar() {
        lstMainLoan = findViewById(R.id.lstMainLoan);

        loanDAO = new LoanDAO(this);
        peopleDAO = new PeopleDAO(this);
    }
}
