package mock.com.camel.regex;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTest {
    String regex = "^20[1-9][0-9]-([0-1][0-9])-([0-3][0-9])\\s([0-2][0-9]):([0-6][0-9]):([0-6][0-9])(,\\d*)?\\sERROR.*";
    String logDateRegex = "^(\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2})(.*)";
    String logLevelRegex = "(.*)\\s(ERROR|INFO|DEBUG|WARN)\\s(.*)";
    
    @Test
    public void patternTest(){
        String log = "2014-09-23 11:14:25 ERROR [JMS-API-26] [com.ebay.sdk.ApiCall] [] [57207562] [com.ebay.sdk.SdkSoapException: Validation of the authentication token in API request failed.]";
        
        Pattern logPattern = Pattern.compile(regex);
        Matcher logMatcher = logPattern.matcher(log);
        assertTrue(logMatcher.matches());
        if (logMatcher.matches()){
            System.out.println("has error log: "+logMatcher.group(0));
        }
        
        Pattern logDatePattern = Pattern.compile(logDateRegex);
        Matcher logDateMatcher = logDatePattern.matcher(log);
        
        assertTrue(logDateMatcher.matches());
        if (logDateMatcher.matches()){
            
            System.out.println("has date matched: "+logDateMatcher.group(1));
        }
        
        Pattern logLevelPattern = Pattern.compile(logLevelRegex);
        Matcher logLevelMatcher = logLevelPattern.matcher(log);
        
        assertTrue(logLevelMatcher.matches());
        if (logLevelMatcher.matches()){
            
            System.out.println("has level matched: "+logLevelMatcher.group(2));
        }
    }
    
    
            
    @Test
    public void patternCompileWithSpecialCharTest(){
        String regex = "2014-10-15 12:13:42 ERROR (listenerContainer-9) [com.fpx.ce.syn.core.client.HttpHandlerClient] [] [329336542]";
        
        String log = "2014-10-15 12:13:42 ERROR (listenerContainer-9) [com.fpx.ce.syn.core.client.HttpHandlerClient] [] [329336542] [com.ebay.sdk.SdkSoapException: Validation of the authentication token in API request failed.]";
        regex = regex.replaceAll("(\\[|\\]|\\(|\\))", "\\\\$1");
        Pattern logPattern = Pattern.compile(regex);
        Matcher logMatcher = logPattern.matcher(log);
        assertTrue(logMatcher.matches());
    }
    
    @Test
    public void matcherGroupTest(){
        String temp = "2014-10-29 16:00:34,582 INFO [com.github.inspektr.audit.support.Slf4jLoggingAuditTrailManager] - Audit trail record BEGIN";
        String testRegex = ".";
        String regex = "(.*)\\s(ERROR|INFO|DEBUG|WARN)\\s(.*)";
        
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(temp);
        matcher.find();
        System.out.println(matcher.group(2));
        assertTrue(matcher.groupCount() > 0);
    }
    
    @Test
    public void String2Array(){
        String temp = "D:/a/b/c";
        String[] arrs = temp.split("\\"+File.separator);
        System.out.println(arrs);
        assertTrue(arrs.length == 4);
    }
}
