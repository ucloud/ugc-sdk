#!/usr/bin/env python
# -*- coding: utf-8 -*-
from sdk import apiInterface
import json

if __name__=='__main__':
	print "example 1 get image bucket list 1"
	response = apiInterface.GetImageBucketList()
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 2 get image bucket list 2"
	response = apiInterface.GetImageBucketList(BucketName="mytestbucket")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 3 get image bucket list 3"
	response = apiInterface.GetImageBucketList(Limit=1)
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""

	print "example 4 get image bucket list 4"
	response = apiInterface.GetImageBucketList(BucketType="User")
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""
