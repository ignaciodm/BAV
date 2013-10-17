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

	public NewAddressRequestListener(NewAddressActivity newAddressActivity) {
		this.activity = newAddressActivity;
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
	public void onRequestSuccess(NewAddressResult result) {		
		Address.save(result.getAddress(), activity.getApplicationContext());
		activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "Dirección Guardada");
	}

}
