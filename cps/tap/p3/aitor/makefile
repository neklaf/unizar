#
# FICHERO MAKEFILE PARA LA PRACTICA 3ª DE TAP
# Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
# Fecha: Agosto 2004
#

COMPILADOR=gnatmake

all: cheq clean

cheq: chequear.adb ristras_bits.ads ristras_bits.adb patricia.ads patricia.adb
	$(COMPILADOR) chequear

clean_all: clean clean_chequear

clean: 
	-rm *.o && rm *.ali

clean_chequear:
	-rm chequear

exportar:
	export TMPDIR=/tmp
