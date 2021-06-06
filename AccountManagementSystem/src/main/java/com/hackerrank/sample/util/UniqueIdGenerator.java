package com.hackerrank.sample.util;

public class UniqueIdGenerator {

	private static final String MIX_STRING = "abcdefghijklm_nopqrstuvwxyz1234567890ABCDEFGHI_JKLMNOPQRSTUVWXYZ";

	private static final int MIX_STRING_LENGTH = MIX_STRING.length();

	public static String fromBase10(final long base10) {
		if (base10 == 0)
			return "0";

		long temp = base10;
		final StringBuilder sb = new StringBuilder();

		while (temp > 0) {
			temp = fromBase10(temp, sb);
		}
		return sb.reverse().toString();
	}

	private static Long fromBase10(final long base10, final StringBuilder sb) {
		final int rem = (int) (base10 % MIX_STRING_LENGTH);
		sb.append(MIX_STRING.charAt(rem));
		return base10 / MIX_STRING_LENGTH;
	}
}