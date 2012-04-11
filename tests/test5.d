proc Proc( ref int a )
{
    a := 42;
}

proc Main()
{
    var int a;
    a := 32;
    Proc( ref a );
    printint( a );
}
