proc ret int Add( int val1, int val2 )
{
    return; // error, no return value
}

proc Main()
{
    printint( Add( 40, 2 ) );
}
