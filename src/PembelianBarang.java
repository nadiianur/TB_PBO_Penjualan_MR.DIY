import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.DriverManager;

//class Pembelian Barang 
//Merupakan class Turunan dari class Barang
public class PembelianBarang extends Barang
{
    //koneksi ke database mysql xampp
    Connection conn;
    String link = "jdbc:mysql://localhost:3306/database_barang";
    
    Scanner input = new Scanner(System.in);
    String nama_pembeli;
    String no_pembeli;
    String nama_barang;
    String kode_barang;
    boolean barangReady=false, barangCukup=false, barangPas=false;
    Integer stok, stokKeluar, stokAwal, stokAkhir;
    Integer total;

    public void nama_pembeli()
    {
        System.out.print("    Nama Pembeli\t: ");
        this.nama_pembeli = input.nextLine();
    }

    public void no_pembeli()
    {
        System.out.print("    Nomor Pembeli\t: ");
        this.no_pembeli = input.nextLine();
    }

    /*public void nama_barang()
    {
        System.out.print("    Nama Barang\t\t: ");
        this.nama_barang = input.nextLine();
    }*/

    public void kode_barang()
    {
        System.out.print("    Kode Barang\t\t: ");
        this.kode_barang = input.nextLine();
    }

    public void pembelianBarang()
    {
        System.out.print("    Jumlah barang yang di beli\t: ");
        this.stokKeluar = input.nextInt();
    }

    public void stok(Integer stok)
    {
        this.stokAwal = stok;
    }

    public void cekStok()
    {
        //Percabangan (if)
        if (this.stokAwal > this.stokKeluar)
        {
            barangCukup = true;
        }
        else if (this.stokAwal == this.stokKeluar)
        {
            barangPas = true;
        }
        else 
        {
            System.out.println("    Jumlah stok barang tidak cukup!");
        }
    }

    public void Beli() throws SQLException 
    {
        //Aritmatika (pengurangan)
        //Untuk memperbarui stok di database_barang tabel barang
        this.stokAkhir = this.stokAwal - this.stokKeluar;
        //Aritmatika (perkalian)
        //Untuk penjumlahan total 
        this.total = this.stokKeluar * this.harga;
        //Mengakses Database
        conn = DriverManager.getConnection(link,"root","");
        String sql = "SELECT * FROM barang WHERE kode_barang='"+this.kode_barang+"'";
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql);

        //Percabangan (if)
        if (result.next())
        {
            sql = "UPDATE barang SET stok = "+this.stokAkhir+" WHERE kode_barang ='"+this.kode_barang+"'";
            if(statement.executeUpdate(sql) > 0)
            {
                    System.out.println("    Total Harga : " + this.total);
                 System.out.println("\n                ~ Transaksi Berhasil ! ~");
                 System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            }
        }

    }

    public void stokHabis() throws SQLException
    {
        //conn = DriverManager.getConnection(link,"root","");
        String sql = "DELETE FROM barang WHERE kode_barang = '"+this.kode_barang+"' ";
        Statement statement = conn.createStatement();
        //Percabangan (if)
        if(statement.executeUpdate(sql) > 0)
        {
            System.out.println("    Barang dengan Kode barang ["+this.kode_barang+"] habis di gudang");
        }
    }

    public void isiTabelPembelian() throws SQLException
    {
        //Mengakses Database
        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();
        String sql = "INSERT INTO beli_barang (nama_pembeli, no_pembeli, nama_barang, kode_barang, jumlah, harga, total) VALUES ('"+this.nama_pembeli+"', '"+this.no_pembeli+"', '"+this.nama_barang+"', '"+this.kode_barang+"', "+this.stokKeluar+" , "+this.harga+" , "+this.total+")";
        statement.execute(sql);
    }

    @Override
    public void search() throws SQLException 
    {
        //nama_barang();
        kode_barang();
        //Mengakses Database
        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();
        String sql = "SELECT * FROM barang WHERE kode_barang LIKE '"+this.kode_barang+"'";
        ResultSet result = statement.executeQuery(sql); 

        //Percabangan (if)
        if (result.next())
        {
            barangReady = true;
            this.nama_barang = result.getString("nama_barang");
            this.harga = result.getInt("harga");
            this.stok = result.getInt("stok");
            stok(result.getInt("stok"));
            
        }
    }

    //Method Delete
    @Override
    public void delete() throws SQLException 
    {
        try
        {
            view();
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("            Transaksi Pembelian Barang MR. DIY");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            nama_pembeli();
            no_pembeli();
            search();
            
            //Percabangan (if)
            if (barangReady)
            {
                pembelianBarang();
                cekStok();

                if (barangCukup)
                {
                    Beli();
                    isiTabelPembelian();
                }
                else if(barangPas)
                {
                    stokHabis();
                    isiTabelPembelian();
                }
            }
            else
            {
                System.out.println("    Barang tidak tersedia di gudang");
            }

        }
        //exception SQL
        catch(SQLException e)
        {
            System.err.println("    Maaf! Ada Kesalahan dalam penginputan data");
            System.out.println("    Silahkan coba lagi");
        }
        //excception jika inputan tidak sesuai dengan tipe data dalam program
        catch(InputMismatchException e)
        {
            System.out.println("    Inputkan data yang benar!");
        }
    }
}

