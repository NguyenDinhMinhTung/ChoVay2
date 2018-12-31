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

import com.example.megas.chovay.AddLoanActivity;
import com.example.megas.chovay.DTO.LoanDTO;
import com.example.megas.chovay.R;

import java.util.List;

public class DetailLoanAdapter extends RecyclerView.Adapter<DetailLoanAdapter.DetailLoanAdapterViewHolder> {

    List<LoanDTO> loanDTOList;

    Action action;
    Context context;

    public Boolean[] checkBoxState;

    public DetailLoanAdapter(Context context, List<LoanDTO> loanDTOList, Action action) {
        this.loanDTOList = loanDTOList;
        this.action = action;
        this.context = context;

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
        final LoanDTO loanDTO = loanDTOList.get(i);

        detailLoanAdapterViewHolder.txtAmount.setText(loanDTO.getAmount() + "円");
        detailLoanAdapterViewHolder.txtNote.setText(loanDTO.getNote());
        detailLoanAdapterViewHolder.checkBox.setChecked(false);
        checkBoxState[i] = false;

        detailLoanAdapterViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkBoxState[i] = isChecked;

                action.RefreshPay();
            }
        });

        detailLoanAdapterViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddLoanActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("loanId", loanDTO.getId());
                context.startActivity(intent);
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
        RelativeLayout layout;

        public DetailLoanAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layoutDetailLoanItem);
            checkBox = itemView.findViewById(R.id.ckbDetailLoanItem);
            txtNote = itemView.findViewById(R.id.txtNoteDetailLoanItem);
            txtAmount = itemView.findViewById(R.id.txtAmountDetailLoanItem);
        }
    }

    public interface Action {
        public void RefreshPay();
    }
}
