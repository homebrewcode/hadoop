#File clean up activity!!
FILE_NAME="Somefile.txt"


#Generating content in the file
max=100
for i in `seq 1 $max`
do
    echo "$i" >> $FILE_NAME
done

