package Expressions;

import java.util.ArrayList;
import java.util.List;

public class Prod extends Node {
    List<Node> args = new ArrayList<>();

    public Prod() {
    }

    public Prod(Node n) {
        args.add(n);
    }

    public Prod(double c) {
        this(new Constant(c));
    }

    public Prod(Node n1, Node n2) {
        this.args.add(n1);
        this.args.add(n2);
    }

    public Prod(double c, Node n) {
        this(new Constant(c), n);
    }

    public Prod mul(Node n) {
        this.args.add(n);

        return this;
    }

    public Prod mul(double c) {
        this.args.add(new Constant(c));

        return this;
    }

    @Override
    public double evaluate() {
        double result = 1;

        for (Node n : this.args) {
            result *= n.evaluate();
        }

        return this.sign * result;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        if (this.sign < 0) {
            b.append("-");
        }

        if(this.args.size()>=1) {
            Node n = this.args.get(0);
            if (n.getSign() < 0) {
                b.append("(");
                b.append(n.toString());
                b.append(")");
            } else {
                b.append(n.toString());
            }
        }

        for (int i = 1; i < args.size(); ++i) {
            Node n = this.args.get(i);
            b.append("*");
            if (n.getSign() < 0 || n.getArgumentsCount() > 1) {
                b.append("(");
                b.append(n.toString());
                b.append(")");
            } else {
                b.append(n.toString());
            }
        }

        return b.toString();
    }

    protected int getArgumentsCount() {
        return this.args.size();
    }

    @Override
    protected Node _diff(Variable var) {
        Sum r = new Sum();

        for (int i = 0; i < args.size(); i++) {
            Prod m = new Prod();

            for (int j = 0; j < args.size(); j++) {
                Node f = args.get(j);

                if (j == i) {
                    m.mul(f._diff(var));
                } else {
                    m.mul(f);
                }
            }
            r.add(m);
        }
        return r;
    }

    @Override
    public boolean isZero() {
        for (Node n : this.args) {
            if (n.isZero()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Node simplify() {

        Prod temp = new Prod();
        temp.sign = this.getSign();

        for (Node n1 : this.args){
            Node n2 = n1.simplify();

            if(n2.isZero()){
                return new Constant(0);
            }

            temp.sign *= n2.getSign();
            n2.plus();

            if (n2 instanceof Prod){
                for (Node nn : ((Prod) n2).args){
                    temp.mul(nn);
                }
            }else{
                temp.mul(n2);
            }
        }

        Prod temp2 = new Prod();
        temp2.sign = temp.getSign();

        double constantResult = 1;
        for (Node n : temp.args){
            if (n instanceof Constant){
                constantResult *= ((Constant) n).value;
            }
        }

        if(constantResult != 1){
            temp2.mul(constantResult);
        }

        for (Node n: temp.args){
            if (! (n instanceof Constant)){
                temp2.mul(n);
            }
        }


        if(temp2.args.size()==1){
            Node r = temp2.args.get(0);
            r.sign *= temp2.getSign();
            return r;
        }else{
            return temp2;
        }
    }
}
