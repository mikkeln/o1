proc Proc( int a )
{
    a := 42;
}

proc Main()
{
    var int tmp;
    tmp := 32;
    Proc( ref tmp ); // error, procedure does not take a ref parameter
    printint( tmp );
}
