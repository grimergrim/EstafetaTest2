package krawa.estafetatest2.ui.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;

import krawa.estafetatest2.R;
import krawa.estafetatest2.databinding.ActivityMainBinding;
import krawa.estafetatest2.ui.imagefinder.ImageFinderFragment;

public class MainActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(binding.toolbar);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ImageFinderFragment(), ImageFinderFragment.TAG)
                    .commit();
        }
    }
}
