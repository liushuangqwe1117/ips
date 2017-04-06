package cn.com.ylink.ips.support.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import cn.com.ylink.ips.core.exception.MsgException;
import cn.com.ylink.ips.core.util.FileUtil;


public class XMLUtil {
	
	private static Logger logger = LoggerFactory.getLogger(XMLUtil.class);
	
	/**
	 * 检验xml内容
	 * @param schemaFileName schema文件路径
	 * @param xmlContent xml内容
	 * @return 
	 * @throws CNAPS2Exception 
	 * @throws SAXException 
	 * @throws SAXException
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 */
	public static void schemaXmlValid(String schemaFileName,String xmlContent, boolean FileFlag) throws MsgException{

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"); 

		Validator validator;
		Source source;
		ByteArrayInputStream strm = null;
		String strData;
		String schemaFilePath = "";
		schemaFilePath = schemaFileName;
		try{			
			File schemaFile = new File(schemaFilePath);
			Schema schema = null;
			schema = schemaFactory.newSchema(schemaFile);
			validator = schema.newValidator();	

		}catch(Exception e){
			logger.error("读取schema配置文件失败" +schemaFilePath,e);
			throw new MsgException("读取schema配置文件失败"+schemaFilePath+e.getMessage());
		}
		try {
			if (FileFlag)
				strData = FileUtil.readFile(xmlContent);
			else
				strData = xmlContent;
			
			strm = new ByteArrayInputStream(strData.getBytes("UTF-8"));
			//报文重新构建为输入流
			source = new StreamSource(strm);
			
			validator.validate(source);
		} catch (Exception e) {
			logger.error("schema验证失败：" + xmlContent);
			String errInfo = "schema验证失败：" + e.getMessage();
			logger.error(errInfo);
			throw new MsgException(errInfo);
		} finally {
			if (strm != null) {
				try {
					strm.close();
				} catch (IOException e) {
					logger.error("关闭字节流(IO close)失败",e);
				}
			}
		}
	}
	
	public static void schemaXmlValid(Validator validator, String xmlContent, boolean FileFlag) throws MsgException{

		Source source;
		ByteArrayInputStream strm = null;
		String strData;
		try {
			if (FileFlag)
				strData = FileUtil.readFile(xmlContent);
			else
				strData = xmlContent;
			
			strm = new ByteArrayInputStream(strData.getBytes("UTF-8"));
			//报文重新构建为输入流
			source = new StreamSource(strm);
			
			validator.validate(source);
		} catch (Exception e) {
			logger.error("schema验证失败：" + xmlContent);
			String errInfo = "schema验证失败：" + e.getMessage();
			logger.error(errInfo);
			throw new MsgException(errInfo);
		} finally {
			if (strm != null) {
				try {
					strm.close();
				} catch (IOException e) {
					logger.error("关闭字节流(IO close)失败",e);
				}
			}
		}
	}
	
	/**
	 * 检验xml内容
	 * @param schemaFileName schema文件路径
	 * @param xmlContent xml内容
	 * @return 
	 * @throws CNAPS2Exception 
	 * @throws SAXException 
	 * @throws SAXException
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 */
	public static void schemaXmlValid(String schemaFileName,String xmlContent, boolean FileFlag,String encoding) throws MsgException{

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"); 

		Validator validator;
		Source source;
		ByteArrayInputStream strm = null;
		String strData;
		String schemaFilePath = "";
		schemaFilePath = schemaFileName;
		try{			
			File schemaFile = new File(schemaFilePath);
			Schema schema = null;
			schema = schemaFactory.newSchema(schemaFile);
			validator = schema.newValidator();	

		}catch(Exception e){
			logger.error("读取schema配置文件失败" +schemaFilePath,e);
			throw new MsgException("读取schema配置文件失败"+schemaFilePath+e.getMessage());
		}
		try {
			if (FileFlag)
				strData = FileUtil.readFile(xmlContent);
			else
				strData = xmlContent;
			
			strm = new ByteArrayInputStream(strData.getBytes(encoding));
			//报文重新构建为输入流
			source = new StreamSource(strm);
			
			validator.validate(source);
		} catch (Exception e) {
			logger.error("schema验证失败：" + xmlContent);
			String errInfo = "schema验证失败：" + e.getMessage();
			logger.error(errInfo);
			throw new MsgException(errInfo);
		} finally {
			if (strm != null) {
				try {
					strm.close();
				} catch (IOException e) {
					logger.error("关闭字节流(IO close)失败",e);
				}
			}
		}
	}
	
	public static void schemaXmlValid(Validator validator, String xmlContent, boolean FileFlag,String encoding) throws MsgException{

		Source source;
		ByteArrayInputStream strm = null;
		String strData;
		try {
			if (FileFlag)
				strData = FileUtil.readFile(xmlContent);
			else
				strData = xmlContent;
			
			strm = new ByteArrayInputStream(strData.getBytes(encoding));
			//报文重新构建为输入流
			source = new StreamSource(strm);
			
			validator.validate(source);
		} catch (Exception e) {
			logger.error("schema验证失败：" + xmlContent);
			String errInfo = "schema验证失败：" + e.getMessage();
			logger.error(errInfo);
			throw new MsgException(errInfo);
		} finally {
			if (strm != null) {
				try {
					strm.close();
				} catch (IOException e) {
					logger.error("关闭字节流(IO close)失败",e);
				}
			}
		}
	}
}
