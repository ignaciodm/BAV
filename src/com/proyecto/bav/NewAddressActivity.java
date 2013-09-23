package com.proyecto.bav;

import java.lang.reflect.Type;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.LocalidadRequestListener;
import com.proyecto.bav.listeners.PartidoRequestListener;
import com.proyecto.bav.listeners.ProvinceRequestListener;
import com.proyecto.bav.listeners.ProvincesRequestListener;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Comisaria;
import com.proyecto.bav.models.Localidad;
import com.proyecto.bav.models.Partido;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.requests.GetLocalidadRequest;
import com.proyecto.bav.requests.GetPartidoRequest;
import com.proyecto.bav.requests.GetProvinceRequest;
import com.proyecto.bav.requests.GetProvincesRequest;

public class NewAddressActivity extends BaseSpiceActivity {

	private final static int CONFIRMAR_PASS = 1;
	
	public EditText provinceEditText;
	public EditText partidoEditText;
	public EditText localidadEditText;
	public EditText comisariaEditText;

	public Provincia provincia;
	public Partido partido;
	public Localidad localidad;
	public Comisaria comisaria;
	
	private boolean nuevaDireccion;
	private int addressID;

	// Con esta variable "bloqueo" el Request de Provincia, Partido, Localidad y Comisarias
	public int lookingFor;
	
	public ProgressDialog myProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_PROGRESS);
		setContentView(R.layout.activity_new_address);

		provinceEditText = (EditText) findViewById(R.id.new_address_provincia);
		partidoEditText = (EditText) findViewById(R.id.new_address_partido);
		localidadEditText = (EditText) findViewById(R.id.new_address_localidad);
		comisariaEditText = (EditText) findViewById(R.id.new_address_policeStation);

		Intent intent = getIntent();
		nuevaDireccion = intent.getBooleanExtra(DisplayAddressesActivity.NUEVA_DIRECCION, true);

		if (nuevaDireccion == false)
			cargarDatosDireccionSeleccionada();

	}

	private void cargarDatosDireccionSeleccionada() {
		Intent intent = getIntent();
		String nuevaDirecciónJson = intent.getStringExtra(DisplayAddressesActivity.DIRECCION_SELECCIONADA);

		Address address = null;

		Gson gson = new Gson();
		Type addressType = new TypeToken<Address>(){}.getType();

		try {
			address = gson.fromJson(nuevaDirecciónJson, addressType);
			cargarDireccionEnPantalla(address);
		} catch (Exception e) {
		}

	}

	private void cargarDireccionEnPantalla(Address address) {
		
		addressID = address.getId();

		EditText editAddressDescripcion = (EditText) findViewById(R.id.new_address_description);
		editAddressDescripcion.setText(address.getDescription());
		editAddressDescripcion = null;

		EditText editAddressCalle = (EditText) findViewById(R.id.new_address_street);
		editAddressCalle.setText(address.getStreet());
		editAddressCalle = null;

		if (address.getNumber() != null) {
			EditText editAddressNumero = (EditText) findViewById(R.id.new_address_street_number);
			editAddressNumero.setText(String.valueOf(address.getNumber()));
			editAddressNumero = null;
		}

		if (address.getPiso() != null) {
			EditText editAddressPiso = (EditText) findViewById(R.id.new_address_street_piso);
			editAddressPiso.setText(String.valueOf(address.getPiso()));
			editAddressPiso = null;
		}

		EditText editAddressDpto = (EditText) findViewById(R.id.new_address_street_dpto);
		editAddressDpto.setText(address.getDpto());
		editAddressDpto = null;

		EditText editAddressEntreCalle1 = (EditText) findViewById(R.id.new_address_entreCalle1);
		editAddressEntreCalle1.setText(address.getEntreCalle1());
		editAddressEntreCalle1 = null;

		EditText editAddressEntreCalle2 = (EditText) findViewById(R.id.new_address_entreCalle2);
		editAddressEntreCalle2.setText(address.getEntreCalle2());
		editAddressEntreCalle2 = null;

		try {
			EditText editAddressProvincia = (EditText) findViewById(R.id.new_address_provincia);
			provincia = address.getProvincia();
			editAddressProvincia.setText(provincia.getNombre());
			editAddressProvincia = null;
		} catch (Exception e) {
		}

		try {
			EditText editAddressPartido = (EditText) findViewById(R.id.new_address_partido);
			partido = address.getPartido();
			editAddressPartido.setText(partido.getNombre());
			editAddressPartido = null;
		} catch (Exception e) {
		}

		try {
			EditText editAddressLocalidad = (EditText) findViewById(R.id.new_address_localidad);
			localidad = address.getLocalidad();
			editAddressLocalidad.setText(localidad.getNombre());
			editAddressLocalidad = null;
		} catch (Exception e) {
		}

		try {
			EditText editAddressComisaria = (EditText) findViewById(R.id.new_address_policeStation);
			comisaria = address.getPoliceStation();
			editAddressComisaria.setText(comisaria.getNombre());
			editAddressComisaria = null;
		} catch (Exception e) {
		}

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
			confirmarPass();
			return true;
		case R.id.menu_guardar:
			confirmarPass();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void confirmarPass() {
		Intent intent = new Intent(this, ConfirmarPassActivity.class);
		startActivityForResult(intent, CONFIRMAR_PASS);		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CONFIRMAR_PASS)
			if(resultCode == RESULT_OK)
				saveAddress();
	}

	public void displayProvinces(View view) {

		if (lookingFor == 0) {
			lookingFor = 1;
			myProgressDialog = ProgressDialog.show(this, "Por favor, espere...", "Buscando Provincias...", true);
			getSpiceManager().execute(new GetProvincesRequest(),
					"provincias", DurationInMillis.ONE_MINUTE,
					new ProvincesRequestListener(this));
		}

	}

	public void displayPartidos(View view) {
		
		if(provincia != null) {
		
			if (lookingFor == 0) {
				lookingFor = 1;			
				myProgressDialog = ProgressDialog.show(this, "Por favor, espere...", "Buscando Partidos...", true);
				getSpiceManager().execute(new GetProvinceRequest(provincia),
						"provincia/" + provincia.getId(), 
						DurationInMillis.ONE_MINUTE,
						new ProvinceRequestListener(this));
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "Primero selecione una Provincia", Toast.LENGTH_SHORT).show();
		}
			
		
	}
	
	public void displayLocalidades(View view) {
		
		if(partido != null){
			if (lookingFor == 0) {			
				lookingFor = 1;		
				myProgressDialog = ProgressDialog.show(this, "Por favor, espere...", "Buscando Localidades...", true);
				getSpiceManager().execute(new GetPartidoRequest(partido),
						"partidos/" + partido.getId(), 
						DurationInMillis.ONE_MINUTE,
						new PartidoRequestListener(this));
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "Primero selecione un Partido", Toast.LENGTH_SHORT).show();
		}
	}
	
	public void displayComisarias(View view) {
		
		if (localidad != null){
			if (lookingFor == 0) {			
				lookingFor = 1;
				myProgressDialog = ProgressDialog.show(this, "Por favor, espere...", "Buscando Comisarias...", true);
				getSpiceManager().execute(new GetLocalidadRequest(localidad),
						"localidades/" + localidad.getId(), 
						DurationInMillis.ONE_MINUTE,
						new LocalidadRequestListener(this));
			}
		}
		else {
			Toast.makeText(getApplicationContext(), "Primero selecione una Localidad", Toast.LENGTH_SHORT).show();
		}		
	}

	public void saveAddress() {

		EditText editTextAddressDescripcion = (EditText) findViewById(R.id.new_address_description);
		String et_address_descripcion = editTextAddressDescripcion.getText().toString();
		editTextAddressDescripcion = null;

		EditText editTextAddressStreet = (EditText) findViewById(R.id.new_address_street);
		String et_address_street = editTextAddressStreet.getText().toString();
		editTextAddressStreet = null;

		EditText editTextAddressNumero = (EditText) findViewById(R.id.new_address_street_number);
		String et_address_numero = editTextAddressNumero.getText().toString();
		editTextAddressNumero = null;

		EditText editTextAddressPiso = (EditText) findViewById(R.id.new_address_street_piso);
		String et_address_piso = editTextAddressPiso.getText().toString();
		editTextAddressPiso = null;

		EditText editTextAddressDpto = (EditText) findViewById(R.id.new_address_street_dpto);
		String et_address_dpto = editTextAddressDpto.getText().toString();
		editTextAddressDpto = null;

		EditText editTextEntreCalle1 = (EditText) findViewById(R.id.new_address_entreCalle1);
		String et_address_entreCalle1 = editTextEntreCalle1.getText().toString();
		editTextEntreCalle1 = null;

		EditText editTextEntreCalle2 = (EditText) findViewById(R.id.new_address_entreCalle2);
		String et_address_entreCalle2 = editTextEntreCalle2.getText().toString();
		editTextEntreCalle2 = null;

		Address address = null;
		if(nuevaDireccion == true)		
			address = new Address(et_address_descripcion,
				et_address_street, et_address_numero, et_address_piso,
				et_address_dpto, et_address_entreCalle1,
				et_address_entreCalle2, provincia, partido, localidad,
				comisaria);
		else
			address = new Address(addressID, et_address_descripcion,
					et_address_street, et_address_numero, et_address_piso,
					et_address_dpto, et_address_entreCalle1,
					et_address_entreCalle2, provincia, partido, localidad,
					comisaria);

		Address.save(address, this.getApplicationContext());
		
		if(nuevaDireccion == true)
			Toast.makeText(getApplicationContext(), "Dirección creada", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "Dirección modificada", Toast.LENGTH_SHORT).show();		
		
		this.finish();

		// getSpiceManager().execute( new PostAddressSpiceRequest(address),
		// "json",
		// DurationInMillis.ONE_MINUTE,
		// new AddressRequestListener(address, this));

	}
	
}
