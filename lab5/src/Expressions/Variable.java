package Expressions;

public class Variable extends Node{
    String name;
    double value;

    public Variable(String name){
        this.name = name;
    }

    public void setValue(double d){
        this.value = d;
    }

    @Override
    public double evaluate(){
        return this.sign * this.value;
    }

    @Override
    public String toString(){
        String sgn = this.sign<0?"-":"";
        return sgn+this.name;
    }

    protected int getArgumentsCount(){
        return 1;
    }

    @Override
    protected Node _diff(Variable var){
        if(var.name.equals(this.name)){
            return new Constant(this.sign);
        }else {
            return new Constant(0);
        }
    }

    @Override
    public boolean isZero(){
        return false;
    }

    @Override
    public Node simplify(){
        return this;
    }
}
