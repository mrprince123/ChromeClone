package com.example.dhromebrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FeedbackActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseApp;
    EditText namefield, emailfield, messagefield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().hide();
        namefield = findViewById(R.id.name_field);
        emailfield = findViewById(R.id.email_field);
        messagefield = findViewById(R.id.message_field);
    }

    public void sendFeedbackMessage(View view){

        String nameField = namefield.getText().toString();
        String emailField = emailfield.getText().toString();
        String messageField = messagefield.getText().toString();

        if(nameField.equals("") || emailField.equals("") || messageField.equals("")){
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String, Object> m = new HashMap<>();
            m.put("UserName", nameField);
            m.put("UserEmail", emailField);
            m.put("UserFeedback", messageField);

            FirebaseDatabase.getInstance().getReference().child("Feedback").push().setValue(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(FeedbackActivity.this, "Feedback Send Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FeedbackActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}