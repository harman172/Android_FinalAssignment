package com.example.harman_fa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    List<PersonModel> personList;
    List<PersonModel> searchList;

    SwipeMenuListView listView;
    public static final String SELECTED_PERSON = "selectedPerson";

    PersonAdapter personAdapter;
    TextView tvNoItem;
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAddPerson = findViewById(R.id.btn_add);
        tvNoItem = findViewById(R.id.tv_no_list);
        listView = findViewById(R.id.listview);
        etSearch = findViewById(R.id.et_search);

        personList = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(this);

        loadAllPersons();

        btnAddPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewPerson.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: ");
                Intent intent = new Intent(MainActivity.this, AddNewPerson.class);

                if (!etSearch.getText().toString().isEmpty()) {

                    //when user is searching data .
                    intent.putExtra(SELECTED_PERSON, searchList.get(position));
                } else {

                    //no text in search field by user.
                    intent.putExtra(SELECTED_PERSON, personList.get(position));
                }


                startActivity(intent);
            }
        });


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchList = new ArrayList<>();

                String text = s.toString();
                if (s.length() != 0) {

                    for (PersonModel personModel : personList) {
                        if (personModel.getfName().contains(text) || personModel.getlName().contains(text)) {

                            searchList.add(personModel);
                        }
                    }
                } else {
                    searchList.addAll(personList);
                }
                personAdapter = new PersonAdapter(MainActivity.this, searchList);
                listView.setAdapter(personAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem delete = new SwipeMenuItem(MainActivity.this);

                delete.setIcon(R.drawable.ic_delete);
                delete.setBackground(new ColorDrawable(Color.parseColor("#F21717")));
                delete.setWidth(250);
                menu.addMenuItem(delete);
            }
        };
        listView.setMenuCreator(swipeMenuCreator);


        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {

                if (index == 0) {



                    if (!etSearch.getText().toString().isEmpty()) {
                        if(searchList.size() == 0){
                            return false;
                        }
                        if (mDatabaseHelper.deletePerson(searchList.get(position).getId())){
                            Toast.makeText(MainActivity.this, "Person deleted successfully!", Toast.LENGTH_SHORT).show();
                            loadAllPersons();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Error in deletion...", Toast.LENGTH_SHORT).show();
                    } else {
                        if(personList.size() == 0){
                            return false;
                        }
                        //DataBase delete code
                        if (mDatabaseHelper.deletePerson(personList.get(position).getId())) {
                            Toast.makeText(MainActivity.this, "Person deleted successfully!", Toast.LENGTH_SHORT).show();
                            loadAllPersons();
                        } else
                            Toast.makeText(MainActivity.this, "Error in deletion...", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            }
        });

        listView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

    }


    private void loadAllPersons(){
        personList.clear();
        Cursor cursor = mDatabaseHelper.getAllPersons();
        if (cursor.moveToFirst()) {
            tvNoItem.setVisibility(View.GONE);
            do {
                personList.add(new PersonModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());
            cursor.close();

            // show items in a listView
            // we use a custom adapter to show employees

            Log.i(TAG, "loadEmployees: 2 " + personList);

            personAdapter = new PersonAdapter(this, personList);
            listView.setAdapter(personAdapter);

        } else{
            tvNoItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        etSearch.setText(null);
        etSearch.clearFocus();
        loadAllPersons();
    }
}
