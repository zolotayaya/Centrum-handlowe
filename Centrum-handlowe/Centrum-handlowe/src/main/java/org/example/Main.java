package org.example;
import java.nio.channels.SeekableByteChannel;
import java.sql.SQLException;
import java.util.*;

public class Main extends Database {
    public static void main(String args[]) throws SQLException {
        Random rand = new Random();
        int liczba_Sprzedarzy_Za_Godzine = 1;
        int wydadki_za_produkty_higieny_na_osobe = 10;
        int wydatki_za_produkty_higieny = 0;
        double wydatki_zaswiatwo_za_godzine = 0.5;
        double wydatki_zaswiatwo = 0;
        int liczba_osob_w_centrum = 0;
        Database db = Database.getInstance();
        db.setDepartmentsFromDB();
        db.setManagersFromDB();
        db.setSellersFromDB();
        db.setBrandFromDB();
        db.setProductsFromDB();
        List<Brand> brands = db.getBrands();
        List<Seller> selers = db.getSellers();
        Set<Seller> assignedSellers = new HashSet<>();
        for (Brand brand : brands) {
            for (Seller seler : selers) {
                if (!assignedSellers.contains(seler) && Objects.equals(seler.getDepartment(), brand.getDepartment())) {
                    brand.setExpert(seler);
                    assignedSellers.add(seler);
                    break;
                }
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj liczbe godzin czynnosci centrum handlowe w ciagu dnia");
        int godziny = sc.nextInt();
        System.out.println();
        System.out.println("Podaj liczne dni czynnosci centrum w ciagu tygodnia");
        int liczba_dni = sc.nextInt();
        System.out.println("Podaj liczbe tygodni dzialania centrum");
        int liczba_tygodni = sc.nextInt();
        System.out.println("Podaj wachlarz doswiadczenie sprzedawcow");
        System.out.println("Min: ");
        int min = sc.nextInt();
        System.out.println("Max: ");
        int max = sc.nextInt();
        db.setExperience(min,max);
        SaleSystem system = new SaleSystem();
        int godzinyNaDzien = godziny;
        int dniNaTydzien = liczba_dni;
        int liczbaTydzien = liczba_tygodni;
        while(liczbaTydzien >0) {
            liczba_dni = dniNaTydzien;
            while (liczba_dni >0) {
                godziny = godzinyNaDzien;
                while(godziny>0){
                    int randInt = rand.nextInt(db.getProducts().size());
                    Product product = db.getProducts().get(randInt);
                    Brand brand = product.getBrand();
                    List<Seller> sellers = brand.getExperts();
                    Seller seller = selers.get(rand.nextInt(sellers.size()));
                    if(seller.getExperience()>=5) {
                        for(int i = 0; i<3; i++) {
                            system.processPurchase(product, seller, 1, 1);
                            System.out.println("Sprzedano: " + product.getName());
                            System.out.println("Quantity: " + product.getQuantity());
                            db.updateQuantityInDB(product, product.getQuantity());
                            liczba_osob_w_centrum++;
                        }
                    }
                    else if(seller.getExperience()>=3 && seller.getExperience()<5) {
                        for(int i = 0; i<2; i++) {
                            system.processPurchase(product, seller, 1, 1);
                            System.out.println("Sprzedano: " + product.getName());
                            System.out.println("Quantity: " + product.getQuantity());
                            db.updateQuantityInDB(product, product.getQuantity());
                            liczba_osob_w_centrum++;
                        }
                    }
                    else if(seller.getExperience()<3) {
                        for(int i = 0; i<1; i++) {
                            system.processPurchase(product, seller, 1, 1);
                            System.out.println("Sprzedano: " + product.getName());
                            System.out.println("Quantity: " + product.getQuantity());
                            db.updateQuantityInDB(product, product.getQuantity());
                            liczba_osob_w_centrum++;
                        }
                    }
                    System.out.println("Wydatek dla " + seller.getName() + ": " + seller.getIncome());
                    wydatki_za_produkty_higieny+= wydadki_za_produkty_higieny_na_osobe*liczba_osob_w_centrum;
                    godziny--;
                    wydatki_zaswiatwo+=wydatki_zaswiatwo_za_godzine;
                }liczba_dni--;
            }liczbaTydzien--;
        }




//        for (int i = 0; i < db.getProducts().size(); i++) {
//            Random rand = new Random();
//            int randInt = rand.nextInt(db.getProducts().size());
//            Product product = db.getProducts().get(randInt);
//            Brand brand = product.getBrand();
//            Seller seller = brand.getExpert();
//            system.processPurchase(product, seller, 1, 1);
//        }
    }
}


















//       Random rand = new Random();
//       for(int i = 0; i<departments.size(); i++) {
//         ]p       sql1 = "UPDATE Seler SET rating = ?";
//           PreparedStatement ps1 = con.prepareStatement(sql1);
//           ps1.setInt(1,rand.nextInt(1,6));
//           ps1.executeUpdate();
//       }
//        Department Electronics =new Department("Electronics");
//        Department Clothes=new Department ("Clothes");
//        Department Shoes= new Department ("Shoes");
//        Department Cosmetics =new Department("Cosmetics");
//        Department GardenTools=new Department("Garden Tools");
//
//        Seller s=new Seller(01,"Anna",Electronics,1000,2,0,5);
//        Brand Apple =new Brand("Apple",Electronics);
//        Apple.setExpert(s);
//        Seller s2=new Seller(02,"Adam",Electronics,1000,2,0,5);
//        Apple.setExpert(s2);
//        Brand Zara=new Brand("Zara",Clothes);
//        Seller s7=new Seller(07,"Antek",Electronics,1000,2,0,5);
//
//        Seller s1=new Seller(03,"Jane",Clothes,1500,3,1,5);
//        Seller s3=new Seller(04,"Jan",Clothes,1500,3,1,5);
//        Seller s4=new Seller(05,"Katarzyna",Clothes,1500,3,1,5);
//        Seller s5=new Seller(06,"Piotr",Shoes,1200,3,3,5);
//        Seller s6=new Seller (07,"Tomash",Shoes,1200,3,3,5);
//        Manager m=new Manager(01,"Monika",Electronics,1500,1);
//        Manager m1=new Manager(01,"Monika",Electronics,1500,1);
//        Manager m2=new Manager(03,"Katarzyna",Clothes,1500,1);
//
//        Zara.setExpert(s5);
//        Zara.setExpert(s3);
//        Product p=new Product( "Iphone 11",6500,10,"telephon 256 Gb",Apple);
//        Product p1=new Product("Iphone 11",4500,200,"telephon 128 Gb",Apple);
//        Product p2=new Product("Iphone 16",11500,10,"telephon 256 Gb",Apple);
//        Product c=new Product("Skirt",1250,30,"size:s, pink color",Zara);
//        SaleSystem w=new SaleSystem();
//        w.processPurchase(p,s,2,01);
//        System.out.println("Sales count: "+ s.getsalesCount());
//        w.processPurchase(p,s,2,02);
//        System.out.println("Sales count: "+ s.getsalesCount());
//        w.processPurchase(p1,s,3,03);
//        w.processPurchase(p2,s,1,01);
//        System.out.println("Sales count: "+ s.getsalesCount());
//        w.processPurchase(p2,s,1,04);
//        System.out.println();
//        //s.checkPromotionCondition();
//        w.processPurchase(p2,s,1,04);
//        System.out.println("Sales count: "+ s.getsalesCount());
//        System.out.print(Electronics.getManager());
