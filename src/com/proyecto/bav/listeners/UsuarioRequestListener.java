package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.DatosPersonalesActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.UsuarioResult;

public class UsuarioRequestListener implements RequestListener<UsuarioResult> {

	private DatosPersonalesActivity activity;
	private User user;
	private boolean retry;
	
	public UsuarioRequestListener(DatosPersonalesActivity datosPersonalesActivity, User user, boolean retry) {
		this.activity = datosPersonalesActivity;
		this.user = user;
		this.retry = retry;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
				
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else 
			
			if(this.retry == true)
				activity.getUsuario(this.user, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}
		
	}

	@Override
	public void onRequestSuccess(UsuarioResult result) {		
		result.getUser().save(activity.getApplicationContext());		
		activity.myProgressDialog.dismiss();
		activity.fetchDatosPersonales();
		Dialog.showDialog(activity, false, true, "Datos Personales sincronizados exitosamente");
	}

}
