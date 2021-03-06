package com.example.megas.chovay;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.megas.chovay.Adapter.DetailLoanAdapter;
import com.example.megas.chovay.DAO.LoanDAO;
import com.example.megas.chovay.DTO.LoanDTO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class DetailLoanActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView lstDetailLoan;
    List<LoanDTO> loanDTOList;

    FloatingActionButton fabAdd;

    Button btnPaid;

    TextView txtSum;

    DetailLoanAdapter detailLoanAdapter;

    LoanDAO loanDAO;

    int peopleId;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_loan);

        name = getIntent().getStringExtra("name");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(name);

        setVar();

        generateList();

        setEvent();
    }

    private void setVar() {
        lstDetailLoan = findViewById(R.id.lstDetailLoan);
        txtSum = findViewById(R.id.txtSumDetailLoan);
        btnPaid = findViewById(R.id.btnPaidDetailLoan);
        fabAdd = findViewById(R.id.fabAddDetailLoan);

        loanDAO = new LoanDAO(this);

        peopleId = getIntent().getIntExtra("peopleId", 0);
    }

    private void setEvent() {
        btnPaid.setOnClickListener(this);
        fabAdd.setOnClickListener(this);
    }

    public void refreshPay() {
        int sum = 0;
        for (int i = 0; i < detailLoanAdapter.checkBoxState.length; i++) {
            if (detailLoanAdapter.checkBoxState[i] != null && detailLoanAdapter.checkBoxState[i]) {
                sum += loanDTOList.get(i).getAmount();
            }
        }

        if (sum == 0)
            btnPaid.setEnabled(false);
        else
            btnPaid.setEnabled(true);

        btnPaid.setText("支払済み（" + sum + "円）");
    }

    private void generateList() {

        loanDTOList = loanDAO.getListLoanByPeopleId(peopleId, 0);
        detailLoanAdapter = new DetailLoanAdapter(this, loanDTOList, new DetailLoanAdapter.Action() {
            @Override
            public void RefreshPay() {
                refreshPay();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        layoutManager.setOrientation(LinearLayout.VERTICAL);

        lstDetailLoan.setLayoutManager(layoutManager);
        lstDetailLoan.setAdapter(detailLoanAdapter);

        setSum();
    }

    private void setSum() {
        int sum = 0;

        for (LoanDTO loanDTO : loanDTOList) {
            sum += loanDTO.getAmount();
        }

        txtSum.setText("合計：" + sum + "円");
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId) {
            case R.id.btnPaidDetailLoan:
                List<LoanDTO> remove = new ArrayList<>();

                for (int i = 0; i < detailLoanAdapter.checkBoxState.length; i++) {
                    if (detailLoanAdapter.checkBoxState[i] != null && detailLoanAdapter.checkBoxState[i]) {
                        detailLoanAdapter.checkBoxState[i] = false;
                        remove.add(loanDTOList.get(i));
                    }
                }

                for (LoanDTO loanDTO : remove) {
                    loanDAO.setPaidByLoanId(loanDTO.getId());
                    loanDTOList.remove(loanDTO);
                }

                generateList();
                refreshPay();
                break;

            case R.id.fabAddDetailLoan:
                Intent intent = new Intent(this, AddLoanActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
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

    @Override
    protected void onResume() {
        super.onResume();

        generateList();
        refreshPay();
    }
}
