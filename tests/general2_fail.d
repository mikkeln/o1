proc Proc( int a )
{
    var int a; // error, param already named a
    printint( a );
}

proc Main()
{
    Proc();
}
