# XLSX2SQLite

一个命令行程序，它具有四个命令行参数：
1．数据库名
2．xlsx文件名，带.xlsx后缀
3．Excel中的页名，当这个参数省略时，取第一个页
4．数据库表名，当这个参数省略时，用页名或当第三个参数也省略时用xlsx文件名前缀作为表名

程序打开xlsx文件，找到指定的页，在本地的SQLite数据库中建立指定名称的表。xlsx里的第一行作为表中的字段名，字段类型由xlsx数据推断得到。推断的规则为：
a.  整数的为int类型；
b.	浮点数为real类型；
c.	其他为char类型，大小为所有行中最大的长度。
然后程序将所有的行数据导入该表，并为该表建立一个PK，以表达行号。 程序输出表的结构（SQL）和行的数量。