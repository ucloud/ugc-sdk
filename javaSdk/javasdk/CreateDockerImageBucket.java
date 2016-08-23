package javasdk;

import java.util.TreeMap;
import java.io.IOException;

//创建Docker镜像bucket
public class CreateDockerImageBucket {
	public String  region;      //required
	public String  bucket_name;  //required
	public String public_key;  //required  用户的 公钥
	public String private_key;  //required  用户的 私钥

	/* 返回值
	 * {
	 * 	"Action" : "CreateDockerImageBucket",
	 *      "RetCode" : 0,
	 *      "Message" : “”,
	 * }
	 *
	 */	
	public String CreateImageBucketRequest() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);
		this_map.put("Action", "CreateDockerImageBucket");
		this_map.put("BucketName", this.bucket_name);
		this_map.put("PublicKey", this.public_key);

		String signature = ApiUrl.vefsig(this.private_key, this_map);
	
		this_map.put("Signature", signature);

		String url = ApiUrl.generateRequestUrl(ApiUrl.COMMON_API_URL, this_map);

		return HttpClient.httpGet(url);
	}
}
