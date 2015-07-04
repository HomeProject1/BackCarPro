package cn.backcar.util;

import android.os.Environment;

public class Constant {
	

	
	public static final int TYPE_EDIT = 71;
	public static final int TYPE_LIST = 72;
	public static final int TYPE_MUTILIST = 73;
	public static final int TYPE_DATA = 74;
	public static final int TYPE_EDIT_NUMBER = 75;

	public static final int FROM_CAMERA_IMAGE = 101;
	public static final int FROM_FILE_IMAGE = 102;
	public static final int Crop_IMAGE = 105;
	public static final int Choose_Image = 106;
	public static final String Intent_Name = "object";
	public static final String Intent_ValueBack = "value";
	public static final String Common_Remind = "���Ե�Ƭ��...";
	public static final String BasePath = Environment
			.getExternalStorageDirectory().toString() + "/backcar/";
	public static final String cameraBitmapPath = BasePath + "image/";
	public static final String cameraName = "camera.jpg";
	public static final String Prefreence_IsFirst = "isfirst";
	

	public static final String BaseUrl = "";
	
	
}
