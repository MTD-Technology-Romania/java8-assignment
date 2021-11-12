package training.java8.stream.transaction;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;


public class TransactionPlay {

    private final Trader raoul = new Trader("Raoul", "Cambridge");
    private final Trader mario = new Trader("Mario", "Milan");
    private final Trader alan = new Trader("Alan", "Cambridge");
    private final Trader brian = new Trader("Brian", "Cambridge");

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
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getYear() == 2011) {
                list.add(transactions.get(i));
            }
        }
        assertEquals(expected, list);
    }

    @Test //1
    public void all_2011_transactions_sorted_by_value() {
        List<Transaction> expected = Arrays.asList(transactions.get(0), transactions.get(2));

        List<Transaction> list = transactions.stream()
                .filter(e -> e.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());

        assertEquals(expected, list);
    }

    @Test //2
    public void old_school_unique_cities_of_the_traders() {
        List<String> expected = Arrays.asList("Cambridge", "Milan");

        List<String> list = null; // TODO


        assertEquals(expected, list);
    }

    @Test //2
    public void unique_cities_of_the_traders() {
        List<String> expected = Arrays.asList("Cambridge", "Milan");

        List<String> list = transactions.stream()
                .map(Transaction::getTrader) //stream Trader -> Traders
                .map(Trader::getCity) // stream String -> city
                .distinct()   //distinct elements
                .collect(Collectors.toList()); //collect all data and put in list

        assertEquals(expected, list);
    }

    @Test //3
    public void old_school_traders_from_Cambridge_sorted_by_name() {
        List<Trader> expected = Arrays.asList(alan, brian, raoul);

        List<Trader> list = null; // TODO

        assertEquals(expected, list);
    }

    @Test //3
    public void traders_from_Cambridge_sorted_by_name() {
        List<Trader> expected = Arrays.asList(alan, brian, raoul);


        List<Trader> list = transactions.stream()
                .map(Transaction::getTrader)
                .filter(e -> e.getCity().equals("Cambridge")) //Filter the names of cities with the value "Cambridge" compared to other values
                .sorted(Comparator.comparing(Trader::getName))  // sort elements by name
                .distinct()                                     // pass through only distinct elements
                .collect(Collectors.toList());

//an attempt that did not work
//                .filter(e -> e.getTrader().getCity() =="Cambridge")
//                .collect(Collectors.groupingBy(Transaction::getTrader))
//                .sorted(Comparator.comparing(Trader::getName))
//                .collect(Collectors.toList());


        assertEquals(expected, list);
    }

    @Test //4
    public void old_school_names_of_all_traders_sorted_joined() {
        String expected = "Alan,Brian,Mario,Raoul";

        String joined = null; //TODO

        assertEquals(expected, joined);
    }

    @Test //4
    public void names_of_all_traders_sorted_joined() {
        String expected = "Alan,Brian,Mario,Raoul";

        String joined = transactions.stream()
                .map(Transaction::getTrader)
                .map(Trader::getName)
                .sorted()   //if we do not use parameters in this function then element are sorted in natural order
                .distinct()
                .collect(Collectors.joining(","));  //adding all distinct sorted by natural elements and delimited them with ","

        assertEquals(expected, joined);
    }

    @Test //5
    public void old_school_are_traders_in_Milano() {
//        boolean areTradersInMilan = false; // TODO
//
//        assertTrue(areTradersInMilan);
    }

    @Test //5
    public void are_traders_in_Milano() {
        boolean areTradersInMilan = transactions.stream()
                .map(Transaction::getTrader)
                //.map(Trader::getCity)
                //.anyMatch(e -> "Milan".equals(e));
                .anyMatch(e -> "Milan".equals(e.getCity()));  // check if there is at least one match this line replace the 2 lines above

        assertTrue(areTradersInMilan);

    }

    @Test //6
    public void old_school_sum_of_values_of_transactions_from_Cambridge_traders() {
        int sum = -1; // TODO

        assertEquals(2650, sum);
    }

    @Test //6
    public void sum_of_values_of_transactions_from_Cambridge_traders() {
        int sum = transactions.stream()
                .filter(e -> e.getTrader().getCity().equals("Cambridge"))
                .mapToInt(Transaction::getValue) //convert our stream with a IntStream Object, so we can call sum() on it
                .sum(); // calculates the sum of the stream elements

        assertEquals(2650, sum);
    }

    @Test //7
    public void old_school_max_transaction_value() {
        int max = -1; // TODO

        assertEquals(1000, max);
    }

    @Test //7
    public void max_transaction_value() {
        int max = transactions.stream()
                //   .max(Transaction::getValue).get();
                .max(Comparator.comparing(Transaction::getValue)) // return the max value of the comparison. the type Optional, we do not know what is the type
                .get()    // calling get() take the returning value of max
                .getValue();


        assertEquals(1000, max);
    }


    @Test //8
    public void old_school_transaction_with_smallest_value() {
        Transaction expected = transactions.get(0);
        Transaction min = null; // TODO
        assertEquals(expected, min);
    }

    @Test //8
    public void transaction_with_smallest_value() {
        Transaction expected = transactions.get(0);
        Transaction min = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue))
                .get();  //return the transactions object
        assertEquals(expected, min);
    }

    @Test //9
    public void old_school_a_transaction_from_2012() {
        Transaction expected = transactions.get(1);
        Transaction tx2012 = null; // TODO

        assertEquals(expected, tx2012);
    }

    @Test //9
    public void a_transaction_from_2012() {
        Transaction expected = transactions.get(1);
        Transaction tx2012 = transactions.stream()
                .filter(e -> e.getYear() == 2012)
                .findFirst()
                .get();

        assertEquals(expected, tx2012);
    }

    // bonus
    // You aren't suppose to be able to solve this one: :)
    @Test
    public void old_school_uniqueCharactersOfManyWords() {
        List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
        List<String> wordsStream = Arrays.asList("abcd", "acdf");

        List<String> actual = null; // TODO
        assertEquals(expected, actual);
    }

    @Test
    public void uniqueCharactersOfManyWords() {
        List<String> expected = Arrays.asList("a", "b", "c", "d", "f");
        List<String> wordsStream = Arrays.asList("abcd", "acdf");

        List<String> actual = null; // TODO
        assertEquals(expected, actual);
    }


}
