package com.example.megas.chovay;

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

import java.util.List;

public class DetailLoanActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView lstDetailLoan;
    List<LoanDTO> loanDTOList;

    Button btnPaid;

    TextView txtSum;

    DetailLoanAdapter detailLoanAdapter;

    LoanDAO loanDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_loan);

        String name =getIntent().getStringExtra("name");
        
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

        loanDAO =new LoanDAO(this);

        loanDTOList = (List<LoanDTO>) getIntent().getSerializableExtra("list");
    }

    private void setEvent(){
        btnPaid.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        int viewId=v.getId();

        switch (viewId){
            case R.id.btnPaidDetailLoan:
                for (int i=0;i<detailLoanAdapter.checkBoxState.length;i++){
                    if (detailLoanAdapter.checkBoxState[i]){
                        loanDAO.setPaidByLoanId(loanDTOList.get(i).getId());
                        loanDTOList.remove(i);
                    }
                }

                generateList();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
