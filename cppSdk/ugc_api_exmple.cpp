#include <string>
#include <iostream>
#include <unistd.h>

#include "ugc_cpp_sdk/rapidjson/document.h"
#include "ugc_cpp_sdk/ugc_api_interface.h"

using namespace std;
using namespace rapidjson;

static string ACCESS_TOKEN;


//public_key  和 pirvate key 需要修改成自己的key

static string PUBLIC_KEY =  "";
static string PRIVATE_KEY = "";

void test_get_bucket_image_list()
{
	cout << "=================== test_get_bucket_image_list  ============================" << endl;
	get_image_bucket_list_param param;
	param.region = "cn-bj2";
	param.public_key=PUBLIC_KEY;
	param.private_key=PRIVATE_KEY;
	string result;
	int ret = get_bucket_images_list(param, result);
	cout << result << endl;
	cout << "=================== test_get_bucket_image_list  ============================" << endl;
}

void test_get_docker_image_list()
{
	cout << "===================  test_get_docker_image_list  ============================" << endl;

	get_docker_image_list_reqest param;
	string result;
	param.region = "cn-bj2";
	param.bucket_name = "library";
	param.public_key=PUBLIC_KEY;
	param.private_key=PRIVATE_KEY;
	int ret = get_docker_images_list(param, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;
	cout << "===================  test_get_docker_image_list  ============================" << endl;
}


void test_get_all_images()
{
	cout << "=================== test_get_all_images ============================" << endl;

	get_all_images_request param;
	string result;
	param.region = "cn-bj2";
	param.public_key=PUBLIC_KEY;
	param.private_key=PRIVATE_KEY;
	int ret = get_all_images(param, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;
	cout << "=================== test_get_all_images ============================" << endl;
}


void test_create_docker_image_bucket()
{
	cout << "===================  test_create_docker_image_bucket ============================" << endl;

	create_docker_image_bucket_request param;
	string result;
	param.region = "cn-bj2";
	param.bucket_name = "wujianguotest";
	param.public_key=PUBLIC_KEY;
	param.private_key=PRIVATE_KEY;
	int ret = create_docker_image_bucket(param, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;
	cout << "=================== test_create_docker_image_bucket ============================" << endl;
}

void test_get_task_list()
{
	cout << "===================  test_get_task_list ============================" << endl;

	get_task_list_request param;
	string result;
	param.region = "cn-bj2";
	param.public_key=PUBLIC_KEY;
	param.private_key=PRIVATE_KEY;
	param.date="2016-11-11";
	int ret = get_task_list(param, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;
	cout << "=================== test_get_task_list ============================" << endl;
	
}

void test_get_task_detail()
{
	cout << "=================== test_get_task_detail ============================" << endl;

	get_task_detail_request param;
	string result;
	param.region = "cn-bj2";
	param.task_id = "6ac36b13-5c7e-44f1-94d9-5a3951ec35b8";
	param.public_key=PUBLIC_KEY;
	param.private_key=PRIVATE_KEY;
	int ret = get_task_detail(param, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;
	cout << "=================== test_get_task_detail ============================" << endl;
}

void test_get_access_token()
{
	cout << "=================== test_get_access_token  ============================" << endl;

	get_access_token_request param;
	string result;
	param.region = "cn-bj2";
	param.public_key=PUBLIC_KEY;
	param.private_key=PRIVATE_KEY;
	int ret = get_access_token(param, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;

	Document document;
	document.Parse(result.c_str());
	if (document["RetCode"].GetInt() == 0)
	{
		ACCESS_TOKEN = document["AccessToken"].GetString();
		cout << "SUCCESS get  token" <<  ACCESS_TOKEN << endl;
	}else
	{
		cout << "FAILED get token " << endl;
	}

	cout << "=================== test_get_access_token ============================" << endl;
}


void test_submit_task()
{
	cout << "=================== test_submit_task  ============================" << endl;
	submit_task_request param;
	param.region = "cn-bj2";
	param.image_name = "cn-bj2.ugchub.service.ucloud.cn/library/helloworld:first";
	param.access_token = ACCESS_TOKEN;
	param.cmd = "this is cmd";
	param.output_dir = "/tmp";
	param.output_filename = "result";
	param.task_name = "for_test";

	string result;
	string data = "this is data";
	int ret = submit_task(param, data, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;


	cout << "=================== test_submit_task  ============================" << endl;
}

string ASYNC_TASK_ID;

void test_submit_async_task()
{
	cout << "=================== test_submit_async_task  ============================" << endl;
	submit_task_request param;
	param.region = "cn-bj2";
	param.image_name = "cn-bj2.ugchub.service.ucloud.cn/library/helloworld:first";
	param.access_token = ACCESS_TOKEN;
	param.cmd = "this is cmd";
	param.output_dir = "/tmp";
	param.output_filename = "result";
	param.task_name = "for_test";
	param.task_type = "Async";

	string result;
	string data = "this is data";
	int ret = submit_task(param, data, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;

	Document document;
	document.Parse(result.c_str());

	ASYNC_TASK_ID = document["TaskId"].GetString();

	cout << "=================== test_submit_async_task  ============================" << endl;
}

void test_get_task_result()
{
	cout << "===================  test_get_task_result ============================" << endl;

	get_task_result_request param;
	string result;
	param.region = "cn-bj2";
	param.task_id = ASYNC_TASK_ID;
	param.access_token = ACCESS_TOKEN;
	int ret = get_task_result(param, result);
	cout << "ret " << ret << endl;
	cout <<  result << endl;
	cout << "=================== test_get_task_result  ============================" << endl;
}


int main(int argc, char *argv[])
{
	//获取可用的Docker镜像仓库列表，包括公有和私有的
	test_get_bucket_image_list();
	//获取可用的Docker镜像列表，包括公有和私有的
	test_get_docker_image_list();
	//获取所有可用的Docker镜像名称，包括公有和私有的
	test_get_all_images();
	//创建Docker镜像bucket
	test_create_docker_image_bucket();
	//获取提交的任务列表
	test_get_task_list();
	//查看任务详情
	test_get_task_detail();
	//获取授权Token
	test_get_access_token();
	//提交同步任务
	test_submit_task();
	//提交异步任务
	test_submit_async_task();
	sleep(2);
	//获取异步任务结果
	test_get_task_result();
	return 0;
}
