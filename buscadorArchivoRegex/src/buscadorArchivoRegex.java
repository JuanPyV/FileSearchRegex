

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class buscadorArchivoRegex {

	public static void main(String[] args) {

		final String path;
		final String regex;

		regex = JOptionPane.showInputDialog("Ingrese la expresion regular: ");
		if (regex == null) {
			System.exit(0);
		}

		path = JOptionPane.showInputDialog("Ingrese path: ");
		if (path == null) {
			System.exit(0);
		}

		File folder = new File(path);
		ArrayList<File> files = new ArrayList<File>();

		ArrayList<File> fileList = listaArchivo(folder, files);

		for (File file : fileList) {
			System.out.println(file.getName() + ", " + file.getPath());

			boolean found = buscaUnion(regex, file.getName().toLowerCase());

			boolean foundText = false;

			// Si regresa falso significa que no lo encontro en el titulo y lo buscara
			// dentro
			if (found == false) {
				try {
					if (leeTexto(regex, new BufferedReader(new FileReader(file))) == true)
						found = true;
					foundText = true;

				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}

			if (found == true) {
				System.out.println();
				System.out.println("Expresion: " + regex);
				System.out.println("Archivo: " + file.getName());
				System.out.println("Ruta: " + file.getAbsolutePath());
				System.out.println("Coincide: "+found);
				if (foundText == true) {
					System.out.println("--Coincidencia encontrada en el texto");
				}
			} else {
				System.out.println("No coincide");
			}

			System.out.println("--------------------------");
		}
		;

	}

	private static ArrayList<File> listaArchivo(File directoryName, ArrayList<File> files) {

		for (File file : directoryName.listFiles()) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				listaArchivo(file, files);
			}
		}
		return files;
	}

	private static boolean buscaUnion(String regex, String text) {
		ArrayList<String> partes = new ArrayList<>();
		int i = 0;
		int j = 0;
		boolean found = false;

		//
		while (j <= regex.length()) {
			if (j == regex.length() || regex.charAt(j) == '+') {
				// a�adimos lo que esta antes del + en el arraylist
				// o a�adimos toda el regex
				partes.add(regex.substring(i, j));
				// el indice i = j + i nos servira para ubicar el caracter despues del +
				i = j + 1;
			}
			j++;
		}
		// para cada elemento en la lista
		for (String rgx : partes) {
			if (found != true) {
				found = evaluarRegex(rgx, text);
			} else
				break;
		}

		return found;
	}

	private static boolean evaluarRegex(String rgx, String text) {
		int k = 0;
		int r = 0;
		int c = 0;

		while (k < text.length()) {
			for (int p = k; p < text.length(); p++) {
				// System.out.println(rgx.charAt(r) + " " + txt.charAt(p));

				if (r >= rgx.length() - 1 && (rgx.charAt(r) == '*' || rgx.charAt(r) == text.charAt(p)))
					return true;
				else if (p == text.length() - 1)
					return false;

				// Cerradura
				// indice p para la palabra
				// indice r para el rexeg
				else if ((r + 1) < rgx.length() && rgx.charAt(r + 1) == '*' && r < rgx.length() - 1) {
					if (rgx.charAt(r) == text.charAt(p)) {
						// la letra antes del * y la letra de la palabra en el mismo indice
						// Se incrementa el indice para la palabra mientras la letra sea la misma
						// que el caracter antes del asterisco en rgx
						do {
							p++;
						} while (rgx.charAt(r) == text.charAt(p));
						// se le suma dos pq 1 es el caracter y el otro es el *, y asi r y c apuntan
						// al caracter despues del *
						c = r += 2;
						while (r >= rgx.length())
							r--;
						k = p;
						// resto una p pq en el for se suma una
						p--;
					} else {
						// si los caracteres no son iguales, solo aumento el caracter de la palabra
						// hasta
						// para que se haga la primera condicion

						if (r >= 0 && r < rgx.length()) {
							while (r >= rgx.length())
								r--;
							k++;
							/*
							 * if (r >= 0 && r < rgx.length()) { c = r += 2; while (r >= rgx.length()) r--;
							 * p++; k = p; p--;
							 */
						} else {
							p++;
							k = p;
							p--;
						}
					}
				}

				// Si no coincide que el caracter despues del que se lee es *

				// verifica si las letras coinciden
				else if (rgx.charAt(r) == text.charAt(p)) {
					if (r == c)
						k = p;
					r++;
					while (r >= rgx.length())
						r--;

					// si las letras no coinciden solo regresa los apuntadores como estaba
					// pq en el for de arriba la j avanzara al siguiente caracter en la palabra
				} else {
					if (r != c)
						p = k;

					r = 0;
					c = 0;
				}
			}
		}
		return false;
	}

	private static boolean leeTexto(String rgx, BufferedReader br) throws IOException {
		String line = br.readLine();
		System.out.println("///// TEXTO ///////");
		while (line != null) {//de preferencia no poner "== true" !line
			if (buscaUnion(rgx, line) == true)
				return true;
			// lee la proxima linea
			line = br.readLine();
		}
		return false;
	}
}
