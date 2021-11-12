package training.java8.stream.transaction;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TransactionPlay {

	private final Trader raoul = new Trader("Raoul", "Cambridge");
	private final Trader mario = new Trader("Mario","Milan");
	private final Trader alan = new Trader("Alan","Cambridge");
	private final Trader brian = new Trader("Brian","Cambridge");

	private final List<Transaction> transactions = Arrays.asList(
	    new Transaction(brian, 2011, 300),
	    new Transaction(raoul, 2012, 1000),
	    new Transaction(raoul, 2011, 400),
	    new Transaction(mario, 2012, 710),
	    new Transaction(mario, 2012, 700),
	    new Transaction(alan, 2012, 950)
	);

	@Test //1
	public void old_school_all_2011_transactions_sorted_by_value() {
		List<Transaction> expected = Arrays.asList(transactions.get(0), transactions.get(2));

		List<Transaction> list = new ArrayList<>(); // TODO
		for(Transaction t : transactions){
			if(t.getYear() == 2011){
				list.add(t);
				System.out.println(t);
			}
		}
		/**
		 * var 1  list.sort(Comparator.comparing(Transaction::getValue));
		 */

		Collections.sort(list, new Comparator<Transaction>() {
			@Override
			public int compare(Transaction o1, Transaction o2) {
				return o1.getValue()<o2.getValue()?o1.getValue():o2.getValue();
			}
		});
		assertEquals(expected, list);
	}

	@Test //1
	public void all_2011_transactions_sorted_by_value() {
		List<Transaction> expected = Arrays.asList(transactions.get(0), transactions.get(2));
		
		List<Transaction> list = // TODO
		transactions.stream()
						.filter(e -> e.getYear() == 2011)
								.sorted(Comparator.comparing(Transaction::getValue))
										.collect(Collectors.toList());
		assertEquals(expected, list); 									
	}
		
	@Test //2
	public void old_school_unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");
		
		List<String> list = new ArrayList<>(); // TODO
		Set<String> set  = new TreeSet<>();
		for(Transaction t : transactions){
			set.add(t.getTrader().getCity());
		}

		list.addAll(set);
		assertEquals(expected, list);
	}

	@Test //2
	public void unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");

		List<String> list = transactions.stream()
						.map(t -> t.getTrader().getCity())
						.distinct()
						.collect(Collectors.toList());


		assertEquals(expected, list);
	}
	
	@Test //3
	public void old_school_traders_from_Cambridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);
		List<Trader> list = new ArrayList<>(); // TODO

		for(Transaction t : transactions){
			if(t.getTrader().getCity() == "Cambridge")
			{
				if(!list.contains(t.getTrader()))
					list.add(t.getTrader());
			}
		}

		list.sort(Comparator.comparing(Trader::getName));
		/* Varianta 2
		Collections.sort(list, new Comparator<Trader>() {
			public int compare(Trader o1, Trader o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});*/
		assertEquals(expected, list);
	}

	@Test //3
	public void traders_from_Cambridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);
		List<Trader> list = transactions.stream()
				.filter(e -> e.getTrader().getCity() == "Cambridge")
				.map(e -> e.getTrader())// TODO
				.sorted(Comparator.comparing(Trader::getName))
				.distinct().collect(Collectors.toList());

		assertEquals(expected, list);
	}
	
	@Test //4
	public void old_school_names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";
		
		String joined = ""; // TODO
		List<String> list = new ArrayList<>();
		for(Transaction t : transactions){
			if(!list.contains(t.getTrader().getName()))
			{
				list.add(t.getTrader().getName());
			}
		}
		Collections.sort(list);
		for(String s : list){
			joined += s + ",";
		}
		joined = joined.substring(0,joined.length()-1);
		assertEquals(expected, joined);
	}

	@Test //4
	public void names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";

		String joined = transactions.stream()
				.map(e -> e.getTrader().getName())
				.distinct()
				.sorted()
				.collect(Collectors.joining(","));// TODO

		assertEquals(expected, joined);
	}
			
	@Test //5
	public void old_school_are_traders_in_Milano() {
		boolean areTradersInMilan = false; // TODO

		for(Transaction t : transactions){
			if(t.getTrader().getCity() == "Milan")
				areTradersInMilan = true;
		}

		assertTrue(areTradersInMilan);
	}

	@Test //5
	public void are_traders_in_Milano() {
		boolean areTradersInMilan = false; // TODO

		long k = transactions.stream()
						.filter(e -> e.getTrader().getCity() == "Milan" )
						.map(e-> e.getTrader())
						.distinct()
						.count();
		System.out.println(k);
		if(k > 0)
			areTradersInMilan = true;

		assertTrue(areTradersInMilan);
	}
	
	@Test //6 
	public void old_school_sum_of_values_of_transactions_from_Cambridge_traders() {
		int sum = 0; // TODO

		for(Transaction t : transactions){
			if(t.getTrader().getCity() == "Cambridge"){
				sum += t.getValue();
			}
		}
		assertEquals(2650, sum);
	}

	@Test //6
	public void sum_of_values_of_transactions_from_Cambridge_traders() {
		int sum = -1; // TODO

		 sum = transactions.stream()
						.filter(e -> e.getTrader().getCity() == "Cambridge")
				 		.map(t -> t.getValue())
				 		.reduce(0,(s,value) -> s + value);
		assertEquals(2650, sum);
	}
	
	@Test //7
	public void old_school_max_transaction_value() {
		int max = -1; // TODO

		for(Transaction t: transactions){
			if(t.getValue() > max)
				max = t.getValue();
		}
		assertEquals(1000, max);
	}

	@Test //7
	public void max_transaction_value() {
		int max = -1; // TODO

		max = transactions.stream()
				.map(e->e.getValue())
				.max(Comparator.naturalOrder())
				.get();

		assertEquals(1000, max);
	}


	@Test //8
	public void old_school_transaction_with_smallest_value() {
		Transaction expected = transactions.get(0);
		Transaction min = null; // TODO
		int minim = 100_000_000;
		for(Transaction t : transactions){
			if(t.getValue() < minim) {
				min = t;
				minim = t.getValue();
			}
		}
		assertEquals(expected, min);
	}

	@Test //8
	public void transaction_with_smallest_value() {
		Transaction expected = transactions.get(0);
		Transaction min = transactions.stream()
				.min(Comparator.comparing(Transaction::getValue))
				.get();// TODO
		assertEquals(expected, min);
	}

	@Test //9
	public void old_school_a_transaction_from_2012() {
		Transaction expected = transactions.get(1);
		Transaction tx2012 = null; // TODO

		for(Transaction t : transactions){
			if(t.getYear() == 2012)
			{
				tx2012 = t;
				break;
			}
		}
		assertEquals(expected, tx2012);
	}

	@Test //9
	public void a_transaction_from_2012() {
		Transaction expected = transactions.get(1);
		Transaction tx2012 = transactions.stream()
				.filter(e -> e.getYear() == 2012)
				.findFirst()
				.get();// TODO

		assertEquals(expected, tx2012);
	}

	// bonus
	// You aren't suppose to be able to solve this one: :)
	@Test
	public void old_school_uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");
		TreeSet<String> words = new TreeSet<>();
		for(String s: wordsStream){
			for(Character c: s.toCharArray())
			{
				words.add(c.toString());
			}
		}
		List<String> actual = new ArrayList<>(); // TODO
		actual.addAll(words);
		assertEquals(expected, actual);
	}

	@Test
	public void uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");

		List<String> actual = wordsStream.stream()
				.map(e -> e.toCharArray())
				.distinct()
				.sorted(new Comparator<char[]>() {
					@Override
					public int compare(char[] o1, char[] o2) {
						return o1.toString().compareTo(o2.toString());
					}
				})
						.map(e -> e.toString())
								.collect(Collectors.toList());
		assertEquals(expected, actual);
	}
	
	
}
