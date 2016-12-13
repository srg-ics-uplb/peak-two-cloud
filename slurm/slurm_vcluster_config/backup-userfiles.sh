#!/bin/bash

if [ "$#" -ne 1 ]; then
    echo "Usage: $0 <users file>"
    exit
fi



IFS=$'\r\n' GLOBIGNORE='*' command eval  'XYZ=($(cat $1))'
	
BACKUP_FILE=$(hostname)-userfiles-$(date +%Y%m%d).tar

rm $BACKUP_FILE.gz

for i in "${XYZ[@]}"
do
        #echo $i
        COLS=( $i );
	tar --append --file=$BACKUP_FILE ${COLS[0]}
#        tar czvf  ${COLS[0]}
done
gzip $BACKUP_FILE


