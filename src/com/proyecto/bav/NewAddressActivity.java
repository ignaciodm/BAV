package com.proyecto.bav;


import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.ProvinceRequestListener;
import com.proyecto.bav.listeners.ProvincesRequestListener;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Localidad;
import com.proyecto.bav.models.Partido;
import com.proyecto.bav.models.PoliceStation;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.requests.GetProvinceRequest;
import com.proyecto.bav.requests.GetProvincesRequest;

public class NewAddressActivity extends BaseSpiceActivity {

	public EditText provinceEditText;
	public EditText partidoEditText;
	public EditText localidadEditText;
	
	public Provincia provincia;
	public Partido partido;
	public Localidad localidad;
	public PoliceStation policeStation;
	
	public List<Provincia> provincias = new ArrayList<Provincia>();
	public List<Partido> partidos = new ArrayList<Partido>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_new_address);
		
		provinceEditText = (EditText) findViewById(R.id.address_provincia);
		partidoEditText = (EditText) findViewById(R.id.address_partido);
		localidadEditText = (EditText) findViewById(R.id.address_localidad);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_address, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.btn_guardar:
	        	saveAddress();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
    public void displayProvinces(View view) {
    	getSpiceManager().execute( new GetProvincesRequest(), 
									"provincias",
									DurationInMillis.ONE_MINUTE,
									new ProvincesRequestListener(this));
    }
    
    public void displayMatches(View view) {
    	getSpiceManager().execute( new GetProvinceRequest(provincia), 
				"provincia/" + provincia.getId(),
				DurationInMillis.ONE_MINUTE,
				new ProvinceRequestListener(this));
    }
	
	public void saveAddress() {
		
		EditText editTextAddressDescripcion = (EditText) findViewById(R.id.address_description);
		String et_address_descripcion = editTextAddressDescripcion.getText().toString();
		editTextAddressDescripcion = null;
		
		EditText editTextAddressStreet = (EditText) findViewById(R.id.address_street);
		String et_address_street = editTextAddressStreet.getText().toString();
		editTextAddressStreet = null;
		
		EditText editTextAddressNumero = (EditText) findViewById(R.id.address_street_number);
		String et_address_numero = editTextAddressNumero.getText().toString();
		editTextAddressNumero = null;
		
		EditText editTextAddressPiso = (EditText) findViewById(R.id.address_street_piso);
		String et_address_piso = editTextAddressPiso.getText().toString();
		editTextAddressPiso = null;
		
		EditText editTextAddressDpto = (EditText) findViewById(R.id.address_street_dpto);
		String et_address_dpto = editTextAddressDpto.getText().toString();
		editTextAddressDpto = null;
		
		EditText editTextEntreCalle1 = (EditText) findViewById(R.id.address_entreCalle1);
		String et_address_entreCalle1 = editTextEntreCalle1.getText().toString();
		editTextEntreCalle1 = null;
		
		EditText editTextEntreCalle2 = (EditText) findViewById(R.id.address_entreCalle2);
		String et_address_entreCalle2 = editTextEntreCalle2.getText().toString();
		editTextEntreCalle2 = null;
		
		Address address = new Address(et_address_descripcion, 
									et_address_street, 
									et_address_numero, 
									et_address_piso, 
									et_address_dpto, 
									et_address_entreCalle1,
									et_address_entreCalle2);
		
		Address.save(address, this.getApplicationContext());
		
		datosGuardados();
		this.finish();
	    	    
	    /*int number = 10; //TODO exponer edicion
		Address address = new Address(description, street, number);
	    getSpiceManager().execute( new PostAddressSpiceRequest(address), 
	    							"json",
	    							DurationInMillis.ONE_MINUTE,
	    							new AddressRequestListener(address, this));*/
	    
	}
	
	private void datosGuardados() {
		Toast toast = Toast.makeText(getApplicationContext(), "Datos Guardados", Toast.LENGTH_SHORT);
		toast.show();
	}
	
}
