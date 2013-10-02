package com.proyecto.bav;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.bav.models.User;

public class DatosPersonalesActivity extends BaseSpiceActivity {
	
	private final static int CONFIRMAR_PASS = 1;
	
	private int diaNacimiento;
	private int mesNacimiento;
	private int anioNacimiento;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_datos_personales);
		setBackgroundAPILowerThan11();
		fetchDatosPersonales();		
	}

	private void setBackgroundAPILowerThan11() {		
		if(android.os.Build.VERSION.SDK_INT <= 11){
			findViewById(R.id.et_fecha_nacimiento).setBackgroundResource(R.drawable.background_edittext_bot_left_redondeado_api8);
			findViewById(R.id.btn_select_date).setBackgroundResource(R.drawable.background_button_bot_right_redondeado_api8);
		}		
	}

	private void fetchDatosPersonales() {
		
		User user = User.getUser(this.getApplicationContext());
		
		if(user != null) {
		
			EditText editTextEmail = (EditText) findViewById(R.id.et_email);
			editTextEmail.setText(user.getEmail());
			editTextEmail = null;
			
			EditText editTextDni = (EditText) findViewById(R.id.et_dni);
			editTextDni.setText(user.getDniString());
			editTextDni = null;
			
			EditText editTextNombre = (EditText) findViewById(R.id.et_nombre);
			editTextNombre.setText(user.getNombres());
			editTextNombre = null;
			
			EditText editTextApellido = (EditText) findViewById(R.id.et_apellido);
			editTextApellido.setText(user.getApellidos());
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
	        	confirmarPass();
	            return true;
	        case R.id.btn_sincronizar:
	        	sincronizar();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void confirmarPass() {
		
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
		if (requestCode == CONFIRMAR_PASS)
			if(resultCode == RESULT_OK)
				saveUser();         
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
		// TODO Auto-generated method stub		
	}
	
	/** Called when the user clicks the Cambiar Contrase�a button */
	public void modificarPass(View view) {
		Intent intent = new Intent(this, ModificarPassActivity.class);
		startActivity(intent);
	}
	
	/** Called when the user clicks the Fecha de Nacimiento EditText */
	@SuppressLint("NewApi")
	public void selectDate(View view) {
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");
	}
	
	@SuppressLint({ "NewApi", "ValidFragment" })
	public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			diaNacimiento = day;
			mesNacimiento = month+1;
			anioNacimiento = year;
			((TextView) getActivity().findViewById(R.id.et_fecha_nacimiento)).setText(diaNacimiento + " - " + getMonthName(mesNacimiento) + " - " + anioNacimiento);
		}		
		
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
