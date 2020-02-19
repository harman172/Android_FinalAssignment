package com.example.harman_fa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewPerson extends AppCompatActivity {

    EditText etFirstName, etLastName, etAddress, etPhone;
    String fName, lName, address, phone;
    private DatabaseHelper mDatabaseHelper;
    private PersonModel personModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_person);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        personModel = (PersonModel) intent.getSerializableExtra(MainActivity.SELECTED_PERSON);

        if(personModel != null){
            etFirstName.setText(personModel.getfName());
            etLastName.setText(personModel.getlName());
            etAddress.setText(personModel.getAddress());
            etPhone.setText(personModel.getPhone());
        }

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fName = etFirstName.getText().toString().trim();
                lName = etLastName.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                address = etAddress.getText().toString().trim();


                if(validations()){
                    if(personModel != null){

                        //update existing person
                        if(mDatabaseHelper.updatePerson(personModel.getId(), fName, lName, phone, address)){
                            Toast.makeText(AddNewPerson.this, fName + " updated successfully!!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else
                            Toast.makeText(AddNewPerson.this, "Error updating " + fName, Toast.LENGTH_SHORT).show();
                    } else {

                        //add new person
                        if (mDatabaseHelper.addPerson(fName, lName, phone, address)) {
                            Toast.makeText(AddNewPerson.this, fName + " added successfully!!", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        } else
                            Toast.makeText(AddNewPerson.this, "Cannot add....", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });



    }

    private boolean validations(){

        if(fName.isEmpty()){
            etFirstName.setError("First name is required");
            etFirstName.requestFocus();
            return false;
        } else{
            if(lName.isEmpty()){
                etLastName.setError("Last name is required");
                etLastName.requestFocus();
                return false;
            } else{
                if(address.isEmpty()){
                    etFirstName.setError("First name required");
                    etFirstName.requestFocus();
                    return false;
                } else{
                    if(phone.isEmpty()){
                        etFirstName.setError("First name required");
                        etFirstName.requestFocus();
                        return false;
                    }
                    else{
                        return true;
                    }
                }
            }
        }

    }
}
