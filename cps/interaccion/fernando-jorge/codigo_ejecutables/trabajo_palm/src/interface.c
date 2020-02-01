
#ifdef HAVE_CONFIG_H
#  include <config.h>
#endif
#include <stdlib.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>
#include <gdk/gdkkeysyms.h>
#include <gtk/gtk.h>

#include "callbacks.h"
#include "interface.h"
#include "support.h"
  char *precio;
  char c[20];//sino no funciona.no se pq es
  FILE *mi_file;
  int carta,pcomb,menu,cafe,bebida,primera;	
  float precio_total;  
  float *precio_puntero;

  GtkWidget *window1;
  GtkWidget *fixed1;
  GtkWidget *button1;
  GtkWidget *button2;
  GtkWidget *button3;
  GtkWidget *vseparator2;
  GtkWidget *button5;
  GtkWidget *button7;
  GtkWidget *button8;
  GtkWidget *button11;
  GtkWidget *button12;
  GtkWidget *button13;
  GtkWidget *hseparator3;
  GtkWidget *vseparator4;
  GtkWidget *hseparator1;
  GtkWidget *hseparator4;
  GtkWidget *label1;
  GtkWidget *vseparator6;
  GtkWidget *vseparator7;
  GtkWidget *vseparator5;
//  GtkWidget *button14;
  GtkWidget *label8;
  GtkWidget *hseparator5;
  GtkWidget *combo3;
  GList *combo3_items = NULL;
  GtkWidget *combo_entry3;
  GtkWidget *hseparator2;
  GtkWidget *vseparator1;
  GtkWidget *label3;
  GtkWidget *label6;
  GtkWidget *combo6;
  GList *combo6_items = NULL;
  GtkWidget *combo_entry6;
  GtkWidget *label11;
  GtkWidget *label10;
  GtkWidget *label9;
  GtkWidget *combo11;
  GList *combo11_items = NULL;
  GtkWidget *combo_entry11;
  GtkWidget *vseparator3;
  GtkWidget *vseparator8;
  GtkWidget *hseparator6;
  GtkWidget *combo13;
  GList *combo13_items = NULL;
  GtkWidget *combo_entry13;
  GtkWidget *combo4;
  GList *combo4_items = NULL;
  GtkWidget *combo_entry4;
  GtkWidget *combo5;
  GList *combo5_items = NULL;
  GtkWidget *combo_entry5;
  GtkWidget *label4;
  GtkWidget *label5;
  GtkWidget *combo7;
  GList *combo7_items = NULL;
  GtkWidget *combo_entry7;
  GtkWidget *combo8;
  GList *combo8_items = NULL;
  GtkWidget *combo_entry8;
  GtkWidget *combo9;
  GList *combo9_items = NULL;
  GtkWidget *combo_entry9;
  GtkWidget *combo10;
  GList *combo10_items = NULL;
  GtkWidget *combo_entry10;
  GtkWidget *combo12;
  GList *combo12_items = NULL;
  GtkWidget *combo_entry12;
  GtkWidget *combo1;
  GList *combo1_items = NULL;
  GtkWidget *combo_entry1;
  GtkWidget *label2;
  GtkWidget *combo2;
  GList *combo2_items = NULL;
  GtkWidget *combo_entry2;
  GtkWidget *label7;
  GtkWidget *entry4;




GtkWidget*
create_window1 (void)
{

  window1 = gtk_window_new (GTK_WINDOW_TOPLEVEL);
  gtk_object_set_data (GTK_OBJECT (window1), "window1", window1);
  gtk_window_set_title (GTK_WINDOW (window1), "window1");

  fixed1 = gtk_fixed_new ();
  gtk_widget_ref (fixed1);
  gtk_object_set_data_full (GTK_OBJECT (window1), "fixed1", fixed1,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (fixed1);
  gtk_container_add (GTK_CONTAINER (window1), fixed1);

  button1 = gtk_button_new_with_label ("Grabar");
  gtk_widget_ref (button1);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button1", button1,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button1);
  gtk_fixed_put (GTK_FIXED (fixed1), button1, 200, 352);
  gtk_widget_set_uposition (button1, 200, 352);
  gtk_widget_set_usize (button1, 61, 26);

  button2 = gtk_button_new_with_label ("Parar");
  gtk_widget_ref (button2);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button2", button2,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button2);
  gtk_fixed_put (GTK_FIXED (fixed1), button2, 264, 352);
  gtk_widget_set_uposition (button2, 264, 352);
  gtk_widget_set_usize (button2, 61, 26);

  button3 = gtk_button_new_with_label ("Enviar");
  gtk_widget_ref (button3);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button3", button3,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button3);
  gtk_fixed_put (GTK_FIXED (fixed1), button3, 176, 384);
  gtk_widget_set_uposition (button3, 176, 384);
  gtk_widget_set_usize (button3, 61, 26);

  vseparator2 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator2);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator2", vseparator2,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator2);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator2, 8, 8);
  gtk_widget_set_uposition (vseparator2, 8, 8);
  gtk_widget_set_usize (vseparator2, 16, 480);

  button5 = gtk_button_new_with_label ("Menu");
  gtk_widget_ref (button5);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button5", button5,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button5);
  gtk_fixed_put (GTK_FIXED (fixed1), button5, 176, 104);
  gtk_widget_set_uposition (button5, 176, 104);
  gtk_widget_set_usize (button5, 61, 26);

  button7 = gtk_button_new_with_label ("Plato Combinado");
  gtk_widget_ref (button7);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button7", button7,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button7);
  gtk_fixed_put (GTK_FIXED (fixed1), button7, 176, 72);
  gtk_widget_set_uposition (button7, 176, 72);
  gtk_widget_set_usize (button7, 136, 24);

  button8 = gtk_button_new_with_label ("Carta");
  gtk_widget_ref (button8);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button8", button8,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button8);
  gtk_fixed_put (GTK_FIXED (fixed1), button8, 248, 104);
  gtk_widget_set_uposition (button8, 248, 104);
  gtk_widget_set_usize (button8, 61, 26);

  button11 = gtk_button_new_with_label ("Bebidas");
  gtk_widget_ref (button11);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button11", button11,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button11);
  gtk_fixed_put (GTK_FIXED (fixed1), button11, 328, 72);
  gtk_widget_set_uposition (button11, 328, 72);
  gtk_widget_set_usize (button11, 70, 26);

  button12 = gtk_button_new_with_label ("Cafes");
  gtk_widget_ref (button12);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button12", button12,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button12);
  gtk_fixed_put (GTK_FIXED (fixed1), button12, 328, 104);
  gtk_widget_set_uposition (button12, 328, 104);
  gtk_widget_set_usize (button12, 70, 26);
/*
  button14 = gtk_button_new_with_label ("Ayuda");
  gtk_widget_ref (button14);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button14", button14,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button14);
  gtk_fixed_put (GTK_FIXED (fixed1), button14, 328, 425);
  gtk_widget_set_uposition (button14, 328, 425);
  gtk_widget_set_usize (button14, 70, 26);

  */
  hseparator3 = gtk_hseparator_new ();
  gtk_widget_ref (hseparator3);
  gtk_object_set_data_full (GTK_OBJECT (window1), "hseparator3", hseparator3,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (hseparator3);
  gtk_fixed_put (GTK_FIXED (fixed1), hseparator3, 24, 16);
  gtk_widget_set_uposition (hseparator3, 24, 16);
  gtk_widget_set_usize (hseparator3, 384, 16);

  vseparator4 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator4);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator4", vseparator4,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator4);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator4, 400, 24);
  gtk_widget_set_uposition (vseparator4, 400, 24);
  gtk_widget_set_usize (vseparator4, 16, 392);

  hseparator1 = gtk_hseparator_new ();
  gtk_widget_ref (hseparator1);
  gtk_object_set_data_full (GTK_OBJECT (window1), "hseparator1", hseparator1,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (hseparator1);
  gtk_fixed_put (GTK_FIXED (fixed1), hseparator1, 16, 0);
  gtk_widget_set_uposition (hseparator1, 16, 0);
  gtk_widget_set_usize (hseparator1, 400, 16);

  hseparator4 = gtk_hseparator_new ();
  gtk_widget_ref (hseparator4);
  gtk_object_set_data_full (GTK_OBJECT (window1), "hseparator4", hseparator4,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (hseparator4);
  gtk_fixed_put (GTK_FIXED (fixed1), hseparator4, 16, 480);
  gtk_widget_set_uposition (hseparator4, 16, 480);
  gtk_widget_set_usize (hseparator4, 400, 16);

  label1 = gtk_label_new ("Camarero");
  gtk_widget_ref (label1);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label1", label1,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label1);
  gtk_fixed_put (GTK_FIXED (fixed1), label1, 32, 40);
  gtk_widget_set_uposition (label1, 32, 40);
  gtk_widget_set_usize (label1, 72, 16);

  vseparator6 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator6);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator6", vseparator6,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator6);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator6, 392, 64);
  gtk_widget_set_uposition (vseparator6, 392, 64);
  gtk_widget_set_usize (vseparator6, 16, 72);

  vseparator7 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator7);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator7", vseparator7,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator7);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator7, 312, 64);
  gtk_widget_set_uposition (vseparator7, 312, 64);
  gtk_widget_set_usize (vseparator7, 16, 72);

  vseparator5 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator5);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator5", vseparator5,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator5);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator5, 160, 64);
  gtk_widget_set_uposition (vseparator5, 160, 64);
  gtk_widget_set_usize (vseparator5, 16, 72);

  button13 = gtk_button_new_with_label ("Confirmar");
  gtk_widget_ref (button13);
  gtk_object_set_data_full (GTK_OBJECT (window1), "button13", button13,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (button13);
  gtk_fixed_put (GTK_FIXED (fixed1), button13, 80, 384);
  gtk_widget_set_uposition (button13, 80, 384);
  gtk_widget_set_usize (button13, 80, 24);

  label8 = gtk_label_new ("- - - - - - - - - -\n- - - - - - -\n- - -");
  gtk_widget_ref (label8);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label8", label8,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label8);
  gtk_fixed_put (GTK_FIXED (fixed1), label8, 136, 416);
  gtk_widget_set_uposition (label8, 136, 416);
  gtk_widget_set_usize (label8, 128, 56);

  hseparator5 = gtk_hseparator_new ();
  gtk_widget_ref (hseparator5);
  gtk_object_set_data_full (GTK_OBJECT (window1), "hseparator5", hseparator5,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (hseparator5);
  gtk_fixed_put (GTK_FIXED (fixed1), hseparator5, 168, 128);
  gtk_widget_set_uposition (hseparator5, 168, 128);
  gtk_widget_set_usize (hseparator5, 232, 16);

  combo3 = gtk_combo_new ();
  gtk_widget_ref (combo3);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo3", combo3,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo3);
  gtk_fixed_put (GTK_FIXED (fixed1), combo3, 120, 144);
  gtk_widget_set_uposition (combo3, 120, 144);
  gtk_widget_set_usize (combo3, 224, 24);
  combo3_items = g_list_append (combo3_items, (gpointer) "");
  combo3_items = g_list_append (combo3_items, (gpointer) "Paella");
  combo3_items = g_list_append (combo3_items, (gpointer) "Macarrones");
  combo3_items = g_list_append (combo3_items, (gpointer) "Revuelto de setas");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo3), combo3_items);
  g_list_free (combo3_items);

  combo_entry3 = GTK_COMBO (combo3)->entry;
  gtk_widget_ref (combo_entry3);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry3", combo_entry3,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry3);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry3), FALSE);

  hseparator2 = gtk_hseparator_new ();
  gtk_widget_ref (hseparator2);
  gtk_object_set_data_full (GTK_OBJECT (window1), "hseparator2", hseparator2,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (hseparator2);
  gtk_fixed_put (GTK_FIXED (fixed1), hseparator2, 24, 408);
  gtk_widget_set_uposition (hseparator2, 24, 408);
  gtk_widget_set_usize (hseparator2, 384, 16);

  vseparator1 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator1);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator1", vseparator1,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator1);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator1, 16, 24);
  gtk_widget_set_uposition (vseparator1, 16, 24);
  gtk_widget_set_usize (vseparator1, 16, 392);

  label3 = gtk_label_new ("1\272");
  gtk_widget_ref (label3);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label3", label3,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label3);
  gtk_fixed_put (GTK_FIXED (fixed1), label3, 72, 144);
  gtk_widget_set_uposition (label3, 72, 144);
  gtk_widget_set_usize (label3, 42, 20);

  label6 = gtk_label_new ("Peticiones Especiales");
  gtk_widget_ref (label6);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label6", label6,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label6);
  gtk_fixed_put (GTK_FIXED (fixed1), label6, 32, 352);
  gtk_widget_set_uposition (label6, 32, 352);
  gtk_widget_set_usize (label6, 160, 24);

  combo6 = gtk_combo_new ();
  gtk_widget_ref (combo6);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo6", combo6,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo6);
  gtk_fixed_put (GTK_FIXED (fixed1), combo6, 152, 320);
  gtk_widget_set_uposition (combo6, 152, 320);
  gtk_widget_set_usize (combo6, 240, 24);
  combo6_items = g_list_append (combo6_items, (gpointer) "");
  combo6_items = g_list_append (combo6_items, (gpointer) "Plato Combinado 1");
  combo6_items = g_list_append (combo6_items, (gpointer) "Plato Combinado 2");
  combo6_items = g_list_append (combo6_items, (gpointer) "Plato Combinado 3");
  combo6_items = g_list_append (combo6_items, (gpointer) "Plato Combinado 4");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo6), combo6_items);
  g_list_free (combo6_items);

  combo_entry6 = GTK_COMBO (combo6)->entry;
  gtk_widget_ref (combo_entry6);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry6", combo_entry6,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry6);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry6), FALSE);

  label11 = gtk_label_new ("Pl Combinados");
  gtk_widget_ref (label11);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label11", label11,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label11);
  gtk_fixed_put (GTK_FIXED (fixed1), label11, 32, 328);
  gtk_widget_set_uposition (label11, 32, 328);
  gtk_widget_set_usize (label11, 112, 16);

  label10 = gtk_label_new ("Caf\351s");
  gtk_widget_ref (label10);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label10", label10,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label10);
  gtk_fixed_put (GTK_FIXED (fixed1), label10, 32, 288);
  gtk_widget_set_uposition (label10, 32, 288);
  gtk_widget_set_usize (label10, 48, 16);

  label9 = gtk_label_new ("Bebidas");
  gtk_widget_ref (label9);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label9", label9,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label9);
  gtk_fixed_put (GTK_FIXED (fixed1), label9, 32, 256);
  gtk_widget_set_uposition (label9, 32, 256);
  gtk_widget_set_usize (label9, 64, 16);

  combo11 = gtk_combo_new ();
  gtk_widget_ref (combo11);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo11", combo11,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo11);
  gtk_fixed_put (GTK_FIXED (fixed1), combo11, 104, 280);
  gtk_widget_set_uposition (combo11, 104, 280);
  gtk_widget_set_usize (combo11, 240, 24);
  combo11_items = g_list_append (combo11_items, (gpointer) "");
  combo11_items = g_list_append (combo11_items, (gpointer) "Cortado");
  combo11_items = g_list_append (combo11_items, (gpointer) "Cortado con Hielo");
  combo11_items = g_list_append (combo11_items, (gpointer) "Solo");
  combo11_items = g_list_append (combo11_items, (gpointer) "Solo con Hielo");
  combo11_items = g_list_append (combo11_items, (gpointer) "Cafe con leche");
  combo11_items = g_list_append (combo11_items, (gpointer) "Bomb\363n");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo11), combo11_items);
  g_list_free (combo11_items);

  combo_entry11 = GTK_COMBO (combo11)->entry;
  gtk_widget_ref (combo_entry11);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry11", combo_entry11,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry11);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry11), FALSE);

  vseparator3 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator3);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator3", vseparator3,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator3);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator3, 400, 24);
  gtk_widget_set_uposition (vseparator3, 400, 24);
  gtk_widget_set_usize (vseparator3, 16, 376);

  vseparator8 = gtk_vseparator_new ();
  gtk_widget_ref (vseparator8);
  gtk_object_set_data_full (GTK_OBJECT (window1), "vseparator8", vseparator8,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (vseparator8);
  gtk_fixed_put (GTK_FIXED (fixed1), vseparator8, 408, 8);
  gtk_widget_set_uposition (vseparator8, 408, 8);
  gtk_widget_set_usize (vseparator8, 16, 480);

  hseparator6 = gtk_hseparator_new ();
  gtk_widget_ref (hseparator6);
  gtk_object_set_data_full (GTK_OBJECT (window1), "hseparator6", hseparator6,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (hseparator6);
  gtk_fixed_put (GTK_FIXED (fixed1), hseparator6, 168, 56);
  gtk_widget_set_uposition (hseparator6, 168, 56);
  gtk_widget_set_usize (hseparator6, 232, 16);

  combo13 = gtk_combo_new ();
  gtk_widget_ref (combo13);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo13", combo13,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo13);
  gtk_fixed_put (GTK_FIXED (fixed1), combo13, 352, 280);
  gtk_widget_set_uposition (combo13, 352, 280);
  gtk_widget_set_usize (combo13, 48, 24);
  combo13_items = g_list_append (combo13_items, (gpointer) "");
  combo13_items = g_list_append (combo13_items, (gpointer) "1");
  combo13_items = g_list_append (combo13_items, (gpointer) "2");
  combo13_items = g_list_append (combo13_items, (gpointer) "3");
  combo13_items = g_list_append (combo13_items, (gpointer) "4");
  combo13_items = g_list_append (combo13_items, (gpointer) "5");
  combo13_items = g_list_append (combo13_items, (gpointer) "6");
  combo13_items = g_list_append (combo13_items, (gpointer) "7");
  combo13_items = g_list_append (combo13_items, (gpointer) "8");
  combo13_items = g_list_append (combo13_items, (gpointer) "9");
  combo13_items = g_list_append (combo13_items, (gpointer) "10");
  combo13_items = g_list_append (combo13_items, (gpointer) "11");
  combo13_items = g_list_append (combo13_items, (gpointer) "12");
  combo13_items = g_list_append (combo13_items, (gpointer) "13");
  combo13_items = g_list_append (combo13_items, (gpointer) "14");
  combo13_items = g_list_append (combo13_items, (gpointer) "15");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo13), combo13_items);
  g_list_free (combo13_items);

  combo_entry13 = GTK_COMBO (combo13)->entry;
  gtk_widget_ref (combo_entry13);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry13", combo_entry13,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry13);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry13), FALSE);

  combo4 = gtk_combo_new ();
  gtk_widget_ref (combo4);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo4", combo4,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo4);
  gtk_fixed_put (GTK_FIXED (fixed1), combo4, 120, 176);
  gtk_widget_set_uposition (combo4, 120, 176);
  gtk_widget_set_usize (combo4, 224, 24);
  combo4_items = g_list_append (combo4_items, (gpointer) "");
  combo4_items = g_list_append (combo4_items, (gpointer) "Lomo");
  combo4_items = g_list_append (combo4_items, (gpointer) "Ternera");
  combo4_items = g_list_append (combo4_items, (gpointer) "Trucha");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo4), combo4_items);
  g_list_free (combo4_items);

  combo_entry4 = GTK_COMBO (combo4)->entry;
  gtk_widget_ref (combo_entry4);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry4", combo_entry4,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry4);

  combo5 = gtk_combo_new ();
  gtk_widget_ref (combo5);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo5", combo5,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo5);
  gtk_fixed_put (GTK_FIXED (fixed1), combo5, 120, 208);
  gtk_widget_set_uposition (combo5, 120, 208);
  gtk_widget_set_usize (combo5, 224, 24);
  combo5_items = g_list_append (combo5_items, (gpointer) "");
  combo5_items = g_list_append (combo5_items, (gpointer) "Helado");
  combo5_items = g_list_append (combo5_items, (gpointer) "Fruta");
  combo5_items = g_list_append (combo5_items, (gpointer) "Sorbete limon");
  combo5_items = g_list_append (combo5_items, (gpointer) "Sorbete Mango");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo5), combo5_items);
  g_list_free (combo5_items);

  combo_entry5 = GTK_COMBO (combo5)->entry;
  gtk_widget_ref (combo_entry5);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry5", combo_entry5,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry5);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry5), FALSE);

  label4 = gtk_label_new ("2\272");
  gtk_widget_ref (label4);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label4", label4,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label4);
  gtk_fixed_put (GTK_FIXED (fixed1), label4, 72, 176);
  gtk_widget_set_uposition (label4, 72, 176);
  gtk_widget_set_usize (label4, 42, 20);

  label5 = gtk_label_new ("3\272");
  gtk_widget_ref (label5);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label5", label5,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label5);
  gtk_fixed_put (GTK_FIXED (fixed1), label5, 72, 208);
  gtk_widget_set_uposition (label5, 72, 208);
  gtk_widget_set_usize (label5, 42, 20);

  combo7 = gtk_combo_new ();
  gtk_widget_ref (combo7);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo7", combo7,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo7);
  gtk_fixed_put (GTK_FIXED (fixed1), combo7, 128, 144);
  gtk_widget_set_uposition (combo7, 128, 144);
  gtk_widget_set_usize (combo7, 224, 24);
  combo7_items = g_list_append (combo7_items, (gpointer) "");
  combo7_items = g_list_append (combo7_items, (gpointer) "Esparragos Trigueros");
  combo7_items = g_list_append (combo7_items, (gpointer) "Revuelto de setas con gambas");
  combo7_items = g_list_append (combo7_items, (gpointer) "Coctel de mariscos");
  combo7_items = g_list_append (combo7_items, (gpointer) "Ensaladilla italiana");
  combo7_items = g_list_append (combo7_items, (gpointer) "Ensalada Campera");
  combo7_items = g_list_append (combo7_items, (gpointer) "Raviolis a los 4 quesos");
  combo7_items = g_list_append (combo7_items, (gpointer) "Arroz Negro");
  combo7_items = g_list_append (combo7_items, (gpointer) "Paella");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo7), combo7_items);
  g_list_free (combo7_items);

  combo_entry7 = GTK_COMBO (combo7)->entry;
  gtk_widget_ref (combo_entry7);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry7", combo_entry7,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry7);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry7), FALSE);

  combo8 = gtk_combo_new ();
  gtk_widget_ref (combo8);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo8", combo8,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo8);
  gtk_fixed_put (GTK_FIXED (fixed1), combo8, 128, 176);
  gtk_widget_set_uposition (combo8, 128, 176);
  gtk_widget_set_usize (combo8, 224, 24);
  combo8_items = g_list_append (combo8_items, (gpointer) "");
  combo8_items = g_list_append (combo8_items, (gpointer) "Rodaballo al licor");
  combo8_items = g_list_append (combo8_items, (gpointer) "Ri\361ones al Jerez");
  combo8_items = g_list_append (combo8_items, (gpointer) "Solomillo a la pimienta");
  combo8_items = g_list_append (combo8_items, (gpointer) "Mero a las finas hierbas");
  combo8_items = g_list_append (combo8_items, (gpointer) "Codornices en su jugo");
  combo8_items = g_list_append (combo8_items, (gpointer) "Ternasco con patatas a lo pobre");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo8), combo8_items);
  g_list_free (combo8_items);

  combo_entry8 = GTK_COMBO (combo8)->entry;
  gtk_widget_ref (combo_entry8);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry8", combo_entry8,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry8);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry8), FALSE);

  combo9 = gtk_combo_new ();
  gtk_widget_ref (combo9);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo9", combo9,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo9);
  gtk_fixed_put (GTK_FIXED (fixed1), combo9, 128, 208);
  gtk_widget_set_uposition (combo9, 128, 208);
  gtk_widget_set_usize (combo9, 224, 24);
  combo9_items = g_list_append (combo9_items, (gpointer) "");
  combo9_items = g_list_append (combo9_items, (gpointer) "Fresas con Nata");
  combo9_items = g_list_append (combo9_items, (gpointer) "Nueces con miel");
  combo9_items = g_list_append (combo9_items, (gpointer) "Cuajada");
  combo9_items = g_list_append (combo9_items, (gpointer) "Sorbete al limon");
  combo9_items = g_list_append (combo9_items, (gpointer) "Pudding de coco");
  combo9_items = g_list_append (combo9_items, (gpointer) "Pijama");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo9), combo9_items);
  g_list_free (combo9_items);

  combo_entry9 = GTK_COMBO (combo9)->entry;
  gtk_widget_ref (combo_entry9);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry9", combo_entry9,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry9);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry9), FALSE);

  combo10 = gtk_combo_new ();
  gtk_widget_ref (combo10);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo10", combo10,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo10);
  gtk_fixed_put (GTK_FIXED (fixed1), combo10, 104, 248);
  gtk_widget_set_uposition (combo10, 104, 248);
  gtk_widget_set_usize (combo10, 240, 24);
  combo10_items = g_list_append (combo10_items, (gpointer) "");
  combo10_items = g_list_append (combo10_items, (gpointer) "Agua");
  combo10_items = g_list_append (combo10_items, (gpointer) "Gaseosa");
  combo10_items = g_list_append (combo10_items, (gpointer) "Cerveza");
  combo10_items = g_list_append (combo10_items, (gpointer) "Vino");
  combo10_items = g_list_append (combo10_items, (gpointer) "Coca Cola");
  combo10_items = g_list_append (combo10_items, (gpointer) "Limonada ");
  combo10_items = g_list_append (combo10_items, (gpointer) "Naranjada");
  combo10_items = g_list_append (combo10_items, (gpointer) "Zumo");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo10), combo10_items);
  g_list_free (combo10_items);

  combo_entry10 = GTK_COMBO (combo10)->entry;
  gtk_widget_ref (combo_entry10);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry10", combo_entry10,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry10);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry10), FALSE);

  combo12 = gtk_combo_new ();
  gtk_widget_ref (combo12);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo12", combo12,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo12);
  gtk_fixed_put (GTK_FIXED (fixed1), combo12, 352, 248);
  gtk_widget_set_uposition (combo12, 352, 248);
  gtk_widget_set_usize (combo12, 48, 24);
  combo12_items = g_list_append (combo12_items, (gpointer) "");
  combo12_items = g_list_append (combo12_items, (gpointer) "1");
  combo12_items = g_list_append (combo12_items, (gpointer) "2");
  combo12_items = g_list_append (combo12_items, (gpointer) "3");
  combo12_items = g_list_append (combo12_items, (gpointer) "4");
  combo12_items = g_list_append (combo12_items, (gpointer) "5");
  combo12_items = g_list_append (combo12_items, (gpointer) "6");
  combo12_items = g_list_append (combo12_items, (gpointer) "7");
  combo12_items = g_list_append (combo12_items, (gpointer) "8");
  combo12_items = g_list_append (combo12_items, (gpointer) "9");
  combo12_items = g_list_append (combo12_items, (gpointer) "10");
  combo12_items = g_list_append (combo12_items, (gpointer) "11");
  combo12_items = g_list_append (combo12_items, (gpointer) "12");
  combo12_items = g_list_append (combo12_items, (gpointer) "13");
  combo12_items = g_list_append (combo12_items, (gpointer) "14");
  combo12_items = g_list_append (combo12_items, (gpointer) "15");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo12), combo12_items);
  g_list_free (combo12_items);

  combo_entry12 = GTK_COMBO (combo12)->entry;
  gtk_widget_ref (combo_entry12);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry12", combo_entry12,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry12);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry12), FALSE);

  combo1 = gtk_combo_new ();
  gtk_widget_ref (combo1);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo1", combo1,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo1);
  gtk_fixed_put (GTK_FIXED (fixed1), combo1, 120, 32);
  gtk_widget_set_uposition (combo1, 120, 32);
  gtk_widget_set_usize (combo1, 240, 24);
  combo1_items = g_list_append (combo1_items, (gpointer) "");
  combo1_items = g_list_append (combo1_items, (gpointer) "1.- Negre Ramos, Fernando");
  combo1_items = g_list_append (combo1_items, (gpointer) "2.- Mart\355n Casamayor, Jorge");
  combo1_items = g_list_append (combo1_items, (gpointer) "3.- Mateo Lopez, Sergio");
  combo1_items = g_list_append (combo1_items, (gpointer) "4.- Olivan Garcia, Laura");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo1), combo1_items);
  g_list_free (combo1_items);

  combo_entry1 = GTK_COMBO (combo1)->entry;
  gtk_widget_ref (combo_entry1);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry1", combo_entry1,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry1);
  gtk_entry_set_editable (GTK_ENTRY (combo_entry1), FALSE);

  label2 = gtk_label_new ("Mesa");
  gtk_widget_ref (label2);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label2", label2,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label2);
  gtk_fixed_put (GTK_FIXED (fixed1), label2, 56, 64);
  gtk_widget_set_uposition (label2, 56, 64);
  gtk_widget_set_usize (label2, 42, 20);

  combo2 = gtk_combo_new ();
  gtk_widget_ref (combo2);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo2", combo2,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo2);
  gtk_fixed_put (GTK_FIXED (fixed1), combo2, 120, 64);
  gtk_widget_set_uposition (combo2, 120, 64);
  gtk_widget_set_usize (combo2, 40, 24);
  combo2_items = g_list_append (combo2_items, (gpointer) "");
  combo2_items = g_list_append (combo2_items, (gpointer) "1");
  combo2_items = g_list_append (combo2_items, (gpointer) "2");
  combo2_items = g_list_append (combo2_items, (gpointer) "3");
  combo2_items = g_list_append (combo2_items, (gpointer) "4");
  combo2_items = g_list_append (combo2_items, (gpointer) "5");
  combo2_items = g_list_append (combo2_items, (gpointer) "6");
  combo2_items = g_list_append (combo2_items, (gpointer) "7");
  combo2_items = g_list_append (combo2_items, (gpointer) "8");
  combo2_items = g_list_append (combo2_items, (gpointer) "9");
  combo2_items = g_list_append (combo2_items, (gpointer) "10");
  combo2_items = g_list_append (combo2_items, (gpointer) "11");
  combo2_items = g_list_append (combo2_items, (gpointer) "12");
  combo2_items = g_list_append (combo2_items, (gpointer) "13");
  combo2_items = g_list_append (combo2_items, (gpointer) "14");
  combo2_items = g_list_append (combo2_items, (gpointer) "15");
  gtk_combo_set_popdown_strings (GTK_COMBO (combo2), combo2_items);
  g_list_free (combo2_items);

  combo_entry2 = GTK_COMBO (combo2)->entry;
  gtk_widget_ref (combo_entry2);
  gtk_object_set_data_full (GTK_OBJECT (window1), "combo_entry2", combo_entry2,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (combo_entry2);

  label7 = gtk_label_new ("Factura:");
  gtk_widget_ref (label7);
  gtk_object_set_data_full (GTK_OBJECT (window1), "label7", label7,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (label7);
  gtk_fixed_put (GTK_FIXED (fixed1), label7, 256, 384);
  gtk_widget_set_uposition (label7, 256, 384);
  gtk_widget_set_usize (label7, 72, 24);

  entry4 = gtk_entry_new ();
  gtk_widget_ref (entry4);
  gtk_object_set_data_full (GTK_OBJECT (window1), "entry4", entry4,
                            (GtkDestroyNotify) gtk_widget_unref);
  gtk_widget_show (entry4);
  gtk_fixed_put (GTK_FIXED (fixed1), entry4, 328, 384);
  gtk_widget_set_uposition (entry4, 328, 384);
  gtk_widget_set_usize (entry4, 64, 24);

  gtk_signal_connect (GTK_OBJECT (button1), "clicked",
                      GTK_SIGNAL_FUNC (on_button1_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button2), "clicked",
                      GTK_SIGNAL_FUNC (on_button2_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button3), "clicked",
                      GTK_SIGNAL_FUNC (on_button3_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button5), "clicked",
                      GTK_SIGNAL_FUNC (on_button5_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button7), "clicked",
                      GTK_SIGNAL_FUNC (on_button7_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button8), "clicked",
                      GTK_SIGNAL_FUNC (on_button8_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button11), "clicked",
                      GTK_SIGNAL_FUNC (on_button11_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button12), "clicked",
                      GTK_SIGNAL_FUNC (on_button12_clicked),
                      NULL);
  gtk_signal_connect (GTK_OBJECT (button13), "clicked",
                      GTK_SIGNAL_FUNC (on_button13_clicked),
                      NULL);


//---------LO QUE HE TRAIDO DESDE EL CALLBACKS.C----------------------------
	gtk_widget_hide(combo3);
	gtk_widget_hide(combo4);
	gtk_widget_hide(combo5);
	gtk_widget_hide(combo6);
	gtk_widget_hide(combo7);
	gtk_widget_hide(combo8);
	gtk_widget_hide(combo9);
	gtk_widget_hide(combo10);
	gtk_widget_hide(combo11);
	gtk_widget_hide(combo12);
	gtk_widget_hide(combo13);
	gtk_widget_hide(label3);
	gtk_widget_hide(label4);
	gtk_widget_hide(label5);
	gtk_widget_hide(label9);
	gtk_widget_hide(label10);
	gtk_widget_hide(label11);
	gtk_widget_hide(entry4);


	//Ficheros-----------------------------------------------------
	mi_file=fopen("factura","w");
        
	//booleanas-------------------------------------------------------
	carta=0;
	pcomb=0;
	menu=0;
	cafe=0;
	bebida=0;	
	primera=1;	
	return window1;
}
void on_button1_clicked (GtkButton *button,gpointer user_data)
{
printf("GRABANDO PETICIONES ESPECIALES....\n");
}

void on_button2_clicked(GtkButton *button,gpointer         user_data)
{
printf("PETICION ESPECIAL GRABADA\n");
}


void
on_button3_clicked                     (GtkButton       *button,
                                        gpointer         user_data)
{
printf("ENVIANDO SOLICITUD DE COMIDA....\n");
fprintf(mi_file,"\n	Factura = %.2f E\n",precio_total);
precio_total=precio_total*1.16;
fprintf(mi_file,"\n	Total Factura (incluido iva 16%)= %.2f E\n",precio_total);
fprintf(mi_file,"____________________________________________________________\n");
fclose(mi_file);
// *char<----float  sprintf(c,"%f%",float)
//Me vuelca la salida del printf en la variable C.
//Los cast sirven para tipos de variables parecidos.
//OJO la variable c[20] acotarla en posiciones.
printf("precio=%2.2f\n",precio_total);
sprintf(c,"%.2f",precio_total);
gtk_widget_show(entry4);
gtk_entry_set_text(entry4,c);

}

void
on_button5_clicked                     (GtkButton       *button,
                                        gpointer         user_data)
{

gtk_widget_show(combo3);
gtk_widget_show(combo4);
gtk_widget_show(combo5);
gtk_widget_hide(combo6);
gtk_widget_hide(combo7);
gtk_widget_hide(combo8);
gtk_widget_hide(combo9);
gtk_widget_hide(combo10);
gtk_widget_hide(combo11);
gtk_widget_hide(combo12);
gtk_widget_hide(combo13);
gtk_widget_show(label3);
gtk_widget_show(label4);
gtk_widget_show(label5);
gtk_widget_hide(label9);
gtk_widget_hide(label10);
gtk_widget_hide(label11);
gtk_widget_hide(entry4);

carta=0;
pcomb=0;
menu=1;
cafe=0;
bebida=0;	
	
}

void
on_button7_clicked                     (GtkButton       *button,
                                        gpointer         user_data)
{

	gtk_widget_hide(combo3);
	gtk_widget_hide(combo4);
	gtk_widget_hide(combo5);
	gtk_widget_show(combo6);
	gtk_widget_hide(combo7);
	gtk_widget_hide(combo8);
	gtk_widget_hide(combo9);
	gtk_widget_hide(combo10);
	gtk_widget_hide(combo11);
	gtk_widget_hide(combo12);
	gtk_widget_hide(combo13);
	gtk_widget_hide(label3);
	gtk_widget_hide(label4);
	gtk_widget_hide(label5);
	gtk_widget_hide(label9);
	gtk_widget_hide(label10);
	gtk_widget_show(label11);
	gtk_widget_hide(entry4);

carta=0;
pcomb=1;
menu=0;
cafe=0;
bebida=0;	


}


void
on_button8_clicked                     (GtkButton       *button,
                                        gpointer         user_data)
{

	gtk_widget_hide(combo3);
	gtk_widget_hide(combo4);
	gtk_widget_hide(combo5);
	gtk_widget_hide(combo6);
	gtk_widget_show(combo7);
	gtk_widget_show(combo8);
	gtk_widget_show(combo9);
	gtk_widget_hide(combo10);
	gtk_widget_hide(combo11);
	gtk_widget_hide(combo12);
	gtk_widget_hide(combo13);
	gtk_widget_show(label3);
	gtk_widget_show(label4);
	gtk_widget_show(label5);
	gtk_widget_hide(label9);
	gtk_widget_hide(label10);
	gtk_widget_hide(label11);
	gtk_widget_hide(entry4);


carta=1;
pcomb=0;
menu=0;
cafe=0;
bebida=0;	

}


void
on_button9_clicked                     (GtkButton       *button,
                                        gpointer         user_data)
{
}


void
on_button11_clicked                     (GtkButton       *button,
                                        gpointer         user_data)
{

	gtk_widget_hide(combo3);
	gtk_widget_hide(combo4);
	gtk_widget_hide(combo5);
	gtk_widget_hide(combo6);
	gtk_widget_hide(combo7);
	gtk_widget_hide(combo8);
	gtk_widget_hide(combo9);
	gtk_widget_show(combo10);
	gtk_widget_hide(combo11);
	gtk_widget_show(combo12);
	gtk_widget_hide(combo13);
	gtk_widget_hide(label3);
	gtk_widget_hide(label4);
	gtk_widget_hide(label5);
	gtk_widget_show(label9);
	gtk_widget_hide(label10);
	gtk_widget_hide(label11);
	gtk_widget_hide(entry4);

carta=0;
pcomb=0;
menu=0;
cafe=0;
bebida=1;	

}

void
on_button12_clicked                     (GtkButton       *button,
                                        gpointer         user_data)
{
	gtk_widget_hide(combo3);
	gtk_widget_hide(combo4);
	gtk_widget_hide(combo5);
	gtk_widget_hide(combo6);
	gtk_widget_hide(combo7);
	gtk_widget_hide(combo8);
	gtk_widget_hide(combo9);
	gtk_widget_hide(combo10);
	gtk_widget_show(combo11);
	gtk_widget_hide(combo12);
	gtk_widget_show(combo13);
	gtk_widget_hide(label3);
	gtk_widget_hide(label4);
	gtk_widget_hide(label5);
	gtk_widget_hide(label9);
	gtk_widget_show(label10);
	gtk_widget_hide(label11);
	gtk_widget_hide(entry4);

carta=0;
pcomb=0;
menu=0;
cafe=1;
bebida=0;	

}

void on_button13_clicked(GtkButton *button,gpointer user_data)
{
char *text_mesa,*text_camarero,*text_plcomb,*text_bebida,*text_bebida_num,*text_cafe,*text_cafe_num;
char *text_menu1,*text_menu2,*text_menu3,*text_carta1,*text_carta2,*text_carta3;
char text_plcomb_char,*text_plcomb_ok,*kk;
float precio_menu,precio_plcomb,precio_carta1,precio_carta2,precio_carta3;
float precio_bebida,precio_bebida2,precio_cafe,precio_cafe2;
int num_bebida,num_cafe;

   text_camarero = gtk_entry_get_text(combo_entry1);
   text_mesa = gtk_entry_get_text(combo_entry2);
   text_menu1 = gtk_entry_get_text(combo_entry3);
   text_menu2 = gtk_entry_get_text(combo_entry4);
   text_menu3 = gtk_entry_get_text(combo_entry5);
   text_plcomb = gtk_entry_get_text(combo_entry6);
   text_carta1 = gtk_entry_get_text(combo_entry7);
   text_carta2 = gtk_entry_get_text(combo_entry8);
   text_carta3 = gtk_entry_get_text(combo_entry9);
   text_bebida = gtk_entry_get_text(combo_entry10);
   text_cafe = gtk_entry_get_text(combo_entry11);
   text_bebida_num = gtk_entry_get_text(combo_entry12);
   text_cafe_num = gtk_entry_get_text(combo_entry13);
num_bebida=atoi(text_bebida_num);
num_cafe=atoi(text_cafe_num);
if (primera==1){
   fprintf(mi_file,"MESA: %s (Camarero: %s )---------------------\n",text_mesa,text_camarero);
   primera=0;
}
if (pcomb==1){
  precio_plcomb=0;
  if (strncmp(text_plcomb,"Plato Combinado 1",17)==0){
	  printf("el 1");
	  precio_plcomb=3.5;}
  if (strncmp(text_plcomb,"Plato Combinado 2",17)==0) precio_plcomb=4;
  if (strncmp(text_plcomb,"Plato Combinado 3",17)==0) precio_plcomb=4.5;
  if (strncmp(text_plcomb,"Plato Combinado 4",17)==0){
	  printf("el 4");
	  precio_plcomb=5;}
  precio_total=precio_total+precio_plcomb;
  fprintf(mi_file," Plato combinado: (%.2f E)\n",precio_plcomb);
  fprintf(mi_file,"  %s\n",text_plcomb);
  //pcomb=0; Por si quiero repetir peticiones sobre platos combinados
}
if (carta==1){
  precio_carta1=0;
  precio_carta2=0;
  precio_carta3=0;
  if (strncmp(text_carta1,"Esparragos Trigueros",19)==0) precio_carta1=5;
  if (strncmp(text_carta1,"Revuelto de setas con gambas",27)==0)precio_carta1=5;
  if (strncmp(text_carta1,"Coctel de mariscos",17)==0) precio_carta1=5.5;
  if (strncmp(text_carta1,"Ensaladilla italiana",19)==0) precio_carta1=4;
  if (strncmp(text_carta1,"Ensalada Campera",15)==0) precio_carta1=4;
  if (strncmp(text_carta1,"Raviolis a los 4 quesos",22)==0) precio_carta1=4.5;
  if (strncmp(text_carta1,"Arroz Negro",10)==0) precio_carta1=4;
  if (strncmp(text_carta1,"Paella",5)==0) precio_carta1=5;
  if (strncmp(text_carta2,"Rodaballo al licor",17)==0) precio_carta2=5.5;
  if (strncmp(text_carta2,"Riñones al Jerez",15)==0) precio_carta2=6;
  if (strncmp(text_carta2,"Mero a las finas hierbas",23)==0) precio_carta2=6;
  if (strncmp(text_carta2,"Codornices en su jugo",20)==0) precio_carta2=6;
  if (strncmp(text_carta2,"Ternasco con patatas a lo pobre",30)==0) precio_carta2=5;
  if (strncmp(text_carta3,"Fresas con Nata",14)==0) precio_carta3=3;
  if (strncmp(text_carta3,"Nueces con miel",14)==0) precio_carta3=3;
  if (strncmp(text_carta3,"Cuajada",6)==0) precio_carta3=2;
  if (strncmp(text_carta3,"Sorbete al limon",15)==0) precio_carta3=2;
  if (strncmp(text_carta3,"Pudding de coco",14)==0) precio_carta3=3;
  if (strncmp(text_carta3,"Pijama",5)==0) precio_carta3=2;
  precio_total=precio_total+precio_carta1+precio_carta2+precio_carta3;
  fprintf(mi_file," Carta:\n");
  fprintf(mi_file,"  %s (%.2f E)\n",text_carta1,precio_carta1);
  fprintf(mi_file,"  %s (%.2f E)\n",text_carta2,precio_carta2);
  fprintf(mi_file,"  %s (%.2f E)\n",text_carta3,precio_carta3);
  //carta=0;
}

if (menu==1){
   precio_menu=8.5;
   fprintf(mi_file," Menu: (precio fijo %.1f E)\n",precio_menu);
   fprintf(mi_file,"  %s\n",text_menu1);
   fprintf(mi_file,"  %s\n",text_menu2);
   fprintf(mi_file,"  %s\n",text_menu3);
   precio_total=precio_total+precio_menu;
   //menu=0;
}

if (bebida==1){
   precio_bebida=0;
   if (strncmp(text_bebida,"Agua",3)==0) precio_bebida=0.8;
   if (strncmp(text_bebida,"Gaseosa",6)==0) precio_bebida=0.8;
   if (strncmp(text_bebida,"Cerveza",6)==0) precio_bebida=1.5;
   if (strncmp(text_bebida,"Vino",3)==0) precio_bebida=2;
   if (strncmp(text_bebida,"Coca Cola",8)==0) precio_bebida=1.5;
   if (strncmp(text_bebida,"Limonada",7)==0) precio_bebida=1.5;
   if (strncmp(text_bebida,"Naranjada",8)==0) precio_bebida=1.5;
   if (strncmp(text_bebida,"Zumo",3)==0) precio_bebida=1.5;
   precio_bebida2=precio_bebida * num_bebida; 
   precio_total=precio_total+precio_bebida2;
   fprintf(mi_file," Bebidas:\n");
   fprintf(mi_file,"  %s x %s unidades (%.2f x %i = %.2f E )\n",text_bebida,text_bebida_num,precio_bebida,num_bebida,precio_bebida2);
   //bebida=0;
}

if (cafe==1){
   precio_cafe=0;
   if (strncmp(text_cafe,"Cortado",6)==0) precio_cafe=1.2;
   if (strncmp(text_cafe,"Cortado con Hielo",16)==0) precio_cafe=1.4;
   if (strncmp(text_cafe,"Solo",3)==0) precio_cafe=1.2;
   if (strncmp(text_cafe,"Solo con Hielo",13)==0) precio_cafe=1.4;
   if (strncmp(text_cafe,"Cafe con leche",13)==0) precio_cafe=1.2;
   if (strncmp(text_cafe,"Bombón",5)==0) precio_cafe=2;
   precio_cafe2=precio_cafe * num_cafe; 
   precio_total=precio_total + precio_cafe2;
   fprintf(mi_file," Cafes:\n");
   fprintf(mi_file,"  %s x %s unidades (%.2f x %i = %.2f E )\n",text_cafe,text_cafe_num,precio_cafe,num_cafe,precio_cafe2);
   //cafe=0;
}
}
/*
void on_button14_clicked(GtkButton *button,gpointer user_data)
{
system("ls");
system("sh<mozilla file:/home/nacs/Projects/Ok_project/web/int-h-m/palm/ayuda.htm");

}
*/
