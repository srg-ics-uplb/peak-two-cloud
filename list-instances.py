#!/usr/bin/env python

from novaclient import client as novaclient
from credentials import get_nova_creds
creds = get_nova_creds()
nova = novaclient.Client("2", **creds)
print nova.servers.list()
