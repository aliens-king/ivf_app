package org.cf.card.utility;

import org.springframework.stereotype.Service;

@Service("codeGenerator")
public class CodeGenerator {
	private String last ="";
	private int minDigits = 3;
	public CodeGenerator(){
		for(int i = 0; i<minDigits-1 ; i++)
			last = last+"A";
		last = last+"@";
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public CodeGenerator(String last){
		this.last= last;
	}
	public String getNext(){
		boolean flag = false;
		int length = last.length();//3
		int curr ;//
		try{
		 curr = last.charAt(length-1);
		}catch(Exception e){
			last= "A"+last;//A9
			length =  2;
			curr = last.charAt(length-1);
		}
		if(expandRequired()){
			StringBuilder builder = new StringBuilder();
			for (int i = 1; i < last.length(); i++) {
				builder.append("A");
			}
			last = builder.toString();
			last = last+'A';
			last = "A"+last;
			flag =true;
		}
		else if (curr == 90){
			curr = 48; 
		}else if(curr == 57){
			updateparent();
			curr = 65; 
		}else{
			curr ++;
		}
		if(flag ==true){
			return last;
		}
		last = last.substring(0, length-1)+((char)curr);
		return last;
	}

	private void updateparent(){
		
		CodeGenerator t = new CodeGenerator(last.substring(0, last.length()-1));
		last = t.getNext()+last.substring(last.length());
		t=null;
	}
	
	private boolean expandRequired(){
		boolean returnparam = true;
		for(int i = last.length(); i>0 ; i--){
			if('9'!=last.charAt(i-1)){
				returnparam = false;
				break;
			}
			
		}
		return returnparam ;
	}
	
	public static void main(String[] args) {
		CodeGenerator a = new CodeGenerator();
		for (int i = 0; i<=20000000;i++){
			System.out.println(a.getNext());
		}
	}
}