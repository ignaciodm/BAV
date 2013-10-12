package com.proyecto.bav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.UsuarioRequestListener;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.GetUsuarioRequest;

public class DatosPersonalesActivity extends BaseSpiceActivity {
	
	private final static int CONFIRMAR_PASS = 1;
	final static int SELECT_FECHA_NACIMIENTO = 2;
	final static int SINCRONIZAR = 3;
	
	private int diaNacimiento;
	private int mesNacimiento;
	private int anioNacimiento;
	private EditText fechaNacimiento;
	
	public ProgressDialog myProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datos_personales);
		setBackgroundAPILowerThan11();
		fetchDatosPersonales();
		fechaNacimiento = (EditText) this.findViewById(R.id.et_fecha_nacimiento);
	}

	private void setBackgroundAPILowerThan11() {		
		if(android.os.Build.VERSION.SDK_INT <= 11){
			findViewById(R.id.et_fecha_nacimiento).setBackgroundResource(R.drawable.background_edittext_bot_left_redondeado_api8);
			findViewById(R.id.btn_select_date).setBackgroundResource(R.drawable.background_button_bot_right_redondeado_api8);
		}		
	}

	public void fetchDatosPersonales() {
		
		User user = User.getUser(this.getApplicationContext());
		
		if(user != null) {
		
			EditText editTextEmail = (EditText) findViewById(R.id.et_email);
			editTextEmail.setText(user.getEmail());
			editTextEmail = null;
			
			EditText editTextDni = (EditText) findViewById(R.id.et_dni);
			editTextDni.setText(user.getDniString());
			editTextDni = null;
			
			EditText editTextNombre = (EditText) findViewById(R.id.et_nombre);
			editTextNombre.setText(user.getNombre());
			editTextNombre = null;
			
			EditText editTextApellido = (EditText) findViewById(R.id.et_apellido);
			editTextApellido.setText(user.getApellido());
			editTextApellido = null;
			
			EditText editTextTelefono = (EditText) findViewById(R.id.et_telefono);
			editTextTelefono.setText(user.getTelefonoString());
			editTextTelefono = null; 
			
			diaNacimiento = user.getDiaNacimiento();
			mesNacimiento = user.getMesNacimiento();
			anioNacimiento = user.getAnioNacimiento();
			
			if(diaNacimiento != 0){
				EditText editTextFechaNacimiento = (EditText) findViewById(R.id.et_fecha_nacimiento);
				editTextFechaNacimiento.setText(diaNacimiento + " - " + getMonthName(mesNacimiento) + " - " + anioNacimiento);
				editTextFechaNacimiento = null;
			}
		
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.datos_personales, menu);
		
		if(android.os.Build.VERSION.SDK_INT <= 11){
			menu.findItem(R.id.btn_sincronizar).setIcon(R.drawable.ic_sincronizar_black);
			menu.findItem(R.id.btn_guardar).setIcon(R.drawable.ic_guardar_black);
		}
		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.btn_guardar:
	        	confirmarPassSave();
	            return true;
	        case R.id.btn_sincronizar:
	        	confirmarPassUpdate();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void confirmarPassUpdate() {
		Intent intent = new Intent(this, ConfirmarPassActivity.class);
		startActivityForResult(intent, SINCRONIZAR);
	}
	
	private void confirmarPassSave() {
		
		EditText editTextDni = (EditText) findViewById(R.id.et_dni);
		String et_dni = editTextDni.getText().toString();
		editTextDni = null;
		
		if(et_dni.length() == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un DNI", Toast.LENGTH_SHORT).show();
			return;
		}
			
		
		EditText editTextNombre = (EditText) findViewById(R.id.et_nombre);
		String et_nombre = editTextNombre.getText().toString();
		editTextNombre = null;
		
		if(et_nombre.length() == 0)
		{
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un Nombre", Toast.LENGTH_SHORT).show();
			return;
		}	
		
		EditText editTextApellido = (EditText) findViewById(R.id.et_apellido);
		String et_apellido = editTextApellido.getText().toString();
		editTextApellido = null;
		
		if(et_apellido.length() == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un Apellido", Toast.LENGTH_SHORT).show();
			return;
		}
		
		EditText editTextTelefono = (EditText) findViewById(R.id.et_telefono);
		String et_telefono = editTextTelefono.getText().toString();
		editTextTelefono = null;
		
		if(et_telefono.length() == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un Tel�fono", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(anioNacimiento == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese una Fecha de Nacimiento", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent intent = new Intent(this, ConfirmarPassActivity.class);
		startActivityForResult(intent, CONFIRMAR_PASS);	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == CONFIRMAR_PASS)
			if(resultCode == RESULT_OK)
				saveUser();   
		
		if (requestCode == SELECT_FECHA_NACIMIENTO)
			if(resultCode == RESULT_OK){
				diaNacimiento = data.getIntExtra(DatePickerActivity.DAY, 1);
				mesNacimiento = data.getIntExtra(DatePickerActivity.MONTH, 1);
				anioNacimiento = data.getIntExtra(DatePickerActivity.YEAR, 1);
				fechaNacimiento.setText(diaNacimiento + " - " + getMonthName(mesNacimiento) + " - " + anioNacimiento);
			}
		
		if(requestCode == SINCRONIZAR)
			if(resultCode == RESULT_OK)
				sincronizar();				
	}

	private void saveUser() {
				
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		String et_email = editTextEmail.getText().toString();
		editTextEmail = null;
		
		EditText editTextDni = (EditText) findViewById(R.id.et_dni);
		String et_dni = editTextDni.getText().toString();
		editTextDni = null;
		
		EditText editTextNombre = (EditText) findViewById(R.id.et_nombre);
		String et_nombre = editTextNombre.getText().toString();
		editTextNombre = null;
		
		EditText editTextApellido = (EditText) findViewById(R.id.et_apellido);
		String et_apellido = editTextApellido.getText().toString();
		editTextApellido = null;
		
		EditText editTextTelefono = (EditText) findViewById(R.id.et_telefono);
		String et_telefono = editTextTelefono.getText().toString();
		editTextTelefono = null;
				
		User user = new User(et_email, et_dni, et_nombre, et_apellido, et_telefono, diaNacimiento, mesNacimiento, anioNacimiento);
		
		user.save(this.getApplicationContext());
		Toast.makeText(getApplicationContext(), "Datos Guardados", Toast.LENGTH_SHORT).show();
		this.finish();	
		
	}

	private void sincronizar() {
		
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Sincronizando...");
		myProgressDialog.show();
		
		User user = User.getUser(this.getApplicationContext());
		
		getSpiceManager().execute(new GetUsuarioRequest(user.getId(), "be2c9685a9823949304e6ab85ca4de141fd6ad32"),
				null, 
				DurationInMillis.ONE_MINUTE,
				new UsuarioRequestListener(this));
		
	}
	
	/** Called when the user clicks the Cambiar Contrase�a button */
	public void modificarPass(View view) {
		Intent intent = new Intent(this, ModificarPassActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Fecha de Nacimiento EditText */
	public void selectDate(View view) {
		Intent intent = new Intent(this, DatePickerActivity.class);
		startActivityForResult(intent, SELECT_FECHA_NACIMIENTO);	
	}
	
	private String getMonthName(int month) {
		
		switch (month) {
		case 1:
			return "Ene";
		case 2:
			return "Feb";
		case 3:
			return "Mar";
		case 4:
			return "Abr";
		case 5:
			return "May";
		case 6:
			return "Jun";
		case 7:
			return "Jul";
		case 8:
			return "Ago";
		case 9:
			return "Sep";
		case 10:
			return "Oct";
		case 11:
			return "Nov";
		case 12:
			return "Dic";
		default:
			return "";
		}			
		
	}

}
