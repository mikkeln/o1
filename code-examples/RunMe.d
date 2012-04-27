class Complex {
    var float Real;
    var float Imag;
}
var Complex dummy;
proc ret Complex Add( Complex a, Complex b ) {
    var Complex retval;
    retval := new Complex;
    retval.Real := a.Real + b.Real;
    retval.Imag := a.Imag + b.Imag;
    return retval;
}
proc ret int Max( int a, int b ) {
    var int res;
    if a > b then {
         res := a;
    }
    else {
        res := b;
    }
    return res;
}
proc printCmplx(Complex pr) {
    printstr("Real ");
    printfloat((pr).Real);
    printline("");
    printstr("Imag ");
    printfloat((pr).Imag);
    printline("");
}
proc test(){
    var Complex c1;
    var Complex c2;
    var Complex cAdd;

    var int x;
    var int y;
    var int max;

    c1 := new Complex;
    c2 := new Complex;
    c1.Real := 1;
    c1.Imag := 2;
    c2.Real := 3;
    c2.Imag := 4;
    printCmplx(Add(c1, c2));

    x:=3;
    y:=7;
    max := Max(y, x);
}
proc printStr(string str) {
    printstr(str);
}
proc inOutTest(){
    var int v1;
    var int v2;
    
    printline("skriv v1");
    v1 := readint();
    printline("skriv v2");
    v2 := readint();
    printstr("Storst ");
    printint(Max(v1, v2));
    printline("");
}
proc Main() {
    var float num;
    var int num2;
    var string navn;

    num := 6.480740;
    printfloat( num );
    printline("");

    num2 := 7;
    printint(num2);
    printline("");
    
    navn := "TestNavn";
    printStr(navn);
    printline("");
    
    test();
    inOutTest();
    
    dummy := new Complex;
    dummy.Real := 1.0;
    dummy.Imag := 2.0;
    printCmplx(dummy);

    printline("DONE");
}
