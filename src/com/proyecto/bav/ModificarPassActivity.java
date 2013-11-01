package com.proyecto.bav;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.proyecto.bav.listeners.CambiarPassRequestListener;
import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;
import com.proyecto.bav.requests.PostCambiarPassRequest;

public class ModificarPassActivity extends BaseSpiceActivity {
	
	public ProgressDialog myProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_modificar_pass);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.modificar_pass, menu);
		return true;
	}
	
	/** Called when the user clicks the Modificar Contraseña button */
	public void modificarPass(View view) {
		
		EditText etPassAnterior = (EditText) findViewById(R.id.et_pass_anterior);
		String passAnterior = etPassAnterior.getText().toString();
		etPassAnterior = null;
		
		if(passAnterior.length() == 0)
		{
			Dialog.showDialog(this, false, false, "La contraseña anterior no puede ser vacía");
			return;
		}
		
		EditText etPassNueva1 = (EditText) findViewById(R.id.et_pass_nueva1);
		String passNueva1 = etPassNueva1.getText().toString();
		etPassNueva1 = null;
		
		if(passNueva1.length() == 0)
		{
			Dialog.showDialog(this, false, false, "La contraseña nueva no puede ser vacía");
			return;
		}		
		
		EditText etPassNueva2 = (EditText) findViewById(R.id.et_pass_nueva2);
		String passNueva2 = etPassNueva2.getText().toString();
		etPassNueva2 = null;
		
		if(!passNueva2.equals(passNueva1))
		{
			Dialog.showDialog(this, false, false, "Las contraseñas no coinciden");
			return;
		}
		
		if(passNueva2.length() < 8)
		{
			Dialog.showDialog(this, false, false, "La Contraseña debe tener al menos 8 caracteres");
			return;
		}
		
		myProgressDialog = new ProgressDialog(this, R.style.ProgressDialogTheme);
		myProgressDialog.setTitle("Por favor, espere...");
		myProgressDialog.setMessage("Cambiando la contraseña...");
		myProgressDialog.show();
		
		User u = User.getUser(getApplicationContext());
		
		cambiarPass(u.getId(), getCambiarPassJSON(passAnterior, passNueva1, passNueva2, u.getAuthToken()), true);		
		
	}

	public void cambiarPass(int userID, String cambiarPassJSON, boolean retry) {

		getSpiceManager().execute(new PostCambiarPassRequest(userID, cambiarPassJSON),
				null, 
				DurationInMillis.ONE_MINUTE,
				new CambiarPassRequestListener(this, userID, cambiarPassJSON, retry));
		
	}

	private String getCambiarPassJSON(String passAnterior, String passNueva1, String passNueva2, String authToken) {
		return  "{" + 
					"\"password\":" + "\"" + passAnterior + "\"" + "," + 
					"\"nuevaPassword\":" + "\"" + passNueva1 + "\"" + "," +
					"\"nuevaPasswordConfirmacion\":" + "\"" + passNueva2 + "\"" + "," +
					"\"authToken\":" + "\"" + authToken + "\"" +				
				"}";
	}

}
