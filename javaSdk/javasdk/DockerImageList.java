package javasdk;

import java.util.TreeMap;
import java.io.IOException;

//获取可用的Docker镜像列表，包括公有和私有的
public class DockerImageList{
	public DockerImageList(){
		this.limit = 20;	
		this.offset = 0;
	}
 	public String  region;       //required
	public String  bucket_name;  //required
	public String  orderby;      //option
	public int     limit;        //option query num
	public int     offset;       //option  
	public String public_key;  //required  用户的 公钥
	public String private_key;  //required  用户的 私钥

	/*返回值
	 * 	{
	 * 	    "Action" : "GetDockerImageList",
	 * 	    "RetCode" : 0,
	 * 	    "Message" : “”,
	 * 	    "TotalCount" : 1,
	 * 	    "ImageSet" : [
	 * 	                 {
	 * 	                 "ImageName":"ucsdr.ucloud.cn:5000/ucs/helloworld",
	 * 	                 "UpdateTime":1234567890
	 * 	      		 "TagSet" : [{
	 * 	                           "TagName":"6",
	 * 	                           "CreateTime":1234567890
	 * 	                           }]
	 * 	                }
	 *}
	 *
	*/
	public String GetDockerImageList() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);
		this_map.put("BucketName", this.bucket_name);

		if(this.orderby != null && this.orderby.length() != 0){
			this_map.put("OrderBy", this.orderby);
		}

		this_map.put("Limit", Integer.toString(this.limit));
		this_map.put("Offset", Integer.toString(this.offset));

		this_map.put("Action", "GetDockerImageList");
		this_map.put("PublicKey", this.public_key);

		String signature = ApiUrl.vefsig(this.private_key, this_map);
	
		this_map.put("Signature", signature);

		String url = ApiUrl.generateRequestUrl(ApiUrl.COMMON_API_URL, this_map);

		return HttpClient.httpGet(url);
	}		
}
