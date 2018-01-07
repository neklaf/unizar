--
-- MODULO DE IMPLEMENTACION DEL TAD 'diccionario',
-- ALMACENADO EN UN ARBOL AVL
--
-- Version: 2.0
-- Fecha: 25-IX-2002
-- Autor: Javier Campos Laclaustra (jcampos@posta.unizar.es)
-- Cambios a la V.1.0: correcciones en rotacion_izq_dch y dch_izq sugeridas por Isaac Trigo.
-- Para la especificación de las operaciones véase el módulo de definición.
--
with unchecked_deallocation;
with text_io; use text_io;
package body arbol_avl is
--
--  type avl is access nodo;
--  type factor_equilibrio is (pesado_izq, equilibrado, pesado_dch);
--  type nodo is
--    record
--      clave:tp_clave;
--      valor:tp_valor;
--      equilibrio:factor_equilibrio;
--      izq,dch:avl;
--    end record;
--
  procedure disponer is new unchecked_deallocation(nodo,avl);

  procedure vacio(a:out avl) is
  begin
    a:=null;
  end vacio;

  procedure modificar(a:in out avl; clave:in tp_clave; valor:in tp_valor) is
    reequilibrar:boolean;

    procedure modif(a:in out avl; clave:in tp_clave; valor:in tp_valor;
                    reequilibrar:in out boolean) is
    --véase la documentación facilitada en clase
                    
      procedure rotacion_izq(a:in out avl) is
      --véase la documentación facilitada en clase
        aux:avl;
      begin
        aux:=a.izq;
        a.izq:=aux.dch;
        a.equilibrio:=equilibrado;
        aux.dch:=a;
        a:=aux;
        a.equilibrio:=equilibrado;
      end rotacion_izq;

      procedure rotacion_dch(a:in out avl) is
      --véase la documentación facilitada en clase
        aux:avl;
      begin
        aux:=a.dch;
        a.dch:=aux.izq;
        a.equilibrio:=equilibrado;
        aux.izq:=a;
        a:=aux;
        a.equilibrio:=equilibrado;
      end rotacion_dch;

      procedure rotacion_izq_dch(a:in out avl) is
      --véase la documentación facilitada en clase
        aux_1,aux_2:avl;
      begin
        aux_1:=a.izq;
        aux_2:=a.izq.dch;
        aux_1.dch:=aux_2.izq;
        aux_2.izq:=aux_1;
        a.izq:=aux_2;
        if aux_2.equilibrio=pesado_izq then
          aux_1.equilibrio:=equilibrado;
          a.equilibrio:=pesado_dch;
        elsif aux_2.equilibrio=equilibrado then
		  aux_1.equilibrio:=equilibrado;
              a.equilibrio:=equilibrado;
        else
          aux_1.equilibrio:=pesado_izq;
          a.equilibrio:=equilibrado;
        end if;
        a.izq:=aux_2.dch;
        aux_2.dch:=a;
        aux_2.equilibrio:=equilibrado;
        a:=aux_2;
      end rotacion_izq_dch;

      procedure rotacion_dch_izq(a:in out avl) is
      --véase la documentación facilitada en clase
        aux_1,aux_2:avl;
      begin
        aux_1:=a.dch;
        aux_2:=a.dch.izq;
        aux_1.izq:=aux_2.dch;
        aux_2.dch:=aux_1;
        a.dch:=aux_2;
        if aux_2.equilibrio=pesado_dch then
          aux_1.equilibrio:=equilibrado;
          a.equilibrio:=pesado_izq;
        elsif aux_2.equilibrio=equilibrado then
          aux_1.equilibrio:=equilibrado;
          a.equilibrio:=equilibrado;
        else
          aux_1.equilibrio:=pesado_dch; 
          a.equilibrio:=equilibrado;
        end if;
        a.dch:=aux_2.izq;
        aux_2.izq:=a;
        aux_2.equilibrio:=equilibrado;
        a:=aux_2;
      end rotacion_dch_izq;

    begin --de modif
      if a=null then
        a:=new nodo'(clave,valor,equilibrado,null,null);
        reequilibrar:=true;
      elsif clave<a.clave then
        modif(a.izq,clave,valor,reequilibrar);
        if reequilibrar then
          case a.equilibrio is
            when pesado_izq =>
              if a.izq.equilibrio=pesado_izq then
                rotacion_izq(a);
              else
                rotacion_izq_dch(a);
              end if;
              reequilibrar:=false;
            when equilibrado =>
              a.equilibrio:=pesado_izq;
            when pesado_dch =>
              a.equilibrio:=equilibrado;
              reequilibrar:=false;
          end case;
        end if;
      elsif a.clave<clave then
        modif(a.dch,clave,valor,reequilibrar);
        if reequilibrar then
          case a.equilibrio is
            when pesado_izq =>
              a.equilibrio:=equilibrado;
              reequilibrar:=false;
            when equilibrado =>
              a.equilibrio:=pesado_dch;
            when pesado_dch =>
              if a.dch.equilibrio=pesado_dch then
                rotacion_dch(a);
              else
                rotacion_dch_izq(a);
              end if;
              reequilibrar:=false;
          end case;
        end if;
      else
        a.valor:=valor;
      end if;
    end modif;

  begin --de modificar
    reequilibrar:=false;
    modif(a,clave,valor,reequilibrar);
  end modificar;

  procedure borrar(a:in out avl; clave:in tp_clave) is
    reequilibrar:boolean;

    procedure borra(a:in out avl; clave:in tp_clave; reequilibrar:in out boolean) is
    --véase la documentación facilitada en clase
      aux:avl;

      procedure equil_izq(a:in out avl; reequilibrar:in out boolean) is
      --véase la documentación facilitada en clase
        a_dch,a_izq:avl;
        equilibrio_sub_a:factor_equilibrio;
      begin
        case a.equilibrio is
          when pesado_izq =>
            a.equilibrio:=equilibrado;
          when equilibrado =>
            a.equilibrio:=pesado_dch;
            reequilibrar:=false;
          when pesado_dch =>
            a_dch:=a.dch;
            equilibrio_sub_a:=a_dch.equilibrio;
            if equilibrio_sub_a/=pesado_izq then
              a.dch:=a_dch.izq;
              a_dch.izq:=a;
              if equilibrio_sub_a=equilibrado then
                a.equilibrio:=pesado_dch;
                a_dch.equilibrio:=pesado_izq;
                reequilibrar:=false;
              else
                a.equilibrio:=equilibrado;
                a_dch.equilibrio:=equilibrado;
              end if;
              a:=a_dch;
            else
              a_izq:=a_dch.izq;
              equilibrio_sub_a:=a_izq.equilibrio;
              a_dch.izq:=a_izq.dch;
              a_izq.dch:=a_dch;
              a.dch:=a_izq.izq;
              a_izq.izq:=a;
              if equilibrio_sub_a=pesado_dch then
                a.equilibrio:=pesado_izq;
                a_dch.equilibrio:=equilibrado;
              elsif equilibrio_sub_a=equilibrado then
                a.equilibrio:=equilibrado;
                a_dch.equilibrio:=equilibrado;
              else
                a.equilibrio:=equilibrado;
                a_dch.equilibrio:=pesado_dch;
              end if;
              a:=a_izq;
              a_izq.equilibrio:=equilibrado;
            end if;
        end case;
      end equil_izq;

      procedure equil_dch(a:in out avl; reequilibrar:in out boolean) is
      --véase la documentación facilitada en clase
        a_izq,a_dch:avl;
        equilibrio_sub_a:factor_equilibrio;
      begin
        case a.equilibrio is
          when pesado_dch =>
            a.equilibrio:=equilibrado;
          when equilibrado =>
            a.equilibrio:=pesado_izq;
            reequilibrar:=false;
          when pesado_izq =>
            a_izq:=a.izq;
            equilibrio_sub_a:=a_izq.equilibrio;
            if equilibrio_sub_a/=pesado_dch then
              a.izq:=a_izq.dch;
              a_izq.dch:=a;
              if equilibrio_sub_a=equilibrado then
                a.equilibrio:=pesado_izq;
                a_izq.equilibrio:=pesado_dch;
                reequilibrar:=false;
              else
                a.equilibrio:=equilibrado;
                a_izq.equilibrio:=equilibrado;
              end if;
              a:=a_izq;
            else
              a_dch:=a_izq.dch;
              equilibrio_sub_a:=a_dch.equilibrio;
              a_izq.dch:=a_dch.izq;
              a_dch.izq:=a_izq;
              a.izq:=a_dch.dch;
              a_dch.dch:=a;
              if equilibrio_sub_a=pesado_izq then
                a.equilibrio:=pesado_dch;
                a_izq.equilibrio:=equilibrado;
              elsif equilibrio_sub_a=equilibrado then
                a.equilibrio:=equilibrado;
                a_izq.equilibrio:=equilibrado;
              else
                a.equilibrio:=equilibrado;
                a_izq.equilibrio:=pesado_izq;
              end if;
              a:=a_dch;
              a_dch.equilibrio:=equilibrado;
            end if;
        end case;
      end equil_dch;

      procedure borrar_maxima_clave(a:in out avl;
                                    clave:out tp_clave; valor:out tp_valor;
                                    reequilibrar:in out boolean) is
      --véase la documentación facilitada en clase
        aux:avl;
      begin
        if a.dch=null then
          clave:=a.clave;
          valor:=a.valor;
          aux:=a;
          a:=a.izq;
          disponer(aux);
          reequilibrar:=true;
        else
          borrar_maxima_clave(a.dch,clave,valor,reequilibrar);
          if reequilibrar then
            equil_dch(a,reequilibrar);
          end if;
        end if;
      end borrar_maxima_clave;

    begin --de borra
      if a/=null then
        if clave<a.clave then
          borra(a.izq,clave,reequilibrar);
          if reequilibrar then
            equil_izq(a,reequilibrar);
          end if;
        elsif a.clave<clave then
          borra(a.dch,clave,reequilibrar);
          if reequilibrar then
            equil_dch(a,reequilibrar);
          end if;
        else
          if a.izq=null then
            aux:=a;
            a:=a.dch;
            disponer(aux);
            reequilibrar:=true;
          elsif a.dch=null then
            aux:=a;
            a:=a.izq;
            disponer(aux);
            reequilibrar:=true;
          else
            borrar_maxima_clave(a.izq,a.clave,a.valor,reequilibrar);
            if reequilibrar then
              equil_izq(a,reequilibrar);
            end if;
          end if;
        end if;
      end if;
    end borra;

  begin --de borrar
    reequilibrar:=false;
    borra(a,clave,reequilibrar);
  end borrar;

  procedure buscar(a:in avl; clave:in tp_clave; exito:out boolean; valor:out tp_valor) is
  begin
   if a=null then
     exito:=false;
   else
     if a.clave=clave then
       exito:=true;
       valor:=a.valor;
     elsif clave<a.clave then
       buscar(a.izq,clave,exito,valor);
     else
       buscar(a.dch,clave,exito,valor);
     end if;
   end if;
  end buscar;

  function es_vacio(a:avl) return boolean is
  begin
    return a=null;
  end es_vacio;

  procedure put_inorden(a:in avl) is
  begin
   if a/=null then
     put_inorden(a.izq);
     put_clave(a.clave);
     put(": ");
     put_valor(a.valor);
     new_line;
     put_inorden(a.dch);
   end if;
  end put_inorden;

  procedure pintar_arbol(a:in avl) is
    procedure pintar(a:in avl; margen:in positive_count) is
    begin
      if a/=null then
        set_col(margen);
        put_clave(a.clave);
        new_line;
        pintar(a.izq,margen+3);
        pintar(a.dch,margen+3);
      end if;
    end pintar;
  begin
    pintar(a,1);
  end pintar_arbol;

  function altura(a:avl) return natural is
  
    function max(izq,dch:natural) return natural is
    -- devuelve el máximo de dos naturales
    begin
      if izq>=dch then
        return izq;
      else
        return dch;
      end if;
    end max;
    
  begin --de altura
    if a.izq=null and a.dch=null then
      return 0;
    elsif a.izq=null and a.dch/=null then
      return 1+altura(a.dch);
    elsif a.izq/=null and a.dch=null then
      return 1+altura(a.izq);
    else
      return 1+max(altura(a.izq),altura(a.dch));
    end if;
  end altura;

  function equilibrado(a:avl) return boolean is
    hi,hd:natural;
  begin
    if a=null then
      return true;
    elsif a.izq=null and a.dch=null then
      return true;
    elsif a.izq=null and a.dch/=null then
      return altura(a.dch)=0;
    elsif a.izq/=null and a.dch=null then
      return altura(a.izq)=0;
    else
      hi:=altura(a.izq);
      hd:=altura(a.dch);
      return abs(hi-hd)<=1 and then equilibrado(a.izq) and then equilibrado(a.dch);
    end if;
  end equilibrado;

  function test_factores_equilibrio(a:avl) return boolean is
    hi,hd:natural; ok:boolean;
  begin
    if a=null then
      return true;
    elsif a.izq=null and a.dch=null then
      return a.equilibrio=equilibrado;
    elsif a.izq=null and a.dch/=null then
      return a.equilibrio=pesado_dch and then test_factores_equilibrio(a.dch);
    elsif a.izq/=null and a.dch=null then
      return a.equilibrio=pesado_izq and then test_factores_equilibrio(a.izq);
    else
      hi:=altura(a.izq);
      hd:=altura(a.dch);
      if hi=hd then
        ok:=a.equilibrio=equilibrado;
      elsif hi>hd then
        ok:=a.equilibrio=pesado_izq;
      else
        ok:=a.equilibrio=pesado_dch;
      end if;
      return ok and then test_factores_equilibrio(a.izq)
                and then test_factores_equilibrio(a.dch);
    end if;
  end test_factores_equilibrio;

end arbol_avl;