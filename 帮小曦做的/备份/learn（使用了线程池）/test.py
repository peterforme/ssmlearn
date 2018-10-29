#!/usr/bin/env python
# coding:utf-8

import time
import random
import threadpool

HEHE = dict()

def sayhello(name, v):
    global HEHE
    if name in HEHE:
        HEHE[name] = HEHE[name] + '+' + v
    else:
        HEHE[name] = v
    return 1;
    #time.sleep(2)

#定义结果统一回调
totalsum=0
def onresult(req,sum):
    global totalsum
    totalsum+=sum

#name_list = [(['caoshuai', '1'], None), (['yangliu', '2'], None),(['caoshuai', '3'], None),(['ss', '10'], None),(['wwwwww', '12'], None),]
name_list = [(['caoshuai','1'],None),(['caoshuai','2'],None),(['a','3'],None),(['ss','10'],None),(['wwwwww','12'],None),(['m','12'],None),(['n','12'],None),(['b','12'],None),(['v','12'],None),(['x','12'],None),(['z','12'],None),]

#name_list = [1, -5, 6, -4]

start_time = time.time()

pool_t = threadpool.ThreadPool(4)

requestss = threadpool.makeRequests(sayhello, name_list,onresult)

[pool_t.putRequest(req) for req in requestss]

pool_t.wait()
print(HEHE)
print ("%s second" % (time.time()-start_time))
print(totalsum)

while True:
    time.sleep(1)
