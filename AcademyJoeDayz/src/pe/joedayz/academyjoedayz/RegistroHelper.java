package pe.joedayz.academyjoedayz;

import pe.joedayz.academyjoedayz.modelo.Alumno;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

public class RegistroHelper {

	private EditText edtNombre;
	private EditText edtSite;
	private EditText edtDireccion;
	private EditText edtTelefono;
	private RatingBar rtbCalificacion;
	private ImageView imgFoto;
	
	private Alumno alumno;
	
	public RegistroHelper(RegistroActivity registroActivity){
		
		edtNombre = (EditText) registroActivity.findViewById(R.id.edtNombre);
		edtSite = (EditText) registroActivity.findViewById(R.id.edtSite);
		edtDireccion = (EditText) registroActivity.findViewById(R.id.edtDireccion);
		edtTelefono = (EditText) registroActivity.findViewById(R.id.edtTelefono);
		rtbCalificacion = (RatingBar) registroActivity.findViewById(R.id.rtbCalificacion);
		imgFoto = (ImageView)registroActivity.findViewById(R.id.imgFoto);

		alumno = new Alumno();
	}

	public Alumno guardar() {
		alumno.setNombre(edtNombre.getText().toString());
		alumno.setSite(edtSite.getText().toString());
		alumno.setDireccion(edtDireccion.getText().toString());
		alumno.setTelefono(edtTelefono.getText().toString());
		alumno.setNota(Double.valueOf(rtbCalificacion.getRating()));		
		
		return alumno;
	}

	public void visualizarDatos(Alumno alumno) {
		this.alumno = alumno;
		
		edtNombre.setText(alumno.getNombre());
		edtSite.setText(alumno.getSite());
		edtDireccion.setText(alumno.getDireccion());
		edtTelefono.setText(alumno.getTelefono());
		rtbCalificacion.setRating(alumno.getNota().floatValue());
		
		if(alumno.getFoto() != null) this.cargarImagen(alumno.getFoto().toString());
	}
	
	public ImageView getFoto(){
		return imgFoto;		
	}
	
	public void cargarImagen(String rutaArchivo)	{		
		alumno.setFoto(rutaArchivo);
		
		Bitmap imagen = BitmapFactory.decodeFile(rutaArchivo);
		Bitmap imagenReducida = Bitmap.createScaledBitmap(imagen, 100, 100, true);
		
		imgFoto.setImageBitmap(imagenReducida); 
	}
}
