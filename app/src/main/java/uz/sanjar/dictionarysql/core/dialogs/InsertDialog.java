package uz.sanjar.dictionarysql.core.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import uz.sanjar.dictionarysql.R;
import uz.sanjar.dictionarysql.core.database.Database;

public class InsertDialog extends Dialog {
    private EditText englishAdd, uzbekAdd, descriptionAdd;
    private Button cancelButton, saveButton;


    public InsertDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_input);
        loadViews();
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        loadActions();
    }

    private void loadViews() {
        englishAdd = findViewById(R.id.edit_text_english);
        uzbekAdd = findViewById(R.id.edit_text_uzbek);
        cancelButton = findViewById(R.id.cancel_dialog_btn);
        saveButton = findViewById(R.id.save_dialog_btn);
        descriptionAdd = findViewById(R.id.edit_text_description);
        englishAdd.requestFocus();
    }

    private void loadActions() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String english = englishAdd.getText().toString();
                String uzbek = uzbekAdd.getText().toString();
                String description = descriptionAdd.getText().toString();
                if (!english.isEmpty() && !uzbek.isEmpty()) {
                    Database.getDatabase().addWords(english, uzbek, description);
                    dismiss();
                } else {
                    Toast.makeText(getContext(), "Write both in English and Uzbek!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}

