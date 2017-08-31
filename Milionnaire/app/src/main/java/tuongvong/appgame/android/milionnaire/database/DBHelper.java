package tuongvong.appgame.android.milionnaire.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import tuongvong.appgame.android.milionnaire.model.Question;

/**
 * Created by admin on 12/27/2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    private Context mycontext;
    public static final String TABLE_NAME="Question";
    public static final String TABLE_ID="_id";
    public static final String TABLE_QUESTION="Question";
    public static final String TABLE_CASE_A="CaseA";
    public static final String TABLE_CASE_B="CaseB";
    public static final String TABLE_CASE_C="CaseC";
    public static final String TABLE_CASE_D="CaseD";
    public static final String TABLE_TRUE_CASE="TrueCase";

    //private String DB_PATH = mycontext.getApplicationContext().getPackageName()+"/databases/";
    private static String DB_NAME = "Question.sqlite";//the extension may be .sqlite or .db
    public SQLiteDatabase myDataBase;
   // public static final String DB_PATH= Environment.getDataDirectory()+
         //   "/data/tuongvong.appgame.android.milionnaire/databases/";
    private String DB_PATH = "/data/data/tuongvong.appgame.android.milionnaire/databases/";

    public DBHelper(Context context) throws IOException {
        super(context,DB_NAME,null,1);
        this.mycontext=context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            //System.out.println("Database exists");
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        myDataBase = null;
        if(dbexist) {
            System.out.println(" Database exists.");
        } else {
           myDataBase = this.getReadableDatabase();
            myDataBase.close();
            try {
                copydatabase();
            } catch(IOException e) {
                throw new Error("Error copying database: " + e);
            }
        }
    }

    private boolean checkdatabase() {
        //SQLiteDatabase checkdb = null;
        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            //checkdb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
            checkdb = dbfile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream("/data/data/tuongvong.appgame.android.milionnaire/databases/Question.sqlite");

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer))>0) {
            myoutput.write(buffer,0,length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {
        //Open the database
        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public ArrayList<Question> getData(){
        opendatabase();
        ArrayList<Question> arrQuestions=new ArrayList<>();
        for (int i=1;i<16;i++) {
            String table = TABLE_NAME + i+"";
            String sql="SELECT * FROM "+table+" ORDER BY random() limit 1";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor= db.rawQuery(sql,null);
            int indexId= cursor.getColumnIndex(TABLE_ID);
            int indexQuestion= cursor.getColumnIndex(TABLE_QUESTION);
            int indexCaseA=cursor.getColumnIndex(TABLE_CASE_A);
            int indexCaseB=cursor.getColumnIndex(TABLE_CASE_B);
            int indexCaseC=cursor.getColumnIndex(TABLE_CASE_C);
            int indexCaseD=cursor.getColumnIndex(TABLE_CASE_D);
            int indexTrueCase=cursor.getColumnIndex(TABLE_TRUE_CASE);
            cursor.moveToFirst();
            int id=cursor.getInt(indexId);
            String question= cursor.getString(indexQuestion);
            String caseA= cursor.getString(indexCaseA);
            String caseB= cursor.getString(indexCaseB);
            String caseC= cursor.getString(indexCaseC);
            String caseD= cursor.getString(indexCaseD);
            int trueCase= cursor.getInt(indexTrueCase);
            Question question1=new Question(question,caseA,caseB,caseC,caseD,trueCase,id);
            arrQuestions.add(question1);
        }
        close();
        return arrQuestions;
    }

    public synchronized void close() {
        if(myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion) {
            try {
                copydatabase();
            } catch (IOException e) {
            }
        }
    }

}
