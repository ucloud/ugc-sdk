# -*- coding: utf-8 -*-

from sdk import apiClient
from config import *
import sys
import json


#API 接口
def GetImageBucketList(Region=REGION, Limit=20, Offset=0, BucketName=None, BucketType=None, OrderBy=None):
	"""
	Args:
		Region String 地域名, 参见地域列表
		BucketName String Docker 镜像仓库名
		BucketType String All： 用户所有可见的仓库 User： 用户个人创建的仓库 Share：第三方公开镜像 默认： All
		OrderBy String Default: 默认排序 
		Limit Integer 默认为 20 
		Offset Integer 默认为 0 

	Returns:
		一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 "TotalCount"
		 "BucketSet" : 一个array Docker bucket列表
		 }
	"""
	parameters = { "Action" : "GetImageBucketList", "Region" :  Region, "Limit" : Limit, "Offset" : Offset }
	if BucketType is not None :parameters["BucketType"] = BucketType
	if BucketName is not None : parameters["BucketName"] = BucketName
	if OrderBy is not None : parameters["OrderBy"] = OrderBy
	response   = apiClient._get(COMMON_API_URL, parameters);
	return response


def GetDockerImageList(BucketName, Region=REGION, Limit=20, Offset=0, OrderBy=None):
	"""
	Args:
		Region String 地域名, 参见地域列表
		BuckName String Docker 镜像仓库名称
		OrderBy String Default: 默认排序
		Limit Integer 默认为 0 
		Offset Integer 默认为 20 

	Returns:
		一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 "TotalCount"
		 "ImageSet" : 一个array Docker 镜像列表
		 }
	"""
	parameters = { "Action" : "GetDockerImageList", "Region" :  Region, "BucketName" : BucketName, "Limit" : Limit, "Offset" : Offset }
	if OrderBy is not None : parameters["OrderBy"] = OrderBy
	response   = apiClient._get(COMMON_API_URL, parameters);
	return response


def GetAllImages(Region=REGION):
	"""
	Args:
		Region String 地域名, 参见地域列表

	Returns:
		一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 "TotalCount"
		 "ImageSet" : 一个array Docker 镜像列表
		 }
	"""
	parameters = { "Action" : "GetAllImages", "Region" :  Region}
	response   = apiClient._get(COMMON_API_URL, parameters);
	return response

def CreateDockerImageBucket(BucketName, Region=REGION):
	"""
	Args:
		Region String 地域名, 参见地域列表
		BuckName String Docker 镜像仓库名称

	Returns:
		一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 }
	"""
	parameters = { "Action" : "CreateDockerImageBucket", "Region" :  Region, "BucketName" : BucketName}
	response   = apiClient._get(COMMON_API_URL, parameters);
	return response


def GetTaskList(Region=REGION, Limit=20, Offset=0, TaskId=None, TaskName=None, TaskType= None, State = "None", OrderBy=None, Date=None):
	"""
	Args:
		Region String 地域名, 参见地域列表
		TaskId String Docker 任务 ID 不填返回所有 Id 的任务
		TaskName String 任务名称不填返回所有名称的任务
		TaskType String 同步任务 Sync异步任务 Async 默认为:All
		State String All:所有任务Running： 运行中Success： 成功Fail： 失败 默认为 ALL
		OrderBy String Default: 默认排序
		Limit Integer 默认为 0 
		Offset Integer 默认为 20 
		Date 日期 格式:2016-11-11

	Returns:
		一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 "TotalCount"
		 "TaskSet" : 一个array 任务列表
		 }
	"""
	parameters = { "Action" : "GetTaskList", "Region" :  Region, "Limit" : Limit, "Offset" : Offset }
	if TaskId is not None :parameters["TaskId"] = TaskId
	if TaskName is not None : parameters["TaskName"] = TaskName
	if TaskType is not None : parameters["TaskType"] = TaskType
	if State is not None : parameters["State"] = State
	if Date is not None: parameters["Date"] = Date
	response   = apiClient._get(COMMON_API_URL, parameters);
	return response

def GetTaskDetail(TaskId, Region=REGION):
	"""
	Args:
		Region String 地域名, 参见地域列表
		TaskId String Docker 任务 ID 不填返回所有 Id 的任务

	Returns:
		一个 dict 变量 
		{
		"Action" : String 响应动作
		"RetCode" : Integer 执行成功与否， 0 表示成功， 其他值则为错误代码
		"Message" : String 错误原因
		"ImageName" : String 任务使用的镜像名
		"Cmd"  : String 镜像运行参数
		"OutputDir" : String 输出文件目录
		"OutputFileName" : String 输出文件名称
		"TaskType" : String 同步任务 Sync异步任务 Async
		"TaskId" : String 任务 ID
		"TaskName" : String 任务名称
		"Owner" : String 任务创建者
		"State" : String Running： 运行中Success： 成功Fail： 失败
		"CreateTime" : Integer 创建时间， 格式为 Unix 时间戳
		"StartTime" : Integer 任务开始时间， 格式为 Unix时间戳
		"EndTime" : Integer 任务结束时间， 格式为 Unix时间戳
		"Timeout" : Integer 超时时间
		"StdoutBrief" : String 任务标准输出概要
		"StderrBrief" : String 任务标准错误概要
		}
	"""
	parameters = { "Action" : "GetTaskDetail", "Region" :  Region, "TaskId" : TaskId}
	response   = apiClient._get(COMMON_API_URL, parameters);
	return response

def GetAccessToken(Region=REGION, ExpireIn=7200, GrantType=None):
	"""
	Args:
		Region String 地域名, 参见地域列表
		ExpireIn Integer 有效时间默认为 7200 秒
		GrantType String 默认为 Task
	Returns:
		一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 "AccessToken" : 授权 Token
		 "ExpireIn" : Token 有效时间
		 }
	"""
	parameters = { "Action" : "GetAccessToken", "Region" :  Region, "ExpireIn" : ExpireIn}
	if GrantType is not None : parameters["GrantType"] = GrantType
	response   = apiClient._get(COMMON_API_URL, parameters);
	return response

def SubmitTask(ImageName, AccessToken, Region=REGION, Cmd=None, OutputDir=None, 
	                    OutputFileName=None, TaskType=None, TimeOut=None, TaskName=None, Data=None):
	"""
	Args:
		Region String 地域名, 参见地域列表
		ImageName String 任务使用的镜像名称
		Cmd String 镜像运行参数
		OutputDir String 输出文件目录
		OutputFileName String 输出文件名称
		TaskType String 同步任务 Sync异步任务 Async 默认为 Sync
		TimeOut Integer 超时时间
		TaskName String 任务名称
		AccessToken String 授权 Token
		Data bytes post数据

	Returns:
		失败: 一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 }
		 同步成功: 返回结果二进制数据
		 异步成功: 一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 "TaskId"  ： 任务id
		 }
	"""
	parameters = { "Action" : "SubmitTask", "Region" :  Region, "AccessToken" : AccessToken, "ImageName" : ImageName}
	if Cmd is not None : parameters["Cmd"] = Cmd
	if OutputDir is not None : parameters["OutputDir"] = OutputDir
	if OutputFileName is not None : parameters["OutputFileName"] = OutputFileName
	if TaskType is not None : parameters["TaskType"] = TaskType
	if TimeOut is not None : parameters["TimeOut"] = TimeOut
	if TaskName is not None : parameters["TaskName"] = TaskName
	response   = apiClient._post(TASK_API_URL, parameters, Data, Content_Type="application/octet-stream");
	return response

def GetTaskResult(AccessToken, TaskId, Region=REGION):
	"""
	Args:
		Region String 地域名, 参见地域列表
		TaskId String Docker 任务 ID 不填返回所有 Id 的任务
		AccessToken String 授权 Token

	Returns:
		失败 或未运行完成: 一个 dict 变量 
		{
		 "Action" :  响应动作,
		 "RetCode" : 执行成功与否， 0 表示成功，
		 "Message" : ,
		 "State" : 任务运行状态
		 }
		 成功: 返回结果二进制数据
	"""
	parameters = { "Action" : "GetTaskResult", "Region" :  Region, "TaskId" : TaskId, "AccessToken" : AccessToken}
	response   = apiClient._get(TASK_API_URL, parameters);
	return response




