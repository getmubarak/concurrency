
/Library/Java/JavaVirtualMachines/1.6.0.jdk/Contents/Home/bin/

The java JDK ships with the jps command which lists all java process ids. You can run this command like this:

$ jps -l

if you are not able to find out process id, use below command:

$ ps -aef | grep java

To obtain thread dump using jstack, run the following command:

$ jstack -l 85367

https://jstack.review/

Thread Dump Analyzer (TDA), Samurai, etc to analyze thread dumps.


jconsole


There are static analysis tools like Sonar, ThreadCheck, etc for catching concurrency bugs at compile-time by analyzing the byte code. Sonar produces reports with recommendations.
