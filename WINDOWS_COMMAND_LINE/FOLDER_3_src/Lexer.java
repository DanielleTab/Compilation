/* The following code was generated by JFlex 1.4.3 on 12/19/16 2:28 PM */

/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/***************************/
/* AUTHOR: OREN ISH SHALOM */
/***************************/

/*************/
/* USER CODE */
/*************/
   
import java_cup.runtime.*; 

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/
      

class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int QUOTE = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\60\1\42\1\24"+
    "\2\61\1\45\1\36\1\61\1\27\1\30\1\23\1\25\1\32\1\26"+
    "\1\33\1\22\1\4\11\5\1\61\1\52\1\44\1\31\1\57\2\61"+
    "\32\20\1\37\1\62\1\47\1\61\1\21\1\61\1\10\1\17\1\6"+
    "\1\16\1\12\1\35\1\53\1\56\1\34\2\17\1\7\1\17\1\15"+
    "\1\55\2\17\1\51\1\11\1\14\1\46\1\54\1\41\1\13\2\17"+
    "\1\40\1\43\1\50\1\61\uff81\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\2\2\2\3\5\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\4\1\1\1\20\1\21\1\4\1\22\1\1\1\23"+
    "\1\24\1\25\1\26\1\4\1\27\1\4\1\30\1\31"+
    "\1\32\1\33\5\4\1\34\1\0\1\35\1\4\1\36"+
    "\1\37\1\4\1\40\1\41\1\42\2\4\1\43\1\44"+
    "\1\45\1\46\1\47\3\4\1\50\1\4\2\34\2\0"+
    "\1\51\6\4\1\52\1\0\1\34\2\4\1\53\1\54"+
    "\2\4\1\55\1\4\1\56\1\4\1\57\1\60";

  private static int [] zzUnpackAction() {
    int [] result = new int[94];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\63\0\146\0\231\0\146\0\146\0\314\0\377"+
    "\0\u0132\0\u0165\0\u0198\0\u01cb\0\u01fe\0\u0231\0\146\0\146"+
    "\0\146\0\146\0\146\0\146\0\u0264\0\146\0\146\0\u0297"+
    "\0\u02ca\0\146\0\146\0\u02fd\0\u0330\0\u0363\0\u0396\0\146"+
    "\0\146\0\146\0\u03c9\0\146\0\u03fc\0\u042f\0\146\0\146"+
    "\0\u0462\0\u0495\0\u04c8\0\u04fb\0\u052e\0\u0561\0\u0594\0\u05c7"+
    "\0\146\0\u05fa\0\u0132\0\146\0\u062d\0\146\0\146\0\146"+
    "\0\u0660\0\u0693\0\146\0\146\0\146\0\146\0\146\0\u06c6"+
    "\0\u06f9\0\u072c\0\u0132\0\u075f\0\u0792\0\146\0\u07c5\0\u07f8"+
    "\0\u0132\0\u082b\0\u085e\0\u0891\0\u08c4\0\u08f7\0\u092a\0\u0132"+
    "\0\u095d\0\u07c5\0\u0990\0\u09c3\0\u0132\0\u0132\0\u09f6\0\u0a29"+
    "\0\u0132\0\u0a5c\0\u0132\0\u0a8f\0\u0132\0\u0132";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[94];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\2\5\1\6\1\7\1\10\2\11\1\12"+
    "\1\13\2\11\1\14\2\11\1\15\1\3\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\11\1\31\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\1\11\1\41\1\42\1\43\1\44\1\11"+
    "\1\45\2\11\1\46\1\5\4\3\1\0\1\3\20\47"+
    "\1\50\32\47\1\46\2\47\1\51\65\0\1\5\64\0"+
    "\2\7\61\0\3\11\1\52\12\11\12\0\2\11\3\0"+
    "\1\11\4\0\1\11\2\0\1\11\1\0\4\11\10\0"+
    "\16\11\12\0\2\11\3\0\1\11\4\0\1\11\2\0"+
    "\1\11\1\0\4\11\10\0\10\11\1\53\5\11\12\0"+
    "\2\11\3\0\1\11\4\0\1\11\2\0\1\11\1\0"+
    "\4\11\10\0\7\11\1\54\6\11\12\0\2\11\3\0"+
    "\1\11\4\0\1\11\2\0\1\11\1\0\4\11\10\0"+
    "\6\11\1\55\7\11\12\0\2\11\3\0\1\11\4\0"+
    "\1\56\2\0\1\11\1\0\4\11\10\0\16\15\12\0"+
    "\2\15\3\0\1\15\4\0\1\15\2\0\1\15\1\0"+
    "\4\15\26\0\1\57\1\60\70\0\1\61\35\0\11\11"+
    "\1\62\4\11\12\0\1\11\1\63\3\0\1\11\4\0"+
    "\1\11\2\0\1\11\1\0\4\11\42\0\1\64\30\0"+
    "\16\11\12\0\2\11\3\0\1\11\4\0\1\11\2\0"+
    "\1\11\1\0\3\11\1\65\35\0\1\66\74\0\1\67"+
    "\50\0\1\70\35\0\6\11\1\71\7\11\12\0\2\11"+
    "\3\0\1\11\4\0\1\11\2\0\1\11\1\0\4\11"+
    "\10\0\16\11\12\0\2\11\3\0\1\11\4\0\1\11"+
    "\2\0\1\11\1\0\2\11\1\72\1\11\35\0\1\73"+
    "\45\0\1\74\1\75\6\0\1\76\24\0\1\77\15\0"+
    "\4\11\1\100\11\11\12\0\2\11\3\0\1\11\4\0"+
    "\1\11\2\0\1\11\1\0\4\11\10\0\16\11\12\0"+
    "\2\11\3\0\1\11\4\0\1\11\2\0\1\101\1\0"+
    "\4\11\10\0\10\11\1\102\5\11\12\0\2\11\3\0"+
    "\1\11\4\0\1\11\2\0\1\11\1\0\4\11\10\0"+
    "\16\11\12\0\2\11\3\0\1\103\4\0\1\11\2\0"+
    "\1\11\1\0\4\11\10\0\3\11\1\104\12\11\12\0"+
    "\2\11\3\0\1\11\4\0\1\11\2\0\1\11\1\0"+
    "\4\11\4\0\1\57\1\105\1\106\60\57\23\107\1\110"+
    "\37\107\4\0\10\11\1\111\5\11\12\0\2\11\3\0"+
    "\1\11\4\0\1\11\2\0\1\11\1\0\4\11\10\0"+
    "\16\11\12\0\1\112\1\11\3\0\1\11\4\0\1\11"+
    "\2\0\1\11\1\0\4\11\10\0\10\11\1\113\5\11"+
    "\12\0\2\11\3\0\1\11\4\0\1\11\2\0\1\11"+
    "\1\0\4\11\10\0\16\11\12\0\1\114\1\11\3\0"+
    "\1\11\4\0\1\11\2\0\1\11\1\0\4\11\10\0"+
    "\5\11\1\115\10\11\12\0\2\11\3\0\1\11\4\0"+
    "\1\11\2\0\1\11\1\0\4\11\10\0\16\11\12\0"+
    "\1\116\1\11\3\0\1\11\4\0\1\11\2\0\1\11"+
    "\1\0\4\11\10\0\6\11\1\117\7\11\12\0\2\11"+
    "\3\0\1\11\4\0\1\11\2\0\1\11\1\0\4\11"+
    "\10\0\3\11\1\120\12\11\12\0\2\11\3\0\1\11"+
    "\4\0\1\11\2\0\1\11\1\0\4\11\6\0\1\106"+
    "\60\0\23\107\1\121\61\107\1\122\1\121\37\107\4\0"+
    "\3\11\1\123\12\11\12\0\2\11\3\0\1\11\4\0"+
    "\1\11\2\0\1\11\1\0\4\11\10\0\16\11\12\0"+
    "\2\11\3\0\1\11\4\0\1\124\2\0\1\11\1\0"+
    "\4\11\10\0\12\11\1\125\3\11\12\0\2\11\3\0"+
    "\1\11\4\0\1\11\2\0\1\11\1\0\4\11\10\0"+
    "\5\11\1\126\10\11\12\0\2\11\3\0\1\11\4\0"+
    "\1\11\2\0\1\11\1\0\4\11\10\0\11\11\1\127"+
    "\4\11\12\0\2\11\3\0\1\11\4\0\1\11\2\0"+
    "\1\11\1\0\4\11\10\0\11\11\1\130\4\11\12\0"+
    "\2\11\3\0\1\11\4\0\1\11\2\0\1\11\1\0"+
    "\4\11\4\0\22\107\1\106\1\121\37\107\4\0\6\11"+
    "\1\131\7\11\12\0\2\11\3\0\1\11\4\0\1\11"+
    "\2\0\1\11\1\0\4\11\10\0\16\11\12\0\2\11"+
    "\3\0\1\11\4\0\1\11\2\0\1\132\1\0\4\11"+
    "\10\0\16\11\12\0\2\11\3\0\1\11\4\0\1\11"+
    "\2\0\1\11\1\0\1\133\3\11\10\0\12\11\1\134"+
    "\3\11\12\0\2\11\3\0\1\11\4\0\1\11\2\0"+
    "\1\11\1\0\4\11\10\0\11\11\1\135\4\11\12\0"+
    "\2\11\3\0\1\11\4\0\1\11\2\0\1\11\1\0"+
    "\4\11\10\0\5\11\1\136\10\11\12\0\2\11\3\0"+
    "\1\11\4\0\1\11\2\0\1\11\1\0\4\11\4\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2754];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\2\11\10\1\6\11\1\1\2\11"+
    "\2\1\2\11\4\1\3\11\1\1\1\11\2\1\2\11"+
    "\7\1\1\0\1\11\2\1\1\11\1\1\3\11\2\1"+
    "\5\11\6\1\1\11\2\0\10\1\1\0\15\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[94];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    /*********************************************************************************/
    /* Create a new java_cup.runtime.Symbol with information about the current token */
    /*********************************************************************************/
    StringBuffer string = new StringBuffer();
    
    private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
    private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 130) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 48: 
          { return symbol(CUP_FILESym.EXTENDS);
          }
        case 49: break;
        case 33: 
          { return symbol(CUP_FILESym.LOR);
          }
        case 50: break;
        case 44: 
          { return symbol(CUP_FILESym.CLASS);
          }
        case 51: break;
        case 12: 
          { return symbol(CUP_FILESym.RPAREN);
          }
        case 52: break;
        case 18: 
          { return symbol(CUP_FILESym.LNEG);
          }
        case 53: break;
        case 4: 
          { return symbol(CUP_FILESym.ID, new String(yytext()));
          }
        case 54: break;
        case 36: 
          { string.append('\t');
          }
        case 55: break;
        case 41: 
          { return symbol(CUP_FILESym.INT);
          }
        case 56: break;
        case 17: 
          { return symbol(CUP_FILESym.LBRACE);
          }
        case 57: break;
        case 2: 
          { /* just skip what was found, do nothing */
          }
        case 58: break;
        case 13: 
          { return symbol(CUP_FILESym.ASSIGN);
          }
        case 59: break;
        case 27: 
          { string.append('\\');
          }
        case 60: break;
        case 8: 
          { string.setLength(0); yybegin(QUOTE);
          }
        case 61: break;
        case 26: 
          { yybegin(YYINITIAL); 
                                       return symbol(CUP_FILESym.QUOTE, 
                                       string.toString());
          }
        case 62: break;
        case 39: 
          { string.append('\r');
          }
        case 63: break;
        case 47: 
          { return symbol(CUP_FILESym.RETURN);
          }
        case 64: break;
        case 1: 
          { throw new java.io.IOException();
          }
        case 65: break;
        case 5: 
          { return symbol(CUP_FILESym.CLASS_ID, new String(yytext()));
          }
        case 66: break;
        case 30: 
          { return symbol(CUP_FILESym.IF);
          }
        case 67: break;
        case 20: 
          { return symbol(CUP_FILESym.MOD);
          }
        case 68: break;
        case 42: 
          { return symbol(CUP_FILESym.NULL);
          }
        case 69: break;
        case 38: 
          { string.append('\"');
          }
        case 70: break;
        case 7: 
          { return symbol(CUP_FILESym.TIMES);
          }
        case 71: break;
        case 16: 
          { return symbol(CUP_FILESym.LBRACK);
          }
        case 72: break;
        case 45: 
          { return symbol(CUP_FILESym.WHILE);
          }
        case 73: break;
        case 11: 
          { return symbol(CUP_FILESym.LPAREN);
          }
        case 74: break;
        case 29: 
          { return symbol(CUP_FILESym.EQUAL);
          }
        case 75: break;
        case 34: 
          { return symbol(CUP_FILESym.LTE);
          }
        case 76: break;
        case 22: 
          { return symbol(CUP_FILESym.RBRACE);
          }
        case 77: break;
        case 31: 
          { return symbol(CUP_FILESym.LAND);
          }
        case 78: break;
        case 23: 
          { return symbol(CUP_FILESym.SEMICOLON);
          }
        case 79: break;
        case 28: 
          { /* just skip */
          }
        case 80: break;
        case 37: 
          { string.append('\n');
          }
        case 81: break;
        case 32: 
          { return symbol(CUP_FILESym.NEQUAL);
          }
        case 82: break;
        case 10: 
          { return symbol(CUP_FILESym.MINUS);
          }
        case 83: break;
        case 40: 
          { return symbol(CUP_FILESym.NEW);
          }
        case 84: break;
        case 9: 
          { return symbol(CUP_FILESym.PLUS);
          }
        case 85: break;
        case 6: 
          { return symbol(CUP_FILESym.DIVIDE);
          }
        case 86: break;
        case 46: 
          { return symbol(CUP_FILESym.STRING);
          }
        case 87: break;
        case 14: 
          { return symbol(CUP_FILESym.COMMA);
          }
        case 88: break;
        case 24: 
          { return symbol(CUP_FILESym.GT);
          }
        case 89: break;
        case 43: 
          { return symbol(CUP_FILESym.VOID);
          }
        case 90: break;
        case 35: 
          { return symbol(CUP_FILESym.GTE);
          }
        case 91: break;
        case 21: 
          { return symbol(CUP_FILESym.RBRACK);
          }
        case 92: break;
        case 25: 
          { string.append( yytext() );
          }
        case 93: break;
        case 19: 
          { return symbol(CUP_FILESym.LT);
          }
        case 94: break;
        case 15: 
          { return symbol(CUP_FILESym.DOT);
          }
        case 95: break;
        case 3: 
          { return symbol(CUP_FILESym.INTEGER, new Integer(yytext()));
          }
        case 96: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { 				System.out.printf("%d: EOF", yyline + 2);
				return symbol(CUP_FILESym.EOF);
		
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
