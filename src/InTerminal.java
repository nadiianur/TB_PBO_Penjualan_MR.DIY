import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

//class InTerminal
public class InTerminal 
{
    //koneksi ke database
    static Connection conn;
    static String link = "jdbc:mysql://localhost:3306/database_barang";
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) throws Exception 
    {
        //Method Date
        Date tanggal = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("E yyyy.MM.dd ', jam' hh:mm:ss a");
        //Method string
        String salamSapa = "||\t   Hai, Semoga Harimu Menyenangkan           ||";
        String sapa = salamSapa.replace("Hai", "Hallo");
        System.out.println("\n|||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        System.out.println(sapa.toUpperCase());
        System.out.println("||-----------------------------------------------------||");
        System.out.println("||                                                     || ");
        System.out.println("||Selamat datang di program Pembelian Barang MR DIY    ||");
        System.out.println("||Penjualan Tanggal : "+ft.format(tanggal)+" ||");
        System.out.println("||                                                     || ");
        System.out.println("|||||||||||||||||||||||||||||||||||||||||||||||||||||||||");

        menu();
    }

    //Method Menu
    private static void menu() throws SQLException 
    {
        boolean remenu = true;
        boolean ulangi = true;
        Integer pilih;
        String pertanyaan;

        //perulangan while
        admin();
        while (remenu)
        {
            System.out.println("\n\n=========================================================");
            System.out.println("|    D A F T A R . M E N U . P R O G R A M . M R D I Y  |");
            System.out.println("=========================================================");
            System.out.println("|    1.\tLihat Stok Barang MR DIY                        |");
            System.out.println("|    2.\tCreate Data Barang Baru MR DIY [Barang In]      |");
            System.out.println("|    3.\tUpdate Data Barang MR DIY [Ubah Data Barang]    | ");
            System.out.println("|    4.\tPembelian Barang MR DIY [Delete data]           |");
            System.out.println("|    5.\tKeluar Dari Program                             |");
            System.out.println("=========================================================");
            System.out.print("Masukkan Pilihan Menu : ");
            pilih = input.nextInt();
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            input.nextLine();
            Barang barang = new Barang(1);
            BarangIn barangin = new BarangIn();
            PembelianBarang pembelianbarang = new PembelianBarang();
            
            //Percabangan menggunakan switch case
            //Untuk Pilihan Menu
            switch (pilih) 
            {
                case 1:
                    barang.view();
                    ulangi = true;
                break;
                        
                case 2: 
                    barangin.save();
                    ulangi = true;
                break;

                case 3:
                    barang.update();
                    ulangi = true;
                break;

                case 4:
                    pembelianbarang.delete();
                    ulangi = true;
                break;

                case 5:
                    ulangi = false;
                    remenu = false;
                break;

                default :
                    System.out.println("Inputan Wajib Berupa Angka Saja");
                break;
            }
            
            //perulangan while
            while (ulangi)
            {
                System.out.print("\n? Balik ke Menu Utama [y/t] ?  ");
                pertanyaan = input.nextLine();
                System.out.println("<><><><><><><><><><><><><><><><><><><><><><><><><><><><><");
                //percabangan if else if
                if (pertanyaan.equalsIgnoreCase("t")) //method string 
                {
                    remenu = false;
                    ulangi = false;
                } else if (pertanyaan.equalsIgnoreCase("y")) //method string
                {
                    remenu = true;
                    ulangi = false;
                } else 
                {
                    System.out.println("Cukup dengan Inputan Character 'y' atau 't' ");
                }
            }
        }
        System.out.println("\n\n\t\t\t ~ P R O G R A M . S E L E S A I ~ \n");
    }

    private static void admin() throws SQLException
    {
        //Membuat objek HashMap
        //Collection Framework
        Map<String, String> Login = new HashMap<String, String>();

        //Mengakses data pada database_barang tabel admin
        String inputUsername, inputPassword;
        String sql = "SELECT * FROM admin";
        boolean relogin = true;
        //Mengakses Database
        conn = DriverManager.getConnection(link,"root","");
        Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);

        //perulangan (while)
        while (result.next())
        {
            //mengambil nilai di database dan menyimpannya ke dalam variable
            String username = result.getString("username");
            String password = result.getString("password");

            //input key dan value 
            Login.put(username, password);
        }
        System.out.println("\n\t    Silahkan Log In Sebagai admin ! ");
        System.out.println("---------------------------------------------------------");

        //perulangan (while)
        while (relogin)
        {
            System.out.print("Username : ");
            inputUsername = input.nextLine();
            System.out.print("Password : ");
            inputPassword = input.nextLine();
            //percabangan (if)
            //method bawaan HashMap
            if (Login.containsValue(inputUsername)==true)
            {
                //percabangan if
                //method bawaan HashMap dan method string
                if (Login.get(inputUsername).equals(inputPassword)) 
                {
                    System.out.println(" > ! LOGIN BERHASIL ! < ");
                    relogin = false;
                }
                else
                {
                    System.out.println("Username atau Password Salah! Silahkan Login Kembali");
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    relogin = true;
                }
            }
            else
            {
                System.out.println("Username atau Password Salah! Silahkan Login Kembali");
                System.out.println("____________________________________________________");
                relogin = true;
            }
        }
        statement.close();
    }
}
