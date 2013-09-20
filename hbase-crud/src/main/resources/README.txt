########## HBase-SHELL-README
##Description: Some of the commonly used shell commands in HBase
##########

#List all the possible HBase commands.. No rocket science ;)
help
help "<cmd>" //Ex. help "create"

#List tables in HBase
list
list 'fb_.*'

#Create a table 
create 'fb_profile','column_family1','column_family2','column_family3'
create 'fb_profile','basic','work','edu'

#Update a table
put 'fb_profile','row_key','basic:sample_column','shell'
put 'fb_profile','row_key','basic:sample_column','updated'
put 'fb_profile','row_key','basic:sample_column1','something'
put 'fb_profile','row_key','basic:sample_column2','somethingelse'

#Drop a table
#One must always disable the table before it has to be deleted
is_enabled 'fb_profile'
disable 'fb_profile'
drop 'fb_profile'

#Read from a table 
scan 'fb_profile'
get 'fb_profile','row_key','basic'
get 'fb_profile','row_key','basic:sample_column1'
get 'fb_profile','row_key',{COLUMN=>'basic:sample_column',VERSIONS=>3}

#Delete a row or specific column
deleteall 'fb_profile','row_key'
delete 'fb_profile','row_key','basic:sample_column'


