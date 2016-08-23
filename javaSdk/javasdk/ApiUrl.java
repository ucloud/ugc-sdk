package javasdk;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class ApiUrl{

	public static final String COMMON_API_URL="http://api.ucloud.cn/";
	public static final String TASK_API_URL="http://api.ugc.service.ucloud.cn/";
			
	public  static String generateRequestUrl(String api,
			 TreeMap<String, String> param_map){
		String url = api;
		boolean first = true;
		Iterator titer=param_map.entrySet().iterator();
		while(titer.hasNext()){
			if (first){
				url += "?";	
				first = false;
			}else{
				url += "&";
			}

			Map.Entry ent=(Map.Entry)titer.next();  
			String keyt = ent.getKey().toString();  
			String valuet = URLEncoder.encode(ent.getValue().toString());

			url += keyt;
			url += "=";
			url += valuet;	
		}
		return url;
	}

	public static String vefsig(String privateKey,
			TreeMap<String, String> param_map) {
		String url_param = "";
		Iterator titer=param_map.entrySet().iterator();
		while(titer.hasNext()){
			Map.Entry ent=(Map.Entry)titer.next();  
			String keyt = ent.getKey().toString();  
			String valuet = ent.getValue().toString();
			url_param = url_param + keyt + valuet;
		}
		url_param += privateKey;

		return sha1(url_param);
	}

	public static String sha1(String input) {

		if (input == null){
			return null;
		}
		try  {
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}

			return sb.toString();
		}catch (NoSuchAlgorithmException e){
			return null;
		}
	}
}
