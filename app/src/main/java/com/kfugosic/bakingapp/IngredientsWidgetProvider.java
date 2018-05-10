package com.kfugosic.bakingapp;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.kfugosic.bakingapp.models.Recipe;
import com.kfugosic.bakingapp.utils.AppUtils;

import org.parceler.Parcels;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientsWidgetProvider extends AppWidgetProvider {


    private static Recipe currentRecipe;

    public static void setRecipe(Recipe recipe) {
        currentRecipe = recipe;
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        CharSequence headerText = null;
        CharSequence widgetText = null;
        PendingIntent pendingIntentHeader = null;
        PendingIntent pendingIntentBody = null;
        if (currentRecipe == null) {
            headerText = context.getString(R.string.app_name);
            widgetText = context.getString(R.string.appwidget_default_text);
            Intent intent = new Intent(context, MainActivity.class);
            pendingIntentBody = pendingIntentHeader = PendingIntent.getActivity(context, -1, intent, 0);

        } else {
            headerText = currentRecipe.getName();
            widgetText = AppUtils.buildIngredientsSummary(currentRecipe.getIngredients());
            Intent mainIntent = new Intent(context, MainActivity.class);

            Intent recipeIntent = new Intent(context, RecipeDetailsActivity.class);
            recipeIntent.putExtra(AppUtils.RECIPE_KEY, Parcels.wrap(currentRecipe));
            TaskStackBuilder stackBuilder1 = TaskStackBuilder // not necessary for this project but good to know for future usage.
                    .create(context)
                    .addNextIntent(mainIntent)
                    .addNextIntent(recipeIntent);
            //pendingIntentHeader = PendingIntent.getActivity(context, currentRecipe.getId(), recipeIntent, 0); //Without stack
            pendingIntentHeader = stackBuilder1.getPendingIntent(currentRecipe.getId(), PendingIntent.FLAG_UPDATE_CURRENT);

            Intent ingridientsIntent = new Intent(context, IngredientsActivity.class);
            ingridientsIntent.putExtra(AppUtils.INGREDIENTS_KEY, widgetText);
            TaskStackBuilder stackBuilder2 = TaskStackBuilder // not necessary for this project but good to know for future usage.
                    .create(context)
                    .addNextIntent(mainIntent)
                    .addNextIntent(recipeIntent)
                    .addNextIntent(ingridientsIntent);
            //pendingIntentBody = PendingIntent.getActivity(context, currentRecipe.getId(), intent, 0); //Without stack
            pendingIntentBody = stackBuilder2.getPendingIntent(currentRecipe.getId(), PendingIntent.FLAG_UPDATE_CURRENT);
        }

        views.setTextViewText(R.id.appwidget_header, headerText);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setOnClickPendingIntent(R.id.appwidget_header, pendingIntentHeader);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntentBody);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

