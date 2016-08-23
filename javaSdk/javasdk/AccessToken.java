package javasdk;

import java.util.TreeMap;
import java.io.IOException;


//提交任务接口使用AccessToken做身份验证，此接口用于获取AccessToken
public class AccessToken {
	public AccessToken(){
		this.expire_in = 7200;
	}
	public String  region;       //required
	public int  expire_in;       //option  token 有效时间 单位秒  默认 7200 秒
	public String grant_type;     //option
	public String public_key;  //required  用户的 公钥
	public String private_key;  //required  用户的 私钥

	/*
	 * Response Example
	 * {
	 *     "Action" : "GetAccessToken",
	 *     "RetCode" : 0,
	 *     "Message" : “”,
	 *     "AccessToken" : “H2JKDKOW3HN24CGG1PV3DV779932C”,
	 *     "ExpireIn" : 7200
	 *}
	 *
	 */	
	public String GetAccessToken() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);
		if(this.grant_type != null && this.grant_type.length() != 0){
			this_map.put("GrantType", this.grant_type);
		}

		this_map.put("ExpireIn", Integer.toString(this.expire_in));

		this_map.put("Action", "GetAccessToken");
		this_map.put("PublicKey", this.public_key);

		String signature = ApiUrl.vefsig(this.private_key, this_map);
	
		this_map.put("Signature", signature);

		String url = ApiUrl.generateRequestUrl(ApiUrl.COMMON_API_URL, this_map);

		return HttpClient.httpGet(url);
	}
}
