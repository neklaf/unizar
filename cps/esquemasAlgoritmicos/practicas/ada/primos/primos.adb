with Ada.Text_IO; use Ada.Text_IO;
package body Primos is
	function esprimo (n: Float) return Boolean is
		i: Float;
	begin
		--Put_Line("Hola mundo!!");
		--He cambiado los long en C por floats
		while i*i <= n loop
			if n mod i = 0 then
				return TRUE;
			end if;
			i := i + 1;
		end loop;
		return FALSE;
	end esprimo;
end Primos;
