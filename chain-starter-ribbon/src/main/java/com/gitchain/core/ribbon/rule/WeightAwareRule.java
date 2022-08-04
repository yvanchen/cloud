package com.gitchain.core.ribbon.rule;

import com.alibaba.nacos.client.naming.utils.Chooser;
import com.alibaba.nacos.client.naming.utils.Pair;
import com.netflix.loadbalancer.Server;
import com.gitchain.core.ribbon.predicate.MetadataAwarePredicate;
import com.gitchain.core.ribbon.predicate.MetadataServer;
import org.springframework.cloud.client.ServiceInstance;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ribbon 路由规则器
 *
 * @author git dream.lu
 */
public class WeightAwareRule extends DiscoveryEnabledRule {
	public WeightAwareRule() {
		super(MetadataAwarePredicate.INSTANCE);
	}

	@Override
	public List<Server> filterServers(List<Server> serverList) {
		Chooser<String, Server> instanceChooser = new Chooser<>("chain.ribbon.rule");

		List<Pair<Server>> hostsWithWeight = serverList.stream()
			.map(serviceInstance -> new Pair<>(serviceInstance, getWeight(serviceInstance)))
			.collect(Collectors.toList());

		instanceChooser.refresh(hostsWithWeight);
		Server server = instanceChooser.randomWithWeight();

		return Collections.singletonList(server);
	}

	/**
	 * Get {@link ServiceInstance} weight metadata.
	 *
	 * @param serviceInstance instance
	 * @return The weight of the instance.
	 */
	protected Double getWeight(Server serviceInstance) {
		MetadataServer metadataServer = new MetadataServer(serviceInstance);
		return Double.parseDouble(metadataServer.getMetadata().get("weight"));
	}

}
