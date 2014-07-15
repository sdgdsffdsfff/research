package inte.com.camel.activemq.camel.business;

public class User {
    private String name;
    public User (String name){
        this.name = name;
    }
    
    public void getUser(){
        System.out.println("user thread name="+Thread.currentThread().getName());
        System.out.println("i am "+this.name);
    }
}
