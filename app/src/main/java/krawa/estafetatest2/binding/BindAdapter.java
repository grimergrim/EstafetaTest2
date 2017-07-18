package krawa.estafetatest2.binding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;

import krawa.estafetatest2.App;

public class BindAdapter {

    private static final String TAG = "BindAdapter";

    @BindingAdapter({"android:src"})
    public static void loadImage(ImageView view, String url) {
        if(url == null || url.isEmpty()) return;
        Picasso picasso = Picasso.with(App.getAppContext());
        RequestCreator rc;
        if(url.startsWith("/")){
            rc = picasso.load(new File(url));
        }else{
            rc = picasso.load(url);
        }
        rc.fit().centerCrop().into(view);
    }

}
