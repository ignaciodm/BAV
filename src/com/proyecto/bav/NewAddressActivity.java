package com.proyecto.bav;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.AddressResult;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewAddressActivity extends BaseSpiceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature( Window.FEATURE_PROGRESS );
		setContentView(R.layout.activity_new_address);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_address, menu);
		return true;
	}
	
	public void saveAddress(View view) {
	    Intent intent = new Intent(this, DisplayAddressesActivity.class);
	    EditText editText = (EditText) findViewById(R.id.desc_address);
	    String descAddress = editText.getText().toString();
	    Address address = new Address(descAddress, 10);
	    getSpiceManager().execute( new SimpleSpiceRequest(address), 
	    							"json",
	    							DurationInMillis.ONE_MINUTE,
	    							new AddressRequestListener(address, this));
	}
	
	
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
//            Toast.makeText( NewAddressActivity.this, "failure", Toast.LENGTH_SHORT ).show();
			Address.save(address, activity.getApplicationContext());
			Intent intent = new Intent(activity, DisplayAddressesActivity.class);
			startActivity(intent);
        }

        @Override
        public void onRequestSuccess( final AddressResult result ) {
//            Toast.makeText( NewAddressActivity.this, "success", Toast.LENGTH_SHORT ).show();
//            String originalText = getString( R.string.textview_text );
//            mLoremTextView.setText( originalText + result.getWeather().getCurren_weather().get( 0 ).getTemp() );
        	Intent intent = new Intent(activity, DisplayAddressesActivity.class);
			startActivity(intent);
        	Address.save(address, activity.getApplicationContext());
        }
    }

}
