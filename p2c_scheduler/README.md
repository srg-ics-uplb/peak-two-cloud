
## Compile  

python -m py\_compile p2c\_scheduler.py 

## Install

The nova scheduler in Ubuntu 14.04 is located in 
`/usr/lib/python2.7/dist-packages/nova/scheduler`.
Create a symlink to the p2c scheduler files(.py and .pyc) inside this 
folder to where this repo was cloned.

Edit `/etc/nova/nova.conf` and comment the default nova scheduler 
related lines (`scheduler_*`). Add the following line: 

`scheduler_driver=nova.scheduler.p2c_scheduler.P2CScheduler`

Restart the nova-scheduler service
`sudo nova-scheduler restart`

Start an instance and check that the scheduler gets executed by viewing the logs

`sudo tail -f /var/log/nova/nova-scheduler.log`
