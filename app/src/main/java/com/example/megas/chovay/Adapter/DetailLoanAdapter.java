package com.example.megas.chovay.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.R;

import java.util.List;

public class DetailLoanAdapter extends RecyclerView.Adapter<DetailLoanAdapter.DetailLoanAdapterViewHolder> {

    List<LoanDTO> loanDTOList;

    public Boolean[] checkBoxState;

    public DetailLoanAdapter(List<LoanDTO> loanDTOList) {
        this.loanDTOList = loanDTOList;

        checkBoxState = new Boolean[loanDTOList.size()];
    }

    @NonNull
    @Override
    public DetailLoanAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_detail_loan_item, viewGroup, false);

        return new DetailLoanAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailLoanAdapterViewHolder detailLoanAdapterViewHolder, final int i) {
        LoanDTO loanDTO = loanDTOList.get(i);

        detailLoanAdapterViewHolder.txtAmount.setText(loanDTO.getAmount() + "円");
        detailLoanAdapterViewHolder.txtNote.setText(loanDTO.getNote());
        detailLoanAdapterViewHolder.checkBox.setChecked(false);
        checkBoxState[i] = false;

        detailLoanAdapterViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxState[i]=isChecked;
            }
        });
    }

    @Override
    public int getItemCount() {
        return loanDTOList.size();
    }

    public class DetailLoanAdapterViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView txtNote;
        TextView txtAmount;

        public DetailLoanAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.ckbDetailLoanItem);
            txtNote = itemView.findViewById(R.id.txtNoteDetailLoanItem);
            txtAmount = itemView.findViewById(R.id.txtAmountDetailLoanItem);
        }
    }
}
