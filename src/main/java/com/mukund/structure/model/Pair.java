package com.mukund.structure.model;

public class Pair<T1, T2> {
	private final T1 val1;
	private final T2 val2;

	public static <IT1, IT2> Pair<IT1, IT2> factory(IT1 val1, IT2 val2) {
		return new Pair<IT1, IT2>(val1, val2);
	}

	private Pair(T1 val1, T2 val2) {
		this.val1 = val1;
		this.val2 = val2;
	}

	@Override
	public int hashCode() {
		return safeHash(val1) * 31 * safeHash(val2);
	}

	private static boolean safeEqual(Object a, Object b) {
		return a != null && b != null ? a.equals(b) : a == null && b == null;
	}
	
	private static int safeHash(Object val1) {
		return val1 != null ? val1.hashCode() : 0;
	}

	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Pair))
			return false;
		Pair<?,?> oth = (Pair<?,?>) other;
		return safeEqual(val1, oth.val1) && safeEqual(val2, oth.val2);
	}

	@Override
	public String toString() {
		return "Pair [val1=" + val1 + ", val2=" + val2 + "]";
	}

}
