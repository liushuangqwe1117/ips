/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-1-8
 */

package cn.com.ylink.ips.support.security.gen;

/** 
 * @author ZhangDM(Mingly)
 * @date 2013-1-8
 * @description：证书生成测试
 */

public class CertGenTest {

	/**
	 * @description 
	 * @param args  
	 * @author ZhangDM(Mingly)
	 * @throws Exception 
	 * @date 2013-1-8
	 */
	public static void main(String[] args) throws Exception {
		
		CertName certName=new CertName();
		certName.setCountry("中国");
		certName.setState("北京");
		certName.setLocality("北京");
		certName.setOrg("证联融通");
//		certName.setOrgUnit("华创基金");
		certName.setOrgUnit("证钱支付");
		certName.setCommonName("基金");
		
		CertGen certGen = new CertGen();
		certGen.genCert(certName);

	}

}
