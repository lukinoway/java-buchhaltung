package konto.data.Util;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import konto.data.model.Konto;

import java.io.*;

public class ParseCSV {

    public ParseCSV(String filename, Konto kn) throws NoSuchAlgorithmException, FileNotFoundException {

	// read from file to array list
	ArrayList<String> myArr = new ArrayList<String>();
	File file = new File(filename);
	Scanner scanner = new Scanner(file);
	while (scanner.hasNextLine()) {
	    myArr.add(scanner.nextLine());
	}
	scanner.close();
	System.out.println("read this amount of lines: " + myArr.size());
	System.out.println("array size: " + myArr.size());

	String tmp;
	// kn = new Konto(myArr.size());

	// load data to class
	for (int i = 0; i < myArr.size(); i++) {
	    tmp = myArr.get(i);
	    String[] data = tmp.split("\\;");
	    if (i == 0) {
		kn.setKontoNr(data[0]);
	    }

	    // do some preparation on betrag string
	    data[4] = data[4].replace(".", "");
	    data[4] = data[4].replace(",", ".");
	    //kn.createTransaktion(data[1], data[2], data[3], data[4], data[5]);
	}
    }
}
