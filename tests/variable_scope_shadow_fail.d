proc Main()
{
    var bool flag;
    
    proc change_flag() {
         var int flag; 
         flag := not flag; // error, refers to local int, not outer level boolean!
    }
    flag := false;    
    change_flag();
}
