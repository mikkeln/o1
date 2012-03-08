class Complex {
    var float Real;
    var float Imag;
}

proc Swap( ref int a, ref int b )
{
    var int tmp;
    tmp := a;
    a := b;
    b := tmp;
}

proc ret Complex Add( Complex a, Complex b )
{
    var Complex retval;
    retval := new Complex;
    retval.Real := a.Real + b.Real;
    retval.Imag := a.Imag + b.Imag;
    
    return retval;
}

proc ret int Max( int a, int b )
{

    if a > b then
    {
       return a;
    }
    
    if not(a > (b+b)) then
    {
       return a+b;
    }
    
    return b;
}

proc Main()
{
    proc ret float Square( float val )
    {
        return val ** 2.0;
    }
    var float num;
    
    num := 6.480740;
    printfloat( num );
    printstr( " squared is " );
    printfloat( Square( num ) );
    return;
}
