package cn.com.ylink.ips.core.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * <p>Title: 与文件相关操作的支持类</p>
 * <p>Description:只能使用它的静态方法</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 雁联</p>
 * @author 郭兵
 * @version 1.0
 */

public class FileUtil {
	/**
	 * Log4J Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	private FileUtil(){};
	
	public static String readFile(String dataFileName) throws Exception
	{
		File file = new File(dataFileName);
		long length = file.length();
		if (length > 50 * 1204 * 1024)
			throw new Exception("文件长度大于50M,拒绝");
		byte[] bytemem = new byte[(int) length];

		FileInputStream fis = new FileInputStream(file);
		fis.read(bytemem);
		
		String strData = new String(bytemem);
		return strData;
	}
	/**
	 * 根据给定的文件位置获取该文件的输入流
	 * @param file String 文件路径，注意可以是相对classpath的路径
	 * @return InputStream
	 */
	public static InputStream getInputStream(String file) {
		
		InputStream in = null;
		
		//如果是磁盘路径
		try
		{
			in = new FileInputStream(file);
			return in;
		}
		catch (FileNotFoundException e)
		{
		}

		//如果磁盘路径找不到，尝试从classpath中寻找
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = FileUtil.class.getClassLoader();
		}
		in = classLoader.getResourceAsStream(file);
		return in;
	}

	
	/**
	 * 复制文件
	 * @param srcFile  源文件路径
	 * @param destFile  目标文件路径
	 */
	public static void copy(String srcFile, String destFile) throws Exception
	{
		
		if (null == srcFile || 0 == srcFile.length())
		{
			logger.error("要复制的源文件为空，复制失败");
			throw new RuntimeException("要复制的源文件为空，复制失败");
		}

		if (null == destFile || 0 == destFile.length())
		{
			logger.error("要复制的目的文件为空，复制失败");
			throw new RuntimeException("要复制的目的文件为空，复制失败");
		}
		
		File fileFrom = new File(srcFile);
		File fileTo = new File(destFile);
		
		try
		{
			int byteRead = 0;
			InputStream inStream = new FileInputStream(fileFrom);
			FileOutputStream foStream = new FileOutputStream(fileTo);
			BufferedOutputStream boStream = new BufferedOutputStream(foStream);
			byte[] buffer = new byte[1440];
			while ((byteRead = inStream.read(buffer)) != -1)
			{
				boStream.write(buffer, 0, byteRead);
				boStream.flush();
			}
			inStream.close();
			foStream.close();

		}
		catch (Exception e)
		{
			logger.error("复制文件" + srcFile + "到" + destFile + "失败，" + e);
			throw e;
		}
	}

	/**
	 * 根据文件全路径获取文件名
	 * @param filePath 文件全路径
	 * @return 文件名
	 */
	public static String getFileName(String filePath)
	{
		int start = Math.max(filePath.lastIndexOf('/'), filePath.lastIndexOf('\\'));
		return filePath.substring(start+1);
	}
	
	/**
	 * 根据文件全路径获取文件内容
	 * @param filePath 文件全路径
	 * @param encoding TODO
	 * @return 文件名
	 */
	public static String getFileContent(String filePath) throws Exception
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));

		StringBuffer sb = new StringBuffer();
		String data = null;
		while((data = in.readLine())!=null)
		{
			sb.append(data + System.getProperty("line.separator"));
		}
		in.close();
		return sb.toString();
	}

	/**
	 * 删除目录，包括目录下文件
	 * @param delpath 目录路径
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void deletefile(File delpath) throws FileNotFoundException,
			IOException {
		File[] filelist = delpath.listFiles();
		if (filelist.length != 0) {
			for (int i = 0; i < filelist.length; i++) {
				if (filelist[i].isDirectory())
					deletefile(filelist[i]);
				else
					filelist[i].delete();
			}
			delpath.delete();
		} else
			delpath.delete();
	}

	 
	public static void main(String[] args)
	{
		/**
		try
		{
			FileUtil.copy("c:\\caroot.crt", "d:\\22.crt");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		**/

		File file1=new File("c:\\tttt");
		  try
		  {
		   deletefile(file1);
		  }
		  catch(Exception e)
		  {
		   e.printStackTrace();
		  }
	
	}
	
	
    /**
     * 根据需要创建文件夹
     * 
     * @param dirPath 文件夹路径
     * @param del    存在文件夹是否删除
     */
    public static void mkdir(String dirPath,boolean del) {
        File dir = new File(dirPath);
        if(dir.exists()) {
            if(del)
                dir.delete();
            else return;
        }
        dir.mkdirs();
    }
	

}
