package javasdk;

import java.util.TreeMap;
import java.io.IOException;


//获取提交的任务列表
public class TaskList {
	public TaskList(){
		this.limit = 20;
		this.offset = 0;
	} 

	public String  region;       //required
	public String  task_id;      //option 任务ID 不填返回所有Id的任务
	public String  task_name;    //option 任务名称  不填返回所有名称的任务 
	public String  task_type;    //option  同步任务"Sync"  异步任务 "Async"  默认"All"
	public String  state;        //option  Running：运行中  Success：成功  Fail：失败  默认为 All:所有  
	public String  orderby;      //option 排序
	public int     limit;        //option  默认为20
	public int     offset;       //option  默认为0
	public String public_key;  //required  用户的 公钥
	public String private_key;  //required  用户的 私钥



	/*
	 * Response Example
	 * {
	 *     "Action" : "GetTaskList",
	 *     "RetCode" : 0,
	 *     "Message" : “”,
	 *     "TotalCount" : 1,
	 *     "TaskSet" : [
	 *                {
	 *                 "TaskId":"0b560b1d-f3b1-43de-a492-08875d79211b",
	 *                 "TaskName":"ucs-helloworld",
	 *                 "Owner":"ucs",
	 *                 "State":"Success",
	 *                 "CreateTime":1234567890
	 *                 "StartTime":1234567892
	 *                 "EndTime":1234567990
	 *                 }
	 *                ]
	 *}
	 *
	 */

	public String GetTaskList() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);
		if(this.task_id != null && this.task_id.length() != 0){
			this_map.put("TaskId", this.task_id);
		}

		if(this.task_name != null && this.task_name.length() != 0){
			this_map.put("TaskName", this.task_name);
		}

		if(this.task_type != null && this.task_type.length() != 0){
			this_map.put("TaskType", this.task_type);
		}

		if(this.state != null && this.state.length() != 0){
			this_map.put("State", this.state);
		}

		if(this.orderby != null && this.orderby.length() != 0){
			this_map.put("OrderBy", this.orderby);
		}
		this_map.put("Limit", Integer.toString(this.limit));
		this_map.put("Offset", Integer.toString(this.offset));

		this_map.put("Action", "GetTaskList");
		this_map.put("PublicKey", this.public_key);

		String signature = ApiUrl.vefsig(this.private_key, this_map);
	
		this_map.put("Signature", signature);

		String url = ApiUrl.generateRequestUrl(ApiUrl.COMMON_API_URL, this_map);

		return HttpClient.httpGet(url);
	}
}
