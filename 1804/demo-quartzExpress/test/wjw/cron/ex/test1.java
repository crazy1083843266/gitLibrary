package wjw.cron.ex;

import java.util.ArrayList;
import java.util.List;

public class test1 {
	public static void main(String[] args) {
		final int a=5;
		final List<Integer> list=new ArrayList<>();
		list.add(1);
		list.add(2);
		list.add(3);
		for (Integer integer : list) {
			System.out.println(integer);
		}
		Integer set = list.set(0, 5);
		for (Integer integer : list) {
			System.out.println(integer);
		}
		
	}
}
