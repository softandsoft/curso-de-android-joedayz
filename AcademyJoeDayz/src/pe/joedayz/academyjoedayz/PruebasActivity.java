package pe.joedayz.academyjoedayz;

import pe.joedayz.academyjoedayz.fragment.DetallesPruebaFragment;
import pe.joedayz.academyjoedayz.fragment.ListaPruebasFragment;
import pe.joedayz.academyjoedayz.modelo.Prueba;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class PruebasActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.pruebas);
		
		if (bundle == null) {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();	
			
			if (isTablet()) {
				transaction
					.replace(R.id.pruebas_view, new ListaPruebasFragment(), ListaPruebasFragment.class.getCanonicalName())
					.replace(R.id.pruebas_detalle, new DetallesPruebaFragment(), DetallesPruebaFragment.class.getCanonicalName());				
			}
			else{
				transaction.replace(R.id.pruebas_view, new ListaPruebasFragment(), ListaPruebasFragment.class.getCanonicalName());
			}
			
			transaction.commit();
		}
	}
	
	public boolean isTablet(){
		return getResources().getBoolean(R.bool.isTablet);
	}

	public void seleccionaPrueba(Prueba prueba) {
		Bundle argumentos = new Bundle();
		
		argumentos.putSerializable("prueba", prueba);
		
		DetallesPruebaFragment detallesPrueba = new DetallesPruebaFragment();
		detallesPrueba.setArguments(argumentos);
		
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		
		transaction.replace(R.id.pruebas_view, detallesPrueba, DetallesPruebaFragment.class.getCanonicalName());
		
		if (!isTablet()) {
			transaction.addToBackStack(null);
		}
		
		transaction.commit();
	}
}