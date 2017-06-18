package ResultCheck;

import android.os.AsyncTask;
import android.util.Log;
import com.eclipsesource.v8.V8;

public class CheckFormula {

	private String formelinput;
	private String umformunginput;
	private boolean wertInputA;
	private boolean wertInputB;
	private boolean wertInputC;
	private boolean wertInputD;
	private String formelvergleichergebnis;
	private String [] wahrheitstabellenErgebnis;
	private Integer anzahl_variablen;


	public CheckFormula (String formel, String umformung,String Anzahl_Variablen) //Konstruktor für Umformungskorrektheit
	{
	/*
	 * Der Konstruktor nimmt zwei Formeln entgegen die mit einander auf Gleichheit verglichen werden.
	 * Die Formel darf aktuell maximal 4 verschiedene Variablen enthalten. Weniger ist auch moeglich.
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
		this.anzahl_variablen = Integer.parseInt(Anzahl_Variablen);

		formelvergleichergebnis = formelVergleich();
		//CheckBool cb = new CheckBool();
		//cb.execute();
	};

	public CheckFormula(String formel, String Anzahl_Variablen){

		//Der überladene Konstruktor fuer das gezielte Errechnen eines boolschen Ausdrucks fuer die Wahrheitstabellen.
		formel = formel.replace('+', '|');
		formel = formel.replace('*', '&');
		formel = formel.replace('¬', '!');
		formel = formel.replace('A', 'a');
		formel = formel.replace('B', 'b');
		formel = formel.replace('C', 'c');
		formel = formel.replace('D', 'd');

		this.formelinput = formel;
		this.anzahl_variablen = Integer.parseInt(Anzahl_Variablen);

		wahrheitstabellenErgebnis = new String [16];
		wahrheitstabellenErgebnis =	calculatewahrheitstabelle(this.anzahl_variablen);
		//CheckWahrheitstabelle t1 = new CheckWahrheitstabelle(); //Async Task
		//t1.execute();

	}

	private String formelVergleich (){

	/* PotenzmengeVariablen enthält ein zweidimensionales Array mit allen
	 * möglichen true, false Kombinationen bei 4 Variablen
	 *
	 * Wenn für jede mögliche Kombination, der boolschen Variablen,
	 * das selbe Ergebnis bei den beiden Bedingungen herauskommt,
	 * ist die Umformung richtig.
	 */
		String ergebnis="";
		PotenzmengeVariablen potenzmenge= new PotenzmengeVariablen(4);

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

	private String [] calculatewahrheitstabelle (Integer Anzahl_Variablen){

		String [] ergebnis = null;
		PotenzmengeVariablen potenzmenge;
		switch (Anzahl_Variablen)
		{


			case 2:
				potenzmenge= new PotenzmengeVariablen(2);
				ergebnis = new String[4];
				for( int i=0; i<4; i++){
					ergebnis [i] =berechneWahrheitstabelle(potenzmenge.potenzmenge[i][0],
							potenzmenge.potenzmenge[i][1],
							formelinput);
				}
				break;


			case 3:
				potenzmenge= new PotenzmengeVariablen(3);
				ergebnis = new String[8];

				for( int i=0; i<8; i++){
					ergebnis [i] =berechneWahrheitstabelle(potenzmenge.potenzmenge[i][0],
							potenzmenge.potenzmenge[i][1],
							potenzmenge.potenzmenge[i][2],
							formelinput);
				}
				break;

			case 4:
				potenzmenge= new PotenzmengeVariablen(3);
				ergebnis = new String[16];

				for( int i=0; i<16; i++){
					ergebnis [i] =berechneWahrheitstabelle(potenzmenge.potenzmenge[i][0],
							potenzmenge.potenzmenge[i][1],
							potenzmenge.potenzmenge[i][2],
							potenzmenge.potenzmenge[i][3],
							formelinput);
				}
				break;

		}
		return ergebnis;
	}
	private String ausdruckBerechnen(boolean a, boolean b, boolean c, boolean d, String Formel1, String Formel2) //Berechnung der Umformung
	{
		try {
			V8 runtime = V8.createV8Runtime();
			runtime.add("a", a);
			runtime.add("b", b);
			runtime.add("c", c);
			runtime.add("d", d);

			//Wenn eine Formel bereits 0,1, true oder false lautet, müssen bzw. können keine Variablenwerte mehr errechnet werden.
			if (!Formel1.equals("1") | !Formel1.equals("0") | !Formel1.equals("true") | !Formel1.equals("false")) {
				Formel1 = runtime.executeScript(Formel1).toString();
			}
			if (!Formel2.equals("1") | !Formel2.equals("0") | !Formel2.equals("true") | !Formel2.equals("false")) {
				Formel2 = runtime.executeScript(Formel2).toString();
			}
			runtime = null;
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

	private String berechneWahrheitstabelle(boolean a, boolean b, String Formel1) //Wahrheitstabelle mit 2 Variablen
	{
		try {
			V8 runtime = V8.createV8Runtime();
			runtime.add("a", a);
			runtime.add("b", b);

			//Wenn eine Formel bereits 0,1, true oder false lautet, müssen bzw. können keine Variablenwerte mehr errechnet werden.
			if (!Formel1.equals("1") | !Formel1.equals("0") | !Formel1.equals("true") | !Formel1.equals("false")) {
				Formel1 = runtime.executeScript(Formel1).toString();
			}
			runtime = null;
		}
		catch (Exception e){
			return "fehler";
		}

		if(Formel1.equals("1")| Formel1.equals("true")){
			return "1";
		}
		if(Formel1.equals("0")| Formel1.equals("false")){
			return "0";
		}
		return "fehler";
	}

	private String berechneWahrheitstabelle(boolean a, boolean b, boolean c, String Formel1) //Wahrheitstabelle mit 3 Variablen
	{
		try {
			V8 runtime = V8.createV8Runtime();
			runtime.add("a", a);
			runtime.add("b", b);
			runtime.add("c", c);

			//Wenn eine Formel bereits 0,1, true oder false lautet, müssen bzw. können keine Variablenwerte mehr errechnet werden.
			if (!Formel1.equals("1") | !Formel1.equals("0") | !Formel1.equals("true") | !Formel1.equals("false")) {
				Formel1 = runtime.executeScript(Formel1).toString();
			}
			runtime = null;
		}
		catch (Exception e){
			return "fehler";
		}

		if(Formel1.equals("1")| Formel1.equals("true")){
			return "1";
		}
		if(Formel1.equals("0")| Formel1.equals("false")){
			return "0";
		}
		return "fehler";
	}
	private String berechneWahrheitstabelle(boolean a, boolean b, boolean c, boolean d, String Formel1) //Wahrheitstabelle mit 4 Variablen
	{
		try {
			V8 runtime = V8.createV8Runtime();
			runtime.add("a", a);
			runtime.add("b", b);
			runtime.add("c", c);
			runtime.add("d", d);

			//Wenn eine Formel bereits 0,1, true oder false lautet, müssen bzw. können keine Variablenwerte mehr errechnet werden.
			if (!Formel1.equals("1") | !Formel1.equals("0") | !Formel1.equals("true") | !Formel1.equals("false")) {
				Formel1 = runtime.executeScript(Formel1).toString();
			}
			runtime = null;
		}
		catch (Exception e){
			return "fehler";
		}

		if(Formel1.equals("1")| Formel1.equals("true")){
			return "1";
		}
		if(Formel1.equals("0")| Formel1.equals("false")){
			return "0";
		}
		return "fehler";
	}

	public String getformelvergleichergebnis (){

		return formelvergleichergebnis;
	}
	public String [] getwahrheitstabellenErgebnis (){

		return wahrheitstabellenErgebnis;
	}

	// Test Marcel: eigener Thread => geht nicht !!
	public class CheckWahrheitstabelle extends AsyncTask<String, Integer, String[]>
	{
		@Override
		protected String[] doInBackground(String... params) {
			Log.d(CheckFormula.class.getSimpleName(), "Bin nun im Asychronen Task");
			wahrheitstabellenErgebnis = calculatewahrheitstabelle(anzahl_variablen);
			return  wahrheitstabellenErgebnis;
		}

		@Override
		protected void onPostExecute(String[] strings) {
			Log.d(CheckFormula.class.getSimpleName(), "Hintergrundrechnung beendet");
		}
	}



	public class CheckBool extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... params) {
			Log.d(CheckFormula.class.getSimpleName(), "Bin nun im Asychronen Task");
			formelvergleichergebnis = formelVergleich();
			publishProgress(100);

			return formelvergleichergebnis;
		}

		@Override
		protected void onPostExecute(String s) {
			Log.d(CheckFormula.class.getSimpleName(), "Hintergrundberechnung beendet!");

		}

		@Override
		protected void onProgressUpdate(Integer... values) {

		}
	}

}
