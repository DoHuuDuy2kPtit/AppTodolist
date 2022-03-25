package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText titleList;
    private ImageButton btnCancel, btnSave;
    private ImageButton btnAddList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddList = (ImageButton) findViewById(R.id.btnAddList);

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewListDialog();
            }
        });
    }

    public void createNewListDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUpAddList = getLayoutInflater().inflate(R.layout.popup_add_list, null);

        titleList = (EditText) popUpAddList.findViewById(R.id.titleList);
        btnCancel = (ImageButton) popUpAddList.findViewById(R.id.btnCancel);
        btnSave = (ImageButton) popUpAddList.findViewById(R.id.btnSave);

        dialogBuilder.setView(popUpAddList);
        dialog = dialogBuilder.create();
        dialog.show();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}