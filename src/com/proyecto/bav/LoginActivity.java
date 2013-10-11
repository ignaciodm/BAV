package com.proyecto.bav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.LoginRequestListener;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.PostLoginRequest;

public class LoginActivity extends BaseSpiceActivity {
	
	public final static String REGISTRO = "com.proyecto.bav.REGISTRO";
	
	public ProgressDialog myProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// Si el usuario ya está logueado, voy directo a la aplicación
		User user = User.getUser(this.getApplicationContext());
		if(user != null){
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			this.finish();
		}		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);getMenuInflater();
		return true;
	}
	
	/** Called when the user clicks the Login button */
	public void loginApp(View view) {		
		
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		String email = editTextEmail.getText().toString();
		
		EditText editTextPassword = (EditText) findViewById(R.id.et_password);
		String password = editTextPassword.getText().toString();
		
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Iniciando Sesión...");
		myProgressDialog.show();
		
		getSpiceManager().execute(new PostLoginRequest(getLoginJSON(email, password)),
				null, 
				DurationInMillis.ONE_MINUTE,
				new LoginRequestListener(this));
	}
	
	private String getLoginJSON(String email, String password) {
		return "{\"email\":" + "\"" + email + "\"" + "," + "\"password\":" + "\"" + password + "\""+ "}";
	}

	/** Called when the user clicks the Registrarse button */
	public void registrarse(View view) {
		Intent intent = new Intent(this, RegistroActivity.class);
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
