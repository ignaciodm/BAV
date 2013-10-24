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
//		activity.myProgressDialog.dismiss();		
		activity.denunciar(this.jsonDeDenuncia); // Si la denuncia falla, la vuelvo a enviar
		
	}

	@Override
	public void onRequestSuccess(DenunciaResult result) {		
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, false, "Denuncia enviada");
	}

}
