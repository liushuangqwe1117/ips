/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-12-10
 */

package cn.com.ylink.ips.support.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ZhangDM(Mingly)
 * @date 2012-12-10
 * @description：FTP工具类
 */

public class FtpUtils {

	private final static Logger logger = LoggerFactory.getLogger(FtpUtils.class);

	private static Object syncObj = new Object();
	private static int connectTimeout = 60*1000;
	/**
	 * 上传文件到server.
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param path
	 * @param fileName
	 * @param input
	 * @return
	 * @throws IOException
	 * @throws FTPException
	 */
	public static boolean upload(String ip, int port, String username, String password, String path, String fileName, InputStream input) throws FTPException {
		synchronized (syncObj) {
			FTPClient ftp = new FTPClient();
			boolean bool = false;
			try {
				InetAddress addr = InetAddress.getByName(ip);
				ftp.setConnectTimeout(connectTimeout);
				ftp.connect(addr, port);
				ftp.login(username, password);
				ftp.enterLocalPassiveMode();

				if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
					ftp.disconnect();
					logger.error("ftp登录失败！");
					throw new FTPException("ftp登录失败！");
				}

				if (StringUtils.isNotEmpty(path)) {
					path = path.replace("\\", "/");
					boolean isOk = ftp.changeWorkingDirectory(path);
					if (!isOk) {
						// 创建目录
						// path = path.replace("\\", "/");
						if (path.startsWith("/")) {
							path = path.substring(1);
						}
						String[] filePaths = path.split("/");
						logger.info("创建目录数：" + filePaths.length);

						for (String pathName : filePaths) {
							isOk = ftp.changeWorkingDirectory(pathName);
							if (!isOk) {
								isOk = ftp.makeDirectory(pathName);
								if (!isOk) {
									logger.error("创建目录失败！");
									throw new FTPException("创建目录失败！");
								}

								isOk = ftp.changeWorkingDirectory(pathName);
								if (!isOk) {
									logger.error("更换目录失败！");
									throw new FTPException("更换目录失败！");
								}
							}

						}
					}
				}
				ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
				bool = ftp.storeFile(fileName, input);

			} catch (Exception e) {
				bool = false;
				logger.error(e.getMessage(), e);
				throw new FTPException(e.getMessage());
			} finally {
				IOUtils.closeQuietly(input);
				closeFtp(ftp);
			}
			return bool;
		}
	}

	public static boolean upload(String ip, int port, String username, String password, String path, String fileName, String localPath, String localFileName) throws IOException, FTPException {
		InputStream in = null;
		boolean  bool = false;
		try{
			in = new FileInputStream(new File(localPath + File.separator + localFileName));
			bool = upload(ip, port, username, password, path, fileName, in);
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage(), e);
		}finally{
			if(in != null) in.close();
		}
		return bool;
	}

	public static void downloadFromWindow(String ip, int port, String username, String password, String path, String fileName, String localPath, String localFileName) throws FTPException {
		download(ip, port, username, password, path, fileName, localPath, localFileName, false);
	}

	public static void download(String ip, int port, String username, String password, String path, String fileName, String localPath, String localFileName) throws FTPException {
		download(ip, port, username, password, path, fileName, localPath, localFileName, true);
	}

	private static void download(String ip, int port, String username, String password, String path, String fileName, String localPath, String localFileName, boolean setFileType) throws FTPException {
		synchronized (syncObj) {
			path = path.replace("\\", "/");
			// 去掉第一个斜杠
			if (StringUtils.isNotBlank(path)) {
				if (path.indexOf("/") == 0) {
					path = path.substring(1);
				}
			}
			System.out.println("file:" + path + "/" + fileName);
			FTPClient ftp = new FTPClient();
			try {
				InetAddress addr = InetAddress.getByName(ip);
				ftp.setConnectTimeout(connectTimeout);
				ftp.connect(addr, port);
				ftp.login(username, password);
				ftp.enterLocalPassiveMode();

				if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
					ftp.disconnect();

					System.out.println("ftp登录失败！");

					logger.error("ftp登录失败！");
					throw new FTPException("ftp登录失败！");
				}

				if (StringUtils.isNotEmpty(path)) {
					ftp.changeWorkingDirectory(path);
				}
				if (setFileType) {
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
				}
				InputStream in = ftp.retrieveFileStream(fileName);

				if (in == null) {
					throw new FTPException("打开远程文件失败: " + fileName);
				}

				File localDir = new File(localPath);
				if (!localDir.exists()) {
					localDir.mkdirs();
				}

				File outFile = new File(localPath + File.separator + localFileName);
				outFile.delete();
				outFile.createNewFile();
				OutputStream os = new FileOutputStream(outFile);

				byte[] buffer = new byte[1024 * 100];
				while (true) {
					int count = in.read(buffer);
					if (count < 0)
						break;
					os.write(buffer, 0, count);
				}
				os.close();

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new FTPException(e.getMessage());
			} finally {
				closeFtp(ftp);
			}
		}
	}

	public static byte[] getFile(String ip, int port, String username, String password, String path, String fileName) throws FTPException {
		FTPClient ftp = new FTPClient();
		InputStream input = null;

		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.setConnectTimeout(connectTimeout);
			ftp.connect(addr, port);
			ftp.login(username, password);
			ftp.enterLocalPassiveMode();

			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();

				logger.error("ftp登录失败！");
				throw new FTPException("ftp登录失败！");
			}

			if (StringUtils.isNotEmpty(path)) {
				ftp.changeWorkingDirectory(path);
			}

			return IOUtils.toByteArray(ftp.retrieveFileStream(fileName));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FTPException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(input);
			closeFtp(ftp);
		}
	}

	public static long getFileSize(String ip, int port, String username, String password, String path, String fileName) throws FTPException {
		FTPClient ftp = new FTPClient();
		InputStream input = null;

		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.setConnectTimeout(connectTimeout);
			ftp.connect(addr, port);
			ftp.login(username, password);
			ftp.enterLocalPassiveMode();

			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();

				logger.error("ftp登录失败！");
				throw new FTPException("ftp登录失败！");
			}

			if (StringUtils.isNotEmpty(path)) {
				ftp.changeWorkingDirectory(path);
			}

			return ftp.retrieveFileStream(fileName).available();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FTPException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(input);
			closeFtp(ftp);
		}
	}

	/**
	 * 判断文件是否存在 0：文件不存在 非0则表示文件存在
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param pathName
	 * @return
	 * @throws FTPException
	 */
	public static int getFileExists(String ip, int port, String username, String password, String path, String fileName) throws FTPException {

		FTPClient ftp = new FTPClient();
		FTPFile[] ftpFiles = null;
		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.setConnectTimeout(connectTimeout);
			ftp.connect(addr, port);
			ftp.login(username, password);
			ftp.enterLocalPassiveMode();

			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();
				logger.error("ftp登录失败！");
				throw new FTPException("ftp登录失败！");
			}
			if (StringUtils.isNotEmpty(path)) {
				path = path.replace("\\", "/");
				boolean isOk = ftp.changeWorkingDirectory(path);
				if (!isOk) {
					// 创建目录
					if (path.startsWith("/")) {
						path = path.substring(1);
					}
					String[] filePaths = path.split("/");
					logger.info("创建目录数：" + filePaths.length);

					for (String pathName : filePaths) {
						isOk = ftp.changeWorkingDirectory(pathName);
						if (!isOk) {
							isOk = ftp.makeDirectory(pathName);
							if (!isOk) {
								logger.error("创建目录失败！");
								throw new FTPException("创建目录失败！");
							}

							isOk = ftp.changeWorkingDirectory(pathName);
							if (!isOk) {
								logger.error("更换目录失败！");
								throw new FTPException("更换目录失败！");
							}
						}

					}
					return 0;
				}
				ftp.changeWorkingDirectory(path);
				ftpFiles = ftp.listFiles(fileName);
				if (ftpFiles == null) {
					return 0; // 0表示不存在。
				} else {
					return ftpFiles.length; // 大小就是文件数目。
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FTPException(e.getMessage());
		} finally {
			closeFtp(ftp);
		}
		return 0;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param pathName
	 * @return
	 * @throws FTPException
	 */
	public static boolean deleteFile(String ip, int port, String username, String password, String pathName) throws FTPException {
		FTPClient ftp = new FTPClient();
		InputStream input = null;

		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.setConnectTimeout(connectTimeout);
			ftp.connect(addr, port);
			ftp.login(username, password);

			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();

				logger.error("ftp登录失败！");
				throw new FTPException("ftp登录失败！");
			}

			return ftp.deleteFile(pathName);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new FTPException(e.getMessage());
		} finally {
			IOUtils.closeQuietly(input);
			closeFtp(ftp);
		}
	}

	/**
	 * 删除指定路径中最后一个目录
	 * 
	 * @param ip
	 * @param port
	 * @param username
	 * @param password
	 * @param pathName
	 * @return
	 */
	public static int deleteDir(String ip, int port, String username, String password, String pathName) {
		FTPClient ftp = new FTPClient();
		InputStream input = null;

		try {
			InetAddress addr = InetAddress.getByName(ip);
			ftp.setConnectTimeout(connectTimeout);
			ftp.connect(addr, port);
			ftp.login(username, password);

			if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
				ftp.disconnect();

				return 0;
			}

			return ftp.rmd(pathName);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
			closeFtp(ftp);
		}
	}

	/**
	 * 关闭.
	 * 
	 * @param input
	 * @param ftp
	 */
	private static void closeFtp(FTPClient ftp) {
		try {
			if (ftp != null) {
				ftp.logout();
				ftp.disconnect();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private static void testUpload(){
		try {
			upload("168.33.130.68", 21, "root", "root", "/testupload", "aaaa.txt", "d:\\", "ie_bookmark.htm");
		} catch (FTPException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		testUpload();
	}
	
	public static void main12(String[] args) {
		/*
		 * ByteInputStream input = new ByteInputStream(); try {
		 * input.read("aaaaaaaaaaaaaa".getBytes()); upload("172.168.9.172", 21,
		 * "root", "root1234", "mypath1\\mypath2", "test123.txt", input); }
		 * catch (Exception e) { e.printStackTrace(); }
		 */
		/*
		 * String path = "mypath1\\mypath2\\mypath3"; path = path.replace("\\",
		 * "/"); System.out.println(path.split("/").length);
		 */

		try {
			String ftpFilePath = "bank" + File.separator + "check" + File.separator + "pull" + File.separator + "100002500000/201402/";
			System.out.println(ftpFilePath);
			String fileName = "123.txt";
			String tmpFileName = "123.txt";
			try {
				int i = FtpUtils.getFileExists("168.33.98.96", 21, "root", "root", ftpFilePath, fileName);
				System.out.println("i=" + i);
			} catch (FTPException e) {
				String errInfo = "下载对账文件[" + ftpFilePath + fileName + "]失败，" + e.getMessage();
				System.out.println(errInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public static void main(String[] args) { try{
	 * FtpUtils.download("172.168.9.172", 21, "root", "root1234",
	 * "/back/mrcht/2000000000016/201310/",
	 * "TaxDateList_200000000016_20131023_1.txt", "e:/test/test/", "a5.txt");
	 * }catch(Throwable t){ t.printStackTrace(); } }
	 */
	public static void main11(String[] args) {
		try {
			FtpUtils.download("172.168.9.111", 21, "anonymous", "", "", "SHOP.105584093110051.20131112.02.fail.txt.gz", "e:\\", "a5.gz");
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

}
