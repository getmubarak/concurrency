import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

enum Gender {

	MALE, FEMALE, OTHER
}

class Person {
    public Person(String name, int age, String state) {
		this.name = name;
		this.age = age;
		this.state = state;
		this.gender = Gender.FEMALE;
	}
    
	public Person(String name, int age, Gender gender) {
		this.name = name;
		this.age = age;
		this.state = "NY";
		this.gender = gender;
	}

	private final String name;
    private final int age;
    private final String state;
    private final Gender gender;
    
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public String getState() {
		return state;
	}
	@Override
	public String toString() {
		return "\nPerson [name=" + name + ", age=" + age + ", state=" + state + "]";
	}
	public Gender getGender() {
		return gender;
	}
}

public class Main {
	static int COLLECTION_SIZE = 10000000	;
	
	private static Collection <Person> getPersonCollection (){
		List <Person> personList = new ArrayList <Person> ();

		String [] names = {"David", "Marry", "Satya", "Matt", "Patrick", "Bill", "Mike", "Jake", "Amber", "Dianne"};
		int [] 	age = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
		String [] states = {"NY", "MA", "MO", "CA", "TX", "MN", "WA", "PE", "NE", "NH", "OH"};		
		
		for (int i=0; i< COLLECTION_SIZE; i++){
			personList.add(new Person (names [getRandom()], age[getRandom()], states [getRandom()]));
		}
		
		System.out.println ("Generated the collection \n");
		return personList;
	}
	
private static void sequentialStreamPerformance (Collection <Person> persons){
    long t1 = System.currentTimeMillis(), count;
    
    count = persons.stream().
    		filter(x-> (x.getState().equals("NY") || x.getState().equals("TX")))
    			.filter(x-> x.getAge() > 50)
    				.filter(x-> x.getName().startsWith("M"))
    					.count();
    
    long t2 = System.currentTimeMillis();
    System.out.println("Count = " + count + " Normal Stream Takes " + (t2-t1) + " ms\n");
}
	
private static void parallelStreamPerformance (Collection <Person> persons){
    long t1 = System.currentTimeMillis(), count;
    
    count = persons.parallelStream().
    		filter(x-> (x.getState().equals("NY") || x.getState().equals("TX")))
    			.filter(x-> x.getAge() > 50)
    				.filter(x-> x.getName().startsWith("M"))
    					.count();
    
    long t2 = System.currentTimeMillis();
    System.out.println("Count = " + count + " Parallel Stream takes " + (t2-t1) + " ms\n");
}
	
	

	private static int getRandom (){
		return new Random().ints(0, 10).findFirst().getAsInt();
	}
	
	public static void main(String[] args) {
		Collection <Person> persons = getPersonCollection ();
		sequentialStreamPerformance (persons);
		//parallelStreamPerformance (persons);
	}
	
}
