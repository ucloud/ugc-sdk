#ifndef UGC_API_INTERFACE_H_
#define UGC_API_INTERFACE_H_

#include <string>

using namespace std;

//==================获取镜像列表的参数===================
struct get_image_bucket_list_param{
	get_image_bucket_list_param(): limit(20),offset(0)
	{ }
	string  region;       //required
	string  bucket_name;  //option 
	string  bucket_type;  //option
	string  orderby;      //option
	int     limit;        //option query num 默认20
	int     offset;       //option 默认为0
	string public_key;  //required  用户的 公钥
	string private_key;  //required  用户的 私钥
};


//获取可用docker 镜像仓库列表
int get_bucket_images_list(get_image_bucket_list_param &request
					, string &result);


//======================获取可用的镜像列表=======================
struct get_docker_image_list_reqest{
	get_docker_image_list_reqest(): limit(20),offset(0)
	{ }
	string  region;       //required
	string  bucket_name;  //required
	string  orderby;      //option
	int     limit;        //option query num
	int     offset;       //option  
	string public_key;  //required  用户的 公钥
	string private_key;  //required  用户的 私钥
};

int get_docker_images_list(get_docker_image_list_reqest &request
					, string  &result);

//====================获取所有的镜像名称====================
struct get_all_images_request{
	string  region;       //required
	string public_key;  //required  用户的 公钥
	string private_key;  //required  用户的 私钥
};

int get_all_images(get_all_images_request &param
		, string &res);

//=================创建镜像仓库bucket=========================
struct create_docker_image_bucket_request{
	string  region;      //required
	string  bucket_name;  //required
	string public_key;  //required  用户的 公钥
	string private_key;  //required  用户的 私钥
};

int create_docker_image_bucket(create_docker_image_bucket_request &parm
		, string &res);

//=================查询任务列表============================
struct get_task_list_request{
	get_task_list_request(): limit(20), offset(0)
	{ }

	string  region;       //required
	string  task_id;      //option 任务ID 不填返回所有Id的任务
	string  task_name;    //option 任务名称  不填返回所有名称的任务 
	string  task_type;    //option  同步任务"Sync"  异步任务 "Async"  默认"All"
	string  state;        //option  Running：运行中  Success：成功  Fail：失败  默认为 All:所有  
	string  orderby;      //option 排序
	int     limit;        //option  默认为20
	int     offset;       //option  默认为0
	string public_key;  //required  用户的 公钥
	string private_key;  //required  用户的 私钥
};

int get_task_list(get_task_list_request &param
		, string &res);

//================获取任务详情=========================
struct get_task_detail_request{
	string  region;       //required
	string  task_id;      //required
	string public_key;  //required  用户的 公钥
	string private_key;  //required  用户的 私钥
};


//private_key 私钥
//result 返回结果
int get_task_detail(get_task_detail_request &param
		, string &result);


//=============== 获取授权token =======================
struct get_access_token_request{
	get_access_token_request(): expire_in(7200)
	{ }
	string  region;       //required
	int  expire_in;       //option  token 有效时间 单位秒  默认 7200 秒
	string grant_type;     //option
	string public_key;  //required  用户的 公钥
	string private_key;  //required  用户的 私钥
};

int get_access_token(get_access_token_request &param
		, string &res);

// =================提交任务相关==========================
struct submit_task_request{
	submit_task_request() :
		task_type("Sync"),      //默认同步方式
		timeout(10)
	{ }
	string  region;       //required
	string image_name;    //required
	string cmd;           //option   命令行参数
	string output_dir;    //option   结果输出路径
	string output_filename; //option 结果保存文件名
	string task_type;       //option "Sync"  同步 "Async"  异步
	int timeout;            //option  默认10s  单位为妙
	string task_name;       //option   任务名称
	string access_token;    //required
};

//data 用户数据
//result 返回结果
//提交失败
//	{
//    "Action" : "SubmitTask",
//    "RetCode" : -1,
//    "Message" : “All Fail”
//  }
//
//    同步任务提交成功
//    Output-> 返回的tar格式二进制文件
//    Stdout  任务的标准输出                             
//    tmp\result 任务设置的文件输出
//
//   异步任务提交成功
//   {
// 		"Action" : "SubmitTask",
//    	"RetCode" : 0,
//      "Message" : “”,
//      "TaskId" : “123455”,
//   }
//
int submit_task(submit_task_request &param
		, string &data
		, string &result);


//========== 获取异步执行结果 ======================
struct get_task_result_request{
	string region;       //required
	string task_id;      //required
	string access_token; //required
};

//获取任务结果失败
//{
//    "Action" : "GetTaskResult",
//    "RetCode" : -1,
//    "Message" : “Not Finish”,
//    "State" : “Running”,
//    }
//获取任务结果成功
//Output-> 返回的tar格式二进制文件
//	Stdout  任务的标准输出                             
//	tmp\result 任务设置的文件输出
int get_task_result(get_task_result_request &param
		, string &result);

#endif
