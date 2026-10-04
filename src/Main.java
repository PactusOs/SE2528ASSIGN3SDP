public class Main{
    private int passed;
    private int total;
    private void check(String id,String classes,String actual,String expected){
        total++;
        boolean correct=expected.equals(actual);
        String status="FAIL";
        if(correct){
            passed++;
            status="PASS";
        }
        System.out.println(id+" "+status+" | "+classes+" | "+actual);
        if(!correct){
            System.out.println("  expected="+expected);
        }
    }
    private void test(String id,String classes,Shape shape,String expected){
        check(id,classes,"result="+shape.execute(),"result="+expected);
    }
    private void testSwitch(){
        Circle original=new Circle("C1",2,new VectorRenderer());
        Circle circle=original;
        String id=original.getId();
        int radius=original.getRadius();
        String before=circle.execute();
        circle.setImplementation(new RasterRenderer());
        String after=circle.execute();
        boolean sameObject=original==circle;
        boolean stateUnchanged=id.equals(circle.getId())&&radius==circle.getRadius();
        String actual="sameObject="+sameObject+" | stateUnchanged="+stateUnchanged
            +"\n  id="+id+"->"+circle.getId()+" | radius="+radius+"->"+circle.getRadius()
            +"\n  before="+before+" | after="+after;
        String expected="sameObject=true | stateUnchanged=true"
            +"\n  id=C1->C1 | radius=2->2"
            +"\n  before=VECTOR circle radius=2 | after=[RASTER] circle {radius=2}";
        check("T5","Circle + VectorRenderer -> RasterRenderer",actual,expected);
    }
    private void demo(){
        test("T1","Circle + VectorRenderer",new Circle("C1",2,new VectorRenderer()),"VECTOR circle radius=2");
        test("T2","Circle + RasterRenderer",new Circle("C1",2,new RasterRenderer()),"[RASTER] circle {radius=2}");
        test("T3","Square + VectorRenderer",new Square("S1",3,new VectorRenderer()),"VECTOR square side=3");
        test("T4","Square + RasterRenderer",new Square("S1",3,new RasterRenderer()),"[RASTER] square {side=3}");
        testSwitch();
        test("T6","Circle + AsciiRenderer",new Circle("C1",2,new AsciiRenderer()),"ASCII | circle | radius=2 |");
        test("T7","Square + AsciiRenderer",new Square("S1",3,new AsciiRenderer()),"ASCII | square | side=3 |");
        System.out.println("SUMMARY: "+passed+"/"+total+" PASS");
    }
    public static void main(String[] args){
        if(args.length==1&&args[0].equals("--demo")){
            new Main().demo();
        }else{
            System.out.println("Usage: java -cp out Main --demo");
        }
    }
}
