public class AsciiRenderer implements Renderer{
    public String render(String shape,String dimension,int size){
        return "ASCII | "+shape+" | "+dimension+"="+size+" |";
    }
}
