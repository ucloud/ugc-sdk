# -*- coding: UTF-8 -*-
from config import *
from sdk import apiInterface
import threading
import time


class TokenManager (threading.Thread):
    def __init__(self, tokenExpireTime=TOKEN_EXPIRE_TIME):
        threading.Thread.__init__(self)
        self.tokenExpireTime = tokenExpireTime
        self.tokenRefreshTime = tokenExpireTime - 300 if tokenExpireTime > 300 else tokenExpireTime/2
        self.failRetryTimes = 10
        self.token = ""
        self.setDaemon(True)
        self._aquireToken()
        self.start()

    def run(self):
        print "TokenManager Starting with tokenRefreshTime : " + str(self.tokenRefreshTime) 
        while True:
            time.sleep(self.tokenRefreshTime)
            self._aquireToken()
        	
    def _aquireToken(self):
    	for i in range(self.failRetryTimes):
    		res =  apiInterface.GetAccessToken(ExpireIn=self.tokenExpireTime)
        	if res["RetCode"] == 0 and res["AccessToken"] is not None and len(res["AccessToken"]) > 0:
        		self.token = res["AccessToken"]
        		print "get token success : " + self.token
        		break
        	else:
        		print "get token fail retrying after 3 second ... "
        		time.sleep(3) #retry

    def getToken(self):
    	return self.token
