import java.io.File;
import java.util.ArrayList;

public class postfijoToArbol {
	
	public static Arbol convert(ArrayList<Character> postfix) {
		Arbol root=null;
		root = convert(postfix, root);
		return root;
	}
	
	private static Arbol convert(ArrayList<Character> postfix, Arbol root){
		if(postfix.isEmpty()) throw new NullPointerException();
		char c = postfix.remove(postfix.size() -1);
		root= new Arbol(c);
		
		if(c=='.' || c=='+') {
			root.right = convert(postfix, root.right);
			root.left = convert(postfix, root.left);
		}else if(c=='*') {
			if(!postfix.isEmpty()) root.left = convert(postfix, root.left); 
			// covers posibility of having: "*" expresion.
		}
		
		return root;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String hola = "aa*.bb.c*.+";
		
		ArrayList<Character> chars = new ArrayList<Character>();
		for (char c : hola.toCharArray()) {
			System.out.println(c);
		  chars.add(c);
		}
		
		convert(chars);

	}
	

}