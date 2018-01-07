#!/usr/bin/ksh
#############################################################################
##ESQUEMAS ALGORITMICOS
##
##Fichero:      ejecutar1.sh 
##Fecha:        Noviembre 2003
##Version:      1.0
##Autores:
##      Acedo Legarre, Aitor    460829@celes.unizar.es  A5189044
##      Morata Miguel, Carolina cmmzarag@yahoo.es       M7757179
##
##Descripc:    Contiene el script para la compilacion y la ejecucion de los 
		ejemplos de la practica primera de esquemas algoritmicos
#############################################################################
echo "Compilando..."
export TMPDIR=/tmp
gnatmake merklehelman
./merklehelman -p 1 > ejemplo1 
./merklehelman -p 2 > ejemplo2
more ejemplo1
more ejemplo2
