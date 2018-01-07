(defun cuenta-atomos2 (lst)
(cond 	((null lst) 0)	;fin condicion1
 	((atom (car lst)) (+ 1 (cuenta-atomos2 (cdr lst))))
	((not (atom (car lst))) (cuenta-atomos2 (cdr lst)))
	)
)
