package pe.joedayz.academyjoedayz.fragment;

import java.util.List;

import pe.joedayz.academyjoedayz.dao.AlumnoDAO;
import pe.joedayz.academyjoedayz.listener.ActualizadorDePosicion;
import pe.joedayz.academyjoedayz.modelo.Alumno;
import pe.joedayz.academyjoedayz.util.Localizador;

import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaFragment extends SupportMapFragment {
	
	@Override
	public void onResume() {
		super.onResume();
		
		getMap().setMyLocationEnabled(true);
		
		new ActualizadorDePosicion(getActivity(), this);
	}
	
//	@Override
//	public void onResume() {		
//		super.onResume();
//		
//		FragmentActivity context = getActivity();
//        Localizador localizador = new Localizador(context);
//        LatLng local = localizador.getCoordenada("Iglesia San José," + " Avenida República Dominicana, Lima");
//
//        central(local);
//        //Log.i("MAPA", "Coordenadas de JoeDayz: " + local);
//        
//		AlumnoDAO dao = new AlumnoDAO(context);
//		List<Alumno> alumnos = dao.getLista();
//		dao.close();
//		
//		for(Alumno alumno: alumnos){			
//			LatLng coordenada = new Localizador(context).getCoordenada(alumno.getDireccion());
//
//			if(coordenada!=null){
//				MarkerOptions marcador = new MarkerOptions()
//					.position(coordenada).title(alumno.getNombre())
//					.snippet(alumno.getDireccion());
//				
//				// configurar marcador o titulo
//				getMap().addMarker(marcador);
//			}
//		}
//	}

	public void central(LatLng local) {
		GoogleMap map = getMap();
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(local, 17);
		map.animateCamera(update);		
	}
}