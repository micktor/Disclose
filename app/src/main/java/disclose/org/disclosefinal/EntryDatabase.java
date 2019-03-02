package disclose.org.disclosefinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.util.Log;

public class EntryDatabase extends SQLiteOpenHelper {

    private static final String TAG = "EntryDatabase";

    private static final String TABLE_NAME = "timeline";
    private static final String COL1 = "ID";
    private static final String COL2 = "Content";
    private static final String COL3 = "Mood";
    private static final String COL4 = "Score";
    private static final String COL5 = "Timestamp";




    public EntryDatabase(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = ("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 +" TEXT, " + COL3 +" TEXT, " + COL4 +" REAL," + COL5 +" DATETIME DEFAULT CURRENT_TIMESTAMP);");
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String item, String item2, Double item3) {
        SQLiteDatabase db = this.getWritableDatabase();

        //db.execSQL("INSERT INTO " + TABLE_NAME + " (COL2, COL3, COL4) VALUES("+item+","+item2+","+item3+")");
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, item);
        contentValues.put(COL3, item2);
        contentValues.put(COL4, item3);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY Timestamp DESC";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getLatest(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" ORDER BY Timestamp DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(int i, int j){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+COL1+" >= "+i+" AND "+COL1+" <= "+j;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

}
