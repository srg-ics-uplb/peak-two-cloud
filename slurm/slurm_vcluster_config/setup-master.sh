#!/bin/bash

#This script sets up the master
#Contact: jachermocilla@gmail.com

sudo echo 'Acquire::http::Proxy "http://10.0.3.201:3142";' > /etc/apt/apt.conf.d/43proxy
sudo apt-get update
sudo apt-get install slurm-llnl -y
sudo apt-get install bsd-mailx -y #choose local only
sudo /usr/sbin/create-munge-key #key will be at /etc/munge/munge.key
cd /etc/slurm-llnl
cat /etc/hosts #take note  of the host names of the master and the slaves
sudo wget https://github.com/srg-ics-uplb/peak-two-cloud/raw/master/slurm/slurm_vcluster_config/slurm.conf
#Edit the ControlMachine and COMPUTE NODES entries
sudo chmod 775 /mirror
cd /usr/bin
sudo wget https://github.com/srg-ics-uplb/peak-two-cloud/raw/master/slurm/slurm_vcluster_config/p2c-mpi-slurm
sudo chmod 755 p2c-mpi-slurm
sudo mkdir /var/spool/slurm
sudo chown slurm: /var/spool/slurm
sudo service munge restart
sudo service slurm-llnl restart
sinfo -N -l #master is idle, slaves are unknown
sudo adduser user01
sudo usermod -G mpiuser user01
cd
cp /etc/slurm-llnl/slurm.conf .
sudo cp /etc/munge/munge.key .
chmod 755 munge.key
sudo chown mpiuser: munge.key
