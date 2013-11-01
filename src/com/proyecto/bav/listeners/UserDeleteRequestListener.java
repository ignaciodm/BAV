package com.proyecto.bav.listeners;

import android.content.Intent;
import android.widget.Toast;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.LoginActivity;
import com.proyecto.bav.MainActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.UsuarioResult;

public class UserDeleteRequestListener implements RequestListener<UsuarioResult> {
	
	private MainActivity activity;
	private boolean retry;
	
	public UserDeleteRequestListener(MainActivity mainActivity, boolean retry) {
		this.activity = mainActivity;
		this.retry = retry;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {		
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else 
			
			if(this.retry == true)
				activity.deleteUser(false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}
	}

	@Override
	public void onRequestSuccess(UsuarioResult result) {		
		
		User.destroy(activity.getApplicationContext());
		Address.destroy(activity.getApplicationContext());
		activity.myProgressDialog.dismiss();
		
		Toast.makeText(activity.getApplicationContext(), "Cuenta Eliminada", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(activity, LoginActivity.class);
		activity.startActivity(intent);
		activity.finish();
	}

}
