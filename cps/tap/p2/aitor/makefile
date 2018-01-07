#
# FICHERO MAKEFILE PARA LA PRACTICA 2ª DE TAP
# Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
# Fecha: Julio 2004
#

COMPILADOR=gnatmake

all: test_estatico test clean

#
#En las reglas para compilar ficheros tenemos que poner como sus dependencias
#los fuentes que queremos compilar, sino hay problemas cuando actualizamos el fuente
#
test: test_conj_dm.adb conj_dm.ads conj_dm.adb
	$(COMPILADOR) test_conj_dm

test_estatico: test_estatico_conj_dm.adb conj_dm.ads conj_dm.adb
	$(COMPILADOR) test_estatico_conj_dm

clean_all: clean clean_estatico clean_test

#
#El guion antes del rm es para evitar que fallen reglas que dependan de clean
#simplemente pq no existan ficheros .ali o .o
#
clean:
	-rm *.o && rm *.ali

clean_estatico:
	-rm test_estatico_conj_dm

clean_test:
	-rm test_conj_dm

exportar:
	export TMPDIR=/tmp
