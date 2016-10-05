#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <username> <password>"
    exit
fi



IFS=$'\r\n' GLOBIGNORE='*' command eval  'XYZ=($(cat ./nodes.txt))'

for i in "${XYZ[@]}"
do
        #echo $i
        ssh -t $i "./adduser.sh $1 $2"
done

