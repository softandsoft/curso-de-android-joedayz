package pe.joedayz.academyjoedayz.listener;

import com.google.android.gms.maps.model.LatLng;

import pe.joedayz.academyjoedayz.fragment.MapaFragment;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class ActualizadorDePosicion implements LocationListener {

	private LocationManager locationManager;
	private MapaFragment mapa;
	
	public ActualizadorDePosicion(Activity activity, MapaFragment mapa) {
		this.mapa = mapa;
		locationManager = (LocationManager)activity.getSystemService(Context.LOCATION_SERVICE);
		
		String provider = LocationManager.GPS_PROVIDER;
		long tiempoMinimo = 20000; // ms
		float distanciaMinima = 20; // m
		
		locationManager.requestLocationUpdates(provider, tiempoMinimo, distanciaMinima, this);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		
		LatLng local = new LatLng(latitude, longitude);
		
		mapa.central(local);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// GPS WIFI
		
	}

	private void cancelar(){
		locationManager.removeUpdates(this);
	}
}