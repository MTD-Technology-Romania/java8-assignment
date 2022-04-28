package training.java8.stream.transaction;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		List<Transaction> list = new ArrayList<Transaction>();
		for (Transaction tranzactie : transactions) {
			int an_tranzactie = tranzactie.getYear();
			if (an_tranzactie == 2011) {
				list.add(tranzactie);
			}
		}
		list.sort(Comparator.comparing(tranzactie-> tranzactie.getValue()));
		System.out.println("Test_1_old "+list); //vizualizare rezultate
		assertEquals(expected, list);
	}

	@Test //1
	public void all_2011_transactions_sorted_by_value() {
		List<Transaction> expected = Arrays.asList(transactions.get(0), transactions.get(2));

		List<Transaction> list =
				transactions.stream()
						    .filter(transaction -> transaction.getYear()==2011 )
						    .sorted(Comparator.comparing(transaction -> transaction.getValue()))
						    .collect(Collectors.toList());
		System.out.println("Test_1 "+list); //vizualizare rezultate
		assertEquals(expected, list);
	}
		
	@Test //2
	public void old_school_unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");
		
		List<String> list = new ArrayList<>();
		for (Transaction tranzactie:transactions) {
			if (!list.contains(tranzactie.getTrader().getCity()))
			list.add(tranzactie.getTrader().getCity());
		}
		System.out.println("Test_2_old "+list);
		assertEquals(expected, list); 									
	}

	@Test //2
	public void unique_cities_of_the_traders() {
		List<String> expected = Arrays.asList("Cambridge", "Milan");

		List<String> list =
				transactions.stream()
						    .map(tranzactie->tranzactie.getTrader().getCity())
						    .distinct()
						    .collect(Collectors.toList());
		System.out.println("Test_2 "+list);
		assertEquals(expected, list);
	}
	
	@Test //3
	public void old_school_traders_from_Cambridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);

		List<Trader> list = new ArrayList<>();
		for (Transaction tranzactie:transactions){
			Trader t=tranzactie.getTrader();
			if (t.getCity()=="Cambridge")
				if (!list.contains(t))
					list.add(t);
		}
		list.sort(Comparator.comparing(Trader::getName));
		System.out.println("Test_3_old "+list); //vizualizare rezultate
		assertEquals(expected, list);
	}

	@Test //3
	public void traders_from_Cambridge_sorted_by_name() {
		List<Trader> expected = Arrays.asList(alan, brian, raoul);

		List<Trader> list =
				transactions.stream()
						    .map(transaction -> transaction.getTrader())
						    .filter(trader -> trader.getCity()=="Cambridge")
						    .distinct()
						    .sorted(Comparator.comparing(trader -> trader.getName()))
						    .collect(Collectors.toList());

		System.out.println("Test_3 "+list); //vizualizare rezultate
		assertEquals(expected, list);
	}
	
	@Test //4
	public void old_school_names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";
		ArrayList<String> traderArrayList = new ArrayList<>();
		for (Transaction tranzactie : transactions) {
			String trader = tranzactie.getTrader().getName();
			if (!traderArrayList.contains(trader))
				traderArrayList.add(trader);
		}
		traderArrayList.sort(Comparator.naturalOrder());
		String joined = traderArrayList.toString();
		joined = joined.substring(1, traderArrayList.toString().length() - 1).replaceAll(" ", "");
		System.out.println("Test_4_old "+joined); //vizualizare rezultate
		assertEquals(expected, joined);
	}

	@Test //4
	public void names_of_all_traders_sorted_joined() {
		String expected = "Alan,Brian,Mario,Raoul";

		List<String> joinedList =
				transactions.stream()
						.map(transaction -> transaction.getTrader().getName())
						.distinct()
						.sorted(Comparator.naturalOrder())
						.toList();

		/*String joined =
		    transactions.stream()
						.map(transaction -> transaction.getTrader().getName())
						.distinct()
						.sorted(Comparator.naturalOrder())
						.collect(Collectors.joining(,));

		System.out.println(joined); //vizualizare rezultate
*/

		String joined = joinedList.toString().substring(1,joinedList.toString().length()-1).replaceAll(" ","");
		System.out.println("Test_4 "+joined); //vizualizare rezultate

		assertEquals(expected, joined);
	}
			
	@Test //5
	public void old_school_are_traders_in_Milano() {

		boolean areTradersInMilan=false;

		for (Transaction tranzactie:transactions){
			String city = tranzactie.getTrader().getCity();
			if (city=="Milan")
				areTradersInMilan=true;
		}
		System.out.println("Test_5_old "+areTradersInMilan); //vizualizare rezultate

		assertTrue(areTradersInMilan);
	}

	@Test //5
	public void are_traders_in_Milano() {
		boolean areTradersInMilan=false;

		List<String> areTradersInMilanList =
				transactions.stream()
						    .map(transaction -> transaction.getTrader().getCity())
						    .filter(city->city.equals("Milan"))
						    .collect(Collectors.toList());

		if (!areTradersInMilanList.isEmpty())
			areTradersInMilan=true;

		System.out.println("Test_5 "+areTradersInMilanList); //vizualizare rezultate
		System.out.println("Test_5 "+areTradersInMilan);     //vizualizare rezultat
		assertTrue(areTradersInMilan);
	}
	
	@Test //6 
	public void old_school_sum_of_values_of_transactions_from_Cambridge_traders() {
		int sum = 0;
		for (Transaction tranzactie:transactions){
			if (tranzactie.getTrader().getCity()=="Cambridge"){
				sum+=tranzactie.getValue();
			}
		}
		System.out.println("Test_6_old "+sum); //vizualizare rezultat
		assertEquals(2650, sum);
	}

	@Test //6
	public void sum_of_values_of_transactions_from_Cambridge_traders() {

		int sum=
				transactions.stream()
						    .filter(transaction -> transaction.getTrader().getCity() == "Cambridge")
						    .map(transaction -> transaction.getValue())
						    .reduce(0,(e1, e2) -> e1 + e2);

		System.out.println("Test_6 "+sum); // vizualizare rezultat
		assertEquals(2650, sum);
	}
	
	@Test //7
	public void old_school_max_transaction_value() {
		int max = -1;

		for (Transaction tranzactie : transactions){
			if (tranzactie.getValue()>max)
				max=tranzactie.getValue();
		}
		System.out.println("Test_7_old "+max); //vizualizare rezultat
		assertEquals(1000, max);
	}

	@Test //7
	public void max_transaction_value() {
		int max =
				transactions.stream()
						    .map(transaction -> transaction.getValue())
						    .max((x, y) -> Integer.compare(x, y))
						    .get();

		System.out.println("Test_7 "+max); //vizualizare rezultat
		assertEquals(1000, max);
	}


	@Test //8
	public void old_school_transaction_with_smallest_value() {
		Transaction expected = transactions.get(0);
		Transaction min = null;

		int a = 2147483647;
		for (Transaction tranzactie : transactions) {
			if (tranzactie.getValue() < a) {
				min = tranzactie;
				a = tranzactie.getValue();
			}
		}
		System.out.println("Test_8_old "+a); //vizualizare rezultat
		assertEquals(expected, min);
	}

	@Test //8 //need help ggl
	public void transaction_with_smallest_value() {
		Transaction expected = transactions.get(0);
		Transaction min = null;

		Transaction m=
				transactions.stream()
						    .min(Comparator.comparing(transaction -> transaction.getValue()))
						    .get();
		min=m;
		System.out.println("Test_8 "+min);
		assertEquals(expected, min);
	}

	@Test //9
	public void old_school_a_transaction_from_2012() {
		Transaction expected = transactions.get(1);
		Transaction tx2012 = null;

		for (Transaction tranzactie:transactions) {
			if (tranzactie.getYear() == 2012) {
				tx2012 = tranzactie;
				//System.out.println(tranzactie.getTrader().toString());
				break; //first transaction
			}
		}
		System.out.println("Test_9_old "+tx2012); //vizualizare rezultat
		assertEquals(expected, tx2012);
	}

	@Test //9
	public void a_transaction_from_2012() {
		Transaction expected = transactions.get(1);
		Transaction tx2012 = null;

		Transaction t=
				transactions.stream()
						    .filter(transaction -> transaction.getYear()==2012)
						    .findFirst()
						    .get();
		tx2012=t;
		System.out.println("Test_9 "+t);
		assertEquals(expected, tx2012);
	}

	// bonus
	// You aren't suppose to be able to solve this one: :)
	@Test
	public void old_school_uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");
		List<String> actual=new ArrayList<>();

		ArrayList<Character> eachChar=new ArrayList<>();
		for (String eachWord:wordsStream) {
			for (int i = 0; i < eachWord.length(); i++) {
				for (int j = 0; j <= eachChar.size(); j++) {
					if (!eachChar.contains(eachWord.charAt(i))) {
						eachChar.add(eachWord.charAt(i));
					}
				}
			}
		}
		for (char e:eachChar) {
			actual.add(String.valueOf(e));
		}

		/*Set <String> sets=new HashSet<>(eachChar);
		actual.addAll(sets);

//.. ?
		actual= actual.stream()
				      .distinct()
				      .collect(Collectors.toList());*/

		System.out.println("Test_bonus_old "+actual); //vizualizarea rezultatului
		assertEquals(expected, actual);
	}




	@Test
	public void uniqueCharactersOfManyWords() {
		List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
		List<String> wordsStream = Arrays.asList("abcd", "acdf");

		System.out.println(wordsStream); //viz initial

		List<String> actual =
				wordsStream.stream()
						   .flatMapToInt(s -> s.chars())
						   .mapToObj(e->Character.valueOf((char) e))
						   .distinct()
						   .map(character -> character.toString())
						   .collect(Collectors.toList());
		System.out.println("Test_bonus "+actual); //viz rez
		assertEquals(expected, actual);
	}
	
	
}
