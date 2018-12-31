package com.example.megas.chovay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView txtInputText;
    Button btnOK, btnCancel;

    Boolean isHaveList = false;
    String[] list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        String title = getIntent().getStringExtra("title");
        setTitle(title);

        isHaveList = getIntent().getBooleanExtra("isHaveList", false);

        if (isHaveList) {
            list = (String[]) getIntent().getSerializableExtra("list");
        }

        setVar();
        setEvent();
    }

    private void setVar() {
        txtInputText = findViewById(R.id.txtInputText);
        btnOK = findViewById(R.id.btnOkInputText);
        btnCancel = findViewById(R.id.btnCancelInputText);
    }

    private void setEvent() {
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        if (isHaveList)
            txtInputText.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId) {
            case R.id.btnOkInputText:
                String name = txtInputText.getText().toString();

                if (name.isEmpty()) {
                    Toast.makeText(this, "入力してください", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("flag", 1);
                    intent.putExtra("name", name);
                    setResult(2, intent);

                    finish();
                }
                break;

            case R.id.btnCancelInputText:
                Intent intent = new Intent();
                intent.putExtra("flag", 0);
                setResult(2, intent);

                finish();
                break;
        }
    }
}
