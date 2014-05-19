package com.leandrofantin.ejerciciomercadolibre.receibers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.leandrofantin.ejerciciomercadolibre.R;
import com.leandrofantin.ejerciciomercadolibre.utils.ConectionUtil;

/**
 * @author lfantin (lea)
 * */
public class ConectionChangeReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		boolean status = ConectionUtil.isConnect(context);

		showMessageConexion(context,status);
	}
	
	public static void showMessageConexion(Context context,boolean ok)	{
		String message = null;
		if (ok) {
            message = context.getString(R.string.message_connection_ok);
        } else {
            message = context.getString(R.string.error_message_connection);
        }
		
        Toast toast = Toast.makeText(context, message , Toast.LENGTH_SHORT );
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();
	}
}
