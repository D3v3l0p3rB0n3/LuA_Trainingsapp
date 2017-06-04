package Database;

import java.sql.*;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 *
 * Created by fgrabscheit on 03.05.2017.
 */

public class SQLiteDatabase extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "LuADatabase.db";

    public SQLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

        //create Tables
        db.execSQL(SQLQuerries.createTableBenutzer);
        db.execSQL(SQLQuerries.createTableAufgabe);
        db.execSQL(SQLQuerries.createTableRelationenschema);
        db.execSQL(SQLQuerries.createTableAufgabenzustand);
        db.execSQL(SQLQuerries.createTableRelation);
        db.execSQL(SQLQuerries.createTableTermvereinfachung);
        db.execSQL(SQLQuerries.createTableNormalformen);
        db.execSQL(SQLQuerries.createTableWahrheitstabellen);

        Log.d(SQLiteDatabase.class.getSimpleName(),"DB anlegen....");
        //inserts
        SQLQuerries querries = new SQLQuerries();

        for(int x=0; x< querries.inserts.size(); x++ ){
            Log.d(SQLiteDatabase.class.getSimpleName(), querries.inserts.get(x).toString());
            db.execSQL(querries.inserts.get(x));
        }


    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor query (android.database.sqlite.SQLiteDatabase db, String sqlstatement)
    {
        Cursor c = db.rawQuery (sqlstatement, null);
        return c;

    }
}
