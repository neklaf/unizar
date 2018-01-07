with Ada.text_io, calculo;
use Ada.text_io, calculo;

-- Modulo para probar el interface del paquete calculo
procedure prueba is

-- Probar guardaValoresDato (YA)
-- Probar cargaValoresDato (YA)
-- Probar introduce (YA)
-- Probar verDato, limpiaDato (YA)
-- No probar generaAleatorioDato
-- No probar crea_tupla, ni codifica, ni descifrar

begin

-- Prueba para el introduce, despues tenemos que ver que todos los valores del dato se pasan al fichero
for i in 1 .. MAX loop
	introduce(mochila, i, i);
end loop;

 introduce(datoN, 73,0);
 introduce(datoW, 13,0);
 introduce(datoWinv, 2,0);

--limpiaDato;
verDato;
guardaValoresDato;
limpiaDato;
verDato;
put_line("************************");
if (cargaValoresDato) then
	verDato;
end if;

end prueba;
