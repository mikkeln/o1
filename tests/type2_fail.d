proc Main()
{
    if 42 then { // error, expression not bool
        printstr( "Should not happen" );
    }
}
