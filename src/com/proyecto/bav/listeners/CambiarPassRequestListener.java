package com.proyecto.bav.listeners;

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
			Dialog.showDialog(activity, false, false, "Ha ocurrido un error con la conexión. Intente nuevamente");
	}

	@Override
	public void onRequestSuccess(CambiarPassResult result) {			
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, false, "Contraseña modificada con éxito");		
	}

}
