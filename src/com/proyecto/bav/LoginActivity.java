package com.proyecto.bav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.LoginRequestListener;
import com.proyecto.bav.listeners.OlvidePassRequestListener;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.PostLoginRequest;
import com.proyecto.bav.requests.PostOlvidePassRequest;

public class LoginActivity extends BaseSpiceActivity {
	
	public final static String REGISTRO = "com.proyecto.bav.REGISTRO";
	
	public ProgressDialog myProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		limpiarPantalla();
		
		// Si el usuario ya est� logueado, voy directo a la aplicaci�n
		User user = User.getUser(this.getApplicationContext());
		if(user != null){
			Intent intent = new Intent(this, MainActivity.class);
			this.startActivity(intent);
			this.finish();
		}		
	}

	private void limpiarPantalla() {
		EditText editTextEmail = (EditText) findViewById(R.id.et_email);
		editTextEmail.setText("");
		
		EditText editTextPassword = (EditText) findViewById(R.id.et_password);
		editTextPassword.setText("");
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
		
		if(email.length() == 0)
			Dialog.showDialog(this, false, true, "Ingrese el e-mail");
		else if (!email.contains("@"))
			Dialog.showDialog(this, false, true, "Ingrese un e-mail v�lido");
		else{
			
			EditText editTextPassword = (EditText) findViewById(R.id.et_password);
			String password = editTextPassword.getText().toString();
			
			myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
			myProgressDialog.setTitle("Por favor, espere...");
			myProgressDialog.setMessage("Iniciando Sesi�n...");
			myProgressDialog.show();
			
			postLogin(getLoginJSON(email, password), true);
			
		}
		
	}
	
	public void postLogin(String loginJSON, boolean retry) {

		getSpiceManager().execute(new PostLoginRequest(loginJSON),
				null, 
				DurationInMillis.ONE_MINUTE,
				new LoginRequestListener(this, loginJSON, retry));
		
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
			Dialog.showDialog(this, false, true, "Ingrese el e-mail");
		else if (!email.contains("@"))
			Dialog.showDialog(this, false, true, "Ingrese un e-mail v�lido");
		else{
			
			myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
			myProgressDialog.setTitle("Por favor, espere...");
			myProgressDialog.setMessage("Enviando la contrase�a a su casilla de email...");
			myProgressDialog.show();
			
			postOlvidePass(getOlvidePassJSON(email), true);
			
		}
		
	}

	public void postOlvidePass(String olvidePassJSON, boolean retry) {
		
		getSpiceManager().execute(new PostOlvidePassRequest(olvidePassJSON),
				null, 
				DurationInMillis.ONE_MINUTE,
				new OlvidePassRequestListener(this, olvidePassJSON, retry));
		
	}

	private String getOlvidePassJSON(String email) {
		return "{\"email\":" + "\"" + email + "\"" + "}";
	}
	
}
