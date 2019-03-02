package disclose.org.disclosefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static String DB_PATH = "/data/data/disclose.org.disclosefinal/databases/";
    private static final String DB_NAME = "sms";

    private SQLiteDatabase myDataBase;
    private final Context myContext;


    private static final String Message = "Message";
    private static final String Thread = "Thread";
    private static final String Contact = "Contact";
    private static final String Timestamp = "Timestamp";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTable = "CREATE TABLE " + DB_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +" TEXT, " + COL3 +" TEXT," + COL4 +" TEXT)";
//        String query = "SELECT * FROM " + DB_NAME;
//        db.execSQL(query);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        //String createTable = "CREATE TABLE " + DB_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +" TEXT, " + COL3 +" TEXT," + COL4 +" TEXT)";
//        String query = "SELECT * FROM " + DB_NAME;
//        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        db.execSQL("DROP IF TABLE EXISTS " + DB_NAME);
//        onCreate(db);
    }

    public boolean addData(String item, String item2, String item3) {
        SQLiteDatabase db = myDataBase;

        //db.execSQL("INSERT INTO " + DB_NAME + " (COL2, COL3, COL4) VALUES("+item+","+item2+","+item3+")");
        ContentValues contentValues = new ContentValues();
        contentValues.put(Message, item);
        contentValues.put(Thread, item2);
        contentValues.put(Contact, item3);
        contentValues.put(Timestamp, item3);

        long result = db.insert(DB_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = myDataBase;
        String query = "SELECT * FROM " + DB_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
