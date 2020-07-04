package com.maks.durov.testapplication.ui;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.maks.durov.testapplication.R;
import com.maks.durov.testapplication.model.Contact;

public class ContactDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        Contact contact = (Contact) getIntent().getParcelableExtra("contact");
        ImageView imageView = findViewById(R.id.detail_contact_image);
        TextView phoneNumberTextView = findViewById(R.id.detail_phone_number);
        TextView nameTextView = findViewById(R.id.detail_name);
        TextView ageTextView = findViewById(R.id.detail_age);
        TextView emailTextView = findViewById(R.id.detail_email);
        if (contact != null) {
            phoneNumberTextView.setText(contact.getPhoneNumber());
            phoneNumberTextView.setTextColor(Color.BLACK);
            nameTextView.setText(contact.getFirstName() + " " + contact.getLastName());
            nameTextView.setTextColor(Color.BLACK);
            ageTextView.setText(contact.getAge() + " years old");
            emailTextView.setText(contact.getEmail());
            emailTextView.setTextColor(Color.BLACK);
            Glide.with(this).load(contact.getBigImageUrl()).circleCrop()
                    .into(imageView);
        }
    }
}
