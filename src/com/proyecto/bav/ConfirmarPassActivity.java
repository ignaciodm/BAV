package com.proyecto.bav;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

public class ConfirmarPassActivity extends BaseSpiceActivity {
	
	public final static String CONTRASENA_OK = "com.proyecto.bav.CONTRASENA_OK";

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
		
		// Validar Contraseña
		boolean pass_ok;
		pass_ok = true;
		
		if(pass_ok == true)
		{	
			Intent returnIntent = new Intent();
			setResult(RESULT_OK,returnIntent);  
			this.finish();
		}
		else {
			Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
		}
		
	}
}
