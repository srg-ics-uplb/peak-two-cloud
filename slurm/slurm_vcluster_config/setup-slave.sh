#/bin/bash

# This script sets up the slaves
# Contact: jachermocilla@gmail.com

sudo apt-get update
sudo apt-get install slurm-llnl
cd /etc/slurm-llnl
sudo cp ~/slurm.conf .
cd /etc/munge
sudo cp ~/munge.key .
sudo chown munge: munge.key
sudo chmod 400 munge.key
sudo useradd user01
sudo passwd user01
sudo usermod -G mpiuser user01
sudo service munge restart
sudo service slurm-llnl restart
