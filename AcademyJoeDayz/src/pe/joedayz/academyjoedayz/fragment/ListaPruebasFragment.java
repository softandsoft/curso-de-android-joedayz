package pe.joedayz.academyjoedayz.fragment;

import java.util.Arrays;
import java.util.List;

import pe.joedayz.academyjoedayz.PruebasActivity;
import pe.joedayz.academyjoedayz.R;
import pe.joedayz.academyjoedayz.modelo.Prueba;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaPruebasFragment extends Fragment {

	private ListView listViewPruebas;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View layoutPruebas = inflater.inflate(R.layout.activity_pruebas, container, false);

		this.listViewPruebas = (ListView) layoutPruebas.findViewById(R.id.lista_pruebas);
		
		Prueba prueba1 = new Prueba("28/01/2015", "AngularJS");
		prueba1.setTopicos(Arrays.asList("Controladores", "Vistas", "Filtros", "Directivas"));

		Prueba prueba2 = new Prueba("28/01/2015", "Ing. de Requerimientos");
		prueba2.setTopicos(Arrays.asList("Actores del negocio", "Casos de uso del negocio", "Trabajadores del negocio", "Entidades del negocio"));
		
		List<Prueba> pruebas = Arrays.asList(prueba1, prueba2);
		
		this.listViewPruebas.setAdapter(new ArrayAdapter<Prueba>(getActivity(), android.R.layout.simple_list_item_1, pruebas));

		this.listViewPruebas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int posicion, long id) {
				
				Prueba seleccionada = (Prueba) adapter.getItemAtPosition(posicion);
				
				Toast.makeText(getActivity(), "Prueba seleccionada: " + seleccionada, Toast.LENGTH_LONG).show();
				
				PruebasActivity calendarioPruebas = (PruebasActivity) getActivity();
				calendarioPruebas.seleccionaPrueba(seleccionada);
			}
		});
		
		return layoutPruebas;
	}
}