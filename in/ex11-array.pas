program testarray1;  
Type  
  TA = Array[0..9,0..9] of Integer;
  TIntegerArray = Array of Integer;  
  TIntegerArrayArray = Array of TIntegerArray;  
  TAOA = Array of array of Integer;  
 
var  
  A,B : TA;  
  I,J : Integer;  
begin  
   For I:=0 to 9 do  
      For J:=0 to 9 do  
         A[I,J]:=I*J;  
   For I:=0 to 9 do  
      begin  
      For J:=0 to 9 do  
       (*  Write(A[I,J]:2,' ');  *)
      Writeln;  
      end;  
   B:=A;  
   Writeln;  
   For I:=0 to 9 do  
      For J:=0 to 9 do  
         A[9-I,9-J]:=I*J;  
   For I:=0 to 9 do  
      begin  
      For J:=0 to 9 do  
         Write(B[I,J]:2,' ');  
      Writeln;  
      end;  
end.