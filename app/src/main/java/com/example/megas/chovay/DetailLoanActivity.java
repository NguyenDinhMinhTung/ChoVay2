package com.example.megas.chovay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.megas.chovay.Adapter.DetailLoanAdapter;
import com.example.megas.chovay.DTO.LoanDTO;

import org.w3c.dom.Text;

import java.util.List;

public class DetailLoanActivity extends AppCompatActivity {
    RecyclerView lstDetailLoan;
    List<LoanDTO> loanDTOList;

    TextView txtSum;

    DetailLoanAdapter detailLoanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_loan);

        setVar();

        generateList();
    }

    private void setVar() {
        lstDetailLoan = findViewById(R.id.lstDetailLoan);
        txtSum = findViewById(R.id.txtSumDetailLoan);

        loanDTOList = (List<LoanDTO>) getIntent().getSerializableExtra("list");
    }

    private void generateList() {

        detailLoanAdapter = new DetailLoanAdapter(loanDTOList);
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
}
