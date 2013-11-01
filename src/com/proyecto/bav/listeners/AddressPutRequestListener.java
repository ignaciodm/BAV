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
	private String json;
	private int addressId;
	private boolean retry;
	
	public AddressPutRequestListener(NewAddressActivity newAddressRequestListener, String json, int addressId, boolean retry) {
		this.activity = newAddressRequestListener;
		this.json = json;
		this.addressId = addressId;
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
				activity.modifyAddress(this.json, this.addressId, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}
			
		}
		
	}

	@Override
	public void onRequestSuccess(AddressResult result) {		
		Address.save(result.getAddress(), activity.getApplicationContext());	    
	    activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, true, true, "Dirección Guardada");
	}

}
