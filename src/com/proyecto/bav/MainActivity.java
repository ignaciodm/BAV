package com.proyecto.bav;

import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseSpiceActivity {
	
	private MainActivity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		activity = this;
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
	
	@Override
	protected void onResume() {
	    super.onResume();
	}
	
	/** Called when the user clicks the Cerrar Sesion button */
	public void cerrarSesion(View view) {		
		User.destroy(this.getApplicationContext());
		Address.destroy(this.getApplicationContext());
		cerrarSesion();	
	}
	
	private void cerrarSesion() {
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		
		alertDialog.setMessage("¿Está seguro que desea cerrar la sesión?");
		
		alertDialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(activity, LoginActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}
		});
		
		alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		AlertDialog alert = alertDialog.create();
        alert.show();
		
        Button b;
	    b = alert.getButton(DialogInterface.BUTTON_POSITIVE);
	    b.setBackgroundResource(R.drawable.background_button_rectangular);
	    b = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
	    b.setBackgroundResource(R.drawable.background_button_rectangular);
	}

	/** Called when the user clicks the Denunciar button */
	public void enviarDenuncia(View view) {
		Intent intent = new Intent(this, DireccionesDenunciarActivity.class);
		startActivity(intent);
	}

}
