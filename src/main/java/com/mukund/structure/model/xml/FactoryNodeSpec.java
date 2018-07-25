package com.mukund.structure.model.xml;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@ToString
@RequiredArgsConstructor
public class FactoryNodeSpec {
	
	private static long DEFAULT_MSEC_TIMEOUT_VALUE = 10000;
	private String connectionFactoryName;
	private String connectionFactoryClass;
	private boolean external;
	private Long msecTimeoutValue = new Long(DEFAULT_MSEC_TIMEOUT_VALUE);
	private String name;
	private String providerUrl;

}
