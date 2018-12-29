package com.example.megas.chovay.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.DTO.PeopleDTO;
import com.example.megas.chovay.R;

import java.util.List;

public class MainLoanAdapter extends RecyclerView.Adapter<MainLoanAdapter.MainLoanAdapterViewHolder> {

    List<List<LoanDTO>> loanDTOList;
    List<PeopleDTO> peopleDTOList;

    public MainLoanAdapter(List<List<LoanDTO>> loanDTOList, List<PeopleDTO> peopleDTOList) {
        this.loanDTOList = loanDTOList;
        this.peopleDTOList = peopleDTOList;
    }

    @NonNull
    @Override
    public MainLoanAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.layout_main_loan_item, viewGroup, false);

        return new MainLoanAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainLoanAdapterViewHolder mainLoanAdapterViewHolder, int i) {
        List<LoanDTO> lstLoanDTO = loanDTOList.get(i);
        PeopleDTO peopleDTO=peopleDTOList.get(i);

        if (lstLoanDTO.size() > 0) {
            int amount=getSum(lstLoanDTO);

            mainLoanAdapterViewHolder.txtName.setText(peopleDTO.getName());
            mainLoanAdapterViewHolder.txtAmount.setText(amount + "円");
            mainLoanAdapterViewHolder.checkBox.setChecked(false);
        } else {
            mainLoanAdapterViewHolder.layout.setVisibility(View.GONE);
        }
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
}
