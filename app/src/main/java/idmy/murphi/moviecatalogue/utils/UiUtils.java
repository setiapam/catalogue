package idmy.murphi.moviecatalogue.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;

import androidx.annotation.ColorRes;
import androidx.core.graphics.drawable.DrawableCompat;

public class UiUtils {

    public static void tintMenuIcon(Context context, MenuItem item, @ColorRes int color) {
        Drawable itemIcon = item.getIcon();
        Drawable iconWrapper = DrawableCompat.wrap(itemIcon);
        DrawableCompat.setTint(iconWrapper, context.getResources().getColor(color));

        item.setIcon(iconWrapper);
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}
