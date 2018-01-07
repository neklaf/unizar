with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded,
ada.integer_text_io, ada.float_text_io, listaGenerica;
use ada.text_io, Ada.Command_Line,
Ada.Strings.Bounded,ada.integer_text_io, ada.float_text_io;
--with ada.unchecked_deallocation;
use listaTramos;
use listaParejas;


package body calculo is

--procedure dispose is new ada.unchecked_deallocation(Tramo,ptTramo);


-------------------------------------------------------------------
-- Declaracion de variables
-------------------------------------------------------------------

--fichText: Ada.text_IO.File_type;




-- DATOS DEL PROBLEMA:
figOrig, figDest, figAux: tpFigura;
vecParejas: tpVectorParejas;
numFilas:integer;
numPasos:integer;
--Matriz de costes simples y acumulados
C, costeAcum: array (0..MAX,0..MAX) of float;






-------------------------------------------------------------------
--Definicion de funciones
-------------------------------------------------------------------

--procedure inicializaFigura( fig: in out tpFigura) is

--begin

--	for i in 1..MAX loop
--		creaVacia( fig(i) ); -- crea una lista vacía por cada fila de 
--	end loop;				 -- la figura.

--end inicializaFigura;

procedure introduceDatos (numF, numP: in integer) is

begin

   numFilas:= numF;
   numPasos:= numP;

end introduceDatos;



procedure inicializaFigura (fig: in out tpFigura; nfilas: in integer) is

begin

	for i in 1..nfilas loop
		creaVacia( fig(i) ); -- crea una lista vacía por cada fila de 
	end loop;				 -- la figura.

end inicializaFigura;

procedure inicializaFigurasOD is

begin

	inicializaFigura( figOrig, MAX );
      inicializaFigura( figDest, MAX );

end inicializaFigurasOD;


-------------------------------------------------------------------
function calculaCosteSimple (ini1,ini2,long1,long2 : integer) return
float is

begin
	return (POND * float(abs(ini1 - ini2)) + (1.0 - POND) * float(abs(long1 - long2)));
end calculaCosteSimple;
-------------------------------------------------------------------


-------------------------------------------------------------------
procedure calculaMatrizCosteS(fila: in integer) is

auxOrig, auxDest: Tramo;
--n: float;

begin
        auxOrig:= observaPrimero( figOrig(fila) );
	  inicializaActual(figOrig,1);

	for i in 1 .. long( figOrig(fila) ) loop

		auxDest:= observaPrimero( figDest(fila) );
		inicializaActual(figDest,1);

				--new_line(3);
				--n:= auxOrig.ini; put(n,0,1,0); put("   ");
				--n:= auxOrig.long; put(n,0,1,0); new_line;

        C(i,0):= calculaCosteSimple(auxOrig.ini,auxOrig.ini+(auxOrig.long/2),auxOrig.long,0);
               -- Coste de dejar el tramo origen sin emparejar con ninguno del dest,
		   -- esto es equivalente a emparejarlo con un tramo destino sin longitud y
		   -- cuyo punto de inicio sea el punto medio de sí mismo.
		for j in 1 .. long ( figDest(fila) ) loop
			--n:= auxDest.ini; put(n,0,1,0); put(" ");
			--n:= auxDest.long; put(n,0,1,0); put("    ");

			C(i,j) := calculaCosteSimple(auxOrig.ini,auxDest.ini,auxOrig.long,auxDest.long);

            auxDest:= siguienteActual(figDest(fila)); --auxDest.ptSig;

		end loop;

        auxOrig:= siguienteActual(figOrig(fila)); -- auxOrig.ptSig;

	end loop;

       auxDest:= observaPrimero( figDest(fila) );
       for j in 1 .. long ( figDest(fila) ) loop
         	C(0,j) := calculaCosteSimple(auxDest.ini+auxDest.long/2,auxDest.ini,0,auxDest.long);
               -- Coste de dejar el tramo destino sin emparejar con ninguno del origen,
			   -- esto es equivalente a emparejarlo con un tramo origen sin longitud y
			   -- cuyo punto de inicio sea el punto medio de sí mismo.
            auxDest:= siguienteActual(figDest(fila));
	end loop;

       C(0,0):= 0.0;



end calculaMatrizCosteS;
-------------------------------------------------------------------


-------------------------------------------------------------------
--function costePartir(t,t1,t2: ptTramo) return float is
function costePartir (listaA, listaB: in out listaTramos; pos1, pos2: in integer) return float is
-- Empareja un tramo de la lista A con varios tramos de la lista B (desde el
-- tramo pos1 hasta el tramo pos2).
-- Para ello trocea el tramo de la lista A en trozos proporcionales a los tramos
-- de la lista B.

aux: Tramo;
longTotal: integer;
longProp: array(1..MAX) of integer;
Prop: array (1.. MAX) of float;
suma: float;
iniProp: integer;
tramoA: Tramo;

begin

	tramoA:= dameActual(listaA);

	--Calcula la suma de las longitudes de los tramos
	longTotal := 0;
	aux:= inicializaActual(listaB, pos1);
	for i in pos1..pos2 loop
		longTotal := longTotal + aux.long;
		aux := siguienteActual(listaB); --aux.ptSig;
	end loop;
	--put("Longitud suma de los dos tramos: "); put(longTotal,0,3,0);new_line;

	--Calcular proporciones
	aux:= inicializaActual(listaB, pos1);
	for i in pos1 .. pos2 loop
		prop(i) := float(aux.long) / float(longTotal);
		--put("Proporcion tramo ");put(i,0);put(": "); put(prop(i),0,3,0);new_line;
		aux := siguienteActual(listaB);
	end loop;

	--Longitud total del tramo origen correspondiente al tramo i destino
	--aux := t1;
	for i in pos1 .. pos2 loop
		longProp(i) := integer(prop(i) * float(tramoA.long));
		--put("Longitud tramo ");put(i,0);put(": "); put(longProp(i),0,3,0);new_line;
		--aux := aux.ptSig;
	end loop;

	--Calcular los costes de emparejamiento

	aux:= inicializaActual(listaB, pos1);
	suma := 0.0;
	iniProp := tramoA.ini;
	for i in pos1 .. pos2 loop
		suma := suma + calculaCosteSimple(iniProp, aux.ini, longProp(i), aux.long);
		aux := siguienteActual(listaB);
		iniProp := iniProp + longProp(i);
	end loop;


	return suma;

end costePartir;
-------------------------------------------------------------------


-------------------------------------------------------------------
--function indexar(lista: in tpLista; num: in integer) return ptTramo is

--aux: ptTramo;

--begin

--	aux:= null;
--	aux:= new Tramo;

--	aux:= lista.primElem;
--	for i in 2..num loop
--		aux:= aux.ptSig;
--	end loop;

--	return aux;

--end indexar;
-------------------------------------------------------------------

-------------------------------------------------------------------
function costeDivision (orig, d1, d2: in integer; fila: in integer) return float is

listaOri, listaDest: listaTramos;
--numPos: integer;

begin

	listaOri:= figOrig(fila);
	inicializarActual(listaOri, orig);
	listaDest:= figDest(fila);
	--inicializaActual(listaDest, d1);
	--numPos:= d2-d1+1;

	return costePartir(listaOri, listaDest, d1, d2);

end costeDivision;
-------------------------------------------------------------------

-------------------------------------------------------------------
function costeMezcla (o1, o2, dest: in integer; fila: in integer) return float is

listaOri, listaDest: listaTramos;
--numPos: integer;

begin

	listaOri:= figOrig(fila);
	--inicializarActual(listaOri, o1);
	listaDest:= figDest(fila);
	inicializaActual(listaDest, dest);
	--numPos:= o2-o1+1;

	return costePartir(listaDest, listaOri, o1, o2);

end costeMezcla;
-------------------------------------------------------------------

procedure escribe is -- ESTE PROC ES DE PRUEBA, HAY QUE BORRARLO

n: float;

begin

	put (5/2,0);new_line;--2,5
	put(5/3,0);new_line; --1,666
	put(5/4,0);new_line; --1,25
	n:=costeDivision(1,1,2,1);
	put(" Coste division 1, 1->2 es: "); put(n,0,4,0);
	new_line(2);
	n:=costeDivision(1,1,3,1);
	put(" Coste division 1, 1->3 es: "); put(n,0,4,0);
	new_line(2);
	n:=costeMezcla(1,3,1,1);
	put(" Coste mezcla 1->3, 1 es: "); put(n,0,4,0);
	new_line(2);

end escribe;

-------------------------------------------------------------------
procedure visualizarMatriz (numFila: in integer) is

begin

	new_line(2);
	put_line(" MATRIZ DE COSTES SIMPLES: ");
	for i in 0 .. long ( figOrig(numFila) ) loop
		for j in 0.. long ( figDest(numFila) ) loop
			put( C(i,j),0,1,0); put("   ");
		end loop;
		new_line;
	end loop;
	new_line(2);

	new_line(2);
	put_line(" MATRIZ DE COSTES ACUMULADOS: ");
	for i in 0 .. long ( figOrig(numFila) ) loop
		for j in 0.. long ( figDest(numFila) ) loop
			put( costeAcum(i,j),0,1,0); put("   ");
		end loop;
		new_line;
	end loop;
	new_line(2);

end visualizarMatriz;
-------------------------------------------------------------------


-------------------------------------------------------------------
procedure borrarFigura (fig: in out tpFigura) is

begin

	for i in 1 .. numFilas loop
		libera ( fig(i) ); -- libera una fila cada vez.
	end loop;



end borrarFigura;
-------------------------------------------------------------------

procedure borrarFiguraAux is

begin

   borrarFigura(figAux);

end borrarFiguraAux;

-------------------------------------------------------------------
procedure borrarFigurasOD is

begin

	borrarFigura(figOrig);
	borrarFigura(figDest);

end borrarFigurasOD;
-------------------------------------------------------------------

-------------------------------------------------------------------
procedure calculaMatrizCosteA (fila: in integer) is

min, suma, opcion: float;
maxFila, maxColum: integer;

begin

      maxFila:= long ( figOrig(fila) ); 
      maxColum:= long ( figDest(fila) );
	-- Valores iniciales:

	costeAcum(0,0):= 0.0; --coste de no emparejar nada ni dejar libre nada.

	for i in 1..maxFila loop
            -- i indica el tramo origen a tratar
		costeAcum(i,0):= C(i,0);
	end loop;

	for j in 1..maxColum loop
            -- j indica el número de tramos dest a tener el cuenta
		costeAcum(0,j):= costeAcum(0,j-1) + C(0,j);
	end loop;

	-- Valores obtenidos con la funcion de prog dinamica con recursion hacia atras:
	min:=0.0;
	for orig in 1..maxFila loop
             -- orig indica el tramo origen a tratar
		for ndest in 1..maxColum loop
              -- ndest indica el número de tramos dest a tener el cuenta
  
			-- OPCION 1:
			min:=C(orig,0)+costeAcum(orig-1,ndest); -- Caso en q tramo orig i no se empareje con ninguno


			for p in 1..ndest loop
				suma:= 0.0;
				for resto in p+1..ndest loop --tramos de dest q se quedan sin emparejar
					suma:= suma + C(0,resto);
				end loop;

				-- OPCION 2 ( P = 1..ndest, q=r=0):
				opcion:= C(orig,p) + costeAcum(orig-1,p-1) + suma;
				if opcion < min then
					min:= opcion;
				end if;

				-- OPCION 3 (q = 1..p-1):
				for q in 1..p-1 loop
					opcion:= costeDivision(orig,q,p,fila) + costeAcum(orig-1,q-1) + suma;
					if opcion < min then
						min:= opcion;
					end if;
				end loop;

				-- OPCION 4 (r = 1..orig-1):
				for r in 1..orig-1 loop
					opcion:= costeMezcla(r,orig,p,fila) + costeAcum(r-1,p-1) + suma;
					if opcion < min then
						min:= opcion;
					end if;
				end loop;

			end loop;

		costeAcum(orig,ndest):= min;
		end loop;
	end loop;


end calculaMatrizCosteA;
-------------------------------------------------------------------



-------------------------------------------------------------------
procedure valoresaFiguras is --BORRAR ESTE PROCEDIMIENTO

aux: Tramo;



begin

--inicializar:
--solo una fila cada una.

	inicializaFigura( figOrig );
	aux.pos:=1;
	aux.ini:=1;
	aux.long:=2;
	aniadeFinal(figOrig(1), aux);

	aux.pos:=2;
	aux.ini:=5;
	aux.long:=2;
	aniadeFinal(figOrig(1), aux);

	aux.pos:=3;
	aux.ini:=8;
	aux.long:=1;
	aniadeFinal(figOrig(1), aux);


	aux.pos:=4;
	aux.ini:=10;
	aux.long:=2;
	aniadeFinal(figOrig(1), aux);

	aux.pos:=5;
	aux.ini:=14;
	aux.long:=5;
	aniadeFinal(figOrig(1), aux);




	inicializaFigura( figDest );
	aux.pos:=1;
	aux.ini:=0;
	aux.long:=4;
	aniadeFinal(figDest(1), aux);

	
	aux.pos:=2;
	aux.ini:=6;
	aux.long:=5;
	aniadeFinal(figDest(1), aux);


	aux.pos:=3;
	aux.ini:=15;
	aux.long:=2;
      aniadeFinal(figDest(1), aux);

	
	aux.pos:=4;
	aux.ini:=19;
	aux.long:=1;
      aniadeFinal(figDest(1), aux);

	
	aux.pos:=5;
	aux.ini:=22;
	aux.long:=2;
      aniadeFinal(figDest(1), aux);

	
end valoresaFiguras;
-------------------------------------------------------------------


-------------------------------------------------------------------
function parejaPartir(listaA, listaB: in out listaTramos; pos1, pos2: in integer) return listaParejas is

longTotal: integer;
longProp: array(1..MAX) of integer;
Prop: array (1.. MAX) of float;
suma: float;
tramoA, tramoB: Tramo;
iniAProp,finA,iniB, finB: integer;

parejaAux: Pareja;
lP : listaParejas;


begin

	tramoA:= dameActual(listaA);

	--Calcula la suma de las longitudes de los tramos
	longTotal := 0;
	tramoB:= inicializaActual(listaB, pos1);
	for i in pos1..pos2 loop
		longTotal := longTotal + tramoB.long;
		tramoB := siguienteActual(listaB); --aux.ptSig;
	end loop;
	--put("Longitud suma de los dos tramos: "); put(longTotal,0,3,0);new_line;

	--Calcular proporciones
	tramoB:= inicializaActual(listaB, pos1);
	for i in pos1 .. pos2 loop
		prop(i) := float(tramoB.long) / float(longTotal);
		--put("Proporcion tramo ");put(i,0);put(": "); put(prop(i),0,3,0);new_line;
		tramoB := siguienteActual(listaB);
	end loop;

	--Longitud total del tramo origen correspondiente al tramo i destino
	--aux := t1;
	for i in pos1 .. pos2 loop
		longProp(i) := integer(prop(i) * float(tramoA.long));
		--put("Longitud tramo ");put(i,0);put(": "); put(longProp(i),0,3,0);new_line;
		--aux := aux.ptSig;
	end loop;


	--Realizar las parejas:

	tramoB := inicializaActual( listaB, pos1);
	suma := 0.0;
	iniAProp := tramoA.ini;
 
    creaVacia(lP);

	for i in pos1 .. pos2 loop
	--Rellenar la lista por si es esta la opcion mejor:
		-- En este caso se toma como referencia el tramo que se debe dividir
            -- en partes.
		parejaAux.refIni:= iniProp;
			finA:= iniProp + longProp(i);
		parejaAux.refFin:= finA;
			--tramoD:= indexar(figDest(fila), p);
			iniB:= tramoB.ini;
			finB:= tramoB.ini + tramoB.long;
		parejaB.longIni:= (iniB - iniAProp) / numPasos;
		parejaB.longFin:= (finB - finA) / numPasos;
  
            aniadeFinal(lP, parejaB);

		tramoB := siguienteActual(listaB);
		iniAProp := iniAProp + longProp(i);


	end loop;


	return lP;


end parejaPartir;
-------------------------------------------------------------------

-------------------------------------------------------------------
function parejaDivision (orig, d1, d2: in integer; fila: in integer) return listaParejas is

listaOri, listaDest: listaTramos;

begin
	listaOri:= figOrig(fila);
	inicializarActual(listaOri, orig);
	listaDest:= figDest(fila);
	--inicializaActual(listaDest, d1);
	--numPos:= d2-d1+1;

	return costePartir(listaOri, listaDest, d1, d2);
 
end parejaDivision;
-------------------------------------------------------------------

-------------------------------------------------------------------
function parejaMezcla (o1, o2, dest: in integer; fila: in integer) return listaParejas is

listaOri, listaDest: listaTramos;

begin
      listaOri:= figOrig(fila);
	--inicializarActual(listaOri, orig);
	listaDest:= figDest(fila);
	inicializaActual(listaDest, dest);
	--numPos:= d2-d1+1;

	return parejaPartir(listaDest, listaOri, o1, o2);

end parejaMezcla;
-------------------------------------------------------------------

-------------------------------------------------------------------
function emparejar(orig, ndest: in integer; fila: in integer) return listaParejas is

par: Pareja;
tramoO, tramoD: Tramo;
min, suma, opcion: float;
oriFin, destIni, destFin, o ,d: integer;

lP: listaParejas;

begin

	tramoO:= observa( figOrig(fila), orig); -- tomaremos fig origen como referencia
	
	if (orig > 0) then
		
			-- OPCION 1:
			min:=C(orig,0)+costeAcum(orig-1,ndest); -- Caso en q tramo orig i no se empareje con ninguno
			   --Rellenar la estructura por si es esta la opcion mejor:
				-- No emparejar tramo orig con ninguno.
				par.refIni:= tramoO.ini;
					oriFin:= tramoO.ini + tramoO.long;
				par.refFin:= oriFin;
					destIni:= tramoO.ini+tramoO.long/2; --Empieza en pto medio
					destFin:= tramoO.ini+tramoO.long/2; --mismo pto, pues la long del tramo dest es 0.
				par.longIni:= (destIni - tramoO.ini) / numPasos;
				par.longFin:= (destFin - oriFin) / numPasos;
                        creaUnitaria(lP, par);


				-- Guardar valores para recursion:
				o:=orig-1; d:= ndest;


			for p in 1..ndest loop
				suma:= 0.0;
				for resto in p+1..ndest loop --tramos de dest q se quedan sin emparejar
					suma:= suma + C(0,resto);
				end loop;

				-- OPCION 2 ( P = 1..ndest, q=r=0):
				opcion:= C(orig,p) + costeAcum(orig-1,p-1) + suma;
				if opcion < min then
					min:= opcion;

					--Rellenar la estructura por si es esta la opcion mejor:
                              -- Opción anterior queda eliminada, pues ésta es menor.
					-- Empareja tamo orig con tramo p destino.
					par.refIni:= tramoO.ini;
						oriFin:= tramoO.ini + tramoO.long;
					par.refFin:= oriFin;
						tramoD:= indexar(figDest(fila), p);
						destIni:= tramoD.ini;
						destFin:= tramoD.ini + tramoD.long;
					par.longIni:= (destIni - tramoO.ini) / numPasos;
					par.longFin:= (destFin - oriFin) / numPasos;
                              libera(lP);
                              creaUnitaria(lP, par);

					-- Guardar valores para recursion:
					o:=orig-1; d:= p-1;

				end if;

				-- OPCION 3 (q = 1..p-1):
				for q in 1..p-1 loop
					opcion:= costeDivision(orig,q,p,fila) + costeAcum(orig-1,q-1) + suma;
					if opcion < min then
						min:= opcion;

						--Rellenar la estructura por si es esta la opcion mejor:
						-- Empareja tamo orig con tramos q..p destino.
                                    libera(lP);
						lP:=parejaDivision(orig,q,p,fila);

						-- Guardar valores para recursion:
						o:=orig-1; d:= q-1;
					end if;
				end loop;

				-- OPCION 4 (r = 1..orig-1):
				for r in 1..orig-1 loop
					opcion:= costeMezcla(r,orig,p,fila) + costeAcum(r-1,p-1) + suma;
					if opcion < min then
						min:= opcion;

						--Rellenar la estructura por si es esta la opcion mejor:
						-- Empareja tamos orig r..orig con tramo p destino.
						libera(lP);
                                    lP:=parejaMezcla(r,orig,p,fila);

						-- Guardar valores para recursion:
						o:=r-1; d:= p-1;
					end if;
				end loop;

			end loop;

			lpAux:= emparejar(o,d, fila);
                  contatenar ( lP, lpAux );


	else
                  creaVacia(lP);
      end if;
      
	return lP;

end emparejar;
-------------------------------------------------------------------


-------------------------------------------------------------------
procedure calculaEmparejamientos(fila: in integer) is

begin

	vecParejas(fila):= emparejar( long(figOrig(fila)), long (figDest(fila)), fila);
	-- como es recurrencia hacia atrás, el resultado final se
	-- obtiene empezando por los valores máximos.
      -- La longitud de las listas indican el número de elmentos que tienen, por tanto
      -- estamos llamando a emparejar con el tramo de mayor posición origen con 
      -- el número total de tramos del destino.
	
end calculaEmparejamientos;
-------------------------------------------------------------------
-- VECTOR DE PAREJAS, eN cada fila es una lista de parejas que 
-- supone los emparejamientos de dicha fila de figura origen y destino

-- para cada emparejamiento de cada fila se almacena la 
-- longIni y longFin necesarios para calcular posteriormente
-- la posIni y posFin y se almacenan en el campo longIni y longFin
-- de la estructura pareja hasta ahora sin usar.

-- del vector de parejas se pasa a una estructura figura, indicandole 
-- el numero de paso, que posteriormente se pasara a fichero.
-- Esto lo hara el procedimiento ImagenesIntermedias que utiliza 
-- el proc dibujaFigura.



procedure rellenarFiguraAux(paso: in integer) is

tramoAux: Tramo;
parejaAux: Pareja;
listaT: listaTramos;
listaP: listaParejas;
auxFin: integer;

begin

	for fila in 1..numFilas loop
            listaP:= vecParejas(fila);
	      creaVacia( figAux(fila) );
            parejaAux:= inicializaActual( listaP, 1);
		for pos in 1..long( listaP ) loop
			tramoAux.pos:= pos;
			tramoAux.ini:= parejaAux.refIni + (parejaAux.longIni * (paso-1));
                  AuxFin:= parejaAux.refFin + (parejaAux.longFin * (paso-1));
			tramoAux.long:= AuxFin - tramoAux.ini;

			anadir( figAux(fila), tramoAux);
                  parejaAux:= siguienteActual(listaP);
		end loop;
	end loop;

end rellenarFiguraAux;


procedure inicializaVectorSolucion is

begin

   --inicializar;
	for i in 1..numFilas loop
		creaVacia( vecParejas(i) );
	end loop;

end inicializaVectorSolucion;


procedure borraVectorSolucion is

begin

   --Borrar;
	for i in 1..numFilas loop
		libera( vecParejas(i) );
	end loop;

end borraVectorSolucion;



--procedure ImagenesIntermedias is  

--figAux: tpFigura;

--begin
		-- Crea imagenes intermedias:	
		-- De un vector de parejas y un numero de paso rellena una figura
		-- Dibuja la figura en un fichero.
--		-- Borra la figura.
--	for p in 1..numPasos-1 loop
--		figAux:= rellenarFigura(p);
--		--dibujarFigura(figAux);
--		borrarFigura(figAux, numFilas);
--	end loop;

--end ImagenesIntermedias;


end calculo;