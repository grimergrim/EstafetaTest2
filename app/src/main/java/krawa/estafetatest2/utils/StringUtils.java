package krawa.estafetatest2.utils;

import android.content.Context;
import android.support.annotation.StringRes;

public class StringUtils {

    private final Context appContext;

    public StringUtils(Context appContext) {
        this.appContext = appContext;
    }

    public String getString(@StringRes int res){
        return appContext.getString(res);
    }

}
