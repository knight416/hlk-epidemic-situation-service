
/**   
* @Title: XmlUtil.java 
* @Package cn.net.hylink.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Wang Yingnan    
* @date 2017年6月27日 下午1:51:57 
* @version V1.0   
*/ 

package cn.net.hlk.util;

import org.dom4j.DocumentHelper;


/** 
 * @author Wang Yingnan  
 */
public class XmlUtil {

	public static boolean isXmlString(String xmlString){
		if(xmlString==null||"".equals(xmlString)){
			return false;
		}
		try{
			 DocumentHelper.parseText(xmlString);
		}catch(Exception e){
			return false;
		}
		
		return true;
	}
}
