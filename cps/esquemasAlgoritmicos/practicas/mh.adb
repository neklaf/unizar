
with Ada.text_io,Ada.integer_text_io,Ada.Command_Line, declaraciones; 
use Ada.text_io,Ada.integer_text_io,Ada.Command_Line, declaraciones; 

procedure mh is 

--ESTE ES EL BUENO!


-- PROCEDIMIENTOS: 

procedure toto (u,v : in Integer; d,a,b: out Integer) is 
w, aa, bb: Integer; 
begin 
        if u = v then 
                Put("u=v");new_line; 
                d := v; a := 0; b := 1; 
        elsif u < v then 
                Put("u menor que v ANTES");new_line; 
                toto(v,u,d,b,a); 
                Put("u menor que v despues TOTO");new_line; 
        else 
                Put("u mayor que v");new_line; 
                w := u mod v; --w<v    d=mcd(v,w) 
                Put("w = "); Put(w,0);new_line; 
                if w = 0 then 
                        d := v; a := 0; b := 1;         
                        Put("d = "); Put(d,0); new_line; 

                else 
                        toto(v,w,d,aa,bb); 
                        Put("toto(v,w,d,aa,bb);");new_line; 
                        a := bb; b := aa-((u / v)*bb); 
                        Put("a = "); Put(a,0);new_line; 
                        Put("b = "); Put(b,0);new_line; 
                end if; 
        end if; 
	--Esto es un pequeqo retoque antes de sacar el inverso necesario para el
	--algoritmo
	if a < 0 then a := a + N;
	end if;
end toto; 

function toto2 (M: Positive; N: Positive) return Positive is 
        R: Natural; 
        TEMPM: Positive; 
        TEMPN: Positive; 
begin 
        TEMPM := M; 
        TEMPN := N; 
        R := TEMPM rem TEMPN; 

        while R /= 0 loop 
                TEMPM := TEMPN; 
                TEMPN := R; 
                R := TEMPM rem TEMPN; 
        end loop; 
        return TEMPN; 
end toto2; 

function inversa (w,N: Integer) return Integer is 
d,s,t: Integer; 
begin 
        toto(w,N,d,s,t); 
        put("d = "); Put(d,0);new_line; 
        put("s = "); Put(s,0);new_line; 
        put("t = "); Put(t,0);new_line; 
        return s; 
end inversa; 

procedure lee_claves is 
inv: Integer; 
begin 

        put("Dame mochila:");new_line; 
        for i in 1..5 loop 
                put("Elemento ");put(i,0);put(" : "); 
                get(mochila(i)); 
        end loop; 
        new_line(2); 
         
        put("Dame N:"); 
        get(N); 
        new_line(2); 
         
        put("Dame w:"); 
        get(w); 
        new_line(2); 
        inv := inversa(w,N); 
        put("Inversa de w: ");         
        put(inv,0); 
        new_line(2); 
         
end lee_claves; 



begin 

        new_line(3); 
        put(" *************************");new_line; 
        put(" ** PROGRAMA DE CIFRADO **");new_line; 
        put(" *************************"); 
        new_line(3); 
         
        if (Argument_Count < 1)or(Argument_Count > 2) then 
                put_line("Uso: merkleHelman -c/-d/-g (nombre_fichero)"); 
                new_line; 
                return; 
        end if; 
         
        case Argument(1)(2) is 
                when 'c'=> 
                        put_line("bien c"); 
                        if (Argument_Count /= 2) then 
                                put_line("Uso: merkleHelman -c nombre_fichero"); 
                                new_line; 
                                return; 
                        end if; 
                when 'd'=> 
                        put_line("bien d"); 
                        if (Argument_Count /= 2) then 
                                put_line("Uso: merkleHelman -d nombre_fichero"); 
                                new_line; 
                                return; 
                        end if; 
                when 'g'=> 
                        if (Argument_Count /= 1) then 
                                put_line("Uso: merkleHelman -g"); 
                                new_line; 
                                return; 
                        end if; 
                        lee_claves; 
                when others=> 
                        put_line("Uso: merkleHelman -c/-d/-g (nombre_fichero)"); 
                        put(Argument(1)(2)); 
                        new_line; 
                        return; 
        end case; 
         


end mh; 
