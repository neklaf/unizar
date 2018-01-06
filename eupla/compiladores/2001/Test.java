public class  Test{

public static void main(String arg[]){

SubSpace sub=new SubSpace();

SubSpaceNet subnet=new SubSpaceNet("192.168.26.140",31337);
Tuple tuple;
while(true){
System.out.println("Recibiendo");
tuple=subnet.receiveTuple();
tuple.write();
if (tuple.getOperation()==Constant.INSERT)
	sub.addTuple(tuple);
if (tuple.getOperation()==Constant.DROP)
	sub.deleteTuple(tuple);
if (tuple.getOperation()==Constant.SEARCH)
	subnet.sendTuple(sub.getTuple(tuple));	
}


}

}
