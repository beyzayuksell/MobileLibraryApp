package com.beyzayuksel.libraryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

// Contsruct logic in this class
// So, this mean is that this class Associate All class(RoomDB.java, MainData.java, loyouts)
public class MainActivity extends AppCompatActivity {

    // Initialize variable
    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    // Adapter for recycler view ui.
    MainAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Process -> Assign Variable
        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);

        // Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDao().getAll();

        // Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        // Initialize adapter -- Define which adapter will use which activity. In other words adapter depend on activity
        adapter = new MainAdapter(MainActivity.this, dataList);
        // Set adapter
        recyclerView.setAdapter(adapter);

        // Add Button listener
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get string from edit text(user's entered text)
                String sText = editText.getText().toString().trim();
                // Check condition
                if(!sText.equals("")) {
                    // When data = new MainData();
                    // Initialize main data
                    MainData d = new MainData();
                    // Set text on main data
                    d.setText(sText);
                    // Insert text in database
                    database.mainDao().insert(d);
                    // Clear edit text
                    editText.setText("");
                    // Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();

                }
            }
        }); // end of add btn

        // Reset Button listener
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete all data from database
                database.mainDao().reset(dataList);
                // Notify when all data deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        }); // end of delete btn


    }
}