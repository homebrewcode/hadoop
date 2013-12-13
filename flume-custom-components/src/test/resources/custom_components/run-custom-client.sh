#!/bin/bash
c=1
while [ $c -le 5 ]
do
 echo ">>>>>>>>>>>>>>>>>>>>> Running the client jar"
 java -jar ./flume-custom-components-0.0.1-SNAPSHOT-jar-with-dependencies.jar 1> client.log 2> client-err.log
 sleep 10
done
