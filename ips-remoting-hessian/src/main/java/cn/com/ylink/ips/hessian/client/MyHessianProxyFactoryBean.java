/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-3-10
 */

package cn.com.ylink.ips.hessian.client;

/** 
 * @author ZhangDM(Mingly)
 * @date 2013-3-10
 * @description：TODO
 */

public class MyHessianProxyFactoryBean extends HessianProxyFactoryBean {
	
	private MyHessianProxyFactory proxyFactory = new MyHessianProxyFactory();

    private int readTimeOut = 10000;

    private int connectTimeOut = 10000;

    public int getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(int readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public int getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(int connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public void afterPropertiesSet() {
    	
        proxyFactory.setReadTimeout(readTimeOut);
        proxyFactory.setConnectTimeout(connectTimeOut);
        setProxyFactory(proxyFactory);
        super.afterPropertiesSet();
    }

}
