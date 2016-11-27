package com.qichen.ruida.sizeUtils;

import android.util.SparseArray;

//import com.hananapp.lehuo.Application.GakkiSettings;
//import com.hananapp.lehuo.R;

/**
 * @class Unified Image Format
 * @author LeonNoW
 * @version 1.2
 * @date 2013-8-27
 */
public class UnifiedImageFormat
	//
{
	public final static int TYPE_ALL = 0;
	public final static int TYPE_THUMB = 1;
	public final static int TYPE_VIEWER = 2;
	public final static int TYPE_AVATAR = 3;
	public final static int TYPE_PAGER = 4;
	public final static int TYPE_HTML = 5;
	
	public final static int FORMAT_JPG = 0;
	public final static int FORMAT_PNG = 1;
	
	public final static int FORCE_CLOSE = 0;
	public final static int FORCE_STROAGE = 1;
	public final static int FORCE_MEMORY = 2;
	public final static int FORCE_RESOURCE = 3;
	
	public final static int SIZE_AVATAR = 200;
	
	public final static String STROAGE_BASE = GakkiSettings.CACHE_IMAGE_STROAGE_PATH;
	public final static String STROAGE_THUMB = String.format("%1$sthumb/", STROAGE_BASE);
	public final static String STROAGE_VIEWER = String.format("%1$sviewer/", STROAGE_BASE);
	public final static String STROAGE_AVATAR = String.format("%1$savatar/", STROAGE_BASE);
	public final static String STROAGE_PAGER = String.format("%1$spager/", STROAGE_BASE);
	public final static String STROAGE_CAPTURE = String.format("%1$scapture/", STROAGE_BASE);
	public final static String STROAGE_HTML = String.format("%1$shtml/", STROAGE_BASE);

//	private final static int DEFAULT_BASE = R.drawable.no;
//	private final static int DEFAULT_THUMB = R.drawable.image_loading;
//	private final static int DEFAULT_VIEWER = R.drawable.no;
//	private final static int DEFAULT_AVATAR = R.drawable.avatar_loading;
//	private final static int DEFAULT_PAGER = R.drawable.image_loading;
//	private final static int DEFAULT_HTML = R.drawable.no;
//	private final static int FAULT_BASE = R.drawable.no;
//	private final static int FAULT_THUMB = R.drawable.no;
//	private final static int FAULT_VIEWER = R.drawable.no;
//	private final static int FAULT_AVATAR = R.drawable.no;
//	private final static int FAULT_PAGER = R.drawable.no;
//	private final static int FAULT_HTML = R.drawable.no;
//	private final static int SHIELD_BASE = R.drawable.image_loading;
	
	private ImageType _type;
	private int _force;
	
	private static SparseArray<ImageType> _all;
	
//	private static synchronized SparseArray<ImageType> getAll()
//	{
//		if (_all == null)
//		{
//			_all = new SparseArray<ImageType>();
//			_all.put(TYPE_ALL, new ImageType(TYPE_ALL, FORMAT_JPG, DEFAULT_BASE, FAULT_BASE, SHIELD_BASE, STROAGE_BASE));
//			_all.put(TYPE_THUMB, new ImageType(TYPE_THUMB, FORMAT_JPG, DEFAULT_THUMB, FAULT_THUMB, SHIELD_BASE, STROAGE_THUMB));
//			_all.put(TYPE_VIEWER, new ImageType(TYPE_VIEWER, FORMAT_JPG, DEFAULT_VIEWER, FAULT_VIEWER, SHIELD_BASE, STROAGE_VIEWER));
//			_all.put(TYPE_AVATAR, new ImageType(TYPE_AVATAR, FORMAT_JPG, DEFAULT_AVATAR, FAULT_AVATAR, SHIELD_BASE, STROAGE_AVATAR));
//			_all.put(TYPE_PAGER, new ImageType(TYPE_PAGER, FORMAT_JPG, DEFAULT_PAGER, FAULT_PAGER, SHIELD_BASE, STROAGE_PAGER));
//			_all.put(TYPE_HTML, new ImageType(TYPE_HTML, FORMAT_PNG, DEFAULT_HTML, FAULT_HTML, SHIELD_BASE, STROAGE_HTML));
//		}
//		return _all;
//	}
	
//	public static String getStroage(int type)
//	{
//		return getAll().get(type).getStroage();
//	}
//
//	public UnifiedImageFormat(int type)
//	{
//		setMember(type, FORCE_CLOSE);
//	}
//
//	public UnifiedImageFormat(int type, int force)
//	{
//		setMember(type, force);
//	}
//
//	private void setMember(int type, int force)
//	{
//		_type = getAll().get(type);
//		_force = force;
//	}
	
	public int getType()
	{
		return _type.getType();
	}
	
	public int getFormat()
	{
		return _type.getFormat();
	}
	
	public String getStroage()
	{
		return _type.getStroage();
	}
	
	public int getDefaultResource()
	{
		return _type.getDefault();
	}
	
	public int getFaultResource()
	{
		return _type.getFault();
	}
	
	public int getShieldResource()
	{
		return _type.getShield();
	}
	
	public int getForce()
	{
		return _force;
	}
	
	public static class ImageType
	{
		private int _type;
		private int _format;
		private int _default;
		private int _fault;
		private int _shield;
		private String _stroage;
		
		public int getType()
		{
			return _type;
		}
		
		public int getFormat()
		{
			return _format;
		}
		
		public int getDefault()
		{
			return _default;
		}
		
		public int getFault()
		{
			return _fault;
		}
		
		public int getShield()
		{
			return _shield;
		}
		
		public String getStroage()
		{
			return _stroage;
		}
		
		public ImageType(int type, int format, int def, int fault, int shield, String stroage)
		{
			_type = type;
			_format = format;
			_default = def;
			_fault = fault;
			_shield = shield;
			_stroage = stroage;
		}
	}
}


