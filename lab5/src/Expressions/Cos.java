package Expressions;

public class Cos extends Node{
    Node arg;

    public Cos(Node arg){
        this.arg = arg;
    }

    @Override
    public double evaluate(){
        return this.sign*Math.cos(this.arg.evaluate());
    }

    public String toString(){
        StringBuilder b = new StringBuilder();

        if(this.sign<0){
            b.append("-");
        }
        b.append("cos("+this.arg+")");

        return b.toString();
    }

    protected int getArgumentsCount() {
        return 1;
    }

    @Override
    protected Node _diff(Variable var) {
        Node r = new Prod(new Sin(this.arg), this.arg._diff(var)).minus();
        r.sign *= this.getSign();
        return r;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public Node simplify() {
        Node r = new Cos(this.arg.simplify());
        r.sign = this.getSign();
        return r;
    }
}

