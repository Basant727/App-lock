package com.example.applocker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LockActivity extends AppCompatActivity {
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        passwordField = findViewById(R.id.passwordField);

        Button unlockButton = findViewById(R.id.unlockButton);
        unlockButton.setOnClickListener(v -> {
            if (passwordField.getText().toString().equals("1234")) {
                finish();
            } else {
                Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // Disable back press
    }
}