/**
 * 版权所有(C) 2013 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2013-1-8
 */

package cn.com.ylink.ips.support.security.gen;

/** 
 * @author ZhangDM(Mingly)
 * @date 2013-1-8
 * @description：代表证书的签发者或者证书的所有者的名称
 */

public class CertName {
	
private String country;
	
	private String state;
	
	private String locality;
	
	private String org;
	
	private String orgUnit;
	
	private String commonName;

	
	public CertName() {

	}
	
	
	/**
	 * 获取所在国家
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 设置所在国家
	 * @return
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * 获取所在省
	 * @return
	 */
	public String getState() {
		return state;
	}

	/**
	 * 设置所在省
	 * @return
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * 获取所在市
	 * @return
	 */
	public String getLocality() {
		return locality;
	}

	/**
	 * 设置所在市
	 * @return
	 */
	public void setLocality(String locality) {
		this.locality = locality;
	}

	/**
	 * 获取所在组织
	 * @return
	 */
	public String getOrg() {
		return org;
	}

	/**
	 * 设置所在组织
	 * @return
	 */
	public void setOrg(String org) {
		this.org = org;
	}

	
	/**
	 * 获取所在单元
	 * @return
	 */
	public String getOrgUnit() {
		return orgUnit;
	}

	/**
	 * 设置所在单元
	 * @return
	 */
	public void setOrgUnit(String orgUnit) {
		this.orgUnit = orgUnit;
	}

	/**
	 * 获取名称
	 * @return
	 */
	public String getCommonName() {
		return commonName;
	}

	/**
	 * 设置名称
	 * @return
	 */
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	
	
	public String toString(){
		StringBuffer certName=new StringBuffer();
		
		certName.append("CN").append("=").append(this.getCommonName()).append(",");
		certName.append("OU").append("=").append(this.getOrgUnit()).append(",");
		certName.append("O").append("=").append(this.getOrg()).append(",");
		certName.append("L").append("=").append(this.getLocality()).append(",");
		certName.append("ST").append("=").append(this.getState()).append(",");
		certName.append("C").append("=").append(this.getCountry());
	
		return certName.toString();
	}
}
