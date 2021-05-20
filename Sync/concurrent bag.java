

import com.google.common.collect;

public class Main
{
 public static void main(String[] args)
 {
	 ConcurrentHashMultiset<String> cms = ConcurrentHashMultiset.create();
	 cms.add("a", 2);
	 cms.add("b", 3);

 }
}
