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
import java.util.HashSet;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.tags.LinkTag;

public class WebCrawler5  {
	private final Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());

	void getSimpleLinks(String url,int depth) {
        //if not already visited
        if (! visitedLinks.contains(url)) {
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
                            && ! visitedLinks.contains(extracted.getLink())) {

                        urls.add(extracted.getLink());
                    }

                }
                //we visited this url
                visitedLinks.add(url);

                if(depth < 20)
                {
	                for (String l : urls) {
	                	getSimpleLinks(l,depth+1);
	                }
                }
                System.out.print("*");
                
            } catch (Exception e) {
            	System.out.println(e.getMessage());
                //ignore all errors for now
            }
        }
    }
    public static void main678(String[] args) throws Exception {
    	 long start = System.currentTimeMillis();
         
    	 WebCrawler5 crawler =  new WebCrawler5();
         crawler.getSimpleLinks("https://www.skan.ai/", 0);
    	 
         System.out.println("done");
         long finish = System.currentTimeMillis();
         long timeElapsed = finish - start;
         System.out.println(timeElapsed);
    }
}
