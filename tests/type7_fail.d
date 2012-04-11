class Foo
{
    var int Attr;
}

proc Main()
{
    var Foo foo;
    (foo).DoesntExist := 42; // error, no such attribute
}
