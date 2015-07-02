package cn.backcar.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;

public class LogUtil
{
	private  final  String Tag="suiting";
	private static LogUtil _lg;
	//single 
	public static LogUtil getInstance(){
		if(_lg == null){
			_lg = new LogUtil();
		}
		return _lg;
	}
	
	//��־��������� ,Ĭ�ϱ������10�����־
	public int logSaveNum=10;
	private boolean needLog=false;
	
	/**������־*/
	public void writeLog(String msg){
	
		if(msg == null)
			return;
		//System.out.println(msg);
		if(LOG_SWITCH){
			File file = checkLogFileIsExist();
			if(file == null)
				return;
			FileOutputStream fos = null;
			try
			{
				fos = new FileOutputStream(file, true);
				fos.write((new Date().toLocaleString() + "	" + msg).getBytes("gbk"));
				fos.write("\r\n".getBytes("gbk"));
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally{
				try
				{
					if(fos != null){
						fos.close();
						fos = null;
					}
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				fos = null;
				file = null;
			}
		}
	}
	/**
	 * ��ӡ�쳣��ջ��Ϣ
	 * @param e
	 * @return
	 */
	public void WriteStackTrace(Throwable e){
        if(e != null){
            //StringWriter sw = new StringWriter();
            //PrintWriter pw = new PrintWriter(sw);
            //e.printStackTrace(pw);
            //return sw.toString();           
            writeLog(e.getStackTrace().toString());
        }
        //return "";
    }
	
	private LogUtil()
	{
		CheckLogDele();
		
	}
	
	private void CheckLogDele()
	{
		//todo ɨ��sd���������־�ļ���ɾ������logSaveNum����־
		
	}
	
	/**��־����·��*/   
	//private static final String LOG_SAVE_PATH = "/sdcard/uu/Log/"; //sd���ϵ���־�ļ�Ŀ¼
	private static final String LOG_SAVE_PATH =Environment.getExternalStorageDirectory()+ "/"+"suiting/Log/"; //sd���ϵ���־�ļ�Ŀ¼
	
	/**��־����*/
	private static final boolean LOG_SWITCH = true;		
	
	/**�����־�ļ��Ƿ����*/
	private File checkLogFileIsExist(){
		//if(!MemorySpaceManager.isSDExist()){  //sd ���Ƿ����
		//	return null;
		//}
		File file = new File(LOG_SAVE_PATH);
		if(!file.exists()){
			file.mkdirs();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		file = new File(LOG_SAVE_PATH + dateStr + ".txt");
		if(!isLogExist(file)){
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		sdf = null;
		return file;
	}
	
	/**
	 * ��鵱����־�ļ��Ƿ����
	 * @param file
	 * @return
	 */
	private boolean isLogExist(File file){
		boolean ret = false;
		try{
			File tempFile = new File(LOG_SAVE_PATH);
			File[] files = tempFile.listFiles();
			if(files==null)
				return ret;
			for(int i = 0; i < files.length; i++){
				String name = files[i].getName().trim();
				if(name!=null&&name.equalsIgnoreCase(file.getName())){
					ret = true;
					break;
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
   public  void  LogError(String msg){
	   if(needLog){
		   writeLog(msg);
		  
		   
		   
	   }
	   android.util.Log.i(Tag, msg);
   }
}