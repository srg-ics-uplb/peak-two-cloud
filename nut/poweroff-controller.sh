#!/bin/bash
#stop controller services
#jachermocilla@gmail.com

LOG_BROWNOUT=/var/log/ics-brownout.log

sudo service nova-api stop
sudo service nova-cert stop
sudo service nova-consoleauth stop
sudo service nova-scheduler stop
sudo service nova-conductor stop
sudo service nova-novncproxy stop
sudo service nova-network stop
sudo service glance-api stop
sudo service glance-registry stop
sudo service keystone-all stop
sudo service mysql stop
sudo service rabbitmq-server stop
date >> $LOG_BROWNOUT
#sudo touch /forcefsck
sudo upsmon -c fsd


