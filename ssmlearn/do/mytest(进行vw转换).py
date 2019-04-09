# -*- coding: utf-8 -*-
import re

fpath = r'C:\temp\style.txt'
fw = open('./test.txt', 'w+')


with open(fpath, 'r') as f:
    for line in f.readlines():
        single_line = line # 把末尾的'\n'删掉
        print(single_line)

        #第一个符合的
        out = single_line
        temp = re.search(r'(\d+)px',out)
        while temp is not None:
            aa = int(temp.group(1))
            num = round(aa/7.5,2)
            result = str(num) + "vw"
            out = re.sub(r'(\d+)px',result, out,1)
            temp = re.search(r'(\d+)px',out)
        print(out)

        #写入文件
        fw.write(out)

fw.close()
