with Ada.text_io,Ada.integer_text_io,Ada.Command_Line, calculo, Ada.sequential_IO,Ada.Strings.Bounded;
use Ada.text_io,Ada.integer_text_io,Ada.Command_Line, calculo,Ada.Strings.Bounded;
 
procedure merklehelman is

--Implementacion de sequential_IO
--package F_Princ is new Ada.sequential_IO(integer);
--use F_Princ;
--package String15 is new Ada.Strings.Bounded.Generic_bounded_length(15);
    use String15;

cad: String15.Bounded_string;
car: character;
vecTupla: tpTupla;
--inv, valW, valN: integer;
--error: boolean;

-- PROCEDIMIENTOS: 

-------------------------------------------------------------

procedure toto (u,v : in Integer; d,a,b: out Integer) is
w, aa, bb: Integer;
begin
	if u = v then
		d := v; a := 0; b := 1;
	elsif u < v then
		toto(v,u,d,b,a);
	else
		w := u mod v; --w<v    d=mcd(v,w)
		if w = 0 then
			d := v; a := 0; b := 1;

		else
			toto(v,w,d,aa,bb);
			a := bb; b := aa-((u / v)*bb);
		end if;
	end if;
      -- if (a < 0) then
      --   a := a + v;
      -- end if;
      -- si inversa resulta nzmero negativo, entonces 
      -- sumarle el msdulo.
 
end toto;

-------------------------------------------------------------

function inversa(a, b: in integer) return integer is
d,s,t: Integer;
begin
	toto(a,b,d,s,t);

      if (s < 0) then
         s := s + b;
      end if;
      -- si inversa resulta numero negativo, entonces 
      -- sumarle el msdulo.
      
      return s; 

end inversa;

-------------------------------------------------------------

procedure prueba (num: in character) is
auxTup: tpTupla;

begin
--el primer indice en la palabra Argument indica el numero de argumento y el segundo el indice de la letra
--de la cadena que corresponda al argumento indicado con el primer numero
case num is
	when '1'=>
		put_line("EJECUCION DEL EJEMPLO NUMERO 1:");
		new_line;
		introduce(mochila,1,1);
		introduce(mochila,2,2);
		introduce(mochila,4,3);
		introduce(mochila,8,4);
		introduce(mochila,16,5);
		introduce(mochila,32,6);
		introduce(mochila,64,7);
		introduce(mochila,128,8);
		introduce(datoN,257,0);
		introduce(datoW,3,0);
		--TENGO QUE INVENTARME UNA VARIABLE PARA QUE NO SE QUEJE EL COMPILADOR
		auxTup:=crea_tupla;
		introduce(datoWinv,inversa(3,257),0);
		verDato;
		new_line;

		codifica(to_bounded_string(file2));
		put("Mensaje codificado con exito"); new_line(2);

		descifrar(to_bounded_string(file2));
		new_line;
	when '2'=>
		put_line("EJECUCION DEL EJEMPLO NUMERO 2:");
		new_line;
		introduce(mochila,2,1);
		introduce(mochila,3,2);
		introduce(mochila,7,3);
		introduce(mochila,15,4);
		introduce(mochila,31,5);
		introduce(mochila,59,6);
		introduce(mochila,118,7);
		introduce(mochila,236,8);
		introduce(datoN,472,0);
		introduce(datoW,17,0);
		auxTup:=crea_tupla;
		introduce(datoWinv,inversa(17,472),0);
		verDato;
		new_line;

		codifica(to_bounded_string(file2));
		put("Mensaje codificado con exito"); new_line(2);

		descifrar(to_bounded_string(file2));
		new_line;
		--Es importante NO olvidarse el when others=>
	when others=>
		put_line("No hay ejemplo con ese identificador");
		return;
end case;

end prueba;

-------------------------------------------------------------

function mcd (a,b:integer) return integer is
-- Calcula el mcd de a y b. El resultado es d.

d,s,t: integer;

begin
	toto(a,b,d,s,t);
	return d;

end mcd;

-------------------------------------------------------------

procedure lee_claves is
--inv: Integer;
suma: integer:=0;
elem, elemW, elemN:integer;
begin

        put("Dame mochila:");new_line;
        for i in 1..MAX loop
                put("Elemento ");put(i,0);put(" : ");
                get(elem);
                while elem<= suma loop
                        put("Repita elemento ");put(i,0);put(" : ");
                        get(elem);
                end loop;
                introduce(mochila,elem,i);
                suma:= suma + elem;
        end loop;
        new_line(2);

        put("Dame N:");
        get(elemN);
        while (elemN <= suma) or (elemN<=0) loop
                put("Repita N: ");
                get(elemN);
        end loop;
        introduce(datoN,elemN,0);
        new_line(2);

        put("Dame w:");
        get(elemW);
        while (elemW<=0) or (mcd(elemW,elemN)/=1) or (elemW>=elemN) loop
                put("Repita w: ");
                get(elemW);
        end loop;
        introduce(datoW,elemW,0);
        new_line(2);
        
        elem:= inversa(elemW, elemN);
        introduce(datoWinv, elem, 0);
        --put("Inversa de w: ");
        --put(inv,0);
        --new_line(2);

end lee_claves;

-------------------------------------------------------------



begin

        new_line(3);
        put(" *************************");new_line;
        put(" ** PROGRAMA DE CIFRADO **");new_line;
        put(" *************************");
        new_line(3);

        if (Argument_Count < 1)or(Argument_Count > 2) then
                put_line("Uso: merklehelman -c/-d/-g (nombre_fichero)");
                new_line;
                return;
        end if;

        case Argument(1)(2) is
                when 'c'=>
                        
                        if (Argument_Count /= 2) then
                                put_line("Uso: merklehelman -c nombre_fichero");
                                new_line;
                                return;
                        end if;
                        
                        --error:= cargaValoresDato;
                        if not cargaValoresDato then --no puede cargar valores pq no hay dato
                                             --en fichero o no existe fichero.
                           --generaAleatorioDato(valW, valN);

			   --ESTA LINEA NO ESTABAN COMENTADAS!!
                           --inv:=inversa(valW, valN);
                           --introduce(datoWinv, inv, 0);
			   put_line("Fichero NO existe");
                        end if;

                        cad := to_bounded_string(Argument(2));
                        codifica(cad);
			put("Mensaje codificado con exito"); new_line(2);
                        
                when 'd'=>
                        if (Argument_Count /= 2) then
                                put_line("Uso: merklehelman  -d nombre_fichero");
                                new_line;
                                return;
                        end if;
                        
                        --error:= cargaValoresDato;
                        if not cargaValoresDato then --no puede cargar valores pq no hay dato
                                             --en fichero o no existe fichero.
                           --generaAleatorioDato(valW, valN);

			   --ESTAS LINEAS NO ESTABAN COMENTADAS!!
                           --inv:=inversa(valW, valN);
                           --introduce(datoWinv, inv, 0);

			   put_line("Fichero NO existe");

			   --MENSAJE PARA DECIRLE QUE CODIFIQUE
                        end if;

                        descifrar(to_bounded_string(Argument(2)));
			new_line;
                        
                when 'g'=>
                        if (Argument_Count /= 1) then
                                put_line("Uso: merklehelman -g");
                                new_line;
                                return;
                        end if;
                        
			-- Tenemos que probar si sale el simbolo contrario a ?
                        put_line(" ?Como desea generar los datos?");
                        put_line(" Opciones:");
                        put_line("          1- Manualmente");
                        put_line("          2- Aleatoriamente");
                        put("RESPUESTA (1-2): ");
                        get(car);
                        while (car/='1') and (car/='2') loop
                           new_line;
                           put_line("ERROR: !Debe elegir la opcion entre 1 y 2!");
                           put("RESPUESTA (1-2): ");
                           get(car);
                        end loop;
                        new_line;
                        if (car = '1') then
                           lee_claves; --lee y hace inversa.
                        else
                           --generaAleatorioDato(valW, valN);

			   --ESTAS LINEAS NO ESTABAN COMENTADAS!!
                           --inv:=inversa(valW, valN);
                           --introduce(datoWinv, inv, 0);
			   put_line("Hemos iintroducido la opcion de generar datos aleatoriamente");
                        end if;
                           
                        --inversa; --calcula el inverso de w y lo almacena.
			      vecTupla:=crea_tupla;
                        put("CLAVE PUBLICA:"); new_line;
	                  put("{");
                        for i in 1..MAX loop
		               put(vecTupla(i),0);put(", ");
                        end loop;
	                  put("}");new_line(2);
                   
                        guardaValoresDato;
			
                when 'p'=>
                        if (Argument_Count /= 2) then
                                put_line("Uso: merklehelman -p numero_ejemplo");
                                new_line;
                                return;
                        end if;
			--Recordar que los parametros pasados son caracteres!!
			if (Argument(2)(1) /= '2')and(Argument(2)(1) /= '1') then
				put_line("El numero del ejemplo tiene que ser 1 o 2"); 
			else
			     prueba(Argument(2)(1));
			end if;

                when others=>
                        put_line("Uso: merklehelman -c/-d/-g (nombre_fichero) o -p numero_ejemplo");
                        put(Argument(1)(2));
                        new_line;
                        return;
        end case;



end merklehelman;
