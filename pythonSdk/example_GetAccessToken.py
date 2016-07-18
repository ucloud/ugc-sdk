#!/usr/bin/env python
# -*- coding: utf-8 -*-
from sdk import apiInterface
from sdk import tokenManager
import json
import time

if __name__=='__main__':
	print "example 1 get accesstoken 1"
	response = apiInterface.GetAccessToken()
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""


	print "example 2 get accesstoken by token manager"
	tokenManager = tokenManager.TokenManager(tokenExpireTime=6)
	print " get token " + tokenManager.getToken()
	time.sleep(2)
	print " get token after 2 second " + tokenManager.getToken()
	time.sleep(2)
	print " get token after 4 second " + tokenManager.getToken()
