/* comienzo del ejemplo holamundo */
#include <gtk/gtk.h>

/* Esta es una funcion respuesta (callback). Sus argumentos
   son ignorados por en este ejemplo */
void hello (GtkWidget *widget, gpointer data)
{
    g_print ("Hola mundo\n");
}

gint delete_event(GtkWidget *widget, GdkEvent *event, gpointer data)
{
    /* si se devuelve FALSE al administrador de llamadas
     * "delete_event", GTK emitira la señal de destruccion
     * "destroy". Esto es util para dialogos emergentes del
     * tipo: ¿Seguro que desea salir?

    g_print ("Ha ocurrido un evento delete\n");

    /* Cambiando TRUE por FALSE la ventana se destruira con "delete_event" */
    g_print ("Ha ocurrido un evento delete\n");
    return (TRUE);	//De esta manera no elimina la ventana
    //return FALSE; 	//Asi elimina la ventana
}

/* otra respuesta */
void destroy (GtkWidget *widget, gpointer data)
{
    //g_print("Este es el otro evento tratado desde la ventana\n");
    gtk_main_quit ();
}

int main (int argc, char *argv[])
{

    /* GtkWidget es el tipo de almacenamiento usado para los
     * widgets */
    GtkWidget *ventana;
    GtkWidget *boton;

    /* En cualquier aplicacion hay que realizar la siguiente
     * llamada. Los argumentos son tomados de la lÃ­nea de comandos
     * y devueltos a la aplicacion. */

    gtk_init (&argc, &argv);
    
    /* creamos una ventana nueva */
    ventana = gtk_window_new (GTK_WINDOW_TOPLEVEL);
    
    /* Cuando la ventana recibe la señal "delete_event" (emitida
     * por el gestor de ventanas, normalmente mediante la opciÃ³n
     * 'close', o en la barra del titulo) hacemos que llame a la
     * funcion delete_event() tal y como ya hemos visto. Los datos
     * pasados a la funcion de respuesta son NULL, y seran ignorados. */
    gtk_signal_connect (GTK_OBJECT (ventana), "delete_event",
                        GTK_SIGNAL_FUNC (delete_event), NULL);

    /* Aqui­ conectamos el evento "destroy" con el administrador de
     * señales. El evento se produce cuando llamamos a
     * gtk_widget_destroy() desde la ventana o si devolvemos 'FALSE'
     * en la respuesta "delete_event". */
    gtk_signal_connect (GTK_OBJECT (ventana), "destroy",
                        GTK_SIGNAL_FUNC (destroy), NULL);
    
    /* establecemos el ancho del borde de la ventana. */
    gtk_container_border_width (GTK_CONTAINER (ventana), 10);
    
    /* creamos un boton nuevo con la  etiqueta "Hola mundo" */
    boton = gtk_button_new_with_label ("Hola mundo");
    
    /* Cuando el boton recibe la señal "clicked" llama a la
     * funcion hello() pasandole NULL como argumento. (La
     * funcion ya ha sido definida arriba). */
    gtk_signal_connect (GTK_OBJECT (boton), "clicked",
                        GTK_SIGNAL_FUNC (hello), NULL);
    
    /* Esto hara que la ventana sea destruida llamando a
     * gtk_widget_destroy(ventana) cuando se produzca "clicked". Una
     * vez mas la señal de destruccion puede provenir del gestor
     * de ventanas o de aqui. */
     gtk_signal_connect_object (GTK_OBJECT (boton), "clicked",
                               GTK_SIGNAL_FUNC (gtk_widget_destroy),
                               GTK_OBJECT (ventana));
    
    /* Ahora empaquetamos el boton en la ventana (usamos un gtk
     * container ). */
    gtk_container_add (GTK_CONTAINER (ventana), boton);
    
    /* El ultimo paso es representar el nuevo widget... */
    gtk_widget_show (boton);
    
    /* y la ventana */
    gtk_widget_show (ventana);
    
    /* Todas las aplicaciones basadas en GTK deben tener una llamada
     * gtk_main() ya que el control termina justo aqui y debe
     * esperar a que suceda algun evento */

    gtk_main ();
    
    return 0;
}
/* final del ejemplo*/
