import java.util.ArrayList;
import java.util.List;

public class AdminUnit {
    String name;
    int adminLevel;
    long population = 0;
    double area = 0;
    double density = 0;
    AdminUnit parent = null;
    BoundingBox bbox = new BoundingBox();
    List<AdminUnit> children = new ArrayList<>();

    AdminUnit(String name, int adminLevel){
        this.name = name;
        this.adminLevel = adminLevel;
    }

    public void setPopulation(long population){
        this.population = population;
    }

    public void setArea(double area){
        this.area = area;
    }

    public void setDensity(double density){
        this.density = density;
    }

    public void setParent(AdminUnit parent){
        this.parent = parent;
    }

    void fixMissingValues(){
        if(this.population == 0 || this.density == 0){
            if(this.parent != null) {
                this.parent.fixMissingValues();
                this.density = this.parent.density;
                this.population = (long) (this.area * this.density);
            }
        }
        else if(this.population == 0 && this.density > 0){
            this.population = (long) (this.area * this.density);
        }
        else if(this.population > 0 && this.density == 0){
            if(this.parent != null){
                this.parent.fixMissingValues();
                this.density = this.parent.density;
            }
        }

    }

    public String toString(){
        StringBuilder b = new StringBuilder();
        b.append("Nazwa: ");
        b.append(this.name);

        b.append(", typ jednostki: ");
        b.append(this.adminLevel);

        b.append(", powierzchnia: ");
        b.append(this.area);

        b.append(", populacja: ");
        b.append(this.population);

        return b.toString();
    }
}
