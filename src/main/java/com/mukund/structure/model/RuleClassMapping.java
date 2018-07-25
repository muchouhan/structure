package com.mukund.structure.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RuleClassMapping {

	private Set<ClassMapping> mapping = new HashSet();

	public Set<ClassMapping> getMapping() {
		return mapping;
	}

	public void addMapping(String request, String structure, String className) {
		this.mapping.add(new ClassMapping(request, structure, className));
	}

	public List<Object> getRules(String action, String type) {
		return mapping.stream().filter(i->i.getRequest().equalsIgnoreCase(action))
							.filter(i->i.getStructure().equalsIgnoreCase(type))
							.map(result -> result.getClassName()).distinct().collect(Collectors.toList());
		
	}

	private class ClassMapping {
		private final String request;
		private final String structure;
		private final String className;

		public ClassMapping(String request, String structure, String className) {
			super();
			this.request = request;
			this.structure = structure;
			this.className = className;
		}

		public String getRequest() {
			return request;
		}

		public String getStructure() {
			return structure;
		}

		public String getClassName() {
			return className;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((className == null) ? 0 : className.hashCode());
			result = prime * result + ((request == null) ? 0 : request.hashCode());
			result = prime * result + ((structure == null) ? 0 : structure.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ClassMapping other = (ClassMapping) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (className == null) {
				if (other.className != null)
					return false;
			} else if (!className.equals(other.className))
				return false;
			if (request == null) {
				if (other.request != null)
					return false;
			} else if (!request.equals(other.request))
				return false;
			if (structure == null) {
				if (other.structure != null)
					return false;
			} else if (!structure.equals(other.structure))
				return false;
			return true;
		}

		private RuleClassMapping getOuterType() {
			return RuleClassMapping.this;
		}
		

	}
}
