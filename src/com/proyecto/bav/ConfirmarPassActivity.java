package com.proyecto.bav;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.ValidarPassRequestListener;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.GetValidarPassRequest;

public class ConfirmarPassActivity extends BaseSpiceActivity {
	
	public ProgressDialog myProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_confirmar_pass);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmar_pass, menu);
		return true;
	}
	
	/** Called when the user clicks the Aceptar button */
	public void aceptarPass(View view) {
		
		EditText editTextPass = (EditText) findViewById(R.id.et_pass);
		String password = editTextPass.getText().toString();
		
		// Validar Contraseņa
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Validando contraseņa...");
		myProgressDialog.show();
		
		User user = User.getUser(getApplicationContext());
		
		getValidarPass(user, password, true);
		
	}

	public void getValidarPass(User user, String password, boolean retry) {
		
		getSpiceManager().execute(new GetValidarPassRequest(user, password),
				null, 
				DurationInMillis.ONE_MINUTE,
				new ValidarPassRequestListener(this, user, password, retry));
		
	}
	
}
