package org.togethwy.cheetah.base;

/**
 * @author wangtonghe
 * @date 2017/10/11 08:42
 */
public class InnerTest {

    private String name;

    private String email;

    public InnerTest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public class Inner{
        private String innerName;

        public void display(){
            System.out.println("outer name :"+name+",email:"+email);
        }
    }

    public static void main(String[] args) {
        InnerTest innerTest = new InnerTest("outer","test@outer.com");
        InnerTest.Inner inner = innerTest.new Inner();
        inner.display();
    }


}
