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
import java.util.concurrent.RecursiveAction;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ForkJoinPool;
import java.util.HashSet;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.tags.LinkTag;


interface LinkHandler {
    int size();
    boolean visited(String link);
    void addVisited(String link);
}
class LinkFinderAction extends RecursiveAction {
    private String url;
    private LinkHandler cr;
    int depth;
    private static final long t0 = System.nanoTime();

    public LinkFinderAction(String url, LinkHandler cr,int depth) {
        this.url = url;
        this.cr = cr;
        this.depth =depth;
    }

    @Override
    public void compute() {
        if (!cr.visited(url)) {
            try {
                List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
                URL uriLink = new URL(url);
                Parser parser = new Parser(uriLink.openConnection());
                NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
                 
                for (int i = 0; i < list.size(); i++) {
                	if(i > 20)
                		break;
                	
                    LinkTag extracted = (LinkTag) list.elementAt(i);

                    if (!extracted.extractLink().isEmpty()
                            && !cr.visited(extracted.extractLink()) 
                            && depth < 3) {

                        actions.add(new LinkFinderAction(extracted.extractLink(), cr, depth + 1));
                    }
                }
                cr.addVisited(url);

                if (cr.size() == 1500) {
                    System.out.println("Time for visit 1500 distinct links= " + (System.nanoTime() - t0));
                }

                //invoke recursively
                invokeAll(actions);
                System.out.print(".");
                
            } catch (Exception e) {
            	System.out.println(e.getMessage());
                //ignore 404, unknown protocol or other server errors
            }
        }
    }
}
public class WebCrawler7 implements LinkHandler {

    private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
//    private final Collection<String> visitedLinks = Collections.synchronizedList(new ArrayList<String>());
    private String url;
    private ForkJoinPool mainPool;

    public WebCrawler7(String startingURL, int maxThreads) {
        this.url = startingURL;
        mainPool = new ForkJoinPool(maxThreads);
    }

    private void startCrawling() {
        mainPool.invoke(new LinkFinderAction(this.url, this,0));
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

    
    public static void main67(String[] args) throws Exception {
    	 long start = System.currentTimeMillis();
          new WebCrawler7("https://www.skan.ai/", 64).startCrawling();
        System.out.println("done");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
    }
}
