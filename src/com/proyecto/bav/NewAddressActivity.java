package com.proyecto.bav;

import java.io.FileOutputStream;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewAddressActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_address);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_address, menu);
		return true;
	}
	
	public void saveAddress(View view) {
	    Intent intent = new Intent(this, DisplayAddressesActivity.class);
	    EditText editText = (EditText) findViewById(R.id.desc_address);
	    String descAddress = editText.getText().toString();
	    saveFile(descAddress);
	    startActivity(intent);
	}

	private void saveFile(String descAddress) {
		String filename = "addresses";
		String string = descAddress;
		FileOutputStream outputStream;

		try {
		  outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
		  outputStream.write(string.getBytes());
		  outputStream.close();
		} catch (Exception e) {
		  e.printStackTrace();
		}
		
	}

}
