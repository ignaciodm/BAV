package com.proyecto.bav.listeners;

import android.content.Intent;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.BaseSpiceActivity;
import com.proyecto.bav.DisplayAddressesActivity;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.results.AddressResult;


// ============================================================================================
// INNER CLASSES
// ============================================================================================

public final class AddressRequestListener implements RequestListener< AddressResult > {

    private Address address;
	private BaseSpiceActivity activity;

	public AddressRequestListener(Address address, BaseSpiceActivity activity) {
		this.address = address;
		this.activity = activity;
	}

	@Override
    public void onRequestFailure( SpiceException spiceException ) {
//        Toast.makeText( NewAddressActivity.this, "failure", Toast.LENGTH_SHORT ).show();
		Address.save(address, activity.getApplicationContext());
		Intent intent = new Intent(activity, DisplayAddressesActivity.class);
		activity.startActivity(intent);
    }

    @Override
    public void onRequestSuccess( final AddressResult result ) {
//        Toast.makeText( NewAddressActivity.this, "success", Toast.LENGTH_SHORT ).show();
//        String originalText = getString( R.string.textview_text );
//        mLoremTextView.setText( originalText + result.getWeather().getCurren_weather().get( 0 ).getTemp() );
    	Intent intent = new Intent(activity, DisplayAddressesActivity.class);
		activity.startActivity(intent);
    	Address.save(address, activity.getApplicationContext());
    }
}