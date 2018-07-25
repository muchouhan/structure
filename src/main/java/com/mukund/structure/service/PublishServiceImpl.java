package com.mukund.structure.service;

import org.springframework.stereotype.Service;

import com.mukund.structure.model.Book;

@Service
public class PublishServiceImpl implements PublishService {

	@Override
	public boolean publish(Book book) {

		String fileName = book.getClass().getSimpleName() + "_" + book.getId() + ".xml";
		// if (LOGGER.isDebugEnabled()) {
		// LOGGER.debug("fileName : " + fileName + " \n write(book=" + book + ")
		// - start");
		// }

		// File aFile = new File(fileName);
		// Writer aWriter;
		// try {
		// aWriter = new FileWriter(aFile);
		// getXmlStream().toXML(book, aWriter);
		// aWriter.close();
		System.out.println(fileName+"\t"+getXmlStream().toXML(book));
		// } catch (IOException e) {
		// LOGGER.error("fileName : "+fileName+" \n write(book=" + book + ")",
		// e);
		// }
		// if (LOGGER.isDebugEnabled()) {
		// LOGGER.debug("fileName : " + fileName + " \n write(book=" + book + ")
		// - end");
		// }

		return false;
	}

}
