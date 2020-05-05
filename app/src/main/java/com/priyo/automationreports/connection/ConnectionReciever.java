package com.priyo.automationreports.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.priyo.automationreports.R;

public abstract class ConnectionReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        onNetworkChange();
    }

    protected abstract void onNetworkChange();
}
