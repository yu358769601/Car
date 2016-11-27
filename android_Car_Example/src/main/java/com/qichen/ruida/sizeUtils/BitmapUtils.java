package com.qichen.ruida.sizeUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * @author LeonNoW
 * @version 1.0
 * @class Bitmap Utils
 * @date 2013-11-13
 */
public class BitmapUtils {
    //
    /**
     * 获取调整大小后的图片
     * @param uri
     * @param size
     * @param autoRotate
     * @return
     */
    public static Bitmap getResizeBitmap(Context context , Uri uri, int size, boolean autoRotate) {
        String path = FileUtils.getContentFilePath(context, uri);
        if (path.toLowerCase(Locale.getDefault()).startsWith("content:") == true) {
            return getResizeBitmapFromContent(context,uri, size, autoRotate);
        } else {
            return getResizeBitmapFromFile(path, size, autoRotate);
        }
    }
    /**
     * 从给定路径加载图片
     * */
    public static Bitmap loadBitmap(String imgpath) {
        return BitmapFactory.decodeFile(imgpath);
    }
    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     *
     * */
    public static Bitmap loadBitmap(String imgpath, boolean adjustOritation) {
        if (!adjustOritation) {
            return loadBitmap(imgpath);
        } else {
            Bitmap bm = loadBitmap(imgpath);
            int digree = 0;
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imgpath);
            } catch (IOException e) {
                e.printStackTrace();
                exif = null;
            }
            if (exif != null) {
                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;
                }
            }
            if (digree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(digree);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
            }
            return bm;
        }
    }

    private static Bitmap getResizeBitmapFromContent(Context context , Uri uri, int size, boolean autoRotate) {
        try {
            ContentResolver resolver = context.getContentResolver();
            InputStream inputStream = resolver.openInputStream(uri);
            Bitmap resizeBitmap = getResizeBitmapFromStream(resolver.openFileDescriptor(uri, "r").getFileDescriptor(), inputStream, size);
            inputStream.close();
            inputStream = null;

            if (autoRotate == true) {
                int degree = readPictureDegreeFromContent(context,uri);
                if (degree != 0) {
                    return rotaingImageView(resizeBitmap, degree);
                }
            }
            return resizeBitmap;
        } catch (OutOfMemoryError e) {
            System.gc();
            return null;
        } catch (IOException e) {
            System.gc();
            return null;
        }
    }

    private static Bitmap getResizeBitmapFromFile(String path, int size, boolean autoRotate) {
        try {
            File file = new File(path);
            if (file.exists() == true && file.isFile()) {
                FileInputStream inputStream = new FileInputStream(path);
                Bitmap resizeBitmap = getResizeBitmapFromStream(inputStream.getFD(), inputStream, size);
                inputStream.close();
                inputStream = null;

                if (autoRotate == true) {
                    int degree = readPictureDegreeFromFile(path);
                    if (degree != 0) {
                        return rotaingImageView(resizeBitmap, degree);
                    }
                }
                return resizeBitmap;
            }
            return null;
        } catch (OutOfMemoryError e) {
            System.gc();
            return null;
        } catch (IOException e) {
            System.gc();
            return null;
        }
    }

    private static Bitmap getResizeBitmapFromStream(FileDescriptor fileDescriptor, InputStream inputStream, int size) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
        options.inSampleSize = BitmapUtils.computeSampleSize(options, size, 2 * size * size);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * 获取指定大小的bitmap
     * @param imageData
     * @param maxSize
     * @param resizeWidth
     * @param resizeHeight
     * @return
     */
    public static Bitmap getResizeBitmapFromByte(byte[] imageData, int maxSize, int resizeWidth, int resizeHeight) {
        try {
            if (imageData.length > 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
                if (maxSize > 0 && (options.outWidth > maxSize || options.outHeight > maxSize)) {
                    options.inSampleSize = BitmapUtils.computeSampleSize(options, maxSize, maxSize * maxSize);
                }
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
                if (resizeWidth > 0 && resizeHeight > 0) {
                    bitmap = Bitmap.createScaledBitmap(bitmap, resizeWidth, resizeHeight, true);//创建指定大小的bitmap
                }
                return bitmap;
            }
        } catch (OutOfMemoryError e) {
            System.gc();
        } catch (Exception e) {
            System.gc();
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    private static Bitmap rotaingImageView(Bitmap bitmap, int angle) {
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return rotateBitmap;
    }

    private static String getImageContentPath(Context context , Uri uri) {
        String[] projection = {MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int nIndex = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(nIndex);
    }

    private static int readPictureDegreeFromContent(Context context , Uri uri) {
        String file = getImageContentPath(context,uri);
        if (file != null && file.length() > 0) {
            return readPictureDegreeFromFile(file);
        } else {
            String tempfile = createTemporaryImage(context,uri);
            if (tempfile != null && tempfile.length() > 0) {
                int degree = readPictureDegreeFromFile(tempfile);
                File deleteFile = new File(tempfile);
                if (deleteFile.exists() == true && deleteFile.isFile() == true) {
                    deleteFile.delete();
                }
                return degree;
            } else {
                return 0;
            }
        }
    }

    private static String createTemporaryImage(Context context , Uri uri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

            String path = String.format("%1$s%2$s.jpg", UnifiedImageFormat.STROAGE_CAPTURE, FormatUtils.getNameByData());

            saveBitmap(bitmap, path);

            return path;
        } catch (OutOfMemoryError e) {
            System.gc();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean saveBitmap(Bitmap bitmap, String path) {
        File saveFile = new File(path);
        try {
            saveFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(saveFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
            outputStream.flush();
            outputStream.close();
            outputStream = null;
            return true;
        } catch (OutOfMemoryError e) {
            System.gc();
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    private static int readPictureDegreeFromFile(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90: {
                    degree = 90;
                    break;
                }
                case ExifInterface.ORIENTATION_ROTATE_180: {
                    degree = 180;
                    break;
                }
                case ExifInterface.ORIENTATION_ROTATE_270: {
                    degree = 270;
                    break;
                }
            }
        } catch (OutOfMemoryError e) {
            System.gc();
            return 0;
        } catch (IOException e) {
            return 0;
        }
        return degree;
    }

    public static String convertBitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream outputStream = null;
        try {
            if (bitmap != null) {
                outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, outputStream);

                outputStream.flush();
                outputStream.close();

                byte[] bitmapBytes = outputStream.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            result = "";
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                result = "";
            }
        }
        return result;
    }

    public static Bitmap getSquareCenterBitmap(Bitmap bitmap, int size) {
        if (bitmap != null && size >= 0) {
            Bitmap result = bitmap;
            int originalWidth = bitmap.getWidth();
            int originalHeight = bitmap.getHeight();

            if (originalWidth > size && originalHeight > size) {
                int longerEdge = size * Math.max(originalWidth, originalHeight) / Math.min(originalWidth, originalHeight);
                int scaledWidth = originalWidth > originalHeight ? longerEdge : size;
                int scaledHeight = originalWidth > originalHeight ? size : longerEdge;
                Bitmap scaledBitmap;

                try {
                    scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);

                    int x = (scaledWidth - size) / 2;
                    int y = (scaledHeight - size) / 2;

                    result = Bitmap.createBitmap(scaledBitmap, x, y, size, size);
                    scaledBitmap.recycle();
                } catch (OutOfMemoryError e) {
                    return null;
                } catch (Exception e) {
                    return null;
                }
            }

            return result;
        } else {
            return null;
        }
    }

    public static Bitmap readResource(Context context , int resId) {
        try {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inPreferredConfig = Bitmap.Config.RGB_565;
            option.inPurgeable = true;
            option.inInputShareable = true;

            InputStream stream = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(stream, null, option);
        } catch (OutOfMemoryError ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }
}
