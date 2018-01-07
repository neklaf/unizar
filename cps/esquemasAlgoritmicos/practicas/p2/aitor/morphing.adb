with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded, ada.integer_text_io, ada.float_text_io, calculo; 
use ada.text_io, Ada.Command_Line, Ada.Strings.Bounded,ada.integer_text_io, ada.float_text_io, calculo;

procedure morphing is





-------------------------------------------------------------------
-- PROGRAMA PRINCIPAL:
-------------------------------------------------------------------


begin




	
	inicializaFiguras;

	calculaMatrizCosteS(1); --num indica el numero de fila de figOrig y figDest a tratar
	
	calculaMatrizCosteA (5,5);
	
	visualizarMatriz(1);
	
	escribe;

	borrarFiguras(1);

--Utilizado para liberar la zona de memoria consumida por el puntero
--dispose(aux);

end morphing;
