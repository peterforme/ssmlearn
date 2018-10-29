# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'test.ui'
#
# Created by: PyQt5 UI code generator 5.11.3
#
# WARNING! All changes made in this file will be lost!
import os
import sys
from PyQt5 import QtCore,QtGui,QtWidgets
from PyQt5.QtWidgets import QApplication, QMainWindow, QFileDialog, QMessageBox
from openpyxl import Workbook
from openpyxl import load_workbook
from extractExl import get_desktop,convert,get_file_list
import threading
import time
import queue


q = queue.Queue()
class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(800, 600)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.radioButton = QtWidgets.QRadioButton(self.centralwidget)
        self.radioButton.setGeometry(QtCore.QRect(100, 220, 89, 16))
        self.radioButton.setObjectName("radioButton")
        self.radioButton.setChecked(True)
        self.radioButton_2 = QtWidgets.QRadioButton(self.centralwidget)
        self.radioButton_2.setGeometry(QtCore.QRect(190, 220, 89, 16))
        self.radioButton_2.setObjectName("radioButton_2")
        self.label = QtWidgets.QLabel(self.centralwidget)
        self.label.setGeometry(QtCore.QRect(70, 40, 271, 41))
        self.label.setObjectName("label")
        self.label.setWordWrap(True)
        self.label_2 = QtWidgets.QLabel(self.centralwidget)
        self.label_2.setGeometry(QtCore.QRect(540, 40, 241, 51))
        self.label_2.setObjectName("label_2")
        self.label_2.setWordWrap(True)
        self.pushButton = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton.setGeometry(QtCore.QRect(70, 110, 75, 23))
        self.pushButton.setObjectName("pushButton")
        self.pushButton_2 = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton_2.setGeometry(QtCore.QRect(540, 110, 75, 23))
        self.pushButton_2.setObjectName("pushButton_2")
        self.pushButton_3 = QtWidgets.QPushButton(self.centralwidget)
        self.pushButton_3.setGeometry(QtCore.QRect(340, 340, 75, 23))
        self.pushButton_3.setObjectName("pushButton_3")
        self.label_3 = QtWidgets.QLabel(self.centralwidget)
        self.label_3.setGeometry(QtCore.QRect(510, 480, 201, 16))
        self.label_3.setObjectName("label_3")
        self.label_4 = QtWidgets.QLabel(self.centralwidget)
        self.label_4.setGeometry(QtCore.QRect(350, 400, 201, 16))
        self.label_4.setObjectName("label_4")
        self.textBrowser = QtWidgets.QTextBrowser(self.centralwidget)
        self.textBrowser.setGeometry(QtCore.QRect(20, 260, 301, 271))
        self.textBrowser.setObjectName("textBrowser")
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 800, 23))
        self.menubar.setObjectName("menubar")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "excel处理辅助工具v1.0"))
        self.radioButton.setText(_translate("MainWindow", "关联单位"))
        self.radioButton_2.setText(_translate("MainWindow", "非关联单位"))
        self.label.setText(_translate("MainWindow", "公司表路径"))
        self.label_2.setText(_translate("MainWindow", "汇总表路径"))
        self.pushButton.setText(_translate("MainWindow", "选择公司表"))
        self.pushButton_2.setText(_translate("MainWindow", "选择汇总表"))
        self.pushButton_3.setText(_translate("MainWindow", "开始转换"))
        self.label_3.setText(_translate("MainWindow", "Made by @彭小华(微信1009480326)"))
        self.label_4.setText(_translate("MainWindow", "转换结果"))
        self.pushButton.clicked.connect(self.select_company)
        self.pushButton_2.clicked.connect(self.select_total)
        self.pushButton_3.clicked.connect(self.execute)

    def select_company(self):
        normal_dir = get_desktop() + "\\往来款"
        if not os.path.exists(normal_dir):
                normal_dir = get_desktop()
        file_location, filetype = QFileDialog.getOpenFileName(self.centralwidget,
                                    "选取文件",
                                    normal_dir, # 起始路径
                                    "Excel Files (*.xlsx)")   # 设置文件扩展名过滤,用双分号间隔

        if file_location == "":
            print("\n取消选择")
            return

        self.label.setText(file_location)

    def select_total(self):
        normal_dir = get_desktop() + "\\往来款"
        if not os.path.exists(normal_dir):
                normal_dir = get_desktop()
        file_location, filetype = QFileDialog.getOpenFileName(self.centralwidget,
                                    "选取文件",
                                    normal_dir, # 起始路径
                                    "Excel Files (*.xlsx)")   # 设置文件扩展名过滤,用双分号间隔

        if file_location == "":
            print("\n取消选择")
            return

        self.label_2.setText(file_location)

    def execute(self):
        try:
            self.label_4.setText("开始转换")
            #是否为关联
            is_related = self.radioButton.isChecked()

            child_file_location = self.label.text()
            if not os.path.exists(child_file_location):
                print("路径不存在")
                self.label_4.setText("转换失败:路径不存在")
                QMessageBox.information(self.centralwidget, '错误', '文件路径有误，请重新选择!', QMessageBox.Ok)
                return
            #获取子表的目录
            child_file_dir = os.path.split(child_file_location)[0]
            #获取所有excel文件
            all_file = get_file_list(child_file_dir,".xlsx")
            print(all_file)

            total_file_location = self.label_2.text()
            if not os.path.exists(total_file_location):
                print("路径不存在")
                self.label_4.setText("转换失败:路径不存在")
                QMessageBox.information(self.centralwidget, '错误', '文件路径有误，请重新选择!', QMessageBox.Ok)
                return

            #汇总表只打开一次
            total_book = load_workbook(total_file_location, data_only=True)
            total_book.guess_types = True   #猜测格式类型
            total_sheet=total_book.active

            success_num = fail_num = 0
            threads = []
            result = list()
            error_info = ""
            #真正的转换操作
            for item in all_file:
                t = threading.Thread(target=convert,args=(is_related,item,total_sheet,q,))
                threads.append(t)
                t.start()

            for thread_item in threads:
                thread_item.join()

            #获取到所有子线程返回的信息
            while not q.empty():
                result.append(q.get())

            for item in result:
                print(item)
                if item.find("#转换成功#:") == -1:
                    fail_num += 1
                    error_info += item + "\n\n"
                else:
                    success_num += 1

            total_book.save(total_file_location)

            self.label_4.setText("转换完成!成功:"+str(success_num) + "  失败:"+str(fail_num) )
            self.textBrowser.setText(error_info)

        #只能捕获到主线程的异常，所以如果采用多线程的话应该用其他方式
        except Exception as e:
            print(e)
            self.label_4.setText("转换失败")
            QMessageBox.information(self.centralwidget, '错误', '出现异常，请和作者联系:'+str(e), QMessageBox.Ok)




if __name__ == "__main__":
    app = QtWidgets.QApplication(sys.argv)
    ex = Ui_MainWindow()
    w = QtWidgets.QMainWindow()
    ex.setupUi(w)
    w.show()
    sys.exit(app.exec_())
