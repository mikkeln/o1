proc Proc()
{
    printstr( "Hello world!" );
}

proc Main()
{
    var int tmp;
    tmp := Proc(); // error, Proc() is declared void
}
