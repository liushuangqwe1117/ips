package cn.com.ylink.ips.support.xml;

/**
 * <p>Title: </p>
 * <p>Description:DOM4j</p>
 * <p>Copyright: Copyright (c) 2010.12</p>
 * <p>Company: 雁联</p>
 * @author aps-yyx
 * @version 1.0
 */

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.xpath.DefaultXPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import cn.com.ylink.ips.core.exception.MsgException;
import cn.com.ylink.ips.core.util.AssertUtil;


public class Dom4jXMLMessage {
	private static final Logger logger = LoggerFactory.getLogger(Dom4jXMLMessage.class);
	private Document xmlDoc;
	private Element root;

	// 啥时候关闭流
	public static Dom4jXMLMessage parse(byte[] msg) throws MsgException
	{
		try
		{
			SAXReader reader = new SAXReader();
		//	reader.setEncoding("UTF-8");
			
	/*		EntityResolver resolver = new EntityResolver() {  
	            public InputSource resolveEntity(String publicId, String systemId) {  
	                InputStream in = this.getClass().getResourceAsStream(  
	                        "/org/soft/resource/100.xsd");  
	                return new InputSource(in);  
	            }  
	        };
	        
	        reader.setValidation(true);
	        reader.setEntityResolver(resolver);  
	        reader.setFeature("http://xml.org/sax/features/validation", true);  
	        reader.setFeature("http://apache.org/xml/features/validation/schema", true);  
	        reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
	*/
			ByteArrayInputStream bis = new ByteArrayInputStream(msg);
			Document xmlDoc = reader.read(bis);
			return makedom4j(xmlDoc);
		}
		catch (Exception e)
		{
			throw new MsgException("解析XML报文错误:"+e.getMessage());
		}
	}

	public static Dom4jXMLMessage parse(ByteArrayInputStream bis) throws MsgException
	{
		try
		{
			SAXReader reader = new SAXReader();
			Document xmlDoc = reader.read(bis);
			return makedom4j(xmlDoc);
		}
		catch (Exception e)
		{
			throw new MsgException("解析XML报文错误:"+e.getMessage());
		}
	}

	// File f = new File("src/example/xml2/3.xml");
	public static Dom4jXMLMessage parse(File file) throws MsgException
	{
		try
		{
			SAXReader reader = new SAXReader();
		//	reader.setEncoding("UTF-8");
			
			Document xmlDoc = reader.read(file);
			return makedom4j(xmlDoc);
		}
		catch (Exception e)
		{
			throw new MsgException("解析XML报文错误:"+e.getMessage());
		}
	}

	private static Dom4jXMLMessage makedom4j(Document xmlDoc) throws MsgException
	{
		try
		{
			Element root = xmlDoc.getRootElement();
			Dom4jXMLMessage result = new Dom4jXMLMessage();
			result.xmlDoc = xmlDoc;
			result.root = root;
			return result;
		}
		catch (Exception e)
		{
			throw new MsgException("解析XML报文错误:"+e.getMessage());
		}

	}
	
	/**
	 * 检验xml内容
	 * @param schemaFilePath schema文件路径
	 * @param xmlContent xml内容
	 * @return 
	 * @throws MsgException 
	 * @throws SAXException 
	 * @throws SAXException
	 * @throws UnsupportedEncodingException 
	 * @throws IOException
	 */
	/*public static void schemaXmlValid(String schemaFileName,String xmlContent, boolean FileFlag) throws MsgException{

		SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema"); 

		Validator validator;
		Source source;
		ByteArrayInputStream strm = null;
		String strData;
		String schemaFilePath = "";
		schemaFilePath = schemaFileName;
		try{			
			File schemaFile = new File(schemaFilePath);
			//File schemaFile = new File("D:\\schema\\ccms.303.001.02");
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
			throw new MsgException("schema验证失败"+e.getMessage());
		} finally {
			if (strm != null) {
				try {
					strm.close();
				} catch (IOException e) {
					logger.error("关闭字节流(IO close)失败",e);
				}
			}
		}
	}*/

	public Node getNode(String xpath)
	{
		Node nameNode = xmlDoc.selectSingleNode(xpath);
		return nameNode;
	}

	public Node getNode(Node node, String childPath)
	{
		XPath path = new DefaultXPath(childPath);
		Node nameNode = node.selectSingleNode(childPath);
		return nameNode;
	}

	public String getNodeText(Node node, String childPath)
	{
		Node nameNode = getNode(node, childPath);
		if (nameNode==null)
			return "";
		return nameNode.getText().trim();
	}
	public String getNodeText(Node node, String childPath,int minLen,int maxLen) throws MsgException
	{
		String noteText = getNodeText(node, childPath);

		if (noteText.getBytes().length >=minLen && noteText.getBytes().length <=maxLen)
			return noteText;
		else
			throw new MsgException("9303","XML报文["+childPath+"]域的长度范围应该在["+minLen+","+maxLen+"]之间");
	}
	//charSet="N"数字 "X"数字/字母 "G"数字/字母/汉字 "D"8位日期 "T"6位时间 "DT"14位日期时间
	//option="O" "M"
	public String getNodeText(Node node, String childPath,int minLen,int maxLen,String charSet) throws MsgException
	{
		String noteText = getNodeText(node,childPath);//,minLen,maxLen);
		AssertUtil.checkData(noteText,"XML报文"+childPath,minLen,maxLen,charSet,"M");
		return noteText;
	}
	//charSet="N"数字 "X"数字/字母 "G"数字/字母/汉字 "D"8位日期 "T"6位时间 "DT"14位日期时间
	public String getNodeText(Node node, String childPath,int minLen,int maxLen,String charSet,String option) throws MsgException
	{
		String noteText = getNodeText(node,childPath);//,minLen,maxLen);
		AssertUtil.checkData(noteText,"XML报文["+childPath+"]域",minLen,maxLen,charSet,option);
		//增加对字符集的判断
		return noteText;
	}
	//直接XPATH
	public String getNodeText(String xpath)
	{
		Node nameNode = xmlDoc.selectSingleNode(xpath);
		if (nameNode==null)
			return "";
		return nameNode.getText().trim();
	}
	public String getNodeText(String xpath,int minLen,int maxLen) throws MsgException
	{
		String noteText = getNodeText(xpath);

		if (noteText.getBytes().length >=minLen && noteText.getBytes().length <=maxLen)
			return noteText;
		else
			throw new MsgException("9303","XML报文["+xpath+"]域的长度范围应该在["+minLen+","+maxLen+"]之间");
	}
	public String getNodeText(String xpath,int minLen,int maxLen,String charSet,String option) throws MsgException
	{
		String noteText = getNodeText(xpath);
		AssertUtil.checkData(noteText,"XML报文["+xpath+"]域",minLen,maxLen,charSet,option);
		//增加对字符集的判断
		return noteText;
	}

	public Integer getFieldIntValue(String xpath)
	{
		Node nameNode = xmlDoc.selectSingleNode(xpath);
		String value = nameNode.getText();
		return Integer.valueOf(value);
	}

	public Long getFieldLongValue(String xpath)
	{
		Node nameNode = xmlDoc.selectSingleNode(xpath);
		String value = nameNode.getText();
		return Long.valueOf(value);
	}

	public Double getNodeDoubleValue(String xpath)
	{
		Node nameNode = xmlDoc.selectSingleNode(xpath);
		String value = nameNode.getText();
		return Double.valueOf(value);
	}

	public Date getNodeDateValue(String xpath, String dateFormat) throws ParseException
	{
		Node nameNode = xmlDoc.selectSingleNode(xpath);
		String value = nameNode.getText();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.parse(value);
	}

	public List<Node> getNodeList(String xpath)
	{
		List<Node> list = xmlDoc.selectNodes(xpath);
		return list;
	}
	
	public static Dom4jXMLMessage createDom4jXMLMessage(String rootName,String encoding,String schemaFileName)
	{
		Dom4jXMLMessage dom4jxml = new Dom4jXMLMessage();
		dom4jxml.xmlDoc = DocumentHelper.createDocument();
		dom4jxml.root = dom4jxml.xmlDoc.addElement(rootName);
		dom4jxml.root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		dom4jxml.root.addAttribute("xsi:noNamespaceSchemaLocation", schemaFileName);
		dom4jxml.xmlDoc.setXMLEncoding(encoding);
		return dom4jxml;
	}

	// //////////////////////////////构建XML
	public static Dom4jXMLMessage createDom4jXMLMessage(String rootName,String schemaFileName)
	{
		Dom4jXMLMessage dom4jxml = new Dom4jXMLMessage();
		dom4jxml.xmlDoc = DocumentHelper.createDocument();
		dom4jxml.root = dom4jxml.xmlDoc.addElement(rootName);
		dom4jxml.root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		dom4jxml.root.addAttribute("xsi:noNamespaceSchemaLocation", schemaFileName);
		dom4jxml.xmlDoc.setXMLEncoding("UTF-8");
		return dom4jxml;
	}
	
	public static Dom4jXMLMessage createDom4jXMLMessage(String rootName)
	{
		Dom4jXMLMessage dom4jxml = new Dom4jXMLMessage();
		dom4jxml.xmlDoc = DocumentHelper.createDocument();
		dom4jxml.root = dom4jxml.xmlDoc.addElement(rootName);
		dom4jxml.xmlDoc.setXMLEncoding("UTF-8");
		return dom4jxml;
	}
	public static Dom4jXMLMessage createDom4jXMLMessageForIcbc(String rootName,String encoding )
	{
		Dom4jXMLMessage dom4jxml = new Dom4jXMLMessage();
		dom4jxml.xmlDoc = DocumentHelper.createDocument();
		dom4jxml.root = dom4jxml.xmlDoc.addElement(rootName);
		dom4jxml.xmlDoc.setXMLEncoding(encoding);
		return dom4jxml;
	}
	public void setEncoding(String encoding)
	{
		xmlDoc.setXMLEncoding(encoding);
	}
	public Element addNode(Element parent, String nodeName, String nodeValue)
	{
		Element newNode = parent.addElement(nodeName);
		if(nodeValue==null){
			nodeValue = "";
		}
		newNode.setText(nodeValue);
		return newNode;
	}
	
	public Element addCDataNode(Element parent, String nodeName, String nodeValue)
	{
		Element newNode = parent.addElement(nodeName);
		if(nodeValue==null){
			nodeValue = "";
		}
		newNode.addCDATA(nodeValue);
		return newNode;
	}

	public String toString()
	{

		return xmlDoc.asXML();
	}
	
	public void toFormatString(String charset, boolean istrans){
		OutputFormat outFmt = null;
		XMLWriter xmlWriter = null;
		try { 
			outFmt = new OutputFormat(" ",true, charset); 
			xmlWriter = new XMLWriter(outFmt);
 			xmlWriter.setEscapeText(istrans);
 			xmlWriter.write(xmlDoc);
 			xmlWriter.close();
         } catch (IOException e) { 
                 System.out.println("格式化XML文档发生异常，请检查！"); 
                 e.printStackTrace(); 
         } 
	}
	//"UTF-8"
	public void printFormatString(String encoding)
	{
		try
		{
			//OutputFormat outFmt = OutputFormat.createPrettyPrint();
			//outFmt.setEncoding("UTF-8");
			//outFmt.setIndent(" ");
			OutputFormat outFmt=new OutputFormat(" ",true,encoding); 

			XMLWriter xmlWriter = new XMLWriter(outFmt);
			xmlWriter.write(xmlDoc);
			xmlWriter.close();
		}
		catch (Exception e)
		{

		}
	}

	

	public Document getXmlDoc()
	{
		return xmlDoc;
	}

	public void setXmlDoc(Document xmlDoc)
	{
		this.xmlDoc = xmlDoc;
	}

	public Element getRoot()
	{
		return root;
	}

	public void setRoot(Element root)
	{
		this.root = root;
	}
	
	public static void main(String[] args) throws Exception
	{
		String xmlStr = "<Cmd>" +
				           "<Type><![CDATA[<>/dasd2dd]]></Type>" +
				           "<Cont>bcd</Cont>" +
				           "</Cmd>";
		Dom4jXMLMessage dom4jxml = Dom4jXMLMessage.parse(xmlStr.getBytes());
		System.out.println(dom4jxml.getNodeText("/Cmd/Type"));
		System.out.println(dom4jxml.toString());
		dom4jxml.toFormatString("GBK",  true);

//		Node node = dom4jxml.getNode("/Cmd");
//		System.out.println(dom4jxml.getNodeText(node, "./Cont"));
		// //////
//		Dom4jXMLMessage xml2 = Dom4jXMLMessage.createDom4jXMLMessage("root");
//		xml2.addNode(xml2.root, "nodeName", "nodeValue");
//		System.out.println(xml2.toString());

	}
}
