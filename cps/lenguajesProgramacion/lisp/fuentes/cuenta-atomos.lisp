(defun cuenta-atomos (lst)
(cond 	((null lst) 0) ;fin condicion1
	((atom (car lst)) (+ 1 (cuenta-atomos (cdr lst)))) ;fin condicion2
	((listp (car lst)) (cuenta-atomos (car lst)))
	((symbolp (car lst)) (cuenta-atomos (cdr lst)))
	((numberp (car lst)) (cuenta-atomos (cdr lst)))
	);fin de cond
);fin de cuenta-atomos
;El problema de este codigo es que no distingue de los atomos repetidos
;La idea es ir recorriendo la lista y creando una nueva con todos los atomos
;no repetidos que hay!!
;Hay que utilizar la funcion 'member'

;En el fichero cuenta-atomos2.lisp creo que esta mejor definido el 
;mejor comportamiento de como he entendido yo las practicas!!
