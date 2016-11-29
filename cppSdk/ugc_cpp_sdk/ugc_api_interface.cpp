#include <stdio.h>
#include <curl/curl.h>
#include <stdlib.h>
#include <iostream>
#include <map>
#include <stdlib.h>
#include <string>
#include <stdint.h>

#include "ugc_api_interface.h"
#include "sha1.hpp"

static string COMMON_API_URL="http://api.ucloud.cn";
static string TASK_API_URL = "http://api.ugc.service.ucloud.cn";


static int http_get(string &strUrl, string &strResponse);
static int http_post(string url
		,  curl_slist *header
		, string post_data
		, string &response);

static string vefsig(string private_key
				, const map<string, string> &param_map);

static string generate_request_url(string api
		, const map<string, string> param_map);

int get_bucket_images_list(get_image_bucket_list_param &request
		, string &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;
	if (request.bucket_name.size() != 0)
		param_map["BucketName"] = request.bucket_name;

	if (request.bucket_type.size() != 0)
		param_map["BucketType"] = request.bucket_type;

	if (request.orderby.size() != 0)
		param_map["OrderBy"] = request.orderby;

	if (request.limit != 0)
		param_map["Limit"] = to_string((long long int)request.limit);

	if (request.offset != 0)
		param_map["Offset"] = to_string((long long int)request.offset);

	param_map["Action"]="GetImageBucketList";
	param_map["PublicKey"]=request.public_key;

	string signature = vefsig(request.private_key,  param_map);
	param_map["Signature"] = signature;
	string url = generate_request_url(COMMON_API_URL, param_map);

	int ret = http_get(url, result);
	return ret;
}


int get_docker_images_list(get_docker_image_list_reqest &request
		, string  &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;
	param_map["BucketName"] = request.bucket_name;

	if (request.orderby.size() != 0)
		param_map["OrderBy"] = request.orderby;

	if (request.limit != 0)
		param_map["Limit"] = to_string((long long int)request.limit);

	if (request.offset != 0)
		param_map["Offset"] = to_string((long long int)request.offset);

	param_map["Action"]="GetDockerImageList";
	param_map["PublicKey"]=request.public_key;

	string signature = vefsig(request.private_key,  param_map);
	param_map["Signature"] = signature;
	string url = generate_request_url(COMMON_API_URL, param_map);

	int ret = http_get(url, result);
	return ret;
}

int get_all_images(get_all_images_request &request
		, string  &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;

	param_map["Action"]="GetAllImages";
	param_map["PublicKey"]=request.public_key;

	string signature = vefsig(request.private_key,  param_map);
	param_map["Signature"] = signature;
	string url = generate_request_url(COMMON_API_URL, param_map);

	int ret = http_get(url,result);
	return ret;
}

int create_docker_image_bucket(create_docker_image_bucket_request &request
		, string  &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;
	param_map["BucketName"] = request.bucket_name;

	param_map["Action"]="CreateDockerImageBucket";
	param_map["PublicKey"]=request.public_key;

	string signature = vefsig(request.private_key,  param_map);
	param_map["Signature"] = signature;
	string url = generate_request_url(COMMON_API_URL, param_map);

	int ret = http_get(url,result);
	return ret;
}

int get_task_list(get_task_list_request &request
				, string  &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;

	if (request.task_id.size() != 0)
		param_map["TaskId"] = request.task_id;

	if (request.task_name.size() != 0)
		param_map["TaskName"] = request.task_name;

	if (request.task_type.size() != 0)
		param_map["TaskType"] = request.task_type;

	if (request.state.size() != 0)
		param_map["State"] = request.state;

	if (request.orderby.size() != 0)
		param_map["OrderBy"] = request.orderby;

	if (request.limit != 0)
		param_map["Limit"] = to_string((long long int)request.limit);

	if (request.offset != 0)
		param_map["Offset"] = to_string((long long int)request.offset);

	param_map["Action"]="GetTaskList";
	param_map["PublicKey"]=request.public_key;

	string signature = vefsig(request.private_key,  param_map);
	param_map["Signature"] = signature;
	param_map["Date"] = request.date;
	string url = generate_request_url(COMMON_API_URL, param_map);

	int ret = http_get(url,result);
	return ret;
}

int get_task_detail(get_task_detail_request &request
		, string &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;
	param_map["TaskId"] = request.task_id;

	param_map["Action"]="GetTaskDetail";
	param_map["PublicKey"]=request.public_key;

	string signature = vefsig(request.private_key,  param_map);
	param_map["Signature"] = signature;
	string url = generate_request_url(COMMON_API_URL, param_map);

	int ret = http_get(url,result);
	return ret;
}


int get_access_token(get_access_token_request &request
		, string &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;

	param_map["Action"]="GetAccessToken";
	param_map["PublicKey"]=request.public_key;

	string signature = vefsig(request.private_key,  param_map);
	param_map["Signature"] = signature;
	string url = generate_request_url(COMMON_API_URL, param_map);

	int ret = http_get(url,result);
	return ret;
}

int submit_task(submit_task_request &request
		, string &data
		, string &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;

	param_map["Action"]="SubmitTask";
	param_map["ImageName"]=request.image_name;
	param_map["AccessToken"]= request.access_token;

	if(request.cmd.size() != 0)
		param_map["Cmd"] = request.cmd;
	if (request.output_dir.size() != 0)
		param_map["OutputDir"] = request.output_dir;
	if (request.output_filename.size() != 0)
		param_map["OutputFileName"] = request.output_filename;
	if (request.task_type.size() != 0)
		param_map["TaskType"] = request.task_type;
	if (request.timeout != 0)
		param_map["TimeOut"] = to_string((long long int)request.timeout);
	if (request.task_name.size() != 0)
		param_map["task_name"] = request.task_name;

	string url = generate_request_url(TASK_API_URL, param_map);


	struct curl_slist *slist=NULL;
	slist = curl_slist_append(slist, "Content-Type:application/octet-stream");

	int ret = http_post(url, slist, data, result);
	curl_slist_free_all(slist);
	return ret;
}

int get_task_result(get_task_result_request &request
		, string &result)
{
	map<string, string> param_map;
	param_map["Region"] = request.region;

	param_map["Action"]="GetTaskResult";
	param_map["TaskId"]=request.task_id;
	param_map["AccessToken"]=request.access_token;

	string url = generate_request_url(TASK_API_URL, param_map);

	int ret = http_get(url,result);
	return ret;
}

size_t on_write_data(void* buffer, size_t size, size_t nmemb, void* lpVoid)  
{  
	string* str = dynamic_cast<std::string*>((std::string *)lpVoid);  
	if( NULL == str || NULL == buffer )  
	{  
		return -1;  
	}  

	char* pData = (char*)buffer;  
	str->append(pData, size * nmemb);  
	return nmemb;
} 

int http_get(string &strUrl, string &strResponse)  
{  
	CURLcode res;  
	CURL* curl = curl_easy_init();
	if(NULL == curl)  
	{  
		return CURLE_FAILED_INIT;  
	}  
	curl_easy_setopt(curl, CURLOPT_URL, strUrl.c_str());  
	curl_easy_setopt(curl, CURLOPT_READFUNCTION, NULL);  
	curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, on_write_data);  
	curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&strResponse);  
	curl_easy_setopt(curl, CURLOPT_NOSIGNAL, 1);  
	curl_easy_setopt(curl, CURLOPT_CONNECTTIMEOUT, 3);  
	curl_easy_setopt(curl, CURLOPT_TIMEOUT, 3);  
	res = curl_easy_perform(curl);  
	curl_easy_cleanup(curl);  
	return res;  
}

int http_post(string url,  curl_slist *header, string post_data, string &response)
{
	CURLcode res;  
	CURL* curl = curl_easy_init();  
	if(NULL == curl)  
	{  
		return CURLE_FAILED_INIT;  
	}  
	curl_easy_setopt(curl, CURLOPT_URL, url.c_str());  
	curl_easy_setopt(curl, CURLOPT_POST, 1);  
	curl_easy_setopt(curl, CURLOPT_HTTPHEADER, header);

	curl_easy_setopt(curl, CURLOPT_POSTFIELDS, post_data.c_str());  
	curl_easy_setopt(curl, CURLOPT_READFUNCTION, NULL);  
	curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, on_write_data);  
	curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *)&response);  
	curl_easy_setopt(curl, CURLOPT_NOSIGNAL, 1);  
	curl_easy_setopt(curl, CURLOPT_CONNECTTIMEOUT, 3);  
	curl_easy_setopt(curl, CURLOPT_TIMEOUT, 3);  
	res = curl_easy_perform(curl);  
	curl_easy_cleanup(curl);  
	return res;  
}

string vefsig(string private_key
		, const map<string, string> &param_map)
{
	string url_param;
	map<string, string>::const_iterator iter = param_map.begin();
	for(; iter != param_map.end(); ++iter)
	{
		url_param +=  iter->first;
		url_param += iter->second;
	}
	url_param += private_key;

	SHA1 checksum;
	checksum.update(url_param);
	return checksum.final();
}

string generate_request_url(string api, const map<string, string> param_map)
{
	string url = api;
	map<string, string>::const_iterator iter = param_map.begin();

	CURL *curl = curl_easy_init();
	for(; iter != param_map.end(); ++iter)
	{
		if (iter == param_map.begin())
		{
			url += "?";
		}else
		{
			url += "&";
		}
		url +=  iter->first;
		url +=  "=";
		char *value = curl_easy_escape(curl
				, iter->second.c_str()
				, iter->second.size());
		url += value;
	}
	return url;
}

