package main

import (
	"fmt"
	"io/ioutil"
	"os"
	"wordCount/logic"
)

func main() {
	//必须从标准输入读取数据
	inputFileText, err := ioutil.ReadAll(os.Stdin)
	if err != nil {
		fmt.Print("ioutil.ReadAll error", err)
		return
	}
	outputFilePath := "/tmp/result"
	//wordCount算法核心逻辑
	outputFileText := logic.CountTestBase(string(inputFileText))
	//结果写到Docker容器中相应的目录
	err = ioutil.WriteFile(outputFilePath, []byte(outputFileText), os.ModePerm)
	if err != nil {
		fmt.Print("ioutil.WriteFile error", err)
	}
	return
}
