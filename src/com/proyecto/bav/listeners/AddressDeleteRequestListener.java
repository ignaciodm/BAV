package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.DisplayAddressesActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.AddressResult;

public class AddressDeleteRequestListener implements RequestListener<AddressResult> {
	
	private DisplayAddressesActivity activity;
	private int addressID;
	private boolean retry;
	
	public AddressDeleteRequestListener(DisplayAddressesActivity newAddressRequestListener, int addressID, boolean retry) {
		this.activity = newAddressRequestListener;
	}

	@Override
	public void onRequestFailure(SpiceException spiceException) {
		
		if (spiceException instanceof NoNetworkException){
			Dialog.showDialog(activity, false, true, "No hay conexión. Intente nuevamente");
			activity.myProgressDialog.dismiss();
		}
		else {
			
			if(this.retry == true)
				activity.deleteAddress(this.addressID, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}

		}
	}

	@Override
	public void onRequestSuccess(AddressResult result) {		
		
		Address.delete(result.getAddress(), activity.getApplicationContext());
		
		activity.fetchAddresses();
	    activity.myProgressDialog.dismiss();
		Dialog.showDialog(activity, false, true, "Dirección Eliminada");
	}

}
