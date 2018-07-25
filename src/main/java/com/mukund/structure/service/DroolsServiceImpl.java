package com.mukund.structure.service;

import org.drools.KnowledgeBase;
import org.drools.runtime.StatefulKnowledgeSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mukund.structure.model.DroolsBookFacts;
import com.mukund.structure.model.RuleClassMapping;

@Service
public class DroolsServiceImpl implements DroolsService {

	@Autowired
	private KnowledgeBase kb;
	
	@Autowired
	@Qualifier( value="rules")
	private RuleClassMapping rules;

	@Override
	public DroolsBookFacts execute(DroolsBookFacts b) {
		StatefulKnowledgeSession session = kb.newStatefulKnowledgeSession();
		rules.getRules(b.getAction(), b.getHierarchy()).stream().forEach(i->{
			try {
				session.insert(Class.forName(i.toString()).newInstance());
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
		session.insert(b);
		session.fireAllRules();
		session.dispose();
		return b;
	}
}
