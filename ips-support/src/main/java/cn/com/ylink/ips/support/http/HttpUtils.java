/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-9-5
 */

package cn.com.ylink.ips.support.http;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-9-5
 * @description：http辅助类
 */

public class HttpUtils {
	
	private HttpClient httpClient=null;
	
	
	/**
	 * @description 打开链接
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public void openConnection(){
		httpClient=new DefaultHttpClient();
	}
	
	
	/**
	 * @description 连接和读取时间设置
	 * @param connTimeOutMillis
	 * @param readTimeOutMillis 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public void openConnection(int connTimeOutMillis,int readTimeOutMillis){
		httpClient=new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeOutMillis);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, connTimeOutMillis);
	}
	
	/**
	 * @description HTTP GET 提交请求，注意服务器要将获取到的参数ISO-8859-1 转化成encoding
	 * @param url
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpGet(String url,String encoding) throws HttpException{
		try{
			
			HttpGet httpGet=new HttpGet(url);
			
			HttpResponse response = this.httpClient.execute(httpGet);
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}
			
			throw new HttpException("通信异常");
		}catch (Exception e) {
			throw new HttpException(e.getMessage());
		}
	}
	
	
	/**
	 * @description HTTP GET 提交请求并传递参数，注意服务器要将ISO-8859-1 转化成encoding
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpGet(String url,List<HttpRequestParam> params,String encoding) throws HttpException{
		try{
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			for(HttpRequestParam param:params){
				qparams.add(new BasicNameValuePair(param.getParaName(), param.getParaValue()));
			}
			
			String qry=URLEncodedUtils.format(qparams, encoding);
			String getUrl=url+"?"+qry;
			HttpGet httpGet=new HttpGet(getUrl);
			HttpResponse response = this.httpClient.execute(httpGet);
			
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}
			
			throw new HttpException("通信异常");
		}catch (Exception e) {
			throw new HttpException(e.getMessage());
		}
	}
	
	/**
	 * @description http post 提交请求
	 * @param url
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpPost(String url,String encoding) throws HttpException{
		try{
			
			HttpPost httpPost=new HttpPost(url);
			
			HttpResponse response = this.httpClient.execute(httpPost);
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}
			
			throw new HttpException("通信异常");
		}catch (Exception e) {
			throw new HttpException(e.getMessage());
		}
	}
	
	/**
	 * @description http post 提交请求，传递参数
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpPost(String url,List<HttpRequestParam> params,String encoding) throws HttpException{
		try{
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			for(HttpRequestParam param:params){
				qparams.add(new BasicNameValuePair(param.getParaName(), param.getParaValue()));
			}
			
			HttpPost httpPost=new HttpPost(url);
			UrlEncodedFormEntity entity=new UrlEncodedFormEntity(qparams,encoding);
			httpPost.setEntity(entity);
			
			HttpResponse response = this.httpClient.execute(httpPost);
			
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}
			
			throw new HttpException("通信异常");
		}catch (Exception e) {
			throw new HttpException(e.getMessage());
		}
	}
	
	
	/**
	 * @description 关闭链接 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public void closeConnection(){
		if(null!=httpClient){
			httpClient.getConnectionManager().shutdown();
		}
	}
}
