proc Main()
{
    proc f() { return; }
    return new f; // Error: argument of new is not declared.
}
