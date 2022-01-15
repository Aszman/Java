package Expressions;

abstract public class Node {
    int sign = 1;

    public Node minus() {
        this.sign = -1;
        return this;
    }

    public Node plus() {
        this.sign = 1;
        return this;
    }

    public int getSign() {
        return this.sign;
    }

    public abstract double evaluate();

    public String toString() {
        return "";
    }

    protected int getArgumentsCount() {
        return 0;
    }

    //Method to simplify automatically after derivation
    public Node diff(Variable var) {
        return this._diff(var).simplify();
    }

    protected abstract Node _diff(Variable var);

    public abstract boolean isZero();

    public abstract Node simplify();
}
