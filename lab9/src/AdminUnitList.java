import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Predicate;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();

    //potrzebne do szybszego wyszukiwania sąsiadów
    AdminUnit abstractRoot = new AdminUnit("Abstract Admin Unit", -1);

    public void read(String filename){

        CSVReader reader = null;
        try {
            reader = new CSVReader(filename,",",true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //przypisuje ID jednostki do jednostki
        Map<Long, AdminUnit> mapIDToUnit = new HashMap<>();
        // przypisuje do jednostki jego ID
        Map<AdminUnit, Long> mapUnitToParentID = new HashMap<>();

        while(reader.next()){
            AdminUnit newUnit = new AdminUnit(reader.get("name"), reader.getInt("admin_level"));
            newUnit.setPopulation(reader.getLong("population"));
            newUnit.setArea(reader.getDouble("area"));
            newUnit.setDensity(reader.getDouble("density"));

            if(reader.getRecordLength() >= 8){
                newUnit.bbox.addPoint(reader.getDouble("x1"), reader.getDouble("y1"));
                newUnit.bbox.addPoint(reader.getDouble("x2"), reader.getDouble("y2"));
                newUnit.bbox.addPoint(reader.getDouble("x3"), reader.getDouble("y3"));
                newUnit.bbox.addPoint(reader.getDouble("x4"), reader.getDouble("y4"));
            }

            mapIDToUnit.put(reader.getLong("id"), newUnit);
            mapUnitToParentID.put(newUnit, reader.getLong("parent"));

            this.units.add(newUnit);
        }

        for(AdminUnit unit: this.units){
            long unitParentID = mapUnitToParentID.get(unit);
            if(unitParentID != 0){
                unit.setParent(mapIDToUnit.get(unitParentID));

                /**
                 * obiekt pod kluczem "unitParentID" jest rodzicem obiektu "unit"
                 * zatem mozemy od razu do naszego rodzica dodać jako dziecko obiekt "unit"
                 **/
                mapIDToUnit.get(unitParentID).children.add(unit);
            }
            else{
                unit.setParent(this.abstractRoot);
                this.abstractRoot.children.add(unit);
            }
        }

        this.fixMissingValues();
    }

    void list(PrintStream out){
        for (AdminUnit unit: units){
            out.println(unit);
        }
    }

    void list(PrintStream out, int offset, int limit){
        int size = this.units.size();
        int printed = 0;

        for (int i = offset; i < size; ++i){
            if(printed >= limit){
                break;
            }

            out.println(this.units.get(i));
            ++printed;
        }
    }

    AdminUnitList selectByName(String pattern, boolean regex){
        AdminUnitList res = new AdminUnitList();
        for (AdminUnit unit: this.units){
            if(regex){
                if(unit.name.matches(pattern)){
                    res.units.add(unit);
                }
            }
            else{
                if(unit.name.contains(pattern)){
                    res.units.add(unit);
                }
            }
        }
        return res;
    }

    private void fixMissingValues(){
        for (AdminUnit unit: this.units){
            unit.fixMissingValues();
        }
    }

    /**
     * Zwraca listę jednostek sąsiadujących z jendostką unit na tym samym poziomie hierarchii admin_level.
     * Czyli sąsiadami wojweództw są województwa, powiatów - powiaty, gmin - gminy, miejscowości - inne miejscowości
     * @param unit - jednostka, której sąsiedzi mają być wyznaczeni
     * @param maxdistance - parametr stosowany wyłącznie dla miejscowości, maksymalny promień odległości od środka unit,
     *                    w którym mają sie znaleźć punkty środkowe BoundingBox sąsiadów
     * @return lista wypełniona sąsiadami
     */
    AdminUnitList getNeighbors(AdminUnit unit, double maxdistance){
        double xmin = unit.bbox.xmin;
        double xmax = unit.bbox.xmax;
        double ymin = unit.bbox.ymin;
        double ymax = unit.bbox.ymax;
        double xCenter = unit.bbox.getCenterX();
        double yCenter = unit.bbox.getCenterY();

        if(unit.adminLevel == 8) {
            double dAngle = Math.toDegrees(maxdistance/6371.0);

            unit.bbox.xmin = xCenter - dAngle;
            unit.bbox.xmax = xCenter + dAngle;
            unit.bbox.ymin = yCenter - dAngle;
            unit.bbox.ymax = yCenter + dAngle;
        }
        AdminUnitList neighbors = new AdminUnitList();
        List<AdminUnit> queue = new ArrayList<>(this.abstractRoot.children);
        while (!queue.isEmpty()){
            AdminUnit neighbor = queue.remove(queue.size()-1);
            if (unit != neighbor && unit.bbox.intersects(neighbor.bbox)){
                if(neighbor.adminLevel == unit.adminLevel){
                    neighbors.units.add(neighbor);
                }else{
                    for(AdminUnit ch: neighbor.children){
                        queue.add(ch);
                    }
                }
            }
        }

        if(unit.adminLevel == 8){
            unit.bbox.xmin = xmin;
            unit.bbox.xmax = xmax;
            unit.bbox.ymin = ymin;
            unit.bbox.ymax = ymax;
        }
        return neighbors;
    }

    AdminUnitList deprecatedGetNeighbors(AdminUnit unit, double maxdistance){
        AdminUnitList neighbors = new AdminUnitList();
        if (unit.adminLevel == 8){
            for (AdminUnit neighbor: this.units){
                if(neighbor.adminLevel == 8 && unit.bbox.distanceTo(neighbor.bbox) <= maxdistance){
                    if(neighbor != unit){
                        neighbors.units.add(neighbor);
                    }
                }
            }
        }else{
            for (AdminUnit neighbor: this.units){
                if(neighbor.adminLevel == unit.adminLevel && unit.bbox.intersects(neighbor.bbox)){
                    if(neighbor != unit){
                        neighbors.units.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    AdminUnitList sortInplaceByName(){
        this.units.sort(new AdminUnitComparator());
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    AdminUnitList sortInplaceByArea(){
        this.units.sort(new Comparator<AdminUnit>() {
            @Override
            public int compare(AdminUnit unit1, AdminUnit unit2) {
                return Double.compare(unit1.area, unit2.area);
            }
        });
        return this;
    }

    /**
     * Sortuje daną listę jednostek (in place = w miejscu)
     * @return this
     */
    AdminUnitList sortInplaceByPopulation(){
        this.units.sort( (AdminUnit u1, AdminUnit u2) -> Long.compare(u1.population, u2.population));
        return this;
    }

    AdminUnitList sortInplace(Comparator<AdminUnit> cmp){
        this.units.sort(cmp);
        return this;
    }

    AdminUnitList sort(Comparator<AdminUnit> cmp){
        AdminUnitList newAdminList = new AdminUnitList();

        newAdminList.abstractRoot = this.abstractRoot;
        newAdminList.units = new ArrayList<>(this.units);
        newAdminList.sortInplace(cmp);

        return newAdminList;
    }

    /**
     *
     * @param pred referencja do interfejsu Predicate
     * @return nową listę, na której pozostawiono tylko te jednostki,
     * dla których metoda test() zwraca true
     */
    AdminUnitList filter(Predicate<AdminUnit> pred){
        return this.filter(pred,0, Integer.MAX_VALUE);
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred
     * @param pred - predykat
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    AdminUnitList filter(Predicate<AdminUnit> pred, int limit){
        return this.filter(pred, 0, limit);
    }

    /**
     * Zwraca co najwyżej limit elementów spełniających pred począwszy od offset
     * Offest jest obliczany po przefiltrowaniu
     * @param pred - predykat
     * @param offset - od którego elementu
     * @param limit - maksymalna liczba elementów
     * @return nową listę
     */
    AdminUnitList filter(Predicate<AdminUnit> pred, int offset, int limit){
        AdminUnitList newAdminList = new AdminUnitList();
        int counter = 0;

        newAdminList.abstractRoot = this.abstractRoot;
        for (AdminUnit unit: this.units){
            if(counter >= limit){
                break;
            }

            if(pred.test(unit)){
                if (offset<=0){
                    newAdminList.units.add(unit);
                    ++counter;
                }
                --offset;
            }
        }

        return newAdminList;
    }

    public String toString(){
        StringBuilder b = new StringBuilder();
        for (AdminUnit unit: this.units){
            b.append(unit);
            b.append("\n");
        }

        return b.toString();
    }

}
