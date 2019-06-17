package com.mkyong.listeners;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import com.mkyong.model.Report;

@Component
public class TransactionItemReaderListener implements ItemReadListener<Report> {

	@Override
	public void beforeRead() {
		//System.out.println("TransactionItemReaderListener - beforeRead");
		
	}

	@Override
	public void afterRead(Report item) {
		//System.out.println("TransactionItemReaderListener - afterRead");
		
	}

	@Override
	public void onReadError(Exception ex) {
		ex.printStackTrace();
		System.out.println("TransactionItemReaderListener - onReadError");	
	}

}
