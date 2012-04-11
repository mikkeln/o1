proc ret int Add( int val1, int val2 )
{
    return val1 + val2;
}

proc Main()
{
    printint( Add( 42, true ) ); // error, second arg should be int
}
