package javasdk;

import java.util.TreeMap;
import java.io.IOException;

//获取可用的镜像仓库列表
public class ImageBucketList {
	public ImageBucketList()
	{
		this.limit = 20;
		this.offset = 0;
	}
	public String  region;       //required
	public String  bucket_name;  //option 
	public String  bucket_type;  //option
	public String  orderby;      //option
	public int     limit;        //option query num 默认20
	public int     offset;       //option 默认为0
	public String public_key;  //required  用户的 公钥
	public String private_key;  //required  用户的 私钥

	/* 返回值
	 * {
	 *     "Action" : "GetImageBucketList",
	 *     "RetCode" : 0,
	 *     "Message" : “”,
	 *     "TotalCount" : 1,
	 *     "ImageSet" : [
	 *                  {
	 *                   "BucketName":"ucsdr.ucloud.cn:5000/ucs/helloworld:6",
	 *                   "BucketType":"Public",
	 *                   “ImageCount” : 10,
	 *                   "CreateTime":1234567890
	 *                   }]
	 * }
	 *
	 */
	
	public String GetImageBucketList() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);
		if(this.bucket_name != null && this.bucket_name.length() != 0){
			this_map.put("BucketName", this.bucket_name);
		}

		if(this.bucket_type != null && this.bucket_type.length() != 0){
			this_map.put("BucketType", this.bucket_type);
		}

		if(this.orderby != null && this.orderby.length() != 0){
			this_map.put("OrderBy", this.orderby);
		}

		this_map.put("Limit", Integer.toString(this.limit));
		this_map.put("Offset", Integer.toString(this.offset));

		this_map.put("Action", "GetImageBucketList");
		this_map.put("PublicKey", this.public_key);

		String signature = ApiUrl.vefsig(this.private_key, this_map);
	
		this_map.put("Signature", signature);

		String url = ApiUrl.generateRequestUrl(ApiUrl.COMMON_API_URL, this_map);
		return HttpClient.httpGet(url);
	}

}


