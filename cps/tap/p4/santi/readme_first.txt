T�cnicas Avanzadas de Programaci�n - 2003/04
Pr�ctica 4: Estructuras auto-organizativas

Para ejecutar la bateria de test se encuentra disponibles dos programas:
    - test_lista_auto <fichero_palabras> <fichero_definiciones> <numero_inserciones> <numero_busquedas> <numero_borrados>
    
        Ejecuta una bateria de test sobre una lista lineal auto-organizativa. Genera una lista con <numero_inserciones> elementos y realiza
        sobre �l <numero_busquedas> busquedas y <numero_borrados> borrados. Finalmente, escribe los resultados en el fichero
        "resultados_practica1.txt".
        
    - test_splay_tree <fichero_palabras> <fichero_definiciones> <numero_inserciones> <numero_busquedas> <numero_borrados>

        Ejecuta una bateria de test sobre un �rbol auto-organizativo ("splay tree"). Genera un �rbol con <numero_inserciones> elementos y realiza
        sobre �l <numero_busquedas> busquedas y <numero_borrados> borrados. Finalmente, escribe los resultados en el fichero
        "resultados_practica1.txt".

Ambos ficheros utilizan el mismo fichero de resultados. 
Debido a la implementaci�n de los mismos, el fichero de resultados debe existir (aunque sea vac�o) ya que �ste se abre
en modo "Append". Es por ello que el Makefile se encarga de crearlo vac�o si �ste no existiera (a trav�s del comando
touch).

**** NOTA ****
Por cuestiones de espacio a la hora de someter las practicas, los ficheros de palabras y definiciones que se incluyen
(palabras.txt y definiciones.txt) contienen solamente 1000 elementos cada uno (de los +100000 originales). Sin embargo, se incluyen ya resultados en
el correspondiente fichero de resultados con estructuras de datos mayores probadas en nuestros equipos a modo de
comparaci�n.

Los ficheros palabras.txt y definiciones.txt son el mismo realmente, simplemente uno hace de fichero de palabras y otro
de definiciones.
