proc Main()
{
    var bool flag;
    var int val;
    var float real;
    val := 42;
    flag := val > 40;
    real := val; // implicit conversion int=>float
    printfloat( real );
    printfloat( val ); // implicit conversion int=>float
    printint( val );
}
