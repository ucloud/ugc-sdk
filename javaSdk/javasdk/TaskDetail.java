package javasdk;

import java.util.TreeMap;
import java.io.IOException;

//查看任务详情
public class TaskDetail{
	public String  region;       //required
	public String  task_id;      //required
	public String public_key;  //required  用户的 公钥
	public String private_key;  //required  用户的 私钥



	/*Response Example
	 * {
	 * 	"Action" : "GetTaskList",
	 *     "RetCode" : 0,
	 *     "Message" : “”,
	 *     "TotalCount" : 1,
	 *          "TaskSet" : [
	 *                  {
	 *                   "TaskId":"0b560b1d-f3b1-43de-a492-08875d79211b",
	 *                   "TaskName":"ucs-helloworld",
	 *                   "Owner":"ucs",
	 *                   "State":"Success",
	 *                   "CreateTime":1234567890
	 *                   "StartTime":1234567892
	 * 	             "EndTime":1234567990
	 * 	             }
	 * 	       ]
	 *}
	 *
	 */
	
	public String GetTaskDetail() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);
		this_map.put("TaskId", this.task_id);

		this_map.put("Action", "GetTaskDetail");
		this_map.put("PublicKey", this.public_key);

		String signature = ApiUrl.vefsig(this.private_key, this_map);
	
		this_map.put("Signature", signature);

		String url = ApiUrl.generateRequestUrl(ApiUrl.COMMON_API_URL, this_map);

		return HttpClient.httpGet(url);
	}

}
