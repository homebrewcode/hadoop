########## HBase-SHELL-README
##Description: Some of the commonly used shell commands in HBase
##########

#How to find out where hbase is installed in a system?
hbase classpath

#List all the possible HBase commands.. No rocket science ;)
help
help "<cmd>" //Ex. help "create"

#List tables in HBase
list
list 'FB_.*' //Reg.ex search

#Create a table 
create 'FB_PROFILE','COLUMN_FAMILY1','COLUMN_FAMILY2','COLUMN_FAMILY3'
create 'FB_PROFILE','BASIC','WORK','EDU'

#Update a table
put 'FB_PROFILE','124163','BASIC:PHONETIC_NAME','Ron'
put 'FB_PROFILE','124163','BASIC:FIRST_NAME','Ronald'
put 'FB_PROFILE','124163','BASIC:LAST_NAME','Weasley'
put 'FB_PROFILE','124163','BASIC:EMAIL','rw@hogwarts.com'

put 'FB_PROFILE','124164','BASIC:PHONETIC_NAME','Harry'
put 'FB_PROFILE','124164','BASIC:FIRST_NAME','Harry'
put 'FB_PROFILE','124164','BASIC:LAST_NAME','Potter'
put 'FB_PROFILE','124164','BASIC:EMAIL','hp@hogwarts.com'

put 'FB_PROFILE','124165','BASIC:PHONETIC_NAME','Hermione'
put 'FB_PROFILE','124165','BASIC:FIRST_NAME','Hermione'
put 'FB_PROFILE','124165','BASIC:LAST_NAME','Granger'
put 'FB_PROFILE','124165','BASIC:EMAIL','hg@hogwarts.com'


#Drop a table
#One must always disable the table before it has to be deleted
is_enabled 'FB_PROFILE' //When a table does not exist, it always returns 'true'!
disable 'FB_PROFILE'
drop 'FB_PROFILE'

#Read from a table 
scan 'FB_PROFILE'
get 'FB_PROFILE','124163','BASIC'
get 'FB_PROFILE','124163','BASIC:PHONETIC_NAME'
get 'FB_PROFILE','124163',{COLUMN=>'BASIC:PHONETIC_NAME',VERSIONS=>3}

#Delete a row or specific column
deleteall 'FB_PROFILE','124163'
delete 'FB_PROFILE','124163','BASIC:PHONETIC_NAME'

#Hbase shell allows piping with other unix commands like below
echo "get 'FB_PROFILE','124163','BASIC'" | hbase shell > FB_Profile.txt

#HBase jargons 
Table
Row
RowKey
Column
ColumnFamily
ColumnQualifier

