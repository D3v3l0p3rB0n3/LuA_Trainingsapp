package ResultCheck;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8ScriptExecutionException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class CheckFormula {
	
	private String formelinput;
	private String umformunginput;
	private boolean wertInputA;
	private boolean wertInputB;
	private boolean wertInputC;
	private boolean wertInputD;
	private String rechenergebnis;
	
	
	public CheckFormula (String formel, String umformung)
	{
	/*
	 * Der Konstruktor nimmt zwei Formeln entgegen die mit einander auf Gleichheit verglichen werden.
	 * Die Formel darf aktuell maximal verschiedene Variablen enthalten. Weniger ist auch moeglich.
	 * 
	 * Zu beachten ist, dass eine Formel "ab" anstelle von "a*b" zu einem Fehler fuehrt.
	 * 
	 * Rechenergebnis(String) wird befüllt: false --> Die Umformung wurde falsch eingegeben.
	 * 										true --> Die Umformung wurde richtig eingegeben.
	 * 										null --> Die Umformung konnte nicht geprüft werden.
	   
	 */

	formel = formel.replace('+', '|');
	formel = formel.replace('*', '&');
	formel = formel.replace('¬', '!');
	formel = formel.replace('A', 'a');
	formel = formel.replace('B', 'b');
	formel = formel.replace('C', 'c');
	formel = formel.replace('D', 'd');
	umformung = umformung.replace('+', '|');
	umformung = umformung.replace('*', '&');
	umformung = umformung.replace('¬', '!');
	umformung = umformung.replace('A', 'a');
	umformung = umformung.replace('B', 'b');
	umformung = umformung.replace('C', 'c');
	umformung = umformung.replace('D', 'd');
    this.formelinput = formel;
    this.umformunginput= umformung;
    
    rechenergebnis = formelVergleich();
	};
	
	public CheckFormula(boolean a, boolean b, boolean c, boolean d, String formel){
		
	//Der ï¿½berladene Konstruktor fï¿½r das gezielte Errechnen eines boolschen Ausdrucks.
	formel = formel.replace('+', '|');
	formel = formel.replace('*', '&');
	formel = formel.replace('¬', '!');
	
	this.formelinput = formel;
	this.wertInputA = a;
	this.wertInputB = b;
	this.wertInputC = c;
	this.wertInputD = d;
	
	rechenergebnis = ausdruckBerechnen();
	
	}
	
	private String formelVergleich (){
	
	/* PotenzmengeVariablen enthï¿½lt ein zweidimensionales Array mit allen
	 * mï¿½glichen true, false Kombinationen bei 4 Variablen
	 * 
	 * Wenn fï¿½r jede mï¿½gliche Kombination, der boolschen Variablen, 
	 * das selbe Ergebnis bei den beiden Bedingungen herauskommt,
	 * ist die Umformung richtig.
	 */
		
	String ergebnis="";																	
	PotenzmengeVariablen potenzmenge= new PotenzmengeVariablen();		
																		
	for( int i=0; i<16; i++){
		ergebnis =ausdruckBerechnen(potenzmenge.potenzmenge[i][0], 
							   potenzmenge.potenzmenge[i][1],
							   potenzmenge.potenzmenge[i][2],
							   potenzmenge.potenzmenge[i][3],
							   formelinput,
							   umformunginput);
	if (ergebnis.equals("false")){
		return ergebnis;
	}
	
	if (ergebnis.equals("fehler"))
	{
		return "fehler";
	}
	}
	return ergebnis;
	}
	
	private String ausdruckBerechnen(boolean a, boolean b, boolean c, boolean d, String Formel1, String Formel2)
	{
		try {
			V8 runtime = V8.createV8Runtime();
			runtime.add("a", a);
			runtime.add("b", b);
			runtime.add("c", c);
			runtime.add("d", d);

			//Wenn eine Formel bereits 0,1, true oder false lautet, mï¿½ssen bzw. kï¿½nnen keine Variablenwerte mehr errechnet werden.
			if (!Formel1.equals("1") | !Formel1.equals("0") | !Formel1.equals("true") | !Formel1.equals("false")) {
				Formel1 = runtime.executeScript(Formel1).toString();
			}
			if (!Formel2.equals("1") | !Formel2.equals("0") | !Formel2.equals("true") | !Formel2.equals("false")) {
				Formel2 = runtime.executeScript(Formel2).toString();
			}
		}
		catch (Exception e){
			return "fehler";
		}

		if(Formel1.equals("1")| Formel1.equals("true")){
			if(Formel2.equals("1")| Formel2.equals("true")){	
			return "true";
			}
		}
		if(Formel1.equals("0")| Formel1.equals("false")){
			if(Formel2.equals("0")| Formel2.equals("false")){	
			return "true";
			}
		}
		return "false";
	}
	
	private String ausdruckBerechnen (){

		try {
			V8 runtime = V8.createV8Runtime();
			runtime.add("a", wertInputA);
			runtime.add("b", wertInputB);
			runtime.add("c", wertInputC);
			runtime.add("d", wertInputD);
	    	formelinput = runtime.executeScript(formelinput).toString();
	    }
	    catch (Exception e) {
			return "fehler";
		}
	    if (formelinput.equals("0") | formelinput.equals("false")){
	    	return "0";
	    }
	    if (formelinput.equals("1") | formelinput.equals("true")){
	    	return "1";
	    }
	    return "fehler";
	}
	
	public String getRechenergebnis (){
		return rechenergebnis;
	}
	
}
