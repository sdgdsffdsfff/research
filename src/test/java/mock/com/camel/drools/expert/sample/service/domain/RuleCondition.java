package mock.com.camel.drools.expert.sample.service.domain;

public class RuleCondition {
    private double minPrice;
    private String source;
    private String dest;
    public RuleCondition(double minPrice, String source, String dest){
        this.setMinPrice(minPrice);
        this.source = source;
        this.dest = dest;
    }
    public double getMinPrice() {
        return minPrice;
    }
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
    public String getSource() {
        return source;
    }
    public String getDest() {
        return dest;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public void setDest(String dest) {
        this.dest = dest;
    }
}
