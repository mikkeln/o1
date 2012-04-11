class Foo
{
    var int Attr;
}

proc Main()
{
    var Foo f;
    f := new Foo;
    f.Attr := 42;
    printint( f.Attr );
    printfloat( f.Attr );
}

