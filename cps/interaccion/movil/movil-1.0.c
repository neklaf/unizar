#include <gtk/gtk.h>

/*
 * Un poco de teoria:
 *
 * Un evento es una accion que el usuario puede realizar en un determinado
 * componente del interfaz grafico.
 *
 * Cuando se lanza un evento lo que ocurre es que un widget emite una señal
 * y dependiendo de si se captura la señal o de que funcion se ejecute
 * se producira un efecto u otro.
 * 
 * */

/* Este es un ejemplo de una funcion callback o tambien llamada funcion 
 * respuesta para un determinado evento y tiene un determinado prototipo.
 * El prototipo es el siguiente:
 *
 * void call_func(GtkWidget *widget, gpointer data);
 *
 * */

/*Poniendo solamente esta funcion de respuesta no es necesario para poder
 * borrar la ventana por lo que tendremos que introducir otra  funcion.*/
gint delete_event(GtkWidget *widget, GdkEvent *event, gpointer data){
	return TRUE; //Asi no se destruye la ventana
	//return FALSE; //Asi se destruye la ventana
}

/*Pero tendremos que desbloquear a la aplicacion del bucle en el que se ha 
 * metido, gtk_main();*/

void destroyMain(GtkWidget *widget, gpointer data){
	/*Simplemente hace un break del bucle en el que se queda el main*/
	gtk_main_quit();
}

/*Funcion de callback (respuesta) para el boton*/
/*Vamos a aportar mas informacion a la respuesta*/
void hello (GtkWidget *widget, gpointer data){
	g_print("El boton presionado es el %s\n", (char *) data);
}

int main (int argc, char *argv[]){
	/* DECLARACION DE LAS VARIABLES*/
	GtkWidget *ventana;
	GtkWidget *boton;
	GtkWidget *caja1, *caja2;
	/*******************************/

	
	gtk_init(&argc, &argv);	//SIEMPRE!!

	//Creacion de la ventana y configuracion del titulo
	ventana = gtk_window_new(GTK_WINDOW_TOPLEVEL);
	gtk_window_set_title (GTK_WINDOW (ventana), "Mis Botones");
	

	/*Captura del evento 'delete_event'que le llega a la ventana*/
	gtk_signal_connect(GTK_OBJECT (ventana), "delete_event",
			   GTK_SIGNAL_FUNC (delete_event), NULL);
	/*Controlador para una señal emitida por la ventana*/
	/***********************************************************/	

	/*Otra captura del evento 'delete_event'*/
	gtk_signal_connect(GTK_OBJECT (ventana), "delete_event",
			   GTK_SIGNAL_FUNC(destroyMain), NULL);
	/*****************************************/

	/* Configuramos el borde de la ventana*/
	gtk_container_border_width(GTK_CONTAINER (ventana), 10);


	/*CAJA 1*/

	/*Creamos un widget que realmente no se ve*/
	caja1 = gtk_hbox_new(FALSE, 0);

	/*Ponemos la caja en la ventana*/
	gtk_container_add(GTK_CONTAINER (ventana), caja1);
	

	/* Podriamos tener la misma variable para las dos cajas pero 
	 * me parece una chorrada*/

	
	/*CAJA 2*/
	caja2 = gtk_hbox_new(FALSE, 0);

	/*Ponemos la caja en la ventana*/
	gtk_container_add(GTK_CONTAINER (ventana), caja2);

	
	/*BOTON*/

	/*Antes de conectar ningn controlador para el widget hay que crearlo*/
	boton = gtk_button_new_with_label("Boton 1");
	
	/*Captura del evento 'clicked' en el boton*/
	gtk_signal_connect(GTK_OBJECT (boton), "clicked",
			   GTK_SIGNAL_FUNC (hello), (gpointer) "boton 1");
	/*******************************/

	/* NOTA IMPORTANTE: a la funcion de respuesta no le tenemos que poner 
	 * un identificador que sea el mismo de una de las variables 
	 * declaradas dentro del main*/

	/*Ahora tenemos que poner el boton en la ventana!!*/
	//gtk_container_add(GTK_CONTAINER (ventana), boton);
	/*CAMBIO*/
	gtk_box_pack_start(GTK_BOX (caja1), boton, TRUE, TRUE, 0);

	
	
	/* Mostramos el boton pero no se vera nada porque la ventana 
	 * todavia no esta pintada. Se han finalizado los preparativos
	 * para el boton.*/
	gtk_widget_show(boton);

	/*BOTON2*/
	boton = gtk_button_new_with_label("Boton 2");
	
	gtk_signal_connect(GTK_OBJECT (boton), "clicked",
			   GTK_SIGNAL_FUNC (hello), (gpointer) "boton 2");

	/* El cuarto parametro son solamente datos que podremos utilizar
	 * en la funcion respuesta.*/	
	
	/*Introducimos el segundo boton en la caja invisible*/
	gtk_box_pack_start(GTK_BOX (caja1), boton, TRUE, TRUE, 0);

	/*Anunciamos que el boton 2 ya esta dispuesto*/
	gtk_widget_show(boton);	


	/*BOTON 3*/
	boton = gtk_button_new_with_label("Salir");

	gtk_signal_connect(GTK_OBJECT (boton), "clicked", 
			   GTK_SIGNAL_FUNC (gtk_widget_destroy),
			   GTK_OBJECT (ventana));

	/*Introducimos el boton tres en la nueva caja*/
	gtk_box_pack_start(GTK_BOX (caja2), boton, TRUE, TRUE, 0);

	/*Anunciamos que el boton 3 ya esta dispuesto*/
	gtk_widget_show(boton);	
	
	/*CAMBIO DOBLE*/
	gtk_widget_show(caja2);
	//gtk_widget_show(caja1);

	/* Ahora pintamos la ventana en la pantalla*/
	gtk_widget_show(ventana);

	gtk_main();

	return 0;
}
