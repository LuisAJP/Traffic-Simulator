package es.ucm.fdi.main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import es.ucm.fdi.ini.Ini;



public class Comprobador {
	
	/**
	 * This method run the simulator on all files that ends with .ini if the given
	 * path, and compares that output to the expected output. It assumes that for
	 * example "example.ini" the expected output is stored in "example.ini.eout".
	 * The simulator's output will be stored in "example.ini.out"
	 * 
	 * @throws IOException
	 */
	private static void test(String path) throws IOException {

		File dir = new File(path);
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ini");
			}
		});
		//Se busca dentro del directorio los ficheros
		for (File file : files) {
			//EJEMPLO
			//obtiene el fichero ex1.ini   String cadena1 =file.getAbsolutePath();
			//obtiene el fichero ex1.ini.eout   String cadena2 =file.getAbsolutePath() + ".out";
			//Obtiene el fichero ex1.ini.out String cadena3 =file.getAbsolutePath() + ".eout";
			
			//Le pasamos el nombre de los 3 ficheros, compara 2 ficheros  que son .out y .eout
			test(file.getAbsolutePath(), file.getAbsolutePath() + ".out", file.getAbsolutePath() + ".eout");
			//imprime el fichero .ini
			System.out.println(file.getAbsolutePath());
		}

	}

	private static void test(String inFile, String outFile, String expectedOutFile) throws IOException {
		//devuelve true si son iguales o false si son distintos para los ficheros .out y .eout
		boolean equalOutput = (new Ini(outFile)).equals(new Ini(expectedOutFile));
		//Si es true , que significaria que son iguales imprimiria "OK!"
		System.out.println("Resultado para: '" + inFile + "' : "
				+ (equalOutput ? "OK!" : ("distinto a la salida esperada +'" + expectedOutFile + "'")));
	}
	

	public static void main(String[] args) throws IOException, InvocationTargetException, InterruptedException {		
		//test("src/resources/examples/events/basic");
		test("misComprobaciones");
	}

}
