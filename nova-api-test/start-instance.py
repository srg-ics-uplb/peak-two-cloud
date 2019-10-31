#!/usr/bin/env python
import os
import time
from novaclient import client as novaclient
from credentials import get_nova_creds

creds = get_nova_creds()
nova = novaclient.Client("2",**creds)
image = nova.images.find(name="Ubuntu-16.04-server-amd64")
flavor = nova.flavors.find(name="p2c.1_512_20_1_1")
instance = nova.servers.create(name="frompython", image=image, flavor=flavor, key_name="guest-key")

# Poll until the status is no longer 'BUILD'
status = instance.status
while status == 'BUILD':
    time.sleep(5)
    # Retrieve the instance again so the status field updates
    instance = nova.servers.get(instance.id)
    status = instance.status
print "status: %s" % status

