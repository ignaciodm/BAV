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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewAddressActivity extends BaseSpiceActivity {

	private EditText provinceEditText;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature( Window.FEATURE_PROGRESS );
		setContentView(R.layout.activity_new_address);
		provinceEditText = (EditText) findViewById(R.id.address_province);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_address, menu);
		return true;
	}
	
    public void displayProvinces(View view) {
        
        final CharSequence[] provinces = {
                "Buenos Aires", "Mendoza", "Catamarca", "San Juan", "San Luis", "Tucuman", "Tierra del Fuego"
        };
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Make your selection");
        builder.setItems(provinces, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int provinceIndex) {
                // Do something with the selection
            	provinceEditText.setText(provinces[provinceIndex]);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	public void saveAddress(View view) {
	    EditText descriptionEditText = (EditText) findViewById(R.id.address_description);
	    EditText streetEditText = (EditText) findViewById(R.id.address_street);
	    
	    String description = descriptionEditText.getText().toString();
	    String street = streetEditText.getText().toString();
	    int number = 10; //TODO exponer edicion
		Address address = new Address(description, street, number);
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
