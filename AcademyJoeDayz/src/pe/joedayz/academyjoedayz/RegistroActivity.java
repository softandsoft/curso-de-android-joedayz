package pe.joedayz.academyjoedayz;

import java.io.File;

import pe.joedayz.academyjoedayz.dao.AlumnoDAO;
import pe.joedayz.academyjoedayz.modelo.Alumno;
import pe.joedayz.academyjoedayz.util.Extras;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class RegistroActivity extends ActionBarActivity {
	
	private String rutaArchivo;
	private RegistroHelper registroHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registro);

		Intent intent = getIntent();
		final Alumno alumnoExistente = (Alumno) intent.getSerializableExtra(Extras.ALUMNO_SELECCIONADO);
		
//		Toast.makeText(this, "Alumno " + alumno, Toast.LENGTH_LONG).show();
				
		registroHelper = new RegistroHelper(this);
		Button btnGrabar = (Button) findViewById(R.id.btnGrabar);
		
		if (alumnoExistente != null) {
			btnGrabar.setText("Modificar");
			registroHelper.visualizarDatos(alumnoExistente);
		}
		
		btnGrabar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Alumno alumno = registroHelper.guardar();
				
				AlumnoDAO dao = new AlumnoDAO(RegistroActivity.this);
				
				if (alumnoExistente == null) {
					dao.guardar(alumno);	
				}
				else {
					alumno.setId(alumnoExistente.getId());
					dao.modificar(alumno);
				}

				dao.close();
				
				finish();
			}
		});
		
		ImageView imgFoto = registroHelper.getFoto();
		
		imgFoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent irParaCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				rutaArchivo = Environment.getExternalStorageDirectory().toString() + "/" + System.currentTimeMillis() + ".png";
				
				File archivo = new File(rutaArchivo);
				Uri localImagen = Uri.fromFile(archivo);
				
				irParaCamara.putExtra(MediaStore.EXTRA_OUTPUT, localImagen);
				
				startActivityForResult(irParaCamara, Extras.TOMAR_FOTO);
			}
		});		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == Extras.TOMAR_FOTO){
			if(resultCode == Activity.RESULT_OK)
				registroHelper.cargarImagen(rutaArchivo);
			else
				rutaArchivo = null;
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registro, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
