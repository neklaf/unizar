with ada.text_io, ada.integer_text_io;
use ada.text_io, ada.integer_text_io;



procedure tipos is

	--Declaracion de tipos en Ada
	subtype miEntero is integer range 0 .. 2;
	subtype miEntero2 is integer range 2 .. 4;
	type miOtroEntero is new integer range 0 .. 3;

	m: miEntero;
	m2: miEntero2;
	i: integer;
	o: miOtroEntero;

begin

	ada.text_io.put_line("El programa rula!!");
	i := 2;
	m := i;
	--put("El valor de m es: "); put(m,0); new_line;
	o := miOtroEntero(i); -- operacion permitida
	--put("El valor de o es: "); put(integer(o),0); new_line;
	m2 := m;
	put("El valor de m2  es: "); put(m2,0); new_line;

end tipos;
