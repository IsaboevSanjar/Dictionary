package uz.sanjar.dictionarysql.core.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import uz.sanjar.dictionarysql.core.lib.DbHelper;
import uz.sanjar.dictionarysql.core.model.Words;

public class Database extends DbHelper {
    private static Database database;

    private Database(@Nullable Context context) {
        super(context, "WORDS.db");
    }

    public static void init(Context context) {
        if (database == null) {
            database = new Database(context);
        }
    }

    public static Database getDatabase() {
        return database;
    }

    @SuppressLint("Range")
    public ArrayList<Words> getDictionary() {
        ArrayList<Words> dictionaries = new ArrayList<>();
        String query = "SELECT * FROM WORDS";
        Cursor cursor = mDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String eng = cursor.getString(cursor.getColumnIndex("en"));
            String uz = cursor.getString(cursor.getColumnIndex("uz"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int history = cursor.getInt(cursor.getColumnIndex("history"));
            int added = cursor.getInt(cursor.getColumnIndex("added"));
            int enuz = cursor.getInt(cursor.getColumnIndex("enuz"));
            Words dictionary = new Words(id, eng, uz, desc, fav, history, added, enuz, false, false);
            dictionaries.add(dictionary);
            cursor.moveToNext();
        }

        return dictionaries;
    }

    public long addWords(String english, String uzbek, String description) {

        // bu class huddi bundledan ma'lumot olib otgandek ishliydi
        ContentValues contentValues = new ContentValues();
        contentValues.put("en", english);
        contentValues.put("uz", uzbek);
        contentValues.put("desc", description);
        contentValues.put("added", 1);
        //insert- ya'ni ma'lulotlarni INSERT INTO buyrug'i helperning uzida mavjud
        return mDataBase.insert("WORDS", null, contentValues);
    }

    public int deleteWords(Words words) {
        int id = words.getId();
        //table
        //shart
        //parametr
        ContentValues contentValues = new ContentValues();
        contentValues.put("en", words.getEng());
        contentValues.put("uz", words.getUz());
        contentValues.put("fav", words.getFav());
        contentValues.put("desc", words.getDescription());
        contentValues.put("history", words.getHistory());
        contentValues.put("added", words.getAdded());
        contentValues.put("enuz", words.getEnuz());
        return mDataBase.delete("WORDS", "id=" + id, null);
    }

    public int updateWords(Words words) {
        int id = words.getId();
        ContentValues contentValues = new ContentValues();
        contentValues.put("en", words.getEng());
        contentValues.put("uz", words.getUz());
        contentValues.put("fav", words.getFav());
        contentValues.put("desc", words.getDescription());
        contentValues.put("history", words.getHistory());
        contentValues.put("added", words.getAdded());
        contentValues.put("enuz", words.getEnuz());
        return mDataBase.update("WORDS", contentValues, "id=" + id, null);
    }

    @SuppressLint("Range")

    public ArrayList<Words> getHistory() {
        ArrayList<Words> dictionaries = new ArrayList<>();
        String query = "SELECT * FROM WORDS where history=1";
        Cursor cursor = mDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String eng = cursor.getString(cursor.getColumnIndex("en"));
            String uz = cursor.getString(cursor.getColumnIndex("uz"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int history = cursor.getInt(cursor.getColumnIndex("history"));
            int added = cursor.getInt(cursor.getColumnIndex("added"));
            int enuz = cursor.getInt(cursor.getColumnIndex("enuz"));
            Words dictionary = new Words(id, eng, uz, desc, fav, history, added, enuz, false, false);
            dictionaries.add(dictionary);
            cursor.moveToNext();
        }
        return dictionaries;
    }

    @SuppressLint("Range")

    public ArrayList<Words> getSearch() {
        ArrayList<Words> dictionaries = new ArrayList<>();
        String query = "SELECT * FROM WORDS where enuz=1";
        Cursor cursor = mDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String eng = cursor.getString(cursor.getColumnIndex("en"));
            String uz = cursor.getString(cursor.getColumnIndex("uz"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int history = cursor.getInt(cursor.getColumnIndex("history"));
            int added = cursor.getInt(cursor.getColumnIndex("added"));
            int enuz = cursor.getInt(cursor.getColumnIndex("enuz"));
            Words dictionary = new Words(id, eng, uz, desc, fav, history, added, enuz, false, false);
            dictionaries.add(dictionary);
            cursor.moveToNext();
        }
        return dictionaries;
    }

    @SuppressLint("Range")

    public ArrayList<Words> getAdded() {
        ArrayList<Words> dictionaries = new ArrayList<>();
        String query = "SELECT * FROM WORDS where added=1";
        Cursor cursor = mDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String eng = cursor.getString(cursor.getColumnIndex("en"));
            String uz = cursor.getString(cursor.getColumnIndex("uz"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int history = cursor.getInt(cursor.getColumnIndex("history"));
            int added = cursor.getInt(cursor.getColumnIndex("added"));
            int enuz = cursor.getInt(cursor.getColumnIndex("enuz"));
            Words dictionary = new Words(id, eng, uz, desc, fav, history, added, enuz, false, false);
            dictionaries.add(dictionary);
            cursor.moveToNext();
        }
        return dictionaries;
    }

    @SuppressLint("Range")

    public ArrayList<Words> getFav() {
        ArrayList<Words> dictionaries = new ArrayList<>();
        String query = "SELECT * FROM WORDS WHERE fav=1";
        Cursor cursor = mDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String eng = cursor.getString(cursor.getColumnIndex("en"));
            String uz = cursor.getString(cursor.getColumnIndex("uz"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int history = cursor.getInt(cursor.getColumnIndex("history"));
            int added = cursor.getInt(cursor.getColumnIndex("added"));
            int enuz = cursor.getInt(cursor.getColumnIndex("enuz"));
            Words dictionary = new Words(id, eng, uz, desc, fav, history, added, enuz, false, false);
            dictionaries.add(dictionary);
            cursor.moveToNext();
        }
        return dictionaries;
    }


    @SuppressLint("Range")
    public ArrayList<Words> searchEnglishWords(String text) {
        ArrayList<Words> englishWord = new ArrayList<>();
        String query = "SELECT * FROM WORDS WHERE en LIKE '" + text + "%'";
        Cursor cursor = mDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String eng = cursor.getString(cursor.getColumnIndex("en"));
            String uz = cursor.getString(cursor.getColumnIndex("uz"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int history = cursor.getInt(cursor.getColumnIndex("history"));
            int added = cursor.getInt(cursor.getColumnIndex("added"));
            int enuz = cursor.getInt(cursor.getColumnIndex("enuz"));
            Words dictionary = new Words(id, eng, uz, desc, fav, history, added, enuz, false, false);
            englishWord.add(dictionary);
            cursor.moveToNext();
        }
        return englishWord;
    }

    @SuppressLint("Range")
    public ArrayList<Words> searchUzWords(String text) {
        ArrayList<Words> englishWord = new ArrayList<>();
        String query = "SELECT * FROM WORDS WHERE uz LIKE '" + text + "%'";
        Cursor cursor = mDataBase.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String eng = cursor.getString(cursor.getColumnIndex("en"));
            String uz = cursor.getString(cursor.getColumnIndex("uz"));
            String desc = cursor.getString(cursor.getColumnIndex("desc"));
            int fav = cursor.getInt(cursor.getColumnIndex("fav"));
            int history = cursor.getInt(cursor.getColumnIndex("history"));
            int added = cursor.getInt(cursor.getColumnIndex("added"));
            int enuz = cursor.getInt(cursor.getColumnIndex("enuz"));
            Words dictionary = new Words(id, eng, uz, desc, fav, history, added, enuz, false, false);
            englishWord.add(dictionary);
            cursor.moveToNext();
        }
        return englishWord;
    }

}