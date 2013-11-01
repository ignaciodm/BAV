package com.proyecto.bav.listeners;

import org.json.JSONException;
import org.json.JSONObject;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.ModificarPassActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.CambiarPassResult;

public class CambiarPassRequestListener implements RequestListener<CambiarPassResult> {
	
	private ModificarPassActivity activity;
	private int userID;
	private String json;
	private boolean retry;

	public CambiarPassRequestListener(ModificarPassActivity modificarPassActivity, int userID, String cambiarPassJSON, boolean retry) {
		this.activity = modificarPassActivity;
		this.userID = userID;
		this.json = cambiarPassJSON;
		this.retry = retry;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, false, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else 
			
			if(this.passIncorrecta(spiceException)){
				Dialog.showDialog(activity, false, false, "Contraseña incorrecta");
				activity.myProgressDialog.dismiss();
			}
			else{
				
				if(this.retry == true)
					activity.cambiarPass(this.userID, this.json, false);
				else{
					Dialog.showDialog(activity, false, false, "Ha ocurrido un error con la conexión. Intente nuevamente");
					activity.myProgressDialog.dismiss();
				}

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
		
		if(password.equals("Password incorrecto."))
			return true;
		
		return false;
		
	}

	@Override
	public void onRequestSuccess(CambiarPassResult result) {			
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, false, "Contraseña modificada con éxito");		
	}

}
