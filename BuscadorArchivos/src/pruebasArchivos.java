import java.io.File;

public class pruebasArchivos {
	
	public static void listFilesForFolder( File folder) {
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            System.out.println(fileEntry.getName() + " --- localizado en: --- " +fileEntry.getParent());
	        }
	    }
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File folder = new File("/home/baboon/Desktop/PruebasMatesCompus");
		listFilesForFolder(folder);

	}

}
