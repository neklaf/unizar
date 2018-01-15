FLAGS=-D__AT_HOME__ -g

myrdp1: Rdp.o carro_1.o
	gcc Rdp.o carro_1.o -o myrdp

myrdp2: Rdp.o carro_2.o
	gcc Rdp.o carro_2.o -o myrdp

myrdp3: Rdp.o carro_3.o
	gcc Rdp.o carro_3.o -o myrdp

Rdp.o: Rdp.c
	gcc $(FLAGS) -c Rdp.c

carro_1.o: carro_1.c
	gcc $(FLAGS) -c carro_1.c

carro_2.o: carro_2.c
	gcc $(FLAGS) -c carro_2.c

carro_3.o: carro_3.c
	gcc $(FLAGS) -c carro_3.c

clean:
	rm -rf *.o
