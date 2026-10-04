public abstract class Shape{
    private final String id;
    private Renderer implementation;
    protected Shape(String id,Renderer implementation){
        this.id=id;
        this.implementation=implementation;
    }
    public String getId(){
        return id;
    }
    public void setImplementation(Renderer implementation){
        this.implementation=implementation;
    }
    protected String render(String shape,String dimension,int size){
        return implementation.render(shape,dimension,size);
    }
    public abstract String execute();
}
