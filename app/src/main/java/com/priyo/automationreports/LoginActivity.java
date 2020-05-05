package com.priyo.automationreports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.priyo.automationreports.databinding.ActivityLoginBinding;
import com.priyo.automationreports.user.UserData;

import org.json.JSONArray;
import org.json.JSONException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private final String TAG = "LoginActivity";
    private LoginActivityViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private ActivityLoginBinding activityLoginBinding;


    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;

    private TextInputLayout emailLabel;
    private TextInputLayout passwordLabel;

    private Button loginBtn;
    private Button registerBtn;

    private LinearLayout loginlayout;

    ProgressDialog progressDialog;
    LoginActivityClickHandlers clickHandlers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = LoginActivityViewModel.getInstance();
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();


        registerBtn = (Button) findViewById(R.id.registerBtn);
        mEmailField = (TextInputEditText) findViewById(R.id.email);
        mPasswordField = (TextInputEditText) findViewById(R.id.pass);
        emailLabel = findViewById(R.id.emailL);
        passwordLabel = findViewById(R.id.passL);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginlayout = findViewById(R.id.loginLinear);

        //setting onClick listeners [Start]
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        //setting onClick listeners [End]

        //setting onFocus listeners [Start]
        //mEmailField.setOnFocusChangeListener(this);
        //mPasswordField.setOnFocusChangeListener(this);
        //setting onFocus listeners [End]

        /*activityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        activityLoginBinding.setUser(new UserData(userData.getEmailID(),userData.getPassword()));
        clickHandlers = new LoginActivityClickHandlers(this);
        activityLoginBinding.setClickHandler(clickHandlers);*/

        Log.i(TAG, "onCreate");
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
        if (loginViewModel.checkLogin()) {
            //navigate to Reports Page
            Intent intent = new Intent(getApplicationContext(), Reports.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            //stay in login page

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loginViewModel.checkLogin()) {
            //navigate to Reports Page
            Intent intent = new Intent(getApplicationContext(), Reports.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            //stay in login page

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void loginWithEmailPassword(String email, String password) {
        if (!validateForm()) {
            return;
        }
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Authenticating");
        progressDialog.show();
        Task<AuthResult> authResultTask = mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LoginActivity.this, "Welcome back",
                                    Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(getApplicationContext(), Reports.class);
                            startActivity(intent);
                            finish();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        Log.i(TAG, "Clicked" + id);

        if (id == R.id.loginBtn) {
           hideKeyBoard();
            loginWithEmailPassword(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (id == R.id.registerBtn) {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            Intent intent = new Intent(getApplicationContext(), Reports.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
           mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        return valid;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (id == R.id.pass) {
            if (hasFocus) {
                passwordLabel.setHint("");
            } else {
                passwordLabel.setHint("Password");
            }
        }
        else if (id == R.id.email) {
            if (hasFocus) {
                emailLabel.setHint("");
            } else {
                emailLabel.setHint("Email Id");
            }
        }
    }
    public class LoginActivityClickHandlers{
        Context context;

        public LoginActivityClickHandlers(Context context){
            this.context = context;
        }

        public void onLoginButtonClicked(View view){


        }
        public void onRegisterButtonClicked(View view){

        }
    }

}
