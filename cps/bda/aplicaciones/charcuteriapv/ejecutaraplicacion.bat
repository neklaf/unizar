set classpath=%classpath%;.;C:\Aplicaciones\charcuteriaPV\lib\odbse.jar;C:\Aplicaciones\charcuteriaPV\lib\jdo.jar
java com.objectdb.Enhancer Central/*.class PuntoDeVenta/*.class
java Main
