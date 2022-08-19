(* pascal program that sum two numbers and print result*)
PROGRAM Test;
VAR
   x : REAL;        { variable name is x, type is real }
   i : INTEGER;     { variable name is i, type is integer }
   c : CHAR;        { variable name is c, type is character }
   s : char;      { variable name is s, type is char }
   a,b: char;
   id,y: INTEGER;
BEGIN
    {x := -34.55;}    { valid real number assigned to variable x }
    {x := 56;}   { valid real number assigned to variable x }
    {writeln(x);     { x contains the value -3.9E-3 }
    {id:=2;}
    {i := 2;
    i := i - 256 - 120 - i - 300 - 300 ;}
    i := 32;
    if 16 = 16 then
        begin
            i:=30;
        end;
    i:=50;
    {else
        begin 
            xOneBig:=false;
            xTwoBig:=false;
        end;}

    {i := i + i + i + i + i + 25;}
    {i := 90;}        { valid integer number assigned to variable i }
    {i := id * i + i / i;}     { valid (!) - i will be 1000 now }
    {i := 9933 + 1;}      { valid integer number assigned to variable i }
    {i := -99999;}    { invalid integer - too small }
    {i := 999.44;}    { invalid assignment - types do not match }
    {i := 'A';       { invalid assignment - types do not match }
    {c := '1';       { valid character assigned to variable c }
    {c := 1;         { invalid assignment - types do not match }
    {c := 'Bert';    { invalid assignment - types do not match }
    {c := 'd';}       { valid character assigned to variable c }
    {writeln(c);     { c contains the value 'd' }
    {d := 'c';       { unknown variable - the variable d is not declared }
    {writeln(s);     { invalid reference - s has undefined value }
END.