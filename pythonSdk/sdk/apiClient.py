# -*- coding: utf-8 -*-
import hashlib, json
import urllib
import requests
from config import *
import platform
from . import __version__


_sys_info = '{0}; {1}'.format(platform.system(), platform.machine())
_python_ver = platform.python_version()
USER_AGENT = 'UGCSDK_Python/{0} ({1}; ) Python/{2}'.format(__version__, _sys_info, _python_ver)
_headers = {'User-Agent': USER_AGENT}
session=requests.session()


SENDREQFIAL=-1
RESPSTATUSCODEFIAL=-2
JOSNUMARSHFAIL=-3

def __res_wrapper(resp):
    if resp.status_code != 200:
        return {"RetCode" : RESPSTATUSCODEFIAL, "Message" : "resp.status_code : " + str(resp.status_code)}
    if resp.headers['Content-Type'] == "application/octet-stream":
        return resp.content
    try:
        a = resp.json()
    except Exception, e:
        return {"RetCode" : JOSNUMARSHFAIL, "Message" : "Exception : " + str(e)}
    return a


def __makeUrlWithCommonParam(url, params):
    params["PublicKey"] = PUBLIC_KEY
    params["Signature"] = __verfy_ac(PRIVATE_KEY, params)
    url += "?" + urllib.urlencode(params)
    return url

def _post(url, params, data, Content_Type=None):
    try:
        url = __makeUrlWithCommonParam(url, params)
        print("post :" + url)

        if Content_Type : _headers["Content-Type"] = Content_Type
        r = session.post(url, data=data, headers=_headers, timeout=30)
    except Exception as e:
        return {"RetCode" : SENDREQFIAL, "Message" : "Exception : " + str(e)}
    return __res_wrapper(r)


def _get(url, params):
    try:
        url = __makeUrlWithCommonParam(url, params)
        print("get :" + url)
        r = session.get(url, timeout=30)
    except Exception as e:
        return {"RetCode" : SENDREQFIAL, "Message" : "Exception : " + str(e)}
    return __res_wrapper(r)


# 签名算法
def __verfy_ac(private_key, params):
    items = params.items()
    items.sort()

    params_data = ""
    for key, value in items:
        params_data = params_data + str(key) + str(value)

    params_data = params_data+private_key
    
    '''use sha1 to encode keys'''
    hash_new = hashlib.sha1()
    hash_new.update(params_data)
    hash_value = hash_new.hexdigest()
    return hash_value
