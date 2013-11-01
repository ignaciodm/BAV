package com.proyecto.bav.listeners;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.api.client.http.HttpResponseException;
import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.LoginActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.OlvidePassResult;

public class OlvidePassRequestListener implements RequestListener<OlvidePassResult> {
	
	private LoginActivity activity;
	private String content;
	private boolean retry;

	public OlvidePassRequestListener(LoginActivity loginActivity, String content, boolean retry) {
		this.activity = loginActivity;
		this.content = content;
		this.retry = retry;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {		
				
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else if (spiceException.getCause() instanceof HttpResponseException){
			Dialog.showDialog(activity, false, true, this.getMessage(spiceException));
			activity.myProgressDialog.dismiss();
		}
		else {
			
			if(this.retry == true)
				activity.postOlvidePass(this.content, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}

		}
	}

	private String getMessage(SpiceException spiceException) {
		
		String stringException = spiceException.getCause().toString();
		String json = stringException.substring(stringException.indexOf("{"), stringException.length());
		
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			return "Error. Intente nuevamente";
		}
		
		String email = "";
		try {
			email = jObj.getString("email");
		} catch (JSONException e) {
			return "Error. Intente nuevamente";
		}
		
		if(email != null)
			return email;
		else{
			
			String error = "";
			try {
				error = jObj.getString("email");
			} catch (JSONException e) {
				return "Error. Intente nuevamente";
			}
			
			if(error != null)
				return error;
		}
		
		return "Error. Intente nuevamente";
		
	}

	@Override
	public void onRequestSuccess(OlvidePassResult result) {
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, false, true, "La contraseña ha sido enviada a su casilla de email");
	}

}
