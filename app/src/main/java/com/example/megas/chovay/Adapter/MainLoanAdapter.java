package com.example.megas.chovay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.DTO.PeopleDTO;
import com.example.megas.chovay.DetailLoanActivity;
import com.example.megas.chovay.R;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class MainLoanAdapter extends RecyclerView.Adapter<MainLoanAdapter.MainLoanAdapterViewHolder> {

    List<List<LoanDTO>> loanDTOList;
    List<PeopleDTO> peopleDTOList;
    Context context;

    Action action;

    public Boolean[] checkBoxState;
    public int[] sum;

    public MainLoanAdapter(List<List<LoanDTO>> loanDTOList, List<PeopleDTO> peopleDTOList, Context context, Action action) {
        this.loanDTOList = loanDTOList;
        this.peopleDTOList = peopleDTOList;
        this.context = context;
        this.action = action;

        checkBoxState = new Boolean[loanDTOList.size()];
        sum = new int[loanDTOList.size()];
    }

    @NonNull
    @Override
    public MainLoanAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_main_loan_item, viewGroup, false);

        return new MainLoanAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainLoanAdapterViewHolder mainLoanAdapterViewHolder, final int i) {
        final List<LoanDTO> lstLoanDTO = loanDTOList.get(i);
        final PeopleDTO peopleDTO = peopleDTOList.get(i);

        checkBoxState[i] = false;

        sum[i] = getSum(lstLoanDTO);

        mainLoanAdapterViewHolder.txtName.setText(peopleDTO.getName());
        mainLoanAdapterViewHolder.txtAmount.setText(sum[i] + "円");
        mainLoanAdapterViewHolder.checkBox.setChecked(false);

        mainLoanAdapterViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailLoanActivity.class);
                intent.putExtra("peopleId", peopleDTO.getId());
                intent.putExtra("name", peopleDTO.getName());

                context.startActivity(intent);
            }
        });

        mainLoanAdapterViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxState[i] = isChecked;
                action.RefreshPay();
            }
        });
    }

    public int getSum(List<LoanDTO> loanDTOList) {
        int sum = 0;

        for (LoanDTO loanDTO : loanDTOList) {
            sum += loanDTO.getAmount();
        }

        return sum;
    }

    @Override
    public int getItemCount() {
        return loanDTOList.size();
    }

    public class MainLoanAdapterViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        CheckBox checkBox;
        TextView txtName;
        TextView txtAmount;

        public MainLoanAdapterViewHolder(View view) {
            super(view);

            layout = view.findViewById(R.id.layoutMainLoanItem);
            checkBox = view.findViewById(R.id.ckbMainLoanItem);
            txtName = view.findViewById(R.id.txtPeopleNameMainLoanItem);
            txtAmount = view.findViewById(R.id.txtAmountMainLoanItem);
        }
    }

    public interface Action {
        public void RefreshPay();
    }
}
