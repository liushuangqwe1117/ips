/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-10-22
 */

package cn.com.ylink.ips.support.xml;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-10-22
 * @description：XML解析工具类
 */

public class Dom4jUtils {
	/**
	 * 创建根元素
	 * @param rootName
	 * @param xmlEncoding
	 * @return
	 */
	public static Document createDocument(String xmlEncoding){
		
		Document document = DocumentHelper.createDocument();
		document.setXMLEncoding(xmlEncoding);
		
		return document;
	}
	
	/**
	 * 创建元素
	 * @param elementName
	 * @return
	 */
	public static Element createElement(String elementName){
		
		Element element = DocumentHelper.createElement(elementName);
		
		return element;
	}
	
	
	/**
	 * 创建元素,并添加文本子节点
	 * @param elementName
	 * @return
	 */
	public static Element createElement(String elementName,String value){
		
		Element element = DocumentHelper.createElement(elementName);
		element.setText(value);
		
		return element;
	}
	
	/**
	 * 创建元素,并添加文本子节点
	 * @param elementName
	 * @return
	 */
	public static Element createCDataElement(String elementName,String value){
		
		Element element = DocumentHelper.createElement(elementName);
		element.addCDATA(value);
		
		return element;
	}
	
	/**
	 * 添加子元素
	 * @param elementName
	 * @return
	 */
	public static void addElement(Document document,Element childElement){
		document.add(childElement);
	}
	
	
	/**
	 * 添加子元素
	 * @param elementName
	 * @return
	 */
	public static Element addElement(Element parentElement,Element childElement){
		
		parentElement.add(childElement);
		
		 return parentElement;
	}
	
	/**
	 * 添加子元素,和子元素的文本节点
	 * @param elementName
	 * @return
	 */
	public static Element addElement(Element parentElement,String elementName, String value){
		
		  if(StringUtils.isEmpty(value)){
			  value="";
		  }
		
		  return addElement(parentElement, createElement(elementName,value));
	}
	
	public static Element addCDataElement(Element parentElement,String elementName, String value){
		
		  if(StringUtils.isEmpty(value)){
			  value="";
		  }
		
		  return addElement(parentElement, createCDataElement(elementName,value));
	}
	
	
	/**
	 *  将列表转换为element并添加到父元素中
	 * @param parentElement
	 * @param items
	 * @param directParentName
	 * @param itemName
	 */
	public static Element addElement(Element parentElement,List<String> items,String directParentName,String itemName){
		if(items==null){
			throw new NullPointerException("items can not be null");
		}
		if(StringUtils.isEmpty(directParentName)){
			throw new NullPointerException("directParentName can not be null");
		}
		if(StringUtils.isEmpty(directParentName)){
			throw new NullPointerException("itemName can not be null");
		}
		Element directParent = Dom4jUtils.createElement(directParentName);
		for (String val : items) {
			Element el = Dom4jUtils.createElement(itemName, val);
			Dom4jUtils.addElement(directParent,el);
		}
		return addElement(parentElement, directParent);
	}
	
	/**
	 * 生成XML
	 * @param document
	 * @return
	 */
	public static String asXml(Document document){
		
		return document.asXML();
	}
	
	/**
	 * 解析用
	 * @param xml
	 * @return
	 * @throws Exception 
	 */
	public static Document parse(String xml) throws Exception{
		
		return DocumentHelper.parseText(xml);
	}
	
	
	/**
	 * 获取节点的值
	 * @param document
	 * @param xpath
	 * @return
	 */
	public static String getValue(Document document, String xpath){
		
		Node node = document.selectSingleNode(xpath);
		
		return (null==node)?null:node.getText();
		
	}
	
	
	/**
	 * 从给定的元素中寻找文本节点的值
	 * @param element
	 * @param nodeName
	 * @return String
	 */
	public static String getValue(Element element, String nodeName){
		Element node = element.element(nodeName);
		if(node==null||!node.isTextOnly()){
			return "";
		}else{//文本节点
			return node.getText().trim();
		}
		
	}
	
	
	/**
	 * 获取重复的节点
	 * @param document
	 * @param xpath
	 * @return
	 */
	public static List<Node> getNodeList(Document document, String xpath){
		
		List<Node> nodes = document.selectNodes(xpath);
		return (null==nodes)?null:nodes;
	}
	
	/**
	 * 获取节点子节点的文本的值
	 * @param document
	 * @param xpath
	 * @return
	 */
	public static String getNodeValue(Node node, String subNodeName){
		
		Node valueNode = node.selectSingleNode(node.getUniquePath()+"/"+subNodeName);
		
		return (null==valueNode)?null:valueNode.getText();
	}
	
	
	/**
	 * 调试用
	 * @param document
	 */
	public static void print(Document document){
		try{
			OutputFormat format = OutputFormat.createPrettyPrint();
		    XMLWriter writer = new XMLWriter(System.out, format );
		    writer.write(document);
		    writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 格式化输出
	 * @param document
	 */
	public static String formatXml(String xml){
		try{
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("GBK");
			
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		    XMLWriter writer = new XMLWriter(byteArrayOutputStream, format);
		    writer.write(Dom4jUtils.parse(xml));
		    String xmlFormat = byteArrayOutputStream.toString("GBK");
		    writer.close();
		    return xmlFormat;
		    
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
