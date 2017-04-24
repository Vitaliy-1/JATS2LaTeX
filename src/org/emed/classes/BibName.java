package org.emed.classes;
/**
 * @file /src/org/emed/classes/BibName.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
public class BibName {
    private String surname;
    private char[] initials;

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public char[] getInitials() {
		return initials;
	}

	public void setInitials(char[] initials) {
		this.initials = initials;
	}
}
