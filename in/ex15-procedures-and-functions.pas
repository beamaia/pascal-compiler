program exProcedure;
var
   a, b, c,  min: integer;

function min(num1, num2: integer; f1,f2 : string ): integer;

var
   (* local variable declaration *)
   result: integer;

begin
   if (num1 < num2) then
      result := num1;
   
   else
      result := num2;
   max := result;
end;

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

function max(num1, num2: integer; f1,f2 : string ): integer;

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

begin
   writeln(' Enter three numbers: ');
   readln( a, b, c);
   findMin(a, b, c, min); (* Procedure call *)
   
   writeln(' Minimum: ', min);
end.