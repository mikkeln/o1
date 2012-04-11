proc Main()
{

    var int Tmp;

    proc ret string Tmp() // error, already a symbol named Tmp in current scope
    {
        return "Top of the world, ma!";
    }

    printstr( Tmp() );
}
