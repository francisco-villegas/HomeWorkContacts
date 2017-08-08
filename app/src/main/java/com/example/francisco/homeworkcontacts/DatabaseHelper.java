package com.example.francisco.homeworkcontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by FRANCISCO on 07/08/2017.
 */

public class DatabaseHelper  extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Contacts";

    public static final String TABLE_NAME ="Contacts";
    public static final String CONTACT_ID ="ID";
    public static final String CONTACT_NAME ="Name";
    public static final String CONTACT_LAST_NAME ="Last_name";
    public static final String CONTACT_PHONE ="Phone";
    public static final String CONTACT_EMAIL ="Email";
    public static final String CONTACT_COMPANY ="Company";
    public static final String CONTACT_ADDRESS ="Address";
    public static final String CONTACT_IMG ="IMG";

    private static final String TAG = "MyDBTag";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
                CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTACT_NAME + " TEXT, " +
                CONTACT_LAST_NAME + " TEXT, " +
                CONTACT_PHONE + " TEXT, " +
                CONTACT_EMAIL + " TEXT, " +
                CONTACT_COMPANY + " TEXT, " +
                CONTACT_ADDRESS + " TEXT, " +
                CONTACT_IMG + " BLOB " +
                ")";
        Log.d(TAG, "onCreate: "+CREATE_TABLE);

        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d(TAG, "onUpgrade: ");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void saveNewContact(MyContact contact){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_NAME, contact.getName());
        contentValues.put(CONTACT_LAST_NAME, contact.getLast_name());
        contentValues.put(CONTACT_PHONE, contact.getPhone());
        contentValues.put(CONTACT_EMAIL, contact.getEmail());
        contentValues.put(CONTACT_COMPANY, contact.getCompany());
        contentValues.put(CONTACT_ADDRESS, contact.getCompany());
        contentValues.put(CONTACT_IMG, contact.getBitmap());

        Log.d(TAG, "saveNewContact: "+contact.getName() + " " + contact.getPhone());

        database.insert(TABLE_NAME,null,contentValues);
    }

    public void uploadNewContact(MyContact contact, String id){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_NAME, contact.getName());
        contentValues.put(CONTACT_LAST_NAME, contact.getLast_name());
        contentValues.put(CONTACT_PHONE, contact.getPhone());
        contentValues.put(CONTACT_EMAIL, contact.getEmail());
        contentValues.put(CONTACT_COMPANY, contact.getCompany());
        contentValues.put(CONTACT_ADDRESS, contact.getCompany());
        contentValues.put(CONTACT_IMG, contact.getBitmap());

        Log.d(TAG, "saveNewContact: "+contact.getName() + " " + contact.getPhone());
        database.update(TABLE_NAME,contentValues, CONTACT_ID+" = ? ",new String[]{id});
    }

    public void DeleteContact(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String[] args = new String[]{id};

        String DELETE = "DELETE FROM " + TABLE_NAME + " WHERE " + CONTACT_ID + " = ? ";
        Log.d(TAG, "DeleteContact: "+DELETE);

        database.execSQL(DELETE,args);
    }

    public ArrayList<MyContact> getContacts(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "";
        Cursor cursor;
        if(id.equals("-1")){
            query = "SELECT * FROM " + TABLE_NAME;
            Log.d(TAG, "getContacts: "+query + " " + id);
            cursor = database.rawQuery(query, null);
        }
        else {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + CONTACT_ID + " = ?";
            Log.d(TAG, "getContacts: "+query + " " + id);
            String parameters[] = {id};
            cursor = database.rawQuery(query, parameters);
        }
        //Cursor cursor = database.rawQuery(query, null);
        ArrayList<MyContact> contacts = new ArrayList();
        if(cursor.moveToFirst()){
            do{
                Log.d(TAG, "getContacts: Name:" + cursor.getString(0) + ", Phone: "+ cursor.getString(1));
                contacts.add(new MyContact(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getBlob(7)));
            }while(cursor.moveToNext());
        }
        else{
            Log.d(TAG, "getContacts: empty");
        }
        return contacts;
    }
}
