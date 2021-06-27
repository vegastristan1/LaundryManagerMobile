package com.example.laundrymanagermobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.laundrymanagermobile.apiconnection.Manager;
import com.example.laundrymanagermobile.apiconnection.RequestHandler;
import com.example.laundrymanagermobile.apiconnection.URLs;
import com.example.laundrymanagermobile.ui.main.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText editTextStoreId , editTextStoreUsername, editTextStoreName, editTextStoreOwner, editTextStoreContactNumber, editTextStoreAddress, editTextStorePassword, editTextStoreEmail;
    Button buttonRegister;
    ProgressBar progressBar;
    ListView listView;
    RadioGroup radioGroupGender;


    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, HomeActivity.class));
            return;
        }

        editTextStoreId = findViewById(R.id.editTextStoreId);
        editTextStoreUsername = findViewById(R.id.editTextStoreUsername);
        editTextStoreName = findViewById(R.id.editTextStoreName);
        editTextStoreOwner = findViewById(R.id.editTextStoreOwner);
        editTextStoreContactNumber = findViewById(R.id.editTextStoreContactNumber);
        editTextStoreAddress = findViewById(R.id.editTextStoreAddress);
        editTextStorePassword = findViewById(R.id.editTextStorePassword);
        editTextStoreEmail = findViewById(R.id.editTextStoreEmail);
        buttonRegister = findViewById(R.id.buttonRegister);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void registerUser() {
        final String store_username = editTextStoreUsername.getText().toString().trim();
        final String store_email_address = editTextStoreEmail.getText().toString().trim();
        final String store_password = editTextStorePassword.getText().toString().trim();
        final String store_name = editTextStoreName.getText().toString().trim();
        final String store_owner = editTextStoreOwner.getText().toString().trim();
        final String store_contact_number = editTextStoreContactNumber.getText().toString().trim();
        final String store_address = editTextStoreAddress.getText().toString().trim();

        System.out.println(store_username);
        //first we will do the validations

        if (TextUtils.isEmpty(store_username)) {
            editTextStoreUsername.setError("Please enter username");
            editTextStoreUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(store_email_address)) {
            editTextStoreEmail.setError("Please enter your email");
            editTextStoreEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(store_email_address).matches()) {
            editTextStoreEmail.setError("Enter a valid email");
            editTextStoreEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(store_password)) {
            editTextStorePassword.setError("Enter a password");
            editTextStorePassword.requestFocus();
            return;
        }

        //if it passes all the validations

        class RegisterUser extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("store_username", store_username);
                params.put("store_email_address", store_email_address);
                params.put("store_password", store_password);
                params.put("store_name", store_name);
                params.put("store_owner", store_owner);
                params.put("store_contact_number", store_contact_number);
                params.put("store_address", store_address);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        JSONObject userJson = obj.getJSONObject("storemanagers");

                        //creating a new user object
                        Manager manager = new Manager(
                                userJson.getInt("id"),
                                userJson.getString("store_username"),
                                userJson.getString("store_email_address"),
                                userJson.getString("store_password"),
                                userJson.getString("store_name"),
                                userJson.getString("store_owner"),
                                userJson.getString("store_contact_number"),
                                userJson.getString("store_address")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(manager);

                        //starting the profile activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        //executing the async task
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}