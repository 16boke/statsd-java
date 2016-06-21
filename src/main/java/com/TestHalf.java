package com;

public class TestHalf {
	public static int getIndex(int target, int[] array) {
		if (array == null || array.length == 0)
			return -1;
		if (target < array[0] || target > array[array.length - 1])
			return -1;
		int left = 0;
		int right = array.length - 1;
		int mid = (left + right) / 2;
		while (left <= right) {
			if (target < array[mid])
				right = mid - 1;
			if (target > array[mid])
				left = mid + 1;
			if (target == array[mid])
				return mid;
			mid = (left + right) / 2;
		}
		return -1;
	}

	public static void main(String[] args) {
		int num[] = { 2, 3, 4, 6, 10, 20, 31, 35, 42, 53, 60, 90 };
		System.out.println(getIndex(7, num));
	}
}
