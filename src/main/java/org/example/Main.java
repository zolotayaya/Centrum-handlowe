package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Department Electronics =new Department("Electronics");
        Department Clothes=new Department ("Clothes");
        Department Shoes= new Department ("Shoes");
        Department Cosmetics =new Department("Cosmetics");
        Department GardenTools=new Department("Garden Tools");

        Seller s=new Seller(01,"Anna",Electronics,1000,2,0,5);
        Brand Apple =new Brand("Apple",Electronics);
        Apple.setExpert(s);
        Seller s2=new Seller(02,"Adam",Electronics,1000,2,0,5);
        Apple.setExpert(s2);
        Brand Zara=new Brand("Zara",Clothes);
        Seller s7=new Seller(07,"Antek",Electronics,1000,2,0,5);

        Seller s1=new Seller(03,"Jane",Clothes,1500,3,1,5);
        Seller s3=new Seller(04,"Jan",Clothes,1500,3,1,5);
        Seller s4=new Seller(05,"Katarzyna",Clothes,1500,3,1,5);
        Seller s5=new Seller(06,"Piotr",Shoes,1200,3,3,5);
        Seller s6=new Seller (07,"Tomash",Shoes,1200,3,3,5);
        Manager m=new Manager(01,"Monika",Electronics,1500,1);
        Manager m1=new Manager(01,"Monika",Electronics,1500,1);
        Manager m2=new Manager(03,"Katarzyna",Clothes,1500,1);

        Zara.setExpert(s5);
        Zara.setExpert(s3);
        Product p=new Product("Iphone 11",6500,10,"telephon 256 Gb",Apple);
        Product p1=new Product("Iphone 11",4500,200,"telephon 128 Gb",Apple);
        Product p2=new Product("Iphone 16",11500,10,"telephon 256 Gb",Apple);
        Product c=new Product("Skirt",1250,30,"size:s, pink color",Zara);
        SaleSystem w=new SaleSystem();
        w.processPurchase(p,s,2,01);
        System.out.println("Sales count: "+ s.getsalesCount());
        w.processPurchase(p,s,2,02);
        System.out.println("Sales count: "+ s.getsalesCount());
        w.processPurchase(p1,s,3,03);
        w.processPurchase(p2,s,1,01);
        System.out.println("Sales count: "+ s.getsalesCount());
        w.processPurchase(p2,s,1,04);
        System.out.println();
        //s.checkPromotionCondition();
        w.processPurchase(p2,s,1,04);
        System.out.println("Sales count: "+ s.getsalesCount());
        System.out.print(Electronics.getManager());

    }
}