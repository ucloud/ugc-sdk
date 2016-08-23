package javasdk;

import java.util.TreeMap;
import java.io.IOException;


//获取所有可用的Docker镜像名称，包括公有和私有的
public class AllDockerImages {
	public AllDockerImages(){
	}

	public String  region;       //required
	public String public_key;  //required  用户的 公钥
	public String private_key;  //required  用户的 私钥

	/*
	 * {
	 *     "Action" : "GetDockerImageList",
	 *     "RetCode" : 0,
	 *     "Message" : “”,
	 *      "ImageSet" : [
	 *           “library/harbor_ui:160615.8”,
	 *           “library/harbor_ui:160615.9”,	 
	 *                   ]
	 *     }
	 *
	 */
	public String GetAllDockerImages() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);

		this_map.put("Action", "GetAllImages");
		this_map.put("PublicKey", this.public_key);

		String signature = ApiUrl.vefsig(this.private_key, this_map);
	
		this_map.put("Signature", signature);

		String url = ApiUrl.generateRequestUrl(ApiUrl.COMMON_API_URL, this_map);

		return HttpClient.httpGet(url);
	}
}
