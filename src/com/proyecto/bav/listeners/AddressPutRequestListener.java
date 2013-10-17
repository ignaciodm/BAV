package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.AddressResult;

public class AddressPutRequestListener implements RequestListener<AddressResult> {
	
	private NewAddressActivity activity;
	
	public AddressPutRequestListener(NewAddressActivity newAddressRequestListener) {
		this.activity = newAddressRequestListener;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		activity.myProgressDialog.dismiss();
		
		if (spiceException instanceof NoNetworkException)
			Dialog.showDialog(activity, false, true, "No hay conexi�n. Intente nuevamente");
		else 
			Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexi�n. Intente nuevamente");
	}

	@Override
	public void onRequestSuccess(AddressResult result) {		
		Address.save(result.getAddress(), activity.getApplicationContext());	    
	    activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "Direcci�n Guardada");
	}

}
