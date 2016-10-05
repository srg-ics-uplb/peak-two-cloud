#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <username> <password>"
    exit
fi


PASSHASH=`perl -e 'printf("%s\n", crypt($ARGV[0], "password"))' "$2"`

MPIUSER_PASS=mpiuser_password

echo $MPIUSER_PASS | sudo -S useradd -m -p $PASSHASH -s /bin/bash $1
echo $MPIUSER_PASS | sudo -S usermod -G mpiuser $1
echo $MPIUSER_PASS | sudo -S chmod 711 /home/$1
