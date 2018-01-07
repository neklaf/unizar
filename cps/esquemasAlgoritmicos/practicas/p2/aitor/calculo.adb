with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded, ada.integer_text_io, ada.float_text_io; 
use ada.text_io, Ada.Command_Line, Ada.Strings.Bounded,ada.integer_text_io, ada.float_text_io;
with ada.unchecked_deallocation;


package body calculo is

procedure dispose is new ada.unchecked_deallocation(Tramo,ptTramo);                                                 


-------------------------------------------------------------------
-- Declaracion de variables
-------------------------------------------------------------------

--fichText: Ada.text_IO.File_type;



--Matriz de costes simples
C, costeAcum:array (0..MAX,0..MAX) of float;
figOrig, figDest: tpFigura;





-------------------------------------------------------------------
--Definicion de funciones
-------------------------------------------------------------------

-------------------------------------------------------------------
function calculaCosteSimple (ini1,ini2,long1,long2 : float) return float is

begin
	return (POND * abs(ini1 - ini2) + (1.0 - POND) * abs(long1 - long2));
end calculaCosteSimple;
-------------------------------------------------------------------


-------------------------------------------------------------------
procedure calculaMatrizCosteS( num: in integer) is

auxOrig, auxDest: ptTramo;
--n: float;

begin
        auxOrig:= figOrig(num).primElem;

	for i in 1 .. figOrig(num).numElem loop
	
		auxDest:= figDest(num).primElem;
	
				--new_line(3);
				--n:= auxOrig.ini; put(n,0,1,0); put("   ");
				--n:= auxOrig.long; put(n,0,1,0); new_line;
	
        C(i,0):= calculaCosteSimple(0.0,0.0,auxOrig.long,0.0);
               -- Coste sin contar posiciC3n y sC3lo tiene longitud el tramo origen
               -- pues es coste de dejar tramo origen i sin emparejar.
		for j in 1 .. figDest(num).numElem loop
			--n:= auxDest.ini; put(n,0,1,0); put(" ");
			--n:= auxDest.long; put(n,0,1,0); put("    ");
		
			C(i,j) := calculaCosteSimple(auxOrig.ini,auxDest.ini,auxOrig.long,auxDest.long);
            auxDest:= auxDest.ptSig;
		end loop;
        auxOrig:= auxOrig.ptSig;
	end loop;

       auxDest:= figDest(num).primElem;
       for j in 1 .. figDest(num).numElem loop
         	C(0,j) := calculaCosteSimple(0.0,0.0,0.0,auxDest.long);
            auxDest:= auxDest.ptSig;
	end loop;

       C(0,0):= 0.0;



end calculaMatrizCosteS;
-------------------------------------------------------------------


-------------------------------------------------------------------
function costePartir(t,t1,t2: ptTramo) return float is
aux: ptTramo;
longTotal: float;
longProp, Prop: array(1..MAX) of float;
suma, origProp: float;
begin
	--Calcula la suma de las longitudes de los tramos
	longTotal := 0.0;
	aux := t1;
	for i in t1.pos .. t2.pos loop
		longTotal := longTotal + aux.long;
		aux := aux.ptSig;
	end loop;
	--put("Longitud suma de los dos tramos: "); put(longTotal,0,3,0);new_line;

	--Calcular proporciones
	aux := t1;
	for i in t1.pos .. t2.pos loop
		prop(i) := aux.long / longTotal;
		--put("Proporcion tramo ");put(i,0);put(": "); put(prop(i),0,3,0);new_line;
		aux := aux.ptSig;
	end loop;

	--Longitud total del tramo origen correspondiente al tramo i destino
	--aux := t1;
	for i in t1.pos .. t2.pos loop
		longProp(i) := prop(i) * t.long;
		--put("Longitud tramo ");put(i,0);put(": "); put(longProp(i),0,3,0);new_line;
		--aux := aux.ptSig;
	end loop;

	--Calcular los costes de emparejamiento

	aux := t1;
	suma := 0.0;
	origProp := t.ini;
	for i in t1.pos .. t2.pos loop
		suma := suma + calculaCosteSimple(origProp, aux.ini, longProp(i), aux.long);	
		aux := aux.ptSig;
		origProp := origProp + longProp(i);
	end loop;


	return suma;
end costePartir;
-------------------------------------------------------------------


-------------------------------------------------------------------
function indexar(lista: in tpLista; num: in integer) return ptTramo is

aux: ptTramo;

begin

	aux:= null;
	aux:= new Tramo;

	aux:= lista.primElem;
	for i in 2..num loop
		aux:= aux.ptSig;
	end loop;
	
	return aux;

end indexar;
-------------------------------------------------------------------

-------------------------------------------------------------------
function costeDivision (orig, d1, d2: in integer; fila: in integer) return float is

begin
	return costePartir(indexar(figOrig(fila),orig), indexar(figDest(fila),d1), indexar(figDest(fila),d2));
end costeDivision;
-------------------------------------------------------------------

-------------------------------------------------------------------
function costeMezcla (o1, o2, dest: in integer; fila: in integer) return float is

begin
	return costePartir(indexar(figDest(fila),dest), indexar(figOrig(fila),o1), indexar(figOrig(fila),o2));
end costeMezcla;
-------------------------------------------------------------------

procedure escribe is -- ESTE PROC ES DE PRUEBA, HAY QUE BORRARLO

n: float;

begin

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
	for i in 0 .. figOrig(numFila).numElem loop
		for j in 0.. figDest(numFila).numElem loop
			put( C(i,j),0,1,0); put("   ");
		end loop;
		new_line;
	end loop;
	new_line(2);
	
	new_line(2);
	put_line(" MATRIZ DE COSTES ACUMULADOS: ");
	for i in 0 .. figOrig(numFila).numElem loop
		for j in 0.. figDest(numFila).numElem loop
			put( costeAcum(i,j),0,1,0); put("   ");
		end loop;
		new_line;
	end loop;
	new_line(2);

end visualizarMatriz;
-------------------------------------------------------------------


-------------------------------------------------------------------
procedure borrarFigura (fig: in out tpFigura; numFilas: in integer) is

auxFig, auxSig: ptTramo;

begin

	for i in 1 .. numFilas loop
		auxSig:= fig(i).primElem.ptSig;
		auxFig:= fig(i).primElem;
		for j in 1.. fig(i).numElem loop
			-- borramos todos los tramos de una fila
			dispose(auxFig);
			auxFig:= auxSig;
			if (j /= fig(i).numElem) then
				auxSig:= auxFig.ptSig;
			end if;
		end loop;
		fig(i).numElem:=0;
		fig(i).primElem:= null;
		fig(i).ultElem:= null;
	end loop;
	
	

end borrarFigura;
-------------------------------------------------------------------

-------------------------------------------------------------------
procedure borrarFiguras (numFilas: in integer) is

begin

	borrarFigura(figOrig, numFilas);
	borrarFigura(figDest, numFilas);
	-- Ahora tb borraria las intermedias.

end borrarFiguras;
-------------------------------------------------------------------

-------------------------------------------------------------------
procedure calculaMatrizCosteA (maxFila, maxColum: in integer) is

min, suma, opcion: float;

begin
	-- Valores iniciales:

	costeAcum(0,0):= 0.0; --coste de no emparejar nada ni dejar libre nada.
	
	for i in 1..maxFila loop
		costeAcum(i,0):= C(i,0);
	end loop;
	
	for j in 1..maxColum loop
		costeAcum(0,j):= costeAcum(0,j-1) + C(0,j);
	end loop;
		
	-- Valores obtenidos con la funcion de prog dinamica con recursion hacia atras:
	min:=0.0;
	for orig in 1..maxFila loop
		for ndest in 1..maxColum loop
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
					opcion:= costeDivision(orig,q,p,1) + costeAcum(orig-1,q-1) + suma;
					if opcion < min then
						min:= opcion;
					end if;
				end loop;
				
				-- OPCION 4 (r = 1..orig-1):
				for r in 1..orig-1 loop
					opcion:= costeMezcla(r,orig,p,1) + costeAcum(r-1,p-1) + suma;
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
procedure inicializaFiguras is

aux, auxP, auxU: ptTramo;



begin

--inicializar:
--solo una fila cada una.
	auxP:=null;
	auxU:=null;
	
	aux:=null;
	aux:= new Tramo;
	aux.pos:=1;
	aux.ini:=1.0;
	aux.long:=2.0;
	aux.ptSig:=null;
	auxP:=aux;
	auxU:=aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=2;
	aux.ini:=5.0;
	aux.long:=2.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=3;
	aux.ini:=8.0;
	aux.long:=1.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=4;
	aux.ini:=10.0;
	aux.long:=2.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=5;
	aux.ini:=14.0;
	aux.long:=5.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;	

	figOrig(1).numElem:=5;
	figOrig(1).primElem:=auxP;
	figOrig(1).ultElem:=auxU;
	
	
	auxP:=null;
	auxU:=null;
	
	aux:=null;
	aux:= new Tramo;
	aux.pos:=1;
	aux.ini:=0.0;
	aux.long:=4.0;
	aux.ptSig:=null;
	auxP:=aux;
	auxU:=aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=2;
	aux.ini:=6.0;
	aux.long:=5.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=3;
	aux.ini:=15.0;
	aux.long:=2.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=4;
	aux.ini:=19.0;
	aux.long:=1.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;
	
	aux:= null;
	aux:= new Tramo;
	aux.pos:=5;
	aux.ini:=22.0;
	aux.long:=2.0;
	aux.ptSig:=null;
	auxU.ptSig:= aux;
	auxU:= aux;
	
	
	figDest(1).numElem:=5;
	figDest(1).primElem:=auxP;
	figDest(1).ultElem:=auxU;
	
	aux:= null;
	auxP:= null;
	auxU:= null;
	
end inicializaFiguras;
-------------------------------------------------------------------


end calculo;
