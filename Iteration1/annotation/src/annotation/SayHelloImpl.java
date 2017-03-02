package annotation;

/*
 * @author: xuan
 * @date: 2016/02/26
 * 
 * @mender: none
 * @date: none
 * 
 * @type: class
 * @description: it is a example
 */

public class SayHelloImpl implements SayHelloService{
	
	private final static int NUMBER = 10;//the times 
	private int chart;
	private String name;//the name of who is said to
	
	/*
	 * @author: xuan
	 * @date: 2016/02/26
	 * @description: it is a method to say hello
	 */
	@Override
	public void sayHello() {
		System.out.println("Hello!");
		chart = 1;//no meaning
		while(chart <= NUMBER){
			System.out.println("Hi!");
		}
		
	}

	/*
	 * @author: xuan
	 * @date: 2016/02/26
	 * @description: it is a method to say goodbye
	 */
	@Override
	public void sayGoodBye() {
		System.out.println("GoodBye!");
		
	}

}
