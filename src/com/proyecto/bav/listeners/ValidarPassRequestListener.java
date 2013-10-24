package com.proyecto.bav.listeners;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.ConfirmarPassActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.ValidarPassResult;

public class ValidarPassRequestListener implements RequestListener<ValidarPassResult> {

	private ConfirmarPassActivity activity;
	
	public ValidarPassRequestListener(ConfirmarPassActivity confirmarPassActivity) {
		this.activity = confirmarPassActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, false, "No hay conexi�n. Intente nuevamente");
		else{
			
			if(this.passIncorrecta(spiceException))
				Dialog.showDialog(activity, false, false, "Contrase�a incorrecta");
			else
				Dialog.showDialog(activity, false, false, "Ha ocurrido un error con la conexi�n. Intente nuevamente");
			
		}		
	}

	private boolean passIncorrecta(SpiceException spiceException) {
		
		String stringException = spiceException.getCause().toString();
		String json = stringException.substring(stringException.indexOf("{"), stringException.length());
		
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			return false;
		}
		
		String password = "";
		try {
			password = jObj.getString("password");
		} catch (JSONException e) {
			return false;
		}
		
		if(password.equals("Contrasenia invalidos"))
			return true;
		
		return false;
		
	}

	@Override
	public void onRequestSuccess(ValidarPassResult result) {
		activity.myProgressDialog.dismiss();
		Intent returnIntent = new Intent();
		activity.setResult(ConfirmarPassActivity.RESULT_OK, returnIntent);  
		activity.finish();			
	}

}
