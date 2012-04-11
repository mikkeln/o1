proc ret string Proc()
{
    return 42; // error, Proc declared as returning int
}

proc Main()
{
    Proc();
}
