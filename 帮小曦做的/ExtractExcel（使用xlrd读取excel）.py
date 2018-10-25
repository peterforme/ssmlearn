# -*- coding: utf-8 -*-
import xlrd
import xlwt
child_xlsfile = r"D:\excel\2018年7月-应收-其他应收-预付款项汇总表-物流.xlsx"# 打开指定路径中的xls文件
child_book = xlrd.open_workbook(child_xlsfile)#得到Excel文件的book对象，实例化对象
child_sheet = child_book.sheet_by_index(0) # 通过sheet索引获得sheet对象


# 通过坐标读取表格中的数据
#关联部分
print("关联部分")
#2018年期初余额
relate_cell_value_12_2 = child_sheet.cell_value(12, 2)
print( "2018年期初余额:", relate_cell_value_12_2)
#本期增加
print("本期增加")
#计算一年内余额
relate_cell_value_12_3 = child_sheet.cell_value(12, 3)
relate_cell_value_12_4 = child_sheet.cell_value(12, 4)
relate_cell_value_12_5 = child_sheet.cell_value(12, 5)
relate_cell_value_12_increase_year = relate_cell_value_12_3 + relate_cell_value_12_4 + relate_cell_value_12_5
print("1年内:" + str(relate_cell_value_12_increase_year))
#计算1-2年余额
relate_cell_value_12_6 = child_sheet.cell_value(12, 6)
print("1-2年:" + str(relate_cell_value_12_6))
#计算2-3年余额
relate_cell_value_12_7 = child_sheet.cell_value(12, 7)
print("2-3年:" + str(relate_cell_value_12_7))
#计算3年以上余额
relate_cell_value_12_8 = child_sheet.cell_value(12, 8)
print("3年以上:" + str(relate_cell_value_12_8))
#本期减少
print("本期减少")
#计算一年内余额
relate_cell_value_12_9 = child_sheet.cell_value(12, 9)
relate_cell_value_12_10 = child_sheet.cell_value(12, 10)
relate_cell_value_12_11 = child_sheet.cell_value(12, 11)
relate_cell_value_12_decrease_year = relate_cell_value_12_9 + relate_cell_value_12_10 + relate_cell_value_12_11
print("1年内:" + str(relate_cell_value_12_decrease_year))
#计算1-2年余额
relate_cell_value_12_12 = child_sheet.cell_value(12, 12)
print("1-2年:" + str(relate_cell_value_12_12))
#计算2-3年余额
relate_cell_value_12_13 = child_sheet.cell_value(12, 13)
print("2-3年:" + str(relate_cell_value_12_13))
#计算3年以上余额
relate_cell_value_12_14 = child_sheet.cell_value(12, 14)
print("3年以上:" + str(relate_cell_value_12_14))
#2018年6月末余额
relate_cell_value_12_15 = child_sheet.cell_value(12, 15)
print( "2018年6月末余额:", relate_cell_value_12_15)
#计算一年内余额
relate_cell_value_12_16 = child_sheet.cell_value(12, 16)
relate_cell_value_12_17 = child_sheet.cell_value(12, 17)
relate_cell_value_12_18 = child_sheet.cell_value(12, 18)
relate_cell_value_12_year = relate_cell_value_12_16 + relate_cell_value_12_17 + relate_cell_value_12_18
print("1年内:" + str(relate_cell_value_12_year))
#计算1-2年余额
relate_cell_value_12_19 = child_sheet.cell_value(12, 19)
print("1-2年:" + str(relate_cell_value_12_19))
#计算2-3年余额
relate_cell_value_12_20 = child_sheet.cell_value(12, 20)
print("2-3年:" + str(relate_cell_value_12_20))
#计算3年以上余额
relate_cell_value_12_21 = child_sheet.cell_value(12, 21)
print("3年以上:" + str(relate_cell_value_12_21))
