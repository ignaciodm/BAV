package com.proyecto.bav;

import java.lang.reflect.Type;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.AddressPutRequestListener;
import com.proyecto.bav.listeners.LocalidadRequestListener;
import com.proyecto.bav.listeners.NewAddressRequestListener;
import com.proyecto.bav.listeners.PartidoRequestListener;
import com.proyecto.bav.listeners.ProvinceRequestListener;
import com.proyecto.bav.listeners.ProvincesRequestListener;
import com.proyecto.bav.models.Address;
import com.proyecto.bav.models.Comisaria;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.Localidad;
import com.proyecto.bav.models.Partido;
import com.proyecto.bav.models.Provincia;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.GetLocalidadRequest;
import com.proyecto.bav.requests.GetPartidoRequest;
import com.proyecto.bav.requests.GetProvinceRequest;
import com.proyecto.bav.requests.GetProvincesRequest;
import com.proyecto.bav.requests.PostNewAddressRequest;
import com.proyecto.bav.requests.PutModifyAddressRequest;

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
		editAddressCalle.setText(address.getCalle());
		editAddressCalle = null;

		if (address.getNumero() != null) {
			EditText editAddressNumero = (EditText) findViewById(R.id.new_address_street_number);
			editAddressNumero.setText(String.valueOf(address.getNumero()));
			editAddressNumero = null;
		}

		if (address.getPiso() != null) {
			EditText editAddressPiso = (EditText) findViewById(R.id.new_address_street_piso);
			editAddressPiso.setText(String.valueOf(address.getPiso()));
			editAddressPiso = null;
		}

		EditText editAddressDpto = (EditText) findViewById(R.id.new_address_street_dpto);
		editAddressDpto.setText(address.getDepartamento());
		editAddressDpto = null;

		EditText editAddressEntreCalle1 = (EditText) findViewById(R.id.new_address_entreCalle1);
		editAddressEntreCalle1.setText(address.getEntreCalle1());
		editAddressEntreCalle1 = null;

		EditText editAddressEntreCalle2 = (EditText) findViewById(R.id.new_address_entreCalle2);
		editAddressEntreCalle2.setText(address.getEntreCalle2());
		editAddressEntreCalle2 = null;
		
		EditText editAddressComentarios = (EditText) findViewById(R.id.new_address_comentarios);
		editAddressComentarios.setText(address.getComentarios());
		editAddressComentarios = null;

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
			comisaria = address.getComisaria();
			editAddressComisaria.setText(comisaria.getNombre());
			editAddressComisaria = null;
		} catch (Exception e) {
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_address, menu);
		
		if(android.os.Build.VERSION.SDK_INT <= 11){
			menu.findItem(R.id.btn_guardar).setIcon(R.drawable.ic_guardar_black);
		}
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.btn_guardar:
			confirmarPass();
			return true;		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void confirmarPass() {
		
		EditText editTextAddressDescripcion = (EditText) findViewById(R.id.new_address_description);
		String et_address_descripcion = editTextAddressDescripcion.getText().toString();
		editTextAddressDescripcion = null;
		
		if(et_address_descripcion.length() == 0){
			Dialog.showDialog(this, false, true, "Por favor, ingrese una Descripción");
			return;
		}
		
		if(et_address_descripcion.length() < 2){
			Dialog.showDialog(this, false, true, "La Descripción debe ser de al menos 2 caracteres");
			return;
		}

		EditText editTextAddressStreet = (EditText) findViewById(R.id.new_address_street);
		String et_address_street = editTextAddressStreet.getText().toString();
		editTextAddressStreet = null;
		
		if(et_address_street.length() == 0){
			Dialog.showDialog(this, false, true, "Por favor, ingrese una Calle");
			return;
		}
		
		if(et_address_street.length() < 2){
			Dialog.showDialog(this, false, true, "La Calle debe ser de al menos 2 caracteres");
			return;
		}

		EditText editTextAddressNumero = (EditText) findViewById(R.id.new_address_street_number);
		String et_address_numero = editTextAddressNumero.getText().toString();
		editTextAddressNumero = null;
		
		if(et_address_numero.length() == 0){
			Dialog.showDialog(this, false, true, "Por favor, ingrese un Número de Calle");
			return;
		}
		
		if(comisaria == null){
			Dialog.showDialog(this, false, true, "Por favor, seleccione una Comisaría");
			return;
		}
		
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
		
		// Si selecciono una Provincia, tengo que borrar la Comisaría, la Localidad y el Partido
		borrarPartido();
		borrarLocalidad();
		borrarComisaria();
		
		// Con esto cierro el Teclado si queda abierto
		InputMethodManager mgr = (InputMethodManager) 
		getSystemService(Context.INPUT_METHOD_SERVICE);
		mgr.hideSoftInputFromWindow(provinceEditText.getWindowToken(), 0);
			
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Buscando Provincias...");
		myProgressDialog.show();
		
		getProvincias(true);
		
	}

	public void getProvincias(boolean retry) {
		
		getSpiceManager().execute(new GetProvincesRequest(User.getTokenUser(getApplicationContext())),
				null, 
				DurationInMillis.ONE_MINUTE,
				new ProvincesRequestListener(this, retry));
		
	}

	public void displayPartidos(View view) {
		
		if(provincia != null) {
			
			// Si selecciono un Partido, tengo que borrar la Comisaría y la Localidad
			borrarLocalidad();
			borrarComisaria();
			
			// Con esto cierro el Teclado si queda abierto
			InputMethodManager mgr = (InputMethodManager) 
			getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(partidoEditText.getWindowToken(), 0);
				
			myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
			myProgressDialog.setTitle("Por favor, espere...");
			myProgressDialog.setMessage("Buscando Partidos...");
			myProgressDialog.show();
			
			getPartidos(provincia, true);			
			
		}
		else {
			Dialog.showDialog(this, false, true, "Primero selecione una Provincia");
		}			
		
	}

	public void getPartidos(Provincia provincia, boolean retry) {
		
		getSpiceManager().execute(new GetProvinceRequest(provincia, User.getTokenUser(getApplicationContext())),
				null, 
				DurationInMillis.ONE_MINUTE,
				new ProvinceRequestListener(this, provincia, retry));
		
	}

	public void displayLocalidades(View view) {
		
		if(partido != null){
			
			// Si selecciono una Localidad, tengo que borrar la Comisaría
			borrarComisaria();
			
			// Con esto cierro el Teclado si queda abierto
			InputMethodManager mgr = (InputMethodManager) 
			getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(localidadEditText.getWindowToken(), 0);
				
			myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
			myProgressDialog.setTitle("Por favor, espere...");
			myProgressDialog.setMessage("Buscando Localidades...");
			myProgressDialog.show();
			
			getLocalidades(partido, true);
		}
		else {
			Dialog.showDialog(this, false, true, "Primero selecione un Partido");
		}
	}	

	public void getLocalidades(Partido partido, boolean retry) {

		getSpiceManager().execute(new GetPartidoRequest(partido, User.getTokenUser(getApplicationContext())),
				null, 
				DurationInMillis.ONE_MINUTE,
				new PartidoRequestListener(this, partido, retry));
		
	}

	public void displayComisarias(View view) {
		
		if (localidad != null){
			
			// Con esto cierro el Teclado si queda abierto
			InputMethodManager mgr = (InputMethodManager) 
			getSystemService(Context.INPUT_METHOD_SERVICE);
			mgr.hideSoftInputFromWindow(comisariaEditText.getWindowToken(), 0);
				
			myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
			myProgressDialog.setTitle("Por favor, espere...");
			myProgressDialog.setMessage("Buscando Comisarias...");
			myProgressDialog.show();
			
			getComisarias(localidad, true);			
			
		}
		else {
			Dialog.showDialog(this, false, true, "Primero selecione una Localidad");
		}		
	}
	
	public void getComisarias(Localidad localidad, boolean retry) {
		
		getSpiceManager().execute(new GetLocalidadRequest(localidad, User.getTokenUser(getApplicationContext())),
				null, 
				DurationInMillis.ONE_MINUTE,
				new LocalidadRequestListener(this, localidad, retry));
		
	}

	private void borrarPartido() {
		partido = null;
		this.partidoEditText.setText("");
	}
	
	private void borrarLocalidad() {
		localidad = null;
		this.localidadEditText.setText("");
	}
	
	private void borrarComisaria() {
		comisaria = null;
		this.comisariaEditText.setText("");
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
		
		EditText editTextComentarios = (EditText) findViewById(R.id.new_address_comentarios);
		String et_address_Comentarios = editTextComentarios.getText().toString();
		editTextComentarios = null;

		Address address = null;
		if(nuevaDireccion == true)		
			address = new Address(et_address_descripcion,
				et_address_street, et_address_numero, et_address_piso,
				et_address_dpto, et_address_entreCalle1,
				et_address_entreCalle2, provincia, partido, localidad,
				comisaria, et_address_Comentarios);
		else
			address = new Address(addressID, et_address_descripcion,
					et_address_street, et_address_numero, et_address_piso,
					et_address_dpto, et_address_entreCalle1,
					et_address_entreCalle2, provincia, partido, localidad,
					comisaria, et_address_Comentarios);
		
		Gson gson = new Gson();
		Type addressType = new TypeToken<Address>() {}.getType();
		String json = gson.toJson(address, addressType);
	
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Guardando Dirección...");
		myProgressDialog.show();
		
		if(nuevaDireccion == true){
			
			newAddress(json, true);			
			
		} else{
			
			modifyAddress(json, address.getId(), true);			
			
		}

	}

	public void modifyAddress(String json, int addressId, boolean retry) {

		getSpiceManager().execute(new PutModifyAddressRequest(json, User.getUserId(this.getApplicationContext()), addressId, User.getTokenUser(getApplicationContext())),
				null,
				DurationInMillis.ONE_MINUTE,
				new AddressPutRequestListener(this, json, addressId, retry));
		
	}

	public void newAddress(String json, boolean retry) {
		
		getSpiceManager().execute(new PostNewAddressRequest(json, User.getUserId(this.getApplicationContext()), User.getTokenUser(getApplicationContext())),
				null, 
				DurationInMillis.ONE_MINUTE,
				new NewAddressRequestListener(this, json, retry));
		
	}
	
}
