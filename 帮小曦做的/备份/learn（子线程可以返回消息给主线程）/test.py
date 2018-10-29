import sys
import threading
import queue
q = queue.Queue()
def worker1(x, y):
	func_name = sys._getframe().f_code.co_name
	print("%s run ..." % func_name)
	q.put((x + y, func_name))
def worker2(x, y):
	func_name = sys._getframe().f_code.co_name
	print( "%s run ...." % func_name)
	q.put((x - y, func_name))
if __name__ == '__main__':
	result = list()
	t1 = threading.Thread(target=worker1, name='thread1', args=(10, 5, ))
	t2 = threading.Thread(target=worker2, name='thread2', args=(20, 1, ))
	print( '-' * 50)
	t1.start()
	t2.start()
	t1.join()
	t2.join()
	while not q.empty():
		result.append(q.get())
	for item in result:
		if item[1] == worker1.__name__:
			print( "%s 's return value is : %s" % (item[1], item[0]))
		elif item[1] == worker2.__name__:
			print( "%s 's return value is : %s" % (item[1], item[0]))
