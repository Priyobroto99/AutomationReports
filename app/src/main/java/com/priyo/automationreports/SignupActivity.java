package com.priyo.automationreports;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener , View.OnFocusChangeListener{
    String TAG = "SignupActivity";
    private FirebaseAuth mAuth;

    private TextInputEditText name;
    private TextInputEditText phoneNo;
    private TextInputEditText mEmailField;
    private TextInputEditText mPasswordField;

    private TextInputLayout nameLabel;
    private TextInputLayout phoneNoLabel;
    private TextInputLayout mEmailFieldLabel;
    private TextInputLayout mPasswordFieldLabel;

    private LinearLayout registerLayout;

    Button registerBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        name = (TextInputEditText) findViewById(R.id.name);
        phoneNo = (TextInputEditText) findViewById(R.id.phone);
        mEmailField = (TextInputEditText) findViewById(R.id.email);
        mPasswordField = (TextInputEditText) findViewById(R.id.pass);
        mEmailFieldLabel = findViewById(R.id.emailL);
        mPasswordFieldLabel = findViewById(R.id.passL);
        nameLabel = findViewById(R.id.nameL);
        phoneNoLabel = findViewById(R.id.phoneL);

        registerLayout = findViewById(R.id.registerLayout);
        registerBtn = (Button) findViewById(R.id.signupBtn);
        loginBtn = (Button) findViewById(R.id.signInBtn);

        //setting onClick listeners [Start]
        registerBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        //setting onClick listeners [End]

        //setting onFocus listeners [Start]
        name.setOnFocusChangeListener(this);
        phoneNo.setOnFocusChangeListener(this);
        mEmailField.setOnFocusChangeListener(this);
        mPasswordField.setOnFocusChangeListener(this);
        //setting onFocus listeners [End]
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.signInBtn) {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.signupBtn)
            registerWithEmailPassword(mEmailField.getText().toString(), mPasswordField.getText().toString());
         else if (id == R.id.registerLayout)
            hideKeyBoard();

    }

    public void registerWithEmailPassword(String email, String password) {
        if (!validateForm()) {
            return;
        }
        ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setMessage("Creating new user...");
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(SignupActivity.this, Reports.class);
                            startActivity(intent);
                            finish();
                            //updateUI(user);
                        } else {
                            progressDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
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

    void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        if (id == R.id.pass) {
            if (hasFocus) {
                mPasswordFieldLabel.setHint("");
            } else {
                mPasswordFieldLabel.setHint("Password");
            }
        }
        else if (id == R.id.email) {
            if (hasFocus) {
                mEmailFieldLabel.setHint("");
            } else {
                mEmailFieldLabel.setHint("Email Id");
            }
        }
        else if (id == R.id.name) {
            if (hasFocus) {
                nameLabel.setHint("");
            } else {
                nameLabel.setHint("Userame");
            }
        }
        else if (id == R.id.phone) {
            if (hasFocus) {
                phoneNoLabel.setHint("");
            } else {
                phoneNoLabel.setHint("Mobile number");
            }
        }
    }
}
