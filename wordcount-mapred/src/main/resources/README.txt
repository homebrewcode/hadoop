#Sample command line arguments when you are running on local windows machine
java wordcount file:///C:\Words.txt file:///C:\Counted

#Sample command line arguments when you are running on local unix machine
java wordcount file:///~/Words.txt file:///~/Counted

#Sample command line arguments when you are running on HDFS
java wordcount <hadoop_path>/Words.txt <hadoop_path>/Counted