package krawa.estafetatest2.ui.main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.arellomobile.mvp.MvpAppCompatActivity;

import javax.inject.Inject;

import krawa.estafetatest2.App;
import krawa.estafetatest2.R;
import krawa.estafetatest2.databinding.ActivityMainBinding;
import krawa.estafetatest2.ui.imagefinder.ImageFinderFragment;
import krawa.estafetatest2.utils.LocationUtils;

public class MainActivity extends MvpAppCompatActivity implements LocationUtils.OnCheckGPSListener {

    @Inject
    LocationUtils locationUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        App.getAppComponent().inject(this);
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        if(savedInstanceState == null) {
            checkPermissions();
        }
    }

    private void checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,  Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            checkGPS();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allGranted = true;
        for (int grantResult : grantResults) {
            if(grantResult != PackageManager.PERMISSION_GRANTED){
                allGranted = false;
                break;
            }
        }
        if(allGranted){
            checkGPS();
        } else {
            showNeedPermissionsDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LocationUtils.GPS_REQUEST_CODE){
            if(resultCode != RESULT_OK){
                showNeedGPSDialog();
            } else {
                onCheckGPSSuccess();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void checkGPS() {
        locationUtils.checkGPS(this);
    }

    @Override
    public void onCheckGPSSuccess() {
        showFinderFragment();
    }

    private void showFinderFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ImageFinderFragment(), ImageFinderFragment.TAG)
                .commit();
    }

    private void showNeedPermissionsDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.need_permiss_message)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermissions();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void showNeedGPSDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.need_gps_message)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkGPS();
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}
