import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminUnitList {
    List<AdminUnit> units = new ArrayList<>();

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

    public static void main(String[] args){
        AdminUnitList polishUnits = new AdminUnitList();
        polishUnits.read("admin-units.csv");
        polishUnits.list(System.out);
    }

}
