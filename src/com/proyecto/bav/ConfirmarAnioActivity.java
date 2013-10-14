package com.proyecto.bav;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.proyecto.bav.models.Dialog;
import com.proyecto.bav.models.User;

public class ConfirmarAnioActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_confirmar_anio);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirmar_anio, menu);
		return true;
	}
	
	/** Called when the user clicks the Aceptar button */
	public void confirmarAnio(View view) {
		
		User user = User.getUser(this.getApplicationContext());
		
		EditText editTextAnio = (EditText) findViewById(R.id.et_anio);
		String et_anio = editTextAnio.getText().toString();
		editTextAnio = null;
		
		int anioNacimientoString;
		try {
			anioNacimientoString = Integer.parseInt(et_anio);
		} catch (Exception e) {
			anioNacimientoString = 0;
		}
		
		
		if(user.getAnioNacimiento() == anioNacimientoString)
		{	
			Intent returnIntent = new Intent();
			setResult(RESULT_OK,returnIntent);  
			this.finish();
		}
		else {
			Dialog.showDialog(this, false, false, "Año incorrecto");
		}
		
	}

}
