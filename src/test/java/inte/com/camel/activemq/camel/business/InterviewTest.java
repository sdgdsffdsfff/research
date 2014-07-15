package inte.com.camel.activemq.camel.business;

public class InterviewTest {
    private String msg;
    private int count;
    
    public void changeMsg(String msg){
        setMsg(msg);
    }
    
    public void changeCount(int ct){
        setCount(ct);
    }

    public void changeCount2(int ct){
    //传入参数变成方法的一个局部变量，是传入参数的一个拷贝，修改拷贝不影响原值
        ct = 20;
    }
    
    public void changeMsg2(String msg){
        msg = "hehe";
    }
    public void setMsg(String msg){
        this.msg = msg;
    }
    public void setCount(int ct){
        this.count = ct;
    }
    
    public void changeObj(InterviewTest obj){
        System.out.println("new obj count="+obj.count);
        obj = new InterviewTest();
        obj.setCount(5);
        System.out.println("new obj count="+obj.count);
    }
    
    public static void main(String[] args) {
        InterviewTest it = new InterviewTest();
        it.changeCount(21);
        System.out.println("count="+it.count);
        it.changeCount2(it.count);
        System.out.println("count="+it.count);
        
        it.changeMsg("ooo");
        System.out.println("msg="+it.msg);
        it.changeMsg(it.msg);
        System.out.println("msg="+it.msg);
        
        it.changeObj(it);
        System.out.println("count="+it.count);
    }

}
