// the role of an interface (eg a Set) is to provide a contract
// the Set below describes what can be done.
interface Set<X> {
    public void insert(X x);
    public void delete(X x);
    public void empty();
    public boolean contains(X x);
    public int size();
}

// a concrete implementation will describe how its done? Perchance?



//inner classses are defined within another class
// has an implicit pointer to the outer class


// (0_0) <(Oh jeeebz!)
//  -|-
//  / \
