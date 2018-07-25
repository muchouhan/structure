package com.mukund.structure.model.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class ConfigNodeSpec {

	private List<FactoryNodeSpec> factories = new ArrayList<>();
	private List<QueueNodeSpec> queues = new ArrayList<>();

	public FactoryNodeSpec getFactoryByName(String aName) {
		Optional<FactoryNodeSpec> optional = getFactories().stream().filter(ele -> ele.getName().equals(aName))
				.findFirst();
		if (optional.isPresent())
			return optional.get();

		return null;
	}

	public QueueNodeSpec getQueueByName(String aName) {
		Optional<QueueNodeSpec> optional = getQueues().stream().filter(ele -> ele.getName().equals(aName)).findFirst();
		if (optional.isPresent())
			return optional.get();

		return null;

	}

}
