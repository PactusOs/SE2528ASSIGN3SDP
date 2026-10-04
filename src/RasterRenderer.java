public class RasterRenderer implements Renderer{
    public String render(String shape,String dimension,int size){
        return "[RASTER] "+shape+" {"+dimension+"="+size+"}";
    }
}
