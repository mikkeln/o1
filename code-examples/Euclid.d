proc ret int gcd(int a, int b){
    var int res;
    if a = 0 then {
        res := b;
    }
    else {
        while b <> 0 do {
            if (a > b) then {
                a := a - b;
		}
            else {
                b := b - a;
		}
        }
        res := a;
    }
    return res;
}
proc Main() {
    printint(gcd(6, 19));
    printline("");
    printint(gcd(6, 9));
    printline("");
    printint(gcd(629, 592));
    printline("");
}
