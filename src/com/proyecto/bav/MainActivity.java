package com.proyecto.bav;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends BaseSpiceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.show_addresses:
	        	showAddresses();
	            return true;
	       /* case R.id.help:
	            showHelp();
	            return true;*/
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	/** Called when the user clicks the Denunciar button */
	public void enviarDenuncia(View view) {
		
	}

	private void showAddresses() {
		Intent intent = new Intent(this, DisplayAddressesActivity.class);
		startActivity(intent);
	}

}
