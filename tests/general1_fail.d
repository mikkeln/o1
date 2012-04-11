proc Main()
{
    proc ret string Tmp()
    {
        return "Top of the world, ma!";
    }

    var int Tmp; // error, already a symbol named Tmp in current scope

    printstr( Tmp() );
}
