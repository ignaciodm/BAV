package com.proyecto.bav.listeners;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.RegistroActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.UsuarioResult;

public class RegistrarUsuarioRequestListener implements RequestListener<UsuarioResult> {

	private RegistroActivity activity;
	
	public RegistrarUsuarioRequestListener(RegistroActivity registroActivity) {
		this.activity = registroActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {	
		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
		else{
			
			if(this.emailExist(spiceException))
				Dialog.showDialog(activity, false, true, "El email ya se encuentra registrado en nuestro sistema");
			else
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
			
		}
			
	}

	private boolean emailExist(SpiceException spiceException) {
		
		String stringException = spiceException.getCause().toString();
		String json = stringException.substring(stringException.indexOf("{"), stringException.length());
		
		JSONObject jObj = null;
		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			return false;
		}
		
		String emailExist = "";
		try {
			emailExist = jObj.getString("email");
		} catch (JSONException e) {
			return false;
		}
		
		if(emailExist.equals("[\"has already been taken\"]"))
			return true;
		
		return false;
		
	}

	@Override
	public void onRequestSuccess(UsuarioResult result) {		
	    	
	    // Con esto cierro el Teclado si queda abierto
		InputMethodManager mgr = (InputMethodManager) 
		activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(activity.editTextFechaNacimiento.getWindowToken(), 0);
		
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "Cuenta creada exitosamente.\n\nVerifique su cuenta de email para confirmar el registro");
	}

}
