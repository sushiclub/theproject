package com.kellydwalters.beertome;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kelly on 2018-03-25.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    //Define the database version
    private static final int DB_VERSION = 1;

    //Define your database name
    private static final String DB_NAME="Beer";

    //Define your table name
    private static final String TABLE_REVIEW="Review";
    private static final String TABLE_REVIEW_CAT="review_categories";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String COL_NAME="Name";

    // REVIEW TABLE Create constants defining your column names
    private static final String COL_DESC="Description";
    private static final String COL_ABV="ABC";
    private static final String COL_REVIEW="Review";
    private static final String COL_RATING="Rating";
    private static final String COL_IMAGE="Image";


    //Category table
    //Define your table name
    private static final String TABLE_CATEGORY="Catagories";
    //Create constants defining your column names
//    private static final String CAT_COL_NAME="Name";

    // TABLE_REVIEW_CAT Table - column names
    private static final String KEY_REVIEW_ID = "review_id";
    private static final String KEY_CAT_ID = "category_id";

    //Define your create statement in typical sql format
    //CREATE TABLE {Tablename} (
    //Colname coltype
    //)
    private static final String CREATE_TABLE_REVIEW  =
            "CREATE TABLE " + TABLE_REVIEW + " (" + KEY_ID + " INTEGER PRIMARY KEY," +
                    COL_NAME + " TEXT NOT NULL, " +
                    COL_DESC + " TEXT NOT NULL, " +
                    COL_ABV + ", " +
                    COL_REVIEW + " TEXT NOT NULL " +
                    COL_RATING + "," + COL_IMAGE + " );";

    private static final String CREATE_TABLE_CATEGORY  =
            "CREATE TABLE " + TABLE_CATEGORY + " (" + KEY_ID + " INTEGER PRIMARY KEY," +
                    COL_NAME + " TEXT NOT NULL" + " );";

    private static final String CREATE_TABLE_REV_CAT  =
            "CREATE TABLE " + TABLE_REVIEW_CAT + " (" + KEY_ID + " INTEGER PRIMARY KEY," +
                    KEY_REVIEW_ID + " INTEGER," + KEY_CAT_ID + " INTEGER,"
                    + ");";

    //constructor
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        //  Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_REVIEW);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_REV_CAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEW_CAT);

        // create new tables
        onCreate(db);
    }

    /*
     * Creating a review
     */
    public long createReview(BeerReview review, long[] cat_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, review.getName());
        values.put(COL_DESC, review.getDescription());
        values.put(COL_ABV, review.getAbv());
        values.put(COL_REVIEW, review.getReview());
        values.put(COL_RATING, review.getRating());


        // insert row
        long review_id = db.insert(TABLE_REVIEW, null, values);

        // insert tag_ids
        for (long cat_id : cat_ids) {
            createReviewCat(review_id, cat_id);
        }

        return review_id;
    }

    /*
     * get single review
     */
    public BeerReview getReview(long review_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_REVIEW + " WHERE "
                + KEY_ID + " = " + review_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        BeerReview rev = new BeerReview();
        rev.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        rev.setName((c.getString(c.getColumnIndex(COL_NAME))));
        rev.setDescription(c.getString(c.getColumnIndex(COL_DESC)));
        rev.setAbv((c.getString(c.getColumnIndex(COL_ABV))));
        rev.setReview(c.getString(c.getColumnIndex(COL_REVIEW)));
        rev.setRating((c.getString(c.getColumnIndex(COL_RATING))));
//        rev.setImage(c.getString(c.getColumnIndex(COL_IMAGE)));

        return rev;
    }

    /**
     * getting all reviews
     * */
    public List<BeerReview> getAllReviews() {
        List<BeerReview> reviews = new ArrayList<BeerReview>();
        String selectQuery = "SELECT  * FROM " + TABLE_REVIEW;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                BeerReview rev = new BeerReview();
                rev.setName((c.getString(c.getColumnIndex(COL_NAME))));
                rev.setDescription(c.getString(c.getColumnIndex(COL_DESC)));
                rev.setAbv((c.getString(c.getColumnIndex(COL_ABV))));
                rev.setReview(c.getString(c.getColumnIndex(COL_REVIEW)));
                rev.setRating((c.getString(c.getColumnIndex(COL_RATING))));
//              rev.setImage(c.getString(c.getColumnIndex(COL_IMAGE)));

                // adding to review list
                reviews.add(rev);
            } while (c.moveToNext());
        }

        return reviews;
    }

    /*
    * getting review count
    */
    public int getReviewCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REVIEW;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /*
	 * Creating category
	 */
    public long createCategory(BeerCategory cat) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_NAME, cat.getCategoryName());

        // insert row
        long cat_id = db.insert(TABLE_CATEGORY, null, values);

        return cat_id;
    }

    /**
     * getting all tags
     * */
    public List<BeerCategory> getAllCategories() {
        List<BeerCategory> tags = new ArrayList<BeerCategory>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                BeerCategory cat = new BeerCategory();
                cat.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                cat.setCategoryName(c.getString(c.getColumnIndex(COL_NAME)));

                // adding to tags list
                tags.add(cat);
            } while (c.moveToNext());
        }
        return tags;
    }


    /*
     * Creating review_cateogry
     */
    public long createReviewCat(long review_id, long cat_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REVIEW_ID, review_id);
        values.put(KEY_CAT_ID, cat_id);

        long id = db.insert(TABLE_REVIEW_CAT, null, values);

        return id;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
