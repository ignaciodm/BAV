package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.NewAddressResult;

public class NewAddressRequestListener implements RequestListener<NewAddressResult> {
	
	private NewAddressActivity activity;
	private String json;
	private boolean retry;

	public NewAddressRequestListener(NewAddressActivity newAddressActivity, String json, boolean retry) {
		this.activity = newAddressActivity;
		this.json = json;
		this.retry = retry;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else {
			
			if(this.retry == true)
				activity.newAddress(this.json, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}

		}
	}

	@Override
	public void onRequestSuccess(NewAddressResult result) {		
		Address.save(result.getAddress(), activity.getApplicationContext());
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "Dirección Guardada");
	}

}
