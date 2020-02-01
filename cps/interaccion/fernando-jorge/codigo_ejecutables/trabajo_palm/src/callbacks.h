#include <gtk/gtk.h>


void
on_button1_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button2_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button3_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button4_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_optionmenu1_selection_get           (GtkWidget       *widget,
                                        GtkSelectionData *data,
                                        guint            info,
                                        guint            time,
                                        gpointer         user_data);

gboolean
on_optionmenu1_selection_clear_event   (GtkWidget       *widget,
                                        GdkEventSelection *event,
                                        gpointer         user_data);

gboolean
on_optionmenu1_selection_notify_event  (GtkWidget       *widget,
                                        GdkEventSelection *event,
                                        gpointer         user_data);

void
on_optionmenu1_selection_received      (GtkWidget       *widget,
                                        GtkSelectionData *data,
                                        guint            time,
                                        gpointer         user_data);

gboolean
on_optionmenu1_selection_request_event (GtkWidget       *widget,
                                        GdkEventSelection *event,
                                        gpointer         user_data);

void
on_button5_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button5_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button7_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button8_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button9_clicked                     (GtkButton       *button,
                                        gpointer         user_data);

void
on_button13_clicked                    (GtkButton       *button,
                                        gpointer         user_data);

void
on_button11_clicked                    (GtkButton       *button,
                                        gpointer         user_data);

void
on_button12_clicked                    (GtkButton       *button,
                                        gpointer         user_data);
