package com.proyecto.bav.listeners;

import android.widget.Toast;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.NewAddressActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.results.AddressResult;

public class AddressRequestListener implements RequestListener<AddressResult> {
	
	private NewAddressActivity activity;

	public AddressRequestListener(NewAddressActivity newAddressActivity) {
		this.activity = newAddressActivity;
	}

	@Override
	public void onRequestFailure(SpiceException result) {
		Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
		activity.myProgressDialog.dismiss();
	}

	@Override
	public void onRequestSuccess(AddressResult result) {		
		Address.save(result.getAddress(), activity.getApplicationContext());		
		Toast.makeText(activity.getApplicationContext(), "Dirección creada", Toast.LENGTH_SHORT).show();		
		activity.myProgressDialog.dismiss();
		activity.finish();
	}

}
