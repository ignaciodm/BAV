package com.proyecto.bav;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseSpiceActivity {
	
	public final static String REGISTRO = "com.proyecto.bav.REGISTRO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Si el usuario ya está logueado, voy directo a la aplicación
//		Intent intent = new Intent(this, MainActivity.class);
//		this.startActivity(intent);
//		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);getMenuInflater();
		return true;
	}
	
	/** Called when the user clicks the Login button */
	public void loginApp(View view) {

		Intent intent = new Intent(this, MainActivity.class);	
		
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		String email = editTextEmail.getText().toString();
		
		EditText editTextPassword = (EditText) findViewById(R.id.et_password);
		String password = editTextPassword.getText().toString();
		
		// if(email.equals("admin") && password.equals("123") )
			this.startActivity(intent);
			this.finish();
		
	}
	
	/** Called when the user clicks the Registrarse button */
	public void registrarse(View view) {

		Intent intent = new Intent(this, DatosPersonalesActivity.class);	
		intent.putExtra(REGISTRO, true);
		startActivity(intent);
	
	}
	
	/** Called when the user clicks the olvidePass button */
	public void olvidePass(View view) {
		
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		String email = editTextEmail.getText().toString();
		
		if(email.length() == 0)
			Toast.makeText(getApplicationContext(), "Ingrese el e-mail", Toast.LENGTH_SHORT).show();
		else if (!email.contains("@"))
			Toast.makeText(getApplicationContext(), "Ingrese un e-mail válido", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "Olvidé mi contraseña", Toast.LENGTH_SHORT).show();
		
	}
	
}
