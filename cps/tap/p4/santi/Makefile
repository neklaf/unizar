# Técnicas Avanzadas de Programación - Curso 2003/04
# Aitor Acedo & Santiago Faci
# Makefile para práctica 4
CC=gnatmake
SOURCES=test_splay_tree.adb test_lista_auto.adb splay_tree.adb splay_tree.ads lista_auto.ads lista_auto.adb
COMPILE1=test_lista_auto.adb
COMPILE2=test_splay_tree.adb 
TARGET=*.o *.ali test_splay_tree test_lista_auto
RESULTADOS=resultados_practica4.txt

practica1:	$(SOURCES)
		$(CC) $(COMPILE1)
		$(CC) $(COMPILE2)
		touch $(RESULTADOS)
		
clean:		$(TARGET)
		rm $(TARGET)
