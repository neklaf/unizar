--Informacion del libro: Programación en Ada J.P.G. Barnes
with Ada.Text_IO, Ada.Integer_Text_IO; 
use Ada.Text_IO, Ada.Integer_Text_IO; 

procedure ej4 is
--Definicion de una constante (Pagina 43)
	NUM: constant := 3;
--Declaracion de vectores multidimensionales de enteros
	mc: array (0 .. NUM, 0 .. NUM) of Integer := ((1,0,0,0),
							(1,1,1,1),
							(1,1,1,1),
							(1,1,1,1));
	fila: Integer := 0;
	i: Integer := 0;
	j: Integer := 1;
begin
	--put("El valor de la variable 'fila' es "); put(fila); put_line("");
	while ((i<NUM) and then (fila/=1)) loop
	--put_line("Hola!");
		while ((j<NUM) and then (mc(i,j)=0)) loop
			j := j+1;
		end loop;
		if ((j>=NUM) and (i/=NUM-1)) then
			fila := 1;
			j := i;
			i := 0;
		else
			i := j;
			j := j+1;
		end if;
		--put_line("El valor de 'fila' es "); put(fila);
	end loop;
	if fila=1 then
		while ((i<NUM) and then (mc(i,j)=1)) loop
			i := i+1;
		end loop;
		if i>=NUM then
			put("La celebridad es la persona ");put(j+1);
			put_line(" ");
		else
			put_line("No hay celebridad.");
		end if;
	else
		put_line("No hay celebridad!!");
	end if;
end ej4;
