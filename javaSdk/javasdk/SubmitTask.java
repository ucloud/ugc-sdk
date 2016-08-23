package javasdk;

import java.util.TreeMap;
import java.util.HashMap;
import java.io.IOException;

//提交任务
public class SubmitTask {
	public SubmitTask(){
		this.task_type = "Sync";
		this.timeout = 10;
	}
	public String region;       //required
	public String image_name;    //required
	public String cmd;           //option   命令行参数
	public String output_dir;    //option   结果输出路径
	public String output_filename; //option 结果保存文件名
	public String task_type;       //option "Sync"  同步 "Async"  异步
	public int timeout;            //option  默认10s  单位为妙
	public String task_name;       //option   任务名称
	public String access_token;    //required



	/*
	   提交同步任务成功
	   返回任务输出的二进制tar文件，目录结构如下，子文件仅在不为空的时候返回

	   Output-> 返回的tar格式二进制文件
	   Stdout  任务的标准输出                             
	   Stderr   任务的标准错误输出
	   OutputDir\OutputFileName  任务设置的文件输出

	   提交失败
	   {
	   "Action" : "SubmitTask",
	   "RetCode" : -1,
	   "Message" : “All Fail”
	   }

	   异步任务提交成功
	   {
	   "Action" : "SubmitTask",
	   "RetCode" : 0,
	   "Message" : “”,
	   "TaskId" : “123455”,
	   }

	*/
	public String SubmitTask(String data) throws IOException{

		TreeMap<String, String> this_map = new TreeMap<String, String>();
		this_map.put("Region", this.region);
		this_map.put("ImageName", this.image_name);
		this_map.put("Action", "SubmitTask");
		this_map.put("AccessToken", this.access_token);
		this_map.put("TimeOut", Integer.toString(this.timeout));

		if(this.cmd != null && this.cmd.length() != 0){
			this_map.put("Cmd", this.cmd);
		}

		if(this.output_dir != null && this.output_dir.length() != 0){
			this_map.put("OutputDir", this.output_dir);
		}

		if(this.output_filename != null && this.output_filename.length() != 0){
			this_map.put("OutputFileName", this.output_filename);
		}
		if(this.task_type != null && this.task_type.length() != 0){
			this_map.put("TaskType", this.task_type);
		}
		if(this.task_name != null && this.task_name.length() != 0){
			this_map.put("TaskName", this.task_name);
		}

		String url = ApiUrl.generateRequestUrl(ApiUrl.TASK_API_URL, this_map);

		HashMap<String, String> header = new  HashMap<String, String>();
		header.put("Content-Type", "application/octet-stream");

		return HttpClient.httpPost(url, header, data);
	}
}
