--
-- MODULO DE IMPLEMENTACION DEL TAD "CONJUNTO DE NUMEROS NATURALES DISTINTOS"
-- ALMACENADO EN UN ARBOL AVL
--
-- Version: 1.0
-- Fecha: Julio 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: Implementacion de un conjunto de numeros naturales con la operacion de
-- distancia minima.
--
-- Nota: La implementacion de los procedimientos relacionados con los arboles AVL ha sido
-- sacado de la implementación de Javier Campos Laclaustra.
--

with unchecked_deallocation;
with text_io; use text_io;
with Ada.Integer_Text_IO; use Ada.Integer_Text_IO;


package body conj_dm is

	procedure disponer is new unchecked_deallocation(nodo,avl);
	--Implementación de un procedimiento para poder liberar la memoria asignada a un nodo del arbol AVL

	procedure vaciar (c: out conj) is
	-- Devuelve el conjunto vacio
	begin
		c.raiz := null; 
		c.h.num := 0; 
		for i in 1 .. MAX_NUM loop
			c.h.v(i).d := 0;
			c.h.v(i).e := null;
		end loop;
		put_line("Arbol AVL vaciado.");
	end vaciar;


	procedure crear (c: out conj) is
	--Inicializa los valores del conjunto de numeros naturales
	begin
		c.raiz := null; --Inicializamos la raiz del arbol
		c.h.num := 0; --Inicializamos el numero de elementos del monticulo
		c.h.v(1).d := 0; --Si el conjunto esta vacio o tiene un elemento la distancia minima sera 0
		put_line("Conjunto creado.");
	end crear;


		procedure aniadir (h: in out min_heap; e: in integer; n: in out avl) is
			i: indice;
			flotar: boolean;
			aux: nodo_heap;
			desbord: exception;

		begin
			if h.num = MAX_NUM then
				put_line("DESBORDAMIENTO DEL MONTICULO!!");
				raise desbord;
			else

				h.num := h.num + 1; --Actualizamos el contador de elementos del monticulo
				h.v(h.num).d := e; --Insertamos el nuevo elemento en la ultima posicion
				h.v(h.num).e := n; --Asignamos el puntero al nodo del arbol
				h.v(h.num).e.i_dist := h.num;

				i := h.num;
				if i > 1 then --Hay mas de un elemento en el monticulo
					flotar := h.v(i).d < h.v(i / 2).d;
				else --i=1
					flotar := false;
				end if;

				while (i > 1) and then flotar loop
					--Implementacion del swap o intercambio entre dos elementos del monticulo
					aux.d := h.v(i).d;
					aux.e := h.v(i).e;

					h.v(i).d := h.v(i / 2).d;
					h.v(i).e := h.v(i / 2).e;
	
					h.v(i / 2).d := aux.d;
					h.v(i / 2).e := aux.e;

					--Actualizamos los nodos del arbol
					h.v(i / 2).e.i_dist := i / 2;
					h.v(i).e.i_dist := i;
					
					i := i / 2;
					if i > 1 then
						flotar := h.v(i).d < h.v(i / 2).d;
					else --i=1
						flotar := false;
					end if;
				end loop;
			end if;
		end aniadir;


		procedure elimina_item (h: in out min_heap; it: in indice) is
			i, j: indice; 
			aux: nodo_heap;
			vacio: exception;

		begin
			if h.num = 0 or it > h.num then
				put("No se puede borrar el item: "); put(it, 0); put("."); new_line;
				put("El numero de elementos del monticulo es: "); put(h.num, 0); new_line;
				raise vacio;
			else    
				h.v(it).d := h.v(h.num).d; --Asignamos el ultimo elemento a la posicion borrada 
				h.v(it).e := h.v(h.num).e; --Asignamos el puntero al nodo del arbol 
				h.v(it).e.i_dist := it; --Actualizamos el puntero a la distancia en el nodo del arbol

				h.num := h.num - 1;
				i := it; -- i es la posicion del que antes era el ultimo elemento
				
				--Si el elemento que hemos intercambiado es menor que su padre tenemos que flotar el elemento
				--Por supuesto tenemos que comprobar si el padre existe antes de nada
				if i/2 > 1 and then h.v(i/2).d > h.v(i).d then
					--Flotamos el elemento
					while i/2 > 1 and then h.v(i/2).d > h.v(i).d loop
						--Modificacion del swap debido al tipo nodo_heap
						aux.d := h.v(i).d; 
						aux.e := h.v(i).e; 

						h.v(i).d := h.v(i/2).d;
						h.v(i).e := h.v(i/2).e;

						h.v(i/2).d := aux.d; 
						h.v(i/2).e := aux.e; 

						--Actualizamos los nodos del arbol
						h.v(i / 2).e.i_dist := i / 2;
						h.v(i).e.i_dist := i;

						i := i/2; 
					end loop;
				else
					--Mientras i sea menor o igual que el ultimo padre del monticulo
					while i <= (h.num / 2) loop 
						--bajamos el anterior el anterior ultimo del arbol
						if (h.v(2 * i).d < h.v(2 * i + 1).d) or (2 * i = h.num) then
							j := 2 * i;
						else
							j := 2 * i + 1;
						end if;
						--tenemos apuntado por j al menor de los hijos de i
						if h.v(i).d > h.v(j).d then
							--intercambiamos el anterior ultimo elemento y su hijo
							aux.d := h.v(i).d; 
							aux.e := h.v(i).e; 

							h.v(i).d := h.v(j).d; 
							h.v(i).e := h.v(j).e; 

							h.v(j).d := aux.d; 
							h.v(j).e := aux.e; 

							--Actualizamos los nodos del arbol
							h.v(j).e.i_dist := j;
							h.v(i).e.i_dist := i;
							
							i := j;
						else 
							--i := c.num; --Para terminar el bucle;
							exit;
						end if;
					end loop;
				end if;
				if h.num = 0 then h.v(1).d := 0; end if;
			end if; 
		end elimina_item;



	procedure insertar (c: in out conj; num: in natural) is
	-- Inserta un numero natural en el conjunto; si ya estaba, lo deja igual
		rebalancear: boolean;
		insertado: boolean := false;

		procedure insert (a: in out avl; num: in natural; rebalancear: in out boolean; 
						insertado: in out boolean) is

			  procedure rotacion_izq(a:in out avl) is
			  --véase la documentación facilitada en clase
				aux:avl;
			  begin
				aux:=a.izq;
				a.izq:=aux.der;
				a.balance:=balanceado;
				aux.der:=a;
				a:=aux;
				a.balance:=balanceado;
			  end rotacion_izq;

			  procedure rotacion_dch(a:in out avl) is
			  --véase la documentación facilitada en clase
				aux:avl;
			  begin
				aux:=a.der;
				a.der:=aux.izq;
				a.balance:=balanceado;
				aux.izq:=a;
				a:=aux;
				a.balance:=balanceado;
			  end rotacion_dch;
			
			  procedure rotacion_izq_dch(a:in out avl) is
			  --véase la documentación facilitada en clase
				aux_1,aux_2:avl;
			  begin
				aux_1:=a.izq;
				aux_2:=a.izq.der;
				aux_1.der:=aux_2.izq;
				aux_2.izq:=aux_1;
				a.izq:=aux_2;
				if aux_2.balance=pesado_izq then
				  aux_1.balance:=balanceado;
				  a.balance:=pesado_der;
				elsif aux_2.balance=balanceado then
						  aux_1.balance:=balanceado;
					  a.balance:=balanceado;
				else
				  aux_1.balance:=pesado_izq;
				  a.balance:=balanceado;
				end if;
				a.izq:=aux_2.der;
				aux_2.der:=a;
				aux_2.balance:=balanceado;
				a:=aux_2;
			  end rotacion_izq_dch;

			  procedure rotacion_dch_izq(a:in out avl) is
			  --véase la documentación facilitada en clase
				aux_1,aux_2:avl;
			  begin
				aux_1:=a.der;
				aux_2:=a.der.izq;
				aux_1.izq:=aux_2.der;
				aux_2.der:=aux_1;
				a.der:=aux_2;
				if aux_2.balance=pesado_der then
				  aux_1.balance:=balanceado;
				  a.balance:=pesado_izq;
				elsif aux_2.balance=balanceado then
				  aux_1.balance:=balanceado;
				  a.balance:=balanceado;
				else
				  aux_1.balance:=pesado_der;
				  a.balance:=balanceado;
				end if;
				a.der:=aux_2.izq;
				aux_2.izq:=a;
				aux_2.balance:=balanceado;
				a:=aux_2;
			  end rotacion_dch_izq;

		begin -- begin del insert
			  if a = null then
				a := new nodo'(num, balanceado, null, null, null, null, 0);
				rebalancear := true;
				insertado := true;
			  elsif num < a.val then
				--Tendremos que insertar el numero en el subarbol izquierdo
				insert(a.izq, num, rebalancear, insertado);


				--Actualizamos la lista de predecesores y sucesores debido a la insercion en el subarbol izquierdo
				if insertado then
					if a.pred /= null then 
						a.izq.pred := a.pred;
						a.pred.succ := a.izq;
					end if;
					a.izq.succ := a;
					a.pred := a.izq;

					--Actualizamos el monticulo del conjunto
					if a.izq.pred /= null then
						if a.izq.succ /= null then
							--Eliminamos la distancia entre los que eran consecutivos antes de la insercion
							elimina_item(c.h, a.izq.pred.i_dist);
							--Insertamos la distancia entre el nuevo nodo y su sucesor
							aniadir(c.h, a.izq.succ.val - a.izq.val, a.izq);
						end if;
						aniadir(c.h, a.izq.val - a.izq.pred.val, a.izq.pred);
					else --No tiene predecesor
						if a.izq.succ /= null then
							aniadir(c.h, a.izq.succ.val - a.izq.val, a.izq);
							--Si no tiene ni sucesor ni predecesor no tenemos que hacer nada
						end if;
					end if;
					
					insertado := false;
				end if;
				

				
				if rebalancear then
				  case a.balance is
					when pesado_izq =>
					  if a.izq.balance = pesado_izq then
						rotacion_izq(a);
					  else
						rotacion_izq_dch(a);
					  end if;
					  rebalancear:=false;
					when balanceado =>
					  a.balance:=pesado_izq;
					when pesado_der =>
					  a.balance:=balanceado;
					  rebalancear:=false;
				  end case;
				end if;


			  elsif a.val<num then
				--Tendremos que insertar el numero en el subarbol derecho
				insert(a.der,num,rebalancear, insertado);

				--Actualizamos la lista de predecesores y sucesores debido a la insercion en el subarbol derecho 
				if insertado then
					if a.succ /= null then 
						a.der.succ := a.succ;
						a.succ.pred := a.der;
					end if;
					a.succ := a.der;
					a.der.pred := a;

					--Acutalizamos el monticulo del conjunto
					if a.der.pred /= null then
						if a.der.succ /= null then --tiene predecesor y sucesor
							elimina_item(c.h, a.der.pred.i_dist);
							aniadir(c.h, a.der.succ.val - a.der.val, a.der);
						end if;
						aniadir(c.h, a.der.val - a.der.pred.val, a.der.pred);
					else --No tiene predecesor
						if a.der.succ /= null then
							aniadir(c.h, a.der.succ.val - a.der.val, a.der);
							--Si no tenemos predecesor ni sucesor no tenemos que hacer nada
						end if;
					end if;
					
					insertado := false;
				end if;

				if rebalancear then
				  case a.balance is
					when pesado_izq =>
					  a.balance:=balanceado;
					  rebalancear:=false;
					when balanceado =>
					  a.balance:=pesado_der;
					when pesado_der =>
					  if a.der.balance=pesado_der then
						rotacion_dch(a);
					  else
						rotacion_dch_izq(a);
					  end if;
					  rebalancear:=false;
				  end case;
				end if;

			  else
				a.val:=num;
			  end if;
		end insert;

	begin --begin del insertar
   		rebalancear:=false;
    	insert(c.raiz, num, rebalancear, insertado);
	end insertar;



	procedure borrar (c: in out conj; num: in natural) is
	-- Borra el natural del conjunto; si no estaba, lo deja igual
	rebalancear: boolean;
	borrado: boolean := false;

		procedure borra (a: in out avl; num: in natural; rebalancear: in out boolean; borrado: in out boolean) is
			aux: avl;

			  procedure bal_izq(a:in out avl; rebalancear:in out boolean) is
			  --véase la documentación facilitada en clase
				a_der,a_izq:avl;
				balance_sub_a:factores_balance;
			  begin
				case a.balance is
				  when pesado_izq =>
					a.balance:=balanceado;
				  when balanceado =>
					a.balance:=pesado_der;
					rebalancear:=false;
				  when pesado_der =>
					a_der:=a.der;
					balance_sub_a:=a_der.balance;
					if balance_sub_a/=pesado_izq then
					  a.der:=a_der.izq;
					  a_der.izq:=a;
					  if balance_sub_a=balanceado then
						a.balance:=pesado_der;
						a_der.balance:=pesado_izq;
						rebalancear:=false;
					  else
						a.balance:=balanceado;
						a_der.balance:=balanceado;
					  end if;
					  a:=a_der;
					else
					  a_izq:=a_der.izq;
					  balance_sub_a:=a_izq.balance;
					  a_der.izq:=a_izq.der;
					  a_izq.der:=a_der;
					  a.der:=a_izq.izq;
					  a_izq.izq:=a;
					  if balance_sub_a=pesado_der then
						a.balance:=pesado_izq;
						a_der.balance:=balanceado;
					  elsif balance_sub_a=balanceado then
						a.balance:=balanceado;
						a_der.balance:=balanceado;
					  else
						a.balance:=balanceado;
						a_der.balance:=pesado_der;
					  end if;
					  a:=a_izq;
					  a_izq.balance:=balanceado;
					end if;
				end case;
			  end bal_izq;

			  procedure bal_dch(a:in out avl; rebalancear:in out boolean) is
			  --véase la documentación facilitada en clase
				a_izq,a_der:avl;
				balance_sub_a:factores_balance;
			  begin
				case a.balance is
				  when pesado_der =>
					a.balance:=balanceado;
				  when balanceado =>
					a.balance:=pesado_izq;
					rebalancear:=false;
				  when pesado_izq =>
					a_izq:=a.izq;
					balance_sub_a:=a_izq.balance;
					if balance_sub_a/=pesado_der then
					  a.izq:=a_izq.der;
					  a_izq.der:=a;
					  if balance_sub_a=balanceado then
						a.balance:=pesado_izq;
						a_izq.balance:=pesado_der;
						rebalancear:=false;
					  else
						a.balance:=balanceado;
						a_izq.balance:=balanceado;
					  end if;
					  a:=a_izq;
					else
					  a_der:=a_izq.der;
					  balance_sub_a:=a_der.balance;
					  a_izq.der:=a_der.izq;
					  a_der.izq:=a_izq;
					  a.izq:=a_der.der;
					  a_der.der:=a;
					  if balance_sub_a=pesado_izq then
						a.balance:=pesado_der;
						a_izq.balance:=balanceado;
					  elsif balance_sub_a=balanceado then
						a.balance:=balanceado;
						a_izq.balance:=balanceado;
					  else
						a.balance:=balanceado;
						a_izq.balance:=pesado_izq;
					  end if;
					  a:=a_der;
					  a_der.balance:=balanceado;
					end if;
				end case;
			  end bal_dch;

			  --Modificacion en la firma del procedimiento borrar_maxima_clave
			  procedure borrar_maxima_clave(a: in out avl;
											b: in out avl;
											rebalancear: in out boolean) is
			  --véase la documentación facilitada en clase
				aux:avl;
			  begin
				if a.der = null then
				  --Actualizamos los campos del nodo que queremos borrar
				  b.val := a.val;					
				  b.pred := a.pred;
				  b.succ := a.succ;
				  b.i_dist := a.i_dist;

				  --Actualizamos la lista de predecesores y sucesores del nodo que vamos a liberar
				  --ya que sino se quedarán apuntado a una zona de memoria sin contenido
				  a.succ.pred := b;
				  if a.pred /= null then a.pred.succ := b; end if;

				  aux := a;
				  a := a.izq;
				  disponer(aux);
				  rebalancear := true;
				else
				  borrar_maxima_clave(a.der, b, rebalancear);
				  if rebalancear then
					bal_dch(a,rebalancear);
				  end if;
				end if;
			  end borrar_maxima_clave;

	begin --begin de borra
		if a /= null then
			if num < a.val then
			  borra(a.izq, num, rebalancear, borrado);
			  
			  if rebalancear then
				bal_izq(a,rebalancear);
			  end if;

			elsif a.val < num then
			  borra(a.der, num, rebalancear, borrado);
			  
			  if rebalancear then
				bal_dch(a, rebalancear);
			  end if;
			  
			else --Hemos encontrado el nodo a borrar que sera 'a'
			
			--Codigo para actualizar la lista de sucesores y el monticulo
			if a.pred /= null then --el nodo que eliminamos tiene predecesor
				elimina_item(c.h, a.pred.i_dist); --eliminamos la distancia entre el y su predecesor

				if a.succ /= null then --el nodo que eliminamos tiene predecesor y sucesor
					elimina_item(c.h, a.i_dist); --eliminamos la distancia entre su sucesor y el
					aniadir(c.h, a.succ.val - a.pred.val, a.pred); --insertamos la distancia entre su

					a.succ.pred := a.pred;
				else --tiene predecesor pero no tiene sucesor
					a.pred.i_dist := 0; --borramos la marca que quedaba en el nodo!!
				end if;
				a.pred.succ := a.succ;

			else -- no tiene predecesor (es el primer nodo de la lista)

				if a.succ /= null then --el nodo que eliminamos solo tiene sucesor
					elimina_item(c.h, a.i_dist); --eliminamos la distancia entre su sucesor y el
					a.succ.pred := null;
					--Si no tiene ni sucesor ni predecesor NO tenemos que hacer nada
				end if;

			end if;

			
			  if a.izq = null then --no tiene hijo izquierdo
				aux:=a;
				a:=a.der;
				disponer(aux);
				rebalancear:=true;
			  elsif a.der = null then --no tiene hijo derecho
				aux:=a;
				a:=a.izq;
				disponer(aux);
				rebalancear:=true;
			  else --tiene sendos hijos

				borrar_maxima_clave(a.izq, a, rebalancear);

				--Despues del borrado tambien tenemos que actualizar el contenido del monticulo
				c.h.v(a.i_dist).e := a;

				if rebalancear then
				  bal_izq(a, rebalancear);
				end if;
			  end if;
			end if;
		end if;
	end borra;

	begin --begin de borrar
			rebalancear:=false;
			borra(c.raiz, num, rebalancear, borrado);
	end borrar;


  procedure put_inorden(c:in conj) is
  --Prueba para encontrar el siguiente nodo en inorden del arbol
	aux: avl := null;

 	procedure poner_inorden (a: avl; b: avl) is
	begin
		   if a /= null then
			 poner_inorden(a.izq, a);
				put(a.val, 0); put(", ");
			 poner_inorden(a.der, a);
		   end if;
	end poner_inorden;

  begin
	poner_inorden(c.raiz, aux);
	new_line;
	new_line;
  end put_inorden;


	function buscar (c: conj; num: natural) return boolean is
	-- Si el numero esta en el conjunto devuelve verdad; en caso contrario falso
		e: boolean;

		procedure busqueda (a: in avl; num: in natural; exito: out boolean) is
		begin
			if a = null then
				exito := false;
			else
				if num = a.val then
					exito := true;
				elsif num < a.val then
					busqueda(a.izq, num, exito);
				else
					busqueda(a.der, num, exito);
				end if;
			end if;
		end busqueda;

	begin 
		busqueda(c.raiz, num, e);
		return e;
	end buscar;


	function dm (c: conj) return natural is
	-- Devuelve la distancia minima del conjunto
	begin
		return c.h.v(1).d;
	end dm;


	 procedure pintar_conj(c: in conj) is
	 --Procedimiento utilizado para comprobaciones en el arbol
	 --Pinta el contenido del arbol para comprobar que la informacion del nodo sea correcta
	 
	   procedure pintar(a: in avl; margen: in positive_count) is
	   begin
	     if a /= null then
			set_col(margen);
			put(a.val, 0);
--			put("  indice: "); put(a.i_dist, 0); 
--			if a.i_dist /= 0 then	put("  distancia: "); put(c.h.v(a.i_dist).d, 0); end if;
--			if a.succ /= null then 
--				put(" succ: "); put(a.succ.val, 0);
--			else
--				put("  succ: NULL");
--			end if;
--			if a.pred /= null then
--				put("  pred: "); put(a.pred.val, 0);
--			else
--				put("  pred: NULL");
--			end if;
			new_line;
			pintar(a.izq,margen+3);
			pintar(a.der,margen+3);
	     end if;
	 end pintar;
	 
	 begin
	 	if c.raiz /= null then
	    	pintar(c.raiz, 1);
		else
		put_line("Conjunto vacio.");
		end if;
	 end pintar_conj;


	procedure pintar_inorden (c: in conj) is
	--Comprueba la estructura de todos los sucesores en el conjunto
		aux: avl;
	begin
		if c.raiz = null then
			put_line("Arbol AVL vacio.");
		else
			aux := c.raiz;
			--Hemos recorrido hasta finalizar todos los hijos izquierdos por tanto estamos en el
			--menor nodo del arbol
			while aux.izq /= null loop
				aux := aux.izq;
			end loop;
			
			while aux /= null loop
				put(aux.val, 0); 
				if aux.succ /= null then 
					put(", ");
				else
					put(".");
				end if;
				aux := aux.succ;
			end loop;
			new_line;
			
		end if;
	end pintar_inorden;


	procedure listar_predecesores (c: in conj) is
	--Comprueba la estructura de todos los predecesores en el conjunto
		aux: avl;
	begin
		if c.raiz = null then
            put_line("Arbol AVL vacio.");
        else
            aux := c.raiz;
            --Hemos recorrido hasta finalizar todos los hijos derechos por tanto estamos en el
            --mayor nodo del arbol
            while aux.der /= null loop
                aux := aux.der;
            end loop;

            while aux /= null loop
                put(aux.val, 0);
                if aux.pred /= null then
                    put(", ");
                else
                    put(".");
                end if;
                aux := aux.pred;
            end loop;
            new_line;

        end if;
	end listar_predecesores;


		procedure pintar_monticulo (c: in conj) is
		--Comprueba el contenido del monticulo
		begin
			if c.h.num /= 0 then
				for i in 1 .. c.h.num loop
					put("v("); put(i,0); put("): "); put(c.h.v(i).d, 0);
					if c.h.v(i).e /= null then
						put("  -> "); put(c.h.v(i).e.val, 0);
					end if;
					new_line;
				end loop;
				put_line("**************************");
				put_line("");
			else
				put_line("Monticulo vacio.");
			end if;
		end pintar_monticulo;

end conj_dm;
