#!/usr/bin/env python
# -*- coding: utf-8 -*-
from sdk import apiInterface
import json

if __name__=='__main__':
	print "example 1 get image list 1"
	response = apiInterface.GetDockerImageList(BucketName="library")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 2 get image list 2"
	response = apiInterface.GetDockerImageList(BucketName="library", Limit=1)
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 3 get image list 3"
	response = apiInterface.GetDockerImageList(BucketName="notexsit", Limit=1)
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""
