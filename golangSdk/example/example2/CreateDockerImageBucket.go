//CreateDockerImageBucket.go
package main

import (
	"fmt"
	"ugc-sdk/golangSdk/golandSdk"
)

//PUBLIC_KEY和PRIVATE_KEY替换为您的
var PUBLIC_KEY string = ""
var PRIVATE_KEY string = ""

func createDockerImageBucket() {
	var request ugcapi.CreateDockerImageBucketRequest
	request.Region = "cn-bj2"
	request.BucketName = "testBucketName"
	request.PublicKey = PUBLIC_KEY
	request.PrivateKey = PRIVATE_KEY

	rsp, err := ugcapi.CreateDockerImageBucket(request)
	if err != nil {
		fmt.Println("CreateDockerImageBucket", err)
		return
	} else {
		fmt.Println(rsp)
	}
	return
}

func main() {
	createDockerImageBucket()
	return
}
