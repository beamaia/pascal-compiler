program exProcedure;
var
   a, b, c,  min: integer;
   d: real;

function min(num1, num2: integer; f1,f2 : char ): integer;

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

function max(num1, num2: integer; f1,f2 : char ): integer;

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
   findMin(a, b, c, min); (* Procedure call *)
   a:= min + max(a, b, 'a', 'b'); (* Function call *)
   c:= min + max(a, b, 'a', 'b'); (* Function call *)
   writeln(' Minimum: ', min);
end.