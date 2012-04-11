proc ret int Add( int val1, int val2 )
{
    return val1 + val2; 
}

proc Main()
{
    printint( Add( 40 ) ); // error, procedure takes two arguments
}
