package com.proyecto.bav.listeners;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.DireccionesDenunciarActivity;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.DenunciaResult;

public class DenunciarRequestListener implements RequestListener<DenunciaResult> {
	
	private DireccionesDenunciarActivity activity;
	private String jsonDeDenuncia; // Para volver a denunciar si falla

	public DenunciarRequestListener(DireccionesDenunciarActivity activity, String jsonDeDenuncia) {
		this.activity = activity;
		this.jsonDeDenuncia = jsonDeDenuncia;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		if(this.es403(spiceException)){
			Dialog.showDialog(activity, true, false, this.get403Message(spiceException));
			activity.myProgressDialog.dismiss();
		} else
			activity.denunciar(this.jsonDeDenuncia); // Si la denuncia falla, la vuelvo a enviar
		
	}
	
	private String get403Message(SpiceException spiceException) {
		String stringException = spiceException.getCause().toString();
		return stringException.substring(stringException.indexOf("Forbidden") + 10, stringException.length());
	}

	private boolean es403(SpiceException spiceException) {		
		String stringException = spiceException.getCause().toString();		
		return stringException.contains("403 Forbidden");
	}

	@Override
	public void onRequestSuccess(DenunciaResult result) {		
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, false, "Denuncia enviada");
	}

}
