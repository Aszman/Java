import java.util.Locale;

public class Main {

    public static void main(String[] args){
        AdminUnitList polishUnits = new AdminUnitList();
        polishUnits.read("admin-units.csv");

        searchTimeComparison(polishUnits);
        System.out.println("\n\n\n");
        testGetNeighbors(polishUnits);
        System.out.println("\n\n\n");
        testQuery(polishUnits);
    }

    private static void searchTimeComparison(AdminUnitList polishUnits){
        double t1 = System.nanoTime()/1e6;
        for(int i = 0; i<100; ++i){
            if(!polishUnits.units.get(i).bbox.isEmpty()) {
                polishUnits.getNeighbors(polishUnits.units.get(i), 15);
            }
        }
        double t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"Time of optimized searching function for 100 units: t2-t1=%f\n",t2-t1);

        t1 = System.nanoTime()/1e6;
        for(int i = 0; i<100; ++i){
            if(!polishUnits.units.get(i).bbox.isEmpty()) {
                polishUnits.deprecatedGetNeighbors(polishUnits.units.get(i), 15);
            }
        }
        t2 = System.nanoTime()/1e6;
        System.out.printf(Locale.US,"Time of deprecated searching function for 100 units: t2-t1=%f\n\n\n",t2-t1);
    }

    private static void testGetNeighbors(AdminUnitList polishUnits){
        AdminUnit Krk = polishUnits.selectByName("krakowski",false).units.get(0);
        System.out.println(Krk+"\n\n");
        for (AdminUnit neighbor: polishUnits.getNeighbors(Krk,0).units){
            System.out.println(neighbor);
            System.out.println(neighbor.bbox.getWKT()+"\n");
        }
    }

    private static void testQuery(AdminUnitList polishUnits){
        AdminUnitQuery query = new AdminUnitQuery()
                .selectFrom(polishUnits)
                .where(a->a.population<240)
                .and(a->a.name.startsWith("J"))
                .limit(10)
                .offset(6);
        query.execute().list(System.out);
    }
}
