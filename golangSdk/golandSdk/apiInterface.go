package ugcapi

import (
	"strconv"
)

type ImageBucketListParam struct {
	Region     string //required
	BucketName string //option  Docker镜像仓库名称，不填返回所有的镜像仓库
	BucketType string //option  默认All  All：用户所有可见的仓库 User：用户个人创建的仓库 Share：第三方公开镜像
	Orderby    string //option  Default 默认排序
	Limit      int    //option query num 默认20
	Offset     int    //option  默认为0
	PublicKey  string //required  用户的 公钥
	PrivateKey string //required  用户的 私钥
}

//获取可用的Docker镜像仓库列表，包括公有和私有的
func GetBucketImagesList(request ImageBucketListParam) (result string, err error) {
	var paramMap map[string]string = make(map[string]string)

	paramMap["Region"] = request.Region
	if len(request.BucketName) != 0 {
		paramMap["BucketName"] = request.BucketName
	}

	if len(request.BucketType) != 0 {
		paramMap["BucketType"] = request.BucketType
	}

	if len(request.Orderby) != 0 {
		paramMap["OrderBy"] = request.Orderby
	}

	if request.Limit != 0 {
		paramMap["Limit"] = strconv.Itoa(request.Limit)
	}

	if request.Offset != 0 {
		paramMap["Offset"] = strconv.Itoa(request.Offset)
	}

	paramMap["Action"] = "GetImageBucketList"
	paramMap["PublicKey"] = request.PublicKey

	signature := Vefsig(request.PrivateKey, paramMap)
	paramMap["Signature"] = signature
	url := GenerateUrl(COMMON_API_URL, paramMap)

	return HttpGet(url)
}

type DockerImageListReqest struct {
	Region     string //required
	BucketName string //required  Docker镜像仓库名称
	Orderby    string //option  Default: 默认排序
	Limit      int    //option query num 默认20
	Offset     int    //option 默认0
	PublicKey  string //required  用户的 公钥
	PrivateKey string //required  用户的 私钥
}

//获取可用的Docker镜像列表，包括公有和私有的
func GetDockerImagesList(request DockerImageListReqest) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["BucketName"] = request.BucketName

	if len(request.Orderby) != 0 {
		paramMap["OrderBy"] = request.Orderby
	}

	if request.Limit != 0 {
		paramMap["Limit"] = strconv.Itoa(request.Limit)
	}

	if request.Offset != 0 {
		paramMap["Offset"] = strconv.Itoa(request.Offset)
	}

	paramMap["Action"] = "GetDockerImageList"
	paramMap["PublicKey"] = request.PublicKey

	signature := Vefsig(request.PrivateKey, paramMap)
	paramMap["Signature"] = signature
	url := GenerateUrl(COMMON_API_URL, paramMap)

	return HttpGet(url)
}

type AllImagesRequest struct {
	Region     string //required
	PublicKey  string //required  用户的 公钥
	PrivateKey string //required  用户的 私钥
}

//获取所有可用的Docker镜像名称，包括公有和私有的
func GetAllImage(request AllImagesRequest) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["Action"] = "GetAllImages"
	paramMap["PublicKey"] = request.PublicKey

	signature := Vefsig(request.PrivateKey, paramMap)
	paramMap["Signature"] = signature
	url := GenerateUrl(COMMON_API_URL, paramMap)

	return HttpGet(url)
}

type CreateDockerImageBucketRequest struct {
	Region     string //required
	BucketName string //required
	PublicKey  string //required  用户的 公钥
	PrivateKey string //required  用户的 私钥
}

//创建Docker镜像bucket
func CreateDockerImageBucket(request CreateDockerImageBucketRequest) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["Action"] = "CreateDockerImageBucket"
	paramMap["BucketName"] = request.BucketName
	paramMap["PublicKey"] = request.PublicKey

	signature := Vefsig(request.PrivateKey, paramMap)
	paramMap["Signature"] = signature
	url := GenerateUrl(COMMON_API_URL, paramMap)

	return HttpGet(url)
}

type GetTaskListRequest struct {
	Region     string //required
	TaskId     string //option 任务ID 不填返回所有Id的任务
	TaskName   string //option 任务名称  不填返回所有名称的任务
	TaskType   string //option  同步任务"Sync"  异步任务 "Async"  默认"All"
	State      string //option  Running：运行中  Success：成功  Fail：失败  默认为 All:所有
	Orderby    string //option 排序
	Limit      int    //optio   默认为20
	Offset     int    //option  默认为0
	PublicKey  string //required  用户的 公钥
	PrivateKey string //required  用户的 私钥
}

// 获取提交的任务列表
func GetTaskList(request GetTaskListRequest) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["Action"] = "GetTaskList"
	paramMap["PublicKey"] = request.PublicKey

	if len(request.TaskId) != 0 {
		paramMap["TaskId"] = request.TaskId
	}

	if len(request.TaskName) != 0 {
		paramMap["TaskName"] = request.TaskName
	}

	if len(request.TaskType) != 0 {
		paramMap["TaskType"] = request.TaskType
	}

	if len(request.State) != 0 {
		paramMap["State"] = request.State
	}

	if len(request.Orderby) != 0 {
		paramMap["OrderBy"] = request.Orderby
	}

	if request.Limit != 0 {
		paramMap["Limit"] = strconv.Itoa(request.Limit)
	}

	if request.Offset != 0 {
		paramMap["Offset"] = strconv.Itoa(request.Offset)
	}

	signature := Vefsig(request.PrivateKey, paramMap)
	paramMap["Signature"] = signature
	url := GenerateUrl(COMMON_API_URL, paramMap)

	return HttpGet(url)

}

type GetTaskDetailRequest struct {
	Region     string //required
	TaskId     string //required
	PublicKey  string //required  用户的 公钥
	PrivateKey string //required  用户的 私钥
}

// 查看任务详情
func GetTaskDetail(request GetTaskDetailRequest) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["Action"] = "GetTaskDetail"
	paramMap["TaskId"] = request.TaskId
	paramMap["PublicKey"] = request.PublicKey

	signature := Vefsig(request.PrivateKey, paramMap)
	paramMap["Signature"] = signature
	url := GenerateUrl(COMMON_API_URL, paramMap)

	return HttpGet(url)
}

type GetAccessTokenRequest struct {
	Region     string //required
	ExpireIn   int    //option  token 有效时间 单位秒  默认 7200 秒
	GrantType  string //option  默认为task
	PublicKey  string //required  用户的 公钥
	PrivateKey string //required  用户的 私钥
}

//提交任务接口使用AccessToken做身份验证，此接口用于获取AccessToken
func GetAccessToken(request GetAccessTokenRequest) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["Action"] = "GetAccessToken"
	if len(request.GrantType) != 0 {
		paramMap["GrantType"] = request.GrantType
	}
	if request.ExpireIn != 0 {
		paramMap["ExpireIn"] = strconv.Itoa(request.ExpireIn)
	}

	paramMap["PublicKey"] = request.PublicKey

	signature := Vefsig(request.PrivateKey, paramMap)
	paramMap["Signature"] = signature
	url := GenerateUrl(COMMON_API_URL, paramMap)

	return HttpGet(url)

}

type SubmitTaskRequest struct {
	Region         string //required
	ImageName      string //required
	Cmd            string //option   命令行参数
	OutputDir      string //option   结果输出路径
	OutputFilename string //option 结果保存文件名
	TaskType       string //option "Sync"  同步 "Async"  异步
	Timeout        int    //option  默认10s  单位为妙
	TaskName       string //option   任务名称
	AccessToken    string //required
}

/* 提交任务

返回值
提交失败
{
	"Action" : "SubmitTaskResponse",
	"RetCode" : -1,
	"Message" : “All Fail”
}


同步任务提交成功

返回任务输出的二进制tar文件，目录结构如下，子文件仅在不为空的时候返回

Output-> 返回的tar格式二进制文件
Stdout  任务的标准输出
Stderr   任务的标准错误输出
OutputDir\OutputFileName  任务设置的文件输出

异步任务提交成功
{
	"Action" : "SubmitTaskResponse",
	"RetCode" : 0,
	"Message" : “”,
	"TaskId" : “123455”,
}

*/

func SubmitTask(request SubmitTaskRequest, data string) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["ImageName"] = request.ImageName
	paramMap["AccessToken"] = request.AccessToken
	paramMap["Action"] = "SubmitTask"

	if len(request.Cmd) != 0 {
		paramMap["Cmd"] = request.Cmd
	}
	if len(request.OutputDir) != 0 {
		paramMap["OutputDir"] = request.OutputDir
	}
	if len(request.OutputFilename) != 0 {
		paramMap["OutputFileName"] = request.OutputFilename
	}
	if len(request.TaskType) != 0 {
		paramMap["TaskType"] = request.TaskType
	}
	if request.Timeout != 0 {
		paramMap["TimeOut"] = strconv.Itoa(request.Timeout)
	}
	if len(request.TaskName) != 0 {
		paramMap["TaskName"] = request.TaskName
	}

	url := GenerateUrl(TASK_API_URL, paramMap)

	return HttpPost(url, data)
}

type GetTaskResultRequest struct {
	Region      string //required
	TaskId      string //requierd
	AccessToken string //required
}

//获取异步任务的运行结果
/*
获取任务结果失败
{
	"Action" : "GetTaskResultResponse",
	"RetCode" : -1,
	"Message" : “Not Finish”,
	"State" : “Running”,
}

获取任务结果成功
Output-> 返回的tar格式二进制文件
Stdout  任务的标准输出
tmp\result 任务设置的文件输出

*/

func GetTaskResult(request GetTaskResultRequest) (rsp string, err error) {
	paramMap := make(map[string]string)
	paramMap["Region"] = request.Region
	paramMap["TaskId"] = request.TaskId
	paramMap["AccessToken"] = request.AccessToken
	paramMap["Action"] = "GetTaskResult"

	url := GenerateUrl(TASK_API_URL, paramMap)

	return HttpGet(url)
}
