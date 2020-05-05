package com.priyo.automationreports;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivityViewModel extends ViewModel {

    private static SignupActivityViewModel singleInstance = null;
    String TAG = "LoginActivityViewModel";
    private FirebaseAuth mAuth;

    private SignupActivityViewModel(){

    }
    public static SignupActivityViewModel getInstance(){
        if(singleInstance==null){
            singleInstance = new SignupActivityViewModel();
            return singleInstance;
        }
        else{
            return singleInstance;
        }
    }





}
