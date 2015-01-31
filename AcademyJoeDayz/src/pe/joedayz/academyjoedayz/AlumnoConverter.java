package pe.joedayz.academyjoedayz;

import java.util.List;

import org.json.JSONStringer;

import pe.joedayz.academyjoedayz.modelo.Alumno;

public class AlumnoConverter {

	public String toJSON(List<Alumno> alumnos) {

		try {
			JSONStringer js = new JSONStringer();
			
			js.object().key("alumnos").array();
			
			for (Alumno alumno: alumnos) {
				js.object();
				js.key("nombre").value(alumno.getNombre());
				js.key("nota").value(alumno.getNota());
				js.endObject();
			}
			
			js.endArray().endObject();
			
			return js.toString();
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}