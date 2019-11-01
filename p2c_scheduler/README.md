
## Compile  

python -m py\_compile p2c\_scheduler.py 

## Install
 
On the frontend node, create a symlink to the p2c scheduler files(.py and .pyc) inside 
`/usr/lib/python2.7/dist-packages/nova/scheduler`.

Edit `/etc/nova/nova.conf` and comment out the default nova scheduler 
related lines (lines starting with `scheduler_*`). Add the following line: 

`scheduler_driver=nova.scheduler.p2c_scheduler.P2CScheduler`

Restart the nova-scheduler service
`sudo service nova-scheduler restart`

Start an instance and check that the scheduler was executed by viewing the logs

`sudo tail -f /var/log/nova/nova-scheduler.log`
