package com.proyecto.bav;

import java.lang.reflect.Type;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.RegistrarUsuarioRequestListener;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.PostRegistrarUsuarioRequest;

public class RegistroActivity extends BaseSpiceActivity {
	
	private int diaNacimiento;
	private int mesNacimiento;
	private int anioNacimiento;
	public ProgressDialog myProgressDialog;
	public EditText editTextFechaNacimiento;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);
		setBackgroundAPILowerThan11();
		editTextFechaNacimiento = (EditText) this.findViewById(R.id.et_fecha_nacimiento);
		
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		editTextEmail.setText("pabloserra89@gmail.com");
		EditText editTextPass = (EditText) findViewById(R.id.et_password);
		editTextPass.setText("1");
		EditText editTextPassConf = (EditText) findViewById(R.id.et_password_confirmacion);
		editTextPassConf.setText("1");
		EditText editTextDni = (EditText) findViewById(R.id.et_dni);
		editTextDni.setText("34270751");
		EditText editTextNom = (EditText) findViewById(R.id.et_nombre);
		editTextNom.setText("Pablo");
		EditText editTextApe = (EditText) findViewById(R.id.et_apellido);
		editTextApe.setText("Serra");
		EditText editTextTel = (EditText) findViewById(R.id.et_telefono);
		editTextTel.setText("12345678");		
		diaNacimiento = 18;
		mesNacimiento = 3;
		anioNacimiento = 1989;		
	}
	
	private void setBackgroundAPILowerThan11() {		
		if(android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.HONEYCOMB){		
			findViewById(R.id.et_fecha_nacimiento).setBackgroundResource(R.drawable.background_edittext_bot_left_redondeado_api8);
			findViewById(R.id.btn_select_date).setBackgroundResource(R.drawable.background_button_bot_right_redondeado_api8);			
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.registro, menu);
		
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
	        	RegistrarUser();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void RegistrarUser() {
		
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		String et_email = editTextEmail.getText().toString();
		editTextEmail = null;
		
		if(et_email.length() == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un E-mail", Toast.LENGTH_SHORT).show();
			return;
		}
		else if (!et_email.contains("@")){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un E-mail v�lido", Toast.LENGTH_SHORT).show();
			return;
		}
		
		EditText editTextPass = (EditText) findViewById(R.id.et_password);
		String et_password = editTextPass.getText().toString();
		editTextPass = null;
		
		if(et_password.length() == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese una Contrase�a", Toast.LENGTH_SHORT).show();
			return;
		}
		
		EditText editTextPassConf = (EditText) findViewById(R.id.et_password_confirmacion);
		String et_password_conf = editTextPassConf.getText().toString();
		editTextPass = null;
		
		if(!et_password_conf.equals(et_password))
		{
			Toast.makeText(getApplicationContext(), "Las contrase�as no coinciden", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getApplicationContext(), "Por favor, ingrese un Tel�fono", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if(anioNacimiento == 0){
			Toast.makeText(getApplicationContext(), "Por favor, ingrese una Fecha de Nacimiento", Toast.LENGTH_SHORT).show();
			return;
		}
				
		User user = new User(et_email, et_password, et_dni, et_nombre, et_apellido, et_telefono, diaNacimiento, mesNacimiento, anioNacimiento);		
		registrarUsuario(user);		

	}

	private void registrarUsuario(User user) {
		
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Creando cuenta...");
		myProgressDialog.show();
		
		Gson gson = new Gson();
		Type userType = new TypeToken<User>() {}.getType();
		String json = gson.toJson(user, userType);
		
		getSpiceManager().execute(new PostRegistrarUsuarioRequest(json),
				null, 
				DurationInMillis.ONE_MINUTE,
				new RegistrarUsuarioRequestListener(this));		
	}
	
	/** Called when the user clicks the Fecha de Nacimiento EditText */
	@SuppressLint("NewApi")
	public void selectDate(View view) {
		Intent intent = new Intent(this, DatePickerActivity.class);
		startActivityForResult(intent, DatosPersonalesActivity.SELECT_FECHA_NACIMIENTO);	
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
		
		if (requestCode == DatosPersonalesActivity.SELECT_FECHA_NACIMIENTO)
			if(resultCode == RESULT_OK){
				diaNacimiento = data.getIntExtra(DatePickerActivity.DAY, 1);
				mesNacimiento = data.getIntExtra(DatePickerActivity.MONTH, 1);
				anioNacimiento = data.getIntExtra(DatePickerActivity.YEAR, 1);
				editTextFechaNacimiento.setText(diaNacimiento + " - " + getMonthName(mesNacimiento) + " - " + anioNacimiento);
			}
				
	}

}
