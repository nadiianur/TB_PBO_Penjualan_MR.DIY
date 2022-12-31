import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

//Class barang yang mengimplementasikan interface IDatabase
public class Barang implements IDatabase
{
    //koneksi ke database mysql xampp
    Connection conn;
    String link = "jdbc:mysql://localhost:3306/database_barang";
    Scanner input = new Scanner(System.in);

    String nama_barang;
    String kode_barang;
    String kategori;
    Integer harga;
    Integer stok;
    Boolean datakosong = true;

    ///constructor no parameter
    public Barang ()
    { 

    }

    //constructor 1 parameter
    public Barang(Integer x)
    {
        System.out.println("Constructor kelas barang MR.DIY");
    }

    //constructor banyak parameter
    public Barang (String nama_barang , String kode_barang, String kategori, Integer harga, Integer stok)
    {
        this.nama_barang = nama_barang;
        this.kode_barang = kode_barang;
        this.kategori = kategori;
        this.harga = harga;
        this.stok = stok;
        System.out.println("\n---------------------R I N C I A N----------------------");
        System.out.println("Nama barang \t: " + this.nama_barang); 
        System.out.println("Kode barang \t: " + this.kode_barang); 
        System.out.println("Kategori \t: " + this.kategori); 
        System.out.println("Harga\t\t: " + this.harga); 
        System.out.println("Stok Barang \t: " + this.stok); 
        System.out.println("--------------------------------------------------------");
    }

    public void methodKosong()
    {

    }

    //Mengimplementasikan method dari interface IDatabase
    //Method View :
    //Berfungsi untuk menampilkan data barang MR.DIY
    //Dengan mengakses data pada database_barang

    //Method Read
    @Override
    public void view() throws SQLException 
    {
        //Mengakses Database
        //Mengambil data pada database_barang tabel barang
        //Statement yang digunakan :
        String sql = "SELECT * FROM barang";
        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);

        System.out.println("\n.........................................................");
        System.out.println("\t  D A F T A R . B A R A N G . M R D I Y");
        System.out.println(".........................................................");
        //Perulangan (While)
        while (result.next())
        {
            datakosong = false;
            //Menampilkan data barang
            //Mengambil data dari database_barang tabel barang dan menampilkannya
            System.out.print("   Nama Barang \t: ");
            System.out.println(result.getString("nama_barang"));
            System.out.print("   Kode Barang \t: ");
            System.out.println(result.getString("kode_barang"));
            System.out.print("   Kategori \t: ");
            System.out.println(result.getString("kategori"));
            System.out.print("   Harga\t: ");
            System.out.println(result.getInt("harga"));
            System.out.print("   Stok\t\t: ");
            System.out.println(result.getInt("stok"));
            System.out.println(".........................................................");
        }
        
        if (datakosong)
        {
            System.out.println("Tidak ada Data Barang di Toko MR. DIY");
            System.out.println(".........................................................");
        }
        statement.close();
    }

    //Mengimplementasikan method dari interface IDatabase
    //Method Update
    //Berfungsi untuk mengupdate data barang MR.DIY
    @Override
    public void update() throws SQLException 
    {
        //Exception Handling
        //Menggunakan try catch
        //Untuk mengatasi jika nantinya program terjadi eror
        try
        {
            view();
            Integer pilih;
            String text = "\n   [ U B A H  D A T A  B A R A N G  M R . D I Y ]";
            System.out.println(text.toUpperCase());
            System.out.print("    Masukkan Kode Barang : ");
            String ubah = input.nextLine();

            //mengakses data database database_gudang tabel barang
            String sql = "SELECT * FROM barang WHERE kode_barang ='"+ ubah +"'";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            //Percabangan (if)
            if (result.next())
            {
                System.out.println("    -----------------------------------------");
                System.out.println("            Data yang ingin diubah");
                System.out.println("    -----------------------------------------");
                System.out.println("    1. Nama Barang");
                System.out.println("    2. Kategori");
                System.out.println("    3. Harga Barang ");
                System.out.println("    4. Stok");
                System.out.println("    -----------------------------------------");
                System.out.println("    Masukkan Pilihan (1/2/3/4) :  ");
                pilih = input.nextInt();
                input.nextLine();
        
                //Percabangan (switch case)
                switch (pilih)
                {
                    case 1:
                        System.out.print("    Nama Barang ["+result.getString("nama_barang")+"]\t: ");
                        String ubah1 = input.nextLine();
                        //Update data nama barang pada database_barang tabel barang
                        sql = "UPDATE barang SET nama_barang = '"+ubah1+"' WHERE kode_barang ='"+ubah+"'";
                        if(statement.executeUpdate(sql) > 0)
                        {
                            System.out.println("    Succsessfully! Data berhasil di perbarui");
                        }
                    break;
        
                    case 2:
                        System.out.print("    Kategori ["+result.getString("kategori")+"]\t: ");
                        String ubah3 = input.nextLine();
                        //Update data nama barang pada database_barang tabel barang
                        sql = "UPDATE barang SET kategori = '"+ubah3+"' WHERE kode_barang ='"+ubah+"'";
                        if(statement.executeUpdate(sql) > 0)
                        {
                            System.out.println("    Succsessfully! Data berhasil di perbarui");
                        }
                    break;

                    case 3:
                        System.out.print("    Harga ["+result.getInt("harga")+"]\t: ");
                        Integer ubah2 = input.nextInt();
                        //update data harga pada database_barang tabel barang
                        sql = "UPDATE barang SET harga = "+ubah2+" WHERE kode_barang='"+ubah+"'";
                        input.nextLine();
                        if(statement.executeUpdate(sql) > 0)
                        {
                            System.out.println("    Succsessfully! Data berhasil di perbarui");
                        }
                    break;

                    case 4:
                        System.out.print("    Stok ["+result.getInt("stok")+"]\t: ");
                        Integer ubah4 = input.nextInt();
                        //update data stok pada database_barang tabel barang
                        sql = "UPDATE barang SET stok = "+ubah4+" WHERE kode_barang ='"+ubah+"'";
                        input.nextLine();
                        if(statement.executeUpdate(sql) > 0)
                        {
                            System.out.println("    Succsessfully! Data berhasil di perbarui");
                        }
                    break;

                    default :
                        System.out.println("    Inputan harus berupa angka 1 / 2 / 3 / 4");
                    break;
                }
            }
            else
            {
                System.out.println("    Kode barang tidak dapat ditemukan");
            }
        }

        //exeption SQL 
        catch (SQLException e)
        {
            System.err.println("    *Terdapat kesalahan update data");
        }

        //exception, jika inputan tidak sesuai jenis data
        catch (InputMismatchException e)
        {
            System.err.println("    Inputan harus berupa angka!");
        }
    }

    @Override
    public void delete() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void save() throws SQLException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void search() throws SQLException {
        // TODO Auto-generated method stub
        
    }


}
