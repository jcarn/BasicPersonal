import os

#Checks for all .class files without accompanying
dir_list = os.listdir(os.getcwd())
print dir_list
for file_name in dir_list:
    if ".class" in file_name and (file_name.strip(".class") + ".java" ) not in dir_list:
        os.remove(file_name)
    
print dir_list
command = ""
os.system('printer.py')
