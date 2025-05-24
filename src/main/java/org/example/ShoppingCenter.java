package org.example;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ShoppingCenter {
private int id;
private float rating;
private List<Department> departments= new ArrayList<>();
public int service_fee_on_people = 2;
public int service_fee_electric_for_day_open = 200;
public List<Department> getDepartment (){
    return departments;
}
    public static void main(String[] args) throws SQLException {
        int czas_trwania;
        int ilosc_dni;
        int ilosc_godzin;
        Scanner sc = new Scanner(System.in);
        System.out.println("Podaj czas trwania symulacjj w miesiacach(nie mniej jednego miesiaca)");
        czas_trwania = sc.nextInt();
        System.out.println("Podaj ilosc dni w tygodniu, kiedy centrum czynny");
        ilosc_dni = sc.nextInt();
        System.out.println("Podaj czas czynnosci centrum w ciagu dnia(w godzinach) ");
        ilosc_godzin = sc.nextInt();
        System.out.println("Podaj wachlarz doswiadczenie sprzedawcow");
        System.out.println("Min: ");
        int min = sc.nextInt();
        System.out.println("Max: ");
        int max = sc.nextInt();
        start_simulation(czas_trwania, ilosc_dni, ilosc_godzin,min,max);
    }

    public static void start_simulation(int czas_trwania, int ilosc_dni, int ilosc_godzin, int min, int max) throws SQLException {
    while (czas_trwania > 0) {
        int interwal = ilosc_dni;

        while (interwal > 0) {
            int interwal1 = ilosc_godzin;
            if(min > 3){
                interwal1*=3;
            }else if(min > 5){
                interwal1*=5;
            }else {
                interwal1*=7;
            }

            while (interwal1 > 0) {

                if(interwal1 == ilosc_godzin){
                    Department department = new Department();
                    department.setSellersExperience(min,max);
                }

                Brands[] allBrands = Brands.values();
                Random random = new Random();
                Brands randomBrand = allBrands[random.nextInt(allBrands.length)];
                int i = randomBrand.getNumber();
                Department department = new Department();
                List<Product> list_products = department.getProductsByBrande(i);
                    int i1 = random.nextInt(list_products.size());
                    List<Seller> sellerList = department.getSellers(list_products.get(i1));
                    int i2 = random.nextInt(sellerList.size());
                    System.out.println("Sprzedano: " + list_products.get(i1).getName() + ", sprzedawca " + sellerList.get(i2).showInformation() );
                    sellerList.get(i2).showinformation();
                interwal1--;
            }
            interwal--;
        }czas_trwania--;
    }
    }
}
