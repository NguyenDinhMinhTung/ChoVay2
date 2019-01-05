package com.example.megas.chovay;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

    Button btnPaid;

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

        for (int i = 0; i < peopleDTOList.size(); i++) {
            PeopleDTO peopleDTO = peopleDTOList.get(i);

            List<LoanDTO> list = loanDAO.getListLoanByPeopleId(peopleDTO.getId(), 0);
            if (list.size() > 0)
                loanDTOList.add(list);
            else {
                peopleDTOList.remove(peopleDTO);
                i--;
            }
        }

        mainLoanAdapter = new MainLoanAdapter(loanDTOList, peopleDTOList, this, new MainLoanAdapter.Action() {
            @Override
            public void RefreshPay() {
                refreshPay();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        layoutManager.setOrientation(LinearLayout.VERTICAL);

        lstMainLoan.setLayoutManager(layoutManager);
        lstMainLoan.setAdapter(mainLoanAdapter);

        setSum();
    }

    private void refreshPay() {
        int sum = 0;

        for (int i = 0; i < mainLoanAdapter.checkBoxState.length; i++) {
            if (mainLoanAdapter.checkBoxState[i] != null && mainLoanAdapter.checkBoxState[i]) {
                sum += mainLoanAdapter.sum[i];
            }
        }

        if (sum == 0)
            btnPaid.setEnabled(false);
        else
            btnPaid.setEnabled(true);

        btnPaid.setText("支払済み（" + sum + "円）");
    }

    private void setSum() {
        int sum = 0;

        for (List<LoanDTO> listLoan : loanDTOList) {
            for (LoanDTO loanDTO : listLoan) {
                sum += loanDTO.getAmount();
            }
        }
        txtSum.setText("合計：" + sum + "円");
    }

    private void setVar() {
        lstMainLoan = findViewById(R.id.lstMainLoan);
        fabAdd = findViewById(R.id.fabAddMainLoan);
        txtSum = findViewById(R.id.txtSumMainLoan);
        btnPaid = findViewById(R.id.btnPaidMainLoan);

        loanDAO = new LoanDAO(this);
        peopleDAO = new PeopleDAO(this);
    }

    private void setEvent() {
        fabAdd.setOnClickListener(this);
        btnPaid.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fabAddMainLoan:
                Intent intent = new Intent(this, AddLoanActivity.class);
                startActivity(intent);
                break;

            case R.id.btnPaidMainLoan:
                List<PeopleDTO> remove = new ArrayList<>();

                for (int i = 0; i < mainLoanAdapter.checkBoxState.length; i++) {
                    if (mainLoanAdapter.checkBoxState[i]) {
                        remove.add(peopleDTOList.get(i));
                        mainLoanAdapter.checkBoxState[i]=false;
                    }
                }

                for (PeopleDTO peopleDTO:remove){
                    loanDAO.setPaidByPeopleId(peopleDTO.getId());
                }

                generateList();
                refreshPay();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        generateList();
        refreshPay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuGroup:
                Intent intent = new Intent(this, GroupManagerActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
