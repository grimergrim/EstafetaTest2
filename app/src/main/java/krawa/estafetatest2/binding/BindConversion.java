package krawa.estafetatest2.binding;

import android.databinding.BindingConversion;
import android.view.View;

public class BindConversion {

    @BindingConversion
    public static int convertBooleanToVisibility(boolean visible) {
        return visible ? View.VISIBLE : View.GONE;
    }

}
