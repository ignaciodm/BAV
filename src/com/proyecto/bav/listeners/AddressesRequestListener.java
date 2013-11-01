package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.DisplayAddressesActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.results.AddressesResult;

public class AddressesRequestListener implements RequestListener<AddressesResult> {

	private DisplayAddressesActivity activity;
	private User user;
	private boolean retry;
	
	public AddressesRequestListener(DisplayAddressesActivity displayAddressesActivity, User user, boolean retry) {
		this.activity = displayAddressesActivity;
		this.user = user;
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
				activity.getAddress(this.user, false);
			else{
				Dialog.showDialog(activity, false, true, "Ha ocurrido un error con la conexión. Intente nuevamente");
				activity.myProgressDialog.dismiss();
			}

		}
	}

	@Override
	public void onRequestSuccess(AddressesResult result) {
		
		Address.removeAll(activity.getApplicationContext());
		
		if(result.getAddresses() != null)
			for(Address a: result.getAddresses())
				Address.save(a, activity.getApplicationContext());	
	    
	    activity.fetchAddresses();
	    activity.myProgressDialog.dismiss();	    
		Dialog.showDialog(activity, false, true, "Direcciones sincronizadas exitosamente");	
		
	}

}
