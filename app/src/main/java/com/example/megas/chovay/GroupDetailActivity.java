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
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.megas.chovay.Adapter.GroupDetailAdapter;
import com.example.megas.chovay.Adapter.GroupManagerAdapter;
import com.example.megas.chovay.DAO.PeopleDAO;
import com.example.megas.chovay.DTO.GroupDTO;
import com.example.megas.chovay.DTO.PeopleDTO;

import java.io.Serializable;
import java.util.List;

public class GroupDetailActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton fabAdd;
    RecyclerView lstGroupDetail;
    GroupDetailAdapter groupDetailAdapter;

    List<PeopleDTO> peopleDTOList;

    PeopleDAO peopleDAO;

    int groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        groupId = getIntent().getIntExtra("groupId", 0);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Group Detail");

        setVar();

        generateList();

        setEvent();
    }

    private void generateList() {
        peopleDTOList = peopleDAO.getListPeopleByGroupId(groupId);

        groupDetailAdapter = new GroupDetailAdapter(this, peopleDTOList, new GroupDetailAdapter.Action() {
            @Override
            public void RefreshList() {
                generateList();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        layoutManager.setOrientation(LinearLayout.VERTICAL);

        lstGroupDetail.setLayoutManager(layoutManager);
        lstGroupDetail.setAdapter(groupDetailAdapter);
    }

    private void setVar() {
        fabAdd = findViewById(R.id.fabAddGroupDetail);
        lstGroupDetail = findViewById(R.id.lstGroupDetail);

        peopleDAO = new PeopleDAO(this);
    }

    private void setEvent() {
        fabAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.fabAddGroupDetail:
                Intent intent = new Intent(this, AddActivity.class);
                intent.putExtra("title", "Add Member");
                intent.putExtra("isHaveList", true);
                intent.putExtra("list", (Serializable) Util.listPeopleToStringArray(peopleDAO.getListPeople()));

                startActivityForResult(intent, 3);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == 2) {
            int flag = data.getIntExtra("flag", 0);

            if (flag == 1) {
                String name = data.getStringExtra("name");

                int id = peopleDAO.isPeopleExists(name);

                if (id < 0) {
                    peopleDAO.addPeople(new PeopleDTO(0, name, groupId));
                } else {
                    peopleDAO.setGroupId(id, groupId);
                }

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
