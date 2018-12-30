package com.example.megas.chovay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
EditText txtInputText;
Button btnOK, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        setTitle("Input");

        setVar();
        setEvent();
    }

    private void setVar(){
        txtInputText=findViewById(R.id.txtInputText);
        btnOK=findViewById(R.id.btnOkInputText);
        btnCancel=findViewById(R.id.btnCancelInputText);
    }

    private void setEvent(){
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId=view.getId();

        switch (viewId){
            case R.id.btnOkInputText:
                break;

            case R.id.btnCancelInputText:
                finish();
                break;
        }
    }
}
