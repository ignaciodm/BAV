package com.proyecto.bav.listeners;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Button;

import com.google.api.client.http.HttpResponseException;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.LoginActivity;
import com.proyecto.bav.MainActivity;
import com.proyecto.bav.R;
import com.proyecto.bav.results.LoginResult;

public class LoginRequestListener implements RequestListener<LoginResult> {
	
	private LoginActivity activity;

	public LoginRequestListener(LoginActivity loginActivity) {
		this.activity = loginActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
		alertDialog.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   dialog.dismiss();
		   }
		});
		
		if (spiceException instanceof NoNetworkException)
			alertDialog.setMessage("No hay conexión. Intente nuevamente");
		else if (spiceException.getCause() instanceof HttpResponseException)
			alertDialog.setMessage("Email o Contraseña incorrectos");
		else 
			alertDialog.setMessage("Ha ocurrido un error con la conexión. Intente nuevamente");
		
		AlertDialog alert = alertDialog.create();
        alert.show();
		
	    Button b = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
	    b.setBackgroundResource(R.drawable.background_button_rectangular);
		
		activity.myProgressDialog.dismiss();
	}

	@Override
	public void onRequestSuccess(LoginResult result) {
		
		result.getUser().save(activity.getApplicationContext());
		
		activity.myProgressDialog.dismiss();
		Intent intent = new Intent(activity, MainActivity.class);
		activity.startActivity(intent);
		activity.finish();	
		
	}

}
