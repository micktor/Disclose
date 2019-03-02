package disclose.org.disclosefinal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SMSDatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static SMSDatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private SMSDatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of SMSDatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static SMSDatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new SMSDatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getThreads() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Contact FROM sms WHERE Contact > 0 ORDER BY Timestamp", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<Integer> getThId() {
        List<Integer> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT Thread FROM sms WHERE Contact > 0 ORDER BY Timestamp", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> getMessages(int number) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT Message FROM sms WHERE Thread IS "+number+" ORDER BY Timestamp", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

}
