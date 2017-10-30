#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <users file>"
    exit
fi



IFS=$'\r\n' GLOBIGNORE='*' command eval  'XYZ=($(cat $1))'

for i in "${XYZ[@]}"
do
        #echo $i
        COLS=( $i );
        ./adduser.sh ${COLS[0]} ${COLS[1]} ${COLS[2]}
done


