#!/usr/bin/env python
# -*- coding: utf-8 -*-
from sdk import apiInterface
import json
# def GetTaskList(Region=REGION, Limit=20, Offset=0, TaskId=None, TaskName=None, TaskType= None, State = "None", OrderBy=None):
# def GetTaskDetail(TaskId, Region=REGION):
if __name__=='__main__':
	print "example 1 GetTaskList 1"
#	response = apiInterface.GetTaskList(Limit=200)
#	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""#!/usr/bin/env python
# -*- coding: utf-8 -*-
from sdk import apiInterface
import json
# def GetTaskList(Region=REGION, Limit=20, Offset=0, TaskId=None, TaskName=None, TaskType= None, State = "None", OrderBy=None):
# def GetTaskDetail(TaskId, Region=REGION):
if __name__=='__main__':
	print "example 1 GetTaskList 1"
	response = apiInterface.GetTaskList(Limit=5, Date="2016-11-11")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 2 GetTaskList 2"
	response = apiInterface.GetTaskList(Limit=5, TaskName="test_task_01", Date="2016-11-11")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 3 GetTaskList 3"
	response = apiInterface.GetTaskList(Limit=5, TaskId="notexsit", Date="2016-11-11")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 4 GetTaskList 4"
	response = apiInterface.GetTaskList(Limit=5, TaskType="Sync", Date="2016-11-11")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 5 GetTaskList 5"
	response = apiInterface.GetTaskList(Limit=5, State="Fail", Date="2016-11-11")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 6 GetTaskDetail 1"
	response = apiInterface.GetTaskList(Limit=2, Date="2016-11-11")
	for task in response["TaskSet"]:
		taskid = task["TaskId"]
		response2 = apiInterface.GetTaskDetail(TaskId=taskid)
		print json.dumps(response2, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 7 GetTaskDetail 2"
	response= apiInterface.GetTaskDetail(TaskId="notexsit")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""



	print "example 7 GetTaskDetail 2"
	response= apiInterface.GetTaskDetail(TaskId="303c13b4-1f7a-43d1-8b7d-ddff42a41aca")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""
