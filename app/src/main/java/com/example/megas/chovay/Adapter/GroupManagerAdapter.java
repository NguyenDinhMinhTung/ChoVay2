package com.example.megas.chovay.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.megas.chovay.DTO.GroupDTO;
import com.example.megas.chovay.GroupDetailActivity;
import com.example.megas.chovay.R;

import java.util.List;

public class GroupManagerAdapter extends RecyclerView.Adapter<GroupManagerAdapter.GroupManagerAdapterViewHolder> {

    List<GroupDTO> groupDTOList;
    Context context;

    public GroupManagerAdapter(Context context, List<GroupDTO> groupDTOList) {
        this.groupDTOList = groupDTOList;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupManagerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.layout_group_manager_item, viewGroup,false);

        return new GroupManagerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupManagerAdapterViewHolder groupManagerAdapterViewHolder, int i) {
        final GroupDTO groupDTO = groupDTOList.get(i);

        groupManagerAdapterViewHolder.txtOrder.setText((i + 1) + ".");
        groupManagerAdapterViewHolder.txtName.setText(groupDTO.getName());

        groupManagerAdapterViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GroupDetailActivity.class);
                intent.putExtra("groupId", groupDTO.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupDTOList.size();
    }

    public class GroupManagerAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrder, txtName;
        RelativeLayout layout;

        public GroupManagerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layoutGroupManagerItem);

            txtOrder = itemView.findViewById(R.id.txtOrderGroupManager);
            txtName = itemView.findViewById(R.id.txtGroupNameGroupManager);
        }
    }
}
