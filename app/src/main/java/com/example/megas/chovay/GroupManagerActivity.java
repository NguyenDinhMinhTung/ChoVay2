package com.example.megas.chovay;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.megas.chovay.Adapter.DetailLoanAdapter;
import com.example.megas.chovay.Adapter.GroupManagerAdapter;
import com.example.megas.chovay.DAO.GroupDAO;
import com.example.megas.chovay.DTO.GroupDTO;

import java.util.List;

public class GroupManagerActivity extends AppCompatActivity implements View.OnClickListener {
    FloatingActionButton fabAdd;

    RecyclerView lstGroupManager;
    GroupManagerAdapter groupManagerAdapter;

    List<GroupDTO> groupDTOList;

    GroupDAO groupDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Group Manager");

        setVar();

        generateList();

        setEvent();
    }

    private void generateList() {
        groupDTOList = groupDAO.getListGroup();

        groupManagerAdapter = new GroupManagerAdapter(this, groupDTOList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        layoutManager.setOrientation(LinearLayout.VERTICAL);

        lstGroupManager.setLayoutManager(layoutManager);
        lstGroupManager.setAdapter(groupManagerAdapter);
    }

    private void setEvent() {
        fabAdd.setOnClickListener(this);
    }

    private void setVar() {
        fabAdd = findViewById(R.id.fabAddGroupManager);
        lstGroupManager = findViewById(R.id.lstGroupManager);

        groupDAO = new GroupDAO(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.fabAddGroupManager:
                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra("title", "Create Group");
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == 2) {
            int flag = data.getIntExtra("flag", 0);

            if (flag == 1) {
                String name = data.getStringExtra("name");

                groupDAO.addGroup(new GroupDTO(0, name));

                generateList();
            }
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
}
