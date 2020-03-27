import java.util.ArrayList;
import java.util.Stack;

public class pruebasPostFixRegex2 {

	public static ArrayList<Character> expresionPostfijo(String Expresion) {

		String exp = "(" + Expresion + ")";
		String[] arrayInfijo = exp.split("");

		Stack<String> pilaEntrada = new Stack<String>();
		Stack<String> pilaTemp = new Stack<String>();
		Stack<String> pilaSalida = new Stack<String>();

		for (int i = arrayInfijo.length - 1; i >= 0; i--) {
			pilaEntrada.push(arrayInfijo[i]);
		}

		while (!pilaEntrada.isEmpty()) {
			switch (jerarquia(pilaEntrada.peek())) {
			case 1:
				pilaTemp.push(pilaEntrada.pop());
				break;
			case 3:
			case 4:
			case 5:
				while (jerarquia(pilaTemp.peek()) >= jerarquia(pilaEntrada.peek())) {
					pilaSalida.push(pilaTemp.pop());
				}
				pilaTemp.push(pilaEntrada.pop());
				break;
			case 2:
				while (!pilaTemp.peek().equals("(")) {
					pilaSalida.push(pilaTemp.pop());
				}
				pilaTemp.pop();
				pilaEntrada.pop();
				break;
			default:
				pilaSalida.push(pilaEntrada.pop());
			}
		}

		String expresionPosfija = pilaSalida.toString().replace("[", "").replace("]", "").replace(",", "").replace(" ",
				"");

		ArrayList<Character> chars = new ArrayList<Character>();
		for (char c : expresionPosfija.toCharArray()) {
			//System.out.println(c);
			chars.add(c);
		}
		return chars;
	}

	private static int jerarquia(String op) {
		if (op.equals("*"))
			return 5;
		if (op.equals("."))
			return 4;
		if (op.equals("+"))
			return 3;
		if (op.equals(")"))
			return 2;
		if (op.equals("("))
			return 1;
		return 0;
	}

	public static void main(String[] args) {

		String expr = "a.(b.b)+a";
		System.out.println(expr);
		System.out.println("Expresion Posfija: " + expresionPostfijo(expr));

	}
}