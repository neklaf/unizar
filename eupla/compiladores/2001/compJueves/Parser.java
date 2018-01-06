import java.util.*;

class Parser {
	private static final int maxT = 42;
	private static final int maxP = 42;

	private static final boolean T = true;
	private static final boolean x = false;
	private static final int minErrDist = 2;
	private static int errDist = minErrDist;

	static Token token;   // last recognized token
	static Token t;       // lookahead token

	// tipos de variables
	static final int undef = 0;
	static final int integer = 1;
	static final int bool = 2;
	static final int flotante = 3;
	static final int cte = 4;

	// formas para los tipos  (modes)
	static final int vars = 0;
	static final int reg = 1;
	static final int vector = 2;
	static final int proc = 3;      //???

/*----------------------------------------------------------------------------*/



	static void Error(int n) {
		if (errDist >= minErrDist) Scanner.err.ParsErr(n, t.line, t.col);
		errDist = 0;
	}

	static void SemError(int n) {
		if (errDist >= minErrDist) Scanner.err.SemErr(n, token.line, token.col);
		errDist = 0;
	}

	static boolean Successful() {
		return Scanner.err.count == 0;
	}

	static String LexString() {
		return token.str;
	}

	static String LexName() {
		return token.val;
	}

	static String LookAheadString() {
		return t.str;
	}

	static String LookAheadName() {
		return t.val;
	}

	private static void Get() {
		for (;;) {
			token = t;
			t = Scanner.Scan();
			if (t.kind <= maxT) {errDist++; return;}

			t = token;
		}
	}

	private static void Expect(int n) {
		if (t.kind == n) Get(); else Error(n);
	}

	private static boolean StartOf(int s) {
		return set[s][t.kind];
	}

	private static void ExpectWeak(int n, int follow) {
		if (t.kind == n) Get();
		else {
			Error(n);
			while (!StartOf(follow)) Get();
		}
	}

	private static boolean WeakSeparator(int n, int syFol, int repFol) {
		boolean[] s = new boolean[maxT+1];
		if (t.kind == n) {Get(); return true;}
		else if (StartOf(repFol)) return false;
		else {
			for (int i = 0; i <= maxT; i++) {
				s[i] = set[syFol][i] || set[repFol][i] || set[0][i];
			}
			Error(n);
			while (!s[t.kind]) Get();
			return StartOf(syFol);
		}
	}

	private static float Real() {
		float f;
		Expect(1);
		f = (new Float(token.val)).floatValue();
		return f;
	}

	private static boolean Booleano() {
		boolean b;
		Expect(1);
		b = Boolean.valueOf(token.val).booleanValue();
		return b;
	}

	private static void factor() {
		if (t.kind == 1) {
			Get();
			selector();
		} else if (t.kind == 2) {
			Get();
		} else if (t.kind == 14) {
			Get();
			expresion();
			Expect(15);
		} else if (t.kind == 39) {
			Get();
			factor();
		} else Error(43);
	}

	private static void term() {
		factor();
		while (t.kind == 35 || t.kind == 36 || t.kind == 37) {
			if (t.kind == 35) {
				Get();
			} else if (t.kind == 36) {
				Get();
			} else {
				Get();
				Expect(38);
			}
			factor();
		}
	}

	private static void ExpresionSimple() {
		if (t.kind == 32 || t.kind == 33) {
			if (t.kind == 32) {
				Get();
			} else {
				Get();
			}
		}
		term();
		while (t.kind == 32 || t.kind == 33 || t.kind == 34) {
			if (t.kind == 32) {
				Get();
			} else if (t.kind == 33) {
				Get();
			} else {
				Get();
			}
			term();
		}
	}

	private static void ParametrosActuales() {
		Expect(14);
		if (StartOf(1)) {
			expresion();
			while (t.kind == 19) {
				Get();
				expresion();
			}
		}
		Expect(15);
	}

	private static void expresion() {
		ExpresionSimple();
		if (StartOf(2)) {
			switch (t.kind) {
			case 9: {
				Get();
				break;
			}
			case 27: {
				Get();
				break;
			}
			case 28: {
				Get();
				break;
			}
			case 29: {
				Get();
				break;
			}
			case 30: {
				Get();
				break;
			}
			case 31: {
				Get();
				break;
			}
			}
			ExpresionSimple();
		}
	}

	private static void selector() {
		while (t.kind == 7 || t.kind == 40) {
			if (t.kind == 7) {
				Get();
				Expect(1);
			} else {
				Get();
				expresion();
				Expect(41);
			}
		}
	}

	private static void SentenciaWhile() {
		Expect(21);
		expresion();
		Expect(22);
		SecuenciaSentencias();
		Expect(6);
	}

	private static void SentenciaIf() {
		Expect(23);
		expresion();
		Expect(24);
		SecuenciaSentencias();
		while (t.kind == 25) {
			Get();
			expresion();
			Expect(24);
			SecuenciaSentencias();
		}
		if (t.kind == 26) {
			Get();
			SecuenciaSentencias();
		}
		Expect(6);
	}

	private static void sentencia1() {
		Expect(1);
		if (StartOf(3)) {
			if (t.kind == 7 || t.kind == 20 || t.kind == 40) {
				selector();
				Expect(20);
				expresion();
			} else {
				if (t.kind == 14) {
					ParametrosActuales();
				}
			}
		}
	}

	private static void sentencia() {
		if (t.kind == 1 || t.kind == 21 || t.kind == 23) {
			if (t.kind == 1) {
				sentencia1();
			} else if (t.kind == 23) {
				SentenciaIf();
			} else {
				SentenciaWhile();
			}
		}
	}

	private static void ListaCampos() {
		String cad; Struct t1=null,t2;
		if (t.kind == 1) {
			ListaIdent();
			Expect(12);
			cad = tipo1();
			tabla.pontipo(cad);
			tabla.insAuxR(); //Inserta los campos
			t2=new Struct(cad); t1=tabla.buscaT(t2);
			tabla.aux=null; //Reseteamos la liata de variables!!
		}
	}

	private static void TipoRecord() {
		Expect(16);
		tabla.lt.form=reg; //Marcamos q es un registro
		ListaCampos();
		while (t.kind == 4) {
			Get();
			ListaCampos();
		}
		tabla.lt.size+=tabla.tamReg(); //Ponemos el tamano q ocupara el registro
		Expect(6);
	}

	private static void TipoArray() {
		int i2; String c1=null;
		Expect(17);
		tabla.lt.form=vector;
		i2 = Entero();
		Expect(18);
		c1 = tipo1();
		Struct s3,s4;
		s3=new Struct(c1); s4=tabla.buscaT(s3);
		tabla.lt.size=i2*s4.size;
	}

	private static void SeccionPF() {
		String s2=null;
		boolean b=true;
		if (t.kind == 11) {
			Get();
			b=false;
		}
		ListaIdent();
		Expect(12);
		s2 = tipo1();
		tabla.pontipo(s2);tabla.bloquea(b);tabla.insAuxR();
		tabla.aux=null; //Lista aux reseteada!!!
	}

	private static void ParametrosFormales() {
		Expect(14);
		if (t.kind == 1 || t.kind == 11) {
			SeccionPF();
			while (t.kind == 4) {
				Get();
				SeccionPF();
			}
		}
		Expect(15);
	}

	private static void CuerpoProcedimiento() {
		declaraciones();
		if (t.kind == 5) {
			Get();
			SecuenciaSentencias();
		}
		Expect(6);
	}

	private static void CabeceraProcedimiento() {
		String np=null; Struct s4;
		Expect(13);
		np = Ident();
		s4=new Struct(np); tabla.addT(s4);
		tabla.EnterScope();
		if (t.kind == 14) {
			ParametrosFormales();
		}
	}

	private static void DeclaracionProcedimiento() {
		CabeceraProcedimiento();
		Expect(4);
		CuerpoProcedimiento();
		Expect(1);
		tabla.LeaveScope();
	}

	private static String tipo1() {
		String id;
		Expect(1);
		id=token.val;
		return id;
	}

	private static void ListaIdent() {
		String n1,n2=null;
		n1 = Ident();
		tabla.add(n1);
		while (t.kind == 19) {
			Get();
			n2 = Ident();
			tabla.add(n2);
		}
	}

	private static void tipo() {
		if (t.kind == 17) {
			TipoArray();
		} else if (t.kind == 16) {
			TipoRecord();
		} else Error(44);
	}

	private static int Entero() {
		int num;
		Expect(2);
		num=0;
		try{
		num = Integer.parseInt(token.val);
		}catch(Exception e){
		System.out.println("No coge el entero");
		}
		//System.out.println("VALOR: "+num);
		return num;
	}

	private static String Ident() {
		String nombre;
		Expect(1);
		nombre=token.val;
		return nombre;
	}

	private static void SecuenciaSentencias() {
		sentencia();
		while (t.kind == 4) {
			Get();
			sentencia();
		}
	}

	private static void declaraciones() {
		String tv,n1,n2=null,n3; //tabla.EnterScope();
		String nc=null; Obj o1; int en;
		if (t.kind == 8) {
			Get();
			while (t.kind == 1) {
				nc = Ident();
				o1=tabla.NewObj(nc,cte);
				o1.block=true; //Pq a una cte no se le puede modificar el valor
				Expect(9);
				en = Entero();
				Struct st2,st=new Struct("INTEGER");
				st2=tabla.buscaT(st); o1.type=st2;
				o1.val=new Integer(en);
				Expect(4);
				//tabla.topScope.locals.view();
			}
		}
		Struct s;
		if (t.kind == 10) {
			Get();
			while (t.kind == 1) {
				n3 = Ident();
				s=new Struct(n3); //Creo tipo nuevo!!
				tabla.addT(s); //Insertado en la lista de tipos!!
				Expect(9);
				tipo();
				Expect(4);
				//tabla.lt.view();
			}
		}
		if (t.kind == 11) {
			Get();
			while (t.kind == 1) {
				ListaIdent();
				Expect(12);
				tv = tipo1();
				tabla.pontipo(tv);tabla.insTablaS();
				Expect(4);
			}
		}
		while (t.kind == 13) {
			DeclaracionProcedimiento();
			Expect(4);
			tabla.lt.view();
		}
	}

	private static void oberon() {
		tabla.Init();  //Iniciamos el compilador!!
		Expect(3);
		Expect(1);
		Expect(4);
		declaraciones();
		if (t.kind == 5) {
			Get();
			SecuenciaSentencias();
		}
		Expect(6);
		Expect(1);
		tabla.verScopes();
		Expect(7);
	}



	static void Parse() {
		t = new Token();
		Get();
		oberon();

	}

	private static boolean[][] set = {
	{T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x},
	{x,T,T,x, x,x,x,x, x,x,x,x, x,x,T,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,T,x,x, x,x,x,T, x,x,x,x},
	{x,x,x,x, x,x,x,x, x,T,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,T, T,T,T,T, x,x,x,x, x,x,x,x, x,x,x,x},
	{x,x,x,x, x,x,x,T, x,x,x,x, x,x,T,x, x,x,x,x, T,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, x,x,x,x, T,x,x,x}

	};
}
