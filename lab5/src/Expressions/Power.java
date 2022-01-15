package Expressions;

public class Power extends Node{
    double p;
    Node arg;

    public Power(Node n, double p){
        this.arg = n;
        this.p = p;
    }

    @Override
    public double evaluate(){
        double argVal = this.arg.evaluate();
        return Math.pow(argVal,p);
    }

    @Override
    public String toString(){
        StringBuilder b = new StringBuilder();

        if(this.sign < 0){
            b.append("-");
        }

        int argSign = this.arg.getSign();
        int cnt = this.arg.getArgumentsCount();

        boolean useBracket = false;

        if(argSign < 0 || cnt > 1){
            useBracket = true;
            b.append("(");
        }

        b.append(this.arg.toString());

        if(useBracket){
            b.append(")");
        }

        b.append("^");
        b.append(p);

        return b.toString();
    }

    protected int getArgumentsCount(){
        return 2;
    }

    @Override
    protected Node _diff(Variable var){
        Prod r;

        if(p == 1){
            r = new Prod(this.getSign()*this.p, this.arg._diff(var));
        }else{
            r =new Prod(this.sign*this.p,new Power(this.arg, this.p-1));
            r.mul(this.arg._diff(var));
        };
        return r;
    }

    @Override
    public boolean isZero(){
        return false;
    }

    @Override
    public Node simplify() {
        if(this.p == 0){
            return new Constant(this.sign*1);
        }else if(this.p == 1){
            Node r = this.arg;
            r.sign *= this.getSign();
            return r.simplify();
        }else {
            Node r = new Power(this.arg.simplify(),this.p);
            r.sign = this.getSign();
            return r;
        }
    }
}
