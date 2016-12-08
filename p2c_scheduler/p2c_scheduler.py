# vim: tabstop=4 shiftwidth=4 softtabstop=4 expandtab
 
"""
P2C Scheduler
"""
 
import random
 
from oslo.config import cfg
 
from nova.compute import rpcapi as compute_rpcapi
from nova import exception
from nova.openstack.common import log as logging
from nova.openstack.common.gettextutils import _
from nova.scheduler import driver
 
CONF = cfg.CONF
CONF.import_opt('compute_topic', 'nova.compute.rpcapi')
LOG = logging.getLogger(__name__)
 
class P2CScheduler(driver.Scheduler):
    """
    Custom nova scheduler for Peak-Two Cloud
    """
 
    def __init__(self, *args, **kwargs):
        super(P2CScheduler, self).__init__(*args, **kwargs)
        self.compute_rpcapi = compute_rpcapi.ComputeAPI()
        LOG.info("Initializing P2C Scheduler...")
 
    def _filter_hosts(self, request_spec, hosts, filter_properties,
        hostname_prefix):
        """Filter a list of hosts based on hostname prefix."""
 
        LOG.info("jach:hosts %(hosts)s" % locals())

        hosts = [host for host in hosts if host.startswith(hostname_prefix)]
        return hosts
 
    
    def _schedule(self, context, topic, request_spec, filter_properties):
        """Picks a host that is up at random."""
        
        #context_type= type(context)
        #LOG.info("JACH Context Type:  %s " % (context_type))
        
        #context_type= type(topic)
        #LOG.info("JACH Topic Type:  %s " % (context_type))

        #context_type= type(request_spec)
        #LOG.info("JACH Request Spec Type:  %s " % (context_type))
        #LOG.info("jach:request_spec = %(request_spec)s" % locals())

        for k in request_spec:
            LOG.info("%s %s" % (k,request_spec[k]))

        LOG.info("jach:Memory requested(MB): %s",request_spec['instance_type']['memory_mb']);
        LOG.info("jach:VCPU requested: %s",request_spec['instance_type']['vcpus']);
        LOG.info("jach:Image size: %s",request_spec['image']['size']);
        LOG.info("jach:Image name: %s",request_spec['image']['name']);
 
        #context_type= type(filter_properties)
        #LOG.info("JACH Filter Prop Type:  %s " % (context_type))
        #LOG.info("jach:filter_properties = %(filter_properties)s" % locals())
        
        #LOG.info("jach:context = %(context)s" % {'context': context.__dict__})
        #LOG.debug("jach:request_spec = %(request_spec)s" % locals())
        #LOG.debug("jach:filter_properties = %(filter_properties)s" % locals())
        
        node_states = self.host_manager.get_all_host_states(context)
        #LOG.info("node states %s" % type(node_states))
        for host in node_states:
            LOG.info("jach: %s total_usable_ram=%d" % (host.nodename,host.total_usable_ram_mb))
            LOG.info("jach: %s free_ram_mb=%d" % (host.nodename,host.free_ram_mb))
            LOG.info("jach: %s vcpus_total=%d" % (host.nodename,host.vcpus_total))
            LOG.info("jach: %s vcpus_used=%d" % (host.nodename,host.vcpus_used))
            LOG.info("jach: %s free_disk_mb=%d" % (host.nodename,host.free_disk_mb))

        elevated = context.elevated()
        hosts = self.hosts_up(elevated, topic)
        if not hosts:
            msg = _("Is the appropriate service running?")
            raise exception.NoValidHost(reason=msg)

        #for now randomly select a host 
        host = random.choice(hosts)
        
        LOG.info("Request scheduled to %(host)s" % locals())
        return host
        
    def select_destinations(self, context, request_spec, filter_properties):
        """Selects random destinations."""
        num_instances = request_spec['num_instances']
        # NOTE(timello): Returns a list of dicts with 'host', 'nodename' and
        # 'limits' as keys for compatibility with filter_scheduler.
        dests = []
        for i in range(num_instances):
            host = self._schedule(context, CONF.compute_topic,
                request_spec, filter_properties)
            host_state = dict(host=host, nodename=None, limits=None)
                #LOG.debug("jach:host_state %(host_state)s" % locals())
            dests.append(host_state)
 
            if len(dests) < num_instances:
                raise exception.NoValidHost(reason='')
            return dests
 
    def schedule_run_instance(self, context, request_spec,
                          admin_password, injected_files,
                          requested_networks, is_first_time,
                          filter_properties, legacy_bdm_in_spec):
        """Create and run an instance or instances."""
        instance_uuids = request_spec.get('instance_uuids')
        for num, instance_uuid in enumerate(instance_uuids):
            request_spec['instance_properties']['launch_index'] = num
            try:
                #LOG.info("jach:context = %(context)s" % {'context': context.__dict__})
                #LOG.info("jach:request_spec = %(request_spec)s" % locals())
                #LOG.info("jach:filter_properties = %(filter_properties)s" % locals())
                
                host = self._schedule(context, CONF.compute_topic,
                    request_spec, filter_properties)
                updated_instance = driver.instance_update_db(context,
                                       instance_uuid)
                self.compute_rpcapi.run_instance(context,
                    instance=updated_instance, host=host,
                    requested_networks=requested_networks,
                    injected_files=injected_files,
                    admin_password=admin_password,
                    is_first_time=is_first_time,
                    request_spec=request_spec,
                    filter_properties=filter_properties,
                    legacy_bdm_in_spec=legacy_bdm_in_spec)
            except Exception as ex:
                # NOTE(vish): we don't reraise the exception here to make sure
                #             that all instances in the request get set to
                #             error properly
                driver.handle_schedule_error(context, ex, instance_uuid,
                                             request_spec)
