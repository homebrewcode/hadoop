########## HBase-SHELL-README
##Description: Some of the commonly used shell commands in HBase
##########

#List all the possible HBase commands.. No rocket science ;)
help
help "<cmd>" //Ex. help "create"

#List tables in HBase
list
list 'FB_.*'

#Create a table 
create 'FB_PROFILE','COLUMN_FAMILY1','COLUMN_FAMILY2','COLUMN_FAMILY3'
create 'FB_PROFILE','BASIC','WORK','EDU'

#Update a table
put 'FB_PROFILE','row_key','BASIC:SAMPLE_COLUMN','shell'
put 'FB_PROFILE','row_key','BASIC:SAMPLE_COLUMN','updated'
put 'FB_PROFILE','row_key','BASIC:SAMPLE_COLUMN1','something'
put 'FB_PROFILE','row_key','BASIC:SAMPLE_COLUMN2','somethingelse'

#Drop a table
#One must always disable the table before it has to be deleted
is_enabled 'FB_PROFILE'
disable 'FB_PROFILE'
drop 'FB_PROFILE'

#Read from a table 
scan 'FB_PROFILE'
get 'FB_PROFILE','row_key','BASIC'
get 'FB_PROFILE','row_key','BASIC:SAMPLE_COLUMN1'
get 'FB_PROFILE','row_key',{COLUMN=>'BASIC:SAMPLE_COLUMN',VERSIONS=>3}

#Delete a row or specific column
deleteall 'FB_PROFILE','row_key'
delete 'FB_PROFILE','row_key','BASIC:SAMPLE_COLUMN'


