package com.rongzhiheng.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

/**
 * 异步线程加载图片工具类 使用说明： BitmapManager bmpManager; bmpManager = new
 * BitmapManager(BitmapFactory.decodeResource(context.getResources(),
 * R.drawable.loading)); bmpManager.loadBitmap(imageURL, imageView);
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-6-25
 */
public class BitmapManager {

	private static HashMap<String, SoftReference<Bitmap>> cache;
	private static ExecutorService pool;
	private static Map<ImageView, String> imageViews;
	private Bitmap defaultBmp;
	int what = -1;
	static {
		cache = new HashMap<String, SoftReference<Bitmap>>();
		pool = Executors.newFixedThreadPool(5); // 固定线程池
		imageViews = Collections
				.synchronizedMap(new WeakHashMap<ImageView, String>());
	}

	public BitmapManager() {
	}

	public BitmapManager(Bitmap def) {
		this.defaultBmp = def;
	}

	/**
	 * 设置默认图片
	 * 
	 * @param bmp
	 */
	public void setDefaultBmp(Bitmap bmp) {
		defaultBmp = bmp;
	}

	/**
	 * 加载图片
	 * 
	 * @param url
	 * @param imageView
	 */
	public void loadBitmap(String url, ImageView imageView) {
		loadBitmap(url, imageView, this.defaultBmp, 0, 0);
	}

	/**
	 * 加载图片-可设置加载失败后显示的默认图片
	 * 
	 * @param url
	 * @param imageView
	 * @param defaultBmp
	 */
	public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp) {
		loadBitmap(url, imageView, defaultBmp, 0, 0);
	}

	/**
	 * 加载图片-可指定显示图片的高宽
	 * 
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 */
	public void loadBitmap(String url, ImageView imageView, Bitmap defaultBmp,
			int width, int height) {
		imageViews.put(imageView, url);
		Bitmap bitmap = getBitmapFromCache(url);

		if (bitmap != null) {
			System.out.println("bitmap!=null");
			// 显示缓存图片
			imageView.setImageBitmap(bitmap);
		} else {
			// 加载SD卡中的图片缓存
			String filename = FileUtils.getFileName(url);
			String filepath = imageView.getContext().getFilesDir()
					+ File.separator + filename;
			File file = new File(filepath); 
			if (file.exists()) { 
				// 显示SD卡中的图片缓存
				Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(),
						filename);
				imageView.setImageBitmap(bmp);
			} else { 
				// 线程加载网络图片
				imageView.setImageBitmap(defaultBmp);
				queueJob(url, imageView, width, height);
			}
		}
	}

	/**
	 * 加载图片-可指定显示图片的高宽
	 * 
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 */
	public Bitmap loadBitmap_return(String url, ImageView imageView,
			Bitmap defaultBmp, int width, int height) {
		imageViews.put(imageView, url);
		Bitmap bitmap = getBitmapFromCache(url);
		if (bitmap != null) {
			// 显示缓存图片
			return bitmap;
		} else {
			// 加载SD卡中的图片缓存
			String filename = FileUtils.getFileName(url);
			String filepath = imageView.getContext().getFilesDir()
					+ File.separator + filename;
			File file = new File(filepath);
			if (file.exists()) {
				// 显示SD卡中的图片缓存
				Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(),
						filename);
				imageView.setImageBitmap(bmp);
			} else {
				// 线程加载网络图片
				queueJob(url, imageView, width, height);
				return defaultBmp;
			}
		}
		return defaultBmp;
	}

	/**
	 * 从缓存中获取图片
	 * 
	 * @param url
	 */
	public Bitmap getBitmapFromCache(String url) {
		Bitmap bitmap = null;
		if (cache.containsKey(url)) {
			bitmap = cache.get(url).get();
		}
		return bitmap;
	}

	/**
	 * 从网络中加载图片
	 * 
	 * @param url
	 * @param imageView
	 * @param width
	 * @param height
	 */
	public void queueJob(final String url, final ImageView imageView,
			final int width, final int height) {
		/* Create handler in UI thread. */
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				String tag = imageViews.get(imageView); 
				if (tag != null && tag.equals(url)) {
					if (msg.obj != null) {
						imageView.setImageBitmap((Bitmap) msg.obj);
						try {
							// 向SD卡中写入图片缓存
							ImageUtils.saveImage(imageView.getContext(),
									FileUtils.getFileName(url),
									(Bitmap) msg.obj);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};

		pool.execute(new Runnable() {
			public void run() {
				Message message = Message.obtain();
				message.obj = downloadBitmap(url, width, height); 
				handler.sendMessage(message);
			}
		});
	}

	/**
	 * 下载图片-可指定显示图片的高宽
	 * 
	 * @param url
	 * @param width
	 * @param height
	 */
	private Bitmap downloadBitmap(String url, int width, int height) {
		Bitmap bitmap = null;
		System.out.println("downloadBitmap--" + url);
		try {
			// http加载图片
			bitmap = Client.getNetBitmap(url);
			if (width > 0 && height > 0) {
				// 指定显示图片的高宽
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			}

			// 放入缓存
			cache.put(url, new SoftReference<Bitmap>(bitmap));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	public void loadBitmap(String str, String url, ImageView imageView,
			Bitmap decodeResource) {
		// TODO Auto-generated method stub
		loadBitmap(str, url, imageView, defaultBmp, 0, 0);
	}

	private void loadBitmap(String str, String url, ImageView imageView,
			Bitmap defaultBmp, int width, int height) {
		// TODO Auto-generated method stub
		imageViews.put(imageView, url);
		Bitmap bitmap = getBitmapFromCache(url);

		if (bitmap != null) {
			System.out.println("loadBitmap111111111111111111");
			// 显示缓存图片
			imageView.setImageBitmap(bitmap);
		} else {
			// 加载SD卡中的图片缓存
			String filename = FileUtils.getFileName(url);
			String filepath = imageView.getContext().getFilesDir()
					+ File.separator + filename;
			File file = new File(filepath);
			System.out.println("bitmap==null" + filename);
			System.out.println("bitmap==nullpath" + filepath);
			if (file.exists()) {
				System.out.println("loadBitmap----true----" + file.exists());
				// 显示SD卡中的图片缓存
				Bitmap bmp = ImageUtils.getBitmap(imageView.getContext(),
						filename);
				imageView.setImageBitmap(bmp);
			} else {
				System.out.println("loadBitmap------false----" + file.exists());
				// 线程加载网络图片
				imageView.setImageBitmap(defaultBmp);
				queueJob(str, url, imageView, width, height);
			}
		}

	}

	private void queueJob(final String str, final String url,
			final ImageView imageView, final int width, final int height) {
		/* Create handler in UI thread. */
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				// String tag = imageViews.get(imageView);
				// System.out.println("tag----------" + tag);
				System.out.println("queueJob-----" + url);
				// if (tag != null && tag.equals(url)) {
				if (msg.obj != null) {
					imageView.setImageBitmap((Bitmap) msg.obj);
					try {
						// 向SD卡中写入图片缓存
						ImageUtils.saveImage(imageView.getContext(),
								FileUtils.getFileName(url), (Bitmap) msg.obj);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// }
				}
			}
		};

		pool.execute(new Runnable() {
			public void run() {
				Message message = Message.obtain();
				message.obj = downloadBitmap(str, url, width, height);
				System.out.println("queueJob---run");
				if (message.obj != null) {
					if (str.equals("B")) {
						System.out.println("b");
						what = 0;
						handle.sendEmptyMessage(what);
					}
				}
				handler.sendMessage(message);
			}
		});
	}

	Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				System.out.println("handle----" + msg.what);
				// System.out.println(BView.b_adapter.getCount());
				break;
			}
		}
	};

	private Bitmap downloadBitmap(String str, String url, int width, int height) {
		Bitmap bitmap = null;
		System.out.println("downloadBitmap--" + url);
		try {
			// http加载图片
			bitmap = Client.getNetBitmap(url);
			if (width > 0 && height > 0) {
				// 指定显示图片的高宽
				bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
			}
			// 放入缓存
			cache.put(url, new SoftReference<Bitmap>(bitmap));
			System.out.println("downloadBitmap");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	public static Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL
					.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			// conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return bitmap;

	}
}