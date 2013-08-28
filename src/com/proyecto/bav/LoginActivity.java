package com.proyecto.bav;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Si el usuario ya está logueado, voy directo a la aplicación
		// Intent intent = new Intent(this, MainActivity.class);
		// this.startActivity(intent);
		// this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	/** Called when the user clicks the Login button */
	public void loginApp(View view) {

		Intent intent = new Intent(this, MainActivity.class);		
		
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		String email = editTextEmail.getText().toString();
		
		EditText editTextPassword = (EditText) findViewById(R.id.et_password);
		String password = editTextPassword.getText().toString();
		
		//if(email.equals("admin") && password.equals("123") )
			this.startActivity(intent);
			this.finish();
		
	}

}
