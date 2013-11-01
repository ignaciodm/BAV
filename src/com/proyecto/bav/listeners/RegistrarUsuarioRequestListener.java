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
	private String content;
	private boolean retry;
	
	public RegistrarUsuarioRequestListener(RegistroActivity registroActivity, String json, boolean retry) {
		this.activity = registroActivity;
		this.content = json;
		this.retry = retry;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {	
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else{
			
			if(this.emailExist(spiceException)){
				Dialog.showDialog(activity, false, true, "El email ya se encuentra registrado en nuestro sistema");
				activity.myProgressDialog.dismiss();
			}
			else{
				
				if(this.retry == true)
					activity.registrarUsuario(this.content, false);
				else{
					Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
					activity.myProgressDialog.dismiss();
				}
			}
			
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
