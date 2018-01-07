--|::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
--| MODULO DE IMPLEMENTACION DEL TAD 'lista generica con acceso por posicion'
--|::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::


with unchecked_deallocation;
package body listaGenerica is

  --type unDato;
  --type ptUnDato is access unDato;
  --type unDato is record
  --                 dato:elemento;
  --                 sig:ptUnDato;
  --                end record;
  --type lista is record
  --                prim,ult:ptUnDato;
  --                n:natural;
  --              end record;
                 
  procedure liberaDato is new unchecked_deallocation(unDato,ptUnDato);

  procedure creaVacia(l:out lista) is
  begin
    l.prim:=null; 
    l.ult:=null; 
	l.actual:=null;
    l.n:=0;
  end creaVacia;

  procedure aniadePrincipio(e:in elemento; l:in out lista) is
    aux:ptUnDato;
  begin
    aux:=new unDato;
    aux.dato:=e;
    aux.sig:=l.prim;
    l.prim:=aux;
    if l.ult=null then
      l.ult:=l.prim;
    end if;
	l.actual:= l.prim;
    l.n:=l.n+1;
  end aniadePrincipio;

  procedure concatena(l1:in out lista; l2:in lista) is
  begin
    if l2.n/=0 then
      if l1.n=0 then
        l1.prim:=l2.prim; 
        l1.ult:=l2.ult; 
		l1.actual:= l2.actual;
        l1.n:=l2.n;
      else
        l1.ult.sig:=l2.prim;
        l1.ult:=l2.ult;
        l1.n:=l1.n+l2.n;
      end if;
    end if;
  end concatena;

  procedure creaUnitaria(e:in elemento; l:out lista) is
    aux:lista;
  begin
    aux.prim:=new unDato;
    aux.prim.dato:=e;
    aux.prim.sig:=null;
    aux.ult:=aux.prim;
	aux.actual:= aux.prim;
    aux.n:=1;
    l:=aux;
  end creaUnitaria;

  procedure aniadeFinal(l:in out lista; e:in elemento) is
  begin
    if l.ult=null then
      creaUnitaria(e,l);
    else
      l.ult.sig:=new unDato;
      l.ult:=l.ult.sig;
      l.ult.dato:=e;
      l.ult.sig:=null;
      l.n:=l.n+1;
    end if;
  end aniadeFinal;

  procedure eliminaPrimero(l:in out lista) is
    aux:ptUnDato;
  begin
    aux:=l.prim;
	if(l.actual = l.prim) then 
		l.actual := l.prim.sig;
	end if;
    l.prim:=l.prim.sig;
    if l.prim=null then
      l.ult:=null;
    end if;
    l.n:=l.n-1;
    liberaDato(aux);
  end eliminaPrimero;

  procedure eliminaUltimp(l:in out lista) is
    aux:ptUnDato;
  begin
    if l.prim=l.ult then
      liberaDato(l.prim);
      creaVacia(l);
    else
      aux:=l.prim;
      while aux.sig/=l.ult loop 
        aux:=aux.sig;
      end loop;
      aux.sig:=null;
      liberaDato(l.ult);
      l.ult:=aux;
      l.n:=l.n-1;
    end if;
  end eliminaUltimo;

  function observaPrimero(l:in lista) return elemento is
  begin
    return l.prim.dato;
  end observaPrimero;

  function observaUltimo(l:in lista) return elemento is
  begin
    return l.ult.dato;
  end observaUltimo;

  function long(l:in lista) return natural is
  begin
    return l.n;
  end long;

  function esta(e:in elemento; l:in lista) return boolean is
    aux:ptUnDato; encontrado:boolean;
  begin
    if l.prim=null then
      return false;
    else
      aux:=l.prim;
      while aux.dato/=e and aux/=l.ult loop
        aux:=aux.sig;
      end loop;
      return aux.dato=e;
    end if;
  end esta;

  function esVacia(l:in lista) return boolean is
  begin
    return l.n=0;
  end esVacia;

  procedure inser(l:in out lista; i:in positive; e:in elemento) is
    aux,sigAux:ptUnDato; indSig:positive;
  begin
    if i=1 then
      aniadePrincipio(e,l);
    elsif  i=long(l)+1 then
      aniadeFinal(l,e);
    else -- (1<i) and (i<long(l)+1)
      aux:=l.prim;
      indSig:=2;
      while indSig<i loop
        aux:=aux.sig;
        indSig:=indSig+1;
      end loop;
      sigAux:=aux.sig;
      aux.sig:=new unDato;
      aux:=aux.sig;
      aux.dato:=e;
      aux.sig:=sigAux;
      l.n:=l.n+1;
    end if;
  end inser;

  procedure borra(l:in out lista; i:in positive) is
    aux,sigAux:ptUnDato; indSig:positive;
  begin
    if i=1 then
      eliminaPrimero(l);
    elsif  i=long(l) then
      eliminaUltimo(l);
    else -- (1<i) and (i<long(l))
      aux:=l.prim;
      indSig:=2;
      while indSig<i loop
        aux:=aux.sig;
        indSig:=indSig+1;
      end loop;
      sigAux:=aux.sig;
      aux.sig:=sigAux.sig;
      liberaDato(sigAux);
      l.n:=l.n-1;
    end if;
  end borra;

  procedure modif(l:in out lista; i:in positive; e:in elemento) is
    aux:ptUnDato; ind:positive;
  begin
    if i=long(l) then
      l.ult.dato:=e;
    else
      aux:=l.prim;
      ind:=1;
      while ind<i loop
        aux:=aux.sig;
        ind:=ind+1;
      end loop;
      aux.dato:=e;
    end if;
  end modif;

  function observa(l:in lista; i:in positive) return elemento is
    aux:ptUnDato; ind:positive;
  begin
    if i=long(l) then
      return l.ult.dato;
    else
      aux:=l.prim;
      ind:=1;
      while ind<i loop
        aux:=aux.sig;
        ind:=ind+1;
      end loop;
      return aux.dato;
    end if;
  end observa;

  function pos(e:in elemento; l:in lista) return positive is
    aux:ptUnDato; i:positive;
  begin
    aux:=l.prim;
    i:=1;
    while aux.dato/=e loop
      aux:=aux.sig;
      i:=i+1;
    end loop;
    return i;
  end pos;

  procedure asignar(nueva:out lista; vieja:in lista) is
    nnueva,aux:lista;
  begin
    creaVacia(nnueva);
    aux:=vieja;
    for i in 1..long(aux) loop
      aniadePrincipio(nnueva,aux.prim.dato);
      aux.prim:=aux.prim.sig;
    end loop;
    nueva:=nnueva;
  end asignar;

  procedure liberar(l:in out lista) is
    n:natural;
  begin
    n:=long(l);
    for i in 1..n loop
      eliminaPrimero(l);
    end loop;
  end liberar;
  
  
  
--------------------------------------------------------------------------


  function inicializaActual(l: in out lista; pos: in positive) return elemento is
  -- pone al puntero actual apuntando al dato de posicion pos.
  
  aux:ptUnDato; i:positive;
  
  begin
  
  l.actual:= pos;
  
  if pos=long(l) then
      l.actual:= l.ult.dato;
  else
      aux:=l.prim;
      ind:=1;
      while ind<pos loop
        aux:=aux.sig;
        ind:=ind+1;
      end loop;
      l.actual:= aux;
  end if;
  
  return l.actual.dato;
  
  end inicializaActual;
  
  
  --------------------------------------------------------------------------
  
  
  function dameActual(l: in out lista) return elemento is
  -- devuelve el elemento correspondiente al dato apuntado por actual
  
  begin
  
  	return l.actual.dato;
  
  end dameActual;
  
  
  --------------------------------------------------------------------------
  
 function siguienteActual (l: in out lista) return elemento is
 -- puntero actual apunta al dato siguiente.
 
 begin
 
 	l.actual:= l.actual.sig;
	
	return l.actual.dato;
 
 end siguienteActual;
 
 --------------------------------------------------------------------------
  

end listaGenerica;
