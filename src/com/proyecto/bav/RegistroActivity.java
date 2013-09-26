package com.proyecto.bav;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.proyecto.bav.models.User;

public class RegistroActivity extends BaseSpiceActivity {
	
	private int diaNacimiento;
	private int mesNacimiento;
	private int anioNacimiento;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.btn_guardar:
	        	saveUser();
	            return true;
	        case R.id.menu_guardar:
	        	saveUser();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void saveUser() {
		
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		String et_email = editTextEmail.getText().toString();
		editTextEmail = null;
		
		if(et_email.length() == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un email", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (!et_email.contains("@")){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un email válido", Toast.LENGTH_SHORT).show();
			return;
		}
		
		EditText editTextPass = (EditText) findViewById(R.id.et_password);
		String et_password = editTextPass.getText().toString();
		editTextPass = null;
		
		if(et_password.length() == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese una password", Toast.LENGTH_SHORT).show();
			return;
		}
		
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
		
		if(et_nombre.length() == 0){
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
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un Teléfono", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(anioNacimiento == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese una Fecha de Nacimiento", Toast.LENGTH_SHORT).show();
			return;
		}
				
		User user = new User(et_email, et_password, et_dni, et_nombre, et_apellido, et_telefono, diaNacimiento, mesNacimiento, anioNacimiento);
		
		crearUsuario(user);
		
		Toast.makeText(getApplicationContext(), "Usuario Creado.\nVerifique su cuenta de email para confirmar el registro.", Toast.LENGTH_LONG).show();
			
		this.finish();
		
	}

	private void crearUsuario(User user) {
		// TODO Auto-generated method stub
		
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
