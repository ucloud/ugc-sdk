#!/usr/bin/env python
# -*- coding: utf-8 -*-
from sdk import apiInterface
import json

if __name__=='__main__':
	print "example 1 get all images "
	response = apiInterface.GetAllImages()
	print json.dumps(response, sort_keys=True, indent=4, separators=(',', ': ')) 
	print ""
