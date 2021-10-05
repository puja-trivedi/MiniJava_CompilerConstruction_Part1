import java.util.*;

public class Parse{
	
	// set of terminal symbols 
	private static final String BRACE_L = "{";
	private static final String BRACE_R = "}";
	private static final String SOP = "System.out.println";
	private static final String PAREN_L = "(";
	private static final String PAREN_R = ")";
	private static final String SEMIC = ";";
	private static final String IF = "if";
	private static final String ELSE = "else";
	private static final String WHILE = "while";
	private static final String TRUE = "true";
	private static final String FALSE = "false";
	private static final String NOT = "!"; 
	
	// current token 
	public static String token; 
	
	// list of tokens obtained from input program
	public static List<String> tokens = new ArrayList<String>(); 
	
	// iterator for tokens list 
	//public static Iterator<String> iter = tokens.iterator();
	public static int index = 0; 
	

	public static void main(String args[]) {
		// read in input program from stdin 
		Scanner sc = new Scanner(System.in); 
			String t = "";
		while(sc.hasNext()) {
			t = sc.next();
			tokens.add(t);
		}
		System.out.println(tokens.size());
		nextToken();
		S();
	}
	
	public static void eat(String t) {
		if(token.equals(t)) {
			nextToken();
		} else {
			System.out.println("Parse Error in eat()");
			parseError(); 
		}
	}
	
	public static void nextToken(){
		if(index < tokens.size()) {
			token = tokens.get(index);
			System.out.println(token);
			index++;
		} else {
			System.out.println("Program parsed successfully"); 
			System.exit(0);
		}
	}
	
	public static void parseError() {
		System.out.println("Parse error");
		System.exit(0); 
	}
	
	// nonterminal symbol "S"
	// S is the start symbol
	public static void S(){
		if(token.equals(BRACE_L)) {
			eat(BRACE_L); 
			L();
			eat(BRACE_R);
		} else if(token.equals(SOP)) {
			eat(SOP);
			eat(PAREN_L);
			E();
			eat(PAREN_R);
			eat(SEMIC);
		} else if(token.equals(IF)) {
			eat(IF);
			eat(PAREN_L);
			E();
			eat(PAREN_R); 
			S();
			eat(ELSE);
			S();
		} else if(token.equals(WHILE)) {
			eat(WHILE);
			eat(PAREN_L);
			E();
			eat(PAREN_R);
			S();
		} else {
			System.out.println("Parse Error in S");
			parseError(); 
		}	
	}
	
	// nonterminal symbol "L"
	public static void L(){
		if(token.equals(BRACE_L)) {
			S();
			L();
		} else if(token.equals(SOP)) {
			S();
			L();
		} else if(token.equals(IF)) {
			S();
			L();
		} else if(token.equals(WHILE)) {
			S();
			L();
		} else if(token.equals(BRACE_R)) {
			
		} else {
			System.out.println("Parse Error in L");
			parseError(); 
		}
	}
	
	// nonterminal symbol "E"
	public static void E(){
		if(token.equals(TRUE)) {
			eat(TRUE);
		} else if(token.equals(FALSE)) {
			eat(FALSE);
		} else if(token.equals(NOT)) {
			eat(NOT);
			E();
		} else {
			System.out.println("Parse Error in E");
			parseError();
		}
	}	
}
