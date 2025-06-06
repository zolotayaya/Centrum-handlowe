package org.example;
import java.sql.SQLException;
import java.util.*;

public class Main extends Database {
    public static void main(String args[]) throws SQLException {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        int min;
        int max;
        int liczba_Sprzedarzy_Za_Godzine = 1;
        int wydadki_za_produkty_higieny_na_osobe = 10;
        int wydatki_za_produkty_higieny = 0;
        double wydatki_zaswiatwo_za_godzine = 0.5;
        double wydatki_zaswiatwo = 0;
        int liczba_osob_w_centrum = 0;
        Database db = Database.getInstance();
        System.out.println("Podaj wachlarz doswiadczenie sprzedawcow");
        System.out.println("Min: ");
        int a= Math.abs(sc.nextInt());
        if(Math.abs(a)==0){
            min = a+1;
        };
        System.out.println("Max: ");
        int b= Math.abs(sc.nextInt());
        if(b>0 && b<12){
            max = b;
        }else{
            max = 12;
        }
        db.setDepartmentsFromDB();
        db.setManagersFromDB();
        db.setSellersFromDB(min,max);
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
        System.out.println("Podaj liczbe godzin czynnosci centrum handlowe w ciagu dnia");
        int godziny = sc.nextInt();
        System.out.println();
        System.out.println("Podaj liczne dni czynnosci centrum w ciagu tygodnia");
        int liczba_dni = sc.nextInt();
        System.out.println("Podaj liczbe tygodni dzialania centrum");
        int liczba_tygodni = sc.nextInt();
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
                    Seller seller = sellers.get(rand.nextInt(sellers.size()));
                    System.out.println(seller.getName());
                    int quantity = 0;
                    if(seller.getExperience()<3) {
                        quantity += 1;
                    }
                    else if(seller.getExperience()<5) {
                        quantity += 2;
                    }
                    else {
                        quantity += 3;
                    }
                            PurchaseRecord purchaseRecord = system.processPurchase(product, seller, quantity, rand.nextInt(100));
//                            System.out.println("Sprzedano: " + purchaseRecord.getProductName() + " pracownikiem " + seller.getName());
//                            System.out.println("Ilosc sprzedanych jednostek: " + quantity);
//                            System.out.println("Zostalo : " + product.getQuantity());
//                            System.out.println("Doświadczenie sprzedawcy " + seller.getName() + ": " + seller.getExperience());
                            System.out.println("Product price: " + product.getPrice() + ", Seller income: " + seller.getIncome() + ", Manager income: " + seller.getDepartment().getManager().getIncome());
                            db.updateDataBase(product,seller,seller.getDepartment().getManager());
                            liczba_osob_w_centrum++;
                    System.out.println("Wydatek dla " + seller.getName() + ": " + seller.getCommision()*product.getPrice()*quantity/100);
                    godziny--;
                    wydatki_zaswiatwo+=wydatki_zaswiatwo_za_godzine;
                }
                wydatki_za_produkty_higieny+= wydadki_za_produkty_higieny_na_osobe*liczba_osob_w_centrum;
                liczba_dni--;
            }liczbaTydzien--;
            System.out.println("Minal tydzien: " + liczbaTydzien);
            try {
                Thread.sleep(500); // 500 ms = 0.5 sekundy
            } catch (InterruptedException e) {
                e.printStackTrace(); // lub obsłuż przerwanie w inny sposób
            }
            int countOfPurchases = system.getPurchaseHistory().getAll().size();
            for(int i=0; i<countOfPurchases; i++) {
                PurchaseRecord purchaseRecord = system.getPurchaseHistory().getAll().get(i);
                System.out.println("Name: " + purchaseRecord.getProductName() + ", quantity: " + purchaseRecord.getQuantity());
            }
            System.out.println();
            System.out.println();
            System.out.println();
        }
        db.cleanDB();
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
