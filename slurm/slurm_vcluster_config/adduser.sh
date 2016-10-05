#!/bin/bash


PASSHASH=`perl -e 'printf("%s\n", crypt($ARGV[0], "password"))' "$2"`
sudo useradd -m -p $PASSHASH -s /bin/bash $1

