package mock.com.camel.drools.expert.sample.service.domain;

public class Order {
    private double price;
    private String source;
    private String dest;
    public Order(double price, String source, String dest){
        this.price = price;
        this.source = source;
        this.dest = dest;
    }
    public double getPrice() {
        return price;
    }
    public String getSource() {
        return source;
    }
    public String getDest() {
        return dest;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public void setDest(String dest) {
        this.dest = dest;
    }
}
