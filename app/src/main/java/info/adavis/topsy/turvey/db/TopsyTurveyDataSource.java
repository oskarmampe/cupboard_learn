package info.adavis.topsy.turvey.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import info.adavis.topsy.turvey.models.Recipe;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class TopsyTurveyDataSource
{
    private static final String TAG = TopsyTurveyDataSource.class.getSimpleName();
    private SQLiteDatabase database;
    private DatabaseSQLiteHelper dbHelper;

    public TopsyTurveyDataSource(Context context) {
        this.dbHelper = new DatabaseSQLiteHelper(context);
    }


    public void open()
    {
        database = dbHelper.getWritableDatabase();
        Log.d( TAG, "open: database opened" );
    }

    public void close()
    {
        Log.d( TAG, "close: database closed" );
    }

    public void createRecipe (final Recipe recipe)
    {
        long rowId = cupboard().withDatabase(database).put(recipe);

        Log.d(TAG, "createRecipe: the id: " + recipe.getId());
    }

    public List<Recipe> getAllRecipes() {
        return cupboard().withDatabase(database)
                .query(Recipe.class)
                .list();
    }

    /**
     *
     * Recipe update using put
     *
     * @param recipe {@link Recipe} to be updated
     */
    public void updateRecipe(Recipe recipe) {
        cupboard().withDatabase(database).put(recipe);
    }

    /**
     *
     * Recipe update using content values
     *
     * @param recipe {@link Recipe} to be updated
     */
    public void updateRecipeValues(Recipe recipe) {
        ContentValues values = new ContentValues();
        values.put("description", "Wonderful, red cake!");

        cupboard().withDatabase(database).update(Recipe.class, values, "_id = ?", String.valueOf(recipe.getId()));
    }

    public void deleteRecipe(Recipe recipe) {
        cupboard().withDatabase(database).delete(recipe);
    }

    public void deleteAllRecipes() {
        cupboard().withDatabase(database).delete(Recipe.class, null);
    }

}