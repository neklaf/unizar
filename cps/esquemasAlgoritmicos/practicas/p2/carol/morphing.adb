with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded, ada.integer_text_io, ada.float_text_io, calculo;
use ada.text_io, Ada.Command_Line, Ada.Strings.Bounded,ada.integer_text_io, ada.float_text_io, calculo;

procedure morphing is





-------------------------------------------------------------------
-- PROGRAMA PRINCIPAL:
-------------------------------------------------------------------


begin




	inicializaFigurasOD; --( figOrig y figDest );
 
      -- Pasa las figuras de los ficheros a las estructuras:
	numFilasO := PasaFicheroFigura (fo);
	numFilasD := PasaFicheroFigura (fd);
      -- No es necesario que tengan el mismo número de filas,
      -- las figuras intermedias tendrán el máximo número de fila.
 
      if (numFilasO > numFilasD) then
         introduceDatos( numFilasO, numPasos);
      else
         introduceDatos( numFilasD, numPasos);
      end if;
 
 
      inicializaVectorSolucion;
 
       -- Calcula todos los emparejamientos usando la programación dinámica:
	for fila in 1..numFilas loop
             -- En cada iteración se emparejan los tramos de una fila.
		calculaMatrizCosteS(fila);
		calculaMatrizCosteA (fila);
		calculaEmparejamientos(fila);
	end loop;

 
            -- Crea imagenes intermedias:	
		-- De un vector de parejas y un numero de paso rellena una figura
		-- Dibuja la figura en un fichero.
		-- Borra la figura.
	for p in 1..numPasos-1 loop
		rellenarFiguraAux(p);
	      PasaFiguraAuxFichero(fichp&p, p); -- fichp es el nombre que tendrán fich intermedios concatenados con un num p
	      borrarFiguraAux;
      end loop;

 
      borrarFigurasOD;
      -- borra figOrig y figDest;
      borraVectorSolucion;

--------------------------------------------------------------------------------------------
--      calculaMatrizCosteS(1); --num indica el numero de fila de figOrig y figDest a tratar

----	calculaMatrizCosteA (5,5);

--	visualizarMatriz(1);

--	escribe;

--	borrarFiguras(1);


end morphing;