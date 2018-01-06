
class ErrorStream {

	int count;  // number of errors detected

	ErrorStream() {
		count = 0;
	}

	void StoreError(int n, int line, int col, String s) {
		System.out.println("-- line " + line + " col " + col + ": " + s);
	}

	void ParsErr(int n, int line, int col) {
		String s;
		count++;
		switch (n) {
			case 0: {s = "EOF expected"; break;}
			case 1: {s = "ident expected"; break;}
			case 2: {s = "entero expected"; break;}
			case 3: {s = "\"MODULE\" expected"; break;}
			case 4: {s = "\";\" expected"; break;}
			case 5: {s = "\"BEGIN\" expected"; break;}
			case 6: {s = "\"END\" expected"; break;}
			case 7: {s = "\".\" expected"; break;}
			case 8: {s = "\"CONST\" expected"; break;}
			case 9: {s = "\"=\" expected"; break;}
			case 10: {s = "\"TYPE\" expected"; break;}
			case 11: {s = "\"VAR\" expected"; break;}
			case 12: {s = "\":\" expected"; break;}
			case 13: {s = "\"PROCEDURE\" expected"; break;}
			case 14: {s = "\"(\" expected"; break;}
			case 15: {s = "\")\" expected"; break;}
			case 16: {s = "\"RECORD\" expected"; break;}
			case 17: {s = "\"ARRAY\" expected"; break;}
			case 18: {s = "\"OF\" expected"; break;}
			case 19: {s = "\",\" expected"; break;}
			case 20: {s = "\":=\" expected"; break;}
			case 21: {s = "\"WHILE\" expected"; break;}
			case 22: {s = "\"DO\" expected"; break;}
			case 23: {s = "\"IF\" expected"; break;}
			case 24: {s = "\"THEN\" expected"; break;}
			case 25: {s = "\"ELSIF\" expected"; break;}
			case 26: {s = "\"ELSE\" expected"; break;}
			case 27: {s = "\"#\" expected"; break;}
			case 28: {s = "\"<\" expected"; break;}
			case 29: {s = "\"<=\" expected"; break;}
			case 30: {s = "\">\" expected"; break;}
			case 31: {s = "\">=\" expected"; break;}
			case 32: {s = "\"+\" expected"; break;}
			case 33: {s = "\"-\" expected"; break;}
			case 34: {s = "\"OR\" expected"; break;}
			case 35: {s = "\"*\" expected"; break;}
			case 36: {s = "\"DIV\" expected"; break;}
			case 37: {s = "\"MOD\" expected"; break;}
			case 38: {s = "\"&\" expected"; break;}
			case 39: {s = "\"~\" expected"; break;}
			case 40: {s = "\"[\" expected"; break;}
			case 41: {s = "\"]\" expected"; break;}
			case 42: {s = "not expected"; break;}
			case 43: {s = "invalid factor"; break;}
			case 44: {s = "invalid tipo"; break;}

			default: s = "Syntax error " + n;
		}
		StoreError(n, line, col, s);
	}

	void SemErr(int n, int line, int col) {
		String s;
		count++;
		switch (n) {
			case -1: {s = "invalid character"; break;}
			// perhaps insert application specific error messages here
			default: {s = "Semantic error " + n; break;}
		}
		StoreError(n, line, col, s);
	}

	void Exception (String s) {
		System.out.println(s); System.exit(0);
	}

	void Summarize (String s) {
		switch (count) {
			case 0 : System.out.println("No errors detected"); break;
			case 1 : System.out.println("1 error detected"); break;
			default: System.out.println(count + " errors detected"); break;
		}
	}

}
