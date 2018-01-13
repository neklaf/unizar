/* principio del ejemplo helloworld2 */

#include <gtk/gtk.h>

/* Nuestra respuesta mejorada. Los argumentos de la funci√≥n se
 * imprimen en el stdout.*/
void callback (GtkWidget *widget, gpointer data)
{
    g_print ("Hello again - %s was pressed\n", (char *) data);
}

/* otra respuesta*/
void delete_event (GtkWidget *widget, GdkEvent *event, gpointer data)
{
    gtk_main_quit ();
}

int main (int argc, char *argv[])
{
    /* GtkWidget es el tipo de almacenamiento usado para los wigtes*/
    GtkWidget *ventana;
    GtkWidget *boton;
    GtkWidget *caja1;
    
    /* Esta llamada esta presente en todas las aplicaciones basadas
     * en GTK. Los argumentos introducidos a la aplicacion*/
    gtk_init (&argc, &argv);

    /* creamos una nueva ventana*/
    ventana = gtk_window_new (GTK_WINDOW_TOPLEVEL);
    
    /* Esta funcion es nueva, pone como titulo de la ventana
     * "°Hola botones!"*/ 

    gtk_window_set_title (GTK_WINDOW (ventana), "°Hola botones!");

    /* Establecemos el controlador para la llamada delete_event que
     * termina la aplicacion inmediatamente. */

    gtk_signal_connect (GTK_OBJECT (ventana), "delete_event",
                        GTK_SIGNAL_FUNC (delete_event), NULL);

    
    /* Establecemos el ancho del borde de la ventana.*/
    gtk_container_border_width (GTK_CONTAINER (ventana), 10);

    /* Creamos una caja donde empaquetaremos los widgets. El
     * procedimiento de empaquetamiento se describe en detalle en la
     * seccion correspondiente. La caja no se ve realmente, solo
     * sirve para introducir los widgets. */
    caja1 = gtk_hbox_new(FALSE, 0);

    /* ponemos la caja en la ventana principal */
    gtk_container_add (GTK_CONTAINER (ventana), caja1);

    /* Creamos un nuevo boton con la etiqueta "Boton 1". */
    boton = gtk_button_new_with_label ("Boton 1");
    
    /* Cada vez que el bot√≥n sea pulsado llamamos a la funcion
     * "callback" con un puntero a "boton 1" como argumento. */
    gtk_signal_connect (GTK_OBJECT (boton), "clicked",
                        GTK_SIGNAL_FUNC (callback), (gpointer) "boton 1");
    
    /* En lugar de gtk_container_add empaquetamos el boton en la
     * caja invisible, que a su vez ha sido empaquetado en la
     * ventana. */
    gtk_box_pack_start(GTK_BOX(caja1), boton, TRUE, TRUE, 0);

    /* Siempre se debe realizar este paso. Sirve para decirle a GTK
     * que los preparativos del boton ya se han finalizado y que
     * por tanto puede ser mostrado. */
    gtk_widget_show(boton);

    /* hacemos lo mismo para crear un segundo boton. */
    boton = gtk_button_new_with_label ("Boton 2");

    /* Llamamos a la misma funcion de respuesta pero con diferente
     * argumento: un puntero a "boton 2". */
    gtk_signal_connect (GTK_OBJECT (boton), "clicked",
                        GTK_SIGNAL_FUNC (callback), (gpointer) "boton 2");

    gtk_box_pack_start(GTK_BOX(caja1), boton, TRUE, TRUE, 0);

    /* El orden en que mostramos los botones no es realmente
     * importante, pero se recomienda mostrar la ventana la ultima
     * para que todo aparezca de golpe. */
    gtk_widget_show(boton);

    gtk_widget_show(caja1);

    gtk_widget_show (ventana);

    /* Esperamos en gtk_main a que comience el espectaculo.*/
    gtk_main ();

    return 0;
}
/* final del ejemplo*/
