(* switch case in pascal *)
program checkCase;
var
   grade1: char;
   grade2: integer;
begin
   grade1 := 'A';
   grade2 := 92;

   case (grade1) of
      'A' : writeln('Excellent!' );
      'B', 'C': writeln('Well done' );
      'D' : writeln('You passed' );
      'F' : writeln('Better try again' );
   end;   

   case grade2 of
      90..100 : writeln('Excellent!' );
      70..89: writeln('Well done' );
      60..69 : writeln('You passed' );
      0..50 : writeln('Better try again' );
   end;       
   
   case grade2 of
      90..100 : begin
                   writeln('Excellent!' );
                   writeln('You have a high score' );
                end;
      70..89: writeln('Well done' );
      60..69 : writeln('You passed' );
      0..50 : writeln('Better try again' );
   end;       
   
   case grade2 of
      50..100 : begin
                   writeln('You passed!' );
                end;

      ELSE writeln('You have a low score' );
   end;       

   writeln('Your grade is  ', grade1 );
end.