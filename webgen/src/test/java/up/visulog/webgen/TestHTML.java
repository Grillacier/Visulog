package up.visulog.webgen;

import org.junit.Test;

public class TestHTML{
    @Test
    public void printPage(){
	var s = HTML.page();
	System.out.println(s);
    }
}
