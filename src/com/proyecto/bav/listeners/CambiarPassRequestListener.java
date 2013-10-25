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

	public CambiarPassRequestListener(ModificarPassActivity modificarPassActivity) {
		this.activity = modificarPassActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, false, "No hay conexión. Intente nuevamente");
		else 
			
			if(this.passIncorrecta(spiceException))
				Dialog.showDialog(activity, false, false, "Contraseña incorrecta");
			else
				Dialog.showDialog(activity, false, false, "Ha ocurrido un error con la conexión. Intente nuevamente");
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
