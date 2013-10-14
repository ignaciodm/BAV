package com.proyecto.bav;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.proyecto.bav.models.Dialog;

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
		
		Dialog.showDialog(this, true, false, "Contraseña modificada con éxito");
	}

}
