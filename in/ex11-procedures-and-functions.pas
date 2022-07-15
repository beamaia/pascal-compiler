program exProcedure;
var
   a, b, c, min: integer;
   d: real;

procedure findMin(x, y, z: integer; m: integer); 
(* Finds the minimum of the 3 values *)

begin
   if x < y then
      m:= x;
   else
      m:= y;
   
   if z < m then
      m:= z;
end; { end of procedure findMin }  

function max(num1, num2: integer): integer;

var
   (* local variable declaration *)
   result: integer;

begin
   if (num1 > num2) then
      result := num1;
   
   else
      result := num2;
   max := result;
end;

function five(): integer;
begin
   five := 5;
end;

begin
   writeln(' Enter three numbers: ');
   readln( a, b, c);
   five();
   max(a, b);
   writeln(' Minimum: ', a);
end.