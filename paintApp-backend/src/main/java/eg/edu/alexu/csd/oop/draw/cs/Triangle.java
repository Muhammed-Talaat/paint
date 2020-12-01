package eg.edu.alexu.csd.oop.draw.cs;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Triangle extends Shape{
	
//	public boolean checkIfShapeIntersectAPoint(int x,int y) {
//	
//		
//	}
//
	@Override
	public boolean checkIfShapeIntersectAPoint(double x, double y) {

		double x2=boundingBox_left;
		double y2=boundingBox_top+height;
		double x3=boundingBox_left+width;
		double y3=boundingBox_top+height;
		double x4=boundingBox_left+width/2;
		double y4=boundingBox_top;
		
		
		 double A = area (x2, y2, x3, y3, x4, y4); 
	       
	       /* Calculate area of triangle PBC */  
	        double A1 = area (x, y, x3, y3, x4, y4); 
	       
	       /* Calculate area of triangle PAC */  
	        double A2 = area (x2, y2, x, y, x4, y4); 
	       
	       /* Calculate area of triangle PAB */   
	        double A3 = area (x2, y2, x3, y3, x, y); 
	         
	       /* Check if sum of A1, A2 and A3 is same as A */
	        return (A == A1 + A2 + A3); 
		
//		double tmp1 =Math.sqrt((Math.pow(x4-x2, 2))+ (Math.pow(y4-y2, 2)) );
//		double tmp3 =Math.sqrt((Math.pow(x-x2, 2))+ (Math.pow(y-y2, 2)) );
//		double tmp4 =Math.sqrt((Math.pow(x-x3, 2))+ (Math.pow(y-y3, 2)) );
//				
//		if(x>=boundingBox_left && x<= boundingBox_left +width && y>=boundingBox_top && y<=boundingBox_top+height) {
//	        if(Math.asin(tmp1/width)>= Math.asin(tmp3/width) && Math.asin(tmp1/width)>= Math.asin(tmp4/width))   
//			  return true;		
//		 
//		}	
//		
		
	}

    private double area(double x1, double y1, double x2, double y2, double x3, double y3) {

    	return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ 
                x3*(y1-y2))/2.0); 
    	}
	
	
	
//  public int P1_x;
//  public  int P1_y;
//  public  int P2_x ;
//  public int P2_y;
//  public int P3_x;
//  public int P3_y;
// 
// 
// 
// public void draw() {
//	 this.type="triangle";
////	 
////	 if(f_x1>f_x2) {
////	
////		 
////	 }
////	 else if(f_x2>=f_x1) {
////			this.drawP1_x =(x2-x1)/2 +x1;
////	 }
//////	 if(f_y1>f_y2) {
//////			this.drawP1_y =y2;
//////				 
//////			 }
//////			 else if(y2>=y1) {
//////					this.drawP1_y =y1;
//////			 }
//// 
////		P1_x=f_x1;
////		P1_y=f_y1;
////		P2_x=f_x2;
////		P2_y=f_y1;
////	if(f_x2>=f_x1 && f_y2>=f_y1) {
////	
////		P3_x=f_x1+(f_x2 - f_x1)/2;
////		P3_y=f_y2;
////	
////			
////	}
////	else if(f_x2<f_x1 && f_y2>f_y1) {
////		P3_x=f_x2+(f_x1 - f_x2)/2;
////		P3_y=f_y2;
////	
////	}	
////	else if(f_x2>f_x1 && f_y2<f_y1) {
////		P3_x=f_x1+(f_x2 - f_x1)/2;
////		P3_y=f_y2;
////		
////	}	
////	
////	else if(f_x2<f_x1 && f_y2>f_y1) {
////		P3_x=f_x2+(f_x1 - f_x2)/2;
////		P3_y=f_y2;
////			
////	}	
//	 
//	 
// 
//	 
// }
 
 

 
    
	
	
}



@Component
class uploadImage {
	public	List<FakeShape> allShapes = new ArrayList<>();
	public	List<UserAction2> UserActions = new ArrayList<>() ;  
	public int actionIndex;	
//	@Component
//	public class UserAction2{
//    	public String action;
//    public	FakeShape old;
//    public	FakeShape updated;
//    
//    }
	
	public List<Shape> convert(){
		List<Shape> l = new ArrayList<>(); 

		for(int i=0; i<allShapes.size(); i++) {
		ShapeFactory f=new ShapeFactory();
			Shape s=f.getShape(allShapes.get(i).type);
		    s.type =allShapes.get(i).type;
			   s.boundingBox_left=allShapes.get(i).boundingBox_left;
             s.boundingBox_top=allShapes.get(i).boundingBox_top;
			   s.width=allShapes.get(i).width;
			   s.height=allShapes.get(i).height;
			   s.id=allShapes.get(i).id;
			   s.type=allShapes.get(i).type;
			   s.lineColor=allShapes.get(i).lineColor;
			   s.fillColor=allShapes.get(i).fillColor;
			   s.f_x1=allShapes.get(i).f_x1;
			   s.f_y1=allShapes.get(i).f_y1;
			   s.f_x2=allShapes.get(i).f_x2;
			   s.f_y2=allShapes.get(i).f_y2;
			   l.add(s);
		
	}
		
		
		return l;
		
		
	}
	public List<UserAction> convert2(){
		List<UserAction> l = new ArrayList<>(); 

		for(int i=0; i<UserActions.size(); i++) {
		ShapeFactory f=new ShapeFactory();
			Shape s=f.getShape(UserActions.get(i).old.type);
		    s.type =UserActions.get(i).old.type;
			   s.boundingBox_left=UserActions.get(i).old.boundingBox_left;
             s.boundingBox_top=UserActions.get(i).old.boundingBox_top;
			   s.width=UserActions.get(i).old.width;
			   s.height=UserActions.get(i).old.height;
			   s.id=UserActions.get(i).old.id;
			   s.type=UserActions.get(i).old.type;
			   s.lineColor=UserActions.get(i).old.lineColor;
			   s.fillColor=UserActions.get(i).old.fillColor;
			   s.f_x1=UserActions.get(i).old.f_x1;
			   s.f_y1=UserActions.get(i).old.f_y1;
			   s.f_x2=UserActions.get(i).old.f_x2;
			   s.f_y2=UserActions.get(i).old.f_y2;
               
			   Shape ss=null;
			   if(UserActions.get(i).updated!=null ) {
				 ss=f.getShape(UserActions.get(i).updated.type);
			    ss.type =UserActions.get(i).old.type;
				   ss.boundingBox_left=UserActions.get(i).updated.boundingBox_left;
	             ss.boundingBox_top=UserActions.get(i).updated.boundingBox_top;
				   ss.width=UserActions.get(i).updated.width;
				   ss.height=UserActions.get(i).updated.height;
				   ss.id=UserActions.get(i).updated.id;
				   ss.type=UserActions.get(i).updated.type;
				   ss.lineColor=UserActions.get(i).updated.lineColor;
				   ss.fillColor=UserActions.get(i).updated.fillColor;
				   ss.f_x1=UserActions.get(i).updated.f_x1;
				   ss.f_y1=UserActions.get(i).updated.f_y1;
				   ss.f_x2=UserActions.get(i).updated.f_x2;
				   ss.f_y2=UserActions.get(i).updated.f_y2;
			   }
				   UserAction a =new UserAction();
				   a.old=s;
				   a.updated=ss;
				   a.action=this.UserActions.get(i).action;
				   l.add(a);
				   
			   
	}
		
		
		return l;
		
		
	}
	


}



@Component
class UserAction {
   	public String action;
    public	Shape old;
    public	Shape updated;
    public UserAction(){}
    	UserAction(String action,Shape old,Shape updated){
    		this.action=action;
    		this.old=old;
    		this.updated=updated;
    	}
}




@Component
class UserAction2 {
	public String action;
public	FakeShape old;
public	FakeShape updated;

}






