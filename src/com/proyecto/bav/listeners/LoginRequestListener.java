package com.proyecto.bav.listeners;

import org.json.JSONException;
import org.json.JSONObject;

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
	private String json;
	private boolean retry;

	public LoginRequestListener(LoginActivity loginActivity, String json, boolean retry) {
		this.activity = loginActivity;
		this.json = json;
		this.retry = retry;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else if (spiceException.getCause() instanceof HttpResponseException)
			
			if(this.usuarioBloqueado(spiceException)){
				Dialog.showDialog(activity, false, true, "Usuario bloqueado. Dirijase a su cuenta de correo electrónico para desbloquearlo");
				activity.myProgressDialog.dismiss();
			}
			else{
				Dialog.showDialog(activity, false, true, "Email o Contraseña incorrectos");
				activity.myProgressDialog.dismiss();
			}
			
		else {
			
			if(this.retry == true)
				activity.postLogin(this.json, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}
			
		}
	}

	private boolean usuarioBloqueado(SpiceException spiceException) {
		
		String stringException = spiceException.getCause().toString();
		String json = stringException.substring(stringException.indexOf("{"), stringException.length());
		
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			return false;
		}
		
		String bloqueado = "";
		try {
			bloqueado = jObj.getString("bloqueado");
		} catch (JSONException e) {
			return false;
		}
		
		if(bloqueado.equals("true"))
			return true;
		
		return false;
	}

	@Override
	public void onRequestSuccess(LoginResult result) {
		
		if(result.getUser().isComisaria() == false){
		
		result.getUser().save(activity.getApplicationContext());
		
			if(result.getAddresses() != null)
				for(Address a: result.getAddresses())
					Address.save(a, activity.getApplicationContext());		
		
			activity.myProgressDialog.dismiss();
			Intent intent = new Intent(activity, MainActivity.class);
			activity.startActivity(intent);
			activity.finish();
		
		} else{
			activity.myProgressDialog.dismiss();
			Dialog.showDialog(activity, false, true, "Error.\n" + "El usuario es tipo Comisaría");
		}
				
	}

}
