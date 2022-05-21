program exFunction;
var
   a, b, ret : integer;

(*function definition *)
(* function name(argument(s): type1; argument(s): type2; ...): function_type; 
*)
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
   a := 100;
   b := 200;
   (* calling a function to get max value *)
   ret := max(a, b);
   
   writeln( 'Max value is : ', ret );
end.
