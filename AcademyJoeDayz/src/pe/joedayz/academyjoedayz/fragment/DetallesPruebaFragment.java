package pe.joedayz.academyjoedayz.fragment;

import pe.joedayz.academyjoedayz.R;
import pe.joedayz.academyjoedayz.modelo.Prueba;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DetallesPruebaFragment extends Fragment {
	
	private Prueba prueba;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
		View layout = inflater.inflate(R.layout.pruebas_detalle, container, false);
		
		if (getArguments() != null) {
			this.prueba = (Prueba) getArguments().getSerializable("prueba");
		}
		
		if (this.prueba != null) {
			TextView materia = (TextView) layout.findViewById(R.id.detalle_prueba_materia);
			TextView fecha = (TextView) layout.findViewById(R.id.detalle_prueba_fecha);
			ListView topicos = (ListView) layout.findViewById(R.id.detalle_prueba_topicos);
			
			materia.setText(this.prueba.getMateria());
			fecha.setText(this.prueba.getFecha());
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getActivity(), 
					android.R.layout.simple_list_item_1, 
					this.prueba.getTopicos());
			
			topicos.setAdapter(adapter);
		}
		
		return layout;
	}
}