(defun longitud(lst)
(if (null lst)
0
(1+ (longitud (cdr lst)))))

; USO : (setq lst '(a b c))
; (longitud lst)
