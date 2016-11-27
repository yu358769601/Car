package com.qichen.ruida.sizeUtils;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

/**
 * @class File Utils
 * @author LeonNoW
 * @version 1.0
 * @date 2014年5月8日
 */
public class FileUtils
	//
{
	public static boolean isExists(String path)
	{
		return new File(path).exists();
	}
	
	public static void verifyDirectory(String directory)
	{
		File file = new File(directory);
		if (file.exists() == true)
		{
			if (file.isDirectory() == true)
			{
				return;
			}
			else 
			{
				file.delete();
			}
		}
		file.mkdirs();
	}
		
	public static long getFolderSize(File file)
	{  
        long size = 0;  
        File[] fileList = file.listFiles();
        for (int i = 0; i < fileList.length; i++)  
        {  
            if (fileList[i].isDirectory())  
            {  
                size = size + getFolderSize(fileList[i]);  
            } 
            else  
            {  
                size = size + fileList[i].length();  
            }  
        }  
        return size;  
    }  
	
	public static String getFileSize(long size)
	{  
        DecimalFormat format = new DecimalFormat("###.##");
        float f = ((float) size / (1024 * 1024));  
        return format.format(Float.valueOf(f).doubleValue()) + " MB";
    }  
	
	public static void deleteFolderFile(String filePath, boolean deleteThisPath)
    {  
        if (filePath != null && filePath.length() > 0) 
        {  
            File file = new File(filePath);
  
            if (file.isDirectory()) 
            {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) 
                {  
                    deleteFolderFile(files[i].getAbsolutePath(), true);  
                }  
            }  
            if (deleteThisPath) 
            {  
                if (!file.isDirectory()) 
                { 
                    file.delete();  
                } 
                else 
                {
                    if (file.listFiles().length == 0) 
                    { 
                        file.delete();  
                    }  
                }  
            }  
        }  
    }  

//	public static String loadFile(String filename)
//	{
//		if (ApplicationUtils.detectSDMounted() == false)
//		{
//			return null;
//		}
//
//		File loadFile = new File(filename);
//		if (loadFile.exists() == false)
//        {
//        	loadFile = null;
//        	return null;
//        }
//		loadFile = null;
//
//		String text;
//		try
//		{
//			FileInputStream inputStream = new FileInputStream(filename);
//			text = FormatUtils.convertInputStreamToString(inputStream);
//			inputStream.close();
//			inputStream = null;
//		}
//		catch (Exception e)
//		{
//			return null;
//		}
//
//		filename = null;
//		return text;
//	}
//
	public static boolean deleteFile(String filename)
	{
		deleteFolderFile(filename, true);
		return true;
	}
	
//	public static boolean saveFile(String filename, String content, boolean append)
//	{
//		if (ApplicationUtils.detectSDMounted() == false)
//		{
//			return false;
//		}
//
//		File saveFile = new File(filename);
//		File parent = saveFile.getParentFile();
//		if(parent.exists() == false)
//		{
//			if (parent.mkdirs() == false)
//			{
//				parent = null;
//				return false;
//			}
//		}
//
//		if (append == false)
//		{
//			deleteFile(filename);
//			try
//	        {
//				saveFile.createNewFile();
//			}
//	        catch (IOException e)
//	        {
//				return false;
//			}
//		}
//
//        FileOutputStream outputStream = null;
//
//        try
//        {
//        	outputStream = new FileOutputStream(saveFile, append);
//        	saveFile = null;
//        	outputStream.write(content.getBytes());
//        }
//        catch (IOException e)
//        {
//            return false;
//        }
//        try
//        {
//        	outputStream.flush();
//        	outputStream.close();
//        }
//        catch (IOException e) {}
//
//        outputStream = null;
//        return true;
//	}
	
	public static boolean copyFile(String fromFilePath, String toFilePath)
	{
		File fromFile = new File(fromFilePath);
		File toFile = new File(toFilePath);
		
		if (fromFile.exists() == false || fromFile.isFile() == false || fromFile.canRead() == false) 
		{
			return false;
		}

		if (toFile.getParentFile().exists() == false) 
		{
			toFile.getParentFile().mkdirs();
		}
		if (toFile.exists() == true) 
		{
			toFile.delete();
		}

		try 
		{
			FileInputStream input = new FileInputStream(fromFile);
			FileOutputStream output = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = input.read(bt)) > 0) 
			{
				output.write(bt, 0, c);
			}
			input.close();
			output.close();
			
			return true;
		} catch (Exception ex) { }
		
		return false;
	}
		
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static String getContentFilePath(Context context, Uri uri)
	{  
	    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
	    		DocumentsContract.isDocumentUri(context, uri))
	    {  
	        if (isExternalStorageDocument(uri)) 
	        {  
	            String docId = DocumentsContract.getDocumentId(uri);
	            String[] split = docId.split(":");
	            String type = split[0];
	            if ("primary".equalsIgnoreCase(type)) 
	            {  
	                return Environment.getExternalStorageDirectory() + "/" + split[1];
	            }  
	        }   
	        else if (isDownloadsDocument(uri)) 
	        {  
	            String id = DocumentsContract.getDocumentId(uri);
	            Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
	            return getDataColumn(context, contentUri, null, null);  
	        }  
	        else if (isMediaDocument(uri)) 
	        {  
	            String docId = DocumentsContract.getDocumentId(uri);
	            String[] split = docId.split(":");
	            String type = split[0];
	            Uri contentUri = null;
	            if ("image".equals(type)) 
	            {  
	                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
	            } 
	            else if ("video".equals(type)) 
	            {  
	                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	            } 
	            else if ("audio".equals(type)) 
	            {  
	                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	            }  
	            final String selection = "_id=?";
	            final String[] selectionArgs = new String[] { split[1] };
	            return getDataColumn(context, contentUri, selection, selectionArgs);  
	        }  
	    }   
	    else if ("content".equalsIgnoreCase(uri.getScheme())) 
	    {  
	        if (isGooglePhotosUri(uri))  
	        {
	            return uri.getLastPathSegment();  
	        }
	        return getDataColumn(context, uri, null, null);  
	    }  
	    else if ("file".equalsIgnoreCase(uri.getScheme())) 
	    {  
	        return uri.getPath();  
	    }  
	    return uri.toString();  
	} 
	
	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs)
	{  
	    Cursor cursor = null;
	    String column = "_data";
	    String[] projection = { column };
	    try 
	    {  
	        cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);  
	        if (cursor != null && cursor.moveToFirst()) 
	        {  
	            return cursor.getString(cursor.getColumnIndexOrThrow(column));  
	        }  
	    } finally 
	    {  
	        if (cursor != null)
	        {
	            cursor.close();  
	        }
	    }  
	    return null;  
	}  
	  
	private static boolean isExternalStorageDocument(Uri uri)
	{  
	    return "com.android.externalstorage.documents".equals(uri.getAuthority());  
	}  
	  
	private static boolean isDownloadsDocument(Uri uri)
	{  
	    return "com.android.providers.downloads.documents".equals(uri.getAuthority());  
	}  
	  
	private static boolean isMediaDocument(Uri uri)
	{  
	    return "com.android.providers.media.documents".equals(uri.getAuthority());  
	}  
	   
	private static boolean isGooglePhotosUri(Uri uri)
	{  
	    return "com.google.android.apps.photos.content".equals(uri.getAuthority());  
	}
}
