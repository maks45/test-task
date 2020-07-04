package com.maks.durov.testapplication.ui;

import android.content.Intent;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import com.maks.durov.testapplication.R;
import com.maks.durov.testapplication.adapter.RecyclerViewContactAdapter;
import com.maks.durov.testapplication.model.Contact;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int RESULTS_PER_PAGE = 20;
    private int currentPage = 1;
    private List<Contact> contacts;
    private RecyclerViewContactAdapter recyclerViewContactAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contacts = new ArrayList<>();
        recyclerViewContactAdapter = new RecyclerViewContactAdapter(contacts, this);
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setAdapter(recyclerViewContactAdapter);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        new LoadPageTask().execute(1);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(layoutManager.findFirstVisibleItemPosition() + layoutManager.getChildCount()
                        > contacts.size() - 3) {
                    new LoadPageTask().execute(++currentPage);
                }
            }
        });
        recyclerViewContactAdapter.setOnClick(this::startDetailActivity);
    }

    private void startDetailActivity(Contact contact) {
        Intent intent = new Intent(MainActivity.this, ContactDetailActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    private class LoadPageTask extends AsyncTask<Integer, Void, List<Contact>> {
        private static final String BASE_URL = "https://randomuser.me/api?results=20&page=%d";

        @SneakyThrows
        @Override
        protected List<Contact> doInBackground(Integer... integers) {
            StringBuilder stringBuilder = new StringBuilder();
            List<Contact> result = new ArrayList<>();
            URL google = new URL(String.format(BASE_URL, integers[0]));
            BufferedReader in = new BufferedReader(new InputStreamReader(google.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                stringBuilder.append(inputLine);
            }
            in.close();
            JSONObject jsonObject = new JSONObject(stringBuilder.toString());
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < RESULTS_PER_PAGE; i++) {
                JSONObject jsonContact = (JSONObject) results.get(i);
                Contact contact = new Contact();
                contact.setAge(((JSONObject) jsonContact.get("dob")).getInt("age"));
                contact.setFirstName(((JSONObject) jsonContact.get("name"))
                         .getString("first"));
                contact.setLastName(((JSONObject) jsonContact.get("name"))
                         .getString("last"));
                contact.setImageUrl(((JSONObject) jsonContact.get("picture"))
                        .getString("thumbnail"));
                contact.setBigImageUrl(((JSONObject) jsonContact.get("picture"))
                        .getString("large"));
                contact.setEmail(jsonContact.getString("email"));
                contact.setPhoneNumber(jsonContact.getString("cell"));
                result.add(contact);
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<Contact> results) {
            super.onPostExecute(results);
            contacts.addAll(results);
            recyclerViewContactAdapter.notifyDataSetChanged();
        }
    }
}
