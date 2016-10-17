#/bin/bash

# This script sets up the slaves
# Contact: jachermocilla@gmail.com

sudo ifconfig eth0 mtu 1454
#sudo echo 'Acquire::http::Proxy "http://10.0.3.201:3142";' > /etc/apt/apt.conf.d/43proxy
sudo apt-get update
sudo apt-get install slurm-llnl -y
cd /etc/slurm-llnl
sudo cp ~/slurm.conf .
cd /etc/munge
sudo cp ~/munge.key .
sudo chown munge: munge.key
sudo chmod 400 munge.key
sudo useradd -m -d /mirror/user01 -p pazgpwQZNFF9. -s /bin/bash user01
sudo usermod -G mpiuser user01
sudo service munge restart
sudo service slurm-llnl restart
su - mpiuser -c 'chmod 700 ~/.ssh'
su - mpiuser -c 'chmod 600 ~/.ssh/*'
su - mpiuser -c 'chmod 755 /mirror'
