package com.proyecto.bav.listeners;

import android.content.Intent;

import com.google.api.client.http.HttpResponseException;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.LoginActivity;
import com.proyecto.bav.MainActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.LoginResult;

public class LoginRequestListener implements RequestListener<LoginResult> {
	
	private LoginActivity activity;

	public LoginRequestListener(LoginActivity loginActivity) {
		this.activity = loginActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
		else if (spiceException.getCause() instanceof HttpResponseException)
			Dialog.showDialog(activity, false, true, "Email o Contraseña incorrectos");
		else 
			Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
	}

	@Override
	public void onRequestSuccess(LoginResult result) {
		
		result.getUser().save(activity.getApplicationContext());
		
		for(Address a: result.getAddresses())
			Address.save(a, activity.getApplicationContext());		
		
		activity.myProgressDialog.dismiss();
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();	
		
	}

}
