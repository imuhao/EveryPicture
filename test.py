import os
import re
def get_all_modules(dir_path):
	settings_path = os.path.join(dir_path,'settings.gradle')
	if os.path.exists(settings_path):
		data = get_file_content(settings_path)
		print(data)
		modules = []

		for item in re.findall(r'''['"]:(.*?)['"]''',data):
			index = item.rfind(':')
			if index == -1:
				modules.append({'name':item,'path':item})
			else :
				modules.append({'name':item[index+1],'path':item.replace(":",os.sep)})
		print(modules)
		return modules


	
def get_file_content(path):
	if not path or  not os.path.isfile(path):
		return ''
	import codecs
	with codecs.open(path,encoding='utf-8')as f:
		return f.read()

get_all_modules(os.getcwd())
