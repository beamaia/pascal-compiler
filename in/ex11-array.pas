program testarray1;  
 
var  
  A,B : Array[0..9] of Integer;  
  D,E : Array[1..3] of Real;  
  G,H : Array[-1..4] of Char;  

  I,J : Integer;  
  L: Real;
begin  
   For I:=0 to 7 do  
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
      end;  
end.