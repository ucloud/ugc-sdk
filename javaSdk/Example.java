import javasdk.*;
import java.io.IOException;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.*;

public class Example{

        public static String  PUBLIC_KEY="";
        public static String PRIVATE_KEY="";

	public static void testGetImageBucketList(){
		System.out.println("============= Begin GetImageBucketList =================");
		ImageBucketList  il = new ImageBucketList();
		il.region = "cn-bj2";
		il.public_key = PUBLIC_KEY;
		il.private_key = PRIVATE_KEY;
		try {
			String result = il.GetImageBucketList();	
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("ImageBucketList :");
			System.out.println(e);
		}
		System.out.println("============= End GetImageBucketList =================");
	}

	public static void testGetDockerImageList(){
		System.out.println("============= Begin GetDockerImageList =================");
		DockerImageList il = new DockerImageList();
		il.region = "cn-bj2";
		il.bucket_name = "library";
		il.public_key = PUBLIC_KEY;
		il.private_key = PRIVATE_KEY;
		try {
			String result = il.GetDockerImageList();	
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("DockerImageList :");
			System.out.println(e);
		}
		System.out.println("============= End testGetDockerImageList =================");
	}

	public static void testGetAllDockerImages(){
		System.out.println("============= Begin GetAllDockerImages =================");
		AllDockerImages il = new AllDockerImages();
		il.region = "cn-bj2";
		il.public_key = PUBLIC_KEY;
		il.private_key = PRIVATE_KEY;
		try {
			String result = il.GetAllDockerImages();
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("AllDockerImages :");
			System.out.println(e);
		}
		System.out.println("============= End AllDockerImages =================");
	}

	public static void testCreateDockerImageBucket(){
		System.out.println("============= Begin CreateDockerImageBucket =================");
		CreateDockerImageBucket il = new CreateDockerImageBucket();
		il.region = "cn-bj2";
		il.bucket_name ="javaskdtest";
		il.public_key = PUBLIC_KEY;
		il.private_key = PRIVATE_KEY;
		try {
			String result = il.CreateImageBucketRequest();
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("CreateDockerImageBucket :");
			System.out.println(e);
		}
		System.out.println("============= End CreateDockerImageBucket =================");
	}

	
	public static void testTaskList(){
		System.out.println("============= Begin TaskList=================");
		TaskList il = new TaskList();
		il.region = "cn-bj2";
		il.public_key = PUBLIC_KEY;
		il.private_key = PRIVATE_KEY;
		il.date = "2016-11-11";
		try {
			String result = il.GetTaskList();
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("TaskList :");
			System.out.println(e);
		}
		System.out.println("============= End TaskList =================");
	}

	public static void testTastDetail(){
		System.out.println("============= Begin TaskDetail=================");
		TaskDetail il = new TaskDetail();
		il.region = "cn-bj2";
		il.task_id ="19d7cf53-8be7-4aef-80b6-9e946881c872" ;
		il.public_key = PUBLIC_KEY;
		il.private_key = PRIVATE_KEY;
		try {
			String result = il.GetTaskDetail();
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("GetTaskDetail :");
			System.out.println(e);
		}
		System.out.println("============= End TaskDetail =================");
	}

	public static String AccessToken = "null";
	public static void testAccessToken(){
		System.out.println("============= Begin AccessToken=================");
		AccessToken il = new AccessToken();
		il.region = "cn-bj2";
		il.public_key = PUBLIC_KEY;
		il.private_key = PRIVATE_KEY;
		try {
			String result = il.GetAccessToken();
			System.out.println(result);
			
			//解析出access_token
			JSONTokener jsonTokener = new JSONTokener(result);
			try {  
				JSONObject studentJSONObject = (JSONObject) jsonTokener.nextValue();  
				AccessToken = studentJSONObject.getString("AccessToken");
				System.out.println(AccessToken);
			} catch (JSONException e) {  
				e.printStackTrace();  
			}  


		}catch(IOException e) {
			System.out.println("GetAccessToken :");
			System.out.println(e);
		}
		System.out.println("============= End AccessToken =================");
	}


	public static void testSubmitTask(){
		System.out.println("============= Begin SubmitTask=================");
		SubmitTask il = new SubmitTask();
		il.region = "cn-bj2";
		il.image_name = "cn-bj2.ugchub.service.ucloud.cn/library/helloworld:first";
		il.access_token = AccessToken;
		il.cmd = "this is cmd";
		il.output_dir = "/tmp";
		il.output_filename = "result";
		il.task_name = "for_test_";

		try {
			//返回值同步和异步不一样  详细的见SubmitTask.java 文件
			String result = il.SubmitTask("this is data");
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("SubmitTask :");
			System.out.println(e);
		}
		System.out.println("============= End SubmitTask=================");
	}

	public static String TASKID = "null";
	public static void testSubmitAsyncTask(){
		System.out.println("============= Begin Async  SubmitTask=================");
		SubmitTask il = new SubmitTask();
		il.region = "cn-bj2";
		il.image_name = "cn-bj2.ugchub.service.ucloud.cn/library/helloworld:first";
		il.access_token = AccessToken;
		il.cmd = "this is cmd";
		il.output_dir = "/tmp";
		il.output_filename = "result";
		il.task_name = "for_test_";
		il.task_type = "Async";

		try {
			String result = il.SubmitTask("this is data");
			System.out.println(result);

			//获取异步返回的任务id
			JSONTokener jsonTokener = new JSONTokener(result);
			try {  
				JSONObject studentJSONObject = (JSONObject) jsonTokener.nextValue();  
				TASKID = studentJSONObject.getString("TaskId");
				System.out.println(TASKID);
			} catch (JSONException e) {  
				e.printStackTrace();  
			}  


		}catch(IOException e) {
			System.out.println("SubmitTask :");
			System.out.println(e);
		}
		System.out.println("============= End Async SubmitTask=================");
	}



	public static void testTaskResut(){
		System.out.println("============= Begin TaskResult=================");
		TaskResult il = new TaskResult();
		il.region = "cn-bj2";
		il.task_id =  TASKID;
		il.access_token = AccessToken;

		try {
			String result = il.GetTaskResult();
			System.out.println(result);
		}catch(IOException e) {
			System.out.println("GetTaskResult :");
			System.out.println(e);
		}
		System.out.println("============= End SubmitTask=================");
			
	}

	public static void main(String[] args)  throws InterruptedException {
		//获取可用的镜像仓库列表
		testGetImageBucketList();
		//获取可用的Docker镜像列表，包括公有和私有的
		testGetDockerImageList();
		//获取所有可用的Docker镜像名称，包括公有和私有的
		testGetAllDockerImages();
		//创建Docker镜像bucket
		testCreateDockerImageBucket();
		//获取提交的任务列表
		testTaskList();
		//查看任务详情
		testTastDetail();
		//用于获取AccessToken
		testAccessToken();
		//提交同步任务
		testSubmitTask();
		//提交异步接口
		testSubmitAsyncTask();

		System.out.println("wait async task finish");
		 Thread.sleep(5000);
		//获取异步任务的运行结果
		testTaskResut();
	}
}
