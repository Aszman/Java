package Expressions;

public class Sin extends Node{
    Node arg;

    public Sin(Node arg){
        this.arg = arg;
    }

    @Override
    public double evaluate(){
        return this.sign*Math.sin(this.arg.evaluate());
    }

    public String toString(){
        StringBuilder b = new StringBuilder();

        if(this.sign<0){
            b.append("-");
        }
        b.append("sin("+this.arg+")");

        return b.toString();
    }

    protected int getArgumentsCount() {
        return 1;
    }

    @Override
    protected Node _diff(Variable var) {
        Node r = new Prod(new Cos(this.arg),this.arg._diff(var));
        r.sign = this.getSign();
        return r;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public Node simplify() {
        Node r = new Sin(this.arg.simplify());
        r.sign = this.getSign();
        return r;
    }
}
