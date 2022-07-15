program whileLoop;
var
   a: integer;

begin
   a := 10;
   a := 20;
   while  a  do
   
      begin
         writeln('value of a: ', a);
         a := a + 1;
      end;

   while  a < 20  do
      a:=a+2;

   a := 30;
end.