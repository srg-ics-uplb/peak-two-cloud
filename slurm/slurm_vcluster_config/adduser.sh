#!/bin/bash

if [ "$#" -ne 2 ]; then
    echo "Usage: $0 <username> <password>"
    exit
fi



PASSHASH=`perl -e 'printf("%s\n", crypt($ARGV[0], "password"))' "$2"`
sudo useradd -m -p $PASSHASH -s /bin/bash $1
sudo usermod -G mpiuser $1
sudo chmod 711 /home/$1

