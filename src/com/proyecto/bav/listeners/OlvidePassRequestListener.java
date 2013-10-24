package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.LoginActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.OlvidePassResult;

public class OlvidePassRequestListener implements RequestListener<OlvidePassResult> {
	
	private LoginActivity activity;

	public OlvidePassRequestListener(LoginActivity loginActivity) {
		this.activity = loginActivity;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
		else 
			Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
	}

	@Override
	public void onRequestSuccess(OlvidePassResult result) {
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "La contraseña ha sido enviada a su casilla de email");
	}

}
