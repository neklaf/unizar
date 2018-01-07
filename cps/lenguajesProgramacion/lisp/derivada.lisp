(defun derivada (var exp)
(cond	((numberp exp) 0)	;Si es un numero devuelve un '0'
	((symbolp exp) (if (equal exp var) 1 0))
	; Si es la variable respecto de la que se deriva devuelve un '1'
	; Si no es la variable respecto de la que se deriva se pone un 
	((and (listp exp) (eq (car exp) '+)) 
	(list '+ (derivada var (second exp)) (derivada var (cdr (cdr exp)))))	
	;Si es una suma!!
	
	((and (listp exp) (eq (car exp) '*)) 
	(list '+ (list '* (second exp) (derivada var (cdr (cdr exp)))) 
		(list '* (cdr (cdr exp)) (derivada var (second exp)))))
	;Si es un producto
)
)

; Una suma se define como una lista primer término es el símbolo '+
; Creo que puede fallar por el (cdr (cdr exp)) que tenemos que coger el tercer
; sumando
