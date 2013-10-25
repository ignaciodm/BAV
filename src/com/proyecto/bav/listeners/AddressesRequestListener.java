package com.proyecto.bav.listeners;

import com.octo.android.robospice.exception.NoNetworkException;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.DisplayAddressesActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.results.AddressesResult;

public class AddressesRequestListener implements RequestListener<AddressesResult> {

	private DisplayAddressesActivity activity;
	
	public AddressesRequestListener(DisplayAddressesActivity displayAddressesActivity) {
		this.activity = displayAddressesActivity;
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
