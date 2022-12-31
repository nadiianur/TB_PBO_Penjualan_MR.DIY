import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.DriverManager;

//class BarangIn
//Merupakan class turunan (Child Class) dari class barang
//interhitance
public class BarangIn extends Barang
{
    //koneksi ke database mysql xampp
    Connection conn;
    String link = "jdbc:mysql://localhost:3306/database_barang";
    Scanner input = new Scanner(System.in);

    boolean barangReady=false;
    boolean supReady=false;
    Integer stok, stokAwal, stokAkhir;
    Integer harga;
    String nama_sup;
    String kode_sup;
    String noHP;

    public void nama_barang()
    {
        System.out.print("    Nama Barang\t\t: ");
        this.nama_barang = input.nextLine();
    }

    public void kode_barang()
    {
        System.out.print("    Kode Barang\t\t: ");
        this.kode_barang = input.nextLine();
    }

    public void kategori()
    {
        System.out.print("    Kategori Barang\t: ");
        this.kategori = input.nextLine();
    }

    public void harga()
    {
        System.out.print("    Harga \t\t: Rp ");
        this.harga = input.nextInt();
    }

    public void stok()
    {
        System.out.print("    Stok Barang\t\t: ");
        this.stok = input.nextInt();
    }

    public void nama_sup()
    {
        System.out.print("    Nama Supplier\t: ");
        this.nama_sup = input.nextLine();
    }

    public void kode_sup()
    {
        System.out.print("\n    Kode Supplier\t: ");
        this.kode_sup = input.nextLine();
    }

    public void noHP()
    {
        System.out.print("    Nomor HP Supplier\t: ");
        this.noHP = input.nextLine();
    }

    public void cekKode_barang() throws SQLException
    {
        //Mengakses Database 
        //Mengecek data yang ada pada database_barang tabel barang
        //Statement yang digunakan :
        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM barang WHERE kode_barang LIKE '"+this.kode_barang+"'";
        ResultSet result = statement.executeQuery(sql); 

        //Jika inputan kode barang sudah terdapat pada database_barang, maka
        if (result.next())
        {
            System.out.println("Kode barang "+this.kode_barang+" sudah ada pada database barang");
            this.stokAwal = result.getInt("stok");
            this.nama_barang = result.getString("nama_barang");
            barangReady = true;
        }
    }

    public void barangReady() throws SQLException
    {
        //Mengakses data pada database_barang tabel barang
        //Aritmatika (Proses penjumlahan) 
        this.stokAkhir = this.stokAwal + this.stok;
        String sql = "SELECT * FROM barang WHERE kode_barang = '"+this.kode_barang+"'";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        if (result.next())
        {
            //update data pada database_barang tabel barang
            System.out.println("Kode barang '"+this.kode_barang+"' telah di update");
            System.out.println("Stok awal [ "+this.stokAwal+" ] menjadi "+this.stokAkhir+" pcs");
            sql = "UPDATE barang SET stok = "+this.stokAkhir+" WHERE kode_barang ='"+this.kode_barang+"'";  
            
            if(statement.executeUpdate(sql) > 0)
            {
                System.out.println("Data Stok Telah Berhasil Diperbarui");
            }    
        }
    }

    public void supReady() throws SQLException
    {
        //Create data tabel barang_in pada database_barang (Input Data)
        conn = DriverManager.getConnection(link,"root","");
        String sql = "INSERT INTO barang_in (nama_barang, kode_barang, kategori, harga, stok, nama_sup, kode_sup, noHP) VALUES('"+this.nama_barang+"', '"+this.kode_barang+"', '"+this.kategori+"', "+this.harga+", "+this.stok+", '"+this.nama_sup+"', '"+this.kode_sup+"', '"+this.noHP+"')";
        Statement statement = conn.createStatement();
        statement.execute(sql);
    }

    public void cekKode_sup() throws SQLException
    {
        //Mengakses data pada database_barang tabel barang_in
        //Statement yang digunakan :
        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM barang_in WHERE kode_sup LIKE '"+this.kode_sup+"'";
        ResultSet result = statement.executeQuery(sql); 

        if (result.next())
        {
            System.out.println("\n*Kode Supplier {'"+this.kode_sup+"'} sudah ada pada database*");
           
            //Mesave nilai data database_barang tabel barang_in ke dalam variable
            this.nama_sup = result.getString("nama_sup");
            this.noHP = result.getString("noHP");
            supReady = true;
        }
    }

    //public void createBarang()
    //untuk mengcreate barang baru 
    //memasukkan data pada database_barang tabel barang dan tabel barang_in
    public void createBarang() throws SQLException
    {
        Barang barang = new Barang(this.nama_barang, this.kode_barang, this.kategori, this.harga, this.stok);
        barang.methodKosong();

        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();

        //Create data (input data) ke dalam database_barang tabel barang
        String sql = "INSERT INTO barang (kode_barang, nama_barang, kategori, harga, stok) VALUES ('"+this.kode_barang+"', '"+this.nama_barang+"', '"+this.kategori+"', "+this.harga+", "+this.stok+")";
        statement.execute(sql);
        System.out.println("> Daftar barang baru toko MR.DIY telah disimpan <");
    }
    
    //public void createSupplier()
    //untuk mengcreate data supplier baru 
    //memasukkan data pada database_barang tabel barang_in
    public void createSupplier() throws SQLException
    {
        Sup sup = new Sup(this.nama_sup, this.kode_sup, this.noHP);
        sup.methodKosong();

        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();

        //Create data (input data) ke dalam database_barang tabel barang_in
        String sql = "INSERT INTO barang_in (nama_barang, kode_barang, kategori, harga, stok, nama_sup, kode_sup, noHP) VALUES('"+this.nama_barang+"', '"+this.kode_barang+"', '"+this.kategori+"', "+this.harga+", "+this.stok+", '"+this.nama_sup+"', '"+this.kode_sup+"', '"+this.noHP+"')";
        statement.execute(sql);
    }

    @Override
    public void save() throws SQLException 
    {
        //Exception Handling
        //Menggunakan try catch
        //Untuk mengatasi jika nantinya program terjadi eror
        try
        {
            kode_sup();
            nama_sup();
            noHP();
            cekKode_sup();
            nama_barang();
            kode_barang();
            kategori();
            harga();
            stok();
            cekKode_barang();
            
            //percabangan (if)
            if (barangReady)
            {
                //percabangan (if)
                if (supReady)
                {
                    supReady();
                    barangReady();
                }
                else
                {
                    createSupplier();
                    barangReady();
                }
            }
            else
            {
                //percabangan (if)
                if (supReady)
                {
                    supReady();
                    createBarang();
                }
                else
                {
                    createSupplier();
                    createBarang();
                }
            }
        }

        //exception SQL
        catch(SQLException e)
        {
            System.err.println("\nMaaf! Ada Kesalahan dalam penginputan data");
            System.out.println("Silahkan coba lagi");
        }

        //excception jika inputan tidak sesuai dengan tipe data dalam program
        catch(InputMismatchException e)
        {
            System.out.println("\nInputkan data yang benar!");
        }
    }
}
