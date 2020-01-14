package com.example.bloodbank.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodbank.R;
import com.example.bloodbank.database.ApiManager;
import com.example.bloodbank.ui.Base.BaseActivity;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mIcon;
    private TextInputLayout mPhoneEditText;
    private TextInputLayout mPassword;
    private Button mLogin;
    private TextView mOr;
    private TextView mSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        mIcon = findViewById(R.id.icon);
        mPhoneEditText = findViewById(R.id.phoneEditText);
        mPassword =  findViewById(R.id.password);
        mLogin =  findViewById(R.id.login);
        mLogin.setOnClickListener(this);
        mOr = findViewById(R.id.or);
        mSignup =  findViewById(R.id.signup);
        mSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                String phone =mPhoneEditText.getEditText().getText().toString().trim();
                String password =mPassword.getEditText().getText().toString().trim();
                if(isValid(phone,password))
                    login(phone,password);
                    //showToast("successs");
                break;
            case R.id.signup:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    private void login(String phone, String password) {

        Call<String> call = ApiManager.getApis().loginUser(password, phone);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                showToast(response.body()+"lkl");
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showToast(t.getMessage());

            }
        });

    }

    private boolean isValid(String phone, String password) {

        boolean isValid=true;
        if(phone.isEmpty()) {
            mPhoneEditText.setError("required");
            isValid=  false;
        }
        else
            mPhoneEditText.setError(null);
         if (password.isEmpty()){
            mPassword.setError("requires");
            isValid= false;
        }
         else if (password.length()<7) {
             mPassword.setError("must be greater than 7 chars");
             isValid=false;
         }
         else
             mPassword.setError(null);
        return isValid;
    }
}
