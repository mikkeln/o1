proc Proc( ref int a )
{
    a := 42;
}

proc Main()
{
    var int tmp;
    tmp := 32;
    Proc( tmp ); // error, ref not used
    printint( tmp );
}
