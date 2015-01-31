package pe.joedayz.academyjoedayz.dao;

import java.util.ArrayList;
import java.util.List;

import pe.joedayz.academyjoedayz.modelo.Alumno;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AlumnoDAO extends SQLiteOpenHelper {

	private static final String DATABASE = "RegistroJoeDayz";
	private static final int VERSION = 1;

	public AlumnoDAO(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String ddl = "CREATE TABLE Alumnos (id INTEGER PRIMARY KEY, "
				+ "nombre TEXT UNIQUE NOT NULL, " + "site TEXT, "
				+ "direccion TEXT, " + "telefono TEXT, " + "nota REAL, "
				+ "foto TEXT); ";

		db.execSQL(ddl);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String ddl = "DROP TABLE IF EXISTS Alumnos";
		db.execSQL(ddl);

		this.onCreate(db);
	}

	public List<Alumno> getLista() {

		String[] columnas = { "id", "nombre", "site", "direccion", "telefono",
				"nota", "foto" };

		Cursor cursor = getWritableDatabase().query("Alumnos", columnas, null,
				null, null, null, null);

		ArrayList<Alumno> alumnos = new ArrayList<Alumno>();

		while (cursor.moveToNext()) {
			Alumno alumno = new Alumno();

			alumno.setId(cursor.getLong(0));
			alumno.setNombre(cursor.getString(1));
			alumno.setSite(cursor.getString(2));
			alumno.setDireccion(cursor.getString(3));
			alumno.setTelefono(cursor.getString(4));
			alumno.setNota(cursor.getDouble(5));
			alumno.setFoto(cursor.getString(6));

			alumnos.add(alumno);
		}

		return alumnos;
	}

	public void guardar(Alumno alumno) {
		ContentValues values = new ContentValues();

		values.put("nombre", alumno.getNombre());
		values.put("site", alumno.getSite());
		values.put("direccion", alumno.getDireccion());
		values.put("telefono", alumno.getTelefono());
		values.put("nota", alumno.getNota());
		values.put("foto", alumno.getFoto());

		getWritableDatabase().insert("Alumnos", null, values);
	}

	public void modificar(Alumno alumno) {
		ContentValues values = new ContentValues();

		values.put("nombre", alumno.getNombre());
		values.put("site", alumno.getSite());
		values.put("direccion", alumno.getDireccion());
		values.put("telefono", alumno.getTelefono());
		values.put("nota", alumno.getNota());
		values.put("foto", alumno.getFoto());

		String[] whereArgs = { alumno.getId().toString() };
		
		getWritableDatabase().update("Alumnos", values, "id=?", whereArgs);				
	}
	
	public void eliminar(Alumno alumno) {
		String[] whereArgs = { alumno.getId().toString() };
		getWritableDatabase().delete("Alumnos", "id=?", whereArgs);
	}

	public boolean isAlumno(String telefono) {
		String[] args = { telefono };
		Cursor cursor = getWritableDatabase().rawQuery("SELECT id FROM Alumnos WHERE telefono=?", args);
		
		boolean existePrimero = cursor.moveToFirst();
				
		return existePrimero;
	}

}
