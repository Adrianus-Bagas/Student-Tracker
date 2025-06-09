package com.tracker.student.util;

import java.util.Random;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GenerateRandomPassword {

	public String randomPassword(int length) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(characters.charAt(random.nextInt(characters.length())));
		}

		return sb.toString();
	}

}
