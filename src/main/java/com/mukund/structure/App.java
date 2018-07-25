package com.mukund.structure;

public class App {

	public static void main(String args[]) {
		System.setProperty("spring.profiles.active", "mypcdev");

//		AbstractApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
//		MetaDataService service = (MetaDataService) context.getBean(MetaDataService.class);
//		System.out.println(service.fetchBookMetaData());	
//
//		DroolsService droolService = (DroolsService) context.getBean(DroolsService.class);
//		final Message message = new Message();
//		message.setMessage("Hello World");
//		message.setStatus(Message.HELLO);
//		Message fact = (Message) droolService.execute(message);
//		System.out.println("Message after drool rule:" + fact.getMessage());

		// final DroolsBookFacts bookFacts = new DroolsBookFacts(true);
		// DroolsBookFacts newBookFacts = (DroolsBookFacts)
		// manager.fireRule(bookFacts);

		// System.out.println("Message after drool rule
		// newBookFacts:"+newBookFacts);

//		context.close();
	}

}
