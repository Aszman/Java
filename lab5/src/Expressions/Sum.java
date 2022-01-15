package Expressions;

import java.util.ArrayList;
import java.util.List;

public class Sum extends Node {
    List<Node> args = new ArrayList<>();

    public Sum() {
    }

    public Sum(Node n1, Node n2) {
        this.args.add(n1);
        this.args.add(n2);
    }

    public Sum add(Node n) {
        this.args.add(n);

        return this;
    }

    public Sum add(double c) {
        this.args.add(new Constant(c));

        return this;
    }

    public Sum add(double c, Node n) {
        Node mul = new Prod(c, n);
        this.args.add(mul);

        return this;
    }

    @Override
    public double evaluate() {
        double result = 0;

        for (Node n : this.args) {
            result += n.evaluate();
        }
        return this.sign * result;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        if (sign < 0) {
            b.append("-(");
        }

        if (args.size() > 0) {
            b.append(args.get(0).toString());
        }

        for (int i = 1; i < args.size(); ++i) {
            if (args.get(i).getSign() > 0) {
                b.append("+");
            }
            b.append(args.get(i).toString());
        }

        if (sign < 0) {
            b.append(")");
        }
        return b.toString();
    }

    protected int getArgumentsCount() {
        return args.size();
    }

    @Override
    protected Node _diff(Variable var) {
        Sum r = new Sum();

        for (Node n : this.args) {
            Node n_diff = n._diff(var);

            r.add(n_diff);
        }
        return r;
    }

    @Override
    public boolean isZero() {
        for(Node n: this.args){
            if(!n.isZero()){
                return false;
            }
        }
        return true;
    }

    @Override
    public Node simplify(){
        Sum r = new Sum();
        r.sign = this.getSign();

        for(Node n: this.args){
            Node n_simp = n.simplify();
            if(!n_simp.isZero()){
                r.add(n_simp);
            }
        }
        if (r.isZero()){
            return new Constant(0);
        }else if(r.args.size() == 1){
            Node new_r = r.args.get(0);
            new_r.sign *= r.getSign();
            return new_r;
        }else{
            return r;
        }
    }
}
