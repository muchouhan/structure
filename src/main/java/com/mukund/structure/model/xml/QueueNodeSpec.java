package com.mukund.structure.model.xml;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class QueueNodeSpec {
	private boolean isOutboundQueue = true;
	private String messageType;
	private String name;
	private String queueClass;
	private FactoryNodeSpec factorySpec;
}
