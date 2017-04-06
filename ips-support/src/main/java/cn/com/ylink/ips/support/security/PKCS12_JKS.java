/**
 * 版权所有(C) 2015 深圳市雁联计算系统有限公司
 * 创建:Administrator 2015-3-25
 */

package cn.com.ylink.ips.support.security;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;
/** 
 * @author Administrator
 * @date 2015-3-25
 * @description：TODO
 */

public class PKCS12_JKS
{
    //certificate store format
	//将PFX证书转为JKS证书，或将JKS证书转为PFX证书
    public static final String PKCS12 = "PKCS12";
    public static final String JKS = "JKS";

    public static void main(String[] args) {
//    	if (args.length < 5) {
//    		System.out.println("使用以下命令: java flink.util.PKCS12_JKS [p2j/j2p] jks_file_name alias password pkcs12_file_name");
//    		//java flink.util.PKCS12_JKS p2j a.jks hzl 123456 a.pfx
//    		System.out.println("note: p2j means pkcs12 to jks, j2p means jks to pkcs12");
//    		return;
//    	}
//    	if (args[0].equals("p2j"))
//    		pkcs12TOjks(args[1], args[2], args[3], args[4]);
//    	else
//    		jksTOpkcs12(args[1], args[2], args[3], args[4]);
    	String arg = "p2j";
    	String args1 = "/fileContent/test/szfs000000000001.jks";
    	String args2 = "1";
    	String args3 = "12345678";
    	String args4 = "/fileContent/test/szfs000000000001.pfx";
    	if (arg.equals("p2j"))
    		pkcs12TOjks(args1, args2, args3, args4);
    	else
    		jksTOpkcs12(args1, args2, args3, args4);
    }
    
    public static void pkcs12TOjks(String jksFileName, String alias, String password, String pkcs12FileName)
    {
        try
        {
            KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(pkcs12FileName);
 
            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if ((password == null) || password.trim().equals(""))
            {
                nPassword = null;
            }
            else
            {
                nPassword = password.toCharArray();
            }
            inputKeyStore.load(fis, nPassword);
            fis.close();
 
            System.out.println("keystore type=" + inputKeyStore.getType());
 
            //----------------------------------------------------------------------
            // get a JKS keystore and initialize it.
            KeyStore outputKeyStore = KeyStore.getInstance("JKS");
            outputKeyStore.load(null, password.toCharArray());
            // Now we loop all the aliases, we need the alias to get keys.
            // It seems that this value is the "Friendly name" field in the
            // detals tab <-- Certificate window <-- view <-- Certificate
            // Button <-- Content tab <-- Internet Options <-- Tools menu
            // In MS IE 6.
            int aliasCount = 0;
            Enumeration enumn = inputKeyStore.aliases();
            while (enumn.hasMoreElements()) // we are readin just one certificate.
            {
                String keyAlias = (String)enumn.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
                if (inputKeyStore.isKeyEntry(keyAlias))
                {
                    Key key = inputKeyStore.getKey(keyAlias, nPassword);
                    Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);
                    if (aliasCount == 0)
                    	outputKeyStore.setKeyEntry(alias, key, password.toCharArray(), certChain);
                    else
                    	outputKeyStore.setKeyEntry(alias + aliasCount, key, password.toCharArray(), certChain);
                    aliasCount++;
                }
            }
            FileOutputStream out = new FileOutputStream(jksFileName);
            outputKeyStore.store(out, nPassword);
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
    }
    
    public static void jksTOpkcs12(String jksFileName, String alias, String password, String pkcs12FileName)
    {
        try
        {
        	
            KeyStore inputKeyStore = KeyStore.getInstance("JKS");
            FileInputStream fis = new FileInputStream(jksFileName);
 
            // If the keystore password is empty(""), then we have to set
            // to null, otherwise it won't work!!!
            char[] nPassword = null;
            if ((password == null) || password.trim().equals(""))
            {
                nPassword = null;
            }
            else
            {
                nPassword = password.toCharArray();
            }
            inputKeyStore.load(fis, nPassword);
            fis.close();
 
            System.out.println("keystore type=" + inputKeyStore.getType());
 
            //----------------------------------------------------------------------
            // create a new keystore and initialize it.
            KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");
            outputKeyStore.load(null, password.toCharArray());
            // Now we loop all the aliases, we need the alias to get keys.
            // It seems that this value is the "Friendly name" field in the
            // detals tab <-- Certificate window <-- view <-- Certificate
            // Button <-- Content tab <-- Internet Options <-- Tools menu
            // In MS IE 6.
            Enumeration enumn = inputKeyStore.aliases();
            int aliasCount = 0;
            while (enumn.hasMoreElements()) // we are readin just one certificate.
            {
                String keyAlias = (String)enumn.nextElement();
                System.out.println("alias=[" + keyAlias + "]");
                if (inputKeyStore.isKeyEntry(keyAlias))
                {
                    Key key = inputKeyStore.getKey(keyAlias, nPassword);
                    Certificate[] certChain = inputKeyStore.getCertificateChain(keyAlias);
                    if (aliasCount == 0)
                    	outputKeyStore.setKeyEntry(alias, key, password.toCharArray(), certChain);
                    else
                    	outputKeyStore.setKeyEntry(alias + aliasCount, key, password.toCharArray(), certChain);
                    aliasCount++;
                }
            }
            FileOutputStream out = new FileOutputStream(pkcs12FileName);
            outputKeyStore.store(out, nPassword);
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        } 
    }
}

