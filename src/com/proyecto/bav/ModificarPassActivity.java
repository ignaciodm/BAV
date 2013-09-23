package com.proyecto.bav;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

public class ModificarPassActivity extends BaseSpiceActivity {

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
			Toast.makeText(getApplicationContext(), "La contraseña anterior no puede ser vacía", Toast.LENGTH_LONG).show();
			return;
		}
		
		EditText etPassNueva1 = (EditText) findViewById(R.id.et_pass_nueva1);
		String passNueva1 = etPassNueva1.getText().toString();
		etPassNueva1 = null;
		
		if(passNueva1.length() == 0)
		{
			Toast.makeText(getApplicationContext(), "La contraseña nueva no puede ser vacía", Toast.LENGTH_LONG).show();
			return;
		}		
		
		EditText etPassNueva2 = (EditText) findViewById(R.id.et_pass_nueva2);
		String passNueva2 = etPassNueva2.getText().toString();
		etPassNueva2 = null;
		
		if(!passNueva2.equals(passNueva1))
		{
			Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
			return;
		}	
		
		Toast.makeText(getApplicationContext(), "Contraseña modificada con éxito", Toast.LENGTH_SHORT).show();
		this.finish();
	}

}
