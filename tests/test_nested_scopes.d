proc Main()
{
    var bool flag;
    
    proc change_flag() {
         flag := not flag;
    }
    flag := false;    
    
    change_flag();
}
