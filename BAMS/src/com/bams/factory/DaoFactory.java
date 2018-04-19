package com.bams.factory;

import java.util.ResourceBundle;

import com.bams.dao.AccountDao;

public class DaoFactory {
	public static AccountDao getDao(){
		ResourceBundle rb = ResourceBundle.getBundle("bams");
		String adString = rb.getString("ad");
		AccountDao ad = null;
		try {
			Class klass = Class.forName(adString);
			ad = (AccountDao)klass.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ad;
	}
}
