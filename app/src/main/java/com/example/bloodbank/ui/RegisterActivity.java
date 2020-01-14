package com.example.bloodbank.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;
import com.example.bloodbank.R;
import com.example.bloodbank.database.ApiManager;
import com.example.bloodbank.ui.Base.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private TextInputLayout mNameEdittext;
    private TextInputLayout mCityEditText;
    private TextInputLayout mPhoneEditText;
    private TextInputLayout mPassword;
    private Spinner mBloodgroup;
    private Button mSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();



    }

    private void initView() {
        mNameEdittext =  findViewById(R.id.nameEdittext);
        mCityEditText = findViewById(R.id.cityEditText);
        mPhoneEditText = findViewById(R.id.phoneEditText);
        mPassword = findViewById(R.id.password);

        mBloodgroup =  findViewById(R.id.bloodgroup);
        initSpinner();

        mSubmit =  findViewById(R.id.submit);
        mSubmit.setOnClickListener(this);
    }


    public void initSpinner(){
        List <String> bloodlist=new ArrayList<>();
        bloodlist.add("Blood groub");
        bloodlist.add("A+");
        bloodlist.add("A-");
        bloodlist.add("B+");
        bloodlist.add("B-");
        bloodlist.add("AB+");
        bloodlist.add("AB-");
        bloodlist.add("O+");
        bloodlist.add("O-");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_item, bloodlist){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        dataAdapter .setDropDownViewResource(R.layout.spinner_item);
        mBloodgroup.setAdapter(dataAdapter);

        mBloodgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                  //showToast("Selected : " + selectedItemText);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                String name = mNameEdittext.getEditText().getText().toString().trim();
                String city =mCityEditText.getEditText().getText().toString().trim();
                String password =mPassword.getEditText().getText().toString().trim();
                String phone =mPhoneEditText.getEditText().getText().toString().trim();
                String blood =mBloodgroup.getSelectedItem().toString().trim();
                if(isValidForm(name , city , password, phone , blood))
                    register(name , city , password, phone , blood);
                break;
            default:
                break;
        }
    }

    private void register(String name, String city, String password, String phone, String blood) {


        Call<String> call =ApiManager.getApis().createUser(name, city, blood, password, phone);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                showToast(response.body());
                startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                finish();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showToast(t.getMessage());

            }
        });



    }

    private boolean isValidForm(String name, String city, String password, String phone, String blood) {
        boolean isValid =true;
     if(name.isEmpty()){
         mNameEdittext.setError("Required");
         isValid=false;
     }
     else
         mNameEdittext.setError(null);

     if(city.isEmpty()){
         mCityEditText.setError("Required");
         isValid=false;
     }
     else
         mCityEditText.setError(null);

     if(phone.isEmpty()){
         mPhoneEditText.setError("Required");
         isValid=false;
     }
     else
         mPhoneEditText.setError(null);


     if(password.isEmpty()){
            mPassword.setError("Required");
            isValid=false;
     }
     else if(password.length()<7) {
            mPassword.setError("must be greater than 7 chars");
            isValid=false;
     }
        else
            mPassword.setError(null);


     if(blood.isEmpty()||"Blood groub".equals(blood)){
            showToast("type of blood is required");

            isValid=false;
        }

     return  isValid;
    }
}
