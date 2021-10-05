import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Parse{
	// set of terminal symbols given by the grammar
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
	
	// keeps track of the current token of the input program
	public static String token; 
	
	// list of tokens obtained from input program
	public static LinkedList<String> tokens = new LinkedList<String>(); 
	
	// list of patterns used to match against the input program to 
	// create tokens
	public static List<Pattern> regex = new ArrayList<Pattern>(); 
	
	public static void main(String args[]) {
		// add regular expression patterns based off the grammar to regex
		regex.add(Pattern.compile("^(\\" + BRACE_L + ")"));
		regex.add(Pattern.compile("^(\\" + BRACE_R + ")"));
		regex.add(Pattern.compile("^(" + SOP + ")"));
		regex.add(Pattern.compile("^(\\" + PAREN_L + ")"));
		regex.add(Pattern.compile("^(\\" + PAREN_R + ")"));
		regex.add(Pattern.compile("^(" + SEMIC + ")"));
		regex.add(Pattern.compile("^(" + IF + ")"));
		regex.add(Pattern.compile("^(" + ELSE + ")"));
		regex.add(Pattern.compile("^(" + WHILE + ")"));
		regex.add(Pattern.compile("^(" + TRUE + ")"));
		regex.add(Pattern.compile("^(" + FALSE + ")"));
		regex.add(Pattern.compile("^(" + NOT + ")"));
		
		// read in input program from stdin 
		Scanner sc = new Scanner(System.in); 
		while(sc.hasNextLine()) {
			findTokens(sc.nextLine().trim());
		}
		
		// initialize "token" variable with first token from input
		nextToken();
		
		// begin at the start symbol
		S();
		
		// if the parsing is complete but there are remaining tokens 
		// call parseError()
		if(tokens.size() == 0) {
			System.out.println("Program parsed successfully"); 
		} else {
			parseError();
		}
	}
	
	// using the terminal symbols from the grammar to tokenize the input 
	// program
	public static void findTokens(String line){
		while(!line.equals("")) {
			boolean foundMatch = false;
			for(Pattern pat : regex) {
				Matcher match = pat.matcher(line);
				if(match.find()) {
					foundMatch = true;
					String token = match.group().trim();
					line = match.replaceFirst("").trim();
					tokens.add(token);
					break;
				}
			}
			if(!foundMatch){
				parseError();
			}
		}
	}
	
	// compares current token with the grammar's expected token 
	public static void eat(String t) {
		if(token.equals(t)) {
			nextToken();
		} else {
			parseError(); 
		}
	}
	
	// updates "token" to the next token in the input 
	public static void nextToken(){
		if(tokens.size() > 0) {
			token = tokens.remove();
		} else {
			token = "";
		}
	}
	
	// prints "parse error" to stdout and exits program 
	public static void parseError() {
		System.out.println("Parse error");
		System.exit(0); 
	}
	
	// nonterminal symbol "S"
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
			//System.out.println("Parse Error in S");
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
			// do nothing : M[L,BRACE_R] = emptystring
		} else {
			//System.out.println("Parse Error in L");
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
			//System.out.println("Parse Error in E");
			parseError();
		}
	}	
}
