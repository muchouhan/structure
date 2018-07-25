package com.mukund.structure.service;

public interface StructureService {
	default public void initialize() {
		System.out.println("Hi");
	}
}
