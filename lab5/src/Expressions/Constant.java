package Expressions;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Constant extends Node{
    double value;

    public Constant(double value){
        this.sign = value<0?-1:1;
        this.value = value<0?-value:value;
    }

    @Override
    public double evaluate(){
        return this.sign * this.value;
    }

    @Override
    public String toString(){
        String sgn = this.sign<0?"-":"";

        DecimalFormat format = new DecimalFormat("0.#####", new DecimalFormatSymbols(Locale.US));
        return sgn+format.format(this.value);
    }

    protected int getArgumentsCount(){
        return 1;
    }

    @Override
    protected Node _diff(Variable var){
        return new Constant(0);
    }

    @Override
    public boolean isZero(){
        if(this.value == 0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Node simplify() {
        return this;
    }
}
