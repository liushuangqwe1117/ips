/**
 * 版权所有(C) 2014 深圳市雁联计算系统有限公司
 * 创建:Administrator 2014-12-3
 */

package cn.com.ylink.ips.support.socket;

/** 
 * @author Administrator
 * @date 2014-12-3
 * @description：TODO
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.net.SocketException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.com.ylink.ips.core.util.StringUtil;

public class SocketClient {
	private final static Logger logger = LoggerFactory.getLogger(SocketClient.class);
    private String sendMsg = null;    //输出字节
    private int msgLength = 0;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private Socket sock = null;             //Socket 连接
    
    public SocketClient(String strIP, int iPort) throws Exception{
        try{
            sock = new Socket(strIP, iPort);
            sock.setSoTimeout(60 * 1000);
            if(getConnectStream() != 0) throw new SocketException();
        }catch(Exception e){
        	System.out.println("不能绑定该IP：" + strIP + "和端口：" + iPort);
        	logger.error("不能绑定该IP：" + strIP + "和端口：" + iPort + "错误信息：" + e.getMessage());
        	throw new Exception(e.getMessage());
        }
    }
    
//    public static boolean checkSocket(Socket sock){
//        if(sock == null)return false;
//        try {
//            java.io.DataInputStream in = new DataInputStream(sock.getInputStream());
//        }
//        catch (Exception ex) {
//            return false;
//        }
//        return true;
//    }
    
    public void setSendMsg(String sendMsg) {
        this.sendMsg = sendMsg;
    }
    public void setMsgLengeh(int msgLength) {
        this.msgLength = msgLength;
    }
    private int getConnectStream() throws SocketException{
        try{
            if(sock == null) return -1;
            in = new DataInputStream(sock.getInputStream());
            out = new DataOutputStream(sock.getOutputStream());
        }catch(Exception e){
            System.out.println("Socke Client 生成输入、输出流错误"+e.toString());
            logger.error("Socke Client 生成输入、输出流错误"+e.toString());
            throw new SocketException();
        }
        return 0;
    }
    public byte[] doService() throws SocketException, Exception{
        byte[] b = new byte[200];      //每次读取的报文流
        byte[] msgLengthBuffer = null;  //报文长度存储空间
        byte[] msgBuffer = null;        //报文数据体存储空间
        int bytes = 0;                  //每次读取的报文长度
        int mesLen = 0;                //报文长度
        int mesCurLen = 0;             //当前长度
        try{
            if(getConnectStream() != 0) throw new SocketException();
        	out.write(sendMsg.getBytes(), 0, sendMsg.getBytes().length);
            out.flush();
            msgLengthBuffer = new byte[msgLength]; 
            while(true){
                bytes = in.read(b);
                if(bytes == -1)	throw new SocketException();
                if(bytes == 0) continue;
                if(mesLen <= 0){
                    //获得输入流长度
                    System.arraycopy(b, 0, msgLengthBuffer, 0, msgLength);
                    mesLen = StringUtil.hBytesToInt(msgLengthBuffer);
                    logger.info("获得输入流长度 len:" + mesLen);
                    msgBuffer = new byte[mesLen];
                }
                logger.info("获得报文长度=" + mesLen);
                logger.info("获得当前数据流长度=" + mesCurLen);
            	if(bytes + mesCurLen > mesLen)
            		bytes = mesLen - mesCurLen;
                System.arraycopy(b, 0, msgBuffer, mesCurLen, bytes);
                logger.info("输出=[" + new String(msgBuffer) + "]");
                mesCurLen += bytes;
                if(mesLen == mesCurLen){
                	mesLen = 0;
                	mesCurLen = 0;
                    break;
                }
            }
        }catch(SocketException se){
        	logger.info("SocketClient doService SocketException:" + se.toString());
            throw new SocketException();
        }catch(Exception e){
        	logger.info("SocketClient doService Exception:" + e.toString());
            throw new Exception();
        }finally{
        	close();
        }
        return msgBuffer;
    }
    
    
    public byte[] doService(int msgBufferLen) throws SocketException, Exception{
        int bytes = 0;                  //每次读取的报文长度
        byte[] msgBuffer = null;        //报文数据体存储空间
        try{
            if(getConnectStream() != 0) throw new SocketException();
        	out.write(sendMsg.getBytes());
        	out.flush();
            msgBuffer = new byte[msgBufferLen];
            while(true){
                bytes = in.read(msgBuffer, 0, msgBuffer.length);
                if(bytes <= -1)	throw new SocketException();
                if(bytes == 0) continue;
                
                logger.info("输出=[" + new String(msgBuffer) + "]");
                if(bytes > 0){
                	break;
                }
            }
        }catch(SocketException se){
        	logger.info("SocketClient doService SocketException:" + se.toString());
            throw new SocketException();
        }catch(Exception e){
        	logger.info("SocketClient doService Exception:" + e.toString());
            throw new Exception();
        }finally{
        	close();
        }
        return msgBuffer;
    }
    
    
    
    
    public void close(){
        try{
            if (sock != null) sock.close();
        }catch(Exception e){
        }
        
        try{
            if(out != null) out.close();
        }catch(Exception e){
        }
        
        try{
        	if(in != null) in.close();
        }catch(Exception e){
        }
    }
    public static void main(String []args){
        //SocketClient SocketClient1 = new SocketClient("127.0.0.1", 12345);
        //String a = "000000241111111111111112";
        //SocketClient1.setOutBut(a.getBytes());
        //System.out.println(SocketClient1.doService());
    }

}

