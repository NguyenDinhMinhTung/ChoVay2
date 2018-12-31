package com.example.megas.chovay.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.megas.chovay.DAO.PeopleDAO;
import com.example.megas.chovay.DTO.PeopleDTO;
import com.example.megas.chovay.R;

import java.util.List;

public class GroupDetailAdapter extends RecyclerView.Adapter<GroupDetailAdapter.GroupDetailAdapterViewHolder> {

    List<PeopleDTO> peopleDTOList;
    Context context;

    PeopleDAO peopleDAO;
    Action action;

    public GroupDetailAdapter(Context context, List<PeopleDTO> peopleDTOList, Action action) {
        this.peopleDTOList = peopleDTOList;
        this.context = context;
        this.action = action;

        peopleDAO = new PeopleDAO(context);
    }

    @NonNull
    @Override
    public GroupDetailAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_group_detail_item, viewGroup, false);

        return new GroupDetailAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupDetailAdapterViewHolder groupDetailAdapterViewHolder, int i) {
        final PeopleDTO peopleDTO = peopleDTOList.get(i);
        groupDetailAdapterViewHolder.txtOrder.setText((i + 1) + ".");
        groupDetailAdapterViewHolder.txtName.setText(peopleDTO.getName());

        groupDetailAdapterViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peopleDAO.setGroupId(peopleDTO.getId(), -1);
                action.RefreshList();
            }
        });
    }

    @Override
    public int getItemCount() {
        return peopleDTOList.size();
    }

    public class GroupDetailAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrder, txtName;
        Button btnDelete;

        public GroupDetailAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            txtOrder = itemView.findViewById(R.id.txtOrderGroupDetail);
            txtName = itemView.findViewById(R.id.txtGroupNameGroupDetail);
            btnDelete = itemView.findViewById(R.id.btnDeleteGroupDetail);
        }
    }

    public interface Action {
        public void RefreshList();
    }
}
