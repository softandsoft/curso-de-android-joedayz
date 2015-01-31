package pe.joedayz.academyjoedayz;

import java.util.List;

import pe.joedayz.academyjoedayz.dao.AlumnoDAO;
import pe.joedayz.academyjoedayz.modelo.Alumno;
import pe.joedayz.academyjoedayz.task.EnviaAlumnosTask;
import pe.joedayz.academyjoedayz.util.Extras;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class AlumnosActivity extends ActionBarActivity {

	private ListView lsvAlumnos;
	private Alumno alumno;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alumnos);

		this.lsvAlumnos = (ListView) findViewById(R.id.lsvAlumnos);		

		registerForContextMenu(this.lsvAlumnos);
		
		this.lsvAlumnos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				
				Alumno alumno = (Alumno) adapter.getItemAtPosition(position);
				
				Intent intent = new Intent(AlumnosActivity.this, RegistroActivity.class);
				intent.putExtra(Extras.ALUMNO_SELECCIONADO, alumno);
				startActivity(intent);
				
				// Toast.makeText(AlumnosActivity.this, "Click en la posicion " + position, Toast.LENGTH_SHORT).show();
			}
		});

		this.lsvAlumnos.setOnItemLongClickListener(new OnItemLongClickListener() {
					
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				
				alumno = (Alumno) adapter.getItemAtPosition(position);
				
				// Toast.makeText(AlumnosActivity.this, "Click largo en " + adapter.getItemAtPosition(position), Toast.LENGTH_LONG).show();
				return false;
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
	
		// OPCION LLAMAR
		MenuItem llamar = menu.add("Llamar");
		llamar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {				
				Intent irParaTelefono = new Intent(Intent.ACTION_CALL);
				
				Uri llamarA = Uri.parse("tel:" + alumno.getTelefono());
				irParaTelefono.setData(llamarA);
				
				startActivity(irParaTelefono);
				return false;
			}
		});
			
		// OPCION ENVIAR SMS
		MenuItem enviarSMS = menu.add("Enviar un SMS");
		enviarSMS.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaEnviarSMS = new Intent(Intent.ACTION_VIEW);
				
				Uri enviarA = Uri.parse("sms:" + alumno.getTelefono());
				irParaEnviarSMS.setData(enviarA);
				irParaEnviarSMS.putExtra("sms_body", "Estimado alumno...");
				
				startActivity(irParaEnviarSMS);
				return false;
			}
		});
		
		// OPCION VER MAPA
		MenuItem mapa = menu.add("Ver Mapa");
		mapa.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaMapa = new Intent(Intent.ACTION_VIEW);
				
				Uri mapaDeAlumno = Uri.parse("geo:0,0?z=14&q=" + alumno.getDireccion());
				irParaMapa.setData(mapaDeAlumno);
				
				startActivity(irParaMapa);				
				return false;
			}
		});
		
		// OPCION VISITAR SITE
		MenuItem site = menu.add("Visitar su Site");
		site.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irParaSite = new Intent(Intent.ACTION_VIEW);
				
				Uri localSite = Uri.parse("http://" + alumno.getSite());
				irParaSite.setData(localSite);
				
				startActivity(irParaSite);
				return false;
			}
		});
		
		// OPCION ELIMINAR
		MenuItem eliminar = menu.add("Eliminar");		
		eliminar.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
			
				AlumnoDAO dao = new AlumnoDAO(AlumnosActivity.this);
				dao.eliminar(alumno);
				cargarLista();
				dao.close();
				return false;
			}
		});
		
		// OPCION ENVIAR EMAIL
		MenuItem email = menu.add("Enviar un email");
		email.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				Intent irAEnviarEmail = new Intent(Intent.ACTION_SEND);
				irAEnviarEmail.setType("message/rfc822");
				
				irAEnviarEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"informes@joedayz.pe"});				
				irAEnviarEmail.putExtra(Intent.EXTRA_SUBJECT, new String[] {"El curso de android es gratin."});
				irAEnviarEmail.putExtra(Intent.EXTRA_TEXT, new String[] {"El curso esta chevere xD"});
				
				startActivity(irAEnviarEmail);
				
				return false;
			}
		});
		
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	private void cargarLista() {
		
		AlumnoDAO dao = new AlumnoDAO(this);

		List<Alumno> alumnos = dao.getLista();
		dao.close();

		AlumnosAdapter adapter = new AlumnosAdapter(alumnos, this); 		
			
		this.lsvAlumnos.setAdapter(adapter);		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		this.cargarLista();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.alumnos, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			case R.id.nuevo:
				Intent irParaFormulario = new Intent(this, RegistroActivity.class);
				startActivity(irParaFormulario);
				break;
	
			case R.id.mapa:
				Intent irParaMapa = new Intent(this, MuestraAlumnosProximos.class);
				startActivity(irParaMapa);
				break;
				
			case R.id.enviar_alumnos:
				
				EnviaAlumnosTask task = new EnviaAlumnosTask(this);
				task.execute();
				
				//String urlServidor = "http://android-mobile.joedayz.cloudbees.net/alumnos";
				
//				AlumnoDAO dao = new AlumnoDAO(this);
//				List<Alumno> alumnos = dao.getLista();
//				dao.close();
//				
//				String datosJSON = new AlumnoConverter().toJSON(alumnos);
//				
//				Toast.makeText(this, datosJSON, Toast.LENGTH_LONG).show();
				
				//WebClient client = new WebClient(urlServidor);
				//String respuestaJSON = client.post(datosJSON);
				
				//Toast.makeText(this, respuestaJSON, Toast.LENGTH_LONG).show();
				
				break;
				
			case R.id.recibir_pruebas:
				Intent irParaPruebas = new Intent(this, PruebasActivity.class);
				startActivity(irParaPruebas);

			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}
}
