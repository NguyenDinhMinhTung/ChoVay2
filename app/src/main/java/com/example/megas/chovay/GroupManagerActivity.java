package com.example.megas.chovay;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GroupManagerActivity extends AppCompatActivity implements View.OnClickListener {
FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_manager);

        setVar();

        setEvent();
    }

    private void setEvent() {
        fabAdd.setOnClickListener(this);
    }

    private void setVar(){
        fabAdd=findViewById(R.id.fabAddGroupManager);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();

        switch (viewId){
            case R.id.fabAddGroupManager:
                Intent intent=new Intent(this, AddActivity.class);
                startActivity(intent);
                break;
        }
    }
}
