public class VectorRenderer implements Renderer{
    public String render(String shape,String dimension,int size){
        return "VECTOR "+shape+" "+dimension+"="+size;
    }
}
