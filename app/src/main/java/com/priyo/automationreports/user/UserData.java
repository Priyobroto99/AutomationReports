package com.priyo.automationreports.user;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.priyo.automationreports.BR;

public class UserData extends BaseObservable {

    private int id;
    String emailID;
    String password;

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public UserData(String emailID, String password) {
        this.emailID = emailID;
        this.password = password;
    }

    public UserData() {
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
        notifyPropertyChanged(BR.emailID);
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.emailID);
    }

    @Bindable
    public String getEmailID() {
        return emailID;
    }

    @Bindable
    public String getPassword() {
        return password;
    }
}
