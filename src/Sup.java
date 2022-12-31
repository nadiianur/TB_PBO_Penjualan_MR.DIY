//class Supplier
public class Sup
{
    String nama_sup;
    String noHP;
    String kode_sup;
    
    //constructor Supplier no parameter
    public Sup()
    {

    }

    //constuctor Supplier dengan 2 parameter
    public Sup(String nama_sup, String kode_sup,String noHP) 
    {
        this.nama_sup = nama_sup;
        this.kode_sup = kode_sup;
        this.noHP = noHP;
        System.out.println("\n    "+this.nama_sup+" Supplier baru di MR DIY");
    }
    

    public void methodKosong()
    {

    }
}
