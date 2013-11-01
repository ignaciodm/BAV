package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.DatosPersonalesActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.UsuarioResult;

public class UsuarioPutRequestListener implements RequestListener<UsuarioResult> {

	private DatosPersonalesActivity activity;
	private String content;
	private User user;
	private boolean retry;
	
	public UsuarioPutRequestListener(DatosPersonalesActivity datosPersonalesActivity, String json, User user, boolean retry) {
		this.activity = datosPersonalesActivity;
		this.content = json;
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
				activity.modifyUser(this.content, this.user, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}
	}

	@Override
	public void onRequestSuccess(UsuarioResult result) {
		
		result.getUser().save(activity.getApplicationContext());		
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "Datos Guardados");		
	}

}
