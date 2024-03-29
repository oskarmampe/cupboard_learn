package info.adavis.topsy.turvey.features.recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import info.adavis.topsy.turvey.R;
import info.adavis.topsy.turvey.db.RecipesDataProvider;
import info.adavis.topsy.turvey.db.TopsyTurveyDataSource;
import info.adavis.topsy.turvey.models.Recipe;

public class RecipesActivity extends AppCompatActivity
{
    private static final String TAG = RecipesActivity.class.getSimpleName();

    private RecyclerView recipesRecyclerView;
    private TopsyTurveyDataSource dataSource;
    private RecipesAdapter recipesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipesAdapter = new RecipesAdapter(this);
        recipesRecyclerView = (RecyclerView) findViewById(R.id.recipes_recycler_view);

        dataSource = new TopsyTurveyDataSource(this);


        setupRecyclerView();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        dataSource.open();

//        for (Recipe recipe : RecipesDataProvider.recipesList) {
//            dataSource.createRecipe(recipe);
//        }

        List<Recipe> allRecipes = getRecipes();
        Recipe updatedRecipe = allRecipes.get(0);
//        updatedRecipe.setDescription("Wonderful, yellow cake!");
//        dataSource.updateRecipe(updatedRecipe);
//        dataSource.updateRecipeValues(updatedRecipe);
//        dataSource.deleteRecipe(updatedRecipe);
//        dataSource.deleteAllRecipes();

//        getRecipes();
    }

    @Override
    protected void onPause()
    {
        dataSource.close();

        super.onPause();
    }

    public List<Recipe> getRecipes(){
        List<Recipe> allRecipes = dataSource.getAllRecipes();

        for (Recipe allRecipe : allRecipes) {
            Log.i(TAG, "recipe: " + allRecipe);
        }

        recipesAdapter.setRecipes(allRecipes);
        recipesAdapter.notifyDataSetChanged();

        return allRecipes;
    }

    private void setupRecyclerView()
    {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recipesRecyclerView.setLayoutManager(layoutManager);

        recipesRecyclerView.setHasFixedSize(true);

        recipesRecyclerView.setAdapter(recipesAdapter);
    }

}
