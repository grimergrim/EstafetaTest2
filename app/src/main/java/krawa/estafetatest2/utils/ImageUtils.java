package krawa.estafetatest2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Environment;
import android.os.Handler;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import krawa.estafetatest2.model.Image;

public class ImageUtils {

    private Context mContext;

    public ImageUtils(Context context) {
        mContext = context;
    }

    public void saveImages(final List<Image> images, final OnImageSaveCallback callback) {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (Image image : images) {
                        String fileName = getFileNameFromUrl(image.getLink());
                        Bitmap bitmap = Picasso.with(mContext).load(image.getLink()).get();
                        File file = saveBitmapToFile(bitmap, fileName);

                        ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, GpsUtils.convert(image.getLat()));
                        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, GpsUtils.latitudeRef(image.getLat()));
                        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, GpsUtils.convert(image.getLon()));
                        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, GpsUtils.longitudeRef(image.getLon()));
                        exif.saveAttributes();

                        String datetime = exif.getAttribute(ExifInterface.TAG_DATETIME);

                        image.setDateTime(datetime);
                        image.setPath(file.getAbsolutePath());
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onImageSaveResult(images);
                        }
                    });
                } catch (IOException e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onImageSaveResult(null);
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getFileNameFromUrl(String url) {
        return url.substring(url.lastIndexOf('/')+1, url.length());
    }

    private File saveBitmapToFile(Bitmap bitmap, String fileName) throws IOException {
        File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/EstafetaTest/");
        if(!folder.exists()) folder.mkdirs();
        File file = new File(folder, fileName);
        if(!file.exists()){
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
            ostream.flush();
            ostream.close();
        }
        return file;
    }

    public interface OnImageSaveCallback{
        void onImageSaveResult(List<Image> images);
    }
}
