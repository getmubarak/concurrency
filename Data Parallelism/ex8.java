/*
 * Eclipse
Right click on your project in the Package Explorer Window (Shift-Alt-Q + P) and choose Properties.
In the Properties pane choose the Java Build Path view.
Select the Libraries tab.
Click the Add External Jars button.
Browse to <htmlp_dir>/lib (where where <htmlp_dir> is the directory where you unzipped the distribution: xxx/HTMLParserProject-2.0), select the htmlparser.jar and htmllexer.jar files and click on OK.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.HashSet;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.tags.LinkTag;

 interface LinkHandlerEx {
    void queueLink(String link,int depth) throws Exception;
    int size();
    boolean visited(String link);
    void addVisited(String link);
    void completed();
}
class LinkFinder implements Runnable {

    private String url;
    private LinkHandlerEx linkHandler;
    int depth=0;

    public LinkFinder(String url, LinkHandlerEx handler,int depth) {
        this.url = url;
        this.linkHandler = handler;
        this.depth = depth;
    }

    @Override
    public void run() {
        getSimpleLinks(url);
        linkHandler.completed();
        System.out.print(".");
    }

    private void getSimpleLinks(String url) {
        //if not already visited
        if (!linkHandler.visited(url)) {
            try {
                URL uriLink = new URL(url);
                Parser parser = new Parser(uriLink.openConnection());
                NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
                List<String> urls = new ArrayList<String>();

                for (int i = 0; i < list.size(); i++) {
                	if(i > 20)
                		break;
                    LinkTag extracted = (LinkTag) list.elementAt(i);

                    if (!extracted.getLink().isEmpty()
                            && !linkHandler.visited(extracted.getLink())) {

                        urls.add(extracted.getLink());
                    }

                }
                //we visited this url
                linkHandler.addVisited(url);

                if(depth < 5)
                {
	                for (String l : urls) {
	                    linkHandler.queueLink(l,depth+1);
	                }
                }
                System.out.print("*");
                
            } catch (Exception e) {
            	System.out.println(e.getMessage());
                //ignore all errors for now
            }
        }
    }
}
class WebCrawler6 implements LinkHandlerEx {
	volatile int count =0;
    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
//    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ExecutorService execService;
    long start;
    
    public WebCrawler6(String startingURL, int maxThreads) {
        this.url = startingURL;
        execService = Executors.newFixedThreadPool(maxThreads);
    }

    @Override
    public void queueLink(String link,int depth) throws Exception {
    	count++;
        startNewThread(link,depth);
    }

    @Override
    public int size() {
        return visitedLinks.size();
    }

    @Override
    public void addVisited(String s) {
        visitedLinks.add(s);
    }

    @Override
    public boolean visited(String s) {
        return visitedLinks.contains(s);
    }
    @Override
    public void completed() {
       --count;
    }
    private void startNewThread(String link,int depth) throws Exception {
        execService.execute(new LinkFinder(link, this,depth));
    }

    private void startCrawling() throws Exception {
        start = System.currentTimeMillis();
        
        startNewThread(this.url,0);
    }
    
    private void shutdown() throws Exception {
    	
    	Thread.sleep(10000);
    	while(count > 0) {
    		System.out.print(count);
    		Thread.sleep(1000);
    	}
    	execService.shutdown();  
        while (!execService.isTerminated()) {   }  
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
    	WebCrawler6 crawler =  new WebCrawler6("https://www.skan.ai/", 64);
    	crawler.startCrawling();
    	crawler.shutdown();
        
    }
}
