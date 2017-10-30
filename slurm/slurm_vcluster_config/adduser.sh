#!/bin/bash

if [ "$#" -ne 3 ]; then
    echo "Usage: $0 <username> <password> <id>"
    exit
fi


PASSHASH=`perl -e 'printf("%s\n", crypt($ARGV[0], "password"))' "$2"`

MPIUSER_PASS=mpiuser_password

echo $MPIUSER_PASS | sudo -S useradd -m -d /mirror/$1 -p $PASSHASH -s /bin/bash $1
echo $MPIUSER_PASS | sudo -S groupmod -g $3 mpiuser $1
echo $MPIUSER_PASS | sudo -S usermod -u $3 -g $3 -G mpiuser $1
echo $MPIUSER_PASS | sudo -S chmod 711 /mirror/$1
