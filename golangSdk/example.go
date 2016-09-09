package main

import (
	"encoding/json"
	"fmt"
	"golang_sdk/golandSdk"
	"time"
)

var PUBLIC_KEY string = ""
var PRIVATE_KEY string = ""

func testGetBucketImagesList() {

	fmt.Println("\n\n=====================================================================")
	var request ugcapi.ImageBucketListParam
	request.Region = "cn-bj2"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.GetBucketImagesList(request)
	if err != nil {
		fmt.Println("GetBucketImagesList failed ", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")

}

func testGetDockerImagesList() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.DockerImageListReqest
	request.Region = "cn-bj2"
	request.BucketName = "library"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.GetDockerImagesList(request)
	if err != nil {
		fmt.Println("GetDockerImagesListfailed ", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")
}

func testGetAllImages() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.AllImagesRequest
	request.Region = "cn-bj2"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.GetAllImage(request)
	if err != nil {
		fmt.Println("GetDockerImagesListfailed ", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")
}

func testCreateDockerImageBucket() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.CreateDockerImageBucketRequest
	request.Region = "cn-bj2"
	request.BucketName = "wujianguo"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.CreateDockerImageBucket(request)
	if err != nil {
		fmt.Println("CreateDockerImageBucket", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")
}

func testGetTaskList() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.GetTaskListRequest
	request.Region = "cn-bj2"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.GetTaskList(request)
	if err != nil {
		fmt.Println("GetTaskList failed", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")
}

func testGetTaskDetail() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.GetTaskDetailRequest
	request.Region = "cn-bj2"
	request.TaskId = "d50fe44b-022e-45a4-a320-4b70554efcad"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.GetTaskDetail(request)
	if err != nil {
		fmt.Println("GetTaskDetail failed", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")
}

type AccessTokenRsp struct {
	AccessToken string
}

var ACCESS_TOKEN string = ""

func testGetAccessToken() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.GetAccessTokenRequest
	request.Region = "cn-bj2"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.GetAccessToken(request)
	if err != nil {
		fmt.Println("GetAccessToken failed", err)
		return
	} else {
		fmt.Println(rsp)
	}

	bytebody := []byte(rsp)
	var atp AccessTokenRsp
	err = json.Unmarshal(bytebody, &atp)
	if err != nil {
		fmt.Print("json failed\n")
		return
	}
	ACCESS_TOKEN = atp.AccessToken
	fmt.Println("\n\n=====================================================================")
}

func testSubmitTask() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.SubmitTaskRequest
	request.Region = "cn-bj2"

	request.ImageName = "cn-bj2.ugchub.service.ucloud.cn/library/helloworld:first"
	request.Cmd = "this is cmd"
	request.OutputDir = "/tmp"
	request.OutputFilename = "result"
	request.TaskName = "for_test"
	request.AccessToken = ACCESS_TOKEN

	rsp, err := ugcapi.SubmitTask(request, "wujianguo hao")
	if err != nil {
		fmt.Println("SubmitTask failed", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")
}

type AsyncTaskRsp struct {
	TaskId string
}

var ASYNC_TASK_ID string = ""

func testSubmitAsyncTask() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.SubmitTaskRequest
	request.Region = "cn-bj2"

	request.ImageName = "cn-bj2.ugchub.service.ucloud.cn/library/helloworld:first"
	request.AccessToken = ACCESS_TOKEN
	request.Cmd = "this is cmd"
	request.OutputDir = "/tmp"
	request.OutputFilename = "result"
	request.TaskName = "for_test"
	request.TaskType = "Async"

	rsp, err := ugcapi.SubmitTask(request, "wujianguo async test")
	if err != nil {
		fmt.Println("SubmitAsyncTask failed", err)
		return
	} else {
		fmt.Println(rsp)
	}

	bytebody := []byte(rsp)
	var atp AsyncTaskRsp
	err = json.Unmarshal(bytebody, &atp)
	if err != nil {
		fmt.Print("json failed\n")
		return
	}
	ASYNC_TASK_ID = atp.TaskId
	fmt.Println("\n\n=====================================================================")
}

func testGetTaskResult() {
	fmt.Println("\n\n=====================================================================")
	var request ugcapi.GetTaskResultRequest
	request.Region = "cn-bj2"
	request.TaskId = ASYNC_TASK_ID
	request.AccessToken = ACCESS_TOKEN

	rsp, err := ugcapi.GetTaskResult(request)
	if err != nil {
		fmt.Println("GetTaskResult failed", err)
		return
	} else {
		fmt.Println(rsp)
	}
	fmt.Println("\n\n=====================================================================")
}

func main() {
	testGetBucketImagesList()
	testGetDockerImagesList()

	testGetAllImages()
	testCreateDockerImageBucket()
	testGetTaskList()
	testGetTaskDetail()
	testGetAccessToken()
	testSubmitTask()

	testSubmitAsyncTask()
	time.Sleep(time.Second * 5)
	testGetTaskResult()
}
