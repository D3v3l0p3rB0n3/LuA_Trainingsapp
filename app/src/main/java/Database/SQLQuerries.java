package Database;

import java.util.ArrayList;

/**
 * Created by fgrabscheit on 03.05.2017.
 */

public class SQLQuerries {

    protected static final String createTableBenutzer =    "CREATE TABLE Benutzer (" +
                                                        "Name TEXT PRIMARY KEY NOT NULL)";

    protected static final String createTableAufgabe =     "CREATE TABLE Aufgabe (" +
                                                        "ID TEXT PRIMARY KEY NOT NULL, " +
                                                        "Schwierigkeitsgrad TEXT NOT NULL, " +
                                                        "Aufgabenstellung TEXT NOT NULL, " +
                                                        "Hilfe TEXT, " +
                                                        "Benutzername TEXT NOT NULL, " +
                                                        "FOREIGN KEY(Benutzername) REFERENCES Benutzer(Name)" +
                                                        ")";

    protected static final String createTableAufgabenzustand =     "CREATE TABLE Aufgabenzustand (" +
                                                                "Status TEXT NOT NULL, " +
                                                                "Anzahl_der_Bearbeitungen TEXT NOT NULL, " +
                                                                "Benutzername TEXT NOT NULL, " +
                                                                "FOREIGN KEY(Benutzername) REFERENCES Benutzer(Name)" +
                                                                ")";

    protected static final String createTableRelationenschema =        "CREATE TABLE Relationenschema (" +
                                                                    "ID TEXT PRIMARY KEY NOT NULL, " +
                                                                    "Relationennummer TEXT NOT NULL, " +
                                                                    "Aufgabenbeschreibung TEXT, " +
                                                                    "Lösung TEXT NOT NULL, " +
                                                                    "FOREIGN KEY(ID) REFERENCES Aufgabe(ID)" +
                                                                    ")";

    protected static final String createTableRelation =        "CREATE TABLE Relation (" +
                                                            "Relation TEXT PRIMARY KEY NOT NULL, " +
                                                            "Relationennummer TEXT NOT NULL, " +
                                                            "FOREIGN KEY(Relationennummer) REFERENCES Relationenschema(Relationennummer)" +
                                                            ")";

    protected static final String createTableTermvereinfachung =       "CREATE TABLE Termvereinfachung (" +
                                                                    "ID TEXT PRIMARY KEY NOT NULL, " +
                                                                    "Term TEXT NOT NULL, " +
                                                                    "Anzahl_Argumente_der_Lösung TEXT NOT NULL, " +
                                                                    "Lösung TEXT NOT NULL, " +
                                                                    "FOREIGN KEY(ID) REFERENCES Aufgabe(ID)" +
                                                                    ")";

    protected static final String createTableNormalformen =        "CREATE TABLE Normalformen (" +
                                                                "ID TEXT PRIMARY KEY NOT NULL, " +
                                                                "Art_der_Normalform TEXT NOT NULL, " +
                                                                "Term TEXT NOT NULL, " +
                                                                "Anzahl_Argumente_der_Lösung TEXT NOT NULL, " +
                                                                "Lösung TEXT NOT NULL, " +
                                                                "FOREIGN KEY(ID) REFERENCES Aufgabe(ID)" +
                                                                ")";

    protected static final String createTableWahrheitstabellen =       "CREATE TABLE Wahrheitstabellen (" +
                                                                    "ID TEXT PRIMARY KEY NOT NULL, " +
                                                                    "Anzahl_Argumente TEXT NOT NULL, " +
                                                                    "Term TEXT NOT NULL, " +
                                                                    "FOREIGN KEY(ID) REFERENCES Aufgabe(ID)" +
                                                                    ")";

    ArrayList<String> inserts = new ArrayList<String>();

    public SQLQuerries (){
        inserts.add("INSERT INTO Relationenschema VALUES ('1','1','Geben Sie die Titel der Bücher an, die im Jahr 2010 erschienen sind und mehr als 300 Seiten haben.','')");
        inserts.add("INSERT INTO Relationenschema VALUES ('2','1','Geben Sie die Verlagsnamen an, die in München ihren Sitz haben und ein Buch von „Müller“ veröffentlicht haben.','')");
        inserts.add("INSERT INTO Relationenschema VALUES ('3','1','Geben Sie den Titel und das Veröffentlichungsjahr aller Bücher an, die zwischen 2005 und 2006 ausgeliehen wurden.','')");
        inserts.add("INSERT INTO Relationenschema VALUES ('4','1','Geben Sie alle Namen aller Personen an, die aus Heilbronn oder Augsburg kommen und das Buch „Faust“ im Jahr 2010 ausgeliehen hatten.','')");
        inserts.add("INSERT INTO Relationenschema VALUES ('5','1','Geben Sie die Titel der Bücher an, die in Ravensburg veröffentlicht und von „Mayer“ nach 2006 ausgeliehen wurden.','')");
        inserts.add("INSERT INTO Relationenschema VALUES ('6','1','Geben Sie den Titel aller Bücher an, die noch nie ausgeliehen wurden.','')");
        inserts.add("INSERT INTO Relationenschema VALUES ('7','1','Geben Sie alle Titel von Büchern an, die von minderjährigen Studenten ausgeliehen wurden.','')");
        inserts.add("INSERT INTO Relation VALUES ('Buch(Signatur, ISBN, Titel, Autor, Jahr, VerlagID, AnzahlSeiten)','1')");
        inserts.add("INSERT INTO Relation VALUES ('Verlag(VerlagID, VerlagName, Verlagort)','1')");
        inserts.add("INSERT INTO Relation VALUES ('Ausleiher(ID, Name, Geburtsdatum, Ort)','1')");
        inserts.add("INSERT INTO Relation VALUES ('Ausgeliehen(Signatur, ID, VonDatum, BisDatum)','1')");
        inserts.add("INSERT INTO Termvereinfachung VALUES ('30', '( a + ¬(b*a)) * (c+(d+c))', '2', 'c+d')");
        inserts.add("INSERT INTO Termvereinfachung VALUES ('31', '¬a + ¬(a + ¬ab) + ¬(¬a + b)', '2', '¬a + ¬b')");


    }



}
