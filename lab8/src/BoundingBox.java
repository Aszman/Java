import java.util.Locale;
import java.util.NoSuchElementException;

public class BoundingBox {
    double xmin = 0;
    double xmax = 0;
    double ymin = 0;
    double ymax = 0;

    boolean empty = true;

    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    void addPoint(double x, double y) {
        if (this.isEmpty()) {
            this.xmin = this.xmax = x;
            this.ymin = this.ymax = y;

            this.empty = false;
        } else {
            if (x < this.xmin) {
                this.xmin = x;
            } else if (x > this.xmax) {
                this.xmax = x;
            }

            if (y < this.ymin) {
                this.ymin = y;
            } else if (y > this.ymax) {
                this.ymax = y;
            }
        }
    }

    /**
     * Powiększa BB tak, aby zawierał punkt (x,y)
     * @param x - współrzędna x
     * @param y - współrzędna y
     */
    boolean contains(double x, double y) {
        if (this.isEmpty()) {
            return false;
        }

        if (x >= this.xmin && x <= this.xmax) {
            if (y >= this.ymin && y <= this.ymax) {
                return true;
            }
        }

        return false;
    }

    /**
     * Sprawdza czy dany BB zawiera bb
     * @param bb
     * @return
     */
    boolean contains(BoundingBox bb){
        if(this.isEmpty() || bb.isEmpty()){
            return false;
        }

        if(this.xmin <= bb.xmin && this.xmax >= bb.xmax){
            if(this.ymin <= bb.ymin && this.ymax >= bb.ymax){
                return true;
            }
        }

        return false;
    }

    /**
     * Sprawdza, czy dany BB przecina się z bb
     * @param bb
     * @return
     */
    boolean intersects(BoundingBox bb){
        if (this.isEmpty() || bb.isEmpty()){
            return false;
        }

        if(bb.xmax < this.xmin || bb.xmin > this.xmax || bb.ymax < this.ymin || bb.ymin > this.ymax){
            return false;
        }

        return true;
    }

    /**
     * Powiększa rozmiary tak, aby zawierał bb oraz poprzednią wersję this
     * @param bb
     * @return
     */
    BoundingBox add(BoundingBox bb){
        if(!bb.isEmpty() && this.isEmpty()){
            this.xmin = bb.xmin;
            this.xmax = bb.xmax;
            this.ymin = bb.ymin;
            this.ymax = bb.ymax;
            this.empty = false;

        }else if(!this.isEmpty() && !bb.isEmpty()){
            this.xmin = Math.min(this.xmin, bb.xmin);
            this.xmax = Math.max(this.xmax, bb.xmax);
            this.ymin = Math.min(this.ymin, bb.ymin);
            this.ymax = Math.max(this.ymax, bb.ymax);
        }
        return this;
    }

    /**
     * Sprawdza czy BB jest pusty
     * @return
     */
    boolean isEmpty(){
        return this.empty;
    }

    /**
     * Oblicza i zwraca współrzędną x środka
     * @return if !isEmpty() współrzędna x środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterX(){
        if(!isEmpty()){
            return (this.xmin+this.xmax)/2;
        }else{
            throw new NoSuchElementException();
        }
    }

    /**
     * Oblicza i zwraca współrzędną y środka
     * @return if !isEmpty() współrzędna y środka else wyrzuca wyjątek
     * (sam dobierz typ)
     */
    double getCenterY(){
        if(!isEmpty()){
            return (this.ymin+this.ymax)/2;
        }else{
            throw new NoSuchElementException();
        }
    }

    /**
     * Oblicza odległość pomiędzy środkami this bounding box oraz bbx
     * @param bbx prostokąt, do którego liczona jest odległość
     * @return if !isEmpty odległość, else wyrzuca wyjątek lub zwraca maksymalną możliwą wartość double
     * Ze względu na to, że są to współrzędne geograficzne, zamiast odległości użyj wzoru haversine
     * (ang. haversine formula)
     */
    double distanceTo(BoundingBox bbx){
        if(!isEmpty()){
            double lon1 = this.getCenterX();
            double lat1 = this.getCenterY();

            double lon2 = bbx.getCenterX();
            double lat2 = bbx.getCenterY();

            // distance between latitudes and longitudes
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);

            // convert to radians
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);

            // apply formula
            double a = Math.pow(Math.sin(dLat / 2), 2) +
                    Math.pow(Math.sin(dLon / 2), 2) *
                            Math.cos(lat1) *
                            Math.cos(lat2);
            double rad = 6371;
            double c = 2 * Math.asin(Math.sqrt(a));
            return rad * c;
        }else{
            throw new NoSuchElementException();
        }
    }

    public String toString(){
        if(this.isEmpty()) {
            return "";
        }

        return String.format(Locale.US, "Współrzędne: xMin= %f, yMin = %f " +
                                    "xMax = %f, yMax = %f",
                                    this.xmin, this.ymin, this.xmax, this.ymax );

    }

    public String getWKT(){
        if(this.isEmpty()){
            return "";
        }

        return String.format(Locale.US, "LINESTRING(%f %f, %f %f, %f %f, %f %f,%f %f)",
                this.xmin, this.ymin, this.xmin, this.ymax, this.xmax, this.ymax,
                this.xmax, this.ymin, this.xmin, this.ymin);
    }
}
