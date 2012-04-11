proc Main()
{
    class Zot {var int x;}
    var Zot foo;
    foo := new Zot;
    (zot).bar := 42; // error, zot.bar not declared
}
