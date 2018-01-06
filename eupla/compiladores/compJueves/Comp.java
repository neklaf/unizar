//import java.awt.*;

class Comp {

	public static void main (String args[]) {
		String fichero;
		//FileDialog d = new FileDialog(new Frame("EjCoco"), "Elegir fichero de entrada");
		//d.show();
		//fichero = d.getFile();
		fichero = args[0];
		if (fichero != null) {
			//fichero = d.getDirectory() + fichero;
			Scanner.Init(fichero);
			Parser.Parse();
			if (Scanner.err.count == 0) {
                                System.out.println("Reconocido \"" + fichero + "\" sin errores");
			}
		}
		System.exit(0);
	}
}

