package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"ugc-sdk/golangSdk/golandSdk"
)

//PUBLIC_KEY和PRIVATE_KEY替换为您的
var PUBLIC_KEY string = ""
var PRIVATE_KEY string = ""
var ACCESS_TOKEN string = ""

func getAccessToken() {
	type AccessTokenRsp struct {
		AccessToken string
	}
	var request ugcapi.GetAccessTokenRequest
	request.Region = "cn-bj2"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY
	rsp, err := ugcapi.GetAccessToken(request)
	if err != nil {
		fmt.Println("GetAccessToken failed ", err)
		return
	}
	bytebody := []byte(rsp)
	var atp AccessTokenRsp
	err = json.Unmarshal(bytebody, &atp)
	if err != nil {
		fmt.Println("json failed ", err)
		return
	}
	ACCESS_TOKEN = atp.AccessToken
}

func submitTask() {
	var request ugcapi.SubmitTaskRequest
	request.Region = "cn-bj2"
	//替换为你的镜像名
	request.ImageName = "cn-bj2.ugchub.service.ucloud.cn/mytestbucket/wordCount:first"
	request.OutputDir = "/tmp"
	request.OutputFilename = "result"
	request.TaskName = "for_test"
	request.TaskType = "Sync"
	request.AccessToken = ACCESS_TOKEN
	//替换为你需要上传文件的路径
	inputFilePath := "/yourPath/inputfile1"
	inputFile, err := ioutil.ReadFile(inputFilePath)
	//输入数据必须发送Post请求，inputFile通过Body传送
	rsp, err := ugcapi.SubmitTask(request, string(inputFile))

	if err != nil {
		fmt.Println("SubmitTask failed ", err)
		return
	} else {
		//返回的处理结果，保存为outputfile1
		err := ioutil.WriteFile("outputfile1", []byte(rsp), 0600)
		if err != nil {
			fmt.Println("submitTask ioutil.WriteFile() error ", err)
		}
	}
	return
}

func main() {
	getAccessToken()
	submitTask()
}
