package com.proyecto.bav;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
		
		if(android.os.Build.VERSION.SDK_INT <= 11){
			menu.findItem(R.id.btn_datos_personales).setIcon(R.drawable.ic_datos_personales_black);
			menu.findItem(R.id.btn_show_addresses).setIcon(R.drawable.ic_address_black);
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    	case R.id.btn_datos_personales:
	    		showDatosPersonales();
	    		return true;
	        case R.id.btn_show_addresses:
	        	showAddresses();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	private void showDatosPersonales() {
		Intent intent = new Intent(this, DatosPersonalesActivity.class);
		startActivity(intent);
	}	

	private void showAddresses() {
		Intent intent = new Intent(this, DisplayAddressesActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Cerrar Sesion button */
	public void cerrarSesion(View view) {
		Toast.makeText(getApplicationContext(), "Cerrar Sesión", Toast.LENGTH_LONG).show();
	}
	
	/** Called when the user clicks the Denunciar button */
	public void enviarDenuncia(View view) {
		Intent intent = new Intent(this, DireccionesDenunciarActivity.class);
		startActivity(intent);
	}

}
