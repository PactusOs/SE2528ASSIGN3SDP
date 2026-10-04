public class Square extends Shape{
    private final int side;
    public Square(String id,int side,Renderer implementation){
        super(id,implementation);
        this.side=side;
    }
    public int getSide(){
        return side;
    }
    public String execute(){
        return render("square","side",side);
    }
}
