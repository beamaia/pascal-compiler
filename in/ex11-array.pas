program testarray1;  
 
var  
  A,B : Array[0..9] of Integer;  
  D,E : Array[1..3] of Real;  
  G,H : Array[-1..4] of Char;  

  I,J : Integer;  
  L: Real;
begin
   I := 0;
   A[1] := B[I+1];
   A[2] := 2;
   A[3] := 3;
   while  I < 20  do
   
      begin
         A[I] := I;
         I := I + 1;

      end;

 {  For I:=0 to 7 do  
      For J:=0 to 7 do  
         A[I] := A[I]*A[J];  
   For I:=0 to 9 do  
      begin  
      For J:=0 to 8 do  
      Writeln;  
      end;  
   B:=A;  
   Writeln;  
   For I:=0 to 9 do  
      For J:=0 to 10 do  
         A[9-K]:=I*J;  
   For I:=0 to 9 do  
      begin  
      For J:=0 to 5 do  
         Writeln(B[L]);  
      Writeln;  
      end;  }
end.