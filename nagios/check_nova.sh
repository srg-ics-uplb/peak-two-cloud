#!/bin/bash
DOWN_COUNT=`nova-manage service list | grep XXX | wc -l`

case $DOWN_COUNT in
	0)
		echo "OK"
		exit 0
		;;
	*)
		echo "NOT OK"
		exit 2
		;;
esac


