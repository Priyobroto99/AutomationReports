package com.priyo.automationreports;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivityViewModel extends ViewModel {


    String TAG = "LoginActivityViewModel";
    private FirebaseAuth mAuth;

    private static LoginActivityViewModel singleInstance = null;
    private LoginActivityViewModel() {
        mAuth = FirebaseAuth.getInstance();
    }

    public static LoginActivityViewModel getInstance() {
        if (singleInstance == null) {
            singleInstance = new LoginActivityViewModel();
            return singleInstance;
        } else {
            return singleInstance;
        }
    }

    public Boolean checkLogin() {

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            return true;
        else
            return false;

    }


}
