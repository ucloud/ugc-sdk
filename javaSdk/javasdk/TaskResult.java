package javasdk;

import java.util.TreeMap;
import java.io.IOException;

//获取异步任务的运行结果
public class TaskResult {
	public String region;       //required
	public String task_id;      //required
	public String access_token; //required


	/*成功
	 * 返回任务输出的二进制tar文件，目录结构如下，子文件仅在不为空的时候返回
	 *
	 *Output-> 返回的tar格式二进制文件
	 *Stdout  任务的标准输出                             
	 *Stderr   任务的标准错误输出
	 *OutputDir\OutputFileName  任务设置的文件输出

	 *获取任务结果失败
	 *{
	 *"Action" : "GetTaskResult",
	 *"RetCode" : -1,
	 *"Message" : “Not Finish”,
	 *"State" : “Running”,
	 *}

	 */

	public String GetTaskResult() throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);

		this_map.put("TaskId", this.task_id);
		this_map.put("AccessToken", this.access_token);
		this_map.put("Action", "GetTaskResult");

		String url = ApiUrl.generateRequestUrl(ApiUrl.TASK_API_URL, this_map);

		return HttpClient.httpGet(url);
	}
}
