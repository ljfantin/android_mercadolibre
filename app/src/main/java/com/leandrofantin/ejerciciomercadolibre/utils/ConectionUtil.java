package com.leandrofantin.ejerciciomercadolibre.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * @author lfantin
 * */
public class ConectionUtil {
    
	public static boolean isConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        //si hay una red activa me fijo cual es
        if (null != activeNetwork && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting()) {
        	return true;
        } //sino es que no hay coneccion
        else {
        	return false;
        }
    }
  
}
