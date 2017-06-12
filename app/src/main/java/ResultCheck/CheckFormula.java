package ResultCheck;

/**
 * Created by Marcel on 12.06.2017.
 */
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
    public String rechenergebnis;


    public CheckFormula (String formel, String umformung)
    {
	/*
	 * Der Konstruktor nimmt zwei Formeln entgegen die mit einander auf Gleichheit verglichen werden.
	 * Die Formel darf aktuell maximal verschiedene Variablen enthalten. Weniger ist auch mï¿½glich.
	 *
	 * Zu beachten ist, dass eine Formel "ab" anstelle von "a*b" zu einem Fehler fï¿½hrt.
	 *
	 * Im Konstruktor werden die von uns verwendeten Zeichen
	   fï¿½r Negation, UND und ODER in die Zeichen die Java verwendet, zunÃ¤chst ersetzt.

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
                return null;
            }
        }
        return ergebnis;
    }

    private String ausdruckBerechnen(boolean a, boolean b, boolean c, boolean d, String Formel1, String Formel2)
    {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        engine.put("a", a);
        engine.put("b", b);
        engine.put("c", c);
        engine.put("d", d);

        try {
            //Wenn eine Formel bereits 0,1, true oder false lautet, mï¿½ssen bzw. kï¿½nnen keine Variablenwerte mehr errechnet werden.
            if(!Formel1.equals("1") | !Formel1.equals("0") | !Formel1.equals("true") | !Formel1.equals("false")){
                Formel1 = engine.eval(Formel1).toString();
            }
            if(!Formel2.equals("1") | !Formel2.equals("0") | !Formel2.equals("true") | !Formel2.equals("false")){
                Formel2 = engine.eval(Formel2).toString();
            }
        } catch (ScriptException e) {
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

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");
        engine.put("a", wertInputA);
        engine.put("b", wertInputB);
        engine.put("c", wertInputC);
        engine.put("d", wertInputD);

        try {
            formelinput = engine.eval(formelinput).toString();
        }
        catch (ScriptException e) {
            return null;
        }
        if (formelinput.equals("0") | formelinput.equals("false")){
            return "0";
        }
        if (formelinput.equals("1") | formelinput.equals("true")){
            return "1";
        }
        return null;
    }

    public String getRechenergebnis (){
        return rechenergebnis;
    }

}