import java.io.*;
import java.util.*;

class Token {
	int kind;    // token kind
	int pos;     // token position in the source text (starting at 0)
	int col;     // token column (starting at 0)
	int line;    // token line (starting at 1)
	String str;  // exact string value
	String val;  // token string value (uppercase if ignoreCase)
}

class Buffer {

// Portability - use the following for Java 1.0
//	static byte[] buf;  // Java 1.0
// Portability - use the following for Java 1.1
//	static char[] buf;  // Java 1.1

	static char[] buf;  // Java 1.1

	static int bufLen;
	static int pos;
	static final int eof = 65535;

	static void Fill(String name) {
		try {
			File f = new File(name); bufLen = (int) f.length();

// Portability - use the following for Java 1.0
//			BufferedInputStream s = new BufferedInputStream(new FileInputStream(f), bufLen);
//			buf = new byte[bufLen];  // Java 1.0
// Portability - use the following for Java 1.1
//			BufferedReader s = new BufferedReader(new FileReader(f), bufLen);
//			buf = new char[bufLen];  // Java 1.1

			BufferedReader s = new BufferedReader(new FileReader(f), bufLen);
			buf = new char[bufLen];  // Java 1.1

			int n = s.read(buf); pos = 0;
		} catch (IOException e) {
			System.out.println("--- cannot open file " + name);
			System.exit(0);
		}
	}

	static void Set(int position) {
		if (position < 0) position = 0; else if (position >= bufLen) position = bufLen;
		pos = position;
	}

	static int read() {
		if (pos < bufLen) return (int) buf[pos++]; else return eof;
	}
}

class Scanner {

	static ErrorStream err;  // error messages

	private static final char EOF = '\0';
	private static final char CR  = '\r';
	private static final char LF  = '\n';
	private static final int noSym = 42;
	private static final int[] start = {
	 23,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,
	  0,  0,  0, 11,  0,  0, 19,  0,  7,  8, 18, 16,  9, 17,  4,  0,
	  2,  2,  2,  2,  2,  2,  2,  2,  2,  2,  6,  3, 12,  5, 14,  0,
	  0,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,
	  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 21,  0, 22,  0,  0,
	  0,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,
	  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  0,  0,  0, 20,  0,
	  0};

	private static Token t;        // current token
	private static char strCh;     // current input character (original)
	private static char ch;        // current input character (for token)
	private static char lastCh;    // last input character
	private static int pos;        // position of current character
	private static int line;       // line number of current character
	private static int lineStart;  // start position of current line
	private static BitSet ignore;  // set of characters to be ignored by the scanner
	private static int offset = 0; // 1 - MsDos, 0 - Unix/Mac

	static void Init (String file, ErrorStream e) {
		ignore = new BitSet(128);
		ignore.set(9); ignore.set(10); ignore.set(13); ignore.set(32); 
		
		err = e;
		Buffer.Fill(file);
		pos = -1; line = 1; lineStart = 0; lastCh = 0;
		NextCh();
	}

	static void Init (String file) {
		Init(file, new ErrorStream());
	}

	private static void NextCh() {
		lastCh = ch;
		strCh = (char) Buffer.read(); pos++;
		ch = strCh;
		if (ch == LF && lastCh == CR) offset = 1; // MS-Dos format
		if ((ch == CR) || (ch == LF) && (lastCh != CR)) {line++; lineStart = pos + 1;}
		if (ch > '\u007f') {
			if (ch == '\uffff') ch = EOF;
			else {
				Scanner.err.SemErr(-1, line, (pos + 1 - lineStart - offset));
				ch = ' ';
			}
		}
	}

	private static boolean Comment0() {
		int level = 1, line0 = line, lineStart0 = lineStart; char startCh;
		NextCh();
		if (ch == '*') {
			NextCh();
			for(;;) {
				if (ch == '*') {
					NextCh();
					if (ch == ')') {
						level--;
						if (level == 0) {NextCh(); return true;}
						NextCh();
					}
				} else if (ch == '(') {
					NextCh();
					if (ch == '*') {
						level++; NextCh();
					}
					} else if (ch == EOF) return false;
					else NextCh();
				}
		} else {
			if (ch == CR || ch == LF) {line--; lineStart = lineStart0;}
			pos = pos - 2; Buffer.Set(pos+1); NextCh();
		}
		return false;
	}


	private static void CheckLiteral(StringBuffer buf) {
		t.val = buf.toString();
		switch (t.val.charAt(0)) {
			case 'A': {
				if (t.val.equals("ARRAY")) t.kind = 17;
				break;}
			case 'B': {
				if (t.val.equals("BEGIN")) t.kind = 5;
				break;}
			case 'C': {
				if (t.val.equals("CONST")) t.kind = 8;
				break;}
			case 'D': {
				if (t.val.equals("DIV")) t.kind = 36;
				else if (t.val.equals("DO")) t.kind = 22;
				break;}
			case 'E': {
				if (t.val.equals("ELSE")) t.kind = 26;
				else if (t.val.equals("ELSIF")) t.kind = 25;
				else if (t.val.equals("END")) t.kind = 6;
				break;}
			case 'I': {
				if (t.val.equals("IF")) t.kind = 23;
				break;}
			case 'M': {
				if (t.val.equals("MOD")) t.kind = 37;
				else if (t.val.equals("MODULE")) t.kind = 3;
				break;}
			case 'O': {
				if (t.val.equals("OF")) t.kind = 18;
				else if (t.val.equals("OR")) t.kind = 34;
				break;}
			case 'P': {
				if (t.val.equals("PROCEDURE")) t.kind = 13;
				break;}
			case 'R': {
				if (t.val.equals("RECORD")) t.kind = 16;
				break;}
			case 'T': {
				if (t.val.equals("THEN")) t.kind = 24;
				else if (t.val.equals("TYPE")) t.kind = 10;
				break;}
			case 'V': {
				if (t.val.equals("VAR")) t.kind = 11;
				break;}
			case 'W': {
				if (t.val.equals("WHILE")) t.kind = 21;
				break;}
		}
	}

	static Token Scan() {
		int state, apx;
		StringBuffer buf;
		while (ignore.get((int)ch)) NextCh();
		if (ch == '(' && Comment0() ) return Scan();
		t = new Token();
		t.pos = pos; t.col = pos - lineStart + 1 - offset; t.line = line;
		buf = new StringBuffer();
		state = start[ch];
		apx = 0;
		loop: for (;;) {
			buf.append(strCh);
			NextCh();
			switch (state) {
				case 0:
					{t.kind = noSym; break loop;} // NextCh already done
				case 1:
					if ((ch >= '0' && ch <= '9'
					  || ch >= 'A' && ch <= 'Z'
					  || ch >= 'a' && ch <= 'z')) {break;}
					else {t.kind = 1; CheckLiteral(buf); break loop;}
				case 2:
					if ((ch >= '0' && ch <= '9')) {break;}
					else {t.kind = 2; break loop;}
				case 3:
					{t.kind = 4; break loop;}
				case 4:
					{t.kind = 7; break loop;}
				case 5:
					{t.kind = 9; break loop;}
				case 6:
					if (ch == '=') {state = 10; break;}
					else {t.kind = 12; break loop;}
				case 7:
					{t.kind = 14; break loop;}
				case 8:
					{t.kind = 15; break loop;}
				case 9:
					{t.kind = 19; break loop;}
				case 10:
					{t.kind = 20; break loop;}
				case 11:
					{t.kind = 27; break loop;}
				case 12:
					if (ch == '=') {state = 13; break;}
					else {t.kind = 28; break loop;}
				case 13:
					{t.kind = 29; break loop;}
				case 14:
					if (ch == '=') {state = 15; break;}
					else {t.kind = 30; break loop;}
				case 15:
					{t.kind = 31; break loop;}
				case 16:
					{t.kind = 32; break loop;}
				case 17:
					{t.kind = 33; break loop;}
				case 18:
					{t.kind = 35; break loop;}
				case 19:
					{t.kind = 38; break loop;}
				case 20:
					{t.kind = 39; break loop;}
				case 21:
					{t.kind = 40; break loop;}
				case 22:
					{t.kind = 41; break loop;}
				case 23:
					{t.kind = 0; break loop;}
			}
		}
		t.str = buf.toString();
		t.val = t.str;
		return t;
	}
}
