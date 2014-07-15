package inte.com.camel.activemq.camel.business;

public class ThreadTest {
    
    public static void main(String[] args){
        System.out.println("main method thread="+ Thread.currentThread().getName());
        for (int i=0;i<100;i++){
            User user = new User(String.valueOf(i));
            user.getUser();
            System.out.println("for loop Thead="+ Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
